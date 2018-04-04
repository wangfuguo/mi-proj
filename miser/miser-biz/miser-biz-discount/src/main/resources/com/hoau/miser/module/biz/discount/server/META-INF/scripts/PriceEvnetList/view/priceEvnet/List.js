/**
 * 客户信息GRID
 */
Ext.define("Miser.view.priceEvnet.List", {
    extend: "Ext.grid.Panel",
    alias: 'widget.priceEvnetlist',
    store: "PriceEvnet",
    region : 'center',
    emptyText : '没有查询结果,换个查询条件试试.',
    width : '100%',
    viewConfig:{
		enableTextSelection:true
	},
    selModel : Ext.create('Ext.selection.CheckboxModel'),
    columns : {
		defaults: {
		    align : 'left'
		},
		items : [
		    {text : '序号',	width : 50,	xtype : 'rownumberer'},
	        { text: '活动名称', dataIndex: 'eventName', flex:2},
	        { text: '活动开始时间', dataIndex: 'effectiveTime', flex:1.5, xtype : 'datecolumn', format : 'Y-m-d H:i:s'},
			{ text: '活动结束时间', dataIndex: 'invalidTime', flex:1.5, xtype : 'datecolumn', format : 'Y-m-d H:i:s'},
			{ text: '活动状态', dataIndex: 'state', flex:1,renderer: stateRenderer},
			{ text: '创建人', dataIndex: 'createUser', flex:1},
			{ text: '创建时间', dataIndex: 'createDate',flex:1.5, xtype : 'datecolumn', format : 'Y-m-d H:i:s'},
			{ text: '最后操作人', dataIndex: 'modifyUser'},
			{ text: '最后操作时间', dataIndex: 'modifyDate',flex:1.5, xtype : 'datecolumn', format : 'Y-m-d H:i:s'}
		 ]
    },
    pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
				store : this.store,
				dock: 'bottom',
		        displayInfo: true
			});
		}
		return this.pagingToolbar;
	},
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.bbar = me.getPagingToolbar();
		me.callParent([ cfg ]);
	}
	,listeners:{
		  itemdblclick:function(dataview, record, item, index, e){
			// 查看活动
		    	window.parent.delTabpanel('101003');
		    	var cId = record.data.id;
		    	var eventCode = record.data.eventCode;
		    	var url = '/miser-biz-web/discount/priceEvnetAdd.action?cId=' + cId+'&lock=lock&eventCode='+eventCode;
		    	window.parent.initTabpanel('101003', '查看活动信息', url, true);
		  }
	},tbar  : [ {
					text : '新增',
					xtype:'addbutton',
					width:80,
					action : 'add'
				},{
					text : '复制活动并新增',
					xtype:'addbutton',
					width : 140,
					action : 'copyAdd'
				},{
					text : '修改',
					action : 'update',
					width:80,
					xtype : 'updatebutton',
					id:'update_id'
				},{
					text : '作废',
					id:'delete_id',
					width:80,
					xtype : 'deletebutton',
					action : 'delete'
				},{
					text : '强制终止',
					action : 'mandatory',
					xtype : 'deletebutton',
					width : 100,
					id:'mandatory_id',
					hidden : !discount.priceEvnet.isPermission('/miser-biz-web/discount/priceEvnet/forceStop.action')
				},
				 {
					text : '查看详细',
					xtype : 'searchbutton',
					width : 100,
					action : 'selectdetail'
				}
			]
});

/**
 * 活动状态渲染功能
 * 
 * @param value
 * @returns
 * @author 275636
 * @date 2016-1-06
 * @update
 */
function stateRenderer(value){
	return getDataDictionary().rendererSubmitToDisplay(value, "ACTIVITY_STATE");
}
