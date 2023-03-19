package com.kyxs.cloud.personnel.init;

import com.kyxs.cloud.core.base.constants.RedisCacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InitConfigServiceImpl implements IInitConfigService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    private static String operation = ":"; // redis冒号分组

    @Override
    public void refreshParam() {
        String sql = " select cus_id,para_key,para_value from sys_config "; // 系统参数表
        List<Map<String, Object>> mapsList = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(mapsList)) { // 存在值
            log.info("初始化系统参数到redis中，数量:{}",mapsList.size());
            /**
             * 删除指定前缀的key
             */
            Set<String> keys = redisTemplate.keys(RedisCacheConstants.SYS_CONFIG_PRE+operation+"*");
            redisTemplate.delete(keys);
            StringBuilder sb = new StringBuilder();
            //保存sys级别
            List<Map<String, Object>> sysList = mapsList.stream().filter(e->Long.parseLong(e.get("cus_id").toString())==1L).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(sysList)){
                for (Map<String, Object> map : sysList) {
                    sb.setLength(0);
                    String para_key = map.get("para_key").toString(); //名称
                    String para_value = map.get("para_value").toString(); //值
                    if(!"".equals(para_key)){
                        String key = sb.append(RedisCacheConstants.SYS_CONFIG_PRE).append(operation).append(para_key).toString();
                        redisTemplate.opsForValue().set(key, para_value);
                    }
                }
            }
            //保存客户级别
            List<Map<String, Object>> cusList = mapsList.stream().filter(e->Long.parseLong(e.get("cus_id").toString())!=1L).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(cusList)){
                for (Map<String, Object> map : cusList) {
                    sb.setLength(0);
                    String cus_id = map.get("cus_id").toString(); //客户id
                    String para_key = map.get("para_key").toString(); //名称
                    String para_value = map.get("para_value").toString(); //值
                    if(!"".equals(para_key)){
                        String key = sb.append(RedisCacheConstants.SYS_CONFIG_PRE).append(operation).append(cus_id).append(operation).append(para_key).toString();
                        redisTemplate.opsForValue().set(key, para_value);
                    }
                }
            }
        }
    }
}

