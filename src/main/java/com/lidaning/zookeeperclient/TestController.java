package com.lidaning.zookeeperclient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Administrator
 * @since 2021-4-28
 */
@RestController
@RequestMapping("/order")
public class TestController {

    private RestTemplate restTemplate = new RestTemplate();

    private LoadBalanse loadBalanse = new RandomLoadBalance();

    @RequestMapping("/getOrder")
    public Object getProduct(@RequestBody Map entity){
        String host = loadBalanse.choseServiceHost();
        Map res = restTemplate.postForObject("http://"+host+"/product/getProduct",entity,Map.class);
        res.put("host",host);
        return res;
    }

    @RequestMapping("/test")
    public Map test(){
        return null;
    }
}