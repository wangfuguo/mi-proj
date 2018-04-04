/**
 * 客户信息STORE
 */
Ext.define("Miser.store.PriceEvnetSub", {
    extend: "Ext.data.Store",
    model: "Miser.model.PriceEvnetSub",
    pageSize : 999999999,
    proxy: {
        type: 'ajax',
        url: 'priceEvnetAction!queryPDESubListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'discountSubEntities',
            totalProperty: 'totalCount'
        }
    },
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var parentId = operation.pdeParentId;
			if(parentId != ''  ||  parentId != undefined)
			{
				var params = {
						'parentId' :parentId
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});