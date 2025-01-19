package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateLocationRequest;
import com.hometopia.coreservice.dto.request.UpdateLocationRequest;
import com.hometopia.coreservice.dto.response.CreateLocationResponse;
import com.hometopia.coreservice.dto.response.GetListLocationResponse;
import com.hometopia.coreservice.dto.response.GetOneLocationResponse;
import com.hometopia.coreservice.dto.response.UpdateLocationResponse;
import com.hometopia.coreservice.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<ListResponse<GetListLocationResponse>>> getListLocations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) boolean all
    ) {
        return ResponseEntity.ok(locationService.getListLocations(page, size, sort, filter, all));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<GetOneLocationResponse>> getOneLocation(@PathVariable String id) {
        return ResponseEntity.ok(locationService.getOneLocation(id));
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<CreateLocationResponse>> createLocation(@RequestBody @Valid CreateLocationRequest request) {
        RestResponse<CreateLocationResponse> response = locationService.createLocation(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<UpdateLocationResponse>> updateLocation(@PathVariable String id,
                                                                            @RequestBody @Valid UpdateLocationRequest request) {
        return ResponseEntity.ok(locationService.updateLocation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteListLocations(@RequestParam List<String> ids) {
        locationService.deleteListLocations(ids);
        return ResponseEntity.noContent().build();
    }
}
