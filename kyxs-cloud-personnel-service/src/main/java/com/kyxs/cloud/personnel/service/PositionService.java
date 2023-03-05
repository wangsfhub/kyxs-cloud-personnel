package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.Position;

import java.util.List;
import java.util.Map;

public interface PositionService extends IService<Position> {
    Page<List<Position>> queryListByPage(PageQuery pageQuery);

    Map<Long, String> getPositions(Long cusId);
}
