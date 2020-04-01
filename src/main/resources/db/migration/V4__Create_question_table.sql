create table question
(
	id int auto_increment,
	title varchar(50),
	desc text,
	tag varchar(50),
	gmt_create bigint,
	gmt_modified bigint,
	user_id int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	constraint question_pk
		primary key (id)
);
