package com.kyxs.cloud.personnel.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.constant.BaseConstant;
import com.kyxs.cloud.personnel.api.enums.CustomHeaderEnum;
import com.kyxs.cloud.personnel.api.pojo.entity.Employee;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoItem;
import com.kyxs.cloud.personnel.api.pojo.entity.InfoSet;
import com.kyxs.cloud.personnel.mapper.EmployeeMapper;
import com.kyxs.cloud.personnel.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("EmpInfoService")
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService, CustomHeaderCommonService {
    @Autowired
    private InfoItemService infoItemService;

    @Autowired
    private InfoSetService infoSetService;
    @Autowired
    private CustomHeaderService headerService;
    @Override
    public Page<List<Employee>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        //获取自定义头
        List<InfoItem> headers= headerService.getCurrentHeader(CustomHeaderEnum.EMP_INFO.getModuleCode(),this.getAllHeaderList());
        if(CollectionUtils.isEmpty(headers)){
            return pageQuery;
        }
        QueryWrapper<Employee> queryWrapper=new QueryWrapper();
        List<String> columns = headers.stream().map(InfoItem::getItemCode).collect(Collectors.toList());
        columns.add("id");
        columns.add("create_time");
        queryWrapper.select(columns.toArray(new String[columns.size()]));
        queryWrapper.eq("cus_id",userInfo.getCusId()).orderByDesc("create_time");
        pageQuery =baseMapper.selectPage(pageQuery,queryWrapper);
        return pageQuery;
    }

    @Override
    public Map<Long, String> getEmployees(Long cusId) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Employee> employees = baseMapper.selectList(new LambdaQueryWrapper<Employee>().eq(Employee::getCusId,cusId));
            for (Employee employee : employees) {
                map.put(employee.getId(), employee.getEmpName());
            }
        } catch (Exception e) {
            throw new BusinessException("员工信息信息获取失败");
        }
        return map;
    }

    @Override
    public Employee getDetailById(Long id) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Employee employee = baseMapper.selectOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId,id).eq(Employee::getCusId,userInfo.getCusId()));
        List<InfoSet> infoSets = infoSetService.getInfoSets();
        employee.setInfoSetIds(infoSets);
        return employee;
    }

    @Override
    public Map getEmpInfoBySetId(Long setId, Long empId) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        InfoSet infoSet = infoSetService.getById(setId);
        List<InfoItem> fields = infoItemService.list(new LambdaQueryWrapper<InfoItem>().eq(InfoItem::getSetId,setId).eq(InfoItem::getItemStatus,"1").orderByAsc(InfoItem::getItemSort));
        List<Map> list = baseMapper.queryEmpInfoBySetId(infoSet.getSourceTable(),empId,userInfo.getCusId());
        List<List<InfoItem>> lists = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(map -> {
                final List<InfoItem> infoItems = JSON.parseArray(JSON.toJSONString(fields),InfoItem.class);
                infoItems.forEach(infoItem -> {
                    String itemCode = StringUtils.underlineToCamel(infoItem.getItemCode());
                    if(map.containsKey(itemCode)&&map.get(itemCode)!=null){
                        infoItem.setItemValue(map.get(itemCode).toString());
                    }
                });
                //加入主键ID，便于前端编辑
                InfoItem infoItem = new InfoItem();
                infoItem.setItemCode("id");
                infoItem.setItemName("主键ID");
                infoItem.setItemType(1);
                infoItem.setItemValue(map.get("id").toString());
                infoItems.add(0,infoItem);
                lists.add(infoItems);
            });
        }
        return new HashMap(){{
            put("values",lists);
            put("fields",fields);
        }};
    }
    /**
     * 保存员工信息
     */
    @Override
    public void saveDynamicInfo(Map param) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        if(!param.containsKey("setId")||param.get("setId")==null){
            throw new BusinessException("表单信息有误，请核查");
        }
        if(!param.containsKey("empId")||param.get("empId")==null){
            throw new BusinessException("表单信息有误，请核查");
        }
        String setId = param.get("setId").toString();
        InfoSet infoSet = infoSetService.getById(setId);
        if(infoSet==null){
            throw new BusinessException("表单信息有误，请核查");
        }
        List<InfoItem> infoItems = infoItemService.list(new LambdaQueryWrapper<InfoItem>().eq(InfoItem::getSetId,setId).eq(InfoItem::getItemStatus,"1").orderByAsc(InfoItem::getItemSort));
        infoItems.forEach(infoItem -> {
            infoItem.setItemValue(param.get(infoItem.getItemCode())==null?"":param.get(infoItem.getItemCode()).toString());
            if("1".equals(infoItem.getIsNotnull())&&ObjectUtils.isEmpty(infoItem.getItemValue())){
                throw new BusinessException(infoItem.getItemName()+"不能为空");
            }
        });

        if(ObjectUtils.isNotEmpty(param.get("id"))){
            //修改
            baseMapper.dynamicUpdate(infoSet.getSourceTable(),userInfo,infoItems,param.get("id").toString());
        }else{
            //新增计入主键ID、租户ID，客户ID、员工ID
            InfoItem infoItem = new InfoItem();
            infoItem.setItemCode("id");
            infoItem.setItemValue(IdWorker.getIdStr());
            infoItems.add(0,infoItem);
            infoItem = new InfoItem();
            infoItem.setItemCode("tenant_id");
            infoItem.setItemValue(userInfo.getTenantId().toString());
            infoItems.add(1,infoItem);
            infoItem = new InfoItem();
            infoItem.setItemCode("cus_id");
            infoItem.setItemValue(userInfo.getCusId().toString());
            infoItems.add(2,infoItem);
            infoItem = new InfoItem();
            infoItem.setItemCode("emp_id");
            infoItem.setItemValue(param.get("empId").toString());
            infoItems.add(3,infoItem);
            baseMapper.dynamicInsert(infoSet.getSourceTable(),userInfo,infoItems);
        }
    }

    @Override
    public void deleteBySetId(Long setId, Long id) {
        InfoSet infoSet = infoSetService.getById(setId);
        if(infoSet==null){
            throw new BusinessException("删除失败，参数有误");
        }
        baseMapper.deleteBySetId(infoSet.getSourceTable(),UserInfoUtil.getUserInfo().getCusId(),id);
    }


    @Override
    public List<InfoItem> getAllHeaderList() {
        //查询所有
        List<InfoItem> infoItems = infoItemService.list(new LambdaQueryWrapper<InfoItem>().eq(InfoItem::getCusId, BaseConstant.DEFAULT_CUS_ID).eq(InfoItem::getSetId,1L));
        return infoItems;
    }

}
