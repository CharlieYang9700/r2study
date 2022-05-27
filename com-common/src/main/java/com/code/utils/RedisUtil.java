package com.code.utils;



import com.code.biz.BizException;
import com.code.core.base.ResultDecoder;
import com.code.core.canstants.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author king
 * @date 2021-03-19
 * @description redis工具类
 */
@Component
@Slf4j
public class RedisUtil {

    /**
     * 默认过期时间
     */
    private static final int LOCK_WAIT_TIME = 3;
    /**
     * 默认锁的过期时间为60秒
     */
    private static final int LOCK_EXPIRE_TIME = 60;

    private StringRedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);

    }

    public void set(String key, String value) {
        Objects.requireNonNull(key, "key不能为空");
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, Duration expire) {
        Objects.requireNonNull(key, "key不能为空");
        redisTemplate.opsForValue().set(key, value, expire);
    }

    public void set(String key, Object value, Duration expire) {
        set(key, JsonUtil.toJsonString(value), expire);
    }

    public void set(String key, Object value) {
        set(key, JsonUtil.toJsonString(value));
    }

    public void setnx(String key, String value, Duration expire) {
        Objects.requireNonNull(key, "key不能为空");
        Objects.requireNonNull(expire, "失效时间不能为空");
        redisTemplate.opsForValue().setIfAbsent(key, value, expire);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> type) {
        String value = redisTemplate.opsForValue().get(key);
        if (type.equals(String.class)) {
            return (T) value;
        }
        if (CommonUtil.isBlank(value)) {
            return null;
        }
        return JsonUtil.parse(value, type);
    }

    public <T> List<T> list(String key, Class<T> type) {
        String value = redisTemplate.opsForValue().get(key);
        if (CommonUtil.isBlank(value)) {
            return new ArrayList<>(15);
        }
        return JsonUtil.parseAsList(value, type);
    }


    public long incr(String key, Long stepSize) {
        return redisTemplate.opsForValue().increment(key, stepSize);
    }


    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    public <T> T get(String key, Class<T> type, ResultDecoder<String, T> decoder) throws BizException {
        String value = redisTemplate.opsForValue().get(key);
        return decoder.decode(value, type);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }


    /**
     * redisson加锁
     *
     * @param key 锁
     * @throws BizException 业务异常
     */
    public RLock tryLockRds(String key) throws BizException {
        RLock rLock = redissonClient.getLock(key);
        try {
            boolean b = rLock.tryLock(LOCK_WAIT_TIME, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            if (b) {
                // rLock.lock();
                return rLock;
            }
            throw ResultCode.OPT_TOO_FAST.getBizException();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw ResultCode.OPT_TOO_FAST.getBizException();
        }
    }

    /**
     * redisson加锁-不续锁
     *
     * @param key 锁
     * @throws BizException 业务异常
     */
    public RLock tryLockRdsNotWait(String key) throws BizException {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean b = lock.tryLock(0, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            if (b) {
                return lock;
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw ResultCode.OPT_TOO_FAST.getBizException();
        }
        throw ResultCode.OPT_TOO_FAST.getBizException();
    }

    /**
     * redisson 解锁
     *
     * @param key 锁
     */
    public void unLockRds(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    /**
     * redisson 联锁-续锁
     *
     * @param keys 锁
     * @throws BizException 业务异常
     */
    public void tryLockRds(List<String> keys) throws BizException {
        if (CommonUtil.isNotEmpty(keys)) {
            RLock[] rLocks = new RLock[keys.size()];
            for (int i = 0; i < keys.size(); i++) {
                rLocks[i] = redissonClient.getLock(keys.get(i));
            }
            RedissonMultiLock redissonMultiLock = new RedissonMultiLock(rLocks);
            try {
                boolean b = redissonMultiLock.tryLock(LOCK_WAIT_TIME, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
                if (b) {
                    return;
                }
                throw ResultCode.OPT_TOO_FAST.getBizException();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                throw ResultCode.OPT_TOO_FAST.getBizException();
            }
        }
    }

    /**
     * redisson 联锁-不续锁
     *
     * @param keys 锁
     * @throws BizException 业务异常
     */
    public RedissonMultiLock tryLockRdsNotWait(List<String> keys) throws BizException {
        if (CommonUtil.isNotEmpty(keys)) {
            RLock[] rLocks = new RLock[keys.size()];
            for (int i = 0; i < keys.size(); i++) {
                rLocks[i] = redissonClient.getLock(keys.get(i));
            }
            RedissonMultiLock redissonMultiLock = new RedissonMultiLock(rLocks);
            try {
                boolean b = redissonMultiLock.tryLock(0, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
                if (b) {
                    return redissonMultiLock;
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                throw ResultCode.OPT_TOO_FAST.getBizException();
            }
        }
        throw ResultCode.OPT_TOO_FAST.getBizException();
    }


    /**
     * redisson 联锁解锁
     *
     * @param keys 锁
     * @throws BizException 业务异常
     */
    public void unLockRds(List<String> keys) throws BizException {
        if (CommonUtil.isNotEmpty(keys)) {
            RLock[] rLocks = new RLock[keys.size()];
            for (int i = 0; i < keys.size(); i++) {
                rLocks[i] = redissonClient.getLock(keys.get(i));
            }
            RedissonMultiLock redissonMultiLock = new RedissonMultiLock(rLocks);
            redissonMultiLock.unlock();
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String getStr(String key) {
        if (key == null) {
            return null;
        }
        Object val = redisTemplate.opsForValue().get(key);
        return Objects.isNull(val) ? null : val.toString();
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    public <T> void hset(String key, String item, T value) {
        redisTemplate.opsForHash().put(key, item, JsonUtil.toJsonString(value));
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}


