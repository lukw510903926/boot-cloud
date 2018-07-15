package com.tykj.cloud.common.reids;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author : lukew
 * @project : IDEA
 * @created : 2018/4/21 16:59
 * @email : 13507615840@163.com
 * @gitHub : https://github.com/lukw510903926
 * @description :
 */
public class RedisLock {

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    private final JedisPool jedisPool;

    public RedisLock(JedisPool jedisPool) {

        this.jedisPool = jedisPool;
    }

    /**
     * 加锁
     *
     * @param locKName       锁的key
     * @param acquireTimeout 获取超时时间
     * @param timeout        锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locKName, long acquireTimeout, long timeout) {

        Jedis jedis = getJedis();
        try {
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locKName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) (timeout / 1000);
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (jedis.setnx(lockKey, identifier) == 1) {
                    jedis.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    return identifier;
                }
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (jedis.ttl(lockKey) == -1) {
                    jedis.expire(lockKey, lockExpire);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockName   锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {

        Jedis jedis = getJedis();
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            while (true) {
                // 监视lock，准备开始事务
                jedis.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(jedis.get(lockKey))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                jedis.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            this.close(jedis);
        }
        return retFlag;
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param identifier  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     * @link https://www.cnblogs.com/linjiqin/p/8003838.html
     */
    public boolean tryGetDistributedLock(String lockKey, String identifier, int expireTime) {

        Jedis jedis = getJedis();
        String result = getJedis().set(lockKey, identifier, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        this.close(jedis);
        return LOCK_SUCCESS.equals(result);

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param identifier 请求标识
     * @return 是否释放成功
     * @link https://www.cnblogs.com/linjiqin/p/8003838.html
     */
    public boolean releaseDistributedLock(String lockKey, String identifier) {

        //Lua脚本 获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis jedis = this.getJedis();
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(identifier));
        this.close(jedis);
        return RELEASE_SUCCESS.equals(result);
    }

    private Jedis getJedis(){

        return jedisPool.getResource();
    }

    private void close(Jedis jedis){

        if(jedis != null){
            jedis.close();
        }
    }
}
