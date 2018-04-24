package com.oldmanw.sell.enums;

import lombok.Getter;

/**
 * 异常状态枚举类
 */

@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_PAY_STATUS_ERROR(16, "订单支付状态不正确"),
    CART_EMPTY(17, "购物车为空"),
    ORDER_OWNER_ERROR(18, "该订单不属于当前用户"),
    WECHAT_MP_ERROR(19, "微信公众号错误"),
    WECHAT_PAY_MONEY_VERIFY_ERROR(20, "微信支付异步通知金额校验不通过"),
    ORDER_CANCEL_SUCCESS(21, "订单取消成功"),
    ORDER_FINISH_SUCCESS(22, "订单完结成功"),
    PRODUCT_STATUS_ERROR(23, "商品状态不正确"),
    ON_SALE_PRODUCT_SUCCESS(24, "商品上架成功"),
    OFF_SALE_PRODUCT_SUCCESS(25, "商品下架成功"),
    LOGIN_SUCCESS(26, "登陆成功"),
    LOGIN_FAIL(27, "登陆失败，登陆信息不正确"),
    LOGOUT_SUCCESS(28, "登出成功"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
