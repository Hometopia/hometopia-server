package com.hometopia.coreservice.controller.grpc;

import com.hometopia.coreservice.service.WardService;
import com.hometopia.proto.ward.GetWardRequest;
import com.hometopia.proto.ward.GetWardResponse;
import com.hometopia.proto.ward.WardGrpcServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class WardGrpcService extends WardGrpcServiceGrpc.WardGrpcServiceImplBase {

    private final WardService wardService;

    @Override
    public void getWard(GetWardRequest request, StreamObserver<GetWardResponse> responseObserver) {
        responseObserver.onNext(wardService.getWard(request));
        responseObserver.onCompleted();
    }
}
