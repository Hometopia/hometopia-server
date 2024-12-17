package com.hometopia.coreservice.controller.grpc;

import com.hometopia.coreservice.service.DistrictService;
import com.hometopia.proto.district.DistrictGrpcServiceGrpc;
import com.hometopia.proto.district.GetDistrictRequest;
import com.hometopia.proto.district.GetDistrictResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class DistrictGrpcService extends DistrictGrpcServiceGrpc.DistrictGrpcServiceImplBase {

    private final DistrictService districtService;

    @Override
    public void getDistrict(GetDistrictRequest request, StreamObserver<GetDistrictResponse> responseObserver) {
        responseObserver.onNext(districtService.getGetDistrict(request));
        responseObserver.onCompleted();
    }
}
