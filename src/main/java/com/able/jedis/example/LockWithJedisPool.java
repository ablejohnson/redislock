package com.able.jedis.example;

import com.able.jedis.JedisLock;
import com.able.jedis.LockManager;
import com.able.jedis.exceptions.LockException;
import redis.clients.jedis.JedisPool;

public class LockWithJedisPool {
    public static void lockJedisWithPool(JedisPool jedisPool) {
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedisPool, lockKey)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }

    public static void lockJedisWithPoolWithSomeExpiry(JedisPool jedisPool) {
        Integer secondsToExpire = 20;
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedisPool, lockKey,secondsToExpire)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
}
