Ext.define('Platform.baseinfo.View', {
  extend: 'Ext.form.Panel',
  xtype: 'platform-baseinfo',
  title: '基础信息',
  initComponent: function() {
    var me = this;

    me.tbar = [{
      text: '刷新',
      iconCls: 'refresh',
      handler: Ext.bind(me.onRefreshBtnClick, me)
    }];

    me.defaults = {
      labelAlign: 'right',
      labelWidth: 90
    };

    me.items = [{
      xtype: 'displayfield',
      fieldLabel: 'appId',
      name: 'appId',
      margin: '10 0 0 0'
    }, {
      xtype: 'displayfield',
      fieldLabel: 'appSecret',
      name: 'appSecret'
    }, {
      xtype: 'displayfield',
      fieldLabel: 'token',
      name: 'token'
    }, {
      xtype: 'displayfield',
      fieldLabel: 'accessToken',
      name: 'accessToken'
    }, {
      xtype: 'displayfield',
      fieldLabel: 'ticket',
      name: 'ticket'
    }];

    me.listeners = {
      afterrender: me.onAfterRender
    };

    me.callParent();
  },
  loadData: function() {
    var me = this;
    me.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/platform/baseinfo/data.do',
      callback: function(options, success, response) {
        var response = Ext.decode(response.responseText);
        if (response.success) {
          me.getForm().setValues(response.data);
        }
        me.setLoading(false);
      }
    });
  },
  onAfterRender: function() {
    this.loadData();
  },
  onRefreshBtnClick: function() {
    this.loadData();
  }
});