package cn.clouddisk.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

public class CustomCache<K, V> implements Cache<K, V> {
    @Override
    public V get(K k) throws CacheException {
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
