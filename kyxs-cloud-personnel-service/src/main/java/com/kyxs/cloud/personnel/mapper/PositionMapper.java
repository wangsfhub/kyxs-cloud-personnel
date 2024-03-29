package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.personnel.api.pojo.entity.Department;
import com.kyxs.cloud.personnel.api.pojo.entity.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PositionMapper extends BaseMapper<Position> {

}
