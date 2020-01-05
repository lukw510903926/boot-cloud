package com.cloud.common.redis;

import com.cloud.common.util.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lukew
 * @Description:
 * @email 13507615840@163.com
 * @date 2018年3月23日 下午9:36:27
 */
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<Object, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    @Override
    public boolean expire(String key, long time) {
        if (time > 0) {
            return Optional.ofNullable(redisTemplate.expire(key, time, TimeUnit.SECONDS)).orElse(false);
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    @Override
    public long getExpire(@NonNull String key) {
        return Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    @Override
    public boolean hasKey(@NonNull String key) {
        return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    @Override
    public void del(@NonNull List<String> keys) {

        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    @Override
    public void del(@NonNull String key) {

        if (StringUtils.isNotBlank(key)) {
            redisTemplate.delete(key);
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(@NonNull String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    @Override
    public boolean set(@NonNull String key, @NonNull Object value) {

        return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    @Override
    public boolean set(@NonNull String key, @NonNull Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            return set(key, value);
        }
        return true;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    @Override
    public long incr(@NonNull String key, long delta) {
        if (delta < 0) {
            throw new ServiceException("递增因子必须大于0");
        }
        return Optional.ofNullable(redisTemplate.opsForValue().increment(key, delta)).orElse(0L);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    @Override
    public long decr(@NonNull String key, long delta) {
        if (delta < 0) {
            throw new ServiceException("递减因子必须大于0");
        }
        return Optional.ofNullable(redisTemplate.opsForValue().increment(key, -delta)).orElse(0L);
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Object hget(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    @Override
    public Map<Object, Object> hmget(@NonNull String key) {

        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    @Override
    public void hmset(@NonNull String key, @NonNull Map<String, Object> map) {

        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    @Override
    public void hmset(@NonNull String key, @NonNull Map<String, Object> map, long time) {

        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(@NonNull String key, @NonNull String item, @NonNull Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @Override
    public void hdel(@NonNull String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    @Override
    public boolean hHasKey(@NonNull String key, @NonNull String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    @Override
    public double hincr(@NonNull String key, @NonNull String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    @Override
    public double hdecr(@NonNull String key, @NonNull String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    @Override
    public Set<Object> sGet(@NonNull String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    @Override
    public boolean sHasKey(@NonNull String key, @NonNull Object value) {
        return Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value)).orElse(false);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public long sSet(@NonNull String key, @NonNull Object... values) {
        return Optional.ofNullable(redisTemplate.opsForSet().add(key, values)).orElse(0L);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        if (time > 0) {
            expire(key, time);
        }
        return Optional.ofNullable(redisTemplate.opsForSet().add(key, values)).orElse(0L);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    @Override
    public long sGetSetSize(@NonNull String key) {
        return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    @Override
    public long setRemove(@NonNull String key, @NonNull Object... values) {
        return Optional.ofNullable(redisTemplate.opsForSet().remove(key, values)).orElse(0L);
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    @Override
    public List<Object> lGet(@NonNull String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    @Override
    public long lGetListSize(@NonNull String key) {
        return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    @Override
    public Object lGetIndex(@NonNull String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public long lSet(@NonNull String key, Object value) {

        return Optional.ofNullable(redisTemplate.opsForList().rightPush(key, value)).orElse(0L);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
    public long lSet(@NonNull String key, Object value, long time) {

        if (time > 0) {
            expire(key, time);
        }
        return Optional.ofNullable(redisTemplate.opsForList().rightPush(key, value)).orElse(0L);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    @Override
    public long lSet(@NonNull String key, @NonNull List<Object> value) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPushAll(key, value)).orElse(0L);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    @Override
    public boolean lSet(@NonNull String key, List<Object> value, long time) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    @Override
    public void lUpdateIndex(@NonNull String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    @Override
    public long lRemove(@NonNull String key, long count, Object value) {
        return Optional.ofNullable(redisTemplate.opsForList().remove(key, count, value)).orElse(0L);
    }
}
