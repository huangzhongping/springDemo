create table comment
(
	id int auto_increment,
	parent_id bigint not null,
	type int not null,
	content varchar(1024),
	like_count bigint default 0 not null,
	commentor bigint not null,
	gmt_create long not null,
	gmt_modified long,
	constraint comment_pk
		primary key (id)
);

