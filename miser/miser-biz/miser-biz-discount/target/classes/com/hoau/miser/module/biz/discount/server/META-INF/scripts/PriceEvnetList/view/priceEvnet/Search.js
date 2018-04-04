/**
 * 客户信息查询FORM
 */
Ext.define("Miser.view.priceEvnet.Search", {
	extend : "Ext.form.Panel",
	title : '查询条件',
	frame: true,
	collapsible: true,
	alias : 'widget.priceEvnetSearch',
	width : '100%',
	//height : 70,
	layout : 'column',
	region : 'north',
	defaults : {
		//msgTarget : 'qtip',
		margin : '5 0 5 0 ',
		labelWidth : 70,
		columnWidth : 0.195,
		labelAlign : 'right'
	},
	defaultType : 'textfield',
	items : [
			{
				fieldLabel : '活动名称',
				name : 'eventName'
			},
			{
				fieldLabel : '活动状态',
				name : 'eventState',
				xtype : 'combo',
				store : getDataDictionary().getDataDictionaryStore(
						'ACTIVITY_STATE', null, null, null),
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				matchFieldWidth : false,
				editable : false
			},{
				fieldLabel : '有效活动时间点',
				labelWidth : 100,
				name : 'effectiveTime',
				xtype : 'datefield',
				format : 'Y-m-d',
				value : startDate
			}],
	buttons : [ 
				, {
					text : '查询',
					action : 'select'
				}, {
					text : '重置',
					action : 'reset'
				} ]
});
