package com.kyxs.cloud.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Map> queryEmpInfoBySetId(@Param(value = "sourceTable") String sourceTable, @Param(value = "empId") Long empId, @Param(value = "cusId") Long cusId);

    void dynamicUpdate(@Param(value = "sourceTable")String sourceTable, @Param(value = "userInfo")UserInfo userInfo, @Param(value = "fields")List<InfoItem> fields,@Param(value = "id")String id);

    void dynamicInsert(@Param(value = "sourceTable")String sourceTable, @Param(value = "userInfo")UserInfo userInfo, @Param(value = "fields")List<InfoItem> fields);

    void deleteBySetId(@Param(value = "sourceTable")String sourceTable,@Param(value = "cusId")Long cusId,@Param(value = "id") Long id);
}
