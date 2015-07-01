<%@ include file="/public/root.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>Index</title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
  wx.config({
    debug: true,
    appId: '${appId}',
    nonceStr: '${nonce}',
    timestamp: '${timestamp}',
    signature: '${signature}',
    jsApiList: ['scanQRCode', 'onMenuShareTimeline', 'onMenuShareAppMessage']
  });
  wx.ready(function() {
    wx.checkJsApi({
      jsApiList: ['scanQRCode', 'onMenuShareTimeline', 'onMenuShareAppMessage'],
      success: function(res) {
      },
      fail: function() {
      }
    });
    wx.onMenuShareTimeline({
      title: 'onMenuShareTimeline Test',
      link: 'http://letorn.aliapp.com/view.do?page=jssdk.jsp',
      imgUrl: 'http://www.zcdhjob.com/pages/20150529/assets/img/banner_c640.png',
      success: function() {
      },
      cancel: function() {
      }
    });
    wx.onMenuShareAppMessage({
      title: '暨南大学2016届实习生专场招聘会',
      desc: '【暨南大学2016届实习生专场招聘会】时间：“2015年5月29日（星期五）9：30—16：00”',
      link: 'http://www.zcdhjob.com/pages/20150529/wap.jsp',
      imgUrl: 'http://www.zcdhjob.com/pages/20150529/assets/img/banner_c640.png',
      type: '',
      dataUrl: '',
      success: function() {
      },
      cancel: function() {
      }
    });
  });
</script>
</head>
<body>
JS SDK
</body>
</html>