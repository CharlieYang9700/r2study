package com.code.core.common;

import com.code.biz.BizException;
import com.code.utils.CommonUtil;

public interface BaseErrorCode {
    /**
     * 获取错误编码
     *
     * @return 返回错误编码
     */
    String getCode();

    /**
     * 获取错误描述
     *
     * @return 返回错误描述
     */
    String getDesc();

    /**
     * 获取抛出的异常
     *
     * @return 返回构建的异常
     */
    default BizException getBizException() {
        return new BizException(getCode(), getDesc());
    }

    /**
     * 指定错误描述信息
     *
     * @param msg 本次异常的错误描述信息
     * @return 返回错误描述信息
     */
    default BizException getBizException(String msg) {
        if (CommonUtil.isBlank(msg)) {
            msg = getDesc();
        }
        return new BizException(getCode(), msg);
    }

    /**
     * 抛出业务异常
     *
     * @param msg 本次异常的错误描述信息
     * @throws BizException 抛出的业务异常
     */
    default void throwBizException(String msg) throws BizException {
        throw getBizException(msg);
    }

    /**
     * 抛出业务异常
     *
     * @throws BizException 抛出的业务异常
     */
    default void throwBizException() throws BizException {
        throw getBizException();
    }
}
