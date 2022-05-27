package com.code.utils;




import com.code.common.Constants;
import com.code.core.base.AbstractEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 常用工具类型
 *
 * @date 2021-03-29
 * @description 描述
 */
public class CommonUtil {
    private static final Pattern PATTERN_LINE = Pattern.compile("_([a-z])");
    private static final Pattern PATTERN_NUMERIC = Pattern.compile("[0-9]*");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIMESTAMP_DESC_STR);

    public static String blankStr() {
        return "";
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isBlank(String str) {
        return Objects.isNull(str) || Objects.equals(Constants.EMPTY_STR, str.trim());
    }

    public static boolean isNullOrZero(Long value) {
        return (value == null || Objects.equals(value, 0L));
    }

    public static boolean isNullOrZero(Integer value) {
        return (value == null || Objects.equals(value, 0));
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotBlank(String str) {
        return Objects.nonNull(str) && !Objects.equals(Constants.EMPTY_STR, str.trim());
    }

    /**
     * 数组转map
     *
     * @param list     数据源
     * @param getKey   获取key的方法
     * @param getValue 获取value的方法
     * @param <K>      key类型
     * @param <V>      value类型
     * @param <R>      数组元素类型
     * @return map集合
     */
    public static <K, V, R> Map<K, V> listConvertMap(Collection<R> list, Function<R, K> getKey,
                                                     Function<R, V> getValue) {
        if (CommonUtil.isEmpty(list)) {
            return new HashMap<>(8);
        }
        Map<K, V> map = new HashMap<>(Math.min(list.size(), 256));
        list.forEach(item -> map.put(getKey.apply(item), getValue.apply(item)));
        return map;
    }

    public static <K, V> Map<K, V> listConvertMap(Collection<V> list, Function<V, K> getKey) {
        return listConvertMap(list, getKey, Function.identity());
    }

    public static <R, E> List<R> listConvert(List<E> sourceList, Function<E, R> getValue) {
        if (isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(sourceList.size());
        for (E item : sourceList) {
            R r = getValue.apply(item);
            if (Objects.nonNull(r)) {
                rList.add(r);
            }
        }
        return rList;
    }

    public static <K, V, R> List<R> mapConvertList(Map<K, V> source, Function<Map.Entry<K, V>, R> getValue) {
        if (isEmpty(source)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(source.size());
        for (Map.Entry<K, V> e : source.entrySet()) {
            rList.add(getValue.apply(e));
        }
        return rList;
    }

    public static <K, V> Map<K, List<V>> listConvertListMap(List<V> list, Function<V, K> getKey) {
        return listConvertListMap(list, getKey, Function.identity());
    }

    public static <K, V, R> Map<K, List<R>> listConvertListMap(List<V> list, Function<V, K> getKey,
                                                               Function<V, R> getValue) {
        if (list == null || list.isEmpty()) {
            return new HashMap<>(8);
        }
        Map<K, List<R>> map = new HashMap<>(Math.min(list.size(), 256));
        K key;
        for (V v : list) {
            key = getKey.apply(v);
            map.putIfAbsent(key, new ArrayList<>());
            R r = getValue.apply(v);
            map.get(key).add(r);
        }
        return map;
    }

    public static <K, V, R> Map<K, V> listConvertMap(List<R> list, Predicate<R> predicate, Function<R, K> getKey,
                                                     Function<R, V> getValue) {
        if (CommonUtil.isEmpty(list)) {
            return new HashMap<>(8);
        }
        Map<K, V> map = new HashMap<>(Math.min(list.size(), 256));
        for (int i = 0, s = list.size(); i < s; i++) {
            if (predicate.test(list.get(i))) {
                map.put(getKey.apply(list.get(i)), getValue.apply(list.get(i)));
            }
        }
        return map;
    }

    public static <E, R> Set<E> listConvertSet(List<R> list, Predicate<R> predicate, Function<R, E> getKey) {
        if (CommonUtil.isEmpty(list)) {
            return new HashSet<>(8);
        }
        Set<E> set = new HashSet<>(list.size());
        for (int i = 0, s = list.size(); i < s; i++) {
            if (predicate.test(list.get(i))) {
                set.add(getKey.apply(list.get(i)));
            }
        }
        return set;
    }

    public static <E, R> Set<E> listConvertSet(List<R> list, Function<R, E> getKey) {
        return listConvertSet(list, o -> true, getKey);
    }

    public static <R, E> List<R> listConvert(List<E> sourceList, Predicate<E> predicate, Function<E, R> getValue) {
        if (isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<R> rList = new ArrayList<>(sourceList.size());
        for (E item : sourceList) {
            if (predicate.test(item)) {
                rList.add(getValue.apply(item));
            }
        }
        return rList;
    }

    public static String blankElse(String value, String elValue) {
        return CommonUtil.isBlank(value) ? elValue : value;
    }

    public static Object nullElse(Object value, Object elValue) {
        return value == null ? elValue : value;
    }


    public static boolean isNumeric(String str) {
        if (CommonUtil.isBlank(str)) {
            return Boolean.FALSE;
        }
        return PATTERN_NUMERIC.matcher(str).matches();
    }


    public static boolean isEmpty(String value) {
        return isBlank(value);
    }

    public static boolean isEmpty(Integer value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(Long value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(LocalDateTime value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(LocalDate value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(Boolean value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(AbstractEnum value) {
        return Objects.isNull(value);
    }

    public static boolean isEmpty(Object value) {
        return Objects.isNull(value);
    }


    public static boolean isNotEmpty(String value) {
        return isNotBlank(value);
    }

    public static boolean isNotEmpty(Integer value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(Long value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(LocalDateTime value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(LocalDate value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(Boolean value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(AbstractEnum value) {
        return Objects.nonNull(value);
    }

    public static boolean isNotEmpty(Object value) {
        return Objects.nonNull(value);
    }

    public static <T> T getEls(T value, T dev) {
        return isEmpty(value) ? dev : value;
    }

    /**
     * 截取字符串
     *
     * @param value 原始字符串
     * @param index 开始位置
     * @param size  截取长度
     * @return 截取后的字符串
     */
    public static String subString(String value, int index, int size) {
        if (isBlank(value)) {
            return null;
        }
        int length = value.length();
        if (length < index) {
            return "";
        }
        if (length < size) {
            size = length - 1;
        }
        return value.substring(index, index + size);
    }

    /**
     * 截取字符串后边的数据
     *
     * @param endSize 长度
     * @return 截取后的字符串
     */
    public static String subString(String value, int endSize) {
        if (isBlank(value)) {
            return null;
        }
        int length = value.length();
        if (length < endSize) {
            endSize = length - 1;
        }
        int endIndex  =length ;
        int start = endIndex - endSize;
        return value.substring(start, endIndex);
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        Matcher matcher = PATTERN_LINE.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 数组转集合
     *
     * @param arr 数组
     * @param <T> 数组对象模板
     * @return 返回数组对应的集合
     */
    public static <T> List<T> asList(T... arr) {
        List<T> result = new ArrayList<>(arr.length);
        for (T t : arr) {
            result.add(t);
        }
        return result;
    }


    /**
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String firstToUpper(String str) {
        if (isBlank(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getPackageByPath(String path) {
        return path.split("java")[1].substring(1).replaceAll("\\\\", ".");
    }

    public static LocalDateTime toLocalDateTime(String dateStr) {
        return LocalDateTime.parse(dateStr, DATE_TIME_FORMATTER);
    }

}
