package com.kyxs.cloud.personnel.api.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.utils.SpringApplicationContextUtil;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.annotation.Translate;
import com.kyxs.cloud.personnel.api.constant.TranslateConstant;
import com.kyxs.cloud.personnel.api.feign.TranslateFeignService;
import com.kyxs.cloud.personnel.api.pojo.entity.*;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 翻译工具类，针对姓名、部门、代码等翻译
 * @author wangsf
 * @date 2023-3-4
 */
public class TranslateUtils {

    private static TranslateFeignService translateFeignService = SpringApplicationContextUtil.getBean(TranslateFeignService.class);


    /**
     * 根据code翻译字段名称
     *
     * @param list 需要翻译的集合
     */
    public static <T> void translateList(List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        TranslateObjectEntity info = getObjectInfo(list.get(0));
        if (ObjectUtil.isEmpty(info)) {
            return;
        }
        for (T obj : list) {
            translateObject(obj, info);
        }
    }

    /**
     * 根据code翻译字段名称(转化为指定实体集合)
     *
     * @param list 需要翻译的集合
     */
    public static <T, R> List<R> translateList(List<T> list, Class<R> targetClazz) {
        List<R> targrts = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return targrts;
        }
        TranslateObjectEntity info = null;
        for (T obj : list) {
            R targetObj = BeanUtil.copyProperties(obj, targetClazz);
            if (ObjectUtil.isEmpty(info)) {
                info = getObjectInfo(targetObj);
            }
            translateObject(targetObj, info);
            targrts.add(targetObj);
        }
        return targrts;
    }


    /**
     * 翻译单个实体
     *
     * @param obj 实体信息
     * @param <T>
     */
    public static <T> void translateObject(T obj) {
        if (ObjectUtil.isEmpty(obj)) {
            return;
        }
        TranslateObjectEntity info = getObjectInfo(obj);
        if (ObjectUtil.isEmpty(info)) {
            return;
        }
        translateObject(obj, info);
    }

    /**
     * 翻译单个实体
     *
     * @param obj         实体信息
     * @param targetClazz 需返回的实体class
     * @param <T>         原实体
     * @param <R>         返回实体
     * @return
     */
    public static <T, R> R translateObject(T obj, Class<R> targetClazz) {
        if (ObjectUtil.isEmpty(obj) || ObjectUtil.isEmpty(targetClazz)) {
            return null;
        }
        R properties = BeanUtil.copyProperties(obj, targetClazz);
        TranslateObjectEntity info = getObjectInfo(properties);
        if (ObjectUtil.isEmpty(info)) {
            return null;
        }
        translateObject(properties, info);
        return properties;
    }


    /**
     * 翻译单个对象（获取需要翻译的对象实体信息）
     *
     * @param obj 对象信息
     * @param <T> 对象字段翻译信息
     */
    private static <T> TranslateObjectEntity getObjectInfo(T obj) {
        if (ObjectUtil.isEmpty(obj)) {
            return null;
        }
        Class<?> aClass = obj.getClass();
        TranslateObjectEntity objectEntity = new TranslateObjectEntity();
        objectEntity.setAClass(aClass);
        Map<String, Field> fields = getAllFileds(aClass);

        List<TranslateEntity> translateInfo = new ArrayList<>();
        TranslateEntity translateEntity = null;
        Pair<String, String> pair = null;
        // 用户单位
        Map<Long, String> userUnits = null;
        // 用户岗位
        Map<Long, String> userPostions = null;
        // 用户姓名
        Map<Long, String> userEmployees = null;
        // 数据字典
        Map<String, String> dictions = null;

        List<String> dictionsCodes = new ArrayList<>();
        for (Field field : fields.values()) {
            if (field.isAnnotationPresent(Translate.class)) {
                translateEntity = new TranslateEntity();
                translateEntity.setTranslateField(field);
                Translate annotation = field.getAnnotation(Translate.class);
                String translateCode = annotation.code();
                if (StringUtils.isBlank(translateCode)) {
                    translateCode = field.getName();
                }
                translateEntity.setField(fields.get(translateCode));
                int translateType = annotation.type();
                pair = new Pair(translateCode, field.getName());
                translateEntity.setTranslateType(translateType);
                translateEntity.setPair(pair);
                if (TranslateConstant.ENUM_CLAZ == translateType) {
                    translateEntity.setEnumClass(annotation.EnumClaz());
                }
                if (TranslateConstant.EMP_DEPT == translateType && MapUtils.isEmpty(userUnits)) {
                    userUnits =  translateFeignService.getDepartments(UserInfoUtil.getUserInfo().getCusId());
                }
                if (TranslateConstant.EMP_POST == translateType && MapUtils.isEmpty(userPostions)) {
                    userPostions = translateFeignService.getPositions(UserInfoUtil.getUserInfo().getCusId());
                }
                if (TranslateConstant.EMP_NAME == translateType && MapUtils.isEmpty(userEmployees)) {
                    userEmployees = translateFeignService.getEmployees(UserInfoUtil.getUserInfo().getCusId());
                }
                if (TranslateConstant.DICTION == translateType && MapUtils.isEmpty(dictions)) {
                    String[] dictionaryType = annotation.dictionaryType();
                    List<String> strings = Arrays.asList(dictionaryType);
                    if (CollectionUtils.isEmpty(strings)) {
                        throw new BusinessException("数据字典类型不能为空");
                    }
                    dictionsCodes.addAll(strings);
                    translateEntity.setDictionaryType(annotation.dictionaryType());
                }
                translateEntity.setWhetherStr(annotation.whetherStr());
                translateInfo.add(translateEntity);
            }
        }
        dictions = getDictions(dictionsCodes);
        objectEntity.setUserUnits(userUnits);
        objectEntity.setUserPositions(userPostions);
        objectEntity.setUserEmployees(userEmployees);
        objectEntity.setDictions(dictions);
        objectEntity.setTranslateInfo(translateInfo);
        return objectEntity;
    }


    /**
     * 获取所有字段含父级
     *
     * @param aClass 实体类
     * @return 所有字段信息
     */
    private static Map<String, Field> getAllFileds(Class<?> aClass) {
        Map<String, Field> fileds = new HashMap<>();
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = aClass;
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        for (Field field : fieldList) {
            fileds.put(field.getName(), field);
        }
        return fileds;
    }


    /**
     * 翻译单个实体
     *
     * @param obj  实体信息
     * @param info 翻译对象信息
     * @param <T>
     */
    private static <T> void translateObject(T obj, TranslateObjectEntity info) {
        try {
            for (TranslateEntity entity : info.getTranslateInfo()) {
                Integer type = entity.getTranslateType();
                Field field = entity.getField();
                field.setAccessible(true);

                Object codeInfo = field.get(obj);
                Field translate = entity.getTranslateField();
                translate.setAccessible(true);
                if (null == codeInfo) {
                    continue;
                }
                Object value = null;
                String key = codeInfo.toString();

                switch (type) {
                    case TranslateConstant.EMP_DEPT:
                        // 部门
                        if (info.getUserUnits().containsKey(Long.parseLong(key))) {
                            value = info.getUserUnits().get(Long.parseLong(key));
                        }
                        break;
                    case TranslateConstant.EMP_NAME:
                        // 姓名
                        if (info.getUserEmployees().containsKey(Long.parseLong(key))) {
                            value = info.getUserEmployees().get(Long.parseLong(key));
                        }
                        break;
                    case TranslateConstant.EMP_POST:
                        // 岗位
                        if (info.getUserPositions().containsKey(Long.parseLong(key))) {
                            value = info.getUserPositions().get(Long.parseLong(key));
                        }
                        break;
                    case TranslateConstant.ENUM_CLAZ:
                        // 枚举
                        Class enumClass = entity.getEnumClass();
                        value = EnumUtils.getMessageByCode(enumClass, codeInfo);
                        break;
                    case TranslateConstant.DICTION:
                        break;
                    default:
                        break;
                }
                translate.set(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("翻译失败");
        }
    }
    /**
     * 获取数据字典集合
     *
     * @param codes 数据字典code
     * @return 数据字典集合
     */
    private static Map<String, String> getDictions(List<String> codes) {
        Map<String, String> diction = new HashMap<>();
//        try {
//            List<CodeItem> list = null;//后端先不翻译
//            for (CodeItem codeItem : list) {
//                diction.put(codeItem.getId(), codeItem.getCodeName());
//            }
//        } catch (Exception e) {
//            throw new BusinessException("数据字典查询失败");
//        }
        return diction;
    }
}
