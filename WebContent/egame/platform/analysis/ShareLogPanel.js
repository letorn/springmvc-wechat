Ext.define('Platform.analysis.ShareLogPanel', {
  extend: 'Ext.panel.Panel',
  xtype: 'platform-analysis-sharelogpanel',
  title: '分享数',
  border: false,
  layout: {
    type: 'hbox',
    align: 'stretch'
  },
  initComponent: function() {
    var me = this;

    if (me.subTitle) {
      me.title = Ext.String.format('{0}({1})', me.title, me.subTitle);
    }

    me.store = Store.create({
      fields: ['playerId', 'nickname', 'count']
    });
    me.subStore = Store.create({
      fields: ['datetime', 'page', 'target']
    }),

    me.tbar = [{
      text: '刷新',
      iconCls: 'refresh',
      handler: Ext.bind(me.onRefreshBtnClick, me)
    }];
    
    me.items = [{
      itemId: 'grid',
      xtype: 'grid',
      flex: 2,
      border: false,
      store: me.store,
      columns: [{
        text: '用户名',
        dataIndex: 'nickname',
        width: 160
      }, {
        text: '分享数',
        dataIndex: 'count'
      }],
      listeners: {
        itemclick: Ext.bind(me.onGridItemClick, me)
      }
    }, {
      itemId: 'subGrid',
      xtype: 'grid',
      flex: 5,
      border: false,
      style: 'border-left:1px solid #cecece;',
      store: me.subStore,
      columns: [{
        text: '分享时间',
        dataIndex: 'datetime',
        width: 150
      }, {
        text: '页面',
        dataIndex: 'page'
      }, {
        text: '目标',
        dataIndex: 'target'
      }]
    }];

    me.callParent();
  },
  loadData: function(startDate, endDate) {
    var me = this, grid = me.down('#grid'), store = me.store, data = [];
    me.startDate = startDate;
    me.endDate = endDate;
    grid.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/egame/platform/aggrShareLogs.do',
      timeout: 3000,
      params: {
        startDate: startDate,
        endDate: endDate
      },
      callback: function(options, success, response) {
        if (response = decodeResponse(response)) {
          data = response.data;
        }
        store.loadData(data);
        grid.setLoading(false);
      }
    });
  },
  loadSubData: function(record) {
    var me = this, subGrid = me.down('#subGrid'), subStore = me.subStore, startDate = me.startDate, endDate = me.endDate, subData = [];
    subGrid.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/egame/platform/shareLogs.do',
      timeout: 3000,
      params: {
        playerId: record.get('playerId'),
        startDate: startDate,
        endDate: endDate
      },
      callback: function(options, success, response) {
        if (response = decodeResponse(response)) {
          subData = response.data;
        }
        subStore.loadData(subData);
        subGrid.setLoading(false);
      }
    });
  },
  onRefreshBtnClick: function() {
    this.loadData(this.startDate, this.endDate);
  },
  onGridItemClick: function(view, record) {
    this.loadSubData(record);
  }
});