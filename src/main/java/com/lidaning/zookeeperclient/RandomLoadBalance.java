package com.lidaning.zookeeperclient;

import org.springframework.util.CollectionUtils;

import java.util.Random;

/**
 * @author Administrator
 * @since 2021-4-28
 */
public class RandomLoadBalance extends LoadBalanse {

    @Override
    public String choseServiceHost() {
        String result = "";

        if(!CollectionUtils.isEmpty(SERVICE_LIST)){
            int index = new Random().nextInt(SERVICE_LIST.size());
            result = SERVICE_LIST.get(index);
        }
        return result;
    }
}