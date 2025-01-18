package com.hometopia.vendorservice.controller.grpc;

import com.hometopia.proto.vendor.GetListVendorRequest;
import com.hometopia.proto.vendor.GetListVendorResponse;
import com.hometopia.proto.vendor.VendorGrpcServiceGrpc;
import com.hometopia.vendorservice.service.VendorService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class VendorGrpcService extends VendorGrpcServiceGrpc.VendorGrpcServiceImplBase {

    private final VendorService vendorService;

    @Override
    public void getListVendor(GetListVendorRequest request, StreamObserver<GetListVendorResponse> responseObserver) {
        responseObserver.onNext(vendorService.getListVendor(request));
        responseObserver.onCompleted();
    }
}
