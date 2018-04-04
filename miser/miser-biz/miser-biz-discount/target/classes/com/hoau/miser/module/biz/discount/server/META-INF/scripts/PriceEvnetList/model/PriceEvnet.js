/**
 * 客户信息MODEL
 */
Ext.define('Miser.model.PriceEvnet', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id'},
        { name: 'eventCode'},//活动编码
        { name: 'eventName'},//活动名称
        { name: 'effectiveTime',type : 'date', dateFormat:'time'},//活动开始时间
        { name: 'invalidTime',type : 'date', dateFormat:'time'},//活动结束时间
        { name: 'state'},//活动状态
        { name: 'createUser'},//创建人
        { name: 'createDate',type : 'date', dateFormat:'time'},//创建时间
        { name: 'modifyUser'},//最后操作人
        { name: 'modifyDate',type : 'date', dateFormat:'time'}//最后操作时间
    ]
});