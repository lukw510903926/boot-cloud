package com.tykj.cloud.common.util.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    private CountDownLatch latch = new CountDownLatch(1);

    private Integer sessionTimeout;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param serverUrl      zookeeper 服务器地址
     * @param rootPath       根节点
     * @param lockName       锁名称
     * @param sessionTimeout session过期时间
     */
    public ZookeeperLock(String serverUrl, String rootPath, String lockName, int sessionTimeout) {

        try {
            this.rootPath = rootPath;
            this.lockName = lockName;
            this.sessionTimeout = sessionTimeout;
            zooKeeper = new ZooKeeper(serverUrl, sessionTimeout, this);
            latch.await();
            Stat stat = zooKeeper.exists(rootPath, false);
            if (stat == null) {
                zooKeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (InterruptedException e) {
            throwException(e);
        } catch (KeeperException e) {
            throwException(e);
        } catch (IOException e) {
            throwException(e);
        }
    }

    public void lock() {

        if (tryLock()) {
            return;
        } else {
            waitForLock(preLock, sessionTimeout);
        }
    }

    /**
     * 锁定
     */
    public boolean tryLock() {

        try {
            currentLock = zooKeeper.create(rootPath + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zooKeeper.getChildren(rootPath, false);
            Collections.sort(children);
            preLock = children.get(0);
            String proLockName = rootPath + "/" + preLock;
            if (currentLock.equalsIgnoreCase(proLockName)) {
                return true;
            }
            int index = Collections.binarySearch(children, currentLock);
            index = index == 0 ? 1 : index;
            preLock = children.get(index);
        } catch (InterruptedException e) {
            throwException(e);
        } catch (KeeperException e) {
            throwException(e);
        }
        return false;
    }

    private boolean waitForLock(String lockNode, long waitTime) {

        try {
            Stat stat = zooKeeper.exists(lockNode, true);
            if (stat != null) {
                this.countDownLatch = new CountDownLatch(1);
                countDownLatch.await(waitTime, TimeUnit.MILLISECONDS);
                this.countDownLatch = null;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        // 建立连接用
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            this.latch.countDown();
            return;
        }
        if (countDownLatch != null) {
            countDownLatch.countDown();
            this.preLock = null;
            this.currentLock = null;
        }
    }

    public void releaseLock(){

        try {
            Stat stat = zooKeeper.exists(currentLock, false);
            this.zooKeeper.delete(currentLock,stat.getVersion());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private void throwException(Exception exception) {
        throw new LockException(exception);
    }
}
