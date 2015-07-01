<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/public/root.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
  var ownerId = '${ownerId}'
  var playerId = '${playerId}'

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
          page: '/egame/myArena.jsp',
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
          page: '/egame/myArena.jsp',
          target: 'wechat_friends'
        })
      }
    });
  });
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>我的擂台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/egame/css/myArena.css" />
<script src="${ctx}/egame/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body id="body">
  <div id="guide"></div>
  <div id="Container">
    <div class="arenaTitle">
      <img src="img/ArenaList.png" style="max-width: 60%;" />
    </div>

    <div id="arenaContainer" class="arenaContainer">
      <div class="header">
        <div class="headerTop">
          <img id="hostPortrait" class="portraitBig"></img>
        </div>
        <div class="headerInfo">
          <table width="100%" border="0" cellspacing="2" cellpadding="">
            <tbody align="left">
              <tr>
                <td>擂主：<span id="hostName"></span></td>
                <td>荣誉：<span id="hostHonor"></span></td>
                <td>分数：<span id="hostScore"></span></td>
              </tr>
              <tr>
                <td>胜利：<span id="hostWin"></span></td>
                <td>失败：<span id="hostLose"></span></td>
                <td>平局：<span id="hostDogfall"></span></td>
              </tr>
            </tbody>
          </table>
          <div class="bigLine"></div>
        </div>
      </div>
      <div id="memberHolder" class="memberHolder">
        <ul id="memberList"></ul>
      </div>
    </div>

  </div>
  ${ownerId == playerId ? '' : '<div id="setArena" class="BTN">我也要摆擂<span class="small">（向你朋友发起挑战）</span></div>'}
  <a href="${ctx}/egame/index.do?ownerId=${ownerId}&playerId=${playerId}"><div id="Return" class="BTN">返回主页</div></a>
  <script type="text/javascript">
      var owner = ${owner} || {}
	  var players = ${players} || {}
      var memberList = document.getElementById("memberList")

      window.onload = function() {
        var body = document.getElementById("body")
        var merberHolder = document.getElementById("memberHolder")
        var arenaContainer = document.getElementById("arenaContainer")
        var $setArena = $("#setArena"), $guide = $("#guide")
        body.style.height = (window.innerHeight - 10) + 'px'
        body.style.width = window.innerWidth + 'px'
        memberHolder.style.height = (arenaContainer.offsetHeight - 200) + "px"

        changeHost(owner.nickname, owner.honor, owner.score, owner.won, owner.lost, owner.drawn, owner.headImgUrl)
        var badges = ['badgeLose', 'badgeDogfall', 'badgeWin']
        for (var i = 0; i < players.length; i++) {
          var player = players[i]
          addMember(player.nickname, player.honor, player.score, player.chapterKeys, player.headImgUrl, badges[player.matched + 1])
        }
        $setArena.click(function() {
          $guide.css("display", "block");
        })
        $guide.click(function() {
          $guide.css("display", "none");
        })

      }
      function changeHost(Name, Honor, Score, Win, Lose, Dogfall, ImgUrl) {
        var hostName = $('#hostName'), hostHonor = $('#hostHonor'), hostScore = $('#hostScore'), hostWin = $('#hostWin'), hostLose = $('#hostLose'), hostDogfall = $('#hostDogfall'), hostPortrait = $('#hostPortrait')
        hostName.html(Name)
        hostHonor.html(Honor == '' ? '暂无' : Honor)
        hostScore.html(Score + '分')
        hostWin.html(Win)
        hostLose.html(Lose)
        hostDogfall.html(Dogfall)

        hostPortrait.prop('src', ImgUrl)
      }
      function addMember(Name, Honor, Score, Star, ImgUrl, Badge) {
        var starNum = ''
        var i = 0;
        for (; i < Star; i++) {
          starNum += '<li><span class="starFull"></span></li>'
        }
        for (; i < 4; i++) {
          starNum += '<li><span class="starEmpty"></span></li>'
        }
        memberList.innerHTML += '<li class="member">' + 
        '<span class="portraitHolder"><img class="portraitSmall" src="' + ImgUrl + '" /><span class="'+Badge+'"></span></span>' + 
        '<span class="memberName">' + Name + '</span>' + '<span class="memberHonor">' + Honor + '</span>' + 
        '<ul class="star">' + starNum + '</ul>' + '<span class="memberScore">' + 
        Score + '分</span>' + '<div class="smallLine"></div>' + '</li>'
      }
    </script>
</body>
</html>
