crerate table Board(
	board_num number not null,
	title varchar2(150) not null,
	board_category number not null,
	content clob not null,
	hit number(9) default 0 not null,
	isdeleted number(1) default 0 not null,
	reg_date date default sysdate not null,
	modify_date date,
	filename varcharr2(150),
	ip varchar2(40) not null,
	mem_num number not null,
	constraint Board_pk primary key (board_num),
	constraint Board_fk foreign key (mem_num) references member (mem_num)
	
);