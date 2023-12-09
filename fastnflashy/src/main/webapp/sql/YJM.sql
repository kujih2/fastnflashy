create table member(
	mem_num number not null,
	mem_id varchar2(12) not null,
	mem_auth number not null,
	constraint member_pk primary key (mem_num)
);
create sequence member_seq;

create table member_detail(
	mem_num number not null,
	mem_name varchar2(30) not null,
	mem_pw varchar2(12) not null,
	mem_tel varchar2(15) not null,
	mem_email varchar2(50) not null,
	mem_zipcode varchar2(5) not null,
	mem_address1 varchar2(50) not null,
	mem_address2 varchar2(50) not null,
	mem_RegDate date default sysdate not null,
	mem_gender number not null,
	mem_balance number default 0,
	mem_subscription number,
	mem_photo varchar2(50),
	constraint member_detail_pk primary key(mem_num),
	constraint member_detail_fk foreign key(mem_num) references member(mem_num)
);