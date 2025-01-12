package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.enumeration.HouseType;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateListCategoriesRequest;
import com.hometopia.coreservice.dto.request.UpdateCategoryRequest;
import com.hometopia.coreservice.dto.response.CreateCategoryResponse;
import com.hometopia.coreservice.dto.response.SuggestedCategoryResponse;
import com.hometopia.coreservice.dto.response.UpdateCategoryResponse;
import com.hometopia.coreservice.mapper.CategoryMapper;
import com.hometopia.coreservice.mapper.HouseTypeMapper;
import com.hometopia.coreservice.service.CategoryService;
import com.hometopia.proto.category.CategoryGrpcServiceGrpc;
import com.hometopia.proto.category.GetListCategoryRequest;
import com.hometopia.proto.category.GetListCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final HouseTypeMapper houseTypeMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GrpcClient("rule-service")
    private CategoryGrpcServiceGrpc.CategoryGrpcServiceBlockingStub categoryGrpcServiceBlockingStub;

    @GetMapping(value = "/suggested", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<ListResponse<SuggestedCategoryResponse>>> getListSuggestedCategories(@RequestParam HouseType houseType) {
        GetListCategoryResponse response = categoryGrpcServiceBlockingStub.getListCategory(GetListCategoryRequest.newBuilder()
                .setHouseType(houseTypeMapper.toProtoHouseType(houseType))
                .build());

        return ResponseEntity.ok(RestResponse.ok(ListResponse.of(response.getCategoriesList().stream()
                .map(categoryMapper::toSuggestedCategoryResponse)
                .toList())));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<ListResponse<com.hometopia.coreservice.dto.response.GetListCategoryResponse>>> getListCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) boolean all
    ) throws UnsupportedEncodingException {
        return ResponseEntity.ok(categoryService.getListCategories(page, size, sort, filter, all));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<List<CreateCategoryResponse>>> createListCategories(
            @RequestBody @Valid CreateListCategoriesRequest request) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri())
                .body(categoryService.createListCategories(request));
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse<RestResponse<UpdateCategoryResponse>> updateCategory(@PathVariable String id,
                                                                             @RequestBody @Valid UpdateCategoryRequest request) {
        return RestResponse.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestParam List<String> ids) {
        categoryService.deleteListCategories(ids);
        return ResponseEntity.noContent().build();
    }
}
