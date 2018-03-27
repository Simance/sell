package com.doo.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "project")
public class ProjiectUrlConfig {
    private String WechatMpAuthorize;

}
