package com.code.core.base;


import com.code.biz.BizException;

/**
 * @author king
 * @date 2021-03-21
 * @description 描述
 */
public interface ResultDecoder<V, T> {
    /**
     * 结果转换
     *
     * @param value 需要解析的值
     * @param type 转换类型
     * @return 转换结果
     * @throws BizException 业务异常
     */
    T decode(V value, Class<T> type) throws BizException;
}
