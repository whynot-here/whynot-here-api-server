insert into `user`(email, nickname, password, profile_img) values("test@naver.com", "Sangjin", "1234", "http://www.naver.com");

insert into `job`(name) values('개발자');
insert into `job`(name) values('디자이너');
insert into `job`(name) values('기획자');
insert into `job`(name) values('그 외');

insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, user_id) values('2022-01-12 00:00', '공프기 팀원을 모집합니다.', '2022-01-12 00:00', true, 'http://www.naver.com', '[팀원모집] 공프기', 1);

insert into `job_post`(job_id, post_id) values(1, 1);
insert into `job_post`(job_id, post_id) values(2, 1);

insert into `post_apply`(job_id, post_id, user_id) values(1, 1, 1);