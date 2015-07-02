Ext.define('Platform.custommenu.View', {
  extend: 'Ext.tree.Panel',
  xtype: 'platform-custommenu',
  uses: ['Platform.custommenu.Editer'],
  title: '自定义菜单',
  initComponent: function() {
    var me = this;

    me.store = Store.create({
      type: 'tree',
      fields: ['id', 'name', 'type', 'value', 'status', 'parentId']
    });

    me.tbar = [{
      itemId: 'addBtn',
      xtype: 'button',
      text: '新增',
      iconCls: 'add',
      handler: Ext.bind(me.onAddBtnClick, me)
    }, {
      xtype: 'button',
      text: '刷新',
      iconCls: 'refresh',
      handler: Ext.bind(me.onRefreshBtnClick, me)
    }, {
      xtype: 'button',
      text: '更新到公众号',
      handler: Ext.bind(me.onSyncBtnClick, me)
    }];

    me.columns = [{
      xtype: 'treecolumn',
      text: '名称',
      dataIndex: 'name',
      width: 200
    }, {
      text: '类型',
      dataIndex: 'type',
      width: 120,
      renderer: function(value) {
        if ('dir' == value)
          return '目录';
        else if ('click' == value)
          return '点击';
        else if ('view' == value)
          return '跳转';
        else if ('oauth_base' == value)
          return '跳转(静默授权)';
        else if ('oauth_info' == value)
          return '跳转(网页授权)';
        else if ('scancode_push' == value)
          return '扫码';
        else if ('scancode_waitmsg' == value)
          return '扫码(带提示)';
        else if ('pic_sysphoto' == value)
          return '拍照';
        else if ('pic_weixin' == value)
          return '发图';
        else if ('pic_photo_or_album' == value)
          return '拍照/发图';
        else if ('location_select' == value)
          return '地理位置';
        else if ('media_id' == value)
          return '下发消息';
        else if ('view_limited' == value)
          return '图文消息';
        return '';
      }
    }, {
      text: '值',
      dataIndex: 'value',
      width: 400
    }, {
      text: '激活',
      dataIndex: 'status',
      width: 55,
      renderer: function(value) {
        if (1 == value)
          return '是';
        else
          return '否';
      }
    }, {
      text: '序号',
      dataIndex: 'num',
      width: 55
    }, {
      xtype: 'actioncolumn',
      text: '删除',
      iconCls: 'delete',
      align: 'center',
      width: 55,
      isDisabled: function(view, rowIdx, colIdx, item, record) {
        return record.get('id') == -1;
      },
      handler: Ext.bind(me.onDeleteBtnClick, me)
    }];

    me.listeners = {
      afterrender: me.onAfterRender,
      selectionchange: me.onSelectionChange,
      itemclick: me.onItemClick,
      itemdblclick: me.onItemDblClick
    }

    me.callParent();
  },
  loadData: function() {
    var me = this;
    me.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/platform/custommenu/data.do',
      timeout: 3000,
      callback: function(options, success, response) {
        if (response = decodeResponse(response)) {
          me.store.setRoot({
            id: '-1',
            name: '根',
            expanded: true,
            children: response.data
          });
        }
        me.setLoading(false);
      }
    });
  },
  onAfterRender: function() {
    this.loadData();
  },
  onSelectionChange: function(model, selected) {
    var me = this, addBtn = me.down('#addBtn');
    me.selection = selected[0];
    if (me.selection) {
      addBtn.setDisabled(me.selection.getDepth() != 0 && me.selection.getDepth() != 1);
    }
  },
  onItemClick: function(treeview) {
    treeview.toggleOnDblClick = false;
  },
  onItemDblClick: function(v, record) {
    var me = this, parent = record.parentNode;
    if (parent) {
      if (!me.editer) {
        me.editer = Platform.widget('custommenu-editer');
      }
      me.editer.reset();

      me.editer.setValues({
        id: record.get('id'),
        name: record.get('name'),
        type: record.get('type'),
        value: record.get('value'),
        status: record.get('status'),
        num: record.get('num'),
        parentId: record.get('parentId')
      });
      me.editer.setTitle('编辑');
      me.editer.show();
    }
  },
  onAddBtnClick: function() {
    var me = this, parent = me.selection;
    if (!parent) {
      parent = me.getRootNode();
    }
    if (!me.editer) {
      me.editer = Platform.widget('custommenu-editer');
    }
    me.editer.reset();

    var parentIdComboBoxData = [['根', -1]];
    me.getRootNode().eachChild(function(node) {
      if (node.get('type') == 'dir') {
        parentIdComboBoxData.push([node.get('name'), node.get('id')]);
      }
    });
    me.editer.down('#parentIdComboBox').getStore().setData(parentIdComboBoxData);

    me.editer.setValues({
      parentId: parent.get('id')
    });
    me.editer.setTitle('新增');
    me.editer.show();
  },
  onRefreshBtnClick: function() {
    this.loadData();
  },
  onSyncBtnClick: function() {
    var me = this;
    me.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/platform/custommenu/sync.do',
      timeout: 3000,
      callback: function(options, success, response) {
        if (response = decodeResponse(response)) {
          Ext.toast('更新成功', '提示', 't');
        } else {
          Ext.toast('更新失败', '提示', 't');
        }
        me.setLoading(false);
      }
    });
  },
  onDeleteBtnClick: function(tableView, rowIndex, colIndex, actionItem, event, record, row) {
    var me = this;
    me.setLoading(true);
    Ext.Ajax.request({
      url: ctx + '/platform/custommenu/delete.do',
      timeout: 3000,
      params: {
        id: record.get('id')
      },
      callback: function(options, success, response) {
        if (response = decodeResponse(response)) {
          me.loadData();
        }
        me.setLoading(false);
      }
    });
  }
});