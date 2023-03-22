package com.kyxs.cloud.personnel.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyxs.cloud.core.base.entity.UserInfo;
import com.kyxs.cloud.core.base.exception.BusinessException;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.core.base.utils.UserInfoUtil;
import com.kyxs.cloud.personnel.api.pojo.dto.RoleCopyDto;
import com.kyxs.cloud.personnel.api.pojo.entity.Role;
import com.kyxs.cloud.personnel.api.util.TranslateUtils;
import com.kyxs.cloud.personnel.mapper.RoleMapper;
import com.kyxs.cloud.personnel.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Page<List<Role>> queryListByPage(PageQuery pageQuery) {
        UserInfo userInfo = UserInfoUtil.getUserInfo();
        pageQuery =baseMapper.selectPage(pageQuery,new LambdaQueryWrapper<Role>().eq(Role::getCusId,userInfo.getCusId()).orderByDesc(Role::getCreateTime));
        //翻译
        TranslateUtils.translateList(pageQuery.getRecords());
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
}
