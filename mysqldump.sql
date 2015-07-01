-- 阿里云
mysqldump -h rdspud79f4bkmxdk2lel2.mysql.rds.aliyuncs.com -u ru49e128zb2r2f37 -p ru49e128zb2r2f37 wechat_admin wechat_menu wechat_chatter survey_topic survey_chapter survey_section survey_question survey_option survey_player survey_player_score survey_player_option survey_share_log survey_visit_log > wechat-1.0.sql
-- 公司
mysqldump -h 172.18.100.4 -u admin -p zcdh_uni wechat_admin wechat_menu wechat_chatter survey_topic survey_chapter survey_section survey_question survey_option survey_player survey_player_score survey_player_option survey_share_log survey_visit_log > wechat-1.0.sql


create database if not exists ru49e128zb2r2f37 character set utf8;

select * from survey_option