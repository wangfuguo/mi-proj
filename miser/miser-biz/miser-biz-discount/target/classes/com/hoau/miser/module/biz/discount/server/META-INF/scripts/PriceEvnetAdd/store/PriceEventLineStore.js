/**
 * 线路store
 */
Ext.define('Miser.store.PriceEventLineStore',{
    extend: "Ext.data.Store",
    model: "Miser.model.PriceEventLineEntity",
    pageSize : 999999999,
    proxy: {
        type: 'ajax',
        url: 'priceEvnetAction!queryLineSubListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'routeSubEntities',
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