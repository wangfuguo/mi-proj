/**
 * 客户信息STORE
 */
Ext.define("Miser.store.PriceEvnet", {
    extend: "Ext.data.Store",
    model: "Miser.model.PriceEvnet",
    pageSize : 15,
    proxy: {
        type: 'ajax',
        url: 'priceEvnetAction!queryPriceEvnetInfo.action',
        reader: {
            type: 'json',
            rootProperty: 'priceEvnetList',
            totalProperty: 'totalCount'
        }
    },
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('priceEvnetViewId').getPriceEvnetSearchForm().getForm();
			//查询条件
			var eventName = queryForm.findField('eventName').getValue();
			var eventState = queryForm.findField('eventState').getValue();
			var effectiveTime = queryForm.findField('effectiveTime').getValue();
			if (queryForm != null) {
				var params = {
						'priceEvnetEntity.eventName' : eventName,
						'priceEvnetEntity.state' : eventState,
						'priceEvnetEntity.effectiveTime' : effectiveTime
					}
				Ext.apply(store.proxy.extraParams, params);  
			}
		}
	},
    autoLoad: false
});