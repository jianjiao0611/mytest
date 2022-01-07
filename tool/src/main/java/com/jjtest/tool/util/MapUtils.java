package com.jjtest.tool.util;

import com.jjtest.tool.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * map工具类
 */
public  class MapUtils {

    private MapUtils() {

    }

    public static final String DEFAULT_SEPARATOR = "_";

    /**
     *  list转map
     * @param t
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> listToMap(List<T> t, String... keys) {
        return convertMap(DEFAULT_SEPARATOR, t, keys);
    }

    public static <T> Map<String, T> convertMap(String separator, List<T> t, String... keys) {
        Map<String, T> result = new HashMap<>();

        if (CollectionUtils.isEmpty(t)) {
            return result;
        }
        try {
            List<Field> cellFieldList = getCellFieldList(t.get(0).getClass(), keys);

            for (T model : t) {

                result.put(getKey(cellFieldList, separator, model), model);
            }
        } catch (NoSuchFieldException e) {
            throw new ServiceException("未找到对应字段");
        } catch (Exception e) {
            throw new ServiceException("转换异常");
        }
        return result;
    }

    public static <T> String getKey(List<Field> fields, String separator, T model) throws IllegalAccessException {
        if (StringUtils.isBlank(separator)) {
            separator = DEFAULT_SEPARATOR;
        }
        StringBuilder s = new StringBuilder();
        for (Field field : fields) {
            s.append(field.get(model)).append(separator);
        }
        return s.substring(0, s.length() - separator.length());
    }

    public static <T> List<Field> getCellFieldList(Class<T> clazz, String... keys) throws NoSuchFieldException {
        List<Field> list = new LinkedList<>();
        for (String fieldName : keys) {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            list.add(field);
        }
        return list;
    }
}
