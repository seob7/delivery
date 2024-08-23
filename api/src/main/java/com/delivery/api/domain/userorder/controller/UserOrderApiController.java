package com.delivery.api.domain.userorder.controller;

import com.delivery.common.annotation.UserSession;
import com.delivery.common.api.Api;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.business.UserOrderBusiness;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문
    @PostMapping("")
    public Api<UserOrderResponse> userOrder(
        @Valid @RequestBody Api<UserOrderRequest> userOrderRequest,
        @Parameter(hidden = true) @UserSession User user) {
        UserOrderResponse response = userOrderBusiness.userOrder(user, userOrderRequest.getBody());
        return Api.OK(response);
    }

    // 현재 진행중인 주문건
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(
        @Parameter(hidden = true) @UserSession User user) {
        List<UserOrderDetailResponse> response = userOrderBusiness.current(user);
        return Api.OK(response);
    }

    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(
        @Parameter(hidden = true) @UserSession User user) {
        List<UserOrderDetailResponse> response = userOrderBusiness.history(user);
        return Api.OK(response);
    }

    // 주문 1건에 대한 내역
    @GetMapping("/id/{orderId}")
    public Api<UserOrderDetailResponse> read(
        @Parameter(hidden = true) @UserSession User user, @PathVariable Long orderId) {
        UserOrderDetailResponse response = userOrderBusiness.read(user, orderId);
        return Api.OK(response);
    }

}
