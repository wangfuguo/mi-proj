/**
 * 优惠分段明细信息MODEL
 */
Ext.define('Miser.model.PriceEvnetSub', {
    extend: 'Ext.data.Model',
    fields: [
//        { name: 'sub_id'},
		{ name: 'id',type:'string'},
		{ name: 'eventCode',type:'string'},
        { name: 'transTypeCode',type:'string'},//运输类型
        { name: 'freightSectionCode',type:'string'},//运费分段折扣编码
        { name: 'addSectionCode',type:'string'},//附加费分段折扣编码
        { name: 'fuelSectionCode',type:'string'},//燃油费分段折扣编码
        { name: 'pickupSectionCode',type:'string'},//提货费分段折扣编码
        { name: 'deliverySectionCode',type:'string'},//送货费分段折扣编码
        { name: 'upstairSectionCode',type:'string'},//上楼费分段折扣编码
        { name: 'insuranceRateSectionCode',type:'string'},//保价率分段折扣编码
        { name: 'insuranceSectionCode',type:'string'},//保价费分段折扣编码
        { name: 'paperSectionCode',type:'string'},//工本费分段折扣编码
        { name: 'smsSectionCode',type:'string'},//信息费分段折扣编码
        { name: 'collectionRateSectionCode',type:'string'},//代收手续费率分段折扣编码
        { name: 'collectionSectionCode',type:'string'},//代收手续费分段折扣编码
        
        { name: 'transTypeName',type:'string'},//运输类型
        { name: 'freightSectionName',type:'string'},//运费分段折扣编码
        { name: 'addSectionName',type:'string'},//附加费分段折扣编码
        { name: 'fuelSectionName',type:'string'},//燃油费分段折扣编码
        { name: 'pickupSectionName',type:'string'},//提货费分段折扣编码
        { name: 'deliverySectionName',type:'string'},//送货费分段折扣编码
        { name: 'upstairSectionName',type:'string'},//上楼费分段折扣编码
        { name: 'insuranceRateSectionName',type:'string'},//保价率分段折扣编码
        { name: 'insuranceSectionName',type:'string'},//保价费分段折扣编码
        { name: 'paperSectionName',type:'string'},//工本费分段折扣编码
        { name: 'smsSectionName',type:'string'},//信息费分段折扣编码
        { name: 'collectionRateSectionName',type:'string'},//代收手续费率分段折扣编码
        { name: 'collectionSectionName',type:'string'}//代收手续费分段折扣编码
    ]
});