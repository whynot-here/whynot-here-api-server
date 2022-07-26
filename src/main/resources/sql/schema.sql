DROP TABLE IF EXISTS `job_post`;
DROP TABLE IF EXISTS `post_apply`;
DROP TABLE IF EXISTS `post_favorite`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `account`;


CREATE TABLE `account`
(
    `id`                             bigint NOT NULL AUTO_INCREMENT,
    `email`                          varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `email_check_token`              varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `email_check_token_generated_at` datetime                                DEFAULT NULL,
    `email_verified`                 bit(1)                                  DEFAULT NULL,
    `joined_at`                      datetime                                DEFAULT NULL,
    `nickname`                       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `password`                       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `profile_img`                    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `auth_type`                      varchar(255),
    PRIMARY KEY (`id`)
    UNIQUE KEY `UK_q0uja26qgu1atulenwup9rxyr` (`email`),
    UNIQUE KEY `UK_s2a5omeaik0sruawqpvs18qfk` (`nickname`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `account` WRITE;
INSERT INTO `account`
VALUES (1, 'gildong@maver.com', NULL, NULL, _binary '', NULL, 'Gildong', '{bcrypt}$2a$00asdf',
        'https://user-images.githubusercontent.com/42775225/156348135-c44d1de2-c73f-49d9-accf-b0461f0cf740.png',
        'local'),
       (2, 'whynot-user@email.com', NULL, NULL, _binary '', '2022-03-09 07:08:08', 'whynot-user',
        '{bcrypt}$2a$10$xXkjud45CmBA/rOW0B7rteonLeuejhZBaJklcbJ6gwL5eWI1VTbqe', NULL, 'local'),
       (3, 'taeho@maver.com', NULL, NULL, _binary '', NULL, 'taeho', '{bcrypt}$2a$02ydutrj',
        'https://user-images.githubusercontent.com/42775225/156348156-d92c9d3f-b158-4268-bac4-4c3c9660b400.jpeg',
        'local'),
       (4, 'giri@maver.com', NULL, NULL, _binary '', NULL, 'giri', '{bcrypt}$2a$03jytkm',
        'https://user-images.githubusercontent.com/42775225/156348197-87907054-c438-4c7b-8337-55aaf00e3360.jpeg',
        'local'),
       (5, 'tomy@maver.com', NULL, NULL, _binary '', NULL, 'tomy', '{bcrypt}$2a$04vmbnym',
        'https://user-images.githubusercontent.com/42775225/156348207-d44b8a31-4d7b-4271-b8dd-4bc74c09d825.jpeg',
        'local'),
       (6, 'james@maver.com', NULL, NULL, _binary '', NULL, 'james', '{bcrypt}$2a$05gngnt',
        'https://user-images.githubusercontent.com/42775225/156348212-61b86fd0-9d1d-4a3e-b190-174b914a1171.jpeg',
        'local');
UNLOCK TABLES;



CREATE TABLE `job`
(
    `job_id` bigint                                  NOT NULL AUTO_INCREMENT,
    `name`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`job_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `job` WRITE;
INSERT INTO `job`
VALUES (1, '개발자'),
       (2, '디자이너'),
       (3, '기획자'),
       (4, '그 외');
UNLOCK TABLES;



CREATE TABLE `post`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `created_dt`    datetime                                 DEFAULT NULL,
    `updated_dt`    datetime                                 DEFAULT NULL,
    `closed_dt`     datetime                                 DEFAULT NULL,
    `content`       varchar(2500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_recruiting` bit(1)                                   DEFAULT NULL,
    `post_img`      varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `title`         varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `account_id`    bigint                                   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKe5hjewhnd6trrdgt8i6uapkhy` (`account_id`),
    CONSTRAINT `FKe5hjewhnd6trrdgt8i6uapkhy` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `post` WRITE;
INSERT INTO `post`
VALUES (1, '2022-01-12 00:00:00', NULL, NULL, '공프기 팀원을 모집합니다. 저희팀은 커피를 좋아하는 사람들을 위한 서비스를 기획하고 있습니다. 개발자가 필요합니다.',
        _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        '[팀원모집] 공프기', 1),
       (2, '2022-01-12 00:00:00', NULL, NULL,
        '캡스톤을 시작으로 창업까지 이어갈 인원을 모집합니다. 자동차를 싸고 쉽게 구매하기 위한 서비스를 기획하고 있습니다. 디자이너와 개발자를 찾고 있습니다.', _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        '캡스톤에서 창업까지', 2),
       (3, '2022-01-12 00:00:00', NULL, NULL,
        '인공지능 알고리즘을 적용한 서비스를 기획하고 있습니다. 아직까지 구체적인 기획안이 없으며, 기획부터 개발 운영까지 함께할 초기 멤버를 찾고 있습니다.', _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        '인공지능 서비스 - 기획자 구함', 3),
       (4, '2022-01-12 00:00:00', NULL, NULL, '노래를 통해 세상을 치유하고 있는 사람들을 아시나요? 그런 사람들을 위한 무료 예약 시스템을 구축하고자 합니다.',
        _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        '노래로 세상을 치유하는 팀', 4),
       (5, '2022-01-12 00:00:00', NULL, NULL,
        '어려운 전공 과목을 함께 이겨낼 수 있도록 스터디원을 모집할 수 있는 플랫폼을 만들고자 합니다. 010-1234-5678으로 연락주세요', _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        '[팀원모집] 개발자 급구 - 스터디 서비스', 5),
       (6, '2022-01-12 00:00:00', NULL, NULL,
        '과거 역사를 알아야 앞으로 같은 실수를 반복하지 않습니다. 저희는 역사를 좋아하는 사람들이 부담없이 만날 수 있는 서비스를 기획하고 있습니다.', _binary '',
        'https://user-images.githubusercontent.com/42775225/156349826-a00d9152-fb83-4e90-8267-2e4bdb836f21.jpeg',
        'History Maker 초기멤버', 6);
UNLOCK TABLES;



CREATE TABLE `job_post`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `job_id`  bigint DEFAULT NULL,
    `post_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constJobPost` (`job_id`, `post_id`),
    KEY `FKtnk3psdsu3qagjy3eaitcsqqh` (`post_id`),
    CONSTRAINT `FK28ivhoqnbkno4yjdoxqr9erx1` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`),
    CONSTRAINT `FKtnk3psdsu3qagjy3eaitcsqqh` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `job_post` WRITE;
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
UNLOCK TABLES;



CREATE TABLE `post_apply`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `account_id` bigint DEFAULT NULL,
    `job_id`     bigint DEFAULT NULL,
    `post_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constPostApply` (`post_id`, `account_id`, `job_id`),
    KEY `FKj0eyusby5pii3a5b9n4hud0ee` (`account_id`),
    KEY `FKpw6ekp82yubwt4ywaq3s5mu1l` (`job_id`),
    CONSTRAINT `FKd9vripurv4a148fn39u2yemq2` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
    CONSTRAINT `FKj0eyusby5pii3a5b9n4hud0ee` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKpw6ekp82yubwt4ywaq3s5mu1l` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `post_apply` WRITE;
INSERT INTO `post_apply`
VALUES (1, 1, 1, 1);
UNLOCK TABLES;



CREATE TABLE `post_favorite`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `account_id` bigint DEFAULT NULL,
    `post_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `constPostFavorite` (`post_id`, `account_id`),
    KEY `FKls6bgfyknw986o1qrnaulirvp` (`account_id`),
    CONSTRAINT `FKls6bgfyknw986o1qrnaulirvp` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKtnr54tuktg3welr2u950p0mqr` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `post_favorite` WRITE;
UNLOCK TABLES;



CREATE TABLE `comment`
(
    `id`         bigint                                  NOT NULL AUTO_INCREMENT,
    `created_dt` datetime DEFAULT NULL,
    `updated_dt` datetime DEFAULT NULL,
    `content`    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `account_id` bigint   DEFAULT NULL,
    `post_id`    bigint   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKp41h5al2ajp1q0u6ox3i68w61` (`account_id`),
    KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
    CONSTRAINT `FKp41h5al2ajp1q0u6ox3i68w61` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
    CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

LOCK TABLES `comment` WRITE;
INSERT INTO `comment`
VALUES (1, '2021-01-11 22:00:00', NULL, '혹시 디자이너도 뽑나요?', 3, 1),
       (2, '2021-01-11 22:01:00', NULL, '저도 궁금합니다.', 4, 1),
       (3, '2021-01-11 22:02:00', NULL, '현재는 기획자와 개발자만 뽑고 있습니다.', 1, 1),
       (4, '2021-01-11 22:03:00', NULL, '넵, 답변 감사합니다.', 3, 1),
       (5, '2021-01-11 22:04:00', NULL, '위에 답변 달아두었습니다~!', 1, 1);
UNLOCK TABLES;