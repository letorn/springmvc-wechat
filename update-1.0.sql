DROP TABLE IF EXISTS survey_topic;
CREATE TABLE IF NOT EXISTS survey_topic(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  title VARCHAR(100),
  chapter_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

DROP TABLE IF EXISTS survey_chapter;
CREATE TABLE IF NOT EXISTS survey_chapter(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  title VARCHAR(100),
  question_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

DROP TABLE IF EXISTS survey_question;
CREATE TABLE IF NOT EXISTS survey_question(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  type TINYINT,
  text VARCHAR(100),
  option_ids VARCHAR(1000),
  remark VARCHAR(1000)
);

DROP TABLE IF EXISTS survey_option;
CREATE TABLE IF NOT EXISTS survey_option(
  id INT PRIMARY KEY AUTO_INCREMENT,
  num TINYINT,
  type TINYINT,
  text VARCHAR(1000),
  score TINYINT
);

INSERT INTO survey_topic(id,num,title,chapter_ids,remark) VALUES(1,1,'我想静静','[1,2,3]','超超与静静');

INSERT INTO survey_chapter(id,num,title,question_ids,remark) VALUES(1,1,'搭讪 - 要电话','[1,2,3,4,5]','跟陌生人搭讪，被拒绝是不可避免的，要学会坦然面对被拒。有时候直接表明来意就是一种发自内心的赞美，比那些浮夸的搭讪技巧更能让对方感受到你的真诚。摒弃一切多余的假话和废话，多用有趣的方式说真话。“ 我也不知道为什么想认识你， 脑袋一热就跑过来了……”调侃自己脸皮厚、脑袋热，绝对比夸对方气质好更接近实情，也更能让对方接受。另外，既然你都选择踏出搭讪这一步了，何不带上不成功便成仁的自信呢。');
INSERT INTO survey_chapter(id,num,title,question_ids,remark) VALUES(2,2,'陌生人 - 朋友','[6,7,8,9,10]','第一次见面的约会就连博士如我都会搞砸，何况蠢蛋如你呢!<br/>第一次约会最重要的是什么？一个好的印象和以后继续发展的可能性。错误最少的人就是最后的胜者，你的对手不是她，而是那些跟你一样追求她的男生，以及你自己。真诚、清晰和完整的表达自己的想法和心意，是第二个阶段成功的关键。尽量延长交流时间，因为只有这样才有机会表现出你的与众不同的地方，至少也可以让她对你增加一些熟悉感。');
INSERT INTO survey_chapter(id,num,title,question_ids,remark) VALUES(3,3,'朋友 - 男女朋友','[11,12,13,14,15]','情商不足还不想充值掏口袋，和口袋干瘪还不想动脑子的人，滚回去吧。<br/>在追求的过程中，由于求成心切，往往会盲目制造好感，而疏于判断对方的心意，忽视了表白的恰当时机。还有，表白一次是不够的少年！多真诚地表达你对女神的好感，女神就会慢慢接受你啦。记得，表白和搭讪是不同的，适度的技巧运用会让你事半功倍哦。真诚不失风趣，直白说爱的人总会追到女神的！');

INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(1,1,1,'超超应该怎样和静静搭讪呢？','[1,2,3,4]','超超刚刚下课，路过学校的书店发现爱慕已久的女神静静，一个人在看书。<br/>超超鼓起勇气！准备让静静认识一下自己...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(2,2,1,'超超要怎么回复？','[5,6,7,8]','对于这种校园级女神显然经常被搭讪，且看超超如何表现...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(3,3,1,'超超要如何说，才能化解尴尬？','[9,10,11,12]','超超想坐下和静静聊天，可由于太过激动没坐稳！！！悲剧发生了...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(4,4,1,'怎样才能留个联系方式呢？','[13,14,15,16]','超超和女神静静聊得很投机...<br/>欢乐时间貌似过得很快，超超感觉静静要离开了...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(5,5,1,'超超要怎么做？','[17,18,19,20]','超超和静静准备离开，和静静聊了这么久，看来很有希望...<br/>而旁边那些不敢向前打招呼的同学，只能眼巴巴地看着他们....');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(6,1,1,'超超要用什么方法约静静呢？','[21,22,23,24]','超超回到宿舍十分兴奋，他真的没有想到自己能要到静静的电话...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(7,2,1,'第一次约女神，超超应该约在什么地方？','[25,26,27,28]','约会女神一同度过周末，一切都会顺利吗...<br/>关键是约在什么地方见面...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(8,3,1,'超超其实不喜欢看喜剧要怎么回答呢？','[29,30,31,32]','逛了一整天，两人找了一间咖啡厅坐下来...<br/>静静向超超聊起了她喜欢看的电影...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(9,4,1,'其实对电脑一窍不通的他该怎么做？','[33,34,35,36]','对于第一次约会，男孩子都喜欢表现得酷酷滴...<br/>总喜欢在女生面前表现得什么都会...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(10,5,1,'女神买裙子求意见，应该怎么回复？','[37,38,39,40]','渐渐地，两人成为了很好的朋友，静静也开始习惯不管什么事都喜欢问他...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(11,1,1,'怎样的回答说明静静对超超有好感呢？','[41,42,43,44]','和静静也认识一段时间了，超超也不知道静静对自己是否有感觉。这时，他的朋友给出了一招...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(12,2,1,'超超要如何回答呢？','[45,46,47,48]','参加完社团活动，两人并肩走在校园里...<br/>静静困惑为什么那么多男生喜欢她！');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(13,3,1,'超超要怎么回应呢？','[49,50,51,52]','四月中，樱花开了。超超提出和静静一起去骑车游玩...<br/>可是静静以前因为骑单车受过伤...');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(14,4,1,'静静怎样的暗示，超超可以表白？','[53,54,55,56]','随着时间的推移，两人的关系一直没得到进展...<br/>超超非常困惑要不要表白！');
INSERT INTO survey_question(id,num,type,text,option_ids,remark) VALUES(15,5,1,'超超应该怎样表白呢？','[57,58,59,60]','在朋友的鼓励下，超超决定孤注一掷，决定向静静表白...');

INSERT INTO survey_option(id,num,type,text,score) VALUES(1,1,1,'可以做个朋友吗？',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(2,2,1,'你好，我是超超，我想认识你。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(3,3,1,'你也看这本书啊?',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(4,4,1,'请问你有看到《哈利波特》这本书吗？',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(5,1,1,'不为什么，就是很想认识你。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(6,2,1,'他们都想认识你，只是我有勇气就来了。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(7,3,1,'我关注你很久了，希望和你认识。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(8,4,1,'我真的不是坏人，就是想和你认识。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(9,1,1,'自嘲一下：“我看起来太傻了！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(10,2,1,'赶快换一个话题, “你知道***事吗？”',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(11,3,1,'你今天好美，都把我电晕了。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(12,4,1,'没事没事，不用担心。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(13,1,1,'留个电话吧，以后可以联络！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(14,2,1,'拍合照，借机向女神要电话。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(15,3,1,'找一个话题，然后说传资料给她。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(16,4,1,'我可以留一个你的联系方式吗？',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(17,1,1,'你要去哪里呢，我陪你去吧。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(18,2,1,'反正也没什么事情，送你到公交站吧！',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(19,3,1,'好吧，我的朋友也在等我，先走了。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(20,4,1,'别忘了我。下次见。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(21,1,1,'直接打电话邀约？',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(22,2,1,'发短信：“明天有空一起出来玩吧？”',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(23,3,1,'发短信：“我是超超，很高兴认识你。”',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(24,4,1,'先聊一些无关的事情，等待时机再邀约',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(25,1,1,'去看最近比较热门的东西。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(26,2,1,'看静静喜欢的是什么。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(27,3,1,'看超超擅长什么。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(28,4,1,'两个人的共同爱好。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(29,1,1,'还好吧。其实还有一部XX电影还不错。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(30,2,1,'跟我讲讲，你为什么喜欢他演的电影！',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(31,3,1,'对静静所喜欢的人和事大加赞赏！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(32,4,1,'不好意思，我不是很了解。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(33,1,1,'直接告诉静静其实刚刚吹牛了。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(34,2,1,'隐瞒下去，赶快去学电脑知识。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(35,3,1,'装机最重要，请会电脑的朋友去帮忙。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(36,4,1,'说自己最近很忙没有时间，下次再说吧。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(37,1,1,'我陪你去买吧！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(38,2,1,'再考虑下，过两天说不定打折呢！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(39,3,1,'什么样子的啊，求照片啊！',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(40,4,1,'随便啊，你喜欢就好！',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(41,1,1,'真的吗? 太巧了，我最喜欢轮滑了。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(42,2,1,'好啊，我正发愁这周末要做什么呢。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(43,3,1,'我不会滑，到时我看你滑就好了。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(44,4,1,'轮滑好危险的，你要保护好自己。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(45,1,1,'你长得漂亮喽！',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(46,2,1,'你性格好。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(47,3,1,'你哪里都好！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(48,4,1,'不知道，觉得和你一起很开心。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(49,1,1,'每个人都有弱点，没关系的。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(50,2,1,'伤的重不重，现在还好吗？',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(51,3,1,'讲一件自己也很衰的事情。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(52,4,1,'我来教你吧，我会保护你的。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(53,1,1,'给静静发短信打电话都很快回复。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(54,2,1,'静静：别人总问我和你到底是什么关系。',20);
INSERT INTO survey_option(id,num,type,text,score) VALUES(55,3,1,'静静对超超有主动的身体接触。',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(56,4,1,'静静总和超超讨论人生，还有情感困惑。',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(57,1,1,'叫上所有朋友，然后在操场上放烟火！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(58,2,1,'单独约静静出来，对她说：我喜欢你！',10);
INSERT INTO survey_option(id,num,type,text,score) VALUES(59,3,1,'发短信：“静静，做我女朋友吧。”',0);
INSERT INTO survey_option(id,num,type,text,score) VALUES(60,4,1,'送静静最喜欢的礼物，里边夹一封情书。',20);

DROP TABLE IF EXISTS survey_player_score2;
DROP TABLE IF EXISTS survey_player_option2;
ALTER TABLE survey_player_score RENAME TO survey_player_score2;
ALTER TABLE survey_player_option RENAME TO survey_player_option2;

DROP TABLE IF EXISTS survey_player_score;
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

DROP TABLE IF EXISTS survey_player_option;
CREATE TABLE IF NOT EXISTS survey_player_option(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  owner_id BIGINT,
  player_id BIGINT,
  ptype TINYINT,
  topic_id INT,
  chapter_id INT,
  player_score_id BIGINT,
  question_id INT,
  option_ids VARCHAR(200),
  score INT,
  create_date DATETIME
);

DELIMITER $$
DROP FUNCTION IF EXISTS to_score $$
CREATE FUNCTION to_score(owner_id BIGINT,player_id BIGINT,ptype TINYINT,topic_id INT,option_ids VARCHAR(1000),create_date DATETIME) RETURNS INT
BEGIN
  DECLARE opt1 INT;
  DECLARE opt2 INT;
  DECLARE opt3 INT;
  DECLARE opt4 INT;
  DECLARE opt5 INT;
  DECLARE opt6 INT;
  DECLARE opt7 INT;
  DECLARE opt8 INT;
  DECLARE opt9 INT;
  DECLARE opt10 INT;
  DECLARE opt11 INT;
  DECLARE opt12 INT;
  DECLARE opt13 INT;
  DECLARE opt14 INT;
  DECLARE opt15 INT;
  
  SET opt1 = CONVERT(SUBSTRING(option_ids,1,LENGTH(SUBSTRING_INDEX(option_ids,',',1))),UNSIGNED);
  SET opt2 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',1))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',2))-LENGTH(SUBSTRING_INDEX(option_ids,',',1))-1),UNSIGNED);
  SET opt3 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',2))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',3))-LENGTH(SUBSTRING_INDEX(option_ids,',',2))-1),UNSIGNED);
  SET opt4 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',3))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',4))-LENGTH(SUBSTRING_INDEX(option_ids,',',3))-1),UNSIGNED);
  SET opt5 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',4))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',5))-LENGTH(SUBSTRING_INDEX(option_ids,',',4))-1),UNSIGNED);
  SET opt6 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',5))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',6))-LENGTH(SUBSTRING_INDEX(option_ids,',',5))-1),UNSIGNED);
  SET opt7 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',6))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',7))-LENGTH(SUBSTRING_INDEX(option_ids,',',6))-1),UNSIGNED);
  SET opt8 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',7))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',8))-LENGTH(SUBSTRING_INDEX(option_ids,',',7))-1),UNSIGNED);
  SET opt9 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',8))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',9))-LENGTH(SUBSTRING_INDEX(option_ids,',',8))-1),UNSIGNED);
  SET opt10 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',9))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',10))-LENGTH(SUBSTRING_INDEX(option_ids,',',9))-1),UNSIGNED);
  SET opt11 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',10))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',11))-LENGTH(SUBSTRING_INDEX(option_ids,',',10))-1),UNSIGNED);
  SET opt12 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',11))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',12))-LENGTH(SUBSTRING_INDEX(option_ids,',',11))-1),UNSIGNED);
  SET opt13 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',12))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',13))-LENGTH(SUBSTRING_INDEX(option_ids,',',12))-1),UNSIGNED);
  SET opt14 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',13))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',14))-LENGTH(SUBSTRING_INDEX(option_ids,',',13))-1),UNSIGNED);
  SET opt15 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',14))+2,LENGTH(option_ids)-LENGTH(SUBSTRING_INDEX(option_ids,',',14))-1),UNSIGNED);
  
  SELECT SUM(score) INTO @score1 FROM survey_option WHERE id IN(opt1,opt2,opt3,opt4,opt5);
  SELECT SUM(score) INTO @score2 FROM survey_option WHERE id IN(opt6,opt7,opt8,opt9,opt10);
  SELECT SUM(score) INTO @score3 FROM survey_option WHERE id IN(opt11,opt12,opt13,opt14,opt15);
  
  INSERT INTO survey_player_score (owner_id,player_id,ptype,topic_id,chapter_id,question_ids,option_ids,score,begin_date,end_date,create_date) VALUES(owner_id,player_id,ptype,topic_id,1,'[1,2,3,4,5]',CONCAT('[',opt1,',',opt2,',',opt3,',',opt4,',',opt5,']'),@score1,FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 235),FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 160),FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 160));
  INSERT INTO survey_player_score (owner_id,player_id,ptype,topic_id,chapter_id,question_ids,option_ids,score,begin_date,end_date,create_date) VALUES(owner_id,player_id,ptype,topic_id,2,'[6,7,8,9,10]',CONCAT('[',opt6,',',opt7,',',opt8,',',opt9,',',opt10,']'),@score2,FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 155),FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 80),FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 80));
  INSERT INTO survey_player_score (owner_id,player_id,ptype,topic_id,chapter_id,question_ids,option_ids,score,begin_date,end_date,create_date) VALUES(owner_id,player_id,ptype,topic_id,3,'[11,12,13,14,15]',CONCAT('[',opt11,',',opt12,',',opt13,',',opt14,',',opt15,']'),@score3,FROM_UNIXTIME(UNIX_TIMESTAMP(create_date) - 75),create_date,create_date);
  RETURN 1;
END $$
DELIMITER ;

SELECT to_score(owner_id,player_id,ptype,topic_id,option_ids,create_date) FROM survey_player_score2 WHERE chapter_id=1;

DELIMITER $$
DROP FUNCTION IF EXISTS to_option $$
CREATE FUNCTION to_option(player_score_id BIGINT,owner_id BIGINT,player_id BIGINT,ptype TINYINT,topic_id INT,chapter_id INT,question_ids VARCHAR(1000),option_ids VARCHAR(1000),create_date DATETIME) RETURNS VARCHAR(1000)
BEGIN
  DECLARE que1 INT;
  DECLARE que2 INT;
  DECLARE que3 INT;
  DECLARE que4 INT;
  DECLARE que5 INT;
  DECLARE opt1 INT;
  DECLARE opt2 INT;
  DECLARE opt3 INT;
  DECLARE opt4 INT;
  DECLARE opt5 INT;
  
  SET que1 = CONVERT(SUBSTRING(question_ids,2,LENGTH(SUBSTRING_INDEX(question_ids,',',1))-1),UNSIGNED);
  SET que2 = CONVERT(SUBSTRING(question_ids,LENGTH(SUBSTRING_INDEX(question_ids,',',1))+2,LENGTH(SUBSTRING_INDEX(question_ids,',',2))-LENGTH(SUBSTRING_INDEX(question_ids,',',1))-1),UNSIGNED);
  SET que3 = CONVERT(SUBSTRING(question_ids,LENGTH(SUBSTRING_INDEX(question_ids,',',2))+2,LENGTH(SUBSTRING_INDEX(question_ids,',',3))-LENGTH(SUBSTRING_INDEX(question_ids,',',2))-1),UNSIGNED);
  SET que4 = CONVERT(SUBSTRING(question_ids,LENGTH(SUBSTRING_INDEX(question_ids,',',3))+2,LENGTH(SUBSTRING_INDEX(question_ids,',',4))-LENGTH(SUBSTRING_INDEX(question_ids,',',3))-1),UNSIGNED);
  SET que5 = CONVERT(SUBSTRING(question_ids,LENGTH(SUBSTRING_INDEX(question_ids,',',4))+2,LENGTH(SUBSTRING_INDEX(question_ids,']',1))-LENGTH(SUBSTRING_INDEX(question_ids,',',4))-1),UNSIGNED);

  SET opt1 = CONVERT(SUBSTRING(option_ids,2,LENGTH(SUBSTRING_INDEX(option_ids,',',1))-1),UNSIGNED);
  SET opt2 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',1))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',2))-LENGTH(SUBSTRING_INDEX(option_ids,',',1))-1),UNSIGNED);
  SET opt3 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',2))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',3))-LENGTH(SUBSTRING_INDEX(option_ids,',',2))-1),UNSIGNED);
  SET opt4 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',3))+2,LENGTH(SUBSTRING_INDEX(option_ids,',',4))-LENGTH(SUBSTRING_INDEX(option_ids,',',3))-1),UNSIGNED);
  SET opt5 = CONVERT(SUBSTRING(option_ids,LENGTH(SUBSTRING_INDEX(option_ids,',',4))+2,LENGTH(SUBSTRING_INDEX(option_ids,']',1))-LENGTH(SUBSTRING_INDEX(option_ids,',',4))-1),UNSIGNED);
  
  SELECT SUM(score) INTO @score1 FROM survey_option WHERE id IN(opt1);
  SELECT SUM(score) INTO @score2 FROM survey_option WHERE id IN(opt2);
  SELECT SUM(score) INTO @score3 FROM survey_option WHERE id IN(opt3);
  SELECT SUM(score) INTO @score4 FROM survey_option WHERE id IN(opt4);
  SELECT SUM(score) INTO @score5 FROM survey_option WHERE id IN(opt5);
  
  INSERT INTO survey_player_option (owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,question_id,option_ids,score,create_date) VALUES(owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,que1,CONCAT('[',opt1,']'),@score1,create_date);
  INSERT INTO survey_player_option (owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,question_id,option_ids,score,create_date) VALUES(owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,que2,CONCAT('[',opt2,']'),@score2,create_date);
  INSERT INTO survey_player_option (owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,question_id,option_ids,score,create_date) VALUES(owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,que3,CONCAT('[',opt3,']'),@score3,create_date);
  INSERT INTO survey_player_option (owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,question_id,option_ids,score,create_date) VALUES(owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,que4,CONCAT('[',opt4,']'),@score4,create_date);
  INSERT INTO survey_player_option (owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,question_id,option_ids,score,create_date) VALUES(owner_id,player_id,ptype,topic_id,chapter_id,player_score_id,que5,CONCAT('[',opt5,']'),@score5,create_date);
  RETURN CONCAT(@score1,',',@score2,',',@score3,',',@score4,',',@score5);
END $$
DELIMITER ;

SELECT to_option(id,owner_id,player_id,ptype,topic_id,chapter_id,question_ids,option_ids,create_date) FROM survey_player_score;

DROP TABLE IF EXISTS survey_player_score2;
DROP TABLE IF EXISTS survey_player_option2;
DROP FUNCTION IF EXISTS to_score;
DROP FUNCTION IF EXISTS to_option;

DROP TABLE IF EXISTS survey_section;
ALTER TABLE wechat_chatter DROP entry;