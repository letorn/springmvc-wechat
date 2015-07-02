var Views = {
  baseinfo: '基础信息',
  custommenu: '自定义菜单'
};

Ext.application({
  appFolder: '.',
  viewFolder: '.',
  name: 'Platform',
  views: function() {
    var views = ['viewport.View'], suffix = '.View';
    Ext.Object.each(Views, function(view, text) {
      views.push(view + suffix);
    });
    return views;
  }.call(),
  launch: function() {
    Platform.widget('viewport');
  }
});

function decodeResponse(response) {
  if (response.status != 200) {
    Ext.Msg.alert('提示', '系统异常，请重试！');
    return false;
  }
  var contentType = response.getResponseHeader('content-type').toLowerCase();
  if (contentType == 'application/json;charset=utf-8') {
    var object = Ext.decode(response.responseText);
    if (object.success) {
      return object;
    } else {
      return false;
    }
  }
  if (contentType == 'text/html;charset=utf-8') {
    var html = response.responseText;
    if (html.includes('<title>登录</title>')) {
      var loginWindow = Platform.get('loginwindow');
      if (!loginWindow) {
        loginWindow = Platform.widget('loginwindow');
      }
      loginWindow.requestOptions = response.request.options;
      loginWindow.show();
    }
  }
}

Ext.define('Platform.LoginWindow', {
  extend: 'Ext.window.Window',
  xtype: 'platform-loginwindow',
  title: '登录',
  closable: false,
  closeAction: 'hide',
  modal: true,
  initComponent: function() {
    var me = this;

    me.formpanel = Ext.widget('form', {
      border: false,
      margin: '7 10 3 5',
      defaults: {
        labelAlign: 'right',
        labelWidth: 40,
        width: 250
      },
      items: [{
        xtype: 'textfield',
        fieldLabel: '账号',
        name: 'name',
        allowBlank: false
      }, {
        xtype: 'textfield',
        inputType: 'password',
        fieldLabel: '密码',
        name: 'password',
        allowBlank: false
      }]
    });

    me.items = [me.formpanel];

    me.buttons = [{
      text: '确定',
      handler: Ext.bind(me.onSubmitBtnClick, me)
    }]

    me.callParent();
  },
  onSubmitBtnClick: function() {
    var me = this, formpanel = me.formpanel;
    if (formpanel.isValid()) {
      formpanel.submit({
        url: ctx + '/platform/login.do',
        success: function(form, action) {
          me.close();
          Ext.Ajax.request(me.requestOptions);
        },
        failure: function(form, action) {
          Ext.toast({
            title: '提示',
            html: '登录失败，账号或密码错误',
            align: 't',
            slideInDuration: 100,
            slideBackDuration: 800,
            hideDuration: 100,
            autoCloseDelay: 1000,
          });
        }
      });
    }
  }
});