package com.lidaning.zookeeperclient;

import java.util.List;

/**
 * @author Administrator
 * @since 2021-4-28
 */
public abstract class LoadBalanse {
    public volatile static List<String> SERVICE_LIST;
    public abstract String choseServiceHost();
}


