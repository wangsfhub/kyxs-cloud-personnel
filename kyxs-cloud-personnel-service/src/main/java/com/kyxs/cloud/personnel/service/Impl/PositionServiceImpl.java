package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.Position;
import com.kyxs.cloud.personnel.mapper.PositionMapper;
import com.kyxs.cloud.personnel.service.PositionService;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Override
    public Page<List<Position>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery =baseMapper.selectPage(pageQuery,new LambdaQueryWrapper<Position>().eq(Position::getCusId,userInfo.getCusId()).orderByDesc(Position::getCreateTime));
        //翻译
        TranslateUtils.translateList(pageQuery.getRecords());
        return pageQuery;
    }

    @Override
    public Map<Long, String> getTranslatePositions(Long cusId, List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Position> positions = baseMapper.selectList(new QueryWrapper<Position>().select("id","post_name").eq("cus_id",cusId).in("id",ids));
            for (Position position : positions) {
                map.put(position.getId(), position.getPostName());
            }
        } catch (Exception e) {
            throw new BusinessException("用户岗位信息获取失败");
        }
        return map;
    }

}
