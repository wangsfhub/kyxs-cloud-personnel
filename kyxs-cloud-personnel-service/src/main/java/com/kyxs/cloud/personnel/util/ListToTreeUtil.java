package com.kyxs.cloud.personnel.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * list转树
 * @author wangsf
 * @date 2023-3-4
 */
public class ListToTreeUtil {
    /**
     * 指向父级的id 类型必须是int
     */
    private static final String PARENT_FILED_NAME = "superId";

    /**
     * 子集列表
     */
    private static final String CHILD_FILED_NAME = "children";

    /**
     * 本身的id
     */
    private static final String ID_FILED_NAME = "id";


    /**
     * 列表转树形结构
     *
     * @param tList 转换前的列表
     * @param <T>   范型 T 需要保证转换的类中对应关系 Integer类型的 parentId 与指向父级的 id  保证类型中包含 List<T> 的 childList 属性
     * @return 转换后的列表
     */
    public static <T> List<T> transferListToTree(List<T> tList) throws NoSuchFieldException {
        if (CollectionUtils.isEmpty(tList)) {
            return tList;
        }
        return transferListToTree(tList, ID_FILED_NAME, PARENT_FILED_NAME, CHILD_FILED_NAME);
    }

    public static <T> List<T> transferListToTree(List<T> tList, String idFieldName, String pidFieldName, String childListFieldName) throws NoSuchFieldException {
        if (CollectionUtils.isEmpty(tList)) {
            return tList;
        }
        if (StringUtils.isEmpty(idFieldName) || StringUtils.isEmpty(pidFieldName) || StringUtils.isEmpty(childListFieldName)) {
            throw new NoSuchFieldException("属性名称缺失");
        }
        return tList.stream().filter(item -> {
            try {
                // 获取父级节点
                Field field = item.getClass().getDeclaredField(pidFieldName);
                field.setAccessible(Boolean.TRUE);
                Long pid = (Long) field.get(item);
                return pid == -1L;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("树形结构转换失败");
            }
        }).map(item -> {
            try {
                Field childField = item.getClass().getDeclaredField(childListFieldName);
                childField.setAccessible(Boolean.TRUE);
                childField.set(item, getChildList(item, tList, idFieldName, pidFieldName, childListFieldName));
                return item;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("树形结构转换失败");
            }
        }).collect(Collectors.toList());
    }

    private static <T> List<T> getChildList(T t, List<T> tList, String idFieldName, String pidFieldName, String childListFieldName) throws NoSuchFieldException, IllegalAccessException {
        Field parentField = t.getClass().getDeclaredField(idFieldName);
        parentField.setAccessible(Boolean.TRUE);
        Long id = (Long) parentField.get(t);
        return tList.stream().filter(item -> {
            try {
                // 获取该节点下的所有子节点
                Field field = item.getClass().getDeclaredField(pidFieldName);
                field.setAccessible(Boolean.TRUE);
                Long pid = (Long) field.get(item);
                return pid == id;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("树形结构转换失败");
            }
        }).peek(item -> {
            try {
                Field childField = item.getClass().getDeclaredField(childListFieldName);
                childField.setAccessible(Boolean.TRUE);
                childField.set(item, getChildList(item, tList, idFieldName, pidFieldName, childListFieldName));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("树形结构转换失败");
            }
        }).collect(Collectors.toList());
    }
}
