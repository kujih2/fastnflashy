--게시판
create table Board(
	board_num number not null,
	title varchar2(150) not null,
	board_category number not null,
	content clob not null,
	hit number(9) default 0 not null,
	isdeleted number(1) default 0 not null,--0이 삭제 안된것, 1은 삭제처리된 것
	reg_date date default sysdate not null,
	modify_date date,
	filename varchar2(150),
	ip varchar2(40) not null,
	mem_num number not null,
	constraint Board_pk primary key (board_num),
	constraint Board_fk foreign key (mem_num) references member (mem_num)
	
);

create sequence Board_seq;

--게시판 댓글
create table Board_Reply(
	re_num number not null,
	re_content varchar2(900) not null,
	re_isdeleted number(1) default 0 not null,--0이 삭제 안된것, 1은 삭제처리된 것
	re_date date default sysdate not null,
	re_modifydate date,
	re_ip varchar2(40) not null,
	board_num number not null,
	mem_num number not null,
	constraint Board_Reply_pk primary key (re_num),
	constraint Board_Reply_fk1 foreign key (board_num) references Board (board_num),
	constraint Board_Reply_fk2 foreign key (mem_num) references member (mem_num)
	
);

create sequence Board_Reply_seq;

--대댓글
create table Board_Sub_Reply(
	sub_reply_num number not null,
	sub_content varchar2(900) not null,
	sub_isdeleted number(1) default 0 not null,
	sub_date date default sysdate not null,
	sub_modifydate date,
	sub_ip varchar2(40) not null,
	re_num number not null,
	mem_num number not null,
	constraint Board_Sub_Reply_pk primary key (sub_reply_num),
	constraint Board_Sub_Reply_fk1 foreign key (re_num) references Board_Reply (re_num),
	constraint Board_Sub_Reply_fk2 foreign key (mem_num) references member (mem_num)
	
);

create sequence Board_Sub_Reply_seq;

-----------------------------------------------------------------------------

--게시판 좋아요
create table board_like(
	board_num number not null,
	mem_num number not null,
	like_status number(2) default 0 not null,--0이 기본, 1이 좋아요, 2가 싫어요
	constraint board_like_fk1 foreign key (board_num) references board (board_num),
	constraint board_like_fk2 foreign key (mem_num) references member (mem_num)
);
--댓글 좋아요
create table reply_like(
	re_num number not null,
	mem_num number not null,
	reply_like_status number(2) default 0 not null,--0이 기본, 1이 좋아요, 2가 싫어요
	constraint reply_like_fk1 foreign key (re_num) references board_reply (re_num),
	constraint reply_like_fk2 foreign key (mem_num) references member (mem_num)
);
--대댓글 좋아요
create table sub_reply_like(
	sub_reply_num number not null,
	mem_num number not null,
	sub_like_status number(2) default 0 not null,--0이 기본, 1이 좋아요, 2가 싫어요
	constraint sub_reply_like_fk1 foreign key (sub_reply_num) references board_sub_reply (sub_reply_num),
	constraint sub_reply_like_fk2 foreign key (mem_num) references member (mem_num)
);









































