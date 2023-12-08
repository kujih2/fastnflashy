create table booked_seat(
	schedule_num number not null,
	seat_id number not null,
	seat_col varchar2(1) not null,
	seat_row number not null,
	seat_status number default 1 not null,
	constraint booked_seat_fk foreign key (schedule_num) references match_schedule (schedule_num),
	constraint booked_seat_pk primary key (seat_id)
);
create table booked_info(
	booked_num number not null,
	booked_package number,
	seat_id number not null,
	user_num number not null,
	booked_regdate date default sysdate not null,
	booked_name varchar2(10) not null,
	booked_email varchar2(15) not null, 
	booked_ip varchar2(40) not null,
	booked_price number not null,
	constraint booked_info_pk primary key (booked_num),
	constraint booked_info_fk1 foreign key (seat_id) references booked_seat (seat_id),
	constraint booked_info_fk2 foreign key (user_num) references member (user_num)
);



