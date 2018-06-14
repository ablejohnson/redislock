package com.able.jedis.exceptions;

import java.util.concurrent.locks.Lock;

public class LockException extends Exception {
    public LockException(String message,Throwable e){
        super(message,e);
    }
    public LockException(String message){
        super(message);
    }
}
