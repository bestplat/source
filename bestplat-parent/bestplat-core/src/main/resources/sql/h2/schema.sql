---------------------------------企业表（vssq_company）------------------------------------
drop table vssq_company if exists;
create table
    vssq_company
    (
        id_ char(32) not null,--主键
        name_ varchar(1024) not null,--名称
        other_name_ varchar(1024),--其它名称
        registered_address_ varchar(1024),--注册地址
        established_date_ date,--注册日期
        registered_capital_ varchar(256),--注册资金
        legal_person_ varchar(1024) not null,--法人代表
        address_ varchar(1024),--公司地址
        linkman_ varchar(64) not null,--联系人
        linkman_tel_ varchar(64) not null,--联系人电话
        linkman_email_ varchar(256) not null,--联系人邮件
        linkman_address_ varchar(1024),--联系人地址
        remark_ clob,--备注
        data_valid_status_ char(1) default '1' not null,--数据有效状态
        last_modified_time_ timestamp not null,--最后更新时间
        first_created_time_ timestamp not null,--首次创建时间
        version_ integer not null,--版本号
        primary key (id_)--设置主键
    );
create index  idx_company_name_ on vssq_company  (name_);
create index  idx_company_other_name_ on vssq_company  (other_name_);
create index  idx_company_registered_address_ on vssq_company  (registered_address_);
create index  idx_company_established_date_ on vssq_company  (established_date_);
create index  idx_company_registered_capital_ on vssq_company  (registered_capital_);
create index  idx_company_legal_person_ on vssq_company  (legal_person_);
create index  idx_company_address_ on vssq_company  (address_);
create index  idx_company_linkman_ on vssq_company  (linkman_);
create index  idx_company_last_modified_time_ on vssq_company  (last_modified_time_);


---------------------------------用户表（vssq_user）------------------------------------
drop table vssq_user if exists;
create table
    vssq_user
    (
        id_ char(32) not null,--主键
        company_ char(32) not null,--所属企业
        email_ varchar(256) not null,--邮件
        password_ varchar(256) not null,--密码（密文）
        salt_ varchar(64),--密码盐
        name_ varchar(1024) not null,--名称
        other_name_ varchar(1024),--其它名称
        gender_ char(1) not null,--性别
        tel_ varchar(16),--联系电话
        im_ varchar(16),--qq信息
        address_ varchar(1024),--地址
        birthday_ date,--生日
        remark_ clob,--备注
        data_valid_status_ char(1) default '1' not null,--数据有效状态
        last_modified_time_ timestamp not null,--最后更新时间
        first_created_time_ timestamp not null,--首次创建时间
        version_ integer not null,--版本号
        constraint fk_user_company foreign key (company_) references vssq_company (id_) on delete restrict,--设置和company的外键关联
        primary key (id_)--设置主键
    );
create unique index idx_user_email_ on vssq_user  (email_);
create index  idx_user_name_ on vssq_user  (name_);
create index  idx_user_other_name_ on vssq_user  (other_name_);
create index  idx_user_tel_ on vssq_user  (tel_);
create index  idx_user_im_ on vssq_user  (im_);
create index  idx_user_address_ on vssq_user  (address_);
create index  idx_user_birthday_ on vssq_user  (birthday_);

