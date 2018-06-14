package com.able.jedis;

import com.able.jedis.exceptions.LockException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class LockManager {
    private static Integer DEFAULT_LOCK_EXP = 50000;

    public static Integer getDefaultLockExp() {
        return DEFAULT_LOCK_EXP;
    }

    public static void setDefaultLockExp(Integer defaultLockExp) {
        DEFAULT_LOCK_EXP = defaultLockExp;
    }

    public static JedisLock lock(JedisPool jedisPool,String key,Integer secondsToExpire) throws LockException {
        try(Jedis jedis = jedisPool.getResource()){
            lock(jedis,key,secondsToExpire);
            return new JedisLock(jedisPool,key);
        }
        catch (Exception e){
            throw new LockException("Unable to acquire redis lock",e);
        }
    }

    public static JedisLock lock(JedisPool jedisPool,String key) throws LockException {
        return lock(jedisPool,key,DEFAULT_LOCK_EXP);
    }

    public static JedisLock lock(Jedis jedis,String key) throws LockException {
         lockJedis(jedis,key,DEFAULT_LOCK_EXP);
         return new JedisLock(jedis,key);
    }

    public static JedisLock lock(Jedis jedis,String key,Integer secondsToExpire) throws LockException {
        lockJedis(jedis,key,secondsToExpire);
        return new JedisLock(jedis,key);
    }

    private static void lockJedis(Jedis jedis,String key,Integer secondsToExpire) throws LockException {
        boolean locked = jedis.setnx(key,"")==1;
        if(!locked){
            throw new LockException("Unable to acquire redis lock");
        }
        if(secondsToExpire>0) {
            jedis.expire(key, secondsToExpire);
        }
    }
}
