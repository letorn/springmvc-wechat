-- create database if not exists crawler character set utf8;
-- use crawler;

DROP TABLE IF EXISTS survey_topic;
DROP TABLE IF EXISTS survey_chapter;
DROP TABLE IF EXISTS survey_question;
DROP TABLE IF EXISTS survey_option;
DROP TABLE IF EXISTS survey_player;
DROP TABLE IF EXISTS survey_player_score;
DROP TABLE IF EXISTS survey_player_option;
DROP TABLE IF EXISTS survey_share_log;
DROP TABLE IF EXISTS survey_visit_log;

CREATE TABLE IF NOT EXISTS survey_topic(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  title VARCHAR(100),
  chapter_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS survey_chapter(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  title VARCHAR(100),
  question_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS survey_question(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  type TINYINT,
  text VARCHAR(100),
  option_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS survey_option(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  type TINYINT,
  text VARCHAR(1000),
  score TINYINT
);

CREATE TABLE IF NOT EXISTS survey_player(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  chatter_id BIGINT,
  topic_id INT,
  nickname VARCHAR(100) CHARACTER SET utf8mb4,
  gender TINYINT,
  head_img_url VARCHAR(200),
  create_date DATETIME,
  update_date DATETIME
);

CREATE TABLE IF NOT EXISTS survey_player_score(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_id BIGINT,
  player_id BIGINT,
  ptype TINYINT,
  topic_id INT,
  chapter_id INT,
  question_ids VARCHAR(1000),
  option_ids VARCHAR(1000),
  score INT,
  begin_date DATETIME,
  end_date DATETIME,
  create_date DATETIME
);

CREATE TABLE IF NOT EXISTS survey_player_option(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_id BIGINT,
  player_id BIGINT,
  ptype tinyint,
  topic_id INT,
  chapter_id INT,
  player_score_id BIGINT,
  question_id INT,
  option_ids VARCHAR(200),
  score INT,
  create_date DATETIME
);

CREATE TABLE IF NOT EXISTS survey_share_log(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_id BIGINT,
  player_id BIGINT,
  topic_id INT,
  page VARCHAR(100),
  target VARCHAR(100),
  create_date DATETIME
);

CREATE TABLE IF NOT EXISTS survey_visit_log(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_id BIGINT,
  player_id BIGINT,
  topic_id INT,
  page VARCHAR(100),
  create_date DATETIME
);