 --메거진 보드
create table magazin_board (
 mg_board_num number not null, -- 칼럼 게시글 고유번호
 mg_title varchar2(150) not null,
 sports_category number(9) default 0 not null, -- 축구:0,야구:1,배구:2,농구:3
 mg_content clob not null,
 mg_hit number(9) default 0 not null,
 mg_reg_date date default sysdate not null,
 mg_modify_date date,
 mg_filename varchar2(256) not null,
 mg_video varchar2(900),
 mg_ip varchar2(40) not null,
 mem_num number not null, -- 회원 고유번호
 constraint magazin_board_pk primary key (mg_board_num),
 constraint magazin_board_fk foreign key (mem_num) references member (mem_num)
);
create sequence magazin_seq;

-- 메거진 댓글
create table magazin_reply (
 mg_re_num number not null, -- 칼럼 댓글 고유번호
 mg_isdeleted number(1) default 0 not null, -- 댓글 삭제여부 0:기본값, 1:삭제
 mg_content varchar2(900) not null,
 mg_reg_date date default sysdate not null,
 mg_modify_date date,
 mg_re_ip varchar2(40) not null,
 mg_board_num number not null, -- 칼럼 게시글 고유번호
 mem_num number not null, -- 회원 고유번호
 constraint magazin_reply_pk primary key (mg_re_num),
 constraint magazin_reply_fk1 foreign key (mg_board_num) references magazin_board (mg_board_num),
 constraint magazin_reply_fk2 foreign key (mem_num) references member (mem_num)
);
create sequence magazin_reply_seq;

-- 매거진 대댓글(후순위)
create table magazin_sub_reply(
 mg_sub_re_num number not null,
 mg_sub_isdeleted number(1) not null,
 mg_sub_content varchar2(900) not null,
 mg_sub_reg_date date default sysdate not null,
 mg_sub_modify_date date,
 mg_sub_re_ip varchar2(40) not null,
 mg_re_num number not null, -- 칼럼 댓글 고유번호
 mem_num number not null, -- 회원 고유번호
 constraint magazin_sub_reply_pk primary key (mg_sub_re_num), 
 constraint magazin_sub_reply_fk1 foreign key (mg_re_num) references magazin_reply (mg_re_num),
 constraint magazin_sub_reply_fk2 foreign key (mem_num) references member (mem_num)
);
create sequence magazin_sub_reply_seq;

--칼럼 본문 반응 테이블
create table magazin_fav(
 mem_num number not null,
 mg_board_num not null,
 mg_fav_status number(5) default 0 not null, -- DEFAULT:0,공감:1, 후속:2, 추천:3, :4,흥미진진 :5:별로
 constraint magazin_fav_fk1 foreign key (mg_board_num) references magazin_board (mg_board_num),
 constraint magazin_fav_fk2 foreign key (mem_num) references member (mem_num)
);

--칼럼 댓글 좋아요:1, 싫어요:2 기본값:0
create table mg_reply_like (
 mem_num number not null,
 mg_re_num number not null,
 mg_like_status number(2) default 0 not null,
 constraint mg_reply_like_fk1 foreign key (mem_num) references member (mem_num),
 constraint mg_reply_like_fk2 foreign key (mg_re_num) references magazin_reply (mg_re_num)
);

--칼럼 대댓글 좋아요:1, 싫어요:2 기본값:0
create table mg_sub_reply_like (
 mem_num number not null,
 mg_sub_re_num number not null,
 mg_sub_like_status number(2) default 0 not null,
 constraint mg_sub_reply_like_fk1 foreign key (mem_num) references member (mem_num),
 constraint mg_sub_reply_like_fk2 foreign key (mg_sub_re_num) references magazin_sub_reply (mg_sub_re_num)
);


