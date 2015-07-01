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
          page: '/egame/chapterList.jsp',
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
          page: '/egame/chapterList.jsp',
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
    <div id="chapterPage">
      <div class="chapterBack">
        <img src="img/chapter.png" style="max-width: 60%;" />
      </div>
      <div id="chapterList" class="chapterThumb">
        <div id="thumbs01" class="thumbs">
          <b>第1关 搭讪</b>
          <div id="chapter01_heart" class="star">
            <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i>
          </div>

        </div>
        <div id="thumbs02" class="thumbs">
          <b>第2关 约吗</b>
          <div id="chapter02_heart" class="star">
            <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i>
          </div>
        </div>
        <div id="thumbs03" class="thumbs">
          <b>第3关 表白</b>
          <div id="chapter03_heart" class="star">
            <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i>
          </div>
        </div>
        <div id="thumbs04" class="thumbs">
          <b class='qidai'>第4关 敬请期待</b>
          <div id="chapter04_heart" class="star">
            <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i>
          </div>
        </div>
      </div>
      <a id="Start" href="${ctx}/egame/chapter.do?ownerId=${ownerId}&playerId=${playerId}&chapterNum=1"><div id="chapterStart" class="BTN">开始</div></a> <a href="${ctx}/egame/index.do?ownerId=${ownerId}&playerId=${playerId}"><div id="chapterReturn" class="BTN">返回</div></a>
      <div class="chapterPageBack"></div>
    </div>
  </div>
  <script type="text/javascript">
      var chapter1Score = '${chapter1Score}', chapter1Heart = '${chapter1Heart}' || 0
      var chapter2Score = '${chapter2Score}', chapter2Heart = '${chapter2Heart}' || 0
      var chapter3Score = '${chapter3Score}', chapter3Heart = '${chapter3Heart}' || 0
      var chapter4Score = '${chapter4Score}', chapter4Heart = '${chapter4Heart}' || 0

      var chapterkeys = ${chapterKeys}

      var $manuPage = $('#manuPage'), $enter = $('#enter'), $chapterPage = $('#chapterPage'), $start = $('#Start'), $thumbs = $('.thumbs'), $goBtn = $('#chapterStart')
      window.onload = function() {
        var container = document.getElementById("container")
        container.style.height = (window.innerHeight - 10) + 'px'
        container.style.width = window.innerWidth + 'px'
        var gameStart = new Home();
        gameStart.init()
        


        $enter.click(function() {
          $chapterPage.show();
          $manuPage.hide()
        })
        $thumbs.on('click', function(ev) {
          gameStart.selectChapter(ev)
        })
        
        
      }
      
    </script>
</body>
</html>
