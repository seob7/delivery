package com.delivery.storeadmin.domain.userorder.business;

import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.storeadmin.common.annotation.Business;
import com.delivery.storeadmin.domain.sse.connection.model.SseConnectionPool;
import com.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import com.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import com.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.storeadmin.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import com.delivery.storeadmin.domain.userorder.service.UserOrderService;
import com.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;

    /**
     * 주문 주문 내역 찾기 스토어 찾기 연결된 세션 찾아서 push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        UserOrderEntity userOrderEntity = userOrderService.getUserOrder(
                userOrderMessage.getUserOrderId())
            .orElseThrow(() -> new RuntimeException("사용자 주문내역 없음"));

        // user order menu
        List<UserOrderMenuEntity> userOrderMenuList = userOrderMenuService.getUserOrderMenuList(
            userOrderEntity.getId());

        // user order menu -> store menu
        List<StoreMenuResponse> storeMenuResponseList = userOrderMenuList.stream()
            .map(userOrderMenuEntity ->
                storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()))
            .map(storeMenuEntity ->
                storeMenuConverter.toResponse(storeMenuEntity))
            .toList();

        UserOrderResponse userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response
        UserOrderDetailResponse push = UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderResponse)
            .storeMenuResponseList(storeMenuResponseList)
            .build();

        UserSseConnection userConnection = sseConnectionPool.getSession(
            userOrderEntity.getStoreId().toString());

        // 주문 메뉴, 가격, 상태

        // 사용자에게 push
        userConnection.sendMessage(push );
    }

}
