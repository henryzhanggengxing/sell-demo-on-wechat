package com.oldmanw.sell.service;

import com.oldmanw.sell.dataobject.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);

}
