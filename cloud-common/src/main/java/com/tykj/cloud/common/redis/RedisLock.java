package com.tykj.cloud.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
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

    private String prefix = "lock:";

    private final Jedis jedis;

    public RedisLock(Jedis jedis) {

        this.jedis = jedis;
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

        try {
            // 随机生成一个value
            String identify = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = prefix + locKName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) (timeout / 1000);
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (jedis.setnx(lockKey, identify) == 1) {
                    jedis.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    return identify;
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
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param identify   请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     * @link https://www.cnblogs.com/linjiqin/p/8003838.html
     */
    public boolean tryLock(String lockKey, String identify, int expireTime) {

        lockKey = prefix + lockKey;
        String result = jedis.set(lockKey, identify, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        this.close(jedis);
        return LOCK_SUCCESS.equals(result);

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey  锁
     * @param identify 请求标识
     * @return 是否释放成功
     * @link https://www.cnblogs.com/linjiqin/p/8003838.html
     */
    public boolean releaseLock(String lockKey, String identify) {

        //Lua脚本 获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）
        lockKey = prefix + lockKey;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(identify));
        this.close(jedis);
        return RELEASE_SUCCESS.equals(result);
    }

    private void close(Jedis jedis) {

        if (jedis != null) {
            jedis.close();
        }
    }
}
