package com.kyxs.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyxs.cloud.core.base.mybatisplus.PageQuery;
import com.kyxs.cloud.personnel.api.pojo.entity.User;

public interface UserService extends IService<User> {
    PageQuery queryListByPage(PageQuery pageQuery);
}
