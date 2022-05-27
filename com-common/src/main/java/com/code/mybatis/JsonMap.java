package com.code.mybatis;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应数据库中json字段的包装
 * @date 2021-07-08
 */
public class JsonMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    private static final long serialVersionUID = -4607019465986368204L;

    public JsonMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public JsonMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }


    public JsonMap(int initialCapacity) {
        super(initialCapacity);
    }

    public JsonMap() {}

    public Map<K, V> toMap() {
        Map<K, V> hashMap = new HashMap<>(size());
        for (Entry<K, V> e : entrySet()) {
            hashMap.put(e.getKey(), e.getValue());
        }
        return hashMap;
    }
}
