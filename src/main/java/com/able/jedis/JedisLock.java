package com.able.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisLock implements AutoCloseable{
    private final Jedis jedis;
    private final JedisPool jedisPool;
    private final String key;
    public JedisLock(Jedis jedis,String key){
        this.jedis = jedis;
        this.jedisPool = null;
        this.key = key;
    }
    public JedisLock(JedisPool jedispool,String key){
        this.jedis = null;
        this.jedisPool = jedispool;
        this.key = key;
    }

    public void close() {
        if(jedis!=null){
            jedis.del(key);
            return;
        }
        try(Jedis jedis = jedisPool.getResource() ) {
            jedis.del(key);
        }
    }

}
