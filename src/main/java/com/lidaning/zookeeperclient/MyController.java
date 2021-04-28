package com.lidaning.zookeeperclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author Administrator
 * @since 2021-4-27
 */
@Slf4j
public class MyController {

    public static void main(String[] args) {
        zkWay();

        curatorWay();
    }

    public static void zkWay() {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper =
                    new ZooKeeper("192.168.178.128:2181",
                            4000, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                                //如果收到了服务端的响应事件，连接成功
                                countDownLatch.countDown();
                            }
                        }
                    });
            countDownLatch.await();
            //CONNECTED
            log.info("zookeeper status: " + zooKeeper.getState());

            zooKeeper.create("/lidaning", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void curatorWay() {
        try{
            CuratorFramework curatorFramework = CuratorFrameworkFactory.
                    builder().connectString("192.168.178.128:2181").
                    sessionTimeoutMs(4000).retryPolicy(new
                    ExponentialBackoffRetry(1000, 3)).
                    namespace("").build();
            curatorFramework.start();
            Stat stat = new Stat();
            //查询节点数据
            byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/lidaning");
            log.info("###"+new String(bytes));
            curatorFramework.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
