--插入VSSQ_COMPANY表的数据
INSERT INTO VSSQ_COMPANY (ID_, CREATED_TIME_, MODIFIED_TIME_, VALID_STATUS_, VERSION_, ADDRESS_, ESTABLISHED_DATE_, LEGAL_PERSON_, LINKMAN_, LINKMAN_ADDRESS_, LINKMAN_EMAIL_, LINKMAN_TEL_, NAME_, OTHER_NAME_, REGISTERED_ADDRESS_, REGISTERED_CAPITAL_, REMARK_) VALUES ('52a74b05a619429a9ee9b4629a77b1dd', '2013-09-11 01:15:43', '2013-09-11 01:15:43', '1', 0, null, null, 'lujijiang', '卢吉江', null, 'lujijiang@gmail.com', '13922437060', 'testCompany', null, null, null, null);
--插入VSSQ_USER表的数据
INSERT INTO VSSQ_USER (ID_, CREATED_TIME_, MODIFIED_TIME_, VALID_STATUS_, VERSION_, ADDRESS_, BIRTHDAY_, EMAIL_, GENDER_, IM_, NAME_, OTHER_NAME_, PASSWORD_, REMARK_, SALT_, TEL_, COMPANY_) VALUES ('2a0b292f2e9d432787cd2fee75678b8c', '2013-09-11 01:15:43', '2013-09-11 01:15:43', '1', 0, null, null, 'test@gmail.com', '1', null, 'test', null, '7e1411009cce32a01f48e5b40e1285273d10103e0c4f2798d77750e5330f6830', null, '99148fcf3db3f293', null, '52a74b05a619429a9ee9b4629a77b1dd');