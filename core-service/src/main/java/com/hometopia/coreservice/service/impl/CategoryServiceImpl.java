package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateListCategoriesRequest;
import com.hometopia.coreservice.dto.request.UpdateCategoryRequest;
import com.hometopia.coreservice.dto.response.CreateCategoryResponse;
import com.hometopia.coreservice.dto.response.GetListCategoryResponse;
import com.hometopia.coreservice.dto.response.UpdateCategoryResponse;
import com.hometopia.coreservice.entity.Category;
import com.hometopia.coreservice.entity.QCategory;
import com.hometopia.coreservice.entity.User;
import com.hometopia.coreservice.mapper.CategoryMapper;
import com.hometopia.coreservice.repository.CategoryRepository;
import com.hometopia.coreservice.repository.UserRepository;
import com.hometopia.coreservice.service.CategoryService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public RestResponse<ListResponse<GetListCategoryResponse>> getListCategories(int page, int size, String sort, String filter, boolean all) throws UnsupportedEncodingException {
        Specification<Category> sortable = RSQLJPASupport.toSort(sort);
        Specification<Category> filterable = RSQLJPASupport.toSpecification(
                Optional.ofNullable(filter)
                        .map(f -> URLDecoder.decode(f, StandardCharsets.UTF_8))
                        .orElse(null)
        );
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<GetListCategoryResponse> responses = categoryRepository
                .findAll(sortable.and(filterable).and((Specification<Category>) (root, query, cb) ->
                    cb.equal(root.get(QCategory.category.user.getMetadata().getName()),
                            userRepository.getReferenceById(SecurityUtils.getCurrentUserId()))
                ), pageable)
                .map(categoryMapper::toGetListCategoryResponse);
        return RestResponse.ok(ListResponse.of(responses));
    }

    @Override
    @Transactional
    public RestResponse<List<CreateCategoryResponse>> createListCategories(CreateListCategoriesRequest request) {
        User currentUser = userRepository.getReferenceById(SecurityUtils.getCurrentUserId());
        return RestResponse.created(categoryRepository.saveAll(request.categories().stream()
                .map(category -> categoryMapper.toCategory(category, null, currentUser)).toList())
                .stream().map(categoryMapper::toCategoryResponse).toList());
    }

    @Override
    @Transactional
    public RestResponse<UpdateCategoryResponse> updateCategory(String id, UpdateCategoryRequest request) {
        return categoryRepository.findById(id)
                .map(category -> categoryMapper.updateCategory(category, request))
                .map(categoryMapper::toUpdateCategoryResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteListCategories(List<String> ids) {
        categoryRepository.deleteAllById(ids);
    }
}
