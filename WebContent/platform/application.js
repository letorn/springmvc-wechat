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