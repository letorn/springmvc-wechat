<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/public/root.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
  var ownerId = '${ownerId}';
  var playerId = '${playerId}';

  wx.config({
    debug: false,
    appId: '${appId}',
    nonceStr: '${nonce}',
    timestamp: '${timestamp}',
    signature: '${signature}',
    jsApiList: ['closeWindow', 'hideAllNonBaseMenuItem', 'showMenuItems', 'onMenuShareTimeline', 'onMenuShareAppMessage']
  });

  wx.ready(function() {
    if (playerId == -1) {
      wx.closeWindow();
    }
    wx.hideAllNonBaseMenuItem();
    wx.showMenuItems({
      menuList: ['menuItem:share:appMessage', 'menuItem:share:timeline']
    });
    wx.onMenuShareAppMessage({
      title: '${shareTitle}',
      desc: '${shareDesc}',
      imgUrl: '${shareImgUrl}',
      link: '${shareUrl}',
      success: function() {
        $.post(ctx + '/egame/sharePage.do', {
          ownerId: ownerId,
          playerId: playerId,
          page: '/egame/index.jsp',
          target: 'wechat_friend'
        })
      }
    });
    wx.onMenuShareTimeline({
      title: '${shareDesc}',
      imgUrl: '${shareImgUrl}',
      link: '${shareUrl}',
      success: function() {
        $.post(ctx + '/egame/sharePage.do', {
          ownerId: ownerId,
          playerId: playerId,
          page: '/egame/index.jsp',
          target: 'wechat_friends'
        })
      }
    });
  });
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="stylesheet" type="text/css" href="${ctx}/egame/css/main.css" />
<script src="${ctx}/egame/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/egame/js/main.js" type="text/javascript" charset="utf-8" async="async"></script>
<title>我想静静</title>
</head>
<body>
  <div id="container">
    <div id="manuPage">
      <div class="manuBack"></div>
      <div class="btnContainer">
        <a href="${ctx}/egame/chapterList.do?ownerId=${ownerId}&playerId=${playerId}"><div id="enter" class="BTN">${ownerId == playerId ? '进入游戏' : '挑战TA'}</div></a>
        <div id="about" class="BTN">游戏说明</div>
        <a id="ScoreBoard" href="${ctx}/egame/myArena.do?ownerId=${ownerId}&playerId=${playerId}"><div id="ranking" class="BTN">${ownerId == playerId ? '我的擂台' : 'TA的擂台'}</div></a>
      </div>

    </div>
    <div id="aboutPage">
      <div class="aboutBack">
        <img src="img/about.png" style="max-width: 60%;" />
      </div>
      <div class="aboutText">
        <h2>游戏介绍：</h2>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;《我想静静》是由职场导航出品，针对90后开发的情感测评游戏，你将通过回答一些情境问题来帮助总被发“好人卡”的超超追求女神静静。每关100分，所有关卡得分的总和为最终擂台分数。同时获得相应的荣誉称号。本测评科学又有趣，基于 Situational Judgment Test (情境判断测试)与Quantitative Research (定量研究)，本游戏可通过向好友发起擂台挑战，快邀请你的小伙伴一起PK吧！！！</p>
        <h2>操作说明：</h2>
        <p>
          通过点击<span id="finger"></span>控制动画的播放与选择答案。
        </p>
        <h2>制作人员：</h2>
        <p>
          出品人：夜听潮，博望<br /> 剧本创作：敏敏，云云，尚尚，静静，璇璇，洋洋<br /> 概念设定：大鹏，丹丹<br /> 界面设计：大鹏<br /> 动画制作：大鹏<br /> 前端开发：大鹏<br /> 后端开发：阿伟<br /> <i>--来自职场导航的专业测评研发团队，用心理学的严谨思路做游戏。--</i><br />
        </p>
      </div>
      <div id="aboutReturn" class="BTN">返回</div>
      <div class="aboutPageBack"></div>
    </div>

  </div>
  <script type="text/javascript">
      var $about = $('#about'), $aboutPage = $('#aboutPage'), $aboutReturn = $('#aboutReturn'), $manuPage = $('#manuPage')

      window.onload = function() {
        var container = document.getElementById("container")
        container.style.height = (window.innerHeight - 10) + 'px'
        container.style.width = window.innerWidth + 'px'

        $about.click(function() {
          $aboutPage.show();
          $manuPage.hide()
        })
        $aboutReturn.click(function() {
          $aboutPage.hide();
          $manuPage.show()
        })

      }
    </script>
</body>
</html>
