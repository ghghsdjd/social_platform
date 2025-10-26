package com.zyh.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {
    // パラメータ1：認証または認可の格納用の共通名
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        //System.out.println(s);
        return new RedisCache<K,V>(s);
    }
}
