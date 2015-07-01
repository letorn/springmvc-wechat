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
          page: '/egame/chapter1.jsp',
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
          page: '/egame/chapter1.jsp',
          target: 'wechat_friends'
        })
      }
    });
  });
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/egame/css/chapter.css" />
<script src="${ctx}/egame/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/egame/js/chapter.js" type="text/javascript" charset="utf-8" async="async"></script>
</head>

<body id="body">
  <div id="SoundBTN">静音</div>
  <p id="description"></p>
  <div id="gifHolder">
    <div id="gif"></div>
  </div>
  <div id="option" class="option">
    <div class="optionBack">
      <div class="optionHead"></div>
      <span id="title" class="optonTitle"></span>
      <ul class="optionSelector">
        <li class="optionList"><label id="Acircle" class="circle"></label> <span id="A" class="optionContent"></span></li>
        <li class="optionList"><label id="Bcircle" class="circle"></label> <span id="B" class="optionContent"></span></li>
        <li class="optionList"><label id="Ccircle" class="circle"></label> <span id="C" class="optionContent"></span></li>
        <li class="optionList"><label id="Dcircle" class="circle"></label> <span id="D" class="optionContent"></span></li>
      </ul>
    </div>
    <div id="confirm" class="BTN">确认&继续</div>
  </div>
  <div id="partScore">
    <div id="Tips">
      <img src="img/head.png" style="width: 100px;" /><span class="ppTips">PP博士：<b id="speak" style="font-size: 14px;"></b></span>
    </div>
    <div id="comment" class="comment">
      <div class="commentHolder">
        <div id="commentContent">${remark}</div>
      </div>
    </div>
    <div id="Score">
      <div class="scoreHolder">
        <div>
          <span id="heart" class="heart"> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i> <i class="heartEmpty"></i>
          </span> <span id="score" style="font-size: 18px; opacity: 0.6; font-weight: bold; padding-right: 1em; color: rgb(247, 108, 22); margin-left: 1.5em;"></span>
        </div>
      </div>
    </div>
    <div id="PS" class="PS"></div>
    <div id="partButton">
      <div class="partBtnHolder">
        <a href="${ctx}/egame/chapterList.do?ownerId=${ownerId}&playerId=${playerId}"><div id="next" class="next">返回</div></a>
      </div>
    </div>
  </div>

  <div id="ErrorContainer">
    <div class="ErrorBox">
      <span>网络好像不给力哦！~</span>
      <div id="ErrorBtn">重试</div>
    </div>
    <div class="ErrorBack"></div>
  </div>
  <div id="Audio">
    <audio src="audio/start.mp3"></audio>
    <audio src="audio/loop.mp3"></audio>
  </div>
  <script type="text/javascript">
      var questions = ${questions} || []
      var chapterNum = '${chapterNum}'

      var ScoreStorage = window.localStorage
      var $optionHolder = $('#option'), $optionTitle = $('#title'), $commentTitle = $('#commentTitle'), $commentContent = $('#commentContent'), $confirm = $('#confirm'), $animeHolder = $('#gif'), $descriptionHolder = $('#description'), $partScoreHolder = $('#partScore'), $nextPartTitle = $('#nextPart'), $speak = $('#speak'), $PS = $('#PS'), $heart = $('#heart > i'), $star = $('#star > i'), $SoundBtn = $('#SoundBTN'), $honor = $('#honor'), $optionContent = $('.optionContent'), $circle = $('.circle'), $scoreNum = $('#ScoreNum'), $ErrorContainer = $('#ErrorContainer'), $ErrorBtn = $('#ErrorBtn')

      var Num = 0
      var tickNum = 0
      var tickStarNum = 0

      window.onload = function() {
        var body = document.getElementById("body")
        body.style.height = (window.innerHeight - 10) + 'px'
        body.style.width = window.innerWidth + 'px'
        var chapter = new Chapter()
        chapter.init()

        $confirm.click(function() {
          chapter.clickNum++
          chapter.saveOption()
          chapter.hideOption()
          chapter.delayTime()
          //改变gif动画，传入图片序号
          if (chapter.clickNum < questions.length) {
            chapter.changeAnimation(chapter.clickNum)
            chapter.changeDescription()
            chapter.changeOption()
          } else if (chapter.clickNum == questions.length) {
            chapter.showPartScrore()
          }
        })
        $SoundBtn.click(function() {
          chapter.toggleAudio()
        })
        $circle.click(function(ev) {
          chapter.selectOption(ev)
        })
        $optionContent.click(function(ev) {
          chapter.selectOption(ev)
        })
      }

      function Tick() {
        if (Num < tickNum) {
          $heart[Num].className = 'heartFull'
          Num++
          tickTime = window.setTimeout("Tick()", 800)
        }
      }
      function TickStar() {
        if (Num < tickStarNum) {
          $star[Num].className = 'starFull'
          Num++
          tickStarTime = window.setTimeout("TickStar()", 800)
        }
      }
      function Request(url, data, success, error) {
        $.ajax(url, {
          async: false,
          contentType: 'application/x-www-form-urlencoded; charset=utf-8',
          type: 'POST',
          data: data,
          dataType: 'json',
          traditional: true,
          success: success,
          error: error
        })
      }
    </script>
</body>

</html>