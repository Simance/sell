package com.doo.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @RequestMapping("/auth")
    public void auth(@RequestParam("code") String code){
        String url ="";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response = {}", response);
    }
}
