package com.delivery.api.domain.userorder.business;

import com.delivery.common.annotation.Business;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.producer.UserOrderProducer;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userorderemenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userorderemenu.service.UserOrderMenuService;
import com.delivery.db.store.StoreEntity;
import com.delivery.db.storemenu.StoreMenuEntity;
import com.delivery.db.userorder.UserOrderEntity;
import com.delivery.db.userordermenu.UserOrderMenuEntity;
import com.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

    private final StoreMenuService storeMenuService;
    private final UserOrderService userOrderService;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final StoreMenuConverter storeMenuConverter;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;


    public UserOrderResponse userOrder(User user, UserOrderRequest request) {

        var storeEntity = storeService.getStoreWithThrow(request.getStoreId());

        List<StoreMenuEntity> storeMenuEntityList = request.getStoreMenuIdList()
            .stream().map(menuId -> storeMenuService.getStoreMenuWithThrow(menuId)).toList();

        UserOrderEntity userOrderEntity = userOrderConverter.toEntity(user, storeEntity,
            storeMenuEntityList);

        // 주문
        UserOrderEntity newUserOrderEntity = userOrderService.order(userOrderEntity);

        List<UserOrderMenuEntity> userOrderMenuEntityList = storeMenuEntityList.stream()
            .map(entity -> userOrderMenuConverter.toEntity(newUserOrderEntity, entity)).toList();

        userOrderMenuEntityList.forEach(entity -> userOrderMenuService.order(entity));

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        return userOrderConverter.toResponse(newUserOrderEntity);

    }

    public List<UserOrderDetailResponse> current(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.current(user.getId());

        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream()
            .map(userOrderEntity -> {
                // 사용자가 주문한 메뉴
//                List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(
//                    entity.getId());

                var userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED)).toList();

                List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity -> userOrderMenuEntity.getStoreMenu()).toList();

                // 사용자가 주문한 스토어 TODO 리팩터링
                StoreEntity storeEntity = userOrderEntity.getStore();
//                StoreEntity storeEntity = storeService.getStoreWithThrow(
//                    storeMenuEntityList.stream().findFirst().get().getStore().getId());

                return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();

            }).toList();

        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {

        List<UserOrderEntity> userOrderEntityList = userOrderService.history(user.getId());

        List<UserOrderDetailResponse> userOrderDetailResponseList = userOrderEntityList.stream()
            .map(userOrderEntity -> {
                // 사용자가 주문한 메뉴
                List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
                    .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED)).toList();
//                List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(
//                    userOrderEntity.getId());

                List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
                    .map(userOrderMenuEntity -> userOrderMenuEntity.getStoreMenu()).toList();

                // 사용자가 주문한 스토어 TODO 리팩터링
                StoreEntity storeEntity = userOrderEntity.getStore();
//                StoreEntity storeEntity = storeService.getStoreWithThrow(
//                    storeMenuEntityList.stream().findFirst().get().getStore().getId());

                return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                    .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .build();

            }).toList();

        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        UserOrderEntity userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(
            orderId, user.getId());

        List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderEntity.getUserOrderMenuList().stream()
            .filter(it -> it.getStatus().equals(UserOrderMenuStatus.REGISTERED)).toList();
//        List<UserOrderMenuEntity> userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(
//            userOrderEntity.getId());


        List<StoreMenuEntity> storeMenuEntityList = userOrderMenuEntityList.stream()
            .map(userOrderMenuEntity -> userOrderMenuEntity.getStoreMenu()  ).toList();

        // 사용자가 주문한 스토어 TODO 리팩터링
        StoreEntity storeEntity = userOrderEntity.getStore();
//        StoreEntity storeEntity = storeService.getStoreWithThrow(
//            storeMenuEntityList.stream().findFirst().get().getStore().getId());

        return UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
            .storeResponse(storeConverter.toResponse(storeEntity))
            .build();
    }
}