Ext.define("Miser.view.Viewport", {
	id : 'priceEvnetViewId',
    extend: "Ext.container.Viewport",
    layout: "border",
    items: [{
        xtype:"priceEvnetSearch"
    },{
        xtype:"priceEvnetlist"
    }],
    getPriceEvnetSearchForm : function(){
    	return this.items.get(0);
    },
    getPriceEvnetGrid : function(){
    	return this.items.get(1);
    },
    tabChange : function(){
    	// 刷新数据
    	if(parent.allChildrenParamsMap.get('success')){
    		this.getPriceEvnetGrid().getStore().reload();
    	}
    	parent.allChildrenParamsMap.put('success', undefined);
    	
    }
});
