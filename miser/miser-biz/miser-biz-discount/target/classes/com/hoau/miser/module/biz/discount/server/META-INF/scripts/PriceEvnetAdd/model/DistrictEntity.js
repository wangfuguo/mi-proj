/**
 * 组织信息
 */
Ext.define('Miser.model.DistrictEntity', {
	 extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//组织编码
        type : 'string'
    },{
        name : 'name',//组织名称
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
    	name : 'simplePinyin',//拼音简写（首字母）
        type : 'string'
    },{
        name : 'parentName',//上级组织名称
        type : 'string'
    },{
        name : 'parentCode',//上级组织标编码
        type : 'string'
    },{
        name : 'active',//是否可用
        type : 'string'
    }]
});