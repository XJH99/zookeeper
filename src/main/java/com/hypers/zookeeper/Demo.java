package com.hypers.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Demo {

    private static String connectString = "192.168.214.103:2181,192.168.214.104:2181,192.168.214.105:2181";     // 连接的zookeeper服务器地址
    private static int sessionTimeout = 2000;       // 超时时间
    private ZooKeeper zkClient = null;


    /**
     * 创建zookeeper客户端
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // 收到事件通知后的回调函数
//                System.out.printf(watchedEvent.getType() + "--" + watchedEvent.getPath());
//
//                try {
//                    zkClient.getChildren("/", true);
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    /**
     * 创建子节点
     */
    @Test
    public void create() throws KeeperException, InterruptedException {
        String nodeCreate = zkClient.create("/sanguo/hypers", "what".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    /**
     * 获取子节点并监听节点变化
     */
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/sanguo", true);

        for (String child : children) {
            System.out.printf(child + " ");
        }

        //Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断znode节点是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/sanguo", false);
        System.out.printf(stat == null ? "not exist" : "exist");
    }
}
