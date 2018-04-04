/**
 * 线路实体
 */
Ext.define('Miser.model.PriceEventLineEntity', {
	 extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'sendPriceCity',//出发城市
        type : 'string'
    },{
        name : 'sendPriceCityName',//出发城市
        type : 'string'
    },{
        name : 'arrivalPriceCity',//到达城市
        type : 'string'
    },{
        name : 'arrivalPriceCityName',//到达城市
        type : 'string'
    }]
});