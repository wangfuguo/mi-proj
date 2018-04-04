var priceEventCorpSubs = new Array();
/**
 * 活动管理Controller
 */
Ext.define('Miser.controller.PriceEvnetAdd', {
    extend: 'Ext.app.Controller',
    stores: ['PriceEvnetSub','districtTreeStore','PriceEventLineStore','PriceCustomerStore'],
    models: ['PriceEvnetSub','DistrictEntity','PriceEventLineEntity','PriceCustomerEntity'],
    views: ['Viewport','priceEvnetAdd.Add','priceEvnetAdd.PriceSubList','priceEvnetAdd.DistrictTreePanel','priceEvnetAdd.PriceEventLineList','priceEvnetAdd.Import','priceEvnetAdd.PriceCustomerList'],
    init: function () {
        this.control({
        	'priceEvnetAddWin button[action=sumbit]': {
                click: this.sumbitPriceEvnetAddWin
            },'priceEvnetAddWin button[action=canenl]': {
                click: this.canenlPriceEvnetAddWin
            },'priceEvnetAddWin button[action=import]': {
                click: this.importWin
            }
        });
    },
    sumbitPriceEvnetAddWin : function(btn){
    	var form = Ext.getCmp('priceEvnetAddViewId').getPriceEvnetAddWin().getForm();
    	if(!form.isValid()){
			Ext.MessageBox.alert('提示','请完善必填字段信息'); 
			return;
		}
    	var params = {};
    	var priceEvnetEntity = {};
    	var priceEventDiscountSubs = new Array();
    	var priceEventOrderchannelSubs = new Array();
    	var priceEventRouteSubs = new Array();
    	var priceEventCustomerSubs = new Array();
    	priceEvnetEntity.eventCode = form.findField('eventCode').getValue();
    	priceEvnetEntity.eventName = form.findField('eventName').getValue();
    	priceEvnetEntity.effectiveTime = form.findField('effectiveTime').getValue();
    	priceEvnetEntity.invalidTime = form.findField('invalidTime').getValue();
    	priceEvnetEntity.remark = form.findField('remark').getValue();
    	priceEvnetEntity.isForceColse = 'Y';
    	
    	if(priceEvnetEntity.invalidTime <= priceEvnetEntity.effectiveTime)
		{
			//'失效时间 必须大于 生效时间'
			miser.showErrorMes('结束时间必须大于开始时间');
			return;
		}
    	
//    	alert(Ext.getCmp('districtTreePanel').getChecked());
    	//设置区域范围
    	var selNodes = Ext.getCmp('districtTreePanel').getChecked();
    	//遍历获取所有的节点数据
    	Ext.each(selNodes, function (node) {
    	                //子节点 也就是用户节点
//    	       if (node.data.leaf) {
            var sub = new Object();
            sub.corpType = Ext.getCmp('districtTreePanel').getResComboAreaScope().getValue();
//            miser.log(JSON.stringify(node.data.entity.code));
            sub.orgCode = node.data.id;
            sub.eventCode = eventCode;
            //针对区域范围id清空
        	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd')
        		sub.id = '';
        	else
        		sub.id = node.data.entity.eventCorpId;
            priceEventCorpSubs.push(sub);
//    	    }
    	});
    	priceEvnetEntity.priceEventCorpSubs = unique(priceEventCorpSubs);
    	
    	//设置线路
    	var storeline = Ext.getCmp('priceEventLineListId').getStore();
    	for (var i = 0; i < storeline.getCount(); i++) {
			var record = storeline.getAt(i);
			var sub = new Object();
			sub.sendPriceCity = record.get('sendPriceCity');
			sub.arrivalPriceCity = record.get('arrivalPriceCity');
			sub.eventCode = eventCode;
//			if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
				sub.id = '';
//			}else
//				sub.id = record.get('id');
//            sub.id = record.get('id');
//            miser.log(record);
			priceEventRouteSubs.push(sub);
		}
    	priceEvnetEntity.priceEventRouteSubs = priceEventRouteSubs;
    	
    	//设置客户
    	var storecustomer = Ext.getCmp('priceCustomerList').getStore();
    	for (var i = 0; i < storecustomer.getCount(); i++) {
			var record = storecustomer.getAt(i);
			var sub = new Object();
			if(record.get('customerCode') == '' || record.get('customerName') == ''){
				Ext.MessageBox.alert('提示','第'+(i+1)+'行客户信息不能为空！'); 
				return;
			}
			sub.customerCode = record.get('customerCode');
			sub.customerName = record.get('customerName');
			sub.eventCode = eventCode;
            sub.id = record.get('cus_id');
//            miser.log(record);
            priceEventCustomerSubs.push(sub);
		}
    	priceEvnetEntity.priceEventCustomerSubs = priceEventCustomerSubs;
    	
    	//设置优惠分段
    	var storelist = Ext.getCmp('priceSubListId').getStore();
    	for (var i = 0; i < storelist.getCount(); i++) {
			var record = storelist.getAt(i);
			var sub = new Object();
			if(record.get('transTypeCode') == '' || record.get('transTypeCode') == ''){
				Ext.MessageBox.alert('提示','第'+(i+1)+'行优惠分段运输类型不能为空！'); 
				return;
			}
			sub.transTypeCode = record.get('transTypeCode');
			sub.freightSectionCode = record.get('freightSectionCode');
			sub.addSectionCode = record.get('addSectionCode');
			sub.fuelSectionCode = record.get('fuelSectionCode');
			sub.pickupSectionCode = record.get('pickupSectionCode');
			sub.deliverySectionCode = record.get('deliverySectionCode');
			sub.upstairSectionCode = record.get('upstairSectionCode');
			sub.insuranceRateSectionCode = record.get('insuranceRateSectionCode');
			sub.insuranceSectionCode = record.get('insuranceSectionCode');
			sub.paperSectionCode = record.get('paperSectionCode');
			sub.smsSectionCode = record.get('smsSectionCode');
			sub.collectionRateSectionCode = record.get('collectionRateSectionCode');
			sub.collectionSectionCode = record.get('collectionSectionCode');
			sub.eventCode = eventCode;
//			if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
				sub.id = '';
//			}else
//				sub.id = record.get('id');
//			sub.id = record.get('eventCode');
			priceEventDiscountSubs.push(sub);
		}
    	priceEvnetEntity.priceEventDiscountSubs = priceEventDiscountSubs;
    	
    	//设置活动订单
    	 var array = Ext.getCmp('lblName').items; 
		  array.each(function(item){  
			  var sub = new Object();
			  if(item.checked){  
				  sub.channelCode=item.inputValue;
				  sub.eventCode = eventCode;
				  priceEventOrderchannelSubs.push(sub);
	           }  
	      }); 
		priceEvnetEntity.priceEventOrderchannelSubs = priceEventOrderchannelSubs;
    	priceEvnetEntity.active='Y';
    	priceEvnetEntity.id = form.findField('cId').getValue();
    	params.priceEvnetEntity = priceEvnetEntity;
//    	miser.log(JSON.stringify(params));
    	// 提示正在保存中
    	addPriceEvnetMask.show();
    	miser.requestJsonAjax('priceEvnetAction!addPriceEvnet.action', params, 
		function(response){
			// 提示正在保存中
    		addPriceEvnetMask.hide();
			if(response.success){
				Ext.MessageBox.alert('提示','保存活动信息成功', function(){
					priceEventCorpSubs = new Array();
					parent.allChildrenParamsMap.put('success', 'success');
					window.parent.initTabpanel('web_1200001', '活动管理', '/miser-biz-web/discount/priceEvnetIndex.action', true);
					// 关闭页面
					if(Ext.isEmpty(priceEvnetEntity.id)){
						parent.delTabpanel('101002');
					}else{
						parent.delTabpanel('101003');
					}
					
				}); 
			}else{
				Ext.MessageBox.alert('提示','保存活动信息失败'); 
			}
		}, 
		function(response){
			// 提示正在保存中
			addPriceEvnetMask.hide();
			Ext.MessageBox.alert('提示', response.message); 
		});
    	
    },
    canenlPriceEvnetAddWin : function(btn){
    	miser.requestJsonAjax('priceEvnetAction!deleteCorpImportTemp.action', null, 
    			function(response){
    				parent.delTabpanel('101002');
    			}, 
    			function(response){
    				Ext.MessageBox.alert('提示', response.message); 
    			});
//    	btn.up('form').getForm().reset();
    },importWin : function(){
    	var win = Ext.widget("priceEvnetImportWin"); 
//    	parent.allChildrenPanelMap.put("priceEvnetImportWin", win);
        win.show();
    }
});

function unique(arr) {
    var result = [], isRepeated;
    for (var i = 0, len = arr.length; i < len; i++) {
        isRepeated = false;
        for (var j = 0, len1 = result.length; j < len1; j++) {
            if (arr[i].corpType == result[j].corpType && arr[i].orgCode == result[j].orgCode) {   
                isRepeated = true;
                break;
            }
        }
        if (!isRepeated) {
            result.push(arr[i]);
        }
    }
    return result;
 }
