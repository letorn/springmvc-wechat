<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/public/root.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>好友排行榜</title>
<link rel="stylesheet" type="text/css" href="css/scoreBoard.css" />
</head>
<body id="body">
  <div id="scoreBoardPage">
    <div class="scoreBoardTitle">
      <img src="img/ScoreBoard.png" style="max-width: 60%;" />
    </div>
    <div class="memberHolder">
      <div class="member">
        <span class="ranking">第 1 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
      <div class="member">
        <span class="ranking">第 2 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
      <div class="member">
        <span class="ranking">第 3 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
      <div class="member">
        <span class="ranking">第 4 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
      <div class="member">
        <span class="ranking">第 5 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
      <div class="member">
        <span class="ranking">第6 名</span> <span class="portrait"></span> <span class="honor">女神杀手</span> <span class="score">300 分</span>
      </div>
    </div>
    <div id="ShareScore" class="BTN">分享到朋友圈</div>
    <a href="${ctx}/egame/index.do?playerId=${playerId}"><div id="ReturnHome" class="BTN">返回游戏首页</div></a>
    <div class="scoreBoardPageBack">
      <div class="textTips">
        你在好友当中排名为 <span id="rankingNum"> 12 </span>名
      </div>
    </div>
  </div>
  <script type="text/javascript">
			var total = ${total}
			var now = ${now}
			var tops = ${tops}
			window.onload = function(){
				var body = document.getElementById("body")
				body.style.height = window.innerHeight + 'px'
				body.style.width = window.innerWidth + 'px'
			}
		</script>
</body>
</html>
