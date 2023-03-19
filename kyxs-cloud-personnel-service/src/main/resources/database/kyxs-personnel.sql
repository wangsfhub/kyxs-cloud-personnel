#人事数据库
CREATE DATABASE IF NOT EXISTS kyxs_personnel DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

# 系统代码项表
create table `code_set` (
    id VARCHAR(20) not null,
    cus_id BIGINT(20) COMMENT '客户id',
    tenant_id BIGINT(20) COMMENT '租户id',
    set_name VARCHAR(20) COMMENT '代码项名称',
    set_status VARCHAR(20) COMMENT '代码项状态',
    set_sort int COMMENT '代码项排序',
    create_time VARCHAR(20),
    update_time VARCHAR(20),
    creator BIGINT(20),
    operator BIGINT(20),
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统代码项表';

# 系统代码集表
create table `code_item` (
     id VARCHAR(20) not null,
     cus_id BIGINT(20) COMMENT '客户id',
     tenant_id BIGINT(20) COMMENT '租户id',
     set_id VARCHAR(20) COMMENT '代码项id',
     code_name VARCHAR(20) COMMENT '代码名称',
     code_status VARCHAR(20) COMMENT '代码状态',
     code_sort int COMMENT '代码项排序',
     create_time VARCHAR(20),
     update_time VARCHAR(20),
     creator BIGINT(20),
     operator BIGINT(20),
     PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统代码集表';
# 客户信息表
create table `customer` (
    id BIGINT not null,
    tenant_id BIGINT(20) COMMENT '租户id',
    cus_no varchar(20) COMMENT '客户编号',
    cus_name VARCHAR(20) COMMENT '客户名称',
    cus_abbr_name VARCHAR(20) COMMENT '客户简称',
    cus_status VARCHAR(20) COMMENT '客户状态',
    cus_logo VARCHAR(50) COMMENT '客户logo',
    cus_scope VARCHAR(20) COMMENT '客户规模',
    cus_industry VARCHAR(20) COMMENT '客户行业',
    cus_nature VARCHAR(20) COMMENT '客户性质',
    cus_office VARCHAR(20) COMMENT '客户办公地点',
    contact_name VARCHAR(20) COMMENT '联系人姓名',
    contact_idcard VARCHAR(30) COMMENT '联系人证件号码',
    contact_phone int(11) COMMENT '联系人手机',
    contact_email varchar(30) COMMENT '联系人邮箱',
    corp_name VARCHAR(20) COMMENT '法人姓名',
    corp_idcard VARCHAR(30) COMMENT '法人证件号码',
    corp_phone int(11) COMMENT '法人手机',
    corp_email varchar(30) COMMENT '法人邮箱',
    create_time VARCHAR(20),
    update_time VARCHAR(20),
    creator BIGINT(20),
    operator BIGINT(20),
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='客户信息表';
# 部门信息表
create table `department` (
      id BIGINT not null,
      cus_id BIGINT(20) COMMENT '客户id',
      tenant_id BIGINT(20) COMMENT '租户id',
      dept_no varchar(20) COMMENT '部门编号',
      dept_name VARCHAR(20) COMMENT '部门名称',
      super_id BIGINT(20) COMMENT '上级部门',
      dept_type VARCHAR(20) COMMENT '部门类别',
      dept_desc VARCHAR(100) COMMENT '部门描述',
      head_count int COMMENT '编制人数',
      leader_id BIGINT(20) COMMENT '负责人',
      dept_sort int COMMENT '部门排序',
      create_time VARCHAR(20),
      update_time VARCHAR(20),
      creator BIGINT(20),
      operator BIGINT(20),
      PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门信息表';
# 员工信息表
create table `employee` (
    id BIGINT not null,
    cus_id BIGINT(20) COMMENT '客户id',
    tenant_id BIGINT(20) COMMENT '租户id',
    emp_no varchar(20) COMMENT '员工编号',
    emp_name VARCHAR(20) COMMENT '员工姓名',
    id_type VARCHAR(20) COMMENT '证件类型',
    id_card VARCHAR(30) COMMENT '证件号码',
    company_id BIGINT(20) COMMENT '所属公司',
    dept_id BIGINT(20) COMMENT '所属部门',
    gender VARCHAR(20) COMMENT '性别',
    age int COMMENT '年龄',
    nationality VARCHAR(20) COMMENT '国籍',
    birthday VARCHAR(10) COMMENT '出生日期',
    entry_time VARCHAR(20) COMMENT '入职时间',
    emp_status VARCHAR(20) COMMENT '员工状态',
    create_time VARCHAR(20),
    update_time VARCHAR(20),
    creator BIGINT(20),
    operator BIGINT(20),
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工信息表';

# 岗位信息表
create table `position` (
    id BIGINT not null,
    cus_id BIGINT(20) COMMENT '客户id',
    tenant_id BIGINT(20) COMMENT '租户id',
    post_name VARCHAR(20) COMMENT '岗位名称',
    dept_name BIGINT(20) COMMENT '所属部门',
    post_type VARCHAR(20) COMMENT '岗位类别',
    post_status VARCHAR(20) COMMENT '岗位状态',
    head_count int COMMENT '编制人数',
    post_salary DECIMAL(15,2) COMMENT '岗位工资',
    post_desc varchar(100) COMMENT '岗位描述',
    create_time VARCHAR(20),
    update_time VARCHAR(20),
    creator BIGINT(20),
    operator BIGINT(20),
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位信息表';