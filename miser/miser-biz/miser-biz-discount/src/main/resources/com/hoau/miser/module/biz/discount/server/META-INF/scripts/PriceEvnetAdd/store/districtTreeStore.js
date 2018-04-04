/**
 * 定义功能树的store
 */
Ext.define('Miser.store.districtTreeStore',{
	extend: 'Ext.data.TreeStore',
	root: {
		text:'区域范围',
		id : 'Root',
		expandable : true
		,expanded: true
	},
	proxy:{
		type:'ajax',
		url:'../discount/priceEvnetAction!loadDepartmentTree.action',
		actionMethods:'POST',
		reader:{
			type:'json',
			rootProperty:'nodes'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var params = {
					'orgEntity.eventCode' :eventCode,
					'orgEntity.corpType' :resComboAreaScope
			};
			Ext.apply(store.proxy.extraParams, params);
		}
	}
});