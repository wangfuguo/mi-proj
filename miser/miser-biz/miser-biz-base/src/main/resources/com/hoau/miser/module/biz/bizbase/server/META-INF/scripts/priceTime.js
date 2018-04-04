/**
 * 查看活动详情
 * @param eventCode:活动编码
 * @param transTypeCode:运输类型编码
 */
function showEventDetail(eventCode,transTypeCode){
	var params = {
			'priceTimeVo':{
			    'eventCode':eventCode,
			    'priceTimeEntity':{
			    	'standardTransTypeCode':transTypeCode
			    }
			}
	};
	
	var curUrl = "priceTimeAction!eventViewById.action";
	
	var successFun = function(json) {
		
		if(!Ext.isEmpty(json.priceTimeVo.priceSectionEntityList)){
			
			//创建窗体
			var priceTimeViewWindow = Ext.create('Miser.biz.bizbase.PriceTimeViewWindow');
			
			//循环创建 Grid 数据明细
			for(var q = 0;q < json.priceTimeVo.priceSectionEntityList.length;q++){
				
				var priceSectionEntity = json.priceTimeVo.priceSectionEntityList[q];
				var priceSectionSubEntityArr = priceSectionEntity.subEntities;
				var priceSectionSubModelArr = [];
				
				for(var m=0;m<priceSectionSubEntityArr.length;m++){
					var curEntity = priceSectionSubEntityArr[m];
					
					//把后台返回的数据转换为 Model
					var curModel = Ext.create('Miser.model.bizbase.pricetime.PriceSectionSubModel',curEntity);
					priceSectionSubModelArr[m] = curModel;
				}
				
				//创建 Grid
				var priceSectionSubGrid = Ext.create('Miser.biz.bizbase.pricetime.priceSectionSubGrid',{
					'store':Ext.create('Miser.biz.bizbase.pricetime.PriceSectionSubStore',{
						'data':priceSectionSubModelArr
					})
				});
				
				//取出费用分段类型
				var sectionUsedType = priceSectionEntity.sectionedItem;
				var curTitle = '';
				switch(sectionUsedType){
				    case 'FUEL': curTitle = '燃油费'; break;
				    case 'SMS': curTitle = '信息费'; break;
				    case 'PAPER': curTitle = '工本费'; break;
				    case 'UPSTAIRS': curTitle = '上楼费'; break;
				    case 'EXTRA_FEE': curTitle = '附加费'; break;
				    case 'COLLECT_RATE': curTitle = '代收手续费率'; break;
				    case 'INSURANCE': curTitle = '保价率'; break;
				    case 'COLLECTION_RATE': curTitle = '代收货款手续费'; break;
				    case 'COLLECTION': curTitle = '代收货款'; break;
				    //case 'INSURANCE_RATE': curTitle = '保价率'; break;
				    case 'DELIVERY': curTitle = '送货费'; break;
				    case 'FREIGHT': curTitle = '运费'; break;
				    case 'PICKUP': curTitle = '提货费'; break;
				}
				
				//为 grid 设置标题
				priceSectionSubGrid.setTitle(curTitle + '分段明细：');
				
				//向 window 里添加当前 grid
				priceTimeViewWindow.items.add(priceSectionSubGrid);
			}
			
			priceTimeViewWindow.setHeight(json.priceTimeVo.priceSectionEntityList.length*200+100);
			priceTimeViewWindow.show();
			
		} else {
			miser.showInfoMsg('不存在对应明细数据');
		}
	};
	
	var failureFun = function(json) {
		if (Ext.isEmpty(json)) {
			miser.showErrorMes('请求超时');
		} else {
			var message = json.message;
			miser.showInfoMsg(message);
		}
	};
	
	miser.requestJsonAjax(curUrl,params, successFun, failureFun);
}

/**
 * 主界面第一行 点击 发送 窗体定义
 */
Ext.define('Miser.biz.bizbase.PriceTimeSmsSendWindow', {
	extend:'Ext.window.Window',
	closable:true,
	title:'短信发送',
	modal:true,
	parent:null,
	resizable:true,
	closeAction:true,
	priceTimeSmsSendForm:null,
	getPriceTimeSmsSendForm:function(){
		if (Ext.isEmpty(this.priceTimeSmsSendForm)) {
			this.priceTimeSmsSendForm = Ext.create('Miser.base.priceTime.SmsSendForm');
		}
		return this.priceTimeSmsSendForm;	
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [me.getPriceTimeSmsSendForm()];
        me.callParent([cfg]);
    }
});

/**
 * 主界面第一行 点击 发送 表单定义
 */
Ext.define('Miser.base.priceTime.SmsSendForm',{
	extend:'Ext.form.Panel',
	frame:true,
	header:false,  //用于屏蔽掉 标题栏
	width:500,
	height:150,
	collapsible:false,
	region:'north',
	priceTimeEntity:null,
	defaults:{
		labelWidth:0.3,
		columnWidth:0.6,
		margin:'8 10 5 10',
		labelAlign:'right'
	},
	constructor:function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[		         
			        {
						xtype: 'textfield',
						name: 'telephone',
						fieldLabel: '手机号',
						regex:/^[1]\d{10}$/
					},
					{
						xtype: 'button',
						text: '发送',
						name:'smsSendBtn',
						handler:function(){
							priceTimeInfoSmsSend(me);
						}
					}			
				]
			}			
		];				 
		me.callParent([ cfg ]);
	}
});

/**
 * 主界面 点击  发送后 展示 界面里的 短信发送
 * @param me 为当前表单
 */
function priceTimeInfoSmsSend(me){
	var smsTpl = "尊敬的客户：@出发省份@ @出发城市@ @出发区县@ 至 @到达省份@ @到达城市@ @到达区县@，@运输类型@，" +
			"重货 @重货@ 元/公斤，轻货  @轻货@ 元/立方米 ，最低 @最低@ 元" +
			"，预计第 @自提@ 天自提，第 @送货@ 天送货上门。以上报价以当地营业网点为准！退订回复TD【天地华宇】";
	
	var transTypeName = me.priceTimeEntity.standardTransTypeName;
	smsTpl = smsTpl.replace('@运输类型@',transTypeName);
	
	var standardWeightPrice = me.priceTimeEntity.standardWeightPrice;
	smsTpl = smsTpl.replace('@重货@',standardWeightPrice);
	
	var standardVolumePrice = me.priceTimeEntity.standardVolumePrice;
	smsTpl = smsTpl.replace('@轻货@',standardVolumePrice);
	
	var standardLowestFee = me.priceTimeEntity.standardLowestFee;
	smsTpl = smsTpl.replace('@最低@',standardLowestFee);
	
	var pickupPromDay = me.priceTimeEntity.pickupPromDay;
	if(Ext.isEmpty(pickupPromDay)){
		smsTpl = smsTpl.replace('，预计第 @自提@ 天自提','');	
	}else {
	   smsTpl = smsTpl.replace('@自提@',pickupPromDay);
	}
	
	var deliveryPromDay = me.priceTimeEntity.deliveryPromDay;
	if(Ext.isEmpty(deliveryPromDay)){
		smsTpl = smsTpl.replace('，第 @送货@ 天送货上门','');
	} else {
		smsTpl = smsTpl.replace('@送货@',deliveryPromDay);
	}
	
	
	var formObj = me.getForm();
	
    //手机号
    var telephoneVal = formObj.findField('telephone').getValue();
	
    var curUrl = "priceTimeAction!calculateSmsSend.action";
	var params = {
			'priceTimeVo':{
			    'priceTimeEntity':{
			    	'arriveAreaCode':me.priceTimeEntity.arriveAreaCode,
			    	'arriveCityCode':me.priceTimeEntity.arriveCityCode,
			    	'arriveProvinceCode':me.priceTimeEntity.arriveProvinceCode,
			    	'sendAreaCode':me.priceTimeEntity.sendAreaCode,
			    	'sendCityCode':me.priceTimeEntity.sendCityCode,
			    	'sendProvinceCode':me.priceTimeEntity.sendProvinceCode
			    },
			    'smsTpl':smsTpl,
			    'telephone':telephoneVal
			}
	};
    
    var successFun = function(json) {
    	var msg = json.message;
    	if(msg == "success"){
    		miser.showInfoMsg('发送成功');
    	} else {
    		miser.showErrorMes(msg);
    	}
    };

    var failureFun = function(json) {
        if (Ext.isEmpty(json)) {
            miser.showErrorMes('请求超时');
        } else {
            var message = json.message;
            miser.showInfoMsg(message);
        }
    };
    miser.requestJsonAjax(curUrl,params, successFun, failureFun);
}

/**
 * 点击 活动名称时 展示该活动对应的详情 窗体定义
 */
Ext.define('Miser.biz.bizbase.PriceTimeViewWindow', {
	extend:'Ext.window.Window',
	closable:true,
	modal:true,
	parent:null,
	title:'活动详情',
	resizable:false,
	closeAction:true,
	priceSectionEntityList: null,
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [];
        me.callParent([cfg]);
    }
});

/**
 * 点击 计算按钮时 展示窗体定义
 */
Ext.define('Miser.biz.bizbase.PriceTimeCalculateWindow', {
	extend:'Ext.window.Window',
	closable:true,
	modal:true,
	parent:null,
	resizable:false,
	closeAction:true,
	title:'价格计算',
	calculateForm:null,
	getCalculateForm:function(){
		if (Ext.isEmpty(this.calculateForm)) {
			this.calculateForm = Ext.create('Miser.base.priceTime.CalculateForm');
		}
		return this.calculateForm;		
	},
	priceTimeEntity:null,
	getPriceTimeEntity:function(){
		return this.priceTimeEntity;
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [me.getCalculateForm()];
        me.callParent([cfg]);
    },
    listeners:{
    	beforehide:function(me){
    		var formObj = me.getCalculateForm();
    		var item = formObj.items.items[3];
    		var calculateGrid = item.items.items[0];
    		calculateGrid.getStore().removeAll();   
    		
	    	var itemTmp = formObj.items.items[4];
	    	var totalPriceObj = itemTmp.items.items[1];
	    	totalPriceObj.setValue(0);
	    	
			//代收类型
			var collectDeliveryTypeObj = formObj.getForm().findField('collectDeliveryType');
			var collectDeliveryTypeRecord=Ext.data.Record({
				code: 0,
				name: '即日退'
			});
			
			collectDeliveryTypeObj.getStore().add(collectDeliveryTypeRecord);
			collectDeliveryTypeObj.setValue(0);	    	
    	},
    	beforeshow:function(me){
    		var formObj = me.getCalculateForm();
    		var item = formObj.items.items[3];
    		var calculateGrid = item.items.items[0];
    		calculateGrid.getStore().removeAll();
    		
	    	var itemTmp = formObj.items.items[4];
	    	var totalPriceObj = itemTmp.items.items[1];
	    	totalPriceObj.setValue(0);
	    	
			//代收类型
			var collectDeliveryTypeObj = formObj.getForm().findField('collectDeliveryType');
			var collectDeliveryTypeRecord=Ext.data.Record({
				code: 0,
				name: '即日退'
			});
			
			collectDeliveryTypeObj.getStore().add(collectDeliveryTypeRecord);
			collectDeliveryTypeObj.setValue(0);
    	}
    }
});

/**
 * 点击 计算时 窗体包含的 表单定义
 */
Ext.define('Miser.base.priceTime.CalculateForm',{
	extend:'Ext.form.Panel',
	frame:true,
	header:false,//用于屏蔽掉 标题栏
	width:800,
	height:350,
	collapsible:false,
	region:'north',
	defaults:{
		labelWidth:0.3,
		columnWidth:0.6,
		margin:'8 10 5 10',
		labelAlign:'right'
	},
	priceTimeEntity:null,
	getPriceTimeEntity:function(){
		return this.priceTimeEntity;
	},
	constructor:function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
					{
						xtype: 'numberfield',
						name: 'totalWeight',
						fieldLabel: ' 总重量',
						minValue: 0,
						value:0,
						allowBlank:false,
						listeners:{
							blur:function(obj,event,eOpts){
								calculate('totalWeight',obj.value,me);
							}
						}
					},
					{
						xtype: 'label',
                        html:"<div style='width:200px;'>&nbsp;&nbsp;</div>"
					},					
					{
						xtype: 'numberfield',
						name: 'totalVolume',
						fieldLabel: ' 总体积',
						minValue: 0,
						value:0,
						allowBlank:false,
						listeners:{
							blur:function(obj,event,eOpts){
								calculate('totalVolume',obj.value,me);
							}
						}						
					},
					{
						xtype: 'label',
                        html:"<div style='width:100px;'>&nbsp;&nbsp;</div>"
					}					
				]
			},
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
			        {
						xtype: 'numberfield',
						name: 'insuranceFee',
						fieldLabel: '保价金额',
						minValue: 0,
						value:0,
						allowBlank:false,
						listeners:{
							blur:function(obj,event,eOpts){
								calculate('insuranceFee',obj.value,me);
							}
						}						
					},
					{
						xtype: 'label',
                        html:"<div style='width:200px;'>&nbsp;&nbsp;</div>"
					},					
					{
						xtype: 'numberfield',
						name: 'collectionFee',
						fieldLabel: '贷收金额',
						minValue: 0,
						value:0,
						listeners:{
							blur:function(obj,event,eOpts){
								calculate('collectionFee',obj.value,me);
							}
						}
					},
					{
						name:'collectDeliveryType',
						allowBlank:false,
						xtype : 'dataDictionarySelector',
						termsCode : 'COLLECTDELIVERYTYPE',
						width:80,
						listeners:{
							select:function(obj,event,eOpts){
								calculate('collectDeliveryType',obj.value,me);
							}
						}
					}
				]
			},
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
			        {
						xtype: 'checkboxfield',
						name: 'receiveShipment',
						fieldLabel: '是否接货',
						//checked:true,
						listeners:{
							change:function(obj,newValue,oldValue,eOpts){
								calculate('receiveShipment',newValue,me);
							}
						}
					},
					{
						xtype: 'label',
                        html:"<div style='width:355px;'>&nbsp;&nbsp;</div>"
					},
					{
						xtype: 'checkboxfield',
						name: 'deliverShipment',
						fieldLabel: '是否送货',
                        //checked:true,
						listeners:{
							change:function(obj,newValue,oldValue,eOpts){
								calculate('deliverShipment',newValue,me);
							}
						}                        
					},
					{
						xtype: 'label',
                        html:"<div style='width:100px;'>&nbsp;&nbsp;</div>"
					}
				]
			},
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
				   Ext.create('Miser.biz.bizbase.pricetime.CalculateGrid')
				]
			},			
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
					{
						xtype: 'label',
                        html:"<div style='width:150px;'>&nbsp;&nbsp;</div>"
					},
			        {
						xtype: 'numberfield',
						name: 'totalFee',
						fieldLabel: '总金额',
						value:0,
						readOnly:true
					},
					{
						xtype: 'label',
                        html:"<div style='width:180px;'>&nbsp;&nbsp;</div>"
					},
					{
						xtype: 'button',
                        text:'发送短信',
						listeners:{
							click:function(){
								priceTimeShowSms(me);
							}
						} 
					}			
				]
			},
			{
				layout:'column',
				defaults:{
					labelWidth:70,
					labelAlign:"right"
				},
				items:[
					{
						xtype: 'label',
                        html:"<div style='width:200px;'>&nbsp;&nbsp;</div>"
					},				         
			        {
						xtype: 'textfield',
						name: 'telephone',
						fieldLabel: '手机号',
						hidden:true,
						regex:/^[1]\d{10}$/
					},
					{
						xtype: 'button',
						text: '发送',
						name:'smsSendBtn',
						hidden:true,
						handler:function(){
							priceCalculateSmsSend(me);
						}
					}			
				]
			}			
		];				 
		me.callParent([ cfg ]);
	}
});

//表单元素触发统一调用计算方法
function calculate(id,value,formObj){
	
	//取出当前点击行的记录集
	var priceTimeEntity = formObj.priceTimeEntity;
	
	//标准价格 重量单价
	var standardWeightPrice = priceTimeEntity.standardWeightPrice;
	
	//标准价格 体积单价
	var standardVolumePrice = priceTimeEntity.standardVolumePrice;
	
	//出发城市编码
	var standardSendPriceCity = priceTimeEntity.standardSendPriceCity;	
	
	//总重量
	var totalWeight = formObj.getForm().findField('totalWeight').getValue();
	
	//总体积
	var totalVolume = formObj.getForm().findField('totalVolume').getValue();
	
	//贷收金额
	var collectionFee = formObj.getForm().findField('collectionFee').getValue();
	
	//代收类型
	var collectDeliveryType = formObj.getForm().findField('collectDeliveryType').getValue();
	
	
	//若 总重量 总体积 都不为0 需要从后台计算出其最低的纯运费
	if(!Ext.isEmpty(totalVolume) || !Ext.isEmpty(totalWeight) ){
		
		if( totalVolume > 0 || totalWeight > 0){
			
			//取得 活动编码串
		    var eventCodeStr = priceTimeEntity.eventCodeStr;
		    var standardTransTypeCode = priceTimeEntity.standardTransTypeCode;
		    var standardWeightPrice = priceTimeEntity.standardWeightPrice;
		    var standardVolumePrice = priceTimeEntity.standardVolumePrice;
		    
		    var curUrl = "priceTimeAction!queryFreightAndExtraFee.action";
		    
		    var getFormObj = function(){
		    	return formObj;
		    };
		    
		    var params = {
		    		'priceTimeVo':{
		    			'priceTimeEntity':{
		    				'eventCodeStr':eventCodeStr,
		    				'standardTransTypeCode':standardTransTypeCode,
		    				'standardWeightPrice':standardWeightPrice,
		    				'standardVolumePrice':standardVolumePrice,
		    				'standardSendPriceCity':standardSendPriceCity
		    			},
		    			'totalWeight':totalWeight,
		    			'totalVolume':totalVolume,
		    			'collectDeliveryType':collectDeliveryType
		    		}
		    };
		    
		    var successFun = function(json){
		    	
		    	if(!Ext.isEmpty(json.priceTimeVo)){
		    		
		    		var calculateObj = {
		    				'detailFee':'开单金额',
		    				'freightFee':0,
		    				'smsFee':0,
		    				'paperFee':0,
		    				'collectionFee':0,
		    				'insuranceFee':0,
		    				'deliveryFee':0,
		    				'pickupFee':0
		    		};
		    		
		    		var formObj = getFormObj();
		    		
			    	//获取 纯运费
			    	var freightFee = json.priceTimeVo.freightFee;
			    	calculateObj['freightFee'] = freightFee;
			    	
			    	//获取 标准附加费集合
			    	var priceExtrafeeStandardEntityList = json.priceTimeVo.priceExtrafeeStandardEntityList;
			    	
			    	for(var n = 0; n < priceExtrafeeStandardEntityList.length; n ++){
			    		var curExtraFeeEntity = priceExtrafeeStandardEntityList[n];
			    		var curType = curExtraFeeEntity.type;
			    		
						switch(curType){
						    case 'SMS': //信息费
						    	calculateObj['smsFee'] = curExtraFeeEntity.money; 
						    	break;
						    case 'PAPER': //工本费
						    	calculateObj['paperFee'] = curExtraFeeEntity.money; 
						    	break;
						    case 'COLLECTION_RATE'://代收货款手续费
						    	
						    	var tmpCollectionFee = collectionFee * curExtraFeeEntity.money * 0.001;
						    	var collectionFeeFloor = Math.floor(tmpCollectionFee);
						    	if(tmpCollectionFee > collectionFeeFloor){
						    		calculateObj['collectionFee'] = collectionFeeFloor + 1; 
						    	} else {
						    		calculateObj['collectionFee'] = collectionFeeFloor; 
						    	}
						    	
						    	//最低费用校验
						    	if( 0 != curExtraFeeEntity.lowestFee 
						    			&& null != curExtraFeeEntity.lowestFee 
						    			&& undefined != curExtraFeeEntity.lowestFee
						    			&& "" != curExtraFeeEntity.lowestFee
						    			&& calculateObj['collectionFee'] < curExtraFeeEntity.lowestFee ){
						    		
						    		calculateObj['collectionFee'] = curExtraFeeEntity.lowestFee;
						    	}
						    	
						    	//最高费用校验
						    	if( 0 != curExtraFeeEntity.heightestFee 
						    			&& null != curExtraFeeEntity.heightestFee 
						    			&& undefined != curExtraFeeEntity.heightestFee
						    			&& "" != curExtraFeeEntity.heightestFee
						    			&& calculateObj['collectionFee'] > curExtraFeeEntity.heightestFee ){
						    		
						    		calculateObj['collectionFee'] = curExtraFeeEntity.heightestFee;
						    	}						    	
						    	
						    	
						    	break;						    	
						    case 'INSURANCE': //保价率 后台配置为 按千分之几计算
						    	
                                //保价金额 
                                var insuranceFee = formObj.getForm().findField('insuranceFee').getValue();
                                var tmpInsuraceFee = insuranceFee * curExtraFeeEntity.money * 0.001;
                                
                                //四舍五入 保留两位->所有费用不能有小数点，除了“.0”还取“”，其他的“.1~.9”全部都取整+1.
                                var insuraceFeeFloor = Math.floor(tmpInsuraceFee);
                                if(tmpInsuraceFee > insuraceFeeFloor){
                                	calculateObj['insuranceFee'] = insuraceFeeFloor + 1; 
                                } else {
                                	calculateObj['insuranceFee'] = insuraceFeeFloor; 
                                }
                                
                                break;
                                
						    case 'DELIVERY': //送货费
						    	
                                //是否送货
                                var deliverShipment = formObj.getForm().findField('deliverShipment').getValue();
                                var deliverShipmentVal = deliverShipment ? curExtraFeeEntity.money:0;
                                calculateObj['deliveryFee'] = deliverShipmentVal; 
                                break;
                                
						    case 'PICKUP': //提货费
						    	
                                //是否接货
                                var receiveShipment = formObj.getForm().findField('receiveShipment').getValue();
                                var receiveShipmentVal = receiveShipment ? curExtraFeeEntity.money:0;
                                calculateObj['pickupFee'] = receiveShipmentVal;     
						    	break;
						}
			    	}
			    	
			    	var calculateDataObj = Ext.create('Miser.model.bizbase.pricetime.CalculateDetailModel',calculateObj);
			    	
			    	//获取表单中的 grid 并向其中添加后台计算好的数据
			    	var item = formObj.items.items[3];
			    	var calculateGrid = item.items.items[0];
			    	calculateGrid.getStore().loadData([calculateDataObj]);
			    	
			    	
			    	//计算 总的金额
                    var totalPrice = 0;
                    var calculateData = calculateDataObj.data;
                    for(var w in calculateData){
                        if(w != 'detailFee' && w != 'id'){
                            var curVal = parseFloat(calculateData[w]);
                            totalPrice += curVal;
                        }
                    }
			    	
			    	var itemTmp = formObj.items.items[4];
			    	var totalPriceObj = itemTmp.items.items[1];
			    	totalPriceObj.setValue(totalPrice);
			    	
		    	} else {
		    		miser.showInfoMsg('不存在对应明细数据');
		    	}
		    	
		    };
		    
			var failureFun = function(json) {
				
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('请求超时');
				} else {
					var message = json.message;
					miser.showInfoMsg(message);
				}
			};
		    
		    miser.requestJsonAjax(curUrl,params, successFun, failureFun);
		}
	}
}

//点击  发送短信时 展示下面的 手机号 发送按钮
function priceTimeShowSms(me){
	 var itemTmp = me.items.items[5];
     var telPhoneObj = itemTmp.items.items[1];
     telPhoneObj.setVisible(telPhoneObj.isVisible() ? false:true);
     telPhoneObj.setValue(null);

     var sendBtnObj = itemTmp.items.items[2];
     sendBtnObj.setVisible(sendBtnObj.isVisible() ? false:true);
}

/**
 * 主界面查询表单定义
 */
Ext.define('Miser.base.priceTime.QueryForm',{
	extend:'Ext.form.Panel',
	id:'Miser_base_priceTime_queryForm',
	frame:true,
	width:document.documentElement.clientWidth-10,
	title:'查询条件',
	collapsible:true,
	region:'north',
    defaults: {
    	labelWidth: 0.4,
    	columnWidth: 0.6,
        margin: '8 10 5 10',
		labelAlign:'right'
    },				
	constructor:function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [
            {
            	layout: 'hbox',
    			defaults: {
    				labelWidth: 70,
    				labelAlign: "right"
    			},
    			items:[
				{
				    name: 'label',
				    html:'<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门店:</span>',
				    xtype: 'label'
				},{ 
				    xtype: 'dynamicorgcombselector',   	            
					name:'sendSaleDepartment',					
					isSalesDepartment:'Y'
				},{
				    name: 'label',
				    labelWidth: 20,
				    html:'—',
				    xtype: 'label'
				},{
				    xtype: 'dynamicorgcombselector',   	            
					name:'arriveSaleDepartment',					
					isSalesDepartment:'Y'
				},{
				    name: 'label',
				    html:"<div style='width:100px;'>&nbsp;&nbsp;</div>",
				    xtype: 'label'
				},
    			{
    	            name: 'label',
    	            html:'省市区:',
    	            xtype: 'label'
    	        },{
    	        	name: 'sendAreaCode',
    	            xtype: 'hotCitySelector'
    	    	},{
    	            name: 'label',
    	            labelWidth: 20,
    	            html:'—',
    	            xtype: 'label'
    	        },{
    	        	name: 'arriveAreaCode',
    	            xtype: 'hotCitySelector'    	        	 
    	    	}]
            },
            {
            	layout: 'hbox',
    			defaults: {
    				labelWidth: 70,
    				labelAlign: "right"
    			},
    			items:[{
    	            name: 'label',
    	            html:'运输类型:',
    	            xtype: 'label'
    	        },{
		            name: 'transTypeCode',
		            xtype:'transtypecombselector'
		        },{
    	            name: 'label',
    	            flex: 1,
    	            xtype: 'label'
    	        }]
            }           
		],
		me.buttons = [
			{
				text:'查询',
				handler:function() {
					var form = me.getForm();
	            	
					//是否选择了 省市区
	            	var isSelectedPCA = false;
	            	
	            	//是否选择了 门店
	            	var isSelectedSale = false;
	            	
	            	var tagIdArr = ['sendAreaCode','arriveAreaCode'];
	            
	            	for(var w = 0; w < tagIdArr.length; w ++){
	            		var curVal = form.findField(tagIdArr[w]).selectedValue;
	            		if(!Ext.isEmpty(curVal)){
	            			isSelectedPCA = true;
	            			break;
	            		}
	            	}
					
					//出发门店
	            	var sendSaleDepartmentVal = form.findField('sendSaleDepartment').getValue();
	            	isSelectedSale = ! Ext.isEmpty(sendSaleDepartmentVal) ; 
	            	
	            	// 有可能只选择 到达门店
	            	if(! isSelectedSale ){
	            		
						//到达门店
		            	var arriveSaleDepartmentVal = form.findField('arriveSaleDepartment').getValue();
		            	isSelectedSale = ! Ext.isEmpty(arriveSaleDepartmentVal)
	            	}
	            	
	            	
	            	//两个值都为 true 时 需要提示两者只能选择其一
	            	if(isSelectedPCA && isSelectedSale){
	            		miser.showErrorMes('出发到达省市区 与 门店 只能选择其中一种');
	            		return;
	            	}
	            	
	            	if( isSelectedPCA == false && isSelectedSale == false){
	            		miser.showErrorMes('出发到达省市区 与 门店 需至少选择一个作为查询条件');
	            		return;
	            	}
					
	                if (me.getForm().isValid()) {
	                    me.up().getPriceTimeGrid().getPagingToolbar().moveFirst();
	                }										 
				}
			},
			{
				text :'重置',
				handler:function() {
					me.getForm().reset();
				}
			},
			{
				text:'导出',
				handler:function() {
					Ext.Msg.wait('处理中，请稍后...', '提示');
					var queryForm = Ext.getCmp('Miser_base_priceTime_queryForm');
					if (queryForm != null) {
						
		            	var params = {
		                		'priceTimeVo.priceTimeEntity.sendAreaCode':queryForm.getForm().findField('sendAreaCode').selectedValue,
		                		'priceTimeVo.priceTimeEntity.arriveAreaCode':queryForm.getForm().findField('arriveAreaCode').selectedValue,
		                		'priceTimeVo.priceTimeEntity.standardTransTypeCode':queryForm.getForm().findField('transTypeCode').getValue(),
		                		'priceTimeVo.sendSaleDepartment':queryForm.getForm().findField('sendSaleDepartment').getValue(),
		                		'priceTimeVo.arriveSaleDepartment':queryForm.getForm().findField('arriveSaleDepartment').getValue()
		                };
		            	
						Ext.Ajax.request( {  
							   url : '../bizbase/priceTimeAction!excelExport.action',  
							   method : 'post',
							   timeout:280000,
							   params : params,  
								  success : function(response, options) {  
									  
									  Ext.Msg.hide();   
									  
									  var responseArray = Ext.util.JSON.decode(response.responseText);
									  			  
									  var msg="本次导出共"+responseArray.count+"条数据！";
									  miser.showInfoMsg(msg);
									  miser.requestExportAjax(responseArray.filePath);
								  },
								  failure : function() { 
									  Ext.Msg.hide();  
									  Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
								  }
						}); 		            	
					}
				}
			}				
		];
		me.callParent([ cfg ]);
	}
});

/**
 * 主界面查询结果对应实体定义
 */
Ext.define('Miser.model.bizbase.PriceTimeEntityModel',{
	extend:'Ext.data.Model',
	fields:[
	   {name:'sendPathName',type:'string'},//出发省市区
	   {name:'arrivePathName',type:'string'},//到达省市区        
	   {name:'standardPriceId',type:'string'},//标准价格的id
	   {name:'standardTransTypeCode',type:'string'},//标准价格的运输类型编码
	   {name:'standardTransTypeName',type:'string'},//标准价格 运输类型名称
	   {name:'standardSendPriceCity',type:'string'},//标准价格  出发价格城市 编码
	   {name:'standardSendPriceCityName',type:'string'},//标准价格  出发价格城市 名称
	   {name:'standardArrivePriceCity',type:'string'},//标准价格  到达价格城市 编码
	   {name:'standardArrivePriceCityName',type:'string'},//标准价格  到达价格城市 名称
	   {name:'standardWeightPrice',type:'string'},//标准价格  重量单价
	   {name:'standardVolumePrice',type:'string'},//标准价格  体积单价
	   {name:'standardWeightVolumneDesc',type:'string'},//标准价格  重量体积单价拼接描述
	   {name:'standardLowestFee',type:'string'},//标准价格  最低价格描述
	   {name:'sendProvinceCode',type:'string'},//标准价格  出发价格城市对应省份编码
	   {name:'sendCityCode',type:'string'},//标准价格  出发价格城市对应城市编码
	   {name:'sendAreaCode',type:'string'},//标准价格  出发价格城市对应区县编码
	   {name:'arriveProvinceCode',type:'string'},//标准价格  到达价格城市 对应省份编码
	   {name:'arriveCityCode',type:'string'},//标准价格  到达价格城市 对应城市编码
	   {name:'arriveAreaCode',type:'string'},//标准价格  到达价格城市 对应区县编码
	   {name:'eventIdStr',type:'string'},// 涉及活动ID编码串
	   {name:'eventNameStr',type:'string'},//涉及活动名称串
	   {name:'eventCodeStr',type:'string'},//涉及活动编码串
	   {name:'pickupDeliveryDesc',type:'string'},//时效表 客户自提 送货上门时间
	   {name:'pickupPromDay',type:'string'},//时效表 客户自提
	   {name:'deliveryPromDay',type:'string'}//时效表 送货上门
	]
});


/**
 * 偏线store
 */
Ext.define('Miser.biz.bizbase.PriceTimeStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.bizbase.PriceTimeEntityModel',
    id:'priceTimeStore',
    pageSize: 20,
    proxy: {
    	timeout: 180000,
        type: 'ajax',
        actionMethods: 'post',
        url: 'priceTimeAction!query.action',
        reader: {
            type: 'json',
            rootProperty: 'priceTimeVo.priceTimeEntityList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('Miser_base_priceTime_queryForm');
            
            if (queryForm != null) {
            	
            	var params = {
                		'priceTimeVo.priceTimeEntity.sendAreaCode':queryForm.getForm().findField('sendAreaCode').selectedValue,
                		'priceTimeVo.priceTimeEntity.arriveAreaCode':queryForm.getForm().findField('arriveAreaCode').selectedValue,
                		'priceTimeVo.priceTimeEntity.standardTransTypeCode':queryForm.getForm().findField('transTypeCode').getValue(),
                		'priceTimeVo.sendSaleDepartment':queryForm.getForm().findField('sendSaleDepartment').getValue(),
                		'priceTimeVo.arriveSaleDepartment':queryForm.getForm().findField('arriveSaleDepartment').getValue()
                };
            	
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

/**
 * 点击 活动名称 展示 活动详情时 涉及明细实体定义
 */
Ext.define('Miser.model.bizbase.pricetime.PriceSectionSubModel',{
	extend:'Ext.data.Model',
	fields:[
	   {name:'sectionId',type:'string'},//分段项目主键
	   {name:'startValue',type:'string'},//段起
	   {name:'endValue',type:'string'},//段止
	   {name:'sectionAccodingItem',type:'string'},//分段依据
	   {name:'priceType',type:'string'},//费用类型
	   {name:'price',type:'string'},//费用
	   {name:'calcAlone',type:'string'},//是否单独进行结算	
	   {name:'minPrice',type:'string'},//最低收费
	   {name:'maxPrice',type:'string'},//最高收费
	   {name:'remark',type:'string'},//备注
	   {name:'active',type:'string'}//是否可用
	]
});

/**
 * 点击 计算 展示页面 各种费用明细对应实体定义
 */
Ext.define('Miser.model.bizbase.pricetime.CalculateDetailModel',{
	extend:'Ext.data.Model',
	fields:[
	   {name:'detailFee',type:'string'},//费用明细 值固定为 开单金额
	   {name:'freightFee',type:'string'},//纯运费
	   {name:'insuranceFee',type:'string'},//保价费
	   {name:'collectionFee',type:'string'},//代办费
	   {name:'pickupFee',type:'string'},//提货费
	   {name:'deliveryFee',type:'string'},//送货费
	   {name:'smsFee',type:'string'},//信息费	
	   {name:'paperFee',type:'string'}//工本费
	]
});


Ext.define('Miser.biz.bizbase.pricetime.PriceSectionSubStore',{
    extend:'Ext.data.Store',
    model:'Miser.model.bizbase.pricetime.PriceSectionSubModel'
});

Ext.define('Miser.biz.bizbase.pricetime.CalculateStore',{
    extend:'Ext.data.Store',
    model:'Miser.model.bizbase.pricetime.CalculateDetailModel'
});

Ext.define('Miser.biz.bizbase.pricetime.priceSectionSubGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    width:900,
    height:300,
    stripeRows: true,
    region: 'center',
    emptyText:'查询记录为空',
    sectionAccodingTimeObj:{
    	'VOLUMN':'体积',
    	'WEIGHT':'重量',
    	'PIECE':'件数',
    	'INSURANCE':'保价费',
    	'PACKAGE':'票',
    	'COLLECTION':'代收货款'
    },
    priceTypeObj:{
    	'RATE':'单价',
    	'MONEY':'金额'
    },
    viewConfig: {
        enableTextSelection: true
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({}, config);
        me.columns = [{
            text: '分段依据',
            width: 120,
            align: 'center',
            dataIndex:'sectionAccodingItem',
            renderer:function(v,m,record,ri,ci){
            	var result = me.sectionAccodingTimeObj[v];
            	return result;
            }
        },{
            text: '段起(≥)',
            width: 120,
            align: 'center',
            dataIndex:'startValue'
        },{
            text: '段止(<)',
            width: 120,
            align: 'center',
            dataIndex:'endValue'
        },{
            text: '费用类型',
            width: 120,
            align: 'center',
            dataIndex:'priceType',
            renderer:function(v,m,record,ri,ci){
            	var result = me.priceTypeObj[v];
            	return result;
            }
        },{
            text: '费用',
            width: 120,
            align: 'center',
            dataIndex:'price'
        },{
            text: '最低收费',
            width: 120,
            align: 'center',
            dataIndex:'minPrice'
        },{
            text: '最高收费',
            width: 120,
            align: 'center',
            dataIndex:'maxPrice'
        }];
        me.callParent([cfg]);
    }
});

Ext.define('Miser.biz.bizbase.pricetime.CalculateGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    width:800,
    height:100,
    stripeRows: true,
    region: 'center',
    emptyText:'记录为空',
    viewConfig: {
        enableTextSelection: true
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({}, config);
        me.columns = [
			{
			    text: '费用明细',
			    width: 100,
			    align: 'center',
			    dataIndex:'detailFee'
			},
			{
			    text: '纯运费',
			    width: 90,
			    align: 'center',
			    dataIndex:'freightFee'				
			},
			{
			    text: '保价费',
			    width: 90,
			    align: 'center',
			    dataIndex:'insuranceFee'				
			},
			{
			    text: '代办费',
			    width: 90,
			    align: 'center',
			    dataIndex:'collectionFee'				
			},
			{
			    text: '提货费',
			    width: 90,
			    align: 'center',
			    dataIndex:'pickupFee'				
			},
			{
			    text: '送货费',
			    width: 90,
			    align: 'center',
			    dataIndex:'deliveryFee'				
			},
			{
			    text: '信息费',
			    width: 90,
			    align: 'center',
			    dataIndex:'smsFee'				
			},
			{
			    text: '工本费',
			    width: 90,
			    align: 'center',
			    dataIndex:'paperFee'				
			}
        ];
        me.callParent([cfg]);
    }
});

Ext.define('Miser.biz.bizbase.PriceTimeGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    id:'priceTimeGrid',
    autoScroll: true,
    width: '300%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    region: 'center',
    emptyText:bizbase.priceTime.i18n('bizbase.priceTime.resultIsNull'),//查询记录为空
    viewConfig: {
        enableTextSelection: true
    },
    pagingToolbar: null,
    getPagingToolbar: function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
                store: this.store,
                pageSize: 20
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({}, config);
        me.columns = [
        {
			dataIndex:'id',
			hidden:true
        },
        {
            text: '出发省市区',
            width: 120,
            align: 'center',
            dataIndex:'sendPathName'
        },
        {
            text: '到达省市区',
            width: 140,
            align: 'center',
            dataIndex:'arrivePathName'
        },
        {
            text: '出发城市',
            width: 85,
            align: 'center',
            dataIndex:'standardSendPriceCityName'
        },
        {
            text: '到达城市',
            width: 85,
            align: 'center',
            dataIndex:'standardArrivePriceCityName'
        },        
        {
            text: '运输类型',
            width: 90,
            align: 'center',
            dataIndex:'standardTransTypeName'
        },{
            text: '单价',
            width: 150,
            align: 'center',
            dataIndex:'standardWeightPrice',
            renderer:function(v,m,record,ri,ci){
            	var aResult= record.get('standardVolumePrice');  
	        	return '重货:' + v + ' 元/公斤<br/> 轻货:'+aResult + '  元/立方米';
            }
        },{
            text: '最低收费',
            width: 80,
            align: 'center',
            dataIndex:'standardLowestFee'        	
        },{
            text: '运行时长(天)',
            width: 200,
            align: 'center',
            dataIndex:'pickupPromDay',
            renderer:function(v,m,record,ri,ci){
            	
            	var cnt = "";
            	if(!Ext.isEmpty(v)){
            		cnt = '预计客户自提时间:第' + v + ' 天';
            	}
            	
            	var aResult= record.get('deliveryPromDay');  
            	if(!Ext.isEmpty(aResult)){
            		cnt += '<br/>';
            		cnt += '预计送货上门时间:第 '+ aResult + ' 天';
            	}
            	
	        	return cnt;
            }
        },{
            text: '优惠活动',
            width: 110,
            align: 'center',
            dataIndex:'eventNameStr',
            renderer:function(v,m,record,ri,ci){
            	
            	var sendPriceCityCode = record.get('standardSendPriceCity');
            	var arrivePriceCityCode = record.get('standardArrivePriceCity');
            	var transTypeCode = record.get('standardTransTypeCode');
            	
            	var eventCodeStr = record.get('eventCodeStr');
            	if(!Ext.isEmpty(eventCodeStr)){
            		var eventCodeArr = eventCodeStr.split(',');
            		var eventNameArr = v.split(',');
            		
            		var tpl = "<span style='color:blue;display:block;margin:5px;cursor:pointer;' onclick=showEventDetail(@eventCode@,@transTypeCode@)>@eventName@</span>";
            		var html = '';
            		for(var k=0;k<eventCodeArr.length;k++){
            			var curEventId = eventCodeArr[k];
            			var curEventName = eventNameArr[k];
            			var curTplHtml = tpl.replace('@eventCode@',"'"+curEventId+"'");
            			curTplHtml = curTplHtml.replace('@eventName@',curEventName);
            			curTplHtml = curTplHtml.replace('@transTypeCode@',"'"+transTypeCode+"'");
            			html += curTplHtml;
            		}
            		
            		if(html != ''){
            			return html;
            		}
            	}
            }
        },{
            xtype:'actioncolumn',
            text:'操作',
            items: [{
                icon: '../images/bizbase/calculator.png',
                getClass:function(){
                	return 'priceTimeRowBtn';
                },
                tooltip: '计算',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    showCalculateWindow(rec);
                }
            },{
                icon: '../images/bizbase/edit.png',
                getClass:function(){
                	return 'priceTimeRowBtn';
                },
                tooltip: '发送',
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    sendSms(rec);
                }
            }]
        }];
        me.store = Ext.create('Miser.biz.bizbase.PriceTimeStore', {
            autoLoad: false
        });
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});
 

function showCalculateWindow(record){
	var calculateWindow = Ext.create('Miser.biz.bizbase.PriceTimeCalculateWindow');
	calculateWindow.priceTimeEntity = record.data;
	calculateWindow.getCalculateForm().priceTimeEntity = record.data;
	calculateWindow.show();
}

//主页明细记录里 点击 发送按钮响应方法
function sendSms(record){
	var smsSendWindow = Ext.create('Miser.biz.bizbase.PriceTimeSmsSendWindow');
	smsSendWindow.getPriceTimeSmsSendForm().priceTimeEntity = record.data;
	smsSendWindow.show();
}

/**
 * 价格计算里 点击 发送
 * @param me:form
 */
function priceCalculateSmsSend(me){
	var smsTpl = "尊敬的客户：@出发省份@ @出发城市@ @出发区县@ 至  @到达省份@ @到达城市@ @到达区县@，" +
			"@运输类型@，总重量：@总重量@KG，总体积：@总体积@m³，" +
			"保价：@保价@元，代收：@代收@元，预计总费用：@总金额@元（含@接货送货@）" +
			"以上报价以当地营业网点为准！退订回复TD【天地华宇】";
	
	//运输类型
	var transTypeName = me.priceTimeEntity.standardTransTypeName;
	smsTpl = smsTpl.replace('@运输类型@',transTypeName);
	
	var formObj = me.getForm();
	
    //总重量
    var totalWeight = formObj.findField('totalWeight').getValue();
    smsTpl = smsTpl.replace('@总重量@',totalWeight);

    //总体积
    var totalVolume = formObj.findField('totalVolume').getValue();
    smsTpl = smsTpl.replace('@总体积@',totalVolume);
    
    //是否送货
    var deliverShipment = formObj.findField('deliverShipment').getValue();

    //是否接货
    var receiveShipment = formObj.findField('receiveShipment').getValue();
    
    //两者都勾选
    if(deliverShipment && receiveShipment){
    	smsTpl = smsTpl.replace('@接货送货@','接货送货费');
    	deliverShipment = false;
    	receiveShipment = false;
    } else if(deliverShipment){
    	smsTpl = smsTpl.replace('@接货送货@','送货费');
    } else if(receiveShipment){
    	smsTpl = smsTpl.replace('@接货送货@','接货费');
    }
    
    //总金额 
    var totalFee = formObj.findField('totalFee').getValue();
    smsTpl = smsTpl.replace('@总金额@',totalFee);
    
    var item = me.items.items[3];
    var calculateGrid = item.items.items[0];
    var calculateDataObj = calculateGrid.getStore().data.items[0];
    var calculateData = calculateDataObj.data;
    
    //保价费
    var insuranceFeeVal = calculateData.insuranceFee;
    smsTpl = smsTpl.replace('@保价@',insuranceFeeVal);
    
    //代收 现在需求默认为0
    var collectionFeeVal = calculateData.collectionFee;
    smsTpl = smsTpl.replace('@代收@',collectionFeeVal);
    
    //手机号
    var telephoneVal = formObj.findField('telephone').getValue();
	
    var curUrl = "priceTimeAction!calculateSmsSend.action";
	var params = {
			'priceTimeVo':{
			    'priceTimeEntity':{
			    	'arriveAreaCode':me.priceTimeEntity.arriveAreaCode,
			    	'arriveCityCode':me.priceTimeEntity.arriveCityCode,
			    	'arriveProvinceCode':me.priceTimeEntity.arriveProvinceCode,
			    	'sendAreaCode':me.priceTimeEntity.sendAreaCode,
			    	'sendCityCode':me.priceTimeEntity.sendCityCode,
			    	'sendProvinceCode':me.priceTimeEntity.sendProvinceCode
			    },
			    'smsTpl':smsTpl,
			    'telephone':telephoneVal
			}
	};
    
    var successFun = function(json) {
    	var msg = json.message;
    	if(msg == "success"){
    		miser.showInfoMsg('发送成功');
    	} else {
    		miser.showErrorMes(msg);
    	}
    };

    var failureFun = function(json) {
        if (Ext.isEmpty(json)) {
            miser.showErrorMes('请求超时');
        } else {
            var message = json.message;
            miser.showInfoMsg(message);
        }
    };

    miser.requestJsonAjax(curUrl,params, successFun, failureFun);
}

Ext.onReady(function(){
	Ext.QuickTips.init();
	
	var queryForm = Ext.create('Miser.base.priceTime.QueryForm');
	var priceTimeGrid = Ext.create('Miser.biz.bizbase.PriceTimeGrid');
 
    Ext.create('Ext.Viewport', {
        header: {
            titlePosition: 2,
            titleAlign: 'center'
        },
        border: "0 0 0 0",
        renderTo: Ext.getBody(),
        layout: {
            type: 'border'
        },
		getQueryForm:function(){
			return queryForm;
		},
		getPriceTimeGrid:function(){
			return priceTimeGrid;
		},
		items:[queryForm,priceTimeGrid]
    });
});