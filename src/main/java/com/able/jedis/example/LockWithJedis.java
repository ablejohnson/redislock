package com.able.jedis.example;

import com.able.jedis.JedisLock;
import com.able.jedis.LockManager;
import com.able.jedis.exceptions.LockException;
import redis.clients.jedis.Jedis;

public class LockWithJedis {
    public static void lockJedisWith(Jedis jedis) {
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedis, lockKey)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }

    public static void lockJedisWithPoolWithSomeExpiry(Jedis jedis) {
        Integer secondsToExpire = 20;
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedis, lockKey,secondsToExpire)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
}
