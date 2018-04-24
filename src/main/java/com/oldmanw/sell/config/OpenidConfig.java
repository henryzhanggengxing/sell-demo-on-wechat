package com.oldmanw.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "openid")
@Component
public class OpenidConfig {

    private String mpOpenid;

}
