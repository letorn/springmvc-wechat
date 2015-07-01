Ext.define('Platform.analysis.View', {
  extend: 'Ext.tab.Panel',
  xtype: 'platform-analysis',
  uses: ['Platform.analysis.HomePanel'],
  initComponent: function() {
    var me = this;

    me.homePanel = Platform.widget('analysis-homepanel');

    me.items = [me.homePanel];

    me.callParent();
  }
});