# redislock
Simple Lock implementation in redis .This project use jedis the java client for jedis
# Examples
Simple Usecase
```
public static void lockJedisWith(Jedis jedis) {
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedis, lockKey)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
```
Lock with a lock expiry time

```
public static void lockJedisWithPoolWithSomeExpiry(Jedis jedis) {
        Integer secondsToExpire = 20;
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedis, lockKey,secondsToExpire)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
```

Using JedisPool
```
public static void lockJedisWithPool(JedisPool jedisPool) {
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedisPool, lockKey)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
```

Lock with a lock expiry time
```
public static void lockJedisWithPoolWithSomeExpiry(JedisPool jedisPool) {
        Integer secondsToExpire = 20;
        String lockKey = "myKeyToLock";
        try (JedisLock jedisLock = LockManager.lock(jedisPool, lockKey,secondsToExpire)) {
            System.out.println("Acquired lock ");
        } catch (LockException e) {
            System.out.println("could not acquire lock! Try again later");
        }
    }
    
```
