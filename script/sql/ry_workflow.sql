-- ----------------------------
-- 1、流程分类表
-- ----------------------------
create table wf_category
(
    id            bigint(20)   not null comment '流程分类ID',
    tenant_id     varchar(20) default '000000' comment '租户编号',
    category_name varchar(255) null comment '分类名称',
    category_code varchar(255) null comment '分类编码',
    parent_id     bigint       null comment '父级id',
    sort_num      int(19)      null comment '排序',
    create_dept   bigint(20)   null comment '创建部门',
    create_by     bigint(20)   null comment '创建者',
    create_time   datetime     null comment '创建时间',
    update_by     bigint(20)   null comment '更新者',
    update_time   datetime     null comment '更新时间',
    constraint uni_category_code unique (category_code),
    primary key (id)
) engine = innodb comment = '流程分类';
INSERT INTO wf_category values (1, 'OA', 'OA', 0, 0, '000000', 103, 1, sysdate(), 1, sysdate());

-- ----------------------------
-- 2、流程定义配置表
-- ----------------------------
create table wf_definition_config
(
    id            bigint(20)              not null comment '流程定义ID',
    tenant_id     varchar(20)  default '000000' comment '租户编号',
    table_name    varchar(255)            not null comment '表名',
    definition_id varchar(255)            not null comment '流程定义ID',
    process_key   varchar(255)            not null comment '流程KEY',
    version       varchar(255)            not null comment '流程版本',
    create_dept   bigint(20)              null comment '创建部门',
    create_by     bigint(20)              null comment '创建者',
    create_time   datetime                null comment '创建时间',
    update_by     bigint(20)              null comment '更新者',
    update_time   datetime                null comment '更新时间',
    remark        varchar(500) default '' null comment '备注',
    primary key (id),
    constraint uni_definition_id unique (definition_id)
) engine = innodb comment '流程定义配置';

insert into sys_menu values ('11616', '工作流', '0', '6', 'workflow', '', '', '1', '0', 'M', '0', '0', '', 'workflow', 103, 1, sysdate(),NULL, NULL, '');
insert into sys_menu values ('11618', '我的任务', '0', '7', 'task', '', '', '1', '0', 'M', '0', '0', '', 'my-task', 103, 1, sysdate(), NULL,
        NULL, '');
insert into sys_menu
values ('11619', '我的待办', '11618', '2', 'taskWaiting', 'workflow/task/taskWaiting', '', '1', '1', 'C', '0', '0', '',
        'waiting', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11632', '我的已办', '11618', '3', 'taskFinish', 'workflow/task/taskFinish', '', '1', '1', 'C', '0', '0', '',
        'finish', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11633', '我的抄送', '11618', '4', 'taskCopyList', 'workflow/task/taskCopyList', '', '1', '1', 'C', '0', '0',
        '', 'my-copy', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11620', '流程定义', '11616', '3', 'processDefinition', 'workflow/processDefinition/index', '', '1', '1', 'C',
        '0', '0', '', 'process-definition', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11621', '流程实例', '11630', '1', 'processInstance', 'workflow/processInstance/index', '', '1', '1', 'C', '0',
        '0', '', 'tree-table', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11622', '流程分类', '11616', '1', 'category', 'workflow/category/index', '', '1', '0', 'C', '0', '0',
        'workflow:category:list', 'category', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11629', '我发起的', '11618', '1', 'myDocument', 'workflow/task/myDocument', '', '1', '1', 'C', '0', '0', '',
        'guide', 103, 1, sysdate(), NULL, NULL, '');
insert into sys_menu
values ('11630', '流程监控', '11616', '4', 'monitor', '', '', '1', '0', 'M', '0', '0', '', 'monitor', 103, 1, sysdate(),
        NULL, NULL, '');
insert into sys_menu
values ('11631', '待办任务', '11630', '2', 'allTaskWaiting', 'workflow/task/allTaskWaiting', '', '1', '1', 'C', '0',
        '0', '', 'waiting', 103, 1, sysdate(), NULL, NULL, '');

-- 流程分类管理相关按钮
insert into sys_menu values ('11623', '流程分类查询', '11622', '1', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:query', '#', 103, 1,sysdate(), null, null, '');
insert into sys_menu values ('11624', '流程分类新增', '11622', '2', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:add', '#', 103, 1,sysdate(), null, null, '');
insert into sys_menu values ('11625', '流程分类修改', '11622', '3', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:edit', '#', 103, 1,sysdate(), null, null, '');
insert into sys_menu values ('11626', '流程分类删除', '11622', '4', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:remove', '#', 103,1, sysdate(), null, null, '');
insert into sys_menu values ('11627', '流程分类导出', '11622', '5', '#', '', '', 1, 0, 'F', '0', '0', 'workflow:category:export', '#', 103,1, sysdate(), null, null, '');

INSERT INTO sys_dict_type(dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by,update_time, remark) VALUES (13, '000000', '业务状态', 'wf_business_status', 103, 1, sysdate(), NULL, NULL, '业务状态列表');
INSERT INTO sys_dict_type(dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by,update_time, remark) VALUES (14, '000000', '表单类型', 'wf_form_type', 103, 1, sysdate(), NULL, NULL, '表单类型列表');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (39, '000000', 1, '已撤销', 'cancel', 'wf_business_status', '', 'danger', 'N', 103, 1, sysdate(), NULL, NULL,'已撤销');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (40, '000000', 2, '草稿', 'draft', 'wf_business_status', '', 'info', 'N', 103, 1, sysdate(), NULL, NULL, '草稿');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (41, '000000', 3, '待审核', 'waiting', 'wf_business_status', '', 'primary', 'N', 103, 1, sysdate(), NULL, NULL,'待审核');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (42, '000000', 4, '已完成', 'finish', 'wf_business_status', '', 'success', 'N', 103, 1, sysdate(), NULL, NULL,'已完成');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (43, '000000', 5, '已作废', 'invalid', 'wf_business_status', '', 'danger', 'N', 103, 1, sysdate(), NULL, NULL,'已作废');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (44, '000000', 6, '已退回', 'back', 'wf_business_status', '', 'danger', 'N', 103, 1, sysdate(), NULL, NULL,'已退回');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (45, '000000', 7, '已终止', 'termination', 'wf_business_status', '', 'danger', 'N', 103, 1, sysdate(), NULL,NULL, '已终止');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (46, '000000', 1, '自定义表单', 'static', 'wf_form_type', '', 'success', 'N', 103, 1, sysdate(), NULL, NULL,'自定义表单');
INSERT INTO sys_dict_data(dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class,is_default, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (47, '000000', 2, '动态表单', 'dynamic', 'wf_form_type', '', 'primary', 'N', 103, 1, sysdate(), NULL, NULL,'动态表单');

-- ----------------------------
-- 6、请假单信息
-- ----------------------------
create table test_leave
(
    id          bigint(20)   not null comment 'id',
    tenant_id   varchar(20) default '000000' comment '租户编号',
    leave_type  varchar(255) not null comment '请假类型',
    start_date  datetime     not null comment '开始时间',
    end_date    datetime     not null comment '结束时间',
    leave_days  int(10)      not null comment '请假天数',
    remark      varchar(255) null comment '请假原因',
    status      varchar(255) null comment '状态',
    create_dept bigint       null comment '创建部门',
    create_by   bigint       null comment '创建者',
    create_time datetime     null comment '创建时间',
    update_by   bigint       null comment '更新者',
    update_time datetime     null comment '更新时间',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB COMMENT = '请假申请表';

INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11638, '请假申请', 5, 1, 'leave', 'workflow/leave/index', 1, 0, 'C', '0', '0', 'workflow:leave:list', '#', 103,1, sysdate(), NULL, NULL, '请假申请菜单');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11639, '请假申请查询', 11638, 1, '#', '', 1, 0, 'F', '0', '0', 'workflow:leave:query', '#', 103, 1, sysdate(),NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11640, '请假申请新增', 11638, 2, '#', '', 1, 0, 'F', '0', '0', 'workflow:leave:add', '#', 103, 1, sysdate(),NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11641, '请假申请修改', 11638, 3, '#', '', 1, 0, 'F', '0', '0', 'workflow:leave:edit', '#', 103, 1, sysdate(),NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11642, '请假申请删除', 11638, 4, '#', '', 1, 0, 'F', '0', '0', 'workflow:leave:remove', '#', 103, 1, sysdate(),NULL, NULL, '');
INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible,status, perms, icon, create_dept, create_by, create_time, update_by, update_time, remark) VALUES (11643, '请假申请导出', 11638, 5, '#', '', 1, 0, 'F', '0', '0', 'workflow:leave:export', '#', 103, 1, sysdate(),NULL, NULL, '');
