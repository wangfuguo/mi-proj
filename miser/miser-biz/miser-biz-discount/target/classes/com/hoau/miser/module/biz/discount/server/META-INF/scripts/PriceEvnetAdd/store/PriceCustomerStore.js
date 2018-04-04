/**
 * 线路store
 */
Ext.define('Miser.store.PriceCustomerStore',{
    extend: "Ext.data.Store",
    model: "Miser.model.PriceCustomerEntity",
    pageSize : 999999999,
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
		}
	}
});