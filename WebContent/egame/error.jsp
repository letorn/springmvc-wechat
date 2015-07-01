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
          page: '/egame/error.jsp',
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
          page: '/egame/error.jsp',
          target: 'wechat_friends'
        })
      }
    });
  });
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>错误页面</title>
<link rel="stylesheet" type="text/css" href="${ctx}/egame/css/error.css" />
</head>
<body id="body">
  <div class="error">
    <img class="head" src="img/head.png" /> <span class="info">好像出了点问题！~</span>
    <div id="Return" class="btn">返回游戏</div>
  </div>
  <script type="text/javascript">
      window.onload = function() {
        var body = document.getElementById("body")
        body.style.height = window.innerHeight + 'px'
        body.style.width = window.innerWidth + 'px'

        $('#Return').click(function() {
          if (ownerId && playerId) {
            window.location = ctx + '/egame/index.do?ownerId=' + ownerId + '&playerId=' + playerId;
          } else {
            wx.closeWindow();
          }
        })
      }
    </script>
</body>
</html>
