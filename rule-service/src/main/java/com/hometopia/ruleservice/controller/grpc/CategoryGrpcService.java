package com.hometopia.ruleservice.controller.grpc;

import com.hometopia.proto.category.CategoryGrpcServiceGrpc;
import com.hometopia.proto.category.GetListCategoryRequest;
import com.hometopia.proto.category.GetListCategoryResponse;
import com.hometopia.ruleservice.service.CategoryService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CategoryGrpcService extends CategoryGrpcServiceGrpc.CategoryGrpcServiceImplBase {

    private final CategoryService categoryService;

    @Override
    public void getListCategory(GetListCategoryRequest request, StreamObserver<GetListCategoryResponse> responseObserver) {
        responseObserver.onNext(categoryService.getListCategories(request));
        responseObserver.onCompleted();
    }
}
