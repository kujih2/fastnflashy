create table magazin_board (
 mg_board_num number not null,
 mg_title varchar2(150) not null,
 sports_category number(9) not null,
 mg_content clob not null,
 mg_hit number(9) not null,
 mg_reg_date date default sysdate not null,
 mg_modify_date date,
 mg_filename varchar2(256) not null,
 mg_video varchar2(900),
 mg_ip varchar2(40) not null,
 mem_num number not null,
);
create sequance maga
