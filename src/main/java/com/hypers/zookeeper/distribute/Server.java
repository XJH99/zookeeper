package com.hypers.zookeeper.distribute;

import org.apache.zookeeper.*;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        Server server = new Server();

        // 1.连接zookeeper集群
        server.getConnect();

        // 2.注册节点
        server.regist(args[0]);

        // 3.业务逻辑处理
        server.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void regist(String hostname) throws KeeperException, InterruptedException {
        String path = zkClient.create("/servers/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.printf(hostname + "is online");
    }

    private  String connectString = "192.168.214.103:2181,192.168.214.104:2181,192.168.214.105:2181";     // 连接的zookeeper服务器地址
    private  int sessionTimeout = 2000;       // 超时时间
    private ZooKeeper zkClient = null;

    private void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
