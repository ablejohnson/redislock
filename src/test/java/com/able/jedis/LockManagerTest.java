package com.able.jedis;

import com.able.jedis.exceptions.LockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LockManagerTest {
    @Mock
    private JedisPool jedisPool;
    @Mock
    private Jedis jedis;

    @Before
    public void setUp() {
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.setnx(anyString(), anyString())).thenReturn(1L);
        when(jedis.expire(anyString(), anyInt())).thenReturn(1L);
    }

    @Test
    public void LockTest() {
        String key = "testkey";
        try (JedisLock ignored = LockManager.lock(jedisPool, key)) {

        } catch (LockException e) {
            e.printStackTrace();
        }
        verify(jedis).setnx(key, "");
    }

    @Test(expected = LockException.class)
    public void LockTestNoLock() throws LockException {
        when(jedis.setnx(anyString(), anyString())).thenReturn(0L);
        String key = "testkey";
        try (JedisLock ignored = LockManager.lock(jedisPool, key)) {
            fail();
        }
    }

    @Test
    public void LockTestJedis() {
        String key = "testkey";
        try (JedisLock ignored = LockManager.lock(jedis, key)) {

        } catch (LockException e) {
            e.printStackTrace();
        }
        verify(jedis).setnx(key, "");
    }

}