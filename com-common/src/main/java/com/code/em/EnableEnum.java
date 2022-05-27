package com.code.em;

import com.code.core.base.AbstractEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ping
 */
@Getter
@AllArgsConstructor
public enum EnableEnum implements AbstractEnum<EnableEnum> {
    /**
     * 启用
     */
    ENABLE(1, "启用"),
    /**
     * 失效
     */
    UNABLE(0, "失效");

    /**
     * 值
     */
    private Integer value;
    /**
     * 描述
     */
    private String desc;

    /**
     * 验证是否为启用状态
     *
     * @param value 验证值
     * @return 是否为启用状态
     */
    public static Boolean enable(Integer value) {
        if (Objects.equals(ENABLE.value, value)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
