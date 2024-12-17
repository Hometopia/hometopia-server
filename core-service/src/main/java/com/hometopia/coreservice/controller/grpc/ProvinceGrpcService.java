package com.hometopia.coreservice.controller.grpc;

import com.hometopia.coreservice.service.ProvinceService;
import com.hometopia.proto.province.GetProvinceRequest;
import com.hometopia.proto.province.GetProvinceResponse;
import com.hometopia.proto.province.ProvinceGrpcServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProvinceGrpcService extends ProvinceGrpcServiceGrpc.ProvinceGrpcServiceImplBase {

    private final ProvinceService provinceService;

    @Override
    public void getProvince(GetProvinceRequest request, StreamObserver<GetProvinceResponse> responseObserver) {
        responseObserver.onNext(provinceService.getProvince(request));
        responseObserver.onCompleted();
    }
}
