DROP TABLE IF EXISTS `job_post`;
DROP TABLE IF EXISTS `post_apply`;
DROP TABLE IF EXISTS `post_favorite`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `post_image_link`;
DROP TABLE IF EXISTS `user_feedback`;

CREATE TABLE `account`
(
    `id`                             bigint NOT NULL AUTO_INCREMENT,
    `email`                          varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `email_check_token`              varchar(8) COLLATE utf8mb4_unicode_ci   DEFAULT NULL,
    `email_check_token_generated_at` datetime                                DEFAULT NULL,
    `email_verified`                 bit(1)                                  DEFAULT NULL,
    `joined_at`                      datetime                                DEFAULT NULL,
    `nickname`                       varchar(50) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `password`                       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `profile_img`                    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `oauth_ci`                       varchar(50),
    `auth_type`                      varchar(20),
    `category_order`                 varchar(255),
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_s2a5omeaik0sruawqpvs18qfk` (`nickname`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `account` WRITE;
INSERT INTO `account` (`id`, `email`, `nickname`, `password`, `profile_img`, `email_verified`, `email_check_token`,
                       `email_check_token_generated_at`, `auth_type`, `joined_at`, `category_order`)
VALUES (1, 'gildong@maver.com', 'gildong', '{bcrypt}$2a$00asdf',
        'https://user-images.githubusercontent.com/42775225/156348135-c44d1de2-c73f-49d9-accf-b0461f0cf740.png',
        true, '14469777', '2022-01-02 13:22', 'local', '2022-01-02 13:22',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39'),

       (2, 'minsoo@maver.com', 'minsoo', '{bcrypt}$2a$01efwf',
        'https://user-images.githubusercontent.com/42775225/156348148-0cbb89f1-6358-40b1-9172-df9f400c3f85.jpeg',
        true, '11655789', '2022-03-09 07:08:08', 'local', '2022-03-09 07:08:08',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39'),

       (3, 'taeho@maver.com', 'taeho', '{bcrypt}$2a$02ydutrj',
        'https://user-images.githubusercontent.com/42775225/156348156-d92c9d3f-b158-4268-bac4-4c3c9660b400.jpeg',
        true, '14644556', '2022-01-03 13:22', 'local', '2022-01-03 13:22',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39'),

       (4, 'giri@maver.com', 'giri', '{bcrypt}$2a$03jytkm',
        'https://user-images.githubusercontent.com/42775225/156348197-87907054-c438-4c7b-8337-55aaf00e3360.jpeg',
        true, '35459037', '2022-02-02 13:22', 'local', '2022-02-02 13:32',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39'),

       (5, 'tomy@maver.com', 'tomy', '{bcrypt}$2a$04vmbnym',
        'https://user-images.githubusercontent.com/42775225/156348207-d44b8a31-4d7b-4271-b8dd-4bc74c09d825.jpeg',
        true, '91241682', '2022-01-02 13:22', 'local', '2022-01-02 13:22',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39'),

       (6, 'james@maver.com', 'james', '{bcrypt}$2a$05gngnt',
        'https://user-images.githubusercontent.com/42775225/156348212-61b86fd0-9d1d-4a3e-b190-174b914a1171.jpeg',
        true, '55860948', '2022-02-02 13:22', 'local', '2022-02-02 13:22',
        '1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39');
UNLOCK
TABLES;


CREATE TABLE `job`
(
    `job_id` bigint                                 NOT NULL AUTO_INCREMENT,
    `name`   varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `job` WRITE;
INSERT INTO `job`
VALUES (1, '개발자'),
       (2, '디자이너'),
       (3, '기획자'),
       (4, '그 외');
UNLOCK
TABLES;



CREATE TABLE `post`
(
    `id`                  bigint NOT NULL AUTO_INCREMENT,
    `created_dt`          datetime                                 DEFAULT NULL,
    `updated_dt`          datetime                                 DEFAULT NULL,
    `closed_dt`           datetime                                 DEFAULT NULL,
    `content`             varchar(2500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_recruiting`       bit(1)                                   DEFAULT NULL,
    `title`               varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `account_id`          bigint                                   DEFAULT NULL,
    `owner_contact_type`  varchar(20),
    `owner_contact_value` varchar(50),
    `recruit_total_cnt`   int,
    `recruit_current_cnt` int,
    `communication_tool`  varchar(20),
    `category_id`         bigint,
    `views`               int                                      DEFAULT 0,
    `likes`               int                                      DEFAULT 0,
    `location_url`        varchar(255)                             DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY                   `FKe5hjewhnd6trrdgt8i6uapkhy` (`account_id`),
    CONSTRAINT `FKe5hjewhnd6trrdgt8i6uapkhy` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `post` WRITE;
INSERT INTO `post` (`id`, `closed_dt`, `updated_dt`, `content`, `created_dt`, `is_recruiting`, `title`,
                    `account_id`, `owner_contact_type`, `owner_contact_value`, `recruit_total_cnt`,
                    `recruit_current_cnt`,
                    `communication_tool`, `category_id`)
VALUES (1, '2022-08-17 13:22', '2022-01-12 00:00', '공프기 팀원을 모집합니다. 저희팀은 커피를 좋아하는 사람들을 위한 서비스를 기획하고 있습니다. 개발자가 필요합니다.',
        '2022-01-12 00:00', true, '[팀원모집] 공프기', 1,
        'KAKAO_OPEN_CHAT', 'https://open.kakao.com/o/gsOnhywe', 4, 0, 'ONLINE', 2),
       (2, '2022-08-30 13:22', '2022-01-12 00:00',
        '캡스톤을 시작으로 창업까지 이어갈 인원을 모집합니다. 자동차를 싸고 쉽게 구매하기 위한 서비스를 기획하고 있습니다. 디자이너와 개발자를 찾고 있습니다.',
        '2022-01-12 00:00', true, '캡스톤에서 창업까지', 2,
        'GOOGLE_FORM', 'https://forms.gle/m9gs26R6GK51rHP66', 3, 0, 'ONLINE', 2),
       (3, '2022-09-02 13:22', '2022-01-12 00:00',
        '인공지능 알고리즘을 적용한 서비스를 기획하고 있습니다. 아직까지 구체적인 기획안이 없으며, 기획부터 개발 운영까지 함께할 초기 멤버를 찾고 있습니다.',
        '2022-01-12 00:00', true, '인공지능 서비스 - 기획자 구함', 3,
        'EMAIL', 'taeho@maver.com', 2, 1, 'OFFLINE', 2),
       (4, '2022-10-12 13:22', '2022-01-12 00:00', '노래를 통해 세상을 치유하고 있는 사람들을 아시나요? 그런 사람들을 위한 무료 예약 시스템을 구축하고자 합니다.',
        '2022-01-12 00:00', true, '노래로 세상을 치유하는 팀', 4,
        'PHONE', '010-1234-5678', 5, 3, 'ONLINE', 2),
       (5, '2022-11-12 13:22', '2022-01-12 00:00',
        '어려운 전공 과목을 함께 이겨낼 수 있도록 스터디원을 모집할 수 있는 플랫폼을 만들고자 합니다. 010-1234-5678으로 연락주세요',
        '2022-01-12 00:00', true, '[팀원모집] 개발자 급구 - 스터디 서비스', 5,
        'PHONE', '010-5678-1234', 2, 0, 'ONLINE', 2),
       (6, '2022-10-02 13:22', '2022-01-12 00:00',
        '과거 역사를 알아야 앞으로 같은 실수를 반복하지 않습니다. 저희는 역사를 좋아하는 사람들이 부담없이 만날 수 있는 서비스를 기획하고 있습니다.',
        '2022-01-12 00:00', true, 'History Maker 초기멤버', 6,
        'KAKAO_OPEN_CHAT', 'https://open.kakao.com/o/gsOnhywe', 4, 0, 'ONLINE', 2),
       (7, '2022-11-02 13:22', '2022-08-19 00:00',
        '공학 수학 어려우신 분들 다같이 과제도 하고, 중간 기말도 같이 공부합시다~~~ 만나서 진행할거구요!! 주 1회정도 생각하고 있습니다.',
        '2022-08-19 00:00', true, '공학 수학 스터디 구함', 6,
        'KAKAO_OPEN_CHAT', 'https://open.kakao.com/o/gsOnhywe', 3, 0, 'OFFLINE', 3),
       (8, '2022-10-12 10:22', '2022-08-19 10:00',
        '8월 26일 프로그래밍2 오픈세션을 열겠습니다. Q&A 시간이 있으니 궁금하신 내용을 들고 오시면 좋을것 같습니다.',
        '2022-08-19 10:00', true, '[TA] 프로그래밍2 오픈세션', 6,
        'KAKAO_OPEN_CHAT', 'https://open.kakao.com/o/gsOnhywe', 30, 0, 'OFFLINE', 3);
UNLOCK
TABLES;



CREATE TABLE `job_post`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `job_id`  bigint DEFAULT NULL,
    `post_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constJobPost` (`job_id`, `post_id`),
    KEY       `FKtnk3psdsu3qagjy3eaitcsqqh` (`post_id`),
    CONSTRAINT `FK28ivhoqnbkno4yjdoxqr9erx1` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`),
    CONSTRAINT `FKtnk3psdsu3qagjy3eaitcsqqh` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `job_post` WRITE;
INSERT INTO `job_post`
VALUES (1, 1, 1),
       (2, 1, 2),
       (4, 1, 3),
       (11, 1, 6),
       (3, 2, 2),
       (5, 2, 3),
       (8, 2, 4),
       (9, 2, 5),
       (12, 2, 6),
       (6, 3, 3),
       (10, 3, 5),
       (13, 3, 6),
       (7, 4, 3);
UNLOCK
TABLES;



CREATE TABLE `post_apply`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `account_id` bigint DEFAULT NULL,
    `job_id`     bigint DEFAULT NULL,
    `post_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constPostApply` (`post_id`, `account_id`, `job_id`),
    KEY          `FKj0eyusby5pii3a5b9n4hud0ee` (`account_id`),
    KEY          `FKpw6ekp82yubwt4ywaq3s5mu1l` (`job_id`),
    CONSTRAINT `FKd9vripurv4a148fn39u2yemq2` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    CONSTRAINT `FKj0eyusby5pii3a5b9n4hud0ee` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKpw6ekp82yubwt4ywaq3s5mu1l` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `post_apply` WRITE;
INSERT INTO `post_apply`
VALUES (1, 1, 1, 1);
UNLOCK
TABLES;



CREATE TABLE `post_favorite`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `account_id` bigint DEFAULT NULL,
    `post_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constPostFavorite` (`post_id`, `account_id`),
    KEY          `FKls6bgfyknw986o1qrnaulirvp` (`account_id`),
    CONSTRAINT `FKls6bgfyknw986o1qrnaulirvp` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKtnr54tuktg3welr2u950p0mqr` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `post_favorite` WRITE;
UNLOCK
TABLES;



CREATE TABLE `comment`
(
    `id`         bigint                                   NOT NULL AUTO_INCREMENT,
    `created_dt` datetime DEFAULT NULL,
    `updated_dt` datetime DEFAULT NULL,
    `content`    varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
    `account_id` bigint   DEFAULT NULL,
    `post_id`    bigint   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY          `FKp41h5al2ajp1q0u6ox3i68w61` (`account_id`),
    KEY          `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
    CONSTRAINT `FKp41h5al2ajp1q0u6ox3i68w61` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK
TABLES `comment` WRITE;
INSERT INTO `comment` (`created_dt`, `content`, `account_id`, `post_id`)
VALUES ('2021-01-11 22:00:00', '혹시 디자이너도 뽑나요?', 3, 1),
       ('2021-01-11 22:01:00', '저도 궁금합니다.', 4, 1),
       ('2021-01-11 22:02:00', '현재는 기획자와 개발자만 뽑고 있습니다.', 1, 1),
       ('2021-01-11 22:03:00', '넵, 답변 감사합니다.', 3, 1),
       ('2021-01-11 22:04:00', '위에 답변 달아두었습니다~!', 1, 1);
UNLOCK
TABLES;


CREATE TABLE `category`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `order`       int,
    `code`        varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_leaf`     varchar(1) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `parent_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `use_yn`      varchar(1) COLLATE utf8mb4_unicode_ci  DEFAULT 'Y',
    PRIMARY KEY (`id`)
);

LOCK
TABLES `category` WRITE;
INSERT INTO `category` (`order`, `code`, `name`, `is_leaf`, `parent_code`, `use_yn`)
VALUES (1, 'STUDY', '스터디', 'N', 'STUDY', 'Y'),
       (2, 'PROJECT', '프로젝트', 'Y', 'STUDY', 'Y'),
       (3, 'MAJOR', '전공공부', 'Y', 'STUDY', 'Y'),
       (4, 'RECRUIT', '리쿠르팅', 'Y', 'STUDY', 'Y'),
       (5, 'ETC-STUDY', '그 외', 'Y', 'STUDY', 'Y'),

       (6, 'FRIEND', '친목', 'N', 'FRIEND', 'Y'),
       (7, 'WALK', '산책', 'Y', 'FRIEND', 'Y'),
       (8, 'NIGHT', '야식모임', 'Y', 'FRIEND', 'Y'),
       (9, 'TEA', '티타임', 'Y', 'FRIEND', 'Y'),
       (10, 'DATING', '소개팅', 'Y', 'FRIEND', 'Y'),
       (11, 'ALCOHOL', '알코올', 'Y', 'FRIEND', 'Y'),
       (12, 'ETC-FRIEND', '그 외', 'Y', 'FRIEND', 'Y'),

       (13, 'TRANSPORT', '교통수단', 'N', 'TRANSPORT', 'Y'),
       (14, 'CARPOOL', '카풀', 'Y', 'TRANSPORT', 'Y'),
       (15, 'KTX', 'KTX 동반석', 'Y', 'TRANSPORT', 'Y'),
       (16, 'ETC-TRANSPORT', '그 외', 'Y', 'TRANSPORT', 'Y'),

       (17, 'USED', '중고거래', 'N', 'USED', 'Y'),
       (18, 'HOUSE', '양도', 'Y', 'USED', 'Y'),
       (19, 'TEXTBOOK', '전공책', 'Y', 'USED', 'Y'),
       (20, 'ETC-USED', '그 외', 'Y', 'USED', 'Y'),

       (21, 'EXERCISE', '운동', 'N', 'EXERCISE', 'Y'),
       (22, 'FUTSAL', '풋살', 'Y', 'EXERCISE', 'Y'),
       (23, 'PINGPONG', '탁구', 'Y', 'EXERCISE', 'Y'),
       (24, 'ETC-EXERCISE', '그 외', 'Y', 'EXERCISE', 'Y'),

       (25, 'PART-JOB', '알바', 'N', 'PART-JOB', 'Y'),
       (26, 'IN-SCHOOL', '교내', 'Y', 'PART-JOB', 'Y'),
       (27, 'OUT-SCHOOL', '교외', 'Y', 'PART-JOB', 'Y'),
       (28, 'ETC-PART-JOB', '그 외', 'Y', 'PART-JOB', 'Y'),

       (29, 'GAME', '게임', 'N', 'GAME', 'Y'),
       (30, 'BOARD', '보드게임', 'Y', 'GAME', 'Y'),
       (31, 'ROL', '롤', 'Y', 'GAME', 'Y'),
       (32, 'ETC-GAME', '그 외', 'Y', 'GAME', 'Y'),

       (33, 'FAITH', '신앙', 'N', 'FAITH', 'Y'),
       (34, 'PRAY', '기도모임', 'Y', 'FAITH', 'Y'),
       (35, 'ETC-FAITH', '그 외', 'Y', 'FAITH', 'Y'),

       (36, 'GROUP-BUY', '공동구매', 'N', 'GROUP-BUY', 'Y'),
       (37, 'STUFF', '물건', 'Y', 'GROUP-BUY', 'Y'),
       (38, 'OTT', 'OTT 구독', 'Y', 'GROUP-BUY', 'Y'),
       (39, 'ETC-GROUP-BUY', '그 외', 'Y', 'GROUP-BUY', 'Y');
UNLOCK
TABLES;

CREATE TABLE `post_image_link`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `created_dt` datetime,
    `updated_dt` datetime,
    `link`       varchar(255) not null,
    `post_id`    bigint,
    PRIMARY KEY (`id`)
);

LOCK
TABLES `post_image_link` WRITE;
INSERT INTO `post_image_link` (`created_dt`, `updated_dt`, `link`, `post_id`)
VALUES ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/160243121-da4843d4-74e9-428c-835d-0dd4e42713a6.png', 1),
       ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/185349176-6a5dd712-489b-46a6-93cb-a76460501167.png', 2),
       ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/160243201-ffd7bd3d-7d65-43bc-b382-e65c8a709762.png', 3),
       ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/160243238-e2749649-251f-4efa-9685-a90816f1c993.png', 4),
       ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/160243248-7a683e2e-855f-498f-ae42-0dc4d4f4e344.png', 5),
       ('2022-01-12 00:00', '2022-01-12 00:00',
        'https://user-images.githubusercontent.com/42775225/160243284-bdf5084e-3e5e-44fb-a1a9-c3cf791e5f59.png', 6);
UNLOCK
TABLES;

CREATE TABLE `user_feedback`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT,
    `created_dt`  datetime,
    `updated_dt`  datetime,
    `rating`      int,
    `description` varchar(2000) not null,
    PRIMARY KEY (`id`)
);

CREATE TABLE `account_role`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `created_dt` datetime,
    `updated_dt` datetime,
    `role_id`    int,
    `account_id` int,
    PRIMARY KEY (`id`)
);

CREATE TABLE `role`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `code` varchar(255) not null,
    `name` varchar(255) not null,
    PRIMARY KEY (`id`)
);

LOCK
TABLES `role` WRITE;
INSERT INTO `role` (`id`, `code`, `name`)
VALUES (1, 'ROLE_ADMIN', '운영자'),
       (2, 'ROLE_USER', '사용자'),
       (3, 'ROLE_MANAGER', '외부 매니저'),
       (4, 'ROLE_GUEST', '게스트');
UNLOCK
TABLES;

CREATE TABLE `student_auth`
(
    `id`               bigint  NOT NULL AUTO_INCREMENT,
    `account_id`       bigint  NOT NULL,
    `img_url`          varchar(255),
    `is_authenticated` boolean DEFAULT false,
    PRIMARY KEY (`id`),
    UNIQUE (`account_id`)
);

CREATE TABLE `blind_date`
(
    `id`                     bigint NOT NULL AUTO_INCREMENT,
    `created_dt`             datetime,
    `updated_dt`             datetime,
    `account_id`             bigint,
    `matching_blind_date_id` bigint  DEFAULT null,
    `season`                 int,
    `is_active`              boolean DEFAULT false,
    `is_matched`             boolean DEFAULT false,
    `name`                   varchar(32),
    `gender`                 varchar(32),
    `my_age`                 int,
    `favorite_age`           varchar(32),
    `date_style`             varchar(32),
    `hobby`                  varchar(32),
    `faith`                  varchar(32),
    `mbti`                   varchar(8),
    `smoke`                  varchar(8),
    `comment`                varchar(1000),
    `kakao_link`             varchar(128),
    PRIMARY KEY (`id`)
);

CREATE TABLE `exclude_cond`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_dt`    datetime,
    `updated_dt`    datetime,
    `blind_date_id` bigint,
    `name`          varchar(16),
    `department`    varchar(16),
    `student_id`    varchar(16),
    PRIMARY KEY (`id`)
);

CREATE TABLE `matching_history`
(
    `id`                     bigint NOT NULL AUTO_INCREMENT,
    `created_dt`             datetime,
    `updated_dt`             datetime,
    `blind_date_id`          bigint,
    `matching_blind_date_id` bigint,
    PRIMARY KEY (`id`)
);