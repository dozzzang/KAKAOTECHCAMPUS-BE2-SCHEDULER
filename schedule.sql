USE schedule;
CREATE TABLE schedule
(
  schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
  content_todo VARCHAR(100) NOT NULL COMMENT '할일',
  create_date DATETIME NOT NULL COMMENT '작성일',
  update_date DATETIME NOT NULL COMMENT '수정일',
  user_id BIGINT,
  foreign key (user_id) references user(user_id)
);
CREATE TABLE user(
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
  username VARCHAR(100) NOT NULL COMMENT '작성자명',
  password VARCHAR(100) NOT NULL COMMENT '비밀번호'
);