package com.hometopia.ruleservice.controller.grpc;

import com.hometopia.proto.asset.AssetGrpcServiceGrpc;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleRequest;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleResponse;
import com.hometopia.proto.asset.GetAssetUsefulLifeRequest;
import com.hometopia.proto.asset.GetAssetUsefulLifeResponse;
import com.hometopia.ruleservice.service.RuleService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AssetGrpcService extends AssetGrpcServiceGrpc.AssetGrpcServiceImplBase {

    private final RuleService ruleService;

    @Override
    public void getUsefulLife(GetAssetUsefulLifeRequest request, StreamObserver<GetAssetUsefulLifeResponse> responseObserver) {
        responseObserver.onNext(ruleService.getAssetUsefulLife(request));
        responseObserver.onCompleted();
    }

    @Override
    public void getMaintenanceCycle(GetAssetMaintenanceCycleRequest request, StreamObserver<GetAssetMaintenanceCycleResponse> responseObserver) {
        responseObserver.onNext(ruleService.getAssetMaintenanceCycle(request));
        responseObserver.onCompleted();
    }
}
