<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/public/root.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="screen-orientation" content="portrait">
<meta name="format-detection" content="telphone=no, email=no">
<meta name="HandheldFriendly" content="true">
<meta name="x5-orientation" content="portrait">
<meta name="x5-fullscreen" content="true">
<meta name="x5-page-mode" content="app">
<meta name="full-screen" content="yes">
<meta name="browsermode" content="application">
<meta name="msapplication-tap-highlight" content="no">
<link rel="stylesheet" href="${ctx}/pages/20150518/assets/css/styleIndex.css">
<script type="text/javascript" src="${ctx}/script/common/jquery-1.10.2.min.js" charset="utf-8"></script>
<title>进入招聘会</title>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
  wx.config({
    debug: false,
    appId: '${appId}',
    nonceStr: '${nonce}',
    timestamp: '${timestamp}',
    signature: '${signature}',
    jsApiList: ['scanQRCode']
  });

  $(function() {
    var jsapiButton = $("#scanResume");
    jsapiButton.click(function() {
      wx.scanQRCode({
        needResult: 1,
        scanType: ["qrCode", "barCode"]
      });
    }); 
  });
</script>
</head>
<body>
	<script>
    function gogo() {
      var dcheight = $(window).height();
      var imgheight = $(".banner a").height();
      var sjheight = dcheight - imgheight;
      $(".poser .pos").css("height", sjheight / 2);

      if ('${signIn}' == "signIn") {
        suc();
      }
    }

    function suc() {
      var getdiv = $(".ico1");
      getdiv.addClass("ico5");
      getdiv.next().text("已签到");
    }

    //搜索职位
    function toSearchPost() {
      window.location.href = "${ctx}/ent/fair/fairOpt!toSearchPost.action?fairId=${fairId}";
    }

    //实时数据
    function toLed() {
      window.location.href = "${ctx}/ent/fair/fairOpt!toMobileLed.action?fairId=${fairId}";
    }

    //签到
    function toSignIn() {
      $.post(ctx + "/jobhunte/account/accountOpt!jobFairSignIn.action", {
        "openId": '${openId}',
        "fairId": $(fairId)
      }, function(data) {
        if (data.result == 0) {
          suc();
        }
      })
    }

    var scanCount = true;

    //投简历
    function scanResume(url) {
      scanCount = true;
      window.location.href = url;
    }

    function toScanResume() {
      if (scanCount == false) {
        return;
      }
      scanCount = false;
      wx.scanQRCode({
        needResult: 1,
        scanType: ["qrCode", "barCode"],
        success: function(res) {
          var result = res.resultStr;
          setTimeout(function() {
            scanResume(result + "&openId=${openId}");
          }, 1000);
        }
      });
    }
  </script>
</head>
<body onload="gogo()">

	<div class="uibox">
		<div class="banner">
			<a href="" onclick="window.open('${wapUrl}');" style="background:url('${headPic}')  center center no-repeat; background-size:100%; position:absolute; width:100%; height:100%;"></a>
			<span class="detail"></span>
		</div>
		<div class="poser">
			<div class="pos mil" onclick="toSignIn()">
				<div class="ico ico1"></div>
				<p class="text">签到</p>
			</div>
			<div class="pos" onclick="toScanResume()">
				<div class="ico ico2"></div>
				<p class="text">投简历</p>
			</div>
			<div class="pos mil" onclick="toSearchPost()">
				<div class="ico ico4"></div>
				<p class="text">查找更多职位</p>
			</div>
			<div class="pos">
				<div class="ico ico3" onclick="toLed()"></div>
				<p class="text">招聘会实时数据</p>
			</div>
		</div>
	</div>
</body>
</html>