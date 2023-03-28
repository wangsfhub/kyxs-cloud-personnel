package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleCopyDto;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import com.kyxs.cloud.personnel.api.pojo.entity.RolePermissionR;
import com.kyxs.cloud.personnel.api.pojo.entity.RoleScope;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import com.kyxs.cloud.personnel.mapper.RoleMapper;
import com.kyxs.cloud.personnel.service.RolePermissionRService;
import com.kyxs.cloud.personnel.service.RoleScopeService;
import com.kyxs.cloud.personnel.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleScopeService roleScopeService;

    @Autowired
    private RolePermissionRService rolePermissionRService;

    @Override
    public Page<List<Role>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery.setCondition(new HashMap(){{put("cusId",userInfo.getCusId());}});
        pageQuery =baseMapper.queryListByPage(pageQuery);
        //翻译
        List<Role> list = pageQuery.getRecords();
        if(CollectionUtils.isNotEmpty(list)){
            List<Long> ids = list.stream().map(Role::getId).collect(Collectors.toList());
            List<RoleScope> roleScopes = roleScopeService.list(new LambdaQueryWrapper<RoleScope>().eq(RoleScope::getCusId,userInfo.getCusId()).in(RoleScope::getRoleId,ids));
            Map<Long,List<RoleScope>> roleScopeMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(roleScopes)){
                TranslateUtils.translateList(roleScopes);
                //赋值名称
                roleScopes.forEach(roleScope -> {
                    roleScope.setScopeName(StringUtils.isNotEmpty(roleScope.getDeptName())?roleScope.getDeptName():roleScope.getEmpName());
                });
                roleScopeMap = roleScopes.stream().collect(Collectors.groupingBy(RoleScope::getRoleId));
            }
            List<RolePermissionR> rolePermissionRS = rolePermissionRService.getListByRoleIds(userInfo.getCusId(),ids);
            Map<Long,List<RolePermissionR>> rolePermissionMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(rolePermissionRS)){
                rolePermissionMap = rolePermissionRS.stream().collect(Collectors.groupingBy(RolePermissionR::getRoleId));
            }
            Map<Long, List<RoleScope>> finalRoleScopeMap = roleScopeMap;
            Map<Long, List<RolePermissionR>> finalRolePermissionMap = rolePermissionMap;
            list.forEach(role -> {
                role.setRoleScopes(finalRoleScopeMap.get(role.getId()));
                role.setRolePermissionRS(finalRolePermissionMap.get(role.getId()));
            });
        }
        pageQuery.setRecords(list);
        return pageQuery;
    }

    @Override
    public Map<Long, String> getRoles(Long cusId) {
        Map<Long, String> map = new HashMap<>();
        try {
            List<Role> positions = baseMapper.selectList(new LambdaQueryWrapper<Role>().eq(Role::getCusId,cusId));
            for (Role position : positions) {
                map.put(position.getId(), position.getRoleName());
            }
        } catch (Exception e) {
            throw new BusinessException("用户角色信息获取失败");
        }
        return map;
    }
    //角色复制
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(RoleCopyDto roleCopyDto) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        Role role = baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCusId,userInfo.getCusId()).eq(Role::getId,roleCopyDto.getCopyRoleId()));
        if(role==null){
            throw new BusinessException("请选择要复制的角色");
        }
        Role newRole = new Role();
        newRole.setId(IdWorker.getId());
        newRole.setRoleName(roleCopyDto.getRoleName());
        newRole.setRoleDesc(roleCopyDto.getRoleDesc());
        newRole.setRoleType(role.getRoleType());
        newRole.setRoleStatus("1");
        newRole.setCusId(userInfo.getCusId());
        newRole.setTenantId(userInfo.getTenantId());
        baseMapper.insert(newRole);
        //复制范围
        if("1".equals(roleCopyDto.getIsCopyScope())){

        }
        //复制权限
        if("1".equals(roleCopyDto.getIsCopyPermission())){

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(RoleDto roleDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = modelMapper.map(roleDto, Role.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        role.setTenantId(userInfo.getTenantId());
        role.setCusId(userInfo.getCusId());
        if(role.getId()!=null){
            baseMapper.updateById(role);
            //先删除
            roleScopeService.remove(new LambdaQueryWrapper<RoleScope>().eq(RoleScope::getCusId,userInfo.getCusId()).eq(RoleScope::getRoleId,role.getId()));
            rolePermissionRService.remove(new LambdaQueryWrapper<RolePermissionR>().eq(RolePermissionR::getCusId,userInfo.getCusId()).eq(RolePermissionR::getRoleId,role.getId()));
        }else{
            role.setRoleStatus("1");//启用
            role.setId(IdWorker.getId());
            baseMapper.insert(role);
        }
        //保存机构权限
        if(CollectionUtils.isNotEmpty(roleDto.getRoleScope())){
            List<RoleScope> roleScopes = new ArrayList<>();
            roleDto.getRoleScope().forEach(orgScopeDto -> {
                RoleScope roleScope = new RoleScope();
                roleScope.setId(IdWorker.getId());
                roleScope.setRoleId(role.getId());
                roleScope.setCusId(userInfo.getCusId());
                roleScope.setTenantId(userInfo.getTenantId());
                roleScope.setScopeId(Long.parseLong(orgScopeDto.getId()));
                roleScope.setScopeType(orgScopeDto.getType());
                roleScopes.add(roleScope);
            });
            roleScopeService.saveBatch(roleScopes);
        }
        //保存权限组
        if(CollectionUtils.isNotEmpty(roleDto.getRolePermission())){
            List<RolePermissionR> rolePermissionRS = new ArrayList<>();
            roleDto.getRolePermission().forEach(groupId -> {
                RolePermissionR rolePermissionR = new RolePermissionR();
                rolePermissionR.setId(IdWorker.getId());
                rolePermissionR.setRoleId(role.getId());
                rolePermissionR.setCusId(userInfo.getCusId());
                rolePermissionR.setTenantId(userInfo.getTenantId());
                rolePermissionR.setGroupId(Long.parseLong(groupId));
                rolePermissionRS.add(rolePermissionR);
            });
            rolePermissionRService.saveBatch(rolePermissionRS);
        }
    }
}
