var addPriceEvnetMask;
var resComboAreaScope = 'SEND';//默认使用发货区域
Ext.application({
    name: "Miser",
    appFolder: '../scripts/discount/PriceEvnetAdd',
    controllers: ["PriceEvnetAdd"],
    /*autoCreateViewport: true,*/
    autoCreateViewport: true,
    launch: function () {
    	// LOADMASK初始化
    	addPriceEvnetMask = new Ext.LoadMask({
    	    msg    : '数据保存中...',
    	    target : Ext.getCmp('priceEvnetAddViewId')
    	});
    	
    	 // 如果cId不为空，则为修改活动信息
    	if(!Ext.isEmpty(cId)){
    		var params = {};
    		var priceEvnetEntity = {};
    		priceEvnetEntity.id = cId;
    		params.priceEvnetEntity = priceEvnetEntity;
    		miser.requestJsonAjax('priceEvnetAction!queryPriceEvnetrInfoById.action', params, 
    				function(response){
    					if(response.success){
    						var priceEvnetEntity = response.priceEvnetEntity;
    						// 组装参数
    						var form = Ext.getCmp('priceEvnetAddViewId').getPriceEvnetAddWin().getForm();
    						// 主键
    						//设置复制新增id为空
//    						miser.log('ds'+lockView);
					    	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
					    		form.findField('cId').setValue('');
					    		form.findField('effectiveTime').setValue(Ext.Date.format(new Date(startDate),'Y-m-d H:i:s'));
	    				    	form.findField('invalidTime').setValue(Ext.Date.format(new Date(endDate),'Y-m-d H:i:s'));
					    	}else{
					    		form.findField('cId').setValue(cId);
					    		form.findField('effectiveTime').setValue(Ext.Date.format(new Date(priceEvnetEntity.effectiveTime),'Y-m-d H:i:s'));
	    				    	form.findField('invalidTime').setValue(Ext.Date.format(new Date(priceEvnetEntity.invalidTime),'Y-m-d H:i:s'));
					    	}
					    	
					    	//设置区域范围为到货范围的
					    	priceEventCorpSubss = priceEvnetEntity.priceEventCorpSubs;
					    	 for(var i = 0; i < priceEventCorpSubss.length; i++){
					    		 var sub = new Object();
				            		sub.corpType = priceEventCorpSubss[i].corpType;
				            		 sub.orgCode = priceEventCorpSubss[i].orgCode;
				                     sub.eventCode = eventCode;
				                 	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd')
				                		sub.id = '';
				                	else
				                		sub.id = priceEventCorpSubss[i].eventCorpId;
				            		priceEventCorpSubs.push(sub);
 							  }
    						
    						//基本信息
    						form.findField('eventCode').setValue(priceEvnetEntity.eventCode);
    					   	form.findField('eventName').setValue(priceEvnetEntity.eventName);
    				    	form.findField('remark').setValue(priceEvnetEntity.remark);
    				    	//设置活动渠道
    				    	var priceEventOrderchannelSubs = priceEvnetEntity.priceEventOrderchannelSubs;
    				    	var array = Ext.getCmp('lblName').items; 
    						  array.each(function(item){ 
    							  //渠道集合
    							  for(var i = 0; i < priceEventOrderchannelSubs.length; i++){
    								//修改有就勾选
        							  if(item.inputValue == priceEventOrderchannelSubs[i].channelCode){  
        								  item.setValue(true);
        					           } 
    							  }
    					      });
    						  
    						//设置线路
    						var priceEventRouteSubs = priceEvnetEntity.priceEventRouteSubs;
					    	var storeline = Ext.getCmp('priceEventLineListId').getStore();
					    	storeline.reload({
								'pdeParentId':priceEvnetEntity.eventCode
							});
//					    	var countline = storeline.getCount();
//					    	//设置线路id为空
//					    	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
//					    		 for(var i = 0; i < priceEventRouteSubs.length; i++){
//						    		 var data = [{'line_id':'','sendPriceCity': priceEventRouteSubs[i].sendPriceCity,  
//					    	              'arrivalPriceCity': priceEventRouteSubs[i].arrivalPriceCity}]; 
//						    		storeline.insert(countline+1,data);
//	 							  }
//					    	}else{
//					    		 for(var i = 0; i < priceEventRouteSubs.length; i++){
//						    		 var data = [{'line_id':priceEventRouteSubs[i].id,'sendPriceCity': priceEventRouteSubs[i].sendPriceCity,  
//					    	              'arrivalPriceCity': priceEventRouteSubs[i].arrivalPriceCity}]; 
//						    		storeline.insert(countline+1,data);
//	 							  }
//					    	}
					    	
					    	//设置客户
    						var priceEventCustomerSubs = priceEvnetEntity.priceEventCustomerSubs;
					    	var storecustomer = Ext.getCmp('priceCustomerList').getStore();
					    	var countcustomer = storecustomer.getCount();
//					    	 miser.log(countcustomer);
					    	//设置客户id为空
					    	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
					    		 for(var i = 0; i < priceEventCustomerSubs.length; i++){
						    		 var data = [{'cus_id':'','customerCode': priceEventCustomerSubs[i].customerCode,  
					    	              'customerName': priceEventCustomerSubs[i].customerName}]; 
						    		 storecustomer.insert(countcustomer+1,data);
//					    			 var r = Ext.create('Miser.model.PriceCustomerEntity');
//					    			 r.customerCode = priceEventCustomerSubs[i].customerCode;
//					    			 r.customerName = priceEventCustomerSubs[i].customerName;
//					    			 r.cus_id = '';
//					    			 storecustomer.insert(countcustomer+1, r);
	 							  }
					    	}else{
					    		 for(var i = 0; i < priceEventCustomerSubs.length; i++){
						    		 var data = [{'cus_id':priceEventCustomerSubs[i].id,'customerCode': priceEventCustomerSubs[i].customerCode,  
					    	              'customerName': priceEventCustomerSubs[i].customerName}]; 
						    		 storecustomer.insert(countcustomer+1,data);
//					    			 var r = Ext.create('Miser.model.PriceCustomerEntity');
//					    			 r.customerCode = priceEventCustomerSubs[i].customerCode;
//					    			 r.customerName = priceEventCustomerSubs[i].customerName;
//					    			 r.cus_id = priceEventCustomerSubs[i].id;
//					    			 storecustomer.insert(countcustomer+1, r);
	 							  }
					    	}
					    	
					    	//设置优惠段
					    	var priceEventDiscountSubs = priceEvnetEntity.priceEventDiscountSubs;
					    	Ext.getCmp('priceSubListId').getStore().reload({
								'pdeParentId':priceEvnetEntity.eventCode
							});
//					    	var storelist = Ext.getCmp('priceSubListId').getStore();
//					    	var countlist = storelist.getCount();
					    	//设置优惠段id为空
//					    	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd'){
//					    		for(var i = 0; i < priceEventDiscountSubs.length; i++){
//						    		var data = [{'sub_id':'',
//						    			'transTypeCode':priceEventDiscountSubs[i].transTypeCode,
//						    			'freightSectionCode': priceEventDiscountSubs[i].freightSectionCode,  
//			    	              		'addSectionCode': priceEventDiscountSubs[i].addSectionCode,
//			    	              		'fuelSectionCode': priceEventDiscountSubs[i].fuelSectionCode,
//			    	              		'pickupSectionCode': priceEventDiscountSubs[i].pickupSectionCode,
//			    	              		'deliverySectionCode': priceEventDiscountSubs[i].deliverySectionCode,
//			    	              		'upstairSectionCode': priceEventDiscountSubs[i].upstairSectionCode,
//			    	              		'insuranceRateSectionCode': priceEventDiscountSubs[i].insuranceRateSectionCode,
//			    	              		'insuranceSectionCode': priceEventDiscountSubs[i].insuranceSectionCode,
//			    	              		'paperSectionCode': priceEventDiscountSubs[i].paperSectionCode,
//			    	              		'smsSectionCode': priceEventDiscountSubs[i].smsSectionCode,
//			    	              		'collectionRateSectionCode': priceEventDiscountSubs[i].collectionRateSectionCode,
//			    	              		'collectionSectionCode': priceEventDiscountSubs[i].collectionSectionCode}]; 
//						    		storelist.insert(countlist+1,data);
//								  }
//					    	}else{
//					    		for(var i = 0; i < priceEventDiscountSubs.length; i++){
//						    		var data = [{'sub_id':priceEventDiscountSubs[i].id,
//						    			'transTypeCode':priceEventDiscountSubs[i].transTypeCode,
//						    			'freightSectionCode': priceEventDiscountSubs[i].freightSectionCode,  
//			    	              		'addSectionCode': priceEventDiscountSubs[i].addSectionCode,
//			    	              		'fuelSectionCode': priceEventDiscountSubs[i].fuelSectionCode,
//			    	              		'pickupSectionCode': priceEventDiscountSubs[i].pickupSectionCode,
//			    	              		'deliverySectionCode': priceEventDiscountSubs[i].deliverySectionCode,
//			    	              		'upstairSectionCode': priceEventDiscountSubs[i].upstairSectionCode,
//			    	              		'insuranceRateSectionCode': priceEventDiscountSubs[i].insuranceRateSectionCode,
//			    	              		'insuranceSectionCode': priceEventDiscountSubs[i].insuranceSectionCode,
//			    	              		'paperSectionCode': priceEventDiscountSubs[i].paperSectionCode,
//			    	              		'smsSectionCode': priceEventDiscountSubs[i].smsSectionCode,
//			    	              		'collectionRateSectionCode': priceEventDiscountSubs[i].collectionRateSectionCode,
//			    	              		'collectionSectionCode': priceEventDiscountSubs[i].collectionSectionCode}]; 
//						    		storelist.insert(countlist+1,data);
//								  }
//					    	}
					    	//设置按钮禁用
					    	if(!Ext.isEmpty(lockView) && lockView == 'lock'){
					    		Ext.getCmp('tbar_add').disable();
					    		Ext.getCmp('tbar_edit').disable();
					    		Ext.getCmp('tbar_del').disable();
					    		Ext.getCmp('but_sumbit').disable();
					    		Ext.getCmp('but_canenl').disable();
					    		Ext.getCmp('but_addCity').disable();
					    		Ext.getCmp('but_import').disable();
					    		Ext.getCmp('tbar_cus_add').disable();
					    		Ext.getCmp('tbar_cus_edit').disable();
					    		Ext.getCmp('tbar_cus_del').disable();
					    	}
    					}else{
    						Ext.MessageBox.alert('提示','查询活动信息失败'); 
    					}
    				}, 
    				function(){Ext.MessageBox.alert('提示','查询活动信息失败'); });
    	}
    	
    	//add by dengyin 2016-4-21 15:32:59 为 查看详细页面设置查询功能
    	
    	//判断当前是否为 查询请求进入
    	if(Ext.isEmpty(cId) == false && lockView == "lock"){
    		var addCityBtn = Ext.getCmp('but_addCity');
    		addCityBtn.setVisible(false);
    		
    		var cityLineForm = addCityBtn.up('form').getForm();
    		var startPriceCityObj = cityLineForm.findField("startPriceCityselector");
    		startPriceCityObj.setVisible(false);
    		
    		var arrivePriceCityObj = cityLineForm.findField("arrivePriceCityselector");
    		arrivePriceCityObj.setVisible(false);
    		
    		var startArriveCityInputObj = Ext.getCmp('startArriveCityInput');
    		var startArriveCitySearchBtnObj = Ext.getCmp('startArriveCitySearchBtn');
    		
    		startArriveCityInputObj.setVisible(true);
    		startArriveCitySearchBtnObj.setVisible(true);
    		
    		
    	} else {
    		var addCityBtn = Ext.getCmp('but_addCity');
    		addCityBtn.setVisible(true);
    		
    		var cityLineForm = addCityBtn.up('form').getForm();
    		var startPriceCityObj = cityLineForm.findField("startPriceCityselector");
    		startPriceCityObj.setVisible(true);
    		
    		var arrivePriceCityObj = cityLineForm.findField("arrivePriceCityselector");
    		arrivePriceCityObj.setVisible(true);
    		
    		var startArriveCityInputObj = Ext.getCmp('startArriveCityInput');
    		var startArriveCitySearchBtnObj = Ext.getCmp('startArriveCitySearchBtn');
    		
    		startArriveCityInputObj.setVisible(false);
    		startArriveCitySearchBtnObj.setVisible(false);
    		
    	}
    		
    	//end by dengyin 2016-4-21 15:32:59
    }
});
