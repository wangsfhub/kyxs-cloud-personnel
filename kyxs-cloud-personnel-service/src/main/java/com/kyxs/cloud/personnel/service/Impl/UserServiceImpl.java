package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.entity.*;
import com.kyxs.cloud.personnel.mapper.UserMapper;
import com.kyxs.cloud.personnel.service.DepartmentService;
import com.kyxs.cloud.personnel.service.PermissionGroupService;
import com.kyxs.cloud.personnel.service.RoleService;
import com.kyxs.cloud.personnel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final DepartmentService departmentService;
    private final RoleService roleService;
    private final PermissionGroupService permissionGroupService;

    @Override
    public PageQuery queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery.setCondition(new HashMap(){{put("cusId",userInfo.getCusId());}});
        pageQuery =baseMapper.selectPage(pageQuery,new LambdaQueryWrapper<User>().eq(User::getCusId,userInfo.getCusId()).orderByDesc(User::getCreateTime));
        //查询角色、权限组
        List<User> list = pageQuery.getRecords();
        if(CollectionUtils.isNotEmpty(list)){
            //查询所有部门数据
            List<Department> departments = departmentService.list(new QueryWrapper<Department>().select("id,super_id").eq("cus_id",userInfo.getCusId()));
            for(User user : list){
                //获取用户的所有上级部门ID
                List<Long> superIds = getDeptSuperIds(departments,null, null,user.getDepartment());
                //加上当前部门ID、员工ID查询角色信息
                superIds.add(user.getDepartment());
                superIds.add(user.getId());
                List<Role> roles = roleService.getRolesByScopeIds(userInfo.getCusId(),superIds);
                user.setRoles(roles);
                if(CollectionUtils.isNotEmpty(roles)){
                    List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
                    List<PermissionGroup> groups = permissionGroupService.getGroupsByRoleIds(userInfo.getCusId(),roleIds);
                    user.setGroups(groups);
                }
            }
        }
        return pageQuery;
    }
    /**
     * 获取用户所有上级部门ID
     * @param departments 公司所有部门
     * @param current 当前部门
     * @return List<Long> 所有上级部门ID
     */
    public List<Long> getDeptSuperIds(List<Department> departments,List<Long> superIds,Long superId,Long current){
        if(superIds == null){
            superIds = new ArrayList<>();
        }
        //如果当前部门是空则返回 公司ID
        if(current==null){
            superIds.add(departments.get(0).getId());
            return superIds;
        }
        superId = null;
        if(superId==null){
            for(Department department:departments){
                if(department.getId()==current){
                    superId= department.getSuperId();
                    break;
                }
            }
        }
        List<Department> surplusDepts = departments;
        for(Department department:departments){
            if(department.getId()==superId){
                superIds.add(department.getId());
                surplusDepts.remove(department);
                if(department.getSuperId()==-1){
                    return superIds;
                }
                return getDeptSuperIds(surplusDepts,superIds,department.getSuperId(),department.getId());
            }
        }
        return superIds;
    }

}
