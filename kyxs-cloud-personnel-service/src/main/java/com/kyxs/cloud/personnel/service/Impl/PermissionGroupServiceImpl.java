package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.PermissionGroupDto;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionGroup;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionMenu;
import com.kyxs.cloud.personnel.api.pojo.entity.PermissionOrgScope;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import com.kyxs.cloud.personnel.mapper.PermissionGroupMapper;
import com.kyxs.cloud.personnel.service.PermissionGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class PermissionGroupServiceImpl  extends ServiceImpl<PermissionGroupMapper, PermissionGroup> implements PermissionGroupService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveInfo(PermissionGroupDto permissionGroupDto) {
        ModelMapper modelMapper = new ModelMapper();
        PermissionGroup permissionGroup = modelMapper.map(permissionGroupDto, PermissionGroup.class);
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        permissionGroup.setTenantId(userInfo.getTenantId());
        permissionGroup.setCusId(userInfo.getCusId());
        if(permissionGroupDto.getId()!=null){
            baseMapper.updateById(permissionGroup);
            //删除有权机构
            baseMapper.deletePermissionOrgScopeByGroupId(userInfo.getCusId(),permissionGroup.getId());
            //删除操作菜单
            baseMapper.deletePermissionMenuByGroupId(userInfo.getCusId(),permissionGroup.getId());
        }else{
            permissionGroup.setId(IdWorker.getId());
            baseMapper.insert(permissionGroup);
        }
        //插入有权机构
        List<PermissionOrgScope> orgScopes = new ArrayList();
        permissionGroupDto.getGroupOrgs().forEach(orgId ->{
            PermissionOrgScope permissionOrgScope = new PermissionOrgScope();
            permissionOrgScope.setGroupId(permissionGroup.getId());
            permissionOrgScope.setCusId(userInfo.getCusId());
            permissionOrgScope.setTenantId(userInfo.getTenantId());
            permissionOrgScope.setId(IdWorker.getId());
            permissionOrgScope.setOrgId(Long.parseLong(orgId));
            orgScopes.add(permissionOrgScope);
        });
        baseMapper.insertPermissionOrgScope(orgScopes);
        //插入菜单
        List<PermissionMenu> permissionMenus = new ArrayList();
        permissionGroupDto.getGroupMenus().forEach(menuDto ->{
            PermissionMenu permissionMenu = new PermissionMenu();
            permissionMenu.setGroupId(permissionGroup.getId());
            permissionMenu.setCusId(userInfo.getCusId());
            permissionMenu.setTenantId(userInfo.getTenantId());
            permissionMenu.setId(IdWorker.getId());
            permissionMenu.setMenuId(menuDto.getId());
            permissionMenu.setOperationId(String.join(",",menuDto.getCheckedOperator()));
            permissionMenus.add(permissionMenu);
        });
        baseMapper.insertPermissionMenu(permissionMenus);
    }

    @Override
    public Page<List<PermissionGroup>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery =baseMapper.selectPage(pageQuery,new LambdaQueryWrapper<PermissionGroup>().eq(PermissionGroup::getCusId,userInfo.getCusId()).orderByDesc(PermissionGroup::getCreateTime));
        //翻译
        List<PermissionGroup> list = pageQuery.getRecords();
        //查询权限机构
        if(CollectionUtils.isNotEmpty(list)){
            List<Long> ids = list.stream().map(PermissionGroup::getId).collect(Collectors.toList());
            List<PermissionOrgScope> orgScopes = baseMapper.queryOrgScopeByGroupIds(userInfo.getCusId(),ids);
            StringJoiner orgNames = new StringJoiner(",");
            if(CollectionUtils.isNotEmpty(orgScopes)){
                TranslateUtils.translateList(orgScopes);//翻译机构名称
                orgScopes.forEach(permissionOrgScope -> {
                    if(orgNames.length()<3) {
                        orgNames.add(permissionOrgScope.getOrgName());
                    }
                });
            }
            //查询菜单权限
            StringJoiner menuNames = new StringJoiner(",");
            List<PermissionMenu> menus = baseMapper.queryPermissionMenuByGroupIds(userInfo.getCusId(),ids);
            if(CollectionUtils.isNotEmpty(menus)){
                menus.forEach(permissionMenu -> {
                    if(menuNames.length()<3){
                        menuNames.add(permissionMenu.getMenuName());
                    }
                });
            }
            list.forEach(permissionGroup -> {
                permissionGroup.setOrgNames(orgNames.toString());
                permissionGroup.setMenuNames(menuNames.toString());
            });
        }
        TranslateUtils.translateList(list);
        return pageQuery;
    }

    @Override
    public List<PermissionOrgScope> queryOrgScopeByGroupId(Long cusId, Long groupId) {
        List<PermissionOrgScope> list = baseMapper.queryOrgScopeByGroupId(cusId,groupId);
        //翻译
        TranslateUtils.translateList(list);
        return list;
    }

    @Override
    public List<PermissionMenu> queryPermissionMenuByGroupId(Long cusId, Long groupId) {
        return baseMapper.queryPermissionMenuByGroupId(cusId,groupId);
    }

    @Override
    public List<PermissionGroup> getGroupsByRoleIds(Long cusId, List<Long> roleIds) {
        return baseMapper.getGroupsByRoleIds(cusId,roleIds);
    }
}
