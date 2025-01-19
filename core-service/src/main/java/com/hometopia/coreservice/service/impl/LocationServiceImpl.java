package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateLocationRequest;
import com.hometopia.coreservice.dto.request.UpdateLocationRequest;
import com.hometopia.coreservice.dto.response.CreateLocationResponse;
import com.hometopia.coreservice.dto.response.GetListLocationResponse;
import com.hometopia.coreservice.dto.response.GetOneLocationResponse;
import com.hometopia.coreservice.dto.response.UpdateLocationResponse;
import com.hometopia.coreservice.entity.Location;
import com.hometopia.coreservice.entity.QLocation;
import com.hometopia.coreservice.mapper.LocationMapper;
import com.hometopia.coreservice.repository.LocationRepository;
import com.hometopia.coreservice.repository.UserRepository;
import com.hometopia.coreservice.service.LocationService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Override
    public RestResponse<ListResponse<GetListLocationResponse>> getListLocations(int page, int size, String sort, String filter, boolean all) {
        Specification<Location> sortable = RSQLJPASupport.toSort(sort);
        Specification<Location> filterable = RSQLJPASupport.toSpecification(
                Optional.ofNullable(filter)
                        .map(f -> URLDecoder.decode(f, StandardCharsets.UTF_8))
                        .orElse(null)
        );
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<GetListLocationResponse> responses = locationRepository
                .findAll(sortable.and(filterable).and((Specification<Location>) (root, query, cb) ->
                        cb.equal(root.get(QLocation.location.user.getMetadata().getName()),
                                userRepository.getReferenceById(SecurityUtils.getCurrentUserId()))
                ), pageable)
                .map(locationMapper::toGetListLocationResponse);
        return RestResponse.ok(ListResponse.of(responses));
    }

    @Override
    public RestResponse<GetOneLocationResponse> getOneLocation(String id) {
        return locationRepository.findById(id)
                .map(locationMapper::toGetOneLocationResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
    }

    @Override
    @Transactional
    public RestResponse<CreateLocationResponse> createLocation(CreateLocationRequest request) {
        Location location = locationMapper.toLocation(request);
        locationRepository.save(location);
        return RestResponse.ok(locationMapper.toCreateLocationResponse(location));
    }

    @Override
    @Transactional
    public RestResponse<UpdateLocationResponse> updateLocation(String id, UpdateLocationRequest request) {
        return locationRepository.findById(id)
                .map(location -> locationMapper.updateLocation(location, request))
                .map(locationMapper::toUpdateLocationResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
    }

    @Override
    @Transactional
    public void deleteLocation(String id) {
        locationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteListLocations(List<String> ids) {
        locationRepository.deleteAllById(ids);
    }
}
