-- CREATE DATABASE IF NOT EXISTS wechat DEFAULT CHARACTER SET utf8;
-- USE wechat;

DROP TABLE IF EXISTS wechat_admin;
DROP TABLE IF EXISTS wechat_menu;
DROP TABLE IF EXISTS wechat_chatter;

CREATE TABLE IF NOT EXISTS wechat_admin(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50),
  password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS wechat_menu(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50),
  type VARCHAR(30),
  value VARCHAR(100),
  status TINYINT,
  num int,
  parent_id INT
);

CREATE TABLE IF NOT EXISTS wechat_chatter(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  open_id VARCHAR(100),
  nickname VARCHAR(100) CHARACTER SET utf8mb4,
  sex TINYINT,
  language VARCHAR(50),
  country VARCHAR(50),
  province VARCHAR(50),
  city VARCHAR(50),
  head_img_url VARCHAR(200),
  subscribe VARCHAR(50),
  subscribe_time BIGINT,
  privileges VARCHAR(100),
  union_id VARCHAR(100),
  group_id VARCHAR(100),
  remark VARCHAR(1000),
  access_token VARCHAR(200),
  refresh_token VARCHAR(200),
  expires_in INT,
  scopes VARCHAR(50),
  create_date DATETIME,
  oauth_date DATETIME,
  update_date DATETIME,
  entry VARCHAR(50)
);