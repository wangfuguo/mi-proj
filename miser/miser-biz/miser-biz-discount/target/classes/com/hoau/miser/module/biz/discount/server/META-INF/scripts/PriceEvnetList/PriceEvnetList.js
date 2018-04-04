Ext.application({
    name: "Miser",
    appFolder: '../scripts/discount/PriceEvnetList',
    controllers: ["PriceEvnet"],
    /*autoCreateViewport: true,*/
    autoCreateViewport: true,
    launch: function () {
    	  // 页面加载完成之后执行  //当前窗口存入内存方便新增报错关闭时刷新主表数据
    	parent.allChildrenPanelMap.put("web_1200001", Ext.getCmp('priceEvnetViewId'));
    	Ext.getCmp('priceEvnetViewId').tabChange();
    }
});
