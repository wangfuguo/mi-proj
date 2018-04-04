//下拉单选框
Ext.define('Miser.commonSelector.CommonCombSelector', {
	extend : 'Hoau.commonselector.DynamicComboSelector',
	minChars : 0,
	disableKeyFilter: true,
	isPaging : false,// 分页
	isEnterQuery : true,// 回车查询
	listWidth : 200,// 设置下拉框宽度
	active : null,
	myActive : 'Y',
	record : null,
	displayField : null,
	valueField : null, 
	displayField : null,// 显示名称
	valueField : null,// 值
	queryParam : null,// 查询参数
	realValue : null,
	setCombValue : function(displayText, valueText) {
		var me = this, key = me.displayField + '', value = me.valueField
				+ '';
		var m = Ext.create(me.store.getModel().getName());
		m.set(key, displayText);
		m.set(value, valueText);
		me.record = m;
		me.store.loadRecords([m]);
		me.setValue(valueText);
		me.realValue = valueText;
		me.getTrigger('clear').show();
	},
	listeners: {
        /*change: function(comb, newValue, oldValue, eOpts){
        		if(comb.isExpanded==true){
					comb.collapse();
				}
				if(newValue != oldValue){
					comb.realValue = null;
				}
        },*/
        //失去焦点时校验是否做出选择，未做出选择清空下拉框
		blur:function(comb, the, eOpts){
			if (Ext.isEmpty(comb.realValue)) {
				comb.setRawValue(null);
				comb.setValue(null);
			}else{
				var display = comb.record.data[comb.displayField];
				if(!Ext.Object.equals(display,comb.getRawValue())){
					comb.setRawValue(null);
					comb.setValue(null);
					comb.realValue = null;
				}
			}
		},
		select:function(comb, records, obs){
			comb.record = records;
			var data = records.data;
			comb.realValue = data[comb.valueField];
			//显示情况控件
			comb.getTrigger('clear').show();
		},
		keyup : function(combo, event, eOpts){
			//下拉框输入值
			var value = combo.getRawValue();
			//情况控件
			var clearTrigger = combo.getTrigger('clear');
	        if(!Ext.isEmpty(value)){
	        	//输入不为空显示情况控件
	        	clearTrigger.show();
	        }else{
	        	//隐藏情况控件
	        	clearTrigger.hide();
	        }
	        //关闭下拉框
	        //combo.collapse();
			if(combo.isEnterQuery == true && event.getKey() == event.ENTER){
				var rawValue = combo.getRawValue();
				combo.store.loadPage(1,{
					scope: this,
					callback: function(records, operation, success) {
						if(records.length>0){
							//展开下拉框
							combo.expand();
						}
						combo.setRawValue(rawValue);
					}
				});
			}
		}
    },
	
	getBeforeLoad : function(store, operation, e) {
		var me = this;
		var me = this, searchParams = operation.getParams();
		if (Ext.isEmpty(searchParams)) {
			searchParams = {};
		}
		var prefix = me.queryParam.substring(0, me.queryParam
						.lastIndexOf('.'))
				+ '.';
		var param = prefix + me.myQueryParam;
		if (!Ext.isEmpty(me.myQueryParam)) {
			searchParams[param] = me.getRawValue();
			searchParams[me.queryParam] = null;
		} else {
			searchParams[me.queryParam] = me.rawValue;
			if(!Ext.isEmpty(me.myQueryParam)){
				searchParams[param] = null;
			}
		}
		/*var activeParam = prefix + 'active';
		var act = Ext.isEmpty(me.active) ? me.myActive : me.active;
		searchParams[activeParam] = act;*/
		Ext.apply(store.proxy.extraParams, searchParams);
	},
	triggers:{
		clear : {
				cls : 'x-form-clear-trigger',
				handler : function(){
					this.setRawValue(null);
					this.setValue(null);
					this.realValue = null;
					this.getTrigger('clear').hide();
				},
				hidden : true
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.active = config.active; 
		me.store.addListener('select', function(comb, records, obs) {
			var record = records[0];
			me.record = record;
			me.realValue = record[me.valueField];
		});
		me.callParent([cfg]);
		/*me.on('blur',me.getBlur,me); 
		me.on('select',me.getSelect,me); 
		me.on('change',me.getChange,me);
		me.on('keyup',me.getKeyup,me);*/
	},
	getRecord : function() {
		var me = this;
		return me.record;
	}
});




/**
 * #########################################
 * #######      数据字典下拉框开始          #######
 * #########################################
 */
//数据字典下拉单选框
Ext.define('Miser.commonSelector.DataDictionaryCommonSelector', {
	extend : 'Ext.form.field.ComboBox',
	alias: 'widget.dataDictionarySelector',
    displayField: 'valueName',
    valueField: 'valueCode',
    queryMode : 'local',
    editable : false,
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var store = getDataDictionary().getDataDictionaryStore(config.termsCode);
		if(!Ext.isEmpty(store)&&!Ext.isEmpty(config.anyRecords)){
			//store.add(config.anyRecords);
			store.insert(0,config.anyRecords);//新增的数据在第一个位置(“全部”)
		}
		me.store = store;
		me.callParent([cfg]);
//		me.addListener('change', function(me, newValue, oldValue) {
//			if(Ext.isEmpty(newValue)){
//				cfg = Ext.apply({}, config);
//				var store = getDataDictionary().getDataDictionaryStore(config.termsCode);
//				if(!Ext.isEmpty(store)&&!Ext.isEmpty(config.anyRecords)){
//					store.insert(0,config.anyRecords);//新增的数据在第一个位置(“全部”)
//				}
//				me.store = store;
//			}
//		})
	}
});
/**
 * #########################################
 * #######      数据字典下拉框结束          #######
 * #########################################
 */

/**
 * #########################################
 * #######      是否下拉框开始          #######
 * #########################################
 */
Ext.define('Miser.model.baseinfo.YesNoModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'name',
		type : 'string'
	},{
		name : 'value',
		type : 'string'
	}]
});
Ext.define('Miser.commonSelector.YesNoAllStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.baseinfo.YesNoModel',
    data : [{'name':'全部','value':''},{'name':'是','value':'Y'},{'name':'否','value':'N'}]
});
Ext.define('Miser.commonSelector.YesNoStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.baseinfo.YesNoModel',
    data : [{'name':'是','value':'Y'},{'name':'否','value':'N'}]
});
Ext.define('Miser.commonSelector.YesOrNoSelector', {
	extend : 'Ext.form.ComboBox',
	alias : 'widget.yesnocombselector',
	listWidth : this.width,// 下拉列表宽度，从外面传入
	multiSelect : false,// 从外面传入
	displayField : 'name',// 显示名称
	valueField : 'value',// 值
	isShowAll : false,// 是否显示全部
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config); 
		if (config.isShowAll){
			me.store = Ext.create('Miser.commonSelector.YesNoAllStore');
		}else{
			me.store = Ext.create('Miser.commonSelector.YesNoStore');
		}
		me.callParent([cfg]);
	}
});
//是和否checkBox
Ext.define('Miser.commonCheckBox.YesOrNoCheckBox', {
	extend : 'Ext.form.field.Checkbox',
	alias : 'widget.yesnocheckbox',
	inputValue: 'Y',         //选中的值
    uncheckedValue: 'N',
    getValue : function(){
    	if(this.checked){
    		return 'Y';
    	}else{
    		return 'N';
    	}
    }
});
/**
 * #########################################
 * #######      是否下拉框结束          #######
 * #########################################
 */
/**
 * #########################################
 * #######      组织相关下拉框开始          #######
 * #########################################
 */
/**
 * 用户model
 */
Ext.define('Miser.baseinfo.commonSelector.UserModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'userName'
				//用户名
			}, {
				name : 'empCode'
				//员工工号
			}, {
				name : 'empName'
				//员工姓名
			}, {
				name : 'password'
				//密码
			}, {
				name : 'title'
				//称谓
			}, {
				name : 'beginTime'
			}, {
				name : 'endTime'
			}, {
				name : 'isEmp'
				//是否公司员工
			}]
});
//用户下拉框store
Ext.define('Miser.commonUserSelector.UserCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.UserModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonUserSearchAction!seacrhUserByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonUserVo.commonUserEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//用户单选
Ext.define('Miser.commonUserSelector.DynamicUserSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicusercombselector',
	displayField : 'empName',// 显示名称
	valueField : 'userName',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）

	active : null,
	queryParam : 'commonUserVo.userSearchConditionEntity.queryParam',// 查询参数
	showContent : '{empName}&nbsp;&nbsp;&nbsp;{title}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonUserSelector.UserCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}

			if (!Ext.isEmpty(config.isEnable)) {
				searchParams['commonUserVo.userSearchConditionEntity.isEnable'] = config.isEnable;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonUserVo.userSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonUserVo.userSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * 特许经营网点model
 */
Ext.define('Miser.baseinfo.commonSelector.FranchiseModel', {
	extend : 'Ext.data.Model',
	fields : [ {
				name : 'code'
				//编码
			}, {
				name : 'name'
				//名称
			}, {
				name : 'logistCode'
				//物流编码
			}, {
				name : 'management'
				//管理部门
			}]
});

/**
 * 特许经营网点store
 */
Ext.define('Miser.commonUserSelector.FranchsieCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.FranchiseModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonFranchiseSearchAction!searchFranchiseByParams.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonFranchiseVo.commonFranchiseEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 特许经营网点公共选择器
 */
Ext.define('Miser.commonUserSelector.DynamicFranchiseSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicfranchisecombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	orgCode : null,//管理部门code
	active : null,
	queryParam : 'commonFranchiseVo.commonFranchiseEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonUserSelector.FranchsieCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}

			if (!Ext.isEmpty(config.orgCode)) {
				searchParams['commonFranchiseVo.commonFranchiseEntity.code'] = config.orgCode;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonFranchiseVo.commonFranchiseEntity.active'] = config.active;
			}else{
				searchParams['commonFranchiseVo.commonFranchiseEntity.active'] = 'Y';
			}
			searchParams['commonFranchiseVo.commonFranchiseEntity.limit'] = 10;
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});

/**
 *上下转移目的地公司定义model
 */
Ext.define('Miser.baseinfo.commonSelector.TransferModel', {
	extend : 'Ext.data.Model',
	fields : [ {
				name : 'code'
				//编码
			}, {
				name : 'name'
				//名称
			}, {
				name : 'isSuperduct'
				//是上转移还是下转移
			}]
});
/**
 * 下转移网点store
 */
Ext.define('Miser.commonUserSelector.TransferCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.TransferModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonCompanyRelationshipAction!searchCompanyRelationship.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonCompanyRelationshipVo.commonCompanyRelationshipEntities',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 上转移公共选择器
 */
Ext.define('Miser.commonUserSelector.DynamicTransferSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicTransfercombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	orgCode : null,//管理部门code
	isSuperduct : null,
	queryParam : 'commonCompanyRelationshipVo.commonCompanyRelationshipEntity.name',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{isSuperduct}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonUserSelector.TransferCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}

			if (!Ext.isEmpty(config.orgCode)) {
				searchParams['commonCompanyRelationshipVo.commonCompanyRelationshipEntity.code'] = config.orgCode;
			}
			if (!Ext.isEmpty(config.isSuperduct)) {
				searchParams['commonCompanyRelationshipVo.commonCompanyRelationshipEntity.isSuperduct'] = config.active;
			}else{
				searchParams['commonCompanyRelationshipVo.commonCompanyRelationshipEntity.isSuperduct'] = 'Y';
			}
			searchParams['commonCompanyRelationshipVo.commonCompanyRelationshipEntity.limit'] = 10;
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});

/**
 * 员工model
 * 
 */
Ext.define('Miser.baseinfo.commonSelector.EmployeModel',{
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'employeeCode'
			}, {
				name : 'employeeName'
			}, {
				name : 'pininName'
			}, {
				name : 'pinyinShortName'
			}, {
				name : 'account'
			}, {
				name : 'deptName'
			}, {
				name : 'deptCode'
			}, {
				name : 'jobName'
			}, {
				name : 'jobCode'
			}
			]
});

//员工下拉框store
Ext.define('Miser.commonEmployeSelector.EmployeCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.EmployeModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonEmployeeSearchAction!seacrhEmployeeByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonEmployeeVo.commonEmployeeList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 员工单选
 */
Ext.define('Miser.commonEmpSelector.EmployeeCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicemployeecombselector',
	displayField : 'employeeName',// 显示名称
	valueField : 'employeeCode',// 值
	active : null,
	queryParam : 'commonEmployeeVo.employeeSearchConditionEntity.queryParam',// 查询参数
	showContent : '{employeeName}&nbsp;&nbsp;&nbsp;{jobName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonEmployeSelector.EmployeCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonEmployeeVo.employeeSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonEmployeeVo.employeeSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});


/**
 * 组织model
 */
Ext.define('Miser.baseinfo.commonSelector.OrgModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'code'
			}, {
				name : 'logistCode'
			}, {
				name : 'name'
			}, {
				name : 'cityCode'
			}, {
				name : 'countyCode'
			}, {
				name : 'provinceCode'
			}, {
				name : 'active'
			}, {
				name : 'isDivision'
			}, {
				name : 'isBigRegion'
			}, {
				name : 'isRoadArea'
			}, {
				name : 'isSalesDepartment'
			}, {
				name : 'isTransferCenter'
			}, {
				name : 'isFleet'
			},{
				name : 'isPlatform'
			},{
				name : 'parentCode'
			}]
});
//组织下拉框store
Ext.define('Miser.commonOrgSelector.OrgCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OrgModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOrgSearchAction!seacrhOrgByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//组织单选
Ext.define('Miser.commonOrgSelector.DynamicOrgSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicorgcombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	type : 'ORG',// 部门类型 一种部门类型，传递此值
	types : null,// 部门类型 多种部门类型传递次值 两个类型的值之间用","隔开
	isDivision : null,// 查询事业部 配置此值
	isBigRegion : null,// 查询大区 配置此值
	isRoadArea : null,// 查询路区 配置此值
	isSalesDepartment : null,// 查询门店 配置此值
	isTransferCenter : null,// 查询场站 配置此值
	isFleet : null,// 查询车队 配置此值
	isPlatform : null,// 查询平台 配置此值
	logistCode : null,//物流代码	
	arrive:null,//是否可到达
	depart:null,//是否可出发
	parentCode:null,//上一级部门
	queryParam : 'commonOrgVo.orgSearchConditionEntity.queryParam',// 查询参数
	showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonOrgSelector.OrgCombStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			// 传递的部门类型是多种
			var types = null;
			if (!Ext.isEmpty(config.types)) {
				types = config.types.split(',');
				searchParams['commonOrgVo.orgSearchConditionEntity.types'] = types;
			}
			if (!Ext.isEmpty(config.type)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.type'] = config.type;
			}else{
				searchParams['commonOrgVo.orgSearchConditionEntity.type'] = me.type;
			}
			if (!Ext.isEmpty(config.isDivision)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isDivision'] = config.isDivision;
			}
			if (!Ext.isEmpty(config.isBigRegion)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isBigRegion'] = config.isBigRegion;
			}
			if (!Ext.isEmpty(config.isRoadArea)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isRoadArea'] = config.isRoadArea;
			}
			if (!Ext.isEmpty(config.isSalesDepartment)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isSalesDepartment'] = config.isSalesDepartment;
			}
			if (!Ext.isEmpty(config.isTransferCenter)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isTransferCenter'] = config.isTransferCenter;
			}
			if (!Ext.isEmpty(config.isFleet)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isFleet'] = config.isFleet;
			}
			if (!Ext.isEmpty(config.isPlatform)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.isPlatform'] = config.isPlatform;
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonOrgVo.orgSearchConditionEntity.active'] = 'Y';
			}
			if (!Ext.isEmpty(config.parentCode)) {
				searchParams['commonOrgVo.orgSearchConditionEntity.parentCode'] = config.parentCode;
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######      组织相关下拉框结束         #######
 * #########################################
 */

/**
 * #########################################
 * #######      包装费项目下拉框开始         #######
 * #########################################
 */

/**
 * 包装费model
 */
Ext.define('Miser.commonPricePackageFeeItemsSelector.PricePackageFeeItemsCombModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 
        type: 'string'
    },{
        name: 'code',
        // 编号
        type: 'string'
    },{
        name: 'name',
        // 编号
        type: 'string'
    },
    {
    	name: 'remark',
    	// 备注
    	type: 'string'
    },
    {
    	name: 'modifyUser',
    	// 最后修改人
    	type: 'string'
    },
    {
    	name: 'modifyDate',
    	// 最后修改时间
    	type : 'date', 
    	dateFormat:'time'
    }
    ]
});

/**
 * 包装费store 
 */
Ext.define('Miser.commonPricePackageFeeItemsSelector.PricePackageFeeItemsCombStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonPricePackageFeeItemsSelector.PricePackageFeeItemsCombModel',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/pricePackageFeeItems!queryPricePackageFeeItems.action',
        reader: {
            type: 'json',
            rootProperty: 'ppfItemsVo.ppfItemsList',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});


//包装费下拉框
Ext.define('Miser.commonPricePackageFeeItemsSelector.DynamicPricePackageFeeItemsSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicpricepackagefeeitemscombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）

	active : null,
	queryParam : 'ppfItemsVo.ppfItemsEntity.name',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonPricePackageFeeItemsSelector.PricePackageFeeItemsCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			
			searchParams['ppfItemsVo.ppfItemsEntity.active'] = 'Y';
			searchParams['ppfItemsVo.ppfItemsEntity.invalid'] = 'N';
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######      包装费项目下拉框结束         #######
 * #########################################
 */

/**
 * #########################################
 * #######      附加费项目下拉框开始         #######
 * #########################################
 */

/**
 * 附加费model
 */
Ext.define('Miser.commonPriceAddvalueFeeItemsSelector.PriceAddvalueFeeItemsCombModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 
        type: 'string'
    },{
        name: 'code',
        // 编号
        type: 'string'
    },{
        name: 'name',
        // 编号
        type: 'string'
    },
    {
    	name: 'remark',
    	// 备注
    	type: 'string'
    },
    {
    	name: 'modifyUser',
    	// 最后修改人
    	type: 'string'
    },
    {
    	name: 'modifyDate',
    	// 最后修改时间
    	type : 'date', 
    	dateFormat:'time'
    }
    ]
});

/**
 * 附加费store 
 */
Ext.define('Miser.commonPriceAddvalueFeeItemsSelector.PriceAddvalueFeeItemsCombStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonPriceAddvalueFeeItemsSelector.PriceAddvalueFeeItemsCombModel',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/priceAddvalueFeeItems!queryPriceAddvalueFeeItems.action',
        reader: {
            type: 'json',
            rootProperty: 'pafItemsVo.pafItemsList',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});


//附加费下拉框
Ext.define('Miser.commonPriceAddvalueFeeItemsSelector.DynamicPriceAddvalueFeeItemsSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicPriceAddvalueFeeItemscombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）

	active : null,
	queryParam : 'pafItemsVo.pafItemsEntity.name',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonPriceAddvalueFeeItemsSelector.PriceAddvalueFeeItemsCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			
			searchParams['pafItemsVo.pafItemsEntity.active'] = 'Y';
			searchParams['pafItemsVo.pafItemsEntity.invalid'] = 'N';
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * 优惠分段model
 */
Ext.define('Miser.commonPriceSectionSelector.PriceSectionCombModel', {
    extend: 'Ext.data.Model',
    fields: [{
		name : 'id',
		type : 'string'
	}, {
		name : 'code',
		// 优惠分段编号
		type : 'string'
	}, {
		name : 'shortCode',
		// 优惠分段编号
		type : 'string'
	}, {
		name : 'name',
		// 优惠分段名称
		type : 'string'
	},{
		name: 'suitProductName',
		type: 'string'
	}, {
		name : 'suitProduct',
		type : 'string'
	}, {
		name : 'sectionedItem',
		type : 'string'
	}, {
		name : 'remark',
		// 备注
		type : 'string'
	}, {
		name : 'active',
		// 是否有效
		type : 'string'
	}, {
		name : 'modifyUser',
		// 最后修改人
		type : 'string'
	}, {
		name : 'modifyDate',
		// 最后修改时间
		type : 'date',
		dateFormat : 'time'
	}
    ]
});

/**
 *优惠分段store 
 */
Ext.define('Miser.commonPriceSectionSelector.PriceSectionCombStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonPriceSectionSelector.PriceSectionCombModel',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../pricecard/priceSection!queryListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'priceSectionVo.priceSectionList',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});


//优惠分段下拉框
Ext.define('Miser.commonPriceSectionSelector.DynamicPriceSectionSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamicPriceSectioncombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）

	active : null,
	sectionedItem : null,//根据分段类别获取不同的分段
	queryParam : 'priceSectionVo.priceSectionEntity.name',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
	constructor : function(config) {
		var xParam=config.termsCode;
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.commonPriceSectionSelector.PriceSectionCombStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if(Ext.isEmpty(searchParams)){
				searchParams={};
				if(!Ext.isEmpty(xParam)){
					searchParams['priceSectionVo.priceSectionEntity.code'] = xParam;
				}
			}else{
				searchParams['priceSectionVo.priceSectionEntity.code'] = '';
			}
			if(!Ext.isEmpty(config.sectionedItem)){
				searchParams['priceSectionVo.priceSectionEntity.sectionedItem'] = config.sectionedItem;
			}
			searchParams['priceSectionVo.priceSectionEntity.active'] = 'Y';
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		});
		me.callParent([cfg]);
		//初始值，如果存在termsCode则加载
		if(!Ext.isEmpty(xParam)){
		me.setValue(xParam);
		me.getStore().load();
		}
	}
});

/**
 * #########################################
 * #######      附加费项目下拉框结束         #######
 * #########################################
 */

/**
 * #########################################
 * #######      省市区相关下拉框开始        #######
 * #########################################
 */
//行政区域Model
Ext.define('Miser.baseinfo.commonSelector.DistrictModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'districtCode',
				type : 'string'
			}, {
				name : 'districtName',
				type : 'string'
			}, {
				name : 'districtType',
				type : 'string'
			}, {
				name : 'parentDistrictName',
				type : 'string'
			},{
				name : 'parentDistrictCode',
				type : 'string'
			}]
});
//行政区域store
Ext.define('Miser.baseinfo.commonSelector.DistrictStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.DistrictModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../base/districtAction!queryDistrictByName.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			rootProperty : 'districtVo.districtList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 国家store
 */
Ext.define('Miser.baseinfo.commonSelector.NationStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.DistrictModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../base/districtAction!queryAllNation.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			rootProperty : 'districtVo.districtList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 省份store
 */
Ext.define('Miser.baseinfo.commonSelector.ProvinceStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.DistrictModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../base/districtAction!queryProvince.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			rootProperty : 'districtVo.districtList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 城市store
 */
Ext.define('Miser.baseinfo.commonSelector.CityStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.DistrictModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../base/districtAction!queryCity.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			rootProperty : 'districtVo.districtList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 区县store
 */
Ext.define('Miser.baseinfo.commonSelector.AreaStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.DistrictModel',
	pageSize : 50,
	proxy : {
		type : 'ajax',
		url : '../base/districtAction!queryArea.action',
		actionMethods : 'POST',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			rootProperty : 'districtVo.districtList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 省市区单个下拉框
 */
Ext.define('Miser.commonSelector.DistrictSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.commondistrictselector',
	eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
	displayField : 'districtName',// 显示名称
	valueField : 'districtCode',
	queryParam : 'districtVo.district.districtName',// 查询参数
	districtType: null,//省市县类别
	showContent : '{districtName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.baseinfo.commonSelector.DistrictStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if(!Ext.isEmpty(config.districtType)){
				searchParams['districtVo.district.districtType'] = config.districtType;
			} 
			Ext.apply(store.proxy.extraParams, searchParams);
		});
		me.callParent([cfg]);
	}
});

//联动container	
Ext.define('Miser.commonselector.LinkageContainer',{
	extend: 'Ext.form.FieldContainer',
	alias: 'widget.linkagecontainer',
	defaultType: 'linkagecomboselector',
	getItemValue: function(itemId){
		var me = this,
			item = me.items.get(itemId);
		if(item!=null){
			return item.getValue();
		}
		return null;
	},
	getValue: function(){
		var me = this,
			values = new Array();
		for(var i=0;i<me.items.length;i++){
			values[i] = me.getItemValue(me.items.items[i].itemId);
		}
		return values;
	}
});

/**
 * 联动查询组件
 */
Ext.define('Miser.commonselector.LinkageComboSelector',{
	extend: 'Hoau.commonselector.DynamicComboSelector',
	alias: 'widget.linkagecomboselector',
	minChars : 0,
	isPaging : false,// 分页
	isEnterQuery : true,// 回车查询
	parentParamsAndItemIds: null,//级联父组件参数
	realValue : null,
	setCombValue : function(displayText, valueText) {
		var me = this, key = me.displayField + '', value = me.valueField
				+ '';
		var m = Ext.create(me.store.getModel().getName());
		m.set(key, displayText);
		m.set(value, valueText);
		me.record = m;
		me.store.loadRecords([m]);
		me.setValue(valueText);
		me.realValue = valueText;
		//显示情况控件
		me.getTrigger('clear').show();
	},
	listeners:{
        //失去焦点时校验是否做出选择，未做出选择清空下拉框
		blur:function(comb, the, eOpts){
			if (Ext.isEmpty(comb.realValue)) {
				comb.setRawValue(null);
				comb.setValue(null);
			}else{
				var display = comb.record.data[comb.displayField];
				if(!Ext.Object.equals(display,comb.getRawValue())){
					comb.setRawValue(null);
					comb.setValue(null);
					comb.realValue = null;
				}
			}
		},
		select:function(comb, records, obs){
			comb.record = records;
			var data = records.data;
			comb.realValue = data[comb.valueField];
			//显示情况控件
			comb.getTrigger('clear').show();
		},
		keyup : function(combo, event, eOpts){
			//下拉框输入值
			var value = combo.getRawValue();
			//情况控件
			var clearTrigger = combo.getTrigger('clear');
	        if(!Ext.isEmpty(value)){
	        	//输入不为空显示情况控件
	        	clearTrigger.show();
	        }else{
	        	//隐藏情况控件
	        	clearTrigger.hide();
	        }
	        //关闭下拉框
	        //combo.collapse();
			if(combo.isEnterQuery == true && event.getKey() == event.ENTER){
				var rawValue = combo.getRawValue();
				combo.store.loadPage(1,{
					scope: this,
					callback: function(records, operation, success) {
						if(records.length>0){
							//展开下拉框
							combo.expand();
						}
						combo.setRawValue(rawValue);
					}
				});
			}
		}
	},
	/*eventType: ['callparent'],//事件(传入)
	getFocus: function(){
		var me = this;
		me.on('focus',function(){
			me.setValue(null);
			me.store.loadPage(1,{
				scope: this,
				callback: function(records, operation, success) {
					if(records.length>0){
						me.expand();
					}
				}
			});
		});
	},*/
	/*getCallParent: function(){
		var me = this,
			cmp;
		if(!Ext.isEmpty(me.parentParamsAndItemIds)){
			Ext.Object.each(me.parentParamsAndItemIds, function(queryParam, itemId, parentParamsAndItemIds) {
				cmp = me.up().items.get(itemId);
				if(!cmp.hasListener('select')){
					cmp.on('select',function(combo){
						me.setValue(null);
						me.store.loadPage(1,{
							scope: this,
							callback: function(records, operation, success) {
								me.focus(false, 100);
								me.expand();
							}
						});
					});
				}
			});
		}
	},*/
	triggers:{
		clear : {
				cls : 'x-form-clear-trigger',
				handler : function(){
					this.setRawValue(null);
					this.setValue(null);
					this.realValue = null;
					this.getTrigger('clear').hide();
				},
				hidden : true
		}
	},
	initComponent:function(){
		var me = this;
		/*me.on('boxready',function(){
			var callparent = null;
			for(var i = 0;i<me.eventType.length;i++){
				if(me.eventType[i]=='focus'){
					me.getFocus();
				}else if(me.eventType[i]=='callparent'){
					callparent = 'callparent';
					me.getCallParent();
				}
			}
			//调用父类方法会自动包含focus事件
			if(callparent=='callparent'){
				me.un('focus');
			}
		});*/
		this.callParent(arguments);
		//增加级联查询条件
		me.store.on('beforeLoad', function(st,operation,e){
			var cmp=null, searchParams=operation.getParams();
			if(Ext.isEmpty(searchParams)){
				searchParams={};
			}
			searchParams[me.queryParam] = me.rawValue;
			if(!Ext.isEmpty(me.parentParamsAndItemIds)){
				Ext.Object.each(me.parentParamsAndItemIds, function(queryParam, itemId, parentParamsAndItemIds) {
					cmp = me.up().items.get(itemId);
					searchParams[queryParam] = cmp.getValue();
				});
			}
			Ext.apply(st.proxy.extraParams, searchParams);
		});
	}
});	

Ext.define('Miser.commonSelector.linkReginCombSelector', {
	extend : 'Miser.commonselector.LinkageContainer',
	alias : 'widget.linkregincombselector',
//	fieldLabel : '联动选择',
	type : 'N-P-C-C',// type ：N-P-C-C 国省市县 P-C-C :省市县 C-C:市县 P-C:省市
	width : 600,
	nationWidth : 150,// 国家长度
	nationLabel : '',// 国家label
	nationName : '',// 国家名称--对应name
	nationIsBlank : true,
	nationLabelWidth:null,
	provinceWidth : 150,// 省份长度
	provinceLabel : '',// 省份label
	provinceLabelWidth:null,
	provinceName : '',// 省份名称—对应name
	provinceIsBlank : true,
	cityWidth : 150,// 城市长度
	cityLabel : '',// 城市label
	cityName : '',// 城市name
	cityIsBlank : true,
	cityLabelWidth:null,
	areaWidth : 150,// 县长度
	areaLabel : '',// 县label
	areaName : '',// 县name
	areaIsBlank : true,
	areaLabelWidth:null,
	layout : 'column',
	labelWid : 20,
	readOnly : false,
	setReadOnly : function(flag){
		var me =this;
		me.getNation().setReadOnly(flag);
		me.getProvince().setReadOnly(flag);
		me.getCity().setReadOnly(flag);
		me.getCounty().setReadOnly(flag);
	},
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			minChars : 0,
			labelSeparator : ''
		}
	},
	setReginValue : function(displayText, valueText, order) {// 0：国家的值，1：省的值，2：市的值，3：县的值
		var me = this;
		var regionObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				regionObj=me.nation;
			}else if(order == '1'){
				regionObj=me.province;
			}else if(order == '2'){
				regionObj=me.city;
			}else if(order == '3'){
				regionObj=me.county;
			}
		}
		var  key = regionObj.displayField + '', value =regionObj.valueField
				+ '';
		var m = Ext.create(regionObj.store.getModel().getName());
		m.set(key, displayText);
		m.set(value, valueText);
		regionObj.store.loadRecords([m]);
		regionObj.setValue(valueText);
	},
	nation:null,
	getNation:function(nationWidth,nationLabel,nationName,nationIsBlank,nationLabelWidth,configType,provinceObj){
		if(Ext.isEmpty(this.nation)){
			this.nation=Ext.widget('linkagecomboselector',{
				configType :configType,
				provinceObj : provinceObj,
				/*editable:false,*/
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				name : 'province',
				itemId : 'Miser_baseinfo_Nation_ID',
				store : Ext.create('Miser.baseinfo.commonSelector.NationStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						if(ths.configType == 'N-P-C-C'){
							ths.provinceObj.setReadOnly(false);
							var provObj=ths.provinceObj.getEl();
							if(!Ext.isEmpty(provObj)){
								provObj.query('input')[0].readOnly = 'readOnly';	
							}
						} 
					}
				},
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				width : nationWidth,
				fieldLabel : nationLabel,
				name : nationName,
				labelWidth:nationLabelWidth,
				allowBlank : nationIsBlank,
				queryParam : 'districtVo.district.queryParam'
			});
		}
		return this.nation;
	},
	province:null,
	getProvince:function(provinceWidth,provinceLabel,provinceName,provinceIsBlank,provinceLabelWidth,configType,cityObj){
		if(Ext.isEmpty(this.province)){
			this.province=Ext.widget('linkagecomboselector',{
				configType:configType,
				cityObj : cityObj,
				/*editable:false,*/
				itemId : 'Miser_baseinfo_Province_ID',
				eventType : ['callparent'],
				store : Ext.create('Miser.baseinfo.commonSelector.ProvinceStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						ths.cityObj.disable();
						ths.cityObj.setValue(null);
						ths.cityObj.realValue = null;
						/*var cityObj=ths.cityObj.getEl();*/
						/*if(!Ext.isEmpty(cityObj)){
							cityObj.query('input')[0].readOnly = 'readOnly';	
						}*/
					},
					'select' : function(ths, the, eOpts){
						ths.cityObj.enable();
						ths.cityObj.getStore().load();
					}
				},
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				minChars : 0,
				width : provinceWidth,
				fieldLabel : provinceLabel,
				name : provinceName,
				labelWidth:provinceLabelWidth,
				allowBlank : provinceIsBlank,
				isPaging: false, 
				parentParamsAndItemIds : {
					'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_Nation_ID'
				},// 此处城市不需要传入
				queryParam : 'districtVo.district.queryParam'
			
			});
		}
		return this.province;
	},
	city:null,
	getCity:function(cityWidth,cityLabel,cityName,cityIsBlank,cityLabelWidth,configType,countyObj){
		if(Ext.isEmpty(this.city)){
				this.city=Ext.widget('linkagecomboselector',{
					configType:configType,
					countyObj : countyObj,
					/*editable:false,*/
					itemId : 'Miser_baseinfo_City_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Miser.baseinfo.commonSelector.CityStore'),// 从外面传入
					listeners : {
						'change' : function(ths, the, eOpts){
							ths.countyObj.disable();
							ths.countyObj.setValue(null);
							ths.countyObj.realValue = null;
							/*ths.countyObj.setReadOnly(false);
							var countyObj=ths.countyObj.getEl();
							if(!Ext.isEmpty(countyObj)){
								countyObj.query('input')[0].readOnly = 'readOnly';	
							}*/
						},
						'select' : function(ths, the, eOpts){
							ths.countyObj.enable();
							ths.countyObj.getStore().load();
						}
					},
					displayField : 'districtName',// 显示名称
					valueField : 'districtCode',
					minChars : 0,
					width : cityWidth,
					fieldLabel : cityLabel,
					name : cityName,
					labelWidth:cityLabelWidth,
					allowBlank : cityIsBlank,
					isPaging: false,  
					parentParamsAndItemIds : {
						'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_Province_ID'
					},// 此处城市不需要传入
					queryParam : 'districtVo.district.queryParam'
			});
		}
		return this.city;
	},
	county:null,
	getCounty:function(areaWidth,areaLabel,areaNames,areaIsBlank,areaLabelWidth){
		if(Ext.isEmpty(this.county)){
			this.county=Ext.widget('linkagecomboselector',{
				store : Ext.create('Miser.baseinfo.commonSelector.AreaStore'),// 从外面传入
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				minChars : 0,
				/*editable:false,*/
				width : areaWidth,
				fieldLabel : areaLabel,
				name : areaNames,
				allowBlank : areaIsBlank,
				labelWidth:areaLabelWidth,
				isPaging: false, 
				parentParamsAndItemIds : {
					'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_City_ID'
				},// 此处区域不需要传入
				queryParam : 'districtVo.district.queryParam'
				});
			}
		return  this.county;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var county = me.getCounty(config.areaWidth,config.areaLabel,config.areaName,config.areaIsBlank,config.areaLabelWidth);
		var city = me.getCity(config.cityWidth,config.cityLabel,config.cityName,config.cityIsBlank,config.cityLabelWidth,config.type,county);
		var province = me.getProvince(config.provinceWidth,config.provinceLabel,config.provinceName,config.provinceIsBlank,config.provinceLabelWidth,config.type,city);
		var nation = me.getNation(config.nationWidth,config.nationLabel,config.nationName,config.nationIsBlank,config.nationLabelWidth,config.type,province);
		me.items=[nation,province,city,county];
		if (config.type == 'N-P-C-C') {
			/*province.setReadOnly(true);   
			city.setReadOnly(true);   
			county.setReadOnly(true);*/  
			/*province.cls='readonlyhaveborder'; 
			city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';*/			
			/*me.items=[nation,province,city,county];*/
		} else if (config.type == 'P-C-C') {
			if(config.readOnly){
				/*province.disable();
				city.disable();   
				county.disable();*/
				province.setReadOnly(true);
				city.setReadOnly(true);
				county.setReadOnly(true);
			}else{
				city.disable();   
				county.disable();
			}
			/*city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';*/
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city,county];
		} else if (config.type == 'P-C') {
			if(config.readOnly){
				/*province.disable();
				city.disable();*/
				province.setReadOnly(true);
				city.setReadOnly(true);
			}else{
				city.disable();
			}
			/*city.cls='readonlyhaveborder';*/
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city];
		} else if (config.type == 'C-C') {
			if(config.readOnly){
				/*city.disable();
				county.disable(); */
				city.setReadOnly(true);
				county.setReadOnly(true);
			}else{
				county.disable(true); 
			}
			/*county.cls='readonlyhaveborder';*/
			city.eventType=['focus'];
			city.parentParamsAndItemIds = null;
			me.items=[city,county];
		}
		me.callParent([cfg]);
	}
});

/**
 * 支持逆推的省市区级联控件
 */
Ext.define('Miser.commonSelector.myLinkReginCombSelector', {
	extend : 'Miser.commonselector.LinkageContainer',
	alias : 'widget.myLlinkregincombselector',
//	fieldLabel : '联动选择',
	type : 'N-P-C-C',// type ：N-P-C-C 国省市县 P-C-C :省市县 C-C:市县 P-C:省市
	width : 600,
	nationWidth : 150,// 国家长度
	nationLabel : '',// 国家label
	nationName : '',// 国家名称--对应name
	nationIsBlank : true,
	nationLabelWidth:null,
	provinceWidth : 150,// 省份长度
	provinceLabel : '',// 省份label
	provinceLabelWidth:null,
	provinceName : '',// 省份名称—对应name
	provinceIsBlank : true,
	cityWidth : 150,// 城市长度
	cityLabel : '',// 城市label
	cityName : '',// 城市name
	cityIsBlank : true,
	cityLabelWidth:null,
	areaWidth : 150,// 县长度
	areaLabel : '',// 县label
	areaName : '',// 县name
	areaIsBlank : true,
	areaLabelWidth:null,
	layout : 'column',
	labelWid : 20,
	readOnly : false,
	setReadOnly : function(flag){
		var me =this;
		me.getNation().setReadOnly(flag);
		me.getProvince().setReadOnly(flag);
		me.getCity().setReadOnly(flag);
		me.getCounty().setReadOnly(flag);
	},
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			minChars : 0,
			labelSeparator : ''
		}
	},
	setReginValue : function(displayText, valueText, order) {// 0：国家的值，1：省的值，2：市的值，3：县的值
		var me = this;
		var regionObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				regionObj=me.nation;
			}else if(order == '1'){
				regionObj=me.province;
			}else if(order == '2'){
				regionObj=me.city;
			}else if(order == '3'){
				regionObj=me.county;
			}
		}
		var  key = regionObj.displayField + '', value =regionObj.valueField
				+ '';
		var m = Ext.create(regionObj.store.getModel().getName());
		m.set(key, displayText);
		m.set(value, valueText);
		regionObj.store.loadRecords([m]);
		regionObj.setValue(valueText);
	},
	nation:null,
	getNation:function(nationWidth,nationLabel,nationName,nationIsBlank,nationLabelWidth,configType,provinceObj){
		if(Ext.isEmpty(this.nation)){
			this.nation=Ext.widget('linkagecomboselector',{
				configType :configType,
				provinceObj : provinceObj,
				/*editable:false,*/
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				name : 'province',
				itemId : 'Miser_baseinfo_Nation_ID',
				store : Ext.create('Miser.baseinfo.commonSelector.NationStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						if(ths.configType == 'N-P-C-C'){
							ths.provinceObj.setReadOnly(false);
							var provObj=ths.provinceObj.getEl();
							if(!Ext.isEmpty(provObj)){
								provObj.query('input')[0].readOnly = 'readOnly';	
							}
						} 
					}
				},
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				width : nationWidth,
				fieldLabel : nationLabel,
				name : nationName,
				labelWidth:nationLabelWidth,
				allowBlank : nationIsBlank,
				queryParam : 'districtVo.district.queryParam'
			});
		}
		return this.nation;
	},
	province:null,
	getProvince:function(provinceWidth,provinceLabel,provinceName,provinceIsBlank,provinceLabelWidth,configType,cityObj){
		if(Ext.isEmpty(this.province)){
			this.province=Ext.widget('linkagecomboselector',{
				configType:configType,
				cityObj : cityObj,
				/*editable:false,*/
				itemId : 'Miser_baseinfo_Province_ID',
				eventType : ['callparent'],
				store : Ext.create('Miser.baseinfo.commonSelector.ProvinceStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						var cityValue=ths.cityObj.getValue();
						if(!Ext.isEmpty(cityValue)){
							var parentDistrictCode=null;
								var params = {	                
										'districtVo' :{
											'district' : {
												'districtCode' : cityValue
											}
										}
				                    };   	
								var successFun = function (json) {
									parentDistrictCode=json.districtVo.district.parentDistrictCode;
									if(parentDistrictCode!=ths.getValue()){
										//变动
										ths.cityObj.setValue(null);
										ths.cityObj.realValue = null;
									}
								};
								var failureFun = function (json) {
									
								};
							miser.requestJsonAjax('../base/districtAction!queryDistrictByCode.action', params, successFun, failureFun,true);
							
						}else{
							ths.cityObj.setValue(null);
							ths.cityObj.realValue = null;
						}
						
						/*var cityObj=ths.cityObj.getEl();*/
						/*if(!Ext.isEmpty(cityObj)){
							cityObj.query('input')[0].readOnly = 'readOnly';	
						}*/
					},
					'select' : function(ths, the, eOpts){
						ths.cityObj.enable();
						ths.cityObj.getStore().load();
					}
				},
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				minChars : 0,
				width : provinceWidth,
				fieldLabel : provinceLabel,
				name : provinceName,
				labelWidth:provinceLabelWidth,
				allowBlank : provinceIsBlank,
				isPaging: false, 
				parentParamsAndItemIds : {
					'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_Nation_ID'
				},// 此处城市不需要传入
				queryParam : 'districtVo.district.queryParam'
			
			});
		}
		return this.province;
	},
	city:null,
	getCity:function(cityWidth,cityLabel,cityName,cityIsBlank,cityLabelWidth,configType,countyObj){
		if(Ext.isEmpty(this.city)){
				this.city=Ext.widget('linkagecomboselector',{
					configType:configType,
					countyObj : countyObj,
					/*editable:false,*/
					itemId : 'Miser_baseinfo_City_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Miser.baseinfo.commonSelector.CityStore'),// 从外面传入
					listeners : {
						'change' : function(ths, the, eOpts){
							//取出父code的值与省的值比较，如果不同要给省赋值
							var curValue=ths.getValue();
							if(!Ext.isEmpty(curValue)){ 
								var parentObj=ths.up("myLlinkregincombselector").province;
									var parentDistrictName=null;
									var parentDistrictCode=null;
									var params = {	                
											'districtVo' :{
												'district' : {
													'districtCode' : curValue
												}
											}
					                    };   	
									var successFun = function (json) {
										parentDistrictCode=json.districtVo.district.parentDistrictCode;
										parentDistrictName=json.districtVo.district.parentDistrictName;
										if(parentDistrictCode!=parentObj.getValue()){//变动，重新设置父的值
											parentObj.getStore().load();
											parentObj.setCombValue(parentDistrictName,parentDistrictCode);
										}else{//没有变化，且
											if(!Ext.isEmpty(this.countyObj.getOriginalValue())){
												ths.countyObj.setValue(null);
												ths.countyObj.realValue = null;
											}
											
										}
										
										
									};
									var failureFun = function (json) {
										
									};
						        miser.requestJsonAjax('../base/districtAction!queryDistrictByCode.action', params, successFun, failureFun,true);  
										
									//ths.up("myLlinkregincombselector").setReginValue(parentDistrictName,curObjData.parentDistrictCode,'1');
							}else{
								ths.countyObj.setValue(null);
								ths.countyObj.realValue = null;
								ths.getStore().load();
							}
						},
						'select' : function(ths, the, eOpts){
							ths.countyObj.enable();
							ths.countyObj.getStore().load();
						}
					},
					displayField : 'districtName',// 显示名称
					valueField : 'districtCode',
					minChars : 0,
					width : cityWidth,
					fieldLabel : cityLabel,
					name : cityName,
					labelWidth:cityLabelWidth,
					allowBlank : cityIsBlank,
					isPaging: false,  
					parentParamsAndItemIds : {
						'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_Province_ID'
					},// 此处城市不需要传入
					queryParam : 'districtVo.district.queryParam'
			});
		}
		return this.city;
	},
	county:null,
	getCounty:function(areaWidth,areaLabel,areaNames,areaIsBlank,areaLabelWidth){
		if(Ext.isEmpty(this.county)){
			this.county=Ext.widget('linkagecomboselector',{
				store : Ext.create('Miser.baseinfo.commonSelector.AreaStore'),// 从外面传入
				displayField : 'districtName',// 显示名称
				valueField : 'districtCode',
				minChars : 0,
				/*editable:false,*/
				width : areaWidth,
				fieldLabel : areaLabel,
				name : areaNames,
				allowBlank : areaIsBlank,
				labelWidth:areaLabelWidth,
				isPaging: false, 
				listeners : {
					'select' : function(ths, the, eOpts){
						//当前控件的值
						var curValue=ths.getValue();
						if(!Ext.isEmpty(curValue)){
							var parentObj=ths.up("myLlinkregincombselector").city;
							//获取它的父节点
							var params = {	                
									'districtVo' :{
										'district' : {
											'districtCode' : curValue
										}
									}
			                    };   	
							var successFun = function (json) {
								parentDistrictCode=json.districtVo.district.parentDistrictCode;
								parentDistrictName=json.districtVo.district.parentDistrictName;
								if(parentObj.getValue()!=parentDistrictCode){//不匹配重新设置父节点的值
									parentObj.getStore().load();
									parentObj.setCombValue(parentDistrictName,parentDistrictCode);
								}
								
							};
							var failureFun = function (json) {
								
							};
				            miser.requestJsonAjax('../base/districtAction!queryDistrictByCode.action', params, successFun, failureFun,true);  
						}
					}
				},
				parentParamsAndItemIds : {
					'districtVo.district.parentDistrictCode' : 'Miser_baseinfo_City_ID'
				},// 此处区域不需要传入
				queryParam : 'districtVo.district.queryParam'
				});
			}
		return  this.county;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var county = me.getCounty(config.areaWidth,config.areaLabel,config.areaName,config.areaIsBlank,config.areaLabelWidth);
		var city = me.getCity(config.cityWidth,config.cityLabel,config.cityName,config.cityIsBlank,config.cityLabelWidth,config.type,county);
		var province = me.getProvince(config.provinceWidth,config.provinceLabel,config.provinceName,config.provinceIsBlank,config.provinceLabelWidth,config.type,city);
		var nation = me.getNation(config.nationWidth,config.nationLabel,config.nationName,config.nationIsBlank,config.nationLabelWidth,config.type,province);
		me.items=[nation,province,city,county];
		if (config.type == 'N-P-C-C') {
			/*province.setReadOnly(true);   
			city.setReadOnly(true);   
			county.setReadOnly(true);*/  
			/*province.cls='readonlyhaveborder'; 
			city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';*/			
			/*me.items=[nation,province,city,county];*/
		} else if (config.type == 'P-C-C') {
			if(config.readOnly){
				/*province.disable();
				city.disable();   
				county.disable();*/
				province.setReadOnly(true);
				city.setReadOnly(true);
				county.setReadOnly(true);
			}else{
				//city.disable();   
				//county.disable();
			}
			/*city.cls='readonlyhaveborder';
			county.cls='readonlyhaveborder';*/
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city,county];
		} else if (config.type == 'P-C') {
			if(config.readOnly){
				/*province.disable();
				city.disable();*/
				province.setReadOnly(true);
				city.setReadOnly(true);
			}else{
				city.disable();
			}
			/*city.cls='readonlyhaveborder';*/
			province.eventType=['focus'];
			province.parentParamsAndItemIds = null;
			me.items=[province,city];
		} else if (config.type == 'C-C') {
			if(config.readOnly){
				/*city.disable();
				county.disable(); */
				city.setReadOnly(true);
				county.setReadOnly(true);
			}else{
				county.disable(true); 
			}
			/*county.cls='readonlyhaveborder';*/
			city.eventType=['focus'];
			city.parentParamsAndItemIds = null;
			me.items=[city,county];
		}
		me.callParent([cfg]);
	}
});

/**
 * #########################################
 * #######      省市区相关下拉框结束        #######
 * #########################################
 */






/**
 * #########################################
 * #######      产品相关下拉框开始         #######
 * #########################################
 */
//产品Model
Ext.define('Miser.baseinfo.commonSelector.ProductModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'code'
			}, {
				name : 'name'
			}, {
				name : 'active'
			}, {
				name : 'notes'
			}, {
				name : 'levels'
			}, {
				name : 'parentCode'
			}, {
				name : 'parentName'
			}, {
				name : 'shortName'
			}]
 });
//产品store
Ext.define('Miser.baseinfo.commonSelector.ProductStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.ProductModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../base/productAction!queryProductCommonToLevelByCondition.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'productVo.productEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//运输类型公共选择器信息
Ext.define('Miser.commonSelector.CommonProductSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.commonproductselector', 
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	active : 'Y', 
	levelses : '3',//多个产品层级以","分隔
	parentCode : null, //父级产品编号
	queryParam : 'productVo.productEntity.queryParam',// 查询参数
	showContent : '{name}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.baseinfo.commonSelector.ProductStore'); 
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			var levelses = null; 
			if (!Ext.isEmpty(config.levelses)) {
				levelses = config.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] = levelses;
			}else{
				levelses = me.levelses.split(',');
				searchParams['productVo.productEntity.levelsList'] =levelses;
			}
			if (!Ext.isEmpty(config.parentCode)) {
				searchParams['productVo.productEntity.parentCode'] = config.parentCode;
			}
			searchParams['productVo.productEntity.active'] = me.active; 	
			Ext.apply(store.proxy.extraParams, searchParams);
		});
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######      产品相关下拉框结束         #######
 * #########################################
 */
/**
 * #########################################
 * #######      车辆相关下拉框开始        #######
 * #########################################
 */
//自备车model
Ext.define('Miser.baseinfo.commonSelector.OwnTruckModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'vehicleNo'    //车牌号(唯一)
			}/*, {
				name : 'orgCode'      //部门编码
			}, {
				name : 'orgName'      //部门名称
			}*/, {
				name : 'status'       //车辆状态
			}, {
				name : 'brand'       //车辆品牌
			}, {
				name : 'usedType'       //车辆用途
			}, {
				name : 'vehicleLength'       //车长
			}, {
				name : 'vehicleWidth'       //车宽
			}, {
				name : 'vehicleHeight'       //车高
			}, {
				name : 'ratedLoad'       //额定载重
			}, {
				name : 'gps'       //是否有GPS
			}, {
				name : 'vehicleType'       //车辆类型
			}/*, {
				name : 'registDate'       //登记日期
			}*/]
 });

//自备车下拉框store
Ext.define('Miser.commonOwnTruckSelector.OwnTruckCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OwnTruckModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOwnTruckSearchAction!seacrhOwnTruckByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOwnTruckVo.commonOwnTruckList',
			totalProperty : 'totalCount'
		}
	}
});
 
/**
 * 自备车单选
 */
Ext.define('Miser.commonOwnTruckSelector.OwnTruckCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.owntruckselector',
	displayField : 'vehicleNo',// 显示名称
	valueField : 'vehicleNo',// 值
	active : null,
	queryParam : 'commonOwnTruckVo.ownTruckSearchConditionEntity.queryParam',// 查询参数
	showContent : '{vehicleNo}&nbsp;&nbsp;&nbsp;{brand}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonOwnTruckSelector.OwnTruckCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOwnTruckVo.ownTruckSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonOwnTruckVo.ownTruckSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######      车辆相关下拉框结束        #######
 * #########################################
 */

/**
 * #########################################
 * #######      外雇司机信息相关下拉框开始        #######
 * #########################################
 */
//外雇司机信息model
Ext.define('Miser.baseinfo.commonSelector.LeasedDriverModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'driverName'    //司机姓名
			}, {
				name : 'address'       //地址
			}, {
				name : 'driverPhone'       //司机电话
			}, {
				name : 'vehicleNo'       //车牌号
			}, {
				name : 'idCard'       //身份证
			}, {
				name : 'driverLicense'       //驾驶证编号
			}, {
				name : 'qualification'       //从业资格证(号码)
			}, {
				name : 'licenseType'       //驾驶证类型
			}, {
				name : 'beginTime'       //驾照签发日期
			}, {
				name : 'expirationDate'       //有效期限
			}, {
				name : 'active'       //是否可用
			}]
 });

//外雇司机信息下拉框store
Ext.define('Miser.commonLeasedDriverSelector.LeasedDriverCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.LeasedDriverModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonLeasedDriverSearchAction!seacrhLeasedDriverByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonLeasedDriverVo.commonLeasedDriverList',
			totalProperty : 'totalCount'
		}
	}
});
 
/**
 * 外雇司机信息单选
 */
Ext.define('Miser.commonLeasedDriverSelector.LeasedDriverCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.leaseddriverselector',
	displayField : 'driverName',// 显示名称
	valueField : 'driverName',// 值
	active : null,
	queryParam : 'commonLeasedDriverVo.leasedDriverSearchConditionEntity.queryParam',// 查询参数
	showContent : '{driverName}&nbsp;&nbsp;&nbsp;{licenseType}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonLeasedDriverSelector.LeasedDriverCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonLeasedDriverVo.leasedDriverSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonLeasedDriverVo.leasedDriverSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######      外雇司机信息下拉框结束        #######
 * #########################################
 */

/**
 * #########################################
 * #######      偏线公司（合作公司）相关下拉框开始        #######
 * #########################################
 */
//偏线公司信息model
Ext.define('Miser.baseinfo.commonSelector.BusinessPartnerModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'agentCompanyCode'    //（合作公司）编码
			}, {
				name : 'agentCompanyName'    //（合作公司）名称
			}, {
				name : 'simpleName'       //简称
			}, {
				name : 'contact'       //联系人
			}, {
				name : 'contactPhone'       //联系电话
			}, {
				name : 'contactMobile'      //联系人手机
			}, {
				name : 'address'       //详细地址
			}, {
				name : 'active'       //是否可用
			}]
 });

//偏线公司信息下拉框store
Ext.define('Miser.commonBusinessPartnerSelector.BusinessPartnerCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.BusinessPartnerModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonBusinessPartnerSearchAction!seacrhBusinessPartnerByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonBusinessPartnerVo.commonBusinessPartnerList',
			totalProperty : 'totalCount'
		}
	}
});
 
/**
 * 偏线公司信息单选
 */
Ext.define('Miser.commonBusinessPartnerSelector.BusinessPartnerCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.businesspartnerselector',
	displayField : 'agentCompanyName',// 显示名称
	valueField : 'agentCompanyCode',// 值
	active : null,
	queryParam : 'commonBusinessPartnerVo.businessPartnerSearchConditionEntity.queryParam',// 查询参数
	showContent : '{agentCompanyName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonBusinessPartnerSelector.BusinessPartnerCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonBusinessPartnerVo.businessPartnerSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonBusinessPartnerVo.businessPartnerSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######       偏线公司（合作公司）信息下拉框结束        #######
 * #########################################
 */
/**
 * #########################################
 * #######      偏线网点相关下拉框开始        #######
 * #########################################
 */
//偏线网点信息model
Ext.define('Miser.baseinfo.commonSelector.OuterBranchModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'agentDeptCode'    //编码
			}, {
				name : 'agentDeptName'    //名称
			}, {
				name : 'simpleName'       //简称
			}, {
				name : 'contact'       //联系人
			}, {
				name : 'contactPhone'       //联系电话
			}, {
				name : 'contactMobile'      //联系人手机
			}, {
				name : 'pinyin'       //拼音
			}, {
				name : 'address'       //详细地址
			}, {
				name : 'active'       //是否可用
			}]
 });

//偏线网点信息下拉框store
Ext.define('Miser.commonOuterBranchSelector.OuterBranchCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OuterBranchModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOuterBranchSearchAction!seacrhOuterBranchByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'commonOuterBranchVo.commonOuterBranchList',
			totalProperty : 'totalCount'
		}
	}
});
 
/**
 * 偏线网点信息单选
 */
Ext.define('Miser.commonOuterBranchSelector.OuterBranchCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.outerBranchselector',
	displayField : 'agentDeptName',// 显示名称
	valueField : 'agentDeptCode',// 值
	active : null,
	queryParam : 'commonOuterBranchVo.outerBranchSearchConditionEntity.queryParam',// 查询参数
	showContent : '{agentDeptName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonOuterBranchSelector.OuterBranchCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonOuterBranchVo.outerBranchSearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonOuterBranchVo.outerBranchSearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######       偏线网点信息下拉框结束        #######
 * #########################################
 */




/**
 * #########################################
 * #######      价格城市相关下拉框开始        #######
 * #########################################
 */
/**
 * 价格城市model
 * 
 */
Ext.define('Miser.baseinfo.commonSelector.CardCityModel',{
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id'
			}, {
				name : 'cityNo'
			}, {
				name : 'cityName'
			}, {
				name : 'effective'
			}, {
				name : 'remarks'
			}, {
				name : 'province'
			}, {
				name : 'cityType'
			}]
});

//价格城市下拉框store
Ext.define('Miser.commonCardCitySelector.CardCityCombStore',{
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.CardCityModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonCardCitySearchAction!seacrhCardCityByParam.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonCardCityVo.commonCardCityList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 价格城市单选
 */
Ext.define('Miser.commonCardCitySelector.CardCityCommonSelector',{
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.dynamiccardcitycombselector',
	displayField : 'cityName',// 显示名称
	valueField : 'cityNo',// 值
	active : null,
	queryParam : 'commonCardCityVo.cardCitySearchConditionEntity.queryParam',// 查询参数
	showContent : '{cityName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonCardCitySelector.CardCityCombStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			if (!Ext.isEmpty(config.active)) {
				searchParams['commonCardCityVo.cardCitySearchConditionEntity.active'] = config.active;
			}else{
				searchParams['commonCardCityVo.cardCitySearchConditionEntity.active'] = 'Y';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});


/**
 * #########################################
 * #######      价格城市相关下拉框结束       #######
 * #########################################
 */



/**
 * #########################################
 * #######      附件上传按钮开始          #######
 * #########################################
 */
//数据字典下拉单选框
Ext.define('Miser.manage.FileUploads', {
	extend : 'Ext.container.Container',
	alias: 'widget.fileUploadsButton',
    displayField: 'valueName',
    valueField: 'valueCode',
    queryMode : 'local',
    editable : false,
    xtype: 'form-fileuploads',
    controller: 'form-fileuploads',
    items: [{
        items: [ {
            xtype: 'filefield',
            hideLabel: true,
            reference: 'basicFile'
        }]
    }]
});
/**
 * #########################################
 * #######      附件上传按钮结束          #######
 * #########################################
 */


/**
 * #########################################
 * #######      运输类型下拉框开始          #######
 * #########################################
 */
//运输类型Model
Ext.define("Miser.baseinfo.commonSelector.TranstypeModel", {
	extend : "Ext.data.Model",
	fields: [
	         {name: 'code', type: 'string'},
	         {name: 'name', type: 'string'}
	         ]
});

//运输类型Store
Ext.define("Miser.commonTranstypeSelector.TrantypeStore", {
	extend : "Ext.data.Store",
	model : 'Miser.baseinfo.commonSelector.TranstypeModel',
	pageSize : 100,
	proxy : {
		type : 'ajax',
		url : '../bizbase/transtypeAction!queryTranstypes.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'transtypeVo.transtypeEntities',
			totalProperty : 'totalCount'
		}
	}
});

//运输类型选择器
Ext.define('Miser.commonTranstypeSelector.TrantypeSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.transtypecombselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值
	queryParam : 'transtypeVo.transtypeEntity.name',// 查询参数
	showContent : '{name}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonTranstypeSelector.TrantypeStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			searchParams['transtypeVo.transtypeEntity.active'] = 'Y';
			//是否显示作废数据(封存数据，手动作废)
			if(!Ext.isEmpty(config.showAll)){
				if(config.showAll) {
					searchParams['transtypeVo.transtypeEntity.invalid'] = '';
				}
				else {
					searchParams['transtypeVo.transtypeEntity.invalid'] = 'N';
				}
			}else{
				searchParams['transtypeVo.transtypeEntity.invalid'] = '';
			}
			//是否显示“全部”
			if(!Ext.isEmpty(config.containsRoot)){
				if(config.containsRoot) {
					searchParams['transtypeVo.transtypeEntity.containsRoot'] = 'Y';
				}
				else {
					searchParams['transtypeVo.transtypeEntity.containsRoot'] = 'N';
				}
			}else{
				searchParams['transtypeVo.transtypeEntity.containsRoot'] = 'N';
			}
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});

//状态下拉框-start
Ext.define('Miser.model.commonSelector.statusModel',{
	extend : 'Ext.data.Model',
	fields : 
		[{name : 'name',type : 'string'},
		 {name : 'value',type : 'string'}
		]
});


Ext.define('Miser.commonStatusSelector.statusStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.commonSelector.statusModel',
    data : [{'name':'全部','value':'0'},
            {'name':'已失效','value':'1'},
            {'name':'生效中','value':'2'},
            {'name':'待生效','value':'3'},
            {'name':'已作废','value':'4'}
            ]
});
Ext.define('Miser.commonStatusSelector.statusSelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.statuscombselector',
	listWidth : this.width,// 下拉列表宽度，从外面传入
	multiSelect : false,// 从外面传入
	displayField : 'name',// 显示名称
	valueField : 'value',// 值
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store = Ext.create('Miser.commonStatusSelector.statusStore');
		me.callParent([cfg]);
	}
});
//状态下拉框-end


/**
 * #########################################
 * #######     价格城市下拉框开始         #######
 * #########################################
 */

/**
 * 价格城市model
 */
Ext.define('Miser.commonStartPriceCitySelector.PriceCityModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        type: 'string'
    },{
        name: 'code',
        type: 'string'
    },{
        name: 'name',
        type: 'string'
    }
    ]
});

/**
 * 价格城市store
 */
Ext.define('Miser.commonStartPriceCitySelector.PriceCityStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonStartPriceCitySelector.PriceCityModel',
    pageSize: 10,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/priceCityAction!queryPriceCities.action',
        reader: {
            type: 'json',
            rootProperty: 'priceCityVo.priceCityEntities',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});
//出发价格城市下拉框
Ext.define('Miser.commonStartPriceCitySelector.StartPriceCitySelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.startPriceCityselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）
	editable : true,//这项必须为true
	active : null,
	queryParam : 'priceCityVo.queryParam.name',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
	priceCityScope:'STANDARD',//出发价格城市类型 用以区分是标准价格城市 还是 老的客户价格城市
	initPriceCityScope:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonStartPriceCitySelector.PriceCityStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			searchParams['priceCityVo.queryParam.active'] = 'Y';
			searchParams['priceCityVo.queryParam.type'] = 'SEND';
			if(me.initPriceCityScope == null){
				searchParams['priceCityVo.queryParam.priceCityScope'] = me.priceCityScope;	
			} else {
				if(!Ext.isEmpty(me.initPriceCityScope())){
					searchParams['priceCityVo.queryParam.priceCityScope'] = me.initPriceCityScope();
				}
			}
			
			//是否显示作废数据(封存数据，手动作废)
			if(!Ext.isEmpty(config.showAll)){
				if(config.showAll) {
					searchParams['priceCityVo.queryParam.invalid'] = '';
				}
				else {
					searchParams['priceCityVo.queryParam.invalid'] = 'N';
				}
			}else{
				searchParams['priceCityVo.queryParam.invalid'] = '';
			}
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});

//到达价格城市下拉框
Ext.define('Miser.commonArrivePriceCitySelector.ArrivePriceCitySelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.arrivePriceCityselector',
	displayField : 'name',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）
	editable : true,//这项必须为true
	active : null,
	queryParam : 'priceCityVo.queryParam.name',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
	priceCityScope:'STANDARD',//出发价格城市类型 用以区分是标准价格城市 还是 老的客户价格城市
	initPriceCityScope:null,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonStartPriceCitySelector.PriceCityStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			
			searchParams['priceCityVo.queryParam.active'] = 'Y';
			searchParams['priceCityVo.queryParam.type'] = 'ARRIVAL';
			if(me.initPriceCityScope == null){
				searchParams['priceCityVo.queryParam.priceCityScope'] = me.priceCityScope;	
			} else {
				if(!Ext.isEmpty(me.initPriceCityScope())){
					searchParams['priceCityVo.queryParam.priceCityScope'] = me.initPriceCityScope();
				}
			}
			
			//是否显示作废数据(封存数据，手动作废)
			if(!Ext.isEmpty(config.showAll)){
				if(config.showAll) {
					searchParams['priceCityVo.queryParam.invalid'] = '';
				}
				else {
					searchParams['priceCityVo.queryParam.invalid'] = 'N';
				}
			}else{
				searchParams['priceCityVo.queryParam.invalid'] = '';
			}
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######     价格城市结束         #######
 * #########################################
 */

/**
 * #########################################
 * #######     出发时效城市下拉框开始         #######
 * #########################################
 */

/**
 * 时效城市model
 */
Ext.define('Miser.commonStartAgingCitySelector.StartAgingCityModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 
        type: 'string'
    },{
        name: 'code',
        // 编号
        type: 'string'
    },{
        name: 'agingCityName',
        // 编号
        type: 'string'
    }
    ]
});

/**
 * 出发时效城市store 
 */
Ext.define('Miser.commonStartAgingCitySelector.StartAgingCityStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonStartAgingCitySelector.StartAgingCityModel',
    pageSize: 10,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/agingCityAction!queryStartAgingCity.action',
        reader: {
            type: 'json',
            rootProperty: 'agingCityVo.agingCityList',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});
//出发时效城市下拉框
Ext.define('Miser.commonStartAgingCitySelector.StartAgingCitySelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.startAgingCityselector',
	displayField : 'agingCityName',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）
	editable : true,//这项必须为true
	active : null,
	queryParam : 'agingCityVo.agingCityEntity.agingCityName',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{agingCityName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonStartAgingCitySelector.StartAgingCityStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			searchParams['agingCityVo.agingCityEntity.active'] = 'Y';
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######     出发时效城市结束         #######
 * #########################################
 */
/**
 * 到达时效城市store 
 */
Ext.define('Miser.commonArriveAgingCitySelector.ArriveAgingCityStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.commonStartAgingCitySelector.StartAgingCityModel',
    pageSize: 10,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/agingCityAction!queryArriveAgingCity.action',
        reader: {
            type: 'json',
            rootProperty: 'agingCityVo.agingCityList',
            totalProperty: 'totalCount' // 总个数
        }
    }
    
});
//到达时效城市下拉框
Ext.define('Miser.commonArriveAgingCitySelector.ArriveAgingCitySelector', {
	extend : 'Miser.commonSelector.CommonCombSelector',
	alias : 'widget.arriveAgingCityselector',
	displayField : 'agingCityName',// 显示名称
	valueField : 'code',// 值

	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）
	editable : true,//这项必须为true
	active : null,
	queryParam : 'agingCityVo.agingCityEntity.agingCityName',// 查询参数
	showContent : '{code}&nbsp;&nbsp;&nbsp;{agingCityName}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);    
		me.store = Ext.create('Miser.commonArriveAgingCitySelector.ArriveAgingCityStore');
		
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.getParams();
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
			searchParams['agingCityVo.agingCityEntity.active'] = 'Y';
			
			Ext.apply(store.proxy.extraParams, searchParams);  
		})
		me.callParent([cfg]);
	}
});
/**
 * #########################################
 * #######     到达时效城市结束         #######
 * #########################################
 */

 
 // 门店下拉框store
Ext.define('Miser.commonOrgSelector.SalesDepartmentCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OrgModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOrgSearchAction!searchSalesDepartment.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//事业部下拉框store
Ext.define('Miser.commonOrgSelector.RoadAreaCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OrgModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOrgSearchAction!searchRoadArea.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	}
});

// 大区下拉框store
Ext.define('Miser.commonOrgSelector.BigRegionCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OrgModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOrgSearchAction!searchBigRegion.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	}
});

// 路区下拉框store
Ext.define('Miser.commonOrgSelector.DivisionCombStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.baseinfo.commonSelector.OrgModel',
	pageSize : 20,
	proxy : {
		type : 'ajax',
		url : '../base/commonOrgSearchAction!searchDivisions.action',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			rootProperty : 'commonOrgVo.commonOrgEntityList',
			totalProperty : 'totalCount'
		}
	}
});


/**
* #########################################
* #######     组织机构级联查询开始       #######
* #########################################
*/
 Ext.define('Miser.commonSelector.linkOrgCombSelector', {
	extend : 'Miser.commonselector.LinkageContainer',
	alias : 'widget.linkorgcombselector',
	type : 'D-B-R-S',// type ：D-B-R-S 事业部大区路区门店  B-R-S:大区路区门店  R-S:路区门店  S:门店
	width : 900,
	divisionWidth : 180,// 事业部长度
	divisionLabel : '',// 事业部label
	divisionName : '',// 事业部名称--对应name
	divisionIsBlank : true,
	divisionLabelWidth:null,
	bigregionWidth : 180,// 大区长度
	bigregionLabel : '',// 大区label
	bigregionLabelWidth:null,
	bigregionName : '',// 大区名称—对应name
	bigregionIsBlank : true,
	roadareaWidth : 180,// 路区长度
	roadareaLabel : '',// 路区label
	roadareaName : '',// 路区name
	roadareaIsBlank : true,
	roadareaLabelWidth:null,
	salesdepartmentWidth : 180,// 门店长度
	salesdepartmentLabel : '',// 门店label
	salesdepartmentName : '',// 门店name
	salesdepartmentIsBlank : true,
	salesdepartmentLabelWidth:null,
	// 关联对象配置开始
	// 对象所在窗体id
	formid:'',
	// 需要级联的客户名称选择控件
	linkcustomerfield:'',
	// 需要级联的客户编码选择控件
	linkcustomercodefield:'',
	// 直接选择门店时，是否需要关联父级组织，路区大区事业部
	loadParentOrg:false,
	// 是否加载用户默认部门	
	loaddefault:true,
	// 反转操作标识，如果是从门店向上级组织赋值为true，否则为false.
	isReverse:false,
	// 关联对象配置结束
	layout : 'column',
	labelWid : 20,
	readOnly : false,	
	clearCustomer:function(config)
	{
		if(!Ext.isEmpty(config.formid))
		{
			var queryForm = Ext.getCmp(config.formid);
	    	var customerObj = queryForm.getForm().findField(config.linkcustomerfield);
	    	var customerCodeObj = queryForm.getForm().findField(config.linkcustomercodefield);
	    	if(!Ext.isEmpty(customerObj))
			{
	    		customerObj.setValue(null);customerObj.realValue = null;
	    	}
	    	if(!Ext.isEmpty(customerCodeObj))
			{
	    		customerCodeObj.setValue(null);customerCodeObj.realValue = null;
	    	}
		}
	},
	loadCustomer:function(config)
	{
		if(!Ext.isEmpty(config.formid))
		{
			var queryForm = Ext.getCmp(config.formid);
			if(!Ext.isEmpty(config.linkcustomerfield))
			{
	    		var customerObj = queryForm.getForm().findField(config.linkcustomerfield);
		    	if(!Ext.isEmpty(customerObj))
				{
					customerObj.getStore().load();
				}
	    	}
	    	if(!Ext.isEmpty(config.linkcustomercodefield))
	    	{
				var customerCodeObj = queryForm.getForm().findField(config.linkcustomercodefield);
				if(!Ext.isEmpty(customerCodeObj))
				{
					customerCodeObj.getStore().load();
				}
			}
		}
	},
	loadParentOrgInfo:function(config, orgCode)
	{
		if(!Ext.isEmpty(config.formid))
		{			
			var params = {	                
                    'orgAdministrativeVo':{'orgCode': orgCode}
                    };   				
			var successFun = function (json) {
					var tagForm = Ext.getCmp(config.formid);
					var roadAreaObj = tagForm.getForm().findField(config.roadareaName);
					var bigRegionObj = tagForm.getForm().findField(config.bigregionName);
					var divisionObj = tagForm.getForm().findField(config.divisionName);
					if(!Ext.isEmpty(roadAreaObj))
					{
						roadAreaObj.setCombValue(json.orgInfoEntity.parentName, json.orgInfoEntity.parentCode,2);						
					}							
					if(!Ext.isEmpty(bigRegionObj))
					{
						bigRegionObj.setCombValue(json.orgInfoEntity.bigRegionName, json.orgInfoEntity.bigRegionCode,1);
					}
					if(!Ext.isEmpty(divisionObj))
					{
						divisionObj.setCombValue(json.orgInfoEntity.divisionName, json.orgInfoEntity.divisionCode,0);
					}
					config.isReverse = false;
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						miser.showErrorMes('连接超时'); //请求超时
					} else {
						var message = json.message;
						miser.showErrorMes(message); //提示失败原因
					}
				};
			miser.requestJsonAjax('../base/orgAdministrativeAction!queryOrgAdministrativeInfoByCode.action', params, successFun, failureFun); 
			
		}
	},
	setReadOnly : function(flag){
		var me =this;
		me.getDivision().setReadOnly(flag);
		me.getBigRegion().setReadOnly(flag);
		me.getRoadArea().setReadOnly(flag);
		me.getSalesDepartment().setReadOnly(flag);
	},
	getDefults : function(config) {
		return {
			labelWidth : config.labelWid,
			labelAlign: "right",
			minChars : 0,
			labelSeparator : ''
		}
	},
	setOrgValue : function(displayText, valueText, order) {// 0：事业部的值，1：大区的值，2：路区的值，3：门店的值
		var me = this;
		var orgObj=null;
		if(!Ext.isEmpty(order)){
			if(order == '0'){
				orgObj=me.division;
			}else if(order == '1'){
				orgObj=me.bigregion;
			}else if(order == '2'){
				orgObj=me.roadarea;
			}else if(order == '3'){
				orgObj=me.salesdepartment;
			}
		}
		var  key = orgObj.displayField + '', value =orgObj.valueField
				+ '';
		var m = Ext.create(orgObj.store.getModel().getName());
		m.set(key, displayText);
		m.set(value, valueText);
		orgObj.store.loadRecords([m]);
		orgObj.setValue(valueText);
	},
	division : null,
	getDivision:function(bigregionObj,parent,config){
		if(Ext.isEmpty(this.division)){
			this.division=Ext.widget('linkagecomboselector',{
				bigregionObj : bigregionObj,
				eventType : ['focus'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
				name : 'bigregion',
				itemId : 'Miser_baseinfo_Division_ID',
				store : Ext.create('Miser.commonOrgSelector.DivisionCombStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						if(!config.isReverse){
						ths.bigregionObj.setValue(null);
						ths.bigregionObj.realValue = null;
						}
					},
					'select' : function(ths, the, eOpts){
						ths.bigregionObj.enable();
						ths.bigregionObj.getStore().load();
						parent.loadCustomer(config);
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',
				width : config.divisionWidth,
				fieldLabel : config.divisionLabel,
				name : config.divisionName,
				labelWidth : config.divisionLabelWidth,
				allowBlank : config.divisionIsBlank,
				isPaging: false, 
				queryParam : 'commonOrgVo.orgSearchConditionEntity.queryParam',
				margin:config.margin
			});
		}
		return this.division;
	},
	bigregion : null,
	getBigRegion:function(roadareaObj,parent,config){
		if(Ext.isEmpty(this.bigregion)){
			this.bigregion=Ext.widget('linkagecomboselector',{
				roadareaObj : roadareaObj,
				itemId : 'Miser_baseinfo_BigRegion_ID',
				eventType : ['callparent'], 
				parentParamsAndItemIds : {
					'commonOrgVo.orgSearchConditionEntity.parentCode' : 'Miser_baseinfo_Division_ID'
				},
				queryParam : 'commonOrgVo.orgSearchConditionEntity.queryParam',
				parent:parent,				
				store : Ext.create('Miser.commonOrgSelector.BigRegionCombStore'),// 从外面传入
				listeners : {
					'change' : function(ths, the, eOpts){
						if(!config.isReverse){
						ths.roadareaObj.setValue(null);
						ths.roadareaObj.realValue = null;
						}
					},
					'select' : function(ths, the, eOpts){
						ths.roadareaObj.enable();
						ths.roadareaObj.getStore().load();
						parent.loadCustomer(config);
					}
				},
				displayField : 'name',// 显示名称
				valueField : 'code',
				showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',
				minChars : 0,
				width : config.bigregionWidth,
				fieldLabel : config.bigregionLabel,
				name : config.bigregionName,
				labelWidth : config.bigregionLabelWidth,
				allowBlank : config.bigregionIsBlank,
				isPaging: false,
				margin:config.margin
			});
		}
		return this.bigregion;
	},
	roadarea : null,
	getRoadArea : function(salesdepartmentObj,parent,config){
		if(Ext.isEmpty(this.roadarea)){
				this.roadarea=Ext.widget('linkagecomboselector',{
					salesdepartmentObj : salesdepartmentObj,
					itemId : 'Miser_baseinfo_RoadArea_ID',
					eventType : ['callparent'],// 一般callparent包含focus事件，如果有callparent,则focus事件可不用传递
					store : Ext.create('Miser.commonOrgSelector.RoadAreaCombStore'),// 从外面传入
					listeners : {
						'change' : function(ths, the, eOpts){
						if(!config.isReverse){
							ths.salesdepartmentObj.setValue(null);
							ths.salesdepartmentObj.realValue = null;
							}
						},
						'select' : function(ths, the, eOpts){
							ths.salesdepartmentObj.enable();
							ths.salesdepartmentObj.getStore().load();
							parent.loadCustomer(config);
						}
					},
					displayField : 'name',// 显示名称
					valueField : 'code',
					minChars : 0,
					showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',
					width : config.roadareaWidth,
					fieldLabel : config.roadareaLabel,
					name : config.roadareaName,
					labelWidth : config.roadareaLabelWidth,
					allowBlank : config.roadareaIsBlank,
					isPaging: false,  
					parentParamsAndItemIds : {
						'commonOrgVo.orgSearchConditionEntity.parentCode' : 'Miser_baseinfo_BigRegion_ID'
					},
					queryParam : 'commonOrgVo.orgSearchConditionEntity.queryParam',
					margin:config.margin
			});
		}
		return this.roadarea;
	},
	salesdepartment : null,
	getSalesDepartment:function(parent,config){
		if(Ext.isEmpty(this.salesdepartment)){
			this.salesdepartment=Ext.widget('linkagecomboselector',{
				store : Ext.create('Miser.commonOrgSelector.SalesDepartmentCombStore'),// 从外面传入
				listeners : {					
						'select' : function(ths, the, eOpts){
							if(config.loadParentOrg)
							{
								config.isReverse = true;
								parent.loadParentOrgInfo(config, the.data.code);
							}
							parent.loadCustomer(config);
						}
					},
				displayField : 'name',// 显示名称
				valueField : 'code',
				showContent : '{name}&nbsp;&nbsp;&nbsp;{logistCode}',
				minChars : 0,
				width : config.salesdepartmentWidth,
				fieldLabel : config.salesdepartmentLabel,
				name : config.salesdepartmentName,
				allowBlank : config.salesdepartmentIsBlank,
				labelWidth : config.salesdepartmentLabelWidth,
				isPaging: false, 
				parentParamsAndItemIds : {
					'commonOrgVo.orgSearchConditionEntity.parentCode' : 'Miser_baseinfo_RoadArea_ID'
				},// 此处区域不需要传入
				queryParam : 'commonOrgVo.orgSearchConditionEntity.queryParam',
				margin:config.margin
				});
			}
		return  this.salesdepartment;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = me.getDefults(config);
		var salesdepartment = me.getSalesDepartment(me,config);
		var roadarea = me.getRoadArea(salesdepartment,me,config);
		var bigregion = me.getBigRegion(roadarea,me,config);
		var division = me.getDivision(bigregion,me,config);
		me.items=[division,bigregion,roadarea,salesdepartment];
		me.callParent([cfg]);
		if(config.loaddefault==true)
		{
			var orgObj = getUserContext().getCurrentUserDept();
			if(!Ext.isEmpty(orgObj))
			{
				var isDivision = orgObj.isDivision;
				var isBigRegion = orgObj.isBigRegion;
				var isRoadArea = orgObj.isRoadArea;
				var isSales = orgObj.isSalesDepartment;			
					if(isDivision == 'Y')
					{
						me.setOrgValue(orgObj.divisionName, orgObj.divisionCode,0);
						me.getDivision().disable();	
					}
					else if(isBigRegion == 'Y')
					{
						me.setOrgValue(orgObj.divisionName, orgObj.divisionCode,0);
						me.setOrgValue(orgObj.bigRegionName,orgObj.bigRegionCode,1);
						me.getDivision().disable();
						me.getBigRegion().disable();	
					}
					else if(isRoadArea == 'Y')
					{
						me.setOrgValue(orgObj.divisionName, orgObj.divisionCode,0);
						me.setOrgValue(orgObj.bigRegionName,orgObj.bigRegionCode,1);
						me.setOrgValue(orgObj.name,orgObj.code,2);
						me.getDivision().disable();
						me.getBigRegion().disable();
						me.getRoadArea().disable();
					}
					else if(isSales == 'Y')
					{
						me.setOrgValue(orgObj.divisionName, orgObj.divisionCode,0);
						me.setOrgValue(orgObj.bigRegionName,orgObj.bigRegionCode,1);
						me.setOrgValue(orgObj.parentName,orgObj.parentCode,2);
						me.setOrgValue(orgObj.name,orgObj.code,3);
						me.getDivision().disable();
						me.getBigRegion().disable();
						me.getRoadArea().disable();
						me.getSalesDepartment().disable();
					}
			}
		}
	}
});
/**
 * #########################################
 * #######      组织机构级联查询结束        #######
 * #########################################
 */
 
 /**
  * #########################################
  * #######      客户信息选择下拉框开始         #######
  * #########################################
  */

 /**
  * 客户信息model
  */
 Ext.define('Miser.commonBseCustomerSelector.BseCustomerCombModel', {
     extend: 'Ext.data.Model',
     fields: [{
         name: 'id',
         // 
         type: 'string'
     },{
         name: 'code',
         // 客户编号
         type: 'string'
     },{
         name: 'name',
         // 客户名称
         type: 'string'
     },
     {
     	name:'orgCode',
     	// 门店编码
     	type:'string'
     },
     {
     	// 路区编码
     	name:'roadAreaCode',
     	type:'string'
     },
     {
     	// 大区编码
     	name:'bigRegionCode',
     	type:'string'
     },
     {
     	// 事业部编码
     	name:'divisionCode',
     	type:'string'
     }
     ]
 });

 /**
  * 客户信息store 
  */
 Ext.define('Miser.commonBseCustomerSelector.BseCustomerCombStore', {
     extend: 'Ext.data.Store',
     model: 'Miser.commonBseCustomerSelector.BseCustomerCombModel',
     pageSize: 20,
     proxy: {
         type: 'ajax',
         actionMethods: 'post',
         url: '../pricecard/bseCustomer!queryBseCustomer.action',
         reader: {
             type: 'json',
             rootProperty: 'bseCustomerVo.bseCustomerEntityList',
             totalProperty: 'totalCount' // 总个数
         }
     }
     
 });


 //客户名称下拉框，支持组织级联限制查询范围
 Ext.define('Miser.commonBseCustomerSelector.DynamicBseCustomerSelector', {
 	extend : 'Miser.commonSelector.CommonCombSelector',
 	alias : 'widget.bsecustomercombselector',
 	displayField : 'name',// 显示名称
 	valueField : 'code',// 值
 	divisionCodeField:'',
 	bigRegionCodeField:'',
 	roadAreaCodeField:'',
	orgCodeField : '',
	codeField:'',
	formid : '',
 	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）

 	active : null,
 	queryParam : 'bseCustomerVo.name',// 查询参数
 	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
 	listeners : {						
					'select' : function(comb, the, eOpts){
						if(!Ext.isEmpty(comb.config.formid))
						{
							var targForm = Ext.getCmp(comb.config.formid);
							if(!Ext.isEmpty(comb.config.divisionCodeField))
							{
								var divisionObj = targForm.getForm().findField(comb.config.divisionCodeField);
								divisionObj.setCombValue(the.data.divisionName, the.data.divisionCode);
							}
							if(!Ext.isEmpty(comb.config.bigRegionCodeField))
							{
								var bigRegionObj = targForm.getForm().findField(comb.config.bigRegionCodeField);
								bigRegionObj.setCombValue(the.data.bigRegionName, the.data.bigRegionCode);
							}
							if(!Ext.isEmpty(comb.config.roadAreaCodeField))
							{
								var roadAreaObj = targForm.getForm().findField(comb.config.roadAreaCodeField);
								roadAreaObj.setCombValue(the.data.roadAreaName, the.data.roadAreaCode);
							}
							if(!Ext.isEmpty(comb.config.orgCodeField))
							{
								var orgObj = targForm.getForm().findField(comb.config.orgCodeField);
								orgObj.setCombValue(the.data.orgName, the.data.orgCode);
							}
							if(!Ext.isEmpty(comb.config.codeField))
							{
								var codeObj = targForm.getForm().findField(comb.config.codeField);
								codeObj.setCombValue(the.data.code,the.data.code);
							}						
						}		
					}
				},
 	constructor : function(config) {
 		var me = this, cfg = Ext.apply({}, config);    
 		me.store = Ext.create('Miser.commonBseCustomerSelector.BseCustomerCombStore'); 		
 		me.store.addListener('beforeload', function(store, operation, eOpts) {
 			var searchParams = operation.getParams();
 			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
			}
 			// 如果设置了级联对象，设置级联查询参数
 			if(!Ext.isEmpty(config.formid))
 			{
 				var queryForm = Ext.getCmp(config.formid);
 			    var orgCode = queryForm.getForm().findField(config.orgCodeField).getValue();
 				var roadAreaCode = queryForm.getForm().findField(config.roadAreaCodeField).getValue();
 				var bigRegionCode = queryForm.getForm().findField(config.bigRegionCodeField).getValue();
 				var divisionCode = queryForm.getForm().findField(config.divisionCodeField).getValue();
 				if(!Ext.isEmpty(orgCode))
 				{
 					searchParams['bseCustomerVo.orgCode'] = orgCode;
 					searchParams['bseCustomerVo.roadAreaCode']=null;searchParams['bseCustomerVo.bigRegionCode']=null;searchParams['bseCustomerVo.divisionCode']=null;
 				}
 				else if(!Ext.isEmpty(roadAreaCode))
 				{
 					searchParams['bseCustomerVo.orgCode']=null;searchParams['bseCustomerVo.bigRegionCode']=null;searchParams['bseCustomerVo.divisionCode']=null; 				
 					searchParams['bseCustomerVo.roadAreaCode'] = roadAreaCode;
 				}
 				else if(!Ext.isEmpty(bigRegionCode))
 				{
 					searchParams['bseCustomerVo.orgCode']=null;searchParams['bseCustomerVo.roadAreaCode']=null;searchParams['bseCustomerVo.divisionCode']=null; 
 					searchParams['bseCustomerVo.bigRegionCode'] = bigRegionCode;
 				}
 				else if(!Ext.isEmpty(divisionCode))
 				{
 					searchParams['bseCustomerVo.orgCode']=null;searchParams['bseCustomerVo.roadAreaCode']=null;searchParams['bseCustomerVo.bigRegionCode']=null; 
 					searchParams['bseCustomerVo.divisionCode'] = divisionCode;
 				}
 				else
 				{
 					searchParams['bseCustomerVo.orgCode']=null;searchParams['bseCustomerVo.roadAreaCode']=null;searchParams['bseCustomerVo.bigRegionCode']=null; searchParams['bseCustomerVo.divisionCode']=null; 
 				}
 			}
 			searchParams['bseCustomerVo.active'] = 'Y';
 			
 			Ext.apply(store.proxy.extraParams, searchParams);  
 		})
 		me.callParent([cfg]);
 	}
 });
 
 //客户编号下拉框,精准查询后赋值给组织机构和客户名称
 Ext.define('Miser.commonBseCustomerSelector.DynamicBseCustomerCodeSelector', {
 	extend : 'Miser.commonSelector.CommonCombSelector',
 	alias : 'widget.bsecustomercodecombselector',
 	displayField : 'code',// 显示名称
 	valueField : 'code',// 值
 	divisionCodeField:'',
 	bigRegionCodeField:'',
 	roadAreaCodeField:'',
	orgCodeField : '',
	nameField:'',
	formid : '',
 	isEnable : null,//当前是否可用（Y，N--根据启用时间，失效时间判定）
 	active : null,
 	queryParam : 'bseCustomerVo.code',// 查询参数
 	showContent : '{code}&nbsp;&nbsp;&nbsp;{name}',// 显示表格列
 	listeners : {						
					'select' : function(comb, the, eOpts){
						if(!Ext.isEmpty(comb.config.formid))
						{
							var targForm = Ext.getCmp(comb.config.formid);
							if(!Ext.isEmpty(comb.config.divisionCodeField))
							{
								var divisionObj = targForm.getForm().findField(comb.config.divisionCodeField);
								divisionObj.setCombValue(the.data.divisionName, the.data.divisionCode);
							}
							if(!Ext.isEmpty(comb.config.bigRegionCodeField))
							{
								var bigRegionObj = targForm.getForm().findField(comb.config.bigRegionCodeField);
								bigRegionObj.setCombValue(the.data.bigRegionName, the.data.bigRegionCode);
							}
							if(!Ext.isEmpty(comb.config.roadAreaCodeField))
							{
								var roadAreaObj = targForm.getForm().findField(comb.config.roadAreaCodeField);
								roadAreaObj.setCombValue(the.data.roadAreaName, the.data.roadAreaCode);
							}
							if(!Ext.isEmpty(comb.config.orgCodeField))
							{
								var orgObj = targForm.getForm().findField(comb.config.orgCodeField);
								orgObj.setCombValue(the.data.orgName, the.data.orgCode);
							}
							if(!Ext.isEmpty(comb.config.nameField))
							{
								var nameObj = targForm.getForm().findField(comb.config.nameField);
								nameObj.setCombValue(the.data.name,comb.getValue());
							}						
						}		
					}
				},
 	constructor : function(config) {
 		var me = this, cfg = Ext.apply({}, config);    
 		me.store = Ext.create('Miser.commonBseCustomerSelector.BseCustomerCombStore'); 		
 		me.store.addListener('beforeload', function(store, operation, eOpts) {
 			var searchParams = operation.getParams();
 			if (Ext.isEmpty(searchParams)) {
					searchParams = {};
				}
 			searchParams['bseCustomerVo.active'] = 'Y'; 			
 			Ext.apply(store.proxy.extraParams, searchParams);  
 		})
 		me.callParent([cfg]);
 	}
 });
 /**
  * #########################################
  * #######      客户信息选择下拉框结束         #######
  * #########################################
  */
 
 /**
  * #########################################
  * #######    热门城市选择器 开始       #######
  * 取值时请用 selectedValue selectedText
  * value rawValue :
  * 还不清楚是哪里导致始终为展示的汉字...
  * 
  * districtProvinceArr：省份数据最初是从后台获取
  * 但是当展示选择器后 立即很快地速度点击 省份标签
  * 省份及后面的标签会被去掉 因为这时后台还有可能没
  * 返回过来 后面的添加逻辑还没来得及处理
  * #########################################
  */
 Ext.define('Miser.commonSelector.hotCitySelector',{
		extend : 'Ext.form.field.Picker',
		alias: 'widget.hotCitySelector',
		triggerCls:Ext.baseCSSPrefix + 'form-search-trigger',
		districtProvinceArr:[
			{'districtName':'北京市','districtCode':'110000','districtShortName':'北京'},
			{'districtName':'天津市','districtCode':'120000','districtShortName':'天津'},
			{'districtName':'河北省','districtCode':'130000','districtShortName':'河北'},
			{'districtName':'山西省','districtCode':'140000','districtShortName':'山西'},
			{'districtName':'内蒙古自治区','districtCode':'150000','districtShortName':'内蒙古'},
			{'districtName':'辽宁省','districtCode':'210000','districtShortName':'辽宁'},
			{'districtName':'吉林省','districtCode':'220000','districtShortName':'吉林'},
			{'districtName':'黑龙江省','districtCode':'230000','districtShortName':'黑龙江'},
			{'districtName':'上海市','districtCode':'310000','districtShortName':'上海'},
			{'districtName':'江苏省','districtCode':'320000','districtShortName':'江苏'},
			{'districtName':'浙江省','districtCode':'330000','districtShortName':'浙江'},
			{'districtName':'安徽省','districtCode':'340000','districtShortName':'安徽'},
			{'districtName':'福建省','districtCode':'350000','districtShortName':'福建'},
			{'districtName':'江西省','districtCode':'360000','districtShortName':'江西'},
			{'districtName':'山东省','districtCode':'370000','districtShortName':'山东'},
			{'districtName':'河南省','districtCode':'410000','districtShortName':'河南'},
			{'districtName':'湖北省','districtCode':'420000','districtShortName':'湖北'},
			{'districtName':'湖南省','districtCode':'430000','districtShortName':'湖南'},
			{'districtName':'广东省','districtCode':'440000','districtShortName':'广东'},
			{'districtName':'广西壮族自治区','districtCode':'450000','districtShortName':'广西'},
			{'districtName':'海南省','districtCode':'460000','districtShortName':'海南'},
			{'districtName':'重庆市','districtCode':'500000','districtShortName':'重庆'},
			{'districtName':'四川省','districtCode':'510000','districtShortName':'四川'},
			{'districtName':'贵州省','districtCode':'520000','districtShortName':'贵州'},
			{'districtName':'云南省','districtCode':'530000','districtShortName':'云南'},
			{'districtName':'西藏自治区','districtCode':'540000','districtShortName':'西藏'},
			{'districtName':'陕西省','districtCode':'610000','districtShortName':'陕西'},
			{'districtName':'甘肃省','districtCode':'620000','districtShortName':'甘肃'},
			{'districtName':'青海省','districtCode':'630000','districtShortName':'青海'},
			{'districtName':'宁夏回族自治区','districtCode':'640000','districtShortName':'宁夏'},
			{'districtName':'新疆维吾尔自治区','districtCode':'650000','districtShortName':'新疆'},
			{'districtName':'台湾省','districtCode':'710000','districtShortName':'台湾'},
			{'districtName':'香港特别行政区','districtCode':'810000','districtShortName':'香港'},
			{'districtName':'澳门特别行政区','districtCode':'820000','districtShortName':'澳门'}
		],
		createDistrictBtn:function(tabId,record){
			 var me = this;
			 var districtCode = record.districtCode;
			 var districtShortName = record.districtShortName;
			 var districtName = record.districtName;
			 
			 me.hisDataMapObj[districtCode] = record;
   				 
			 var curBtn = Ext.create('Ext.button.Button',{
				 text:districtShortName,
				 tooltip:districtName,
				 preventDefault:true,
				 pressedCls:'hot-btn-item',
				 enableToggle:true,
				 pressed:true,
				 style:'width:60px;height:23px;cursor:pointer;margin:5px;border:0px solid black;background-color:white;',
				 listeners:{
					 'click':function(btnSelf,event,eOpts){
						 
						var hotCityObj = document.getElementById(tabId+'-body');
						var objArr = hotCityObj.getElementsByClassName('x-btn-inner x-btn-inner-default-small');
						Ext.each(objArr,function(record,index,records){
							record.style.fontWeight='normal';
							record.style.color='#777777';
							record.style.height='23px';
						});
					 
						me.showSubDistrictInfo(tabId,record);
					 },
					 'mouseover':function(btnSelf,event,eOpts){
						btnSelf.removeCls('x-btn-default-small');
						btnSelf.removeCls('x-btn-over');
						
						var btnElObj = btnSelf.getEl();
						var curBtnObj = document.getElementById(btnElObj.id+'-btnInnerEl');
						curBtnObj.style.color = '#F15B23';
						curBtnObj.style.marginTop='2px';
						
   				     },
					 'mouseout':function(btnSelf,event,eOpts){
						btnSelf.addCls('x-btn-default-small');
						btnSelf.addCls('x-btn-over');
						
						var btnElObj = btnSelf.getEl();
						var curBtnObj = document.getElementById(btnElObj.id+'-btnInnerEl');
						curBtnObj.style.color = '#777777';
						curBtnObj.style.marginTop='-1px';
   				     }   						 
				 }
			 });
			 return curBtn;
		},
		createPicker : function(){
			var me = this;
			
			var picker = Ext.create('Ext.tab.Panel', {
			    frame : true,
                items: [
                     {
                     	 id:me.name+'_hotCitySelector_tab_hotCity',
                         title: '热门城市',
                         items:[],
                         layout:{
                         	type:'table',
                         	columns:'6'
                         }
                     },
                     {
                     	id:me.name+'_hotCitySelector_tab_province',
                     	title: '省份',
                        layout:{
                        	type:'table',
                        	columns:'6'
                        }
                     },
                     {
                     	id:me.name+'_hotCitySelector_tab_city',
                     	title: '城市',
                        layout:{
                        	type:'table',
                        	columns:'6'
                        }                   	
                     },
                     {
                     	id:me.name+'_hotCitySelector_tab_area',
                     	title: '区县',
                        layout:{
                        	type:'table',
                        	columns:'6'
                        }                     	
                     }
             ],			    
			    floating: true,
	            hidden: true,
	            width : 420,
	            height: 280,
	            columnLines : true,
	            focusOnToFront: false
			});
			
			me.setTabTitleWidth(picker);
			me.picker = picker;
			me.getHotCityStore();
			return picker;
		},
	    triggers:{
	        clear : {
	                cls : 'x-form-clear-trigger',
	                handler : function(){
	                    this.setRawValue(null);
	                    this.setValue(null);
	                    this.selectedValue = null;
	                    this.selectedText = null;
	                    this.getTrigger('clear').hide();
	                },
	                hidden : true
	        }
	    },
		setTabTitleWidth:function(picker){
			
			//设置页签的宽度
			for(var n=0;n<picker.items.items.length;n++){
				var curTab = picker.items.items[n];
				if(n == 0){
					curTab.tab.setWidth(100);
				} else {
					curTab.tab.setWidth(85);
				}
			}
		},
		selectedValue:null,//选择的数据项的值
		selectedText:null,//选择的数据项的文本描述
	    matchFieldWidth:false,
	    hisDataMapObj:{},//每一次点击时将对应数据缓存到该对象 districtCode-record
	    getHotCityStore:function(){//用于获取 热门城市
	    	
	    	var me = this;
	    	
	    	//注意这里的三个数组的数据位置要一一对应
	    	var hotCityDistrictNameArr = ['广州市','深圳市','佛山市','东莞市',
	    	                              '重庆市','成都市','青岛市','武汉市',
	    	                              '北京市','天津市','沈阳市','上海市',
	    	                              '苏州市','杭州市','宁波市','南昌市'];
	    	
	    	var hotCityDistrictShortNameArr = ['广州','深圳','佛山','东莞',
	    	                                   '重庆','成都','青岛','武汉',
	    	                                   '北京','天津','沈阳','上海',
	    	                                   '苏州','杭州','宁波','南昌'];
	    	
	    	var hotCityDistrictParentNameArr = ['广东省','广东省','广东省','广东省',
	    	                                    '重庆市','四川省','山东省','湖北省',
	    	                                    '北京市','天津市','辽宁省','上海市',
	    	                                    '江苏省','浙江省','浙江省','江西省'];	    	
	    	
	    	var hotCityDistrictCodeArr = ['440100','440300','440600','441900',
	    	                              '500100','510100','370200','420100',
	    	                              '110100','120100','210100','310100',
	    	                              '320500','330100','330200','360100'];
	    	
	    	//针对于 直接点击 热门城市后 点击区县 选择后 若清空 再直接点击 区县或直接显示编码 并报错
	    	//需要参照从后台的获取的方式 需要将父级编码 名称也得存放
	    	var hotCityParentDistrictCodeArr = ['440000','440000','440000','440000',
	    	                                    '500000','510000','370000','420000',
	    	                                    '110000','120000','210000','310000',
	    	                                    '320000','330000','330000','360000'];
	    	var hotCityDataArr = [];
	    	
	    	for(var k=0;k<hotCityDistrictNameArr.length;k++){
	    		var curDistrictName = hotCityDistrictNameArr[k];
	    		var curDistrictCode = hotCityDistrictCodeArr[k];
	    		var curDistrictShortName = hotCityDistrictShortNameArr[k];
	    		var curParentName = hotCityDistrictParentNameArr[k];
	    		var curParentDistrictCode = hotCityParentDistrictCodeArr[k];
	    		
	    		var curDataObj = new Object();
	    		
		    	curDataObj.districtName = curDistrictName;
		    	curDataObj.districtCode = curDistrictCode;
		    	curDataObj.districtShortName = curDistrictShortName;
		    	curDataObj.parentDistrictName = curParentName;
		    	curDataObj.parentDistrictCode = curParentDistrictCode;
		    	
		    	hotCityDataArr.push(curDataObj);
		    	me.hisDataMapObj[curDistrictCode] = curDataObj;
		    	
		    	me.hisDataMapObj[curParentDistrictCode] = {
		    			'districtCode':curParentDistrictCode,
		    			'districtName':curParentName,
		    			'districtShortName':curParentName
		    	};
	    	}
	    	
	    	 var btnArr = [];
  			 Ext.each(hotCityDataArr,function(record,index,records){
   			    var curBtn = me.createDistrictBtn(me.name+'_hotCitySelector_tab_hotCity',record);
   				btnArr.push(curBtn);
   			 },this);
	    	
	    	 var picker = me.picker;
				 
		     //当前为 热门城市
  			 curTab = picker.child('#'+me.name+'_hotCitySelector_tab_hotCity');
  			 picker.removeAll();
  			 
  			  picker.add({
             	id:me.name+'_hotCitySelector_tab_hotCity',
            	title:'热门城市',
                layout:{
               	  type:'table',
               	  columns:'6'
                },
                items:btnArr,
                width:me.picker.width
  			  });
  			  
  			 btnArr = [];
  			 
  			 Ext.each(me.districtProvinceArr,function(record,index,records){
   			     var curBtn = me.createDistrictBtn(me.name+'_hotCitySelector_tab_province',record);
   				 btnArr.push(curBtn);
   			 },this);
  			 
 			 picker.add({
 	             id:me.name+'_hotCitySelector_tab_province',
 	             title:'省份',
 	             layout:{
 	               	type:'table',
 	               	columns:'6'
 	             },
 	             items:btnArr,
 	             width:me.picker.width
 	  		 });
  			  
  			 //这里城市 区县 一定要重新创建 之前提及备份原始的 实际上不行 这里要重新创建
 			 //否则 点击 城市 或 区县后 点击  tab 页签头无反应 也切换不到 热门 省份标签 
   			 picker.add({
                  	id:me.name+'_hotCitySelector_tab_city',
                 	title:'城市',
                    layout:{
                    	type:'table',
                    	columns:'6'
                    },
                    items:[],
                    width:me.picker.width
	   	     });		   			 
   			 
   			 picker.add({
                  	id:me.name+'_hotCitySelector_tab_area',
                 	title:'区县',
                    layout:{
                    	type:'table',
                    	columns:'6'
                    },
                    items:[],
                    width:me.picker.width
	   	     });	
  			 
  			  me.setTabTitleWidth(picker);
  			  
			  var hotCityObj = document.getElementById(me.name+'_hotCitySelector_tab_hotCity'+'-body');
			  if(!Ext.isEmpty(hotCityObj)){
				  var objArr = hotCityObj.getElementsByClassName('x-btn-inner x-btn-inner-default-small');
				  Ext.each(objArr,function(record,index,records){
					record.style.color='#777777';
				  });				  
			  }
			  
   			  curTab.show();
   			  picker.setActiveTab(curTab);	
	    },
	    displayCountOfRow:6,//第一行展示的个数
	    showSubDistrictInfo:function(tabId,record){//当点击一个选项后 判断是否设置 还是继续展示下级区域信息
	    	var me = this,districtCode = record.districtCode;
	    	me.hisDataMapObj[districtCode] = record;
	    	
	    	if(tabId == me.name+'_hotCitySelector_tab_hotCity'){ //热门城市
	    		me.getAreaStore(districtCode);
	    	} else if(tabId == me.name+'_hotCitySelector_tab_province'){ //省份
	    		me.getCityStore(districtCode);
	    	} else if(tabId == me.name+'_hotCitySelector_tab_city'){ //城市
	    		me.getAreaStore(districtCode);
	    	} else if(tabId == me.name+'_hotCitySelector_tab_area'){ //区县
	    		me.setValue(districtCode);
	    		me.selectedValue = districtCode;
	    		var areaName = record.districtShortName;
	    		var parentCityCode = record.parentDistrictCode;
	    		var cityName = me.hisDataMapObj[parentCityCode].districtShortName;
	    		var parentProvinceCode = me.hisDataMapObj[parentCityCode].parentDistrictCode;
	    		var provinceName = me.hisDataMapObj[parentProvinceCode].districtShortName;
	    		me.selectedText = provinceName+'-'+cityName+'-'+areaName;
	    		
	    		me.setRawValue(me.selectedText);
	    		me.collapse();
	    		me.getTrigger('clear').show();
	    	}
	    },
	    getDistrictInfo:function(params,tabId,urlMethod){
	    	var me = this;
	        var successFun = function (json) {
		   		 var districtObjList = json.districtVo.districtList;
		   		 var btnArr = [];
		   		 if(!Ext.isEmpty(districtObjList)){
		   			 Ext.each(districtObjList,function(record,index,records){
		   				var curBtn = me.createDistrictBtn(tabId,record);
		   				btnArr.push(curBtn);
		   			 },this);
		   		 }
		   	 
	   			 var picker = me.picker,curTab = null;
	   			 if(tabId == me.name+'_hotCitySelector_tab_hotCity'){
	   				 
	   				 //把省份备份
	   				 var provinceTab = picker.child('#'+me.name+'_hotCitySelector_tab_province');
	   				 var cityTab = picker.child('#'+me.name+'_hotCitySelector_tab_city');
	   				 var areaTab = picker.child('#'+me.name+'_hotCitySelector_tab_area');
	   				 
	   				 //当前为 热门城市
		   			 curTab = picker.child('#'+tabId);
		   			 picker.removeAll();
		   			 
		 	    	 //添加页签
		   			 picker.add({
	                  	id:tabId,
	                 	title:'热门城市',
	                    layout:{
	                    	type:'table',
	                    	columns:'6'
	                    },
	                    items:btnArr,
	                    width:me.picker.width
		   			 });
		   			 
		   			 picker.add(provinceTab);
		   			 picker.add(cityTab);
		   			 picker.add(areaTab);
		   			 
		   			 me.setTabTitleWidth(picker);
		 			
	   			 }else if(tabId == me.name+'_hotCitySelector_tab_province'){
	 	    		
	   				 var cityTab = picker.child('#'+me.name+'_hotCitySelector_tab_city');
	   				 var areaTab = picker.child('#'+me.name+'_hotCitySelector_tab_area');
	   				 
		   			 curTab = picker.child('#'+tabId);
		   			 picker.remove(curTab);
		   			 picker.remove(cityTab);
		   			 picker.remove(areaTab);
		   			 
		   			 picker.add({
	                  	id:tabId,
	                 	title:'省份',
	                    layout:{
	                    	type:'table',
	                    	columns:'6'
	                    },
	                    items:btnArr,
	                    width:me.picker.width
		   			 });
		   			 
		   			 picker.add({
		                  	id:me.name+'_hotCitySelector_tab_city',
		                 	title:'城市',
		                    layout:{
		                    	type:'table',
		                    	columns:'6'
		                    },
		                    items:[],
		                    width:me.picker.width
			   	     });		   			 
		   			 
		   			 picker.add({
		                  	id:me.name+'_hotCitySelector_tab_area',
		                 	title:'区县',
		                    layout:{
		                    	type:'table',
		                    	columns:'6'
		                    },
		                    items:[],
		                    width:me.picker.width
			   	     });	
		   			 
		   			 me.setTabTitleWidth(picker);
	   				 
		    	 } else if(tabId == me.name+'_hotCitySelector_tab_city'){
	   				 var areaTab = picker.child('#'+me.name+'_hotCitySelector_tab_area');
		   			 curTab = picker.child('#'+tabId);
		   			 picker.remove(curTab);
		   			 picker.remove(areaTab);
		   			 
		   			 picker.add({
	                  	id:tabId,
	                 	title:'城市',
	                    layout:{
	                    	type:'table',
	                    	columns:'6'
	                    },
	                    items:btnArr,
	                    width:me.picker.width
		   			 });
		   			 
		   			 picker.add({
		                  	id:me.name+'_hotCitySelector_tab_area',
		                 	title:'区县',
		                    layout:{
		                    	type:'table',
		                    	columns:'6'
		                    },
		                    items:[],
		                    width:me.picker.width
			   	     });	
		   			 
		   			 me.setTabTitleWidth(picker);
		   			 
		    	 } else if(tabId == me.name+'_hotCitySelector_tab_area'){
	   				 
		   			 curTab = picker.child('#'+tabId);
		   			 picker.remove(curTab);
		   			 
		   			 picker.add({
	                  	id:tabId,
	                 	title:'区县',
	                    layout:{
	                    	type:'table',
	                    	columns:'6'
	                    },
	                    items:btnArr,
	                    width:me.picker.width
		   			 });
		   			 
		   			 me.setTabTitleWidth(picker);
		    	 }
	   			 
   				curTab = picker.child('#'+tabId);
	   			curTab.tab.show();
	   			picker.setActiveTab(curTab);		   				
	   			 
				 var hotCityObj = document.getElementById(tabId+'-body');
				 if(!Ext.isEmpty(hotCityObj)){
					 var objArr = hotCityObj.getElementsByClassName('x-btn-inner x-btn-inner-default-small');
					 Ext.each(objArr,function(record,index,records){
						record.style.color='#777777';
					 });
				 }
	   	    };
	   	
		   	var failureFun = function (json) {
		   		console.log(json);
		   	};
		   	
		   	miser.requestJsonAjax('../base/districtAction!'+urlMethod+'.action', params, successFun, failureFun,true);
	    },
	    getCityStore:function(provinceCode){//用于获取 城市
	    	var me = this;
	    	var params = {	                
					'districtVo' :{
						'district' : {
							'parentDistrictCode' : provinceCode
						}
					},
					'limit':100000,
					'start':0
			};
	    	me.getDistrictInfo(params,me.name+'_hotCitySelector_tab_city','queryCity');
	    },
	    getAreaStore:function(cityCode){//用于获取 区县
	    	var me = this;
	    	var params = {	                
					'districtVo' :{
						'district' : {
							'parentDistrictCode' : cityCode
						}
					},
					'limit':100000,
					'start':0
			};
	    	me.getDistrictInfo(params,me.name+'_hotCitySelector_tab_area','queryArea');
	    }
	});
 
 /**
  * #########################################
  * #######    热门城市选择器 结束       #######
  * #########################################
  */