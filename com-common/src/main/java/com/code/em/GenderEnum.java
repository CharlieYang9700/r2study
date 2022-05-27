package com.code.em;

import com.code.core.base.AbstractEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ping
 */

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GenderEnum implements AbstractEnum<GenderEnum> {
    NONE(0,"未知"),
    MALE(1,"男"),
    FEMALE(2,"女");
    /**
     * 值
     */
    private final Integer value;
    /**
     * 描述
     */
    private final String desc;

    public static GenderEnum valueOfCode(Integer sex) {
        if (Objects.equals(1, sex)) {
            return MALE;
        }
        if (Objects.equals(2, sex)) {
            return FEMALE;
        }
        return NONE;
    }

}
