Ext.define('Platform.custommenu.Editer', {
  extend: 'Ext.window.Window',
  xtype: 'platform-custommenu-editer',
  title: '',
  closeAction: 'hide',
  resizable: false,
  layout: 'fit',
  initComponent: function() {
    var me = this;

    me.formpanel = Ext.widget({
      xtype: 'form',
      border: false,
      margin: '7 10 3 5',
      defaults: {
        labelAlign: 'right',
        labelWidth: 40,
        width: 250
      },
      items: [{
        xtype: 'hiddenfield',
        name: 'id'
      }, {
        itemId: 'parentIdComboBox',
        xtype: 'combobox',
        fieldLabel: '上级',
        name: 'parentId',
        displayField: 'name',
        valueField: 'value',
        queryMode: 'local',
        editable: false,
        allowBlank: false,
        value: -1,
        store: Store.get({
          type: 'array',
          fields: ['name', 'value'],
          data: [['根', -1]]
        }),
        listeners: {
          change: Ext.bind(me.onParentIdComboBoxChange, me)
        }
      }, {
        xtype: 'textfield',
        name: 'name',
        fieldLabel: '名称',
        allowBlank: false
      }, {
        itemId: 'typeComboBox',
        xtype: 'combobox',
        fieldLabel: '类型',
        name: 'type',
        displayField: 'name',
        valueField: 'value',
        queryMode: 'local',
        editable: false,
        allowBlank: false,
        value: 'dir',
        store: Store.get({
          type: 'array',
          fields: ['name', 'value'],
          data: [['目录', 'dir'], ['点击', 'click'], ['跳转', 'view'], ['跳转(静默授权)', 'oauth_base'], ['跳转(网页授权)', 'oauth_info'], ['扫码', 'scancode_push'], ['扫码(带提示)', 'scancode_waitmsg'], ['拍照', 'pic_sysphoto'], ['发图', 'pic_weixin'], ['拍照/发图', 'pic_photo_or_album'], ['地理位置', 'location_select'], ['下发消息', 'media_id'], ['图文消息', 'view_limited']]
        }),
        listeners: {
          change: Ext.bind(me.onTypeComboBoxChange, me)
        }
      }, {
        itemId: 'valueTextField',
        xtype: 'textfield',
        name: 'value',
        disabled: true,
        fieldLabel: '值',
        allowBlank: false
      }, {
        xtype: 'fieldcontainer',
        fieldLabel: '激活',
        defaultType: 'radiofield',
        layout: 'hbox',
        items: [{
          boxLabel: '是',
          name: 'status',
          inputValue: 1,
          checked: true,
          flex: 1
        }, {
          boxLabel: '否',
          name: 'status',
          inputValue: 0,
          flex: 1
        }]
      }, {
        xtype: 'numberfield',
        name: 'num',
        fieldLabel: '序号',
        allowBlank: false
      }]
    });

    me.items = [me.formpanel];

    me.buttonAlign = 'center';
    me.buttons = [{
      text: '确认',
      handler: Ext.bind(me.onSubmitBtnClick, me)
    }, {
      text: '取消',
      handler: Ext.bind(me.onCancelBtnClick, me)
    }];

    me.callParent();
  },
  reset: function() {
    return this.formpanel.reset();
  },
  isValid: function() {
    return this.formpanel.isValid();
  },
  getValues: function() {
    return this.formpanel.getValues();
  },
  setValues: function(values) {
    this.formpanel.getForm().setValues(values);
  },
  onSubmitBtnClick: function() {
    var me = this, view = Platform.get('custommenu');
    if (me.isValid()) {
      me.setLoading(true);
      Ext.Ajax.request({
        url: ctx + '/platform/custommenu/save.do',
        params: me.getValues(),
        callback: function(options, success, response) {
          var response = Ext.decode(response.responseText);
          if (response.success) {
            view.loadData();
          }
          me.setLoading(false);
          me.close();
        }
      });
    }
  },
  onCancelBtnClick: function() {
    this.close();
  },
  onParentIdComboBoxChange: function(field, newValue) {
    var me = this, view = Platform.get('custommenu'), typeComboBox = me.down('#typeComboBox'), root = view.getRootNode();
    if (newValue == -1) {
      typeComboBox.getStore().loadData([['目录', 'dir'], ['点击', 'click'], ['跳转', 'view'], ['跳转(静默授权)', 'oauth_base'], ['跳转(网页授权)', 'oauth_info'], ['扫码', 'scancode_push'], ['扫码(带提示)', 'scancode_waitmsg'], ['拍照', 'pic_sysphoto'], ['发图', 'pic_weixin'], ['拍照/发图', 'pic_photo_or_album'], ['地理位置', 'location_select'], ['下发消息', 'media_id'], ['图文消息', 'view_limited']]);
      var type = typeComboBox.getValue();
      if (typeComboBox.getStore().find('value', typeComboBox.getValue()) == -1) {
        typeComboBox.setValue('dir');
      }
    } else if (root.findChild('id', newValue).getDepth() == 1) {
      typeComboBox.getStore().loadData([['点击', 'click'], ['跳转', 'view'], ['跳转(静默授权)', 'oauth_base'], ['跳转(网页授权)', 'oauth_info'], ['扫码', 'scancode_push'], ['扫码(带提示)', 'scancode_waitmsg'], ['拍照', 'pic_sysphoto'], ['发图', 'pic_weixin'], ['拍照/发图', 'pic_photo_or_album'], ['地理位置', 'location_select'], ['下发消息', 'media_id'], ['图文消息', 'view_limited']]);
      if (typeComboBox.getStore().find('value', typeComboBox.getValue()) == -1) {
        typeComboBox.setValue('click');
      }
    }
  },
  onTypeComboBoxChange: function(field, newValue) {
    var me = this, view = Platform.get('custommenu'), parentIdComboBox = me.down('#parentIdComboBox'), valueTextField = me.down('#valueTextField'), root = view.getRootNode(), parentIdComboBoxData = [['根', -1]];
    if (newValue != 'dir') {
      root.eachChild(function(node) {
        if (node.get('type') == 'dir') {
          parentIdComboBoxData.push([node.get('name'), node.get('id')]);
        }
      });
    }
    parentIdComboBox.getStore().setData(parentIdComboBoxData);
    valueTextField.setDisabled(newValue == 'dir')
  }
});