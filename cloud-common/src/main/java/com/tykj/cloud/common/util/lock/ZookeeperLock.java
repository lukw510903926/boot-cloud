package com.tykj.cloud.common.util.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper 分布式锁
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-23 21:11
 **/
public class ZookeeperLock implements Watcher {

    private ZooKeeper zooKeeper;

    /**
     * 锁名称
     */
    private String lockName;

    /**
     * 根节点
     */
    private String rootPath;

    /**
     * 当前锁
     */
    private String currentLock;

    /**
     * 前一个锁
     */
    private String preLock;

    private CountDownLatch countDownLatch;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ZookeeperLock(String serverUrl, String rootPath, String lockName) {

        try {
            this.rootPath = rootPath;
            this.lockName = lockName;
            zooKeeper = new ZooKeeper(serverUrl, 5000, this);
            Stat stat = zooKeeper.exists(rootPath, false);
            if (stat == null) {
                zooKeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("zookeeper lock create error : {}", e);
        }
    }

    /**
     * 锁定
     */
    public void lock() {

        try {
            currentLock = zooKeeper.create(rootPath + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zooKeeper.getChildren(rootPath, false);
            Collections.sort(children);
            preLock = children.get(0);
            String proLockName = preLock.substring(preLock.indexOf("root/") + 1);
            if (currentLock.equalsIgnoreCase(proLockName)) {
                return;
            }
            int index = Collections.binarySearch(children, currentLock);
            index = index == 0 ? 1 : index;
            preLock = children.get(index);
            if (preLock != null) {
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await(4000, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            logger.error("get lock fail : {}", e);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (countDownLatch != null) {
            countDownLatch.countDown();
            this.preLock = null;
            this.currentLock = null;
        }
    }
}
