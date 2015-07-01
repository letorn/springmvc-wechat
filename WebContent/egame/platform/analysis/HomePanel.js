Ext.define('Platform.analysis.HomePanel', {
  extend: 'Ext.panel.Panel',
  xtype: 'platform-analysis-homepanel',
  uses: ['Platform.analysis.PlayerPanel', 'Platform.analysis.PlayerScorePanel', 'Platform.analysis.ShareLogPanel', 'Platform.analysis.VisitLogPanel'],
  title: 'Home',
  border: false,
  layout: {
    type: 'vbox',
    align: 'stretch'
  },
  initComponent: function() {
    var me = this;

    me.store = Store.create({
      fields: ['date', 'player', 'playerScore', 'shareLog', 'visitLog']
    });
    me.sumStore = Store.create({
      fields: ['date', 'player', 'playerScore', 'shareLog', 'visitLog']
    });

    var endDate = new Date();
    endDate.setHours(23), endDate.setMinutes(59), endDate.setSeconds(59), endDate.setMilliseconds(999);
    var startDate = new Date(endDate.getTime() - 1000 * 60 * 60 * 24 * 7 + 1);

    me.tbar = [{
      itemId: 'startDate',
      xtype: 'datefield',
      format: 'Y-m-d',
      width: 180,
      fieldLabel: '时间',
      labelAlign: 'right',
      labelWidth: 60,
      editable: false,
      allowBlank: false,
      value: startDate
    }, {
      xtype: 'label',
      text: ' ~ '
    }, {
      itemId: 'endDate',
      xtype: 'datefield',
      format: 'Y-m-d',
      width: 120,
      hideLabel: true,
      editable: false,
      allowBlank: false,
      value: endDate
    }, {
      text: '刷新',
      iconCls: 'refresh',
      handler: Ext.bind(me.onRefreshBtnClick, me)
    }];

    var columns = [{
      text: '时间',
      dataIndex: 'date',
      align: 'center',
      resizable: false,
      width: 180
    }, {
      text: '用户数',
      dataIndex: 'player',
      align: 'center',
      resizable: false,
      renderer: function(value, metaData, record) {
        return '<a class="player" href="javascript:">' + value + '</a>';
      }
    }, {
      text: '完成数',
      dataIndex: 'playerScore',
      align: 'center',
      resizable: false,
      renderer: function(value, metaData, record) {
        return '<a class="playerScore" href="javascript:">' + value + '</a>';
      }
    }, {
      text: '分享数',
      dataIndex: 'shareLog',
      align: 'center',
      resizable: false,
      renderer: function(value, metaData, record) {
        return '<a class="shareLog" href="javascript:">' + value + '</a>';
      }
    }, {
      text: '访问数',
      dataIndex: 'visitLog',
      align: 'center',
      resizable: false,
      renderer: function(value, metaData, record) {
        return '<a class="visitLog" href="javascript:">' + value + '</a>';
      }
    }];

    me.items = [{
      itemId: 'grid',
      xtype: 'grid',
      flex: 2,
      border: false,
      store: me.store,
      columns: columns,
      listeners: {
        cellclick: me.onGridCellClick
      }
    }, {
      itemId: 'sumGrid',
      xtype: 'grid',
      border: false,
      store: me.sumStore,
      hideHeaders: true,
      columns: columns,
      listeners: {
        cellclick: me.onGridCellClick
      }
    }, {
      itemId: 'charts',
      xtype: 'tabpanel',
      flex: 3,
      border: false,
      items: [{
        xtype: 'chart',
        title: '用户数',
        animate: true,
        shadow: false,
        store: me.store,
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
          fields: 'date',
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
          xField: 'date',
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
              this.setTitle(storeItem.get('date') + ': ' + storeItem.get('player'));
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }, {
        xtype: 'chart',
        title: '完成数',
        animate: true,
        shadow: false,
        store: me.store,
        axes: [{
          type: 'Numeric',
          fields: 'playerScore',
          position: 'left',
          grid: true,
          minimum: 0,
          decimals: 0,
          majorTickSteps: 1
        }, {
          type: 'Category',
          fields: 'date',
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
          xField: 'date',
          yField: 'playerScore',
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
              this.setTitle(storeItem.get('date') + ': ' + storeItem.get('playerScore'));
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }, {
        xtype: 'chart',
        title: '分享数',
        animate: true,
        shadow: false,
        store: me.store,
        axes: [{
          type: 'Numeric',
          fields: 'shareLog',
          position: 'left',
          grid: true,
          minimum: 0,
          decimals: 0,
          majorTickSteps: 1
        }, {
          type: 'Category',
          fields: 'date',
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
          xField: 'date',
          yField: 'shareLog',
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
              this.setTitle(storeItem.get('date') + ': ' + storeItem.get('shareLog'));
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }, {
        xtype: 'chart',
        title: '访问数',
        animate: true,
        shadow: false,
        store: me.store,
        axes: [{
          type: 'Numeric',
          fields: 'visitLog',
          position: 'left',
          grid: true,
          minimum: 0,
          decimals: 0,
          majorTickSteps: 10
        }, {
          type: 'Category',
          fields: 'date',
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
          xField: 'date',
          yField: 'visitLog',
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
              this.setTitle(storeItem.get('date') + ': ' + storeItem.get('visitLog'));
            }
          }
        }],
        listeners: {
          show: me.onChartShow
        }
      }]
    }];

    me.listeners = {
      afterrender: me.onAfterRender
    }

    me.callParent();
  },
  loadData: function() {
    var me = this, grid = me.down('#grid'), chart = me.down('#charts').getActiveTab(), store = me.store, sumStore = me.sumStore, startDateField = me.down('#startDate'), endDateField = me.down('#endDate');
    if (startDateField.isValid() && endDateField.isValid()) {
      var startDate = startDateField.getValue(), endDate = new Date(endDateField.getValue().getTime() + 1000 * 60 * 60 * 24 - 1);
      if (startDate < endDate) {
        grid.setLoading(true);
        var data = [], sumData = [];
        Ext.Ajax.request({
          url: ctx + '/egame/platform/aggrData.do',
          params: {
            startDate: startDate,
            endDate: endDate
          },
          callback: function(options, success, response) {
            if (response = decodeResponse(response)) {
              data = response.data, sumData = [data.shift()];
            }
            store.loadData(data);
            chart.surface.removeAll()
            sumStore.loadData(sumData);
            chart.refresh();
            grid.setLoading(false);
          }
        });
      } else {
        Ext.toast({
          title: '提示',
          html: '结束时间 不能小于 开始时间',
          align: 't',
          slideInDuration: 100,
          slideBackDuration: 800,
          hideDuration: 100,
          autoCloseDelay: 1000,
        });
      }
    }
  },
  onAfterRender: function() {
    this.loadData();
  },
  onRefreshBtnClick: function() {
    this.loadData();
  },
  onGridCellClick: function(table, td, cellIndex, record, tr, rowIndex, e) {
    var view = Platform.get('analysis'), target = e.getTarget(), className = target.className;
    if ('player' == className || 'playerScore' == className || 'shareLog' == className || 'visitLog' == className) {
      var cmap = {
        player: 'analysis-playerpanel',
        playerScore: 'analysis-playerscorepanel',
        shareLog: 'analysis-sharelogpanel',
        visitLog: 'analysis-visitlogpanel'
      };
      var itemId = Ext.String.format('{0}_{1}', className, record.get('date').replace(/\s*~\s*/, '_'));
      var panel = view.queryById(itemId);
      if (panel) {
        view.setActiveItem(panel);
      } else {
        var startDate = record.get('date'), endDate;
        if (Ext.isString(startDate)) {
          if (/\s*~\s*/.test(startDate)) {
            var dates = startDate.split(/\s*~\s*/), startDate = new Date(dates[0]), endDate = new Date(dates[1]);
            startDate.setHours(0), startDate.setMinutes(0), startDate.setSeconds(0), startDate.setMilliseconds(0);
            endDate.setHours(23), endDate.setMinutes(59), endDate.setSeconds(59), endDate.setMilliseconds(999);
          } else {
            startDate = new Date(startDate);
            startDate.setHours(0), startDate.setMinutes(0), startDate.setSeconds(0), startDate.setMilliseconds(0);
          }
        }
        if (Ext.isDate(startDate) && !endDate) {
          endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24 - 1);
        }
        if (Ext.isDate(startDate) && Ext.isDate(endDate)) {
          panel = Platform.widget(cmap[className], {
            itemId: itemId,
            subTitle: record.get('date'),
            closable: true
          });
          panel.loadData(startDate, endDate);
          view.add(panel);
          view.setActiveItem(panel);
        }
      }
    }
  },
  onChartShow: function() {
    this.surface.removeAll()
    this.refresh();
  }
});