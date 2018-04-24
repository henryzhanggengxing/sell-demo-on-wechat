package com.oldmanw.sell.service.impl;

import com.oldmanw.sell.dataobject.SellerInfo;
import com.oldmanw.sell.repository.SellerInfoRepository;
import com.oldmanw.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
