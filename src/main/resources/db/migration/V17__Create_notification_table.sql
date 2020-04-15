create table notification
(
	id bigint auto_increment,
	senderId bigint not null COMMENT  '发送人Id',
	receiverId int not null COMMENT '接受人id（创建问题的人）',
	type int default 0 not null COMMENT '通知类型 0回复了评论 1回复了问题',
	status int not null COMMENT  '消息状态 0 未读 1 已读',
	gtm_create bigint not null,
	constraint notification_pk
		primary key (id)
);

