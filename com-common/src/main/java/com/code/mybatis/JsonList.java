package com.code.mybatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据库中json数组类型的包装
 *
 *
 * @date 2021-07-08
 */
public class JsonList<T> extends ArrayList<T> implements List<T> {
    private static final long serialVersionUID = -4690647310063710861L;

    public JsonList(Collection<T> list) {
        super(list);
    }

    public JsonList(T... ts) {
        super(ts.length);
        for (int i = 0, s = ts.length; i < s; i++) {
            add(ts[i]);
        }
    }

    public JsonList(int initialCapacity) {
        super(initialCapacity);
    }

    public JsonList() {}

    public List<T> toList() {
        List<T> list = new ArrayList<>(size());
        for (int i = 0, s = size(); i < s; i++) {
            list.add(get(i));
        }
        return list;
    }
}
