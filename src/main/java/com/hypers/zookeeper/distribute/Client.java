package com.hypers.zookeeper.distribute;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private String connectString = "192.168.214.103:2181,192.168.214.104:2181,192.168.214.105:2181";
    private int setTimeout = 2000;
    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        Client client = new Client();

        // 1.获取zookeeper连接
        client.getConnect();

        // 2.注册监听
        client.getServerList();

        // 3.业务逻辑处理
        client.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void getServerList() throws KeeperException, InterruptedException {
        // 获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zkClient.getChildren("/servers", true);

        // 存储服务器节点主机名称集合
        List<String> hosts = new ArrayList<String>();

        for (String child: children) {
            byte[] data = zkClient.getData("/servers/" + child, false, null);

            hosts.add(new String(data));
        }

        // 将所有在线主机名称打印到控制台
        System.out.println(hosts);
    }



    private void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, setTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
