package com.cloud.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lukew
 */
public interface RedisService {

    /**
     * 设置过期时间
     *
     * @param key
     * @param time
     * @return
     */
    boolean expire(String key, long time);

    /**
     * 获取过期时间
     *
     * @param key
     * @return
     */
    long getExpire(String key);

    /**
     * key 是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 删除keys
     *
     * @param keys
     */
    void del(List<String> keys);

    /**
     * 删除key
     *
     * @param key
     */
    void del(String key);

    /**
     * 获取
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 设定值
     *
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, Object value);

    /**
     * 设定值及过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean set(String key, Object value, long time);


    /**
     * 对存储在指定key的数值执行原子的加1操作
     *
     * @param key
     * @param delta
     * @return
     */
    long incr(String key, long delta);

    /**
     * 对存储在指定key的数值执行原子的减1操作
     *
     * @param key
     * @param delta
     * @return
     */
    long decr(String key, long delta);

    /**
     * 获取hash值
     *
     * @param key
     * @param item
     * @return
     */
    Object hget(String key, String item);

    /**
     * 获取全部has值
     *
     * @param key
     * @return
     */
    Map<Object, Object> hmget(String key);

    /**
     * 设定hash 值
     *
     * @param key
     * @param map
     * @return
     */
    void hmset(String key, Map<String, Object> map);

    /**
     * 设定hash值及过期时间
     *
     * @param key
     * @param map
     * @param time
     * @return
     */
    void hmset(String key, Map<String, Object> map, long time);

    /**
     * 设定hash
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    boolean hset(String key, String item, Object value);

    /**
     * 设定hash 及过期时间
     *
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    boolean hset(String key, String item, Object value, long time);

    /**
     * 删除hash值
     *
     * @param key
     * @param item
     */
    void hdel(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key
     * @param item
     * @return
     */
    boolean hHasKey(String key, String item);

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    double hincr(String key, String item, double by);

    /**
     * hash递减
     *
     * @param key
     * @param item
     * @param by
     * @return
     */
    double hdecr(String key, String item, double by);

    /**
     * 根据key获取Set中的所有值
     *
     * @param key
     * @return
     */
    Set<Object> sGet(String key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key
     * @param value
     * @return
     */
    boolean sHasKey(String key, Object value);

    /**
     * 将数据放入set缓存
     *
     * @param key
     * @param values
     * @return
     */
    long sSet(String key, Object... values);

    /**
     * 将set数据放入缓存
     *
     * @param key
     * @param time
     * @param values
     * @return
     */
    long sSetAndTime(String key, long time, Object... values);

    /**
     * 获取set缓存的长度
     *
     * @param key
     * @return
     */
    long sGetSetSize(String key);

    /**
     * 移除值为value的
     *
     * @param key
     * @param values
     * @return
     */
    long setRemove(String key, Object... values);

    /**
     * 获取list缓存的内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lGet(String key, long start, long end);

    /**
     * 获取list缓存的长度
     *
     * @param key
     * @return
     */
    long lGetListSize(String key);

    /**
     * 通过索引 获取list中的值
     *
     * @param key
     * @param index
     * @return
     */
    Object lGetIndex(String key, long index);

    /**
     * 将list放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    long lSet(String key, Object value);

    /**
     * 将list放入缓存
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    long lSet(String key, Object value, long time);

    /**
     * 将list放入缓存
     *
     * @param key
     * @param value
     * @return
     */
    long lSet(String key, List<Object> value);

    /**
     * 将list放入缓存 并设定过期时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    boolean lSet(String key, List<Object> value, long time);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    void lUpdateIndex(String key, long index, Object value);

    /**
     * 移除N个值为value
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    long lRemove(String key, long count, Object value);
}
