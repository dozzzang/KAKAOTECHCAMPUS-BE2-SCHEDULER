USE schedule;
CREATE TABLE users
(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    username VARCHAR(100) NOT NULL COMMENT '작성자명',
    email VARCHAR(100) COMMENT '이메일',
    create_date DATETIME NOT NULL COMMENT '사용자 생성일',
    update_date DATETIME NOT NULL COMMENT '마지막 수정일'
);
CREATE TABLE schedule
(
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    content_todo VARCHAR(100) NOT NULL COMMENT '할일',
    create_date DATETIME NOT NULL COMMENT '작성일',
    update_date DATETIME NOT NULL COMMENT '수정일',
    user_id BIGINT NOT NULL COMMENT '작성자 식별자',
    foreign key (user_id) REFERENCES users(user_id)
);