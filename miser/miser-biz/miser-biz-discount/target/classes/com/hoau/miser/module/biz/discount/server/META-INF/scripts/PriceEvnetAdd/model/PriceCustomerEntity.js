/**
 * 线路实体
 */
Ext.define('Miser.model.PriceCustomerEntity', {
	 extend: 'Ext.data.Model',
    fields : [{
        name : 'cus_id',
        type : 'string'
    },{
        name : 'customerCode',//客户编码
        type : 'string'
    },{
        name : 'customerName',//客户名称
        type : 'string'
    }]
});