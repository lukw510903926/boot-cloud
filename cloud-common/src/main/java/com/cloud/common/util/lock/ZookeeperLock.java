package com.cloud.common.util.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * zookeeper 分布式锁
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-23 21:11
 **/
public class ZookeeperLock implements Lock, Watcher {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperLock.class);

    private ZooKeeper zooKeeper;

    /**
     * 根节点
     */
    private String rootPath = "/locks";

    /**
     * 锁名称
     */
    private String lockName;

    /**
     * 等待前一个锁
     */
    private String waitNode;

    /**
     * 当前锁
     */
    private String currentNode;

    /**
     * 计数器
     */
    private CountDownLatch latch;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private int sessionTimeout = 30000;

    private String separator = File.separator;

    /**
     * 创建分布式锁,使用前请确认serverUrl配置的zookeeper服务可用
     *
     * @param serverUrl      192.168.1.127:2181,192.168.1.128:2181
     * @param rootPath       根节点
     * @param lockName       lockName 竞争资源标志,lockName中不能包含单词_lock_
     * @param sessionTimeout session过期时间
     */
    public ZookeeperLock(String serverUrl, String rootPath, String lockName, int sessionTimeout) {

        this.lockName = lockName;
        this.rootPath = rootPath;
        this.sessionTimeout = sessionTimeout;
        try {
            zooKeeper = new ZooKeeper(serverUrl, sessionTimeout, this);
            countDownLatch.await();
            Stat stat = zooKeeper.exists(this.rootPath, false);
            if (stat == null) {
                zooKeeper.create(this.rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (InterruptedException | KeeperException | IOException e) {
            throw new LockException(e);
        }
    }

    /**
     * zookeeper节点的监视器
     */
    @Override
    public void process(WatchedEvent event) {

        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            return;
        }
        if (this.latch != null) {
            this.latch.countDown();
        }
    }

    @Override
    public void lock() {
        try {
            if (this.tryLock()) {
                logger.info("Thread " + Thread.currentThread().getId() + " " + currentNode + " get lock true");
            } else {
                waitForLock(waitNode, sessionTimeout);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new LockException(e);
        }
    }

    @Override
    public boolean tryLock() {
        try {
            String split = "_lock_";
            if (lockName.contains(split)) {
                throw new LockException("lockName can not contains " + split);
            }
            //创建临时子节点
            currentNode = zooKeeper.create(rootPath + separator + lockName + split, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info(currentNode + " is created ");
            //取出所有子节点
            List<String> childrenNodes = zooKeeper.getChildren(rootPath, false);
            //取出所有lockName的锁
            List<String> lockObjNodes = new ArrayList<String>();
            for (String node : childrenNodes) {
                String _node = node.split(split)[0];
                if (_node.equals(lockName)) {
                    lockObjNodes.add(node);
                }
            }
            Collections.sort(lockObjNodes);
            if (currentNode.equals(rootPath + separator + lockObjNodes.get(0))) {
                //如果是最小的节点,则表示取得锁
                logger.info(currentNode + "==" + lockObjNodes.get(0));
                return true;
            }
            //如果不是最小的节点，找到比自己小1的节点
            String tempNode = currentNode.substring(currentNode.lastIndexOf(separator) + 1);
            //找到前一个子节点
            waitNode = lockObjNodes.get(Collections.binarySearch(lockObjNodes, tempNode) - 1);
        } catch (KeeperException | InterruptedException e) {
            throw new LockException(e);
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        try {
            if (this.tryLock()) {
                return true;
            }
            return waitForLock(waitNode, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 等待锁
     *
     * @param lock
     * @param waitTime
     * @return
     * @throws InterruptedException
     * @throws KeeperException
     */
    private boolean waitForLock(String lock, long waitTime) throws InterruptedException, KeeperException {

        //同时注册监听。
        Stat stat = zooKeeper.exists(rootPath + separator + lock, true);
        //判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
        if (stat != null) {
            logger.info("Thread " + Thread.currentThread().getId() + " waiting for " + rootPath + separator + lock);
            this.latch = new CountDownLatch(1);
            //等待，这里应该一直等待其他线程释放锁
            this.latch.await(waitTime, TimeUnit.MILLISECONDS);
            this.latch = null;
        }
        return true;
    }

    @Override
    public void unlock() {
        try {
            logger.info("unlock " + currentNode);
            zooKeeper.delete(currentNode, -1);
            currentNode = null;
            zooKeeper.close();
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() {
        this.lock();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
