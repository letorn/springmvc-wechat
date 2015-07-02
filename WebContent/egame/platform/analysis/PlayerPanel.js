Ext.define('Platform.analysis.PlayerPanel', {
  extend: 'Ext.panel.Panel',
  xtype: 'platform-analysis-playerpanel',
  title: '用户数',
  border: false,
  layout: {
    type: 'vbox',
    align: 'stretch'
  },
  initComponent: function() {
    var me = this;

    if (me.subTitle) {
      me.title = Ext.String.format('{0}({1})', me.title, me.subTitle);
    }

    me.store = Store.create({
      fields: ['nickname', 'gender', 'datetime', 'score', 'chapterKeys']
    });

    me.hourStore = Store.create({
      fields: ['hour', 'player']
    });

    me.genderStore = Store.create({
      fields: ['gender', 'total']
    });

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
        text: '性别',
        dataIndex: 'gender'
      }, {
        text: '时间',
        dataIndex: 'datetime',
        width: 150
      }, {
        text: '最高分',
        dataIndex: 'score'
      }, {
        text: '关卡',
        dataIndex: 'chapterKeys',
        renderer: function(value) {
          return value;
        }
      }]
    }, {
      itemId: 'charts',
      xtype: 'tabpanel',
      flex: 3,
      border: false,
      items: [{
        xtype: 'chart',
        title: '时点',
        animate: true,
        shadow: false,
        store: me.hourStore,
        axes: [{
          type: 'Numeric',
          fields: 'player',
          position: 'left',
          grid: true,
          minimum: 0,
          decimals: 0,
          majorTickSteps: 1
        }, {
          type: 'Category',
          fields: 'hour',
          position: 'bottom',
          grid: true,
          label: {
            rotate: {
              degrees: -45
            }
          }
        }],
        series: [{
          type: 'line',
          axis: 'left',
          xField: 'hour',
          yField: 'player',
          style: {
            'stroke-width': 4
          },
          markerConfig: {
            radius: 4
          },
          highlight: {
            fill: '#000',
            radius: 5,
            'stroke-width': 2,
            stroke: '#fff'
          },
          tips: {
            trackMouse: true,
            style: 'background: #FFF',
            height: 20,
            showDelay: 0,
            dismissDelay: 0,
            hideDelay: 0,
            renderer: function(storeItem, item) {
              this.setTitle(storeItem.get('hour') + ': ' + storeItem.get('player'));
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }, {
        xtype: 'chart',
        title: '男女比例',
        animate: true,
        shadow: false,
        store: me.genderStore,
        legend: {
          field: 'gender',
          position: 'left',
          boxStrokeWidth: 0,
          labelFont: '12px Helvetica'
        },
        series: [{
          type: 'pie',
          angleField: 'total',
          label: {
            field: 'gender',
            display: 'outside',
            calloutLine: true
          },
          showInLegend: true,
          highlight: true,
          highlightCfg: {
            fill: '#000',
            'stroke-width': 20,
            stroke: '#fff'
          },
          tips: {
            trackMouse: true,
            renderer: function(storeItem, item) {
              this.setTitle(storeItem.get('gender') + ': ' + storeItem.get('total') + '%');
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }]
    }];

    me.callParent();
  },
  loadData: function(startDate, endDate) {
    var me = this, grid = me.down('#grid'), chart = me.down('#charts').getActiveTab(), store = me.store, genderStore = me.genderStore, data = [], male = 0, female = 0;
    me.startDate = startDate;
    me.endDate = endDate;
    grid.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/egame/platform/players.do',
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
        chart.surface.removeAll()
        me.loadHourData(data);
        me.loadGenderData(data);
        chart.refresh();
        grid.setLoading(false);
      }
    });
  },
  loadHourData: function(data) {
    var me = this, hourStore = me.hourStore, hourMap = {}, hourData = [];
    for (var i = 0; i < 24; i++) {
      hourMap[i] = 0;
    }
    for (var i = 0; i < data.length; i++) {
      var datetime = Ext.Date.parse(data[i].datetime, "Y-m-d H:i:s");
      if (datetime) {
        var hour = datetime.getHours();
        count = hourMap[hour];
        hourMap[hour] = count + 1;
      }
    }
    for ( var hour in hourMap) {
      hourData.push({
        hour: hour,
        player: hourMap[hour]
      });
    }
    hourStore.loadData(hourData);
  },
  loadGenderData: function(data) {
    var me = this, genderStore = me.genderStore, male = 0, female = 0;
    for (var i = 0; i < data.length; i++) {
      if (data[i].gender == '男') {
        male++;
      }
      if (data[i].gender == '女') {
        female++;
      }
    }
    if (male + female > 0) {
      genderStore.loadData([{
        gender: '男',
        total: (male / (male + female)) * 100
      }, {
        gender: '女',
        total: (female / (male + female)) * 100
      }]);
    } else {
      genderStore.loadData([{
        gender: '男',
        total: 0
      }, {
        gender: '女',
        total: 0
      }]);
    }
  },
  onRefreshBtnClick: function() {
    this.loadData(this.startDate, this.endDate);
  },
  onChartShow: function() {
    this.surface.removeAll()
    this.refresh();
  }
});