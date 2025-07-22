package com.ags.spring_ecommerce_bff.interceptor;

import com.ags.spring_ecommerce_bff.service.TokenService;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

@GrpcGlobalClientInterceptor
@RequiredArgsConstructor
public class GrpcAuthInterceptor implements ClientInterceptor {
  private final TokenService tokenService;

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
    return new ForwardingClientCall.SimpleForwardingClientCall<>(
        channel.newCall(methodDescriptor, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        headers.put(AUTHORIZATION_KEY, tokenService.getCurrentToken());
        super.start(responseListener, headers);
      }
    };
  }
}
