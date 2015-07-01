INSERT INTO wechat_admin(id,name,password) VALUES(1,'letorn','123456');

INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(1,'招聘会','dir',null,1,1,-1);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(2,'个人中心','dir',null,1,2,-1);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(3,'更多','dir',null,1,3,-1);

INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(4,'投简历','scancode_waitmsg','send',1,1,1);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(5,'面试结果','oauth_info','http://letorn.aliapp.com/jobfair/interviewResults.do',1,2,1);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(6,'进入招聘会','oauth_info','http://letorn.aliapp.com/jobfair/jobFair.do',1,3,1);

INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(7,'下载APP','view','http://www.joblbs.com/commons/download.jsp',1,1,2);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(8,'简历管理','oauth_info','http://letorn.aliapp.com/jobfair/resumeManage.do',1,2,2);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(9,'绑定或解绑','oauth_info','http://letorn.aliapp.com/jobfair/bindApp.do',1,3,2);

INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(10,'调查问卷','view','http://www.sojump.com/jq/5053053.aspx',1,1,3);
INSERT INTO wechat_menu(id,name,type,value,status,num,parent_id) VALUES(11,'静静与超超','oauth_info','http://letorn.aliapp.com/egame/index.do',1,2,3);