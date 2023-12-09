--팀 정보
create table match_team(
 team_num number not null primary key,--팀번호
team_category number not null, --경기 종목(축구:0,야구:1,배구:2,농구:3)
 team_name varchar2(25) not null,--팀의 이름
 team_win number default 0,--팀의 승리 수
 team_lose number default 0,--팀의 패배 수
 team_draw number default 0,--팀의 무승부 수
 team_photo varchar2(30),--팀의 마크(사진)
 team_odds number default 0 --팀의 승률
);
create sequence team_seq;

--경기 일정
create table match_schedule(
 schedule_num number not null primary key, --경기 번호
 team_category number not null, --경기 종목(축구:0,야구:1,배구:2,농구:3)
 schedule_start varchar2(30) not null, --경기 시작일(년/월/일/시/분)
 schedule_end varchar2(30) not null, --경기 종료일(년/월/일/시/분)
 schedule_status number not null, --경기현황 (0:종료,1:진행중,2:예정)
 schedule_team1 number not null, --경기하는 팀번호 1
 schedule_team2 number not null --경기하는 팀번호 2
);
create sequence schedule_seq;
--경기 결과
create table match_result(
 result_num number not null primary key,--경기결과 식별번호
 schedule_num number not null, --경기 번호
 result_team1Score number not null, --팀1의 점수
 result_team2Score number  not null, --팀2의 점수
 result_match number not null, --경기 결과
 constraint match_result_fk 
                foreign key (schedule_num) references match_schedule (schedule_num)
);
create sequence result_seq;
--선수 정보
create table match_player(
 player_num number not null primary key,--선수 번호
 team_num number not null,--소속 팀 번호
 player_name varchar(25) not null,--선수 이름
 player_backNum number not null,--선수 등번호
 player_height number not null,--선수 신장
 player_photo varchar2(30) not null,--선수 프로필 사진
 player_position varchar2(20) not null,--선수 포지션
 player_age number not null,--선수 나이
 constraint match_player_fk 
                foreign key (team_num) references match_team (team_num)
);
create sequence player_seq;
--경기장 정보(종목,좌석)
create table match_stadium(
 team_category number not null primary key, --경기 종목
 seat_count number not null  --좌석수
);
