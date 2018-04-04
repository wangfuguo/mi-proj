Ext.define("Miser.view.Viewport", {
	id : 'priceEvnetAddViewId',
    extend: "Ext.container.Viewport",
    layout: 'fit',
    items: [{
        xtype:"priceEvnetAddWin"
    }],
    getPriceEvnetAddWin : function(){
    	return this.items.get(0);
    },
    tabChange : function(){
    	// 刷新数据
    	if(parent.allChildrenParamsMap.get('success')){
    		
    		this.getPriceEvnetGrid().getStore().reload();
    	}
    	parent.allChildrenParamsMap.put('success', undefined);
    }
});
