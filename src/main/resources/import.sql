insert into `account`(email, nickname, password, profile_img, email_verified) values("gildong@maver.com", "Gildong", "{bcrypt}$2a$00asdf", "https://user-images.githubusercontent.com/42775225/156348135-c44d1de2-c73f-49d9-accf-b0461f0cf740.png", 1);
insert into `account`(email, nickname, password, profile_img, email_verified) values("minsoo@maver.com", "minsoo", "{bcrypt}$2a$01efwf", "https://user-images.githubusercontent.com/42775225/156348148-0cbb89f1-6358-40b1-9172-df9f400c3f85.jpeg", 1);
insert into `account`(email, nickname, password, profile_img, email_verified) values("taeho@maver.com", "taeho", "{bcrypt}$2a$02ydutrj", "https://user-images.githubusercontent.com/42775225/156348156-d92c9d3f-b158-4268-bac4-4c3c9660b400.jpeg", 1);
insert into `account`(email, nickname, password, profile_img, email_verified) values("giri@maver.com", "giri", "{bcrypt}$2a$03jytkm", "https://user-images.githubusercontent.com/42775225/156348197-87907054-c438-4c7b-8337-55aaf00e3360.jpeg", 1);
insert into `account`(email, nickname, password, profile_img, email_verified) values("tomy@maver.com", "tomy", "{bcrypt}$2a$04vmbnym", "https://user-images.githubusercontent.com/42775225/156348207-d44b8a31-4d7b-4271-b8dd-4bc74c09d825.jpeg", 1);
insert into `account`(email, nickname, password, profile_img, email_verified) values("james@maver.com", "james", "{bcrypt}$2a$05gngnt", "https://user-images.githubusercontent.com/42775225/156348212-61b86fd0-9d1d-4a3e-b190-174b914a1171.jpeg", 1);

insert into `job`(name) values('개발자');
insert into `job`(name) values('디자이너');
insert into `job`(name) values('기획자');
insert into `job`(name) values('그 외');

insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '공프기 팀원을 모집합니다. 저희팀은 커피를 좋아하는 사람들을 위한 서비스를 기획하고 있습니다. 개발자가 필요합니다.', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', '[팀원모집] 공프기', 1);
insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '캡스톤을 시작으로 창업까지 이어갈 인원을 모집합니다. 자동차를 싸고 쉽게 구매하기 위한 서비스를 기획하고 있습니다. 디자이너와 개발자를 찾고 있습니다.', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', '캡스톤에서 창업까지', 2);
insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '인공지능 알고리즘을 적용한 서비스를 기획하고 있습니다. 아직까지 구체적인 기획안이 없으며, 기획부터 개발 운영까지 함께할 초기 멤버를 찾고 있습니다.', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', '인공지능 서비스 - 기획자 구함', 3);
insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '노래를 통해 세상을 치유하고 있는 사람들을 아시나요? 그런 사람들을 위한 무료 예약 시스템을 구축하고자 합니다.', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', '노래로 세상을 치유하는 팀', 4);
insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '어려운 전공 과목을 함께 이겨낼 수 있도록 스터디원을 모집할 수 있는 플랫폼을 만들고자 합니다. 010-1234-5678으로 연락주세요', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', '[팀원모집] 개발자 급구 - 스터디 서비스', 5);
insert into `post`(closed_dt, content, created_dt, is_recruiting, post_img, title, account_id) values(null, '과거 역사를 알아야 앞으로 같은 실수를 반복하지 않습니다. 저희는 역사를 좋아하는 사람들이 부담없이 만날 수 있는 서비스를 기획하고 있습니다.', '2022-01-12 00:00', true, 'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg', 'History Maker 초기멤버', 6);

insert into `job_post`(job_id, post_id) values(1, 1);
insert into `job_post`(job_id, post_id) values(1, 2);
insert into `job_post`(job_id, post_id) values(2, 2);
insert into `job_post`(job_id, post_id) values(1, 3);
insert into `job_post`(job_id, post_id) values(2, 3);
insert into `job_post`(job_id, post_id) values(3, 3);
insert into `job_post`(job_id, post_id) values(4, 3);
insert into `job_post`(job_id, post_id) values(2, 4);
insert into `job_post`(job_id, post_id) values(2, 5);
insert into `job_post`(job_id, post_id) values(3, 5);
insert into `job_post`(job_id, post_id) values(1, 6);
insert into `job_post`(job_id, post_id) values(2, 6);
insert into `job_post`(job_id, post_id) values(3, 6);

insert into `post_apply`(job_id, post_id, account_id) values(1, 1, 1);

insert into `comment`(id, created_dt, content, account_id, parent_id, post_id) values(1, '2021-01-11 22:00', "혹시 디자이너도 뽑나요?", 3, 1, 1);
insert into `comment`(id, created_dt, content, account_id, parent_id, post_id) values(2, '2021-01-11 22:01', "저도 궁금합니다.", 4, 2, 1);
insert into `comment`(id, created_dt, content, account_id, parent_id, post_id) values(3, '2021-01-11 22:02', "현재는 기획자와 개발자만 뽑고 있습니다.", 1, 1, 1);
insert into `comment`(id, created_dt, content, account_id, parent_id, post_id) values(4, '2021-01-11 22:03', "넵, 답변 감사합니다.", 3, 1, 1);
insert into `comment`(id, created_dt, content, account_id, parent_id, post_id) values(5, '2021-01-11 22:04', "위에 답변 달아두었습니다~!", 1, 2, 1);