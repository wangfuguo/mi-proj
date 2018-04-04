function dateFormat(value){ 
    if(null != value){ 
        return Ext.Date.format(new Date(value),'Y-m-d H:i:s'); 
    }else{ 
        return null; 
    } 
} 

var uploadT={
		oufileName:null,
		excelWindow:function(url,fn){
			if(Ext.isEmpty(fn)){
				Ext.toast('请传入回调函数','温馨提示','t');
				return;
			}
			//没有url则默认为上传文件
			var flag=false;
			if(Ext.isEmpty(url)){
				flag=true;
				url='../common/FileUpLoadAction!upLoadFile.action';
			}
			var fp=new Ext.FormPanel({
				renderTo : Ext.getBody(),
				fileUpload : true,
				width : 523,
				frame : true,
				autoHeight : true,
				bodyStyle : 'padding: 10px 10px 0 10px;',
				labelWidth : 50,
				defaults : {
					anchor : '95%',
					allowBlank : false,
					msgTarget : 'side'
				},
				items : [ new Ext.form.FileUploadField({
					buttonText : '浏览...',
					name : 'serviceXls',
					regex: /^.*?\.(xlsx|xls|zip)$/,
					regexText:'只能上传 *.xlsx,*.xls,*.zip',
					emptyText : '请选择一个文件',
					width : 500,
					buttonCfg : {
						width : 40,
						iconCls : 'upload-icon'
					}
				}) ],
				buttons : [ {
					text : '上传',
					handler : function() {
						if (fp.getForm().isValid()) {
							fp.getForm().submit(
									{
										method : 'post',
										url : url,	//后台处理的action
										waitMsg : '操作处理中，请稍等...',
										waitTitle : '提示',
										success : function(fp, action) {
											if(flag){
												fn(action.result.outFileName,true);
											}else{
												fn(true);
											}
											 xwindow.destroy();
										},
										failure : function(fp, action) {
											if(flag){
												fn('服务器异常',false);
											}else{
												fn(false);
											}
											fn(action.result.outFileName,true);
											xwindow.destroy();
										}
									});
						}
					}
				}]
			});
			var xwindow=new Ext.Window({
			renderTo : Ext.getBody(),
			closeAction : "hide",
			plain : true,
			width : 540,
			title : "上传数据",
			modal : true,
			items : [fp]
		})
			xwindow.show();
		}
}

function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
    	params = {
        	'priceStandardSectionVo.priceStandardSectionEntity.sendPriceCity.code': queryForm.getForm().findField('sendPriceCityCode').getValue(),
            'priceStandardSectionVo.sendProvinceCode': queryForm.getForm().findField('sendProvinceCode').getValue(),
            'priceStandardSectionVo.sendCityCode': queryForm.getForm().findField('sendCityCode').getValue(),
            'priceStandardSectionVo.sendAreaCode': queryForm.getForm().findField('sendAreaCode').getValue(),
            
            'priceStandardSectionVo.priceStandardSectionEntity.arrivePriceCity.code': queryForm.getForm().findField('arrivePriceCityCode').getValue(),
            'priceStandardSectionVo.arriveProvinceCode': queryForm.getForm().findField('arriveProvinceCode').getValue(),
            'priceStandardSectionVo.arriveCityCode': queryForm.getForm().findField('arriveCityCode').getValue(),
            'priceStandardSectionVo.arriveAreaCode': queryForm.getForm().findField('arriveAreaCode').getValue(),
            
            'priceStandardSectionVo.priceStandardSectionEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
            'priceStandardSectionVo.effectiveTime': dateFormat(queryForm.getForm().findField('effectiveTime').getValue()),
            'priceStandardSectionVo.priceStandardSectionEntity.state': queryForm.getForm().findField('state').getValue()
        };
    }
	Ext.Ajax.setTimeout(900000);
	Ext.Ajax.request( {  
	       url : '../pricecard/priceStandardSection!export.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {
				  Ext.Ajax.setTimeout(30000);
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导出共"+responseArray.count+"条数据！";
	              miser.showInfoMsg(msg);
	              miser.requestExportAjax(responseArray.filePath);
	             },
	        failure : function() {
				Ext.Ajax.setTimeout(30000);
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
}

function imple(outFileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.setTimeout(3600000);
		Ext.Ajax.request( {  
	       url : '../pricecard/priceStandardSection!importExcel.action',  
	       method : 'post',  
	       timeout:3600000,
	       params : {  
	    	   'priceStandardSectionVo.filePath' : outFileName  
	          },  
	          success : function(response, options) { 
	        	  Ext.Ajax.setTimeout(30000);
	        	//隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导入"+responseArray.sumSize+"条数据，新增待生效数据"+responseArray.addSize+"条，覆盖原待生效数据"+responseArray.coverSize+"条," +
	              		"失败"+responseArray.errorSize+"条。";
	              
	              miser.showInfoMsg(msg);
	              if(responseArray.filePath!=''){
	            	  //新打开一个下载页面
	            	  miser.requestExportAjax(responseArray.filePath);
	              }
	              
	             },
	        failure : function() {
	        	Ext.Ajax.setTimeout(30000);
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '导入失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	}else{
		miser.showErrorMes('服务器异常');
	}
	
}


Ext.define('Miser.model.baseinfo.baseCommonModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'name',
		type : 'string'
	},{
		name : 'value',
		type : 'string'
	}]
});

//状态下拉框-start
Ext.define('Miser.commonSelector.priceStandardSectionStateStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.baseinfo.baseCommonModel',
    data : [{'name':'全部','value':'0'},{'name':'已失效','value':'1'},{'name':'生效中','value':'2'},{'name':'待生效','value':'3'},{'name':'已作废','value':'4'}]
});
Ext.define('Miser.commonSelector.priceStandardSectionStateSelector', {
	extend : 'Ext.form.ComboBox',
	alias : 'widget.pricestandardsectionstate_combselector',
	listWidth : this.width,// 下拉列表宽度，从外面传入
	multiSelect : false,// 从外面传入
	displayField : 'name',// 显示名称
	valueField : 'value',// 值
	constructor : function(config) {
		var this_ = this, 
		cfg = Ext.apply({}, config); 
		this_.store = Ext.create('Miser.commonSelector.priceStandardSectionStateStore');
		this_.callParent([cfg]);
	}
});
//状态下拉框-end

/**
 * 查询表单
 */
Ext.define('Miser.view.priceStandardSection.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title : pricecard.priceStandardSection.i18n('miser.common.queryCondition'),// '查询条件',
    height: 200,
    width : document.documentElement.clientWidth-10,
    collapsible: true,
    region: 'north',
    defaults: {
    	labelWidth: 0.4,
    	columnWidth: 0.6,
        margin: '8 10 5 10',
		labelAlign : 'right'
    },
    constructor: function(config) {
        var this_ = this,
        cfg = Ext.apply({},config);
        this_.items=[{
        	layout: 'hbox',
			defaults: {
				labelWidth: 70,
				labelAlign: "right"
			},
			items:[{
	            name: 'label',
	            html:'<font style="color:red;">出发</font>',
	            flex:0.25,
	            xtype: 'label'
	        },{
	        	name: '',
	            labelWidth: 5,
	            flex:2.6,
	            cityLabel: '市',
	            cityName: 'sendCityCode',
	            cityIsBlank:true,
	            //名称
	            provinceLabel: '省',
	            provinceName: 'sendProvinceCode',
	            provinceIsBlank:true,
	            //省名称
	            areaLabel: '县',
	            areaName: 'sendAreaCode',
	            areaIsBlank:true,
	            // 县名称
	            type: 'P-C-C',
	            xtype: 'myLlinkregincombselector'
	    	},
	    	{
    			
    			name: 'effectiveTime',
    			fieldLabel: '有效时间点',  //'生效时间',
    			xtype : 'datetimefield',
    			value:new Date(),
    			format : 'Y-m-d H:i:s'
			}]
        },{
        	layout: 'hbox',
			defaults: {
				labelWidth: 70,
				labelAlign: "right"
			},
			items:[{
	            name: 'label',
	            html:'<font style="color:red;">到达</font>',
	            flex:0.25,
	            xtype: 'label'
	        },{
						name: '',
					    labelWidth: 20,
					    cityLabel: '市',
					    flex:2.6,
					    cityName: 'arriveCityCode',
					    cityIsBlank:true,
					    //名称
					    provinceLabel: '省',
					    provinceName: 'arriveProvinceCode',
					    provinceIsBlank:true,
					    //省名称
					    areaLabel: '县',
					    areaName: 'arriveAreaCode',
					    areaIsBlank:true,
					    
					    // 县名称
					    type: 'P-C-C',
					    xtype: 'myLlinkregincombselector'
					},{
					    name: 'state',
					    fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.state'),// '当前状态',
					    xtype : 'dataDictionarySelector',anyRecords:all,
					    termsCode : 'PRICE_SATUS'
					}]
        	},{
            	layout: 'hbox',
    			defaults: {
    				labelWidth: 70,
    				labelAlign: "right"
    			},
    			items:[{
    	            name: 'label',
    	            html:'价格城市:',
    	            flex:0.25,
    	            xtype: 'label'
    	        },{
    	            name: 'sendPriceCityCode',
    	            flex:0.46,
    	            xtype: 'startPriceCityselector'
    	        },{
    	            name: 'label',
    	            labelWidth: 20,
    	            html:'—',
    	            xtype: 'label'
    	        },{
    			    name: 'arrivePriceCityCode',
    			    flex:0.46,
    			    xtype: 'arrivePriceCityselector'
    			},{
    	            name: 'label',
    	            flex:1.1,
    	            xtype: 'label'
    	        },{
		            name: 'transTypeCode',
		            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.transTypeName'),// '词条名称',
		            xtype : 'transtypecombselector'
		        }]
            	}
           
        ],
        this_.buttons = [{
            text: pricecard.priceStandardSection.i18n('miser.common.query'),
            handler: function() {
                if (this_.getForm().isValid()) {
                    this_.up().getPriceStandardSectionGrid().getPagingToolbar().moveFirst();;
                }
            }
        },
        {
            text: pricecard.priceStandardSection.i18n('miser.common.reset'),
            handler: function() {
                this_.getForm().reset();
            }
        }];
        this_.callParent([cfg]);
    }
});

/**
 * 易入户标准价格model
 */
Ext.define('Miser.model.PriceStandardSectionEntity',{
	extend: 'Ext.data.Model',
	fields: [{
		// id
		name: 'id',
		type: 'string'
	},{
		// 运输类型编号
		name: 'transTypeCode',
        type: 'string'
	},{
		// 运输类型
		name: 'transTypeName',
        type: 'string'
	},{
		// 出发价格城市
		name: 'sendPriceCityCode',
        mapping:'sendPriceCity.code',
        type: 'string'
	},{
		// 出发价格城市
		name: 'sendPriceCityName',
        mapping:'sendPriceCity.name',
        type: 'string'
	},{
		// 到达价格城市
		name: 'arrivePriceCityCode',
        mapping:'arrivePriceCity.code',
        type: 'string'
	},{
		// 到达价格城市
		name: 'arrivePriceCityName',
        mapping:'arrivePriceCity.name',
        type: 'string'
	},{
		// 一段首重
		name: 'firstWeight',
		type: 'float'
	},{
		// 一段首重单价
		name: 'firstWeightPrice',
		type: 'float'
	},{
		// 一段续重单价
		name: 'firstAddWeightPrice',
		type: 'float'
	},{
		// 二段首重
		name: 'secondWeight',
		type: 'float'
	},{
		// 二段首重单价
		name: 'secondWeightPrice',
		type: 'float'
	},{
		// 二段续重单价
		name: 'secondAddWeightPrice',
		type: 'float'
	},{
		// 三段首重
		name: 'thirdWeight',
		type: 'float'
	},{
		// 三段首重单价
		name: 'thirdWeightPrice',
		type: 'float'
	},{
		// 三段续重单价
		name: 'thirdAddWeightPrice',
		type: 'float'
	},{
		// 生效时间
		name: 'effectiveTime',
    	type : 'date', 
    	dateFormat:'time'
	},{
		// 失效时间
		name: 'invalidTime',
    	type : 'date', 
    	dateFormat:'time'
	},{
		// 当前状态
		name: 'state',
    	type: 'string'
	},{
		// 是否有效
		name: 'active',
    	type: 'string'
	},{
		// 最后修改人
		name: 'modifyUser',
    	type: 'string'
	},{
		// 最后修改时间
		name: 'modifyDate',
    	type : 'date', 
    	dateFormat:'time'
	},{
		// 最后修改时间
		name: 'remark',
    	type : 'string'
	}]
});

/**
 * 易入户标准价格store
 */
Ext.define('Miser.store.priceStandardSectionStore',{
	extend: 'Ext.data.Store',
	model: 'Miser.model.PriceStandardSectionEntity',
	pageSize: 20,
	proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../pricecard/priceStandardSection!queryPageByCondition.action',
        reader: {
            type: 'json',
            rootProperty: 'priceStandardSectionVo.priceStandardSectionList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'priceStandardSectionVo.priceStandardSectionEntity.sendPriceCity.code': queryForm.getForm().findField('sendPriceCityCode').getValue(),
                    'priceStandardSectionVo.sendProvinceCode': queryForm.getForm().findField('sendProvinceCode').getValue(),
                    'priceStandardSectionVo.sendCityCode': queryForm.getForm().findField('sendCityCode').getValue(),
                    'priceStandardSectionVo.sendAreaCode': queryForm.getForm().findField('sendAreaCode').getValue(),
                    
                    'priceStandardSectionVo.priceStandardSectionEntity.arrivePriceCity.code': queryForm.getForm().findField('arrivePriceCityCode').getValue(),
                    'priceStandardSectionVo.arriveProvinceCode': queryForm.getForm().findField('arriveProvinceCode').getValue(),
                    'priceStandardSectionVo.arriveCityCode': queryForm.getForm().findField('arriveCityCode').getValue(),
                    'priceStandardSectionVo.arriveAreaCode': queryForm.getForm().findField('arriveAreaCode').getValue(),
                    
                    'priceStandardSectionVo.priceStandardSectionEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                    'priceStandardSectionVo.effectiveTime': dateFormat(queryForm.getForm().findField('effectiveTime').getValue()),
                    'priceStandardSectionVo.priceStandardSectionEntity.state': queryForm.getForm().findField('state').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

Ext.define('Miser.view.priceStandardSection.Grid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    height: miser.getBrowserHeight() - 120,
    stripeRows: true,
    // 交替行效果
    selType: "rowmodel",
    // 选择类型设置为：行选择
    emptyText: '查询结果为空',
    // 查询结果为空
    columnLines: true,
    viewConfig: {
        enableTextSelection: true
    },
    priceStandardSectionAddWindow: null,
    getPriceStandardSectionAddWindow: function() {
        if (this.priceStandardSectionAddWindow == null) {
            this.priceStandardSectionAddWindow = Ext.create('Miser.view.pricecard.priceStandardSectionAddWindow');
            this.priceStandardSectionAddWindow.parent = this; // 父元素
        }
        return this.priceStandardSectionAddWindow;
    },
    priceStandardSectionUpdateWindow: null,
    getPriceStandardSectionUpdateWindow: function() {
        if (this.priceStandardSectionUpdateWindow == null) {
            this.priceStandardSectionUpdateWindow = Ext.create('Miser.view.pricecard.priceStandardSectionUpdateWindow');
            this.priceStandardSectionUpdateWindow.parent = this; //父元素
        }
        return this.priceStandardSectionUpdateWindow;
    },
    updatePriceStandardSection : function(){
		var this_ = this;
		var selections = this_.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var id = selections[0].get('id');
		var state=selections[0].get('state');
		if(state!='EFFECTIVE'&&state!='WAIT'){
			miser.showWoringMessage('只有待生效和生效中的数据才能进行该项操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var params = {
				'priceStandardSectionVo' :{
					'priceStandardSectionEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = this_.getPriceStandardSectionUpdateWindow(); //获得修改窗口
			updateWindow.priceStandardSectionEntity = json.priceStandardSectionVo.priceStandardSectionEntity;
			updateWindow.show(); //显示修改窗口
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		// 查询请求
		miser.requestJsonAjax('../pricecard/priceStandardSection!queryById.action', params, successFun, failureFun);
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
    deletePriceStandardSection: function() {
        var this_ = this;
        var selections = this_.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objects = [];
        for(var i=0;i<selections.length;i++){
        	var param = {};
        	var state=selections[i].get('state');
    		if(state!='EFFECTIVE'&&state!='WAIT'){
    			miser.showErrorMes('只有待生效和生效中的数据才能进行该项操作');
    			return;
    		}
    		if(stateType==''){
    			stateType=state;
    		}else if(stateType!=state) {
    			miser.showErrorMes('请选择同种状态的数据进行该项操作');
    			return;
    		}
    		param.id = selections[i].get('id');
    		objects.push(param);
        }
       // miser.showQuestionMes(pricecard.priceStandard.i18n('miser.pricecard.isdelete'),//是否删除
        var msg="您确认作废选中的"+objects.length+"条信息吗？确认后状态会变为";
        if(stateType==3){//待生效数据
        	msg+="已作废";
        }else{
        	msg+="已失效";
        }
        msg+="并会保留您的操作记录.";
        miser.showQuestionMes(msg,//是否删除
        function(e) {
            if (e == 'yes') { // 询问是否删除，是则发送请求
                var params = {
                		'priceStandardSectionVo': {
                			'priceStandardSectionList' : objects
                        }
                };
                var successFun = function(json) {
                    var message = json.message;
                    miser.showInfoMsg(message);
                    this_.getStore().load();
                };
                var failureFun = function(json) {
                    if (Ext.isEmpty(json)) {
                        miser.showErrorMes(pricecard.priceStandardSection.i18n('miser.common.requestTimeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../pricecard/priceStandardSection!delete.action', params, successFun, failureFun);
            }
        });
    },
    constructor: function(config) {
        var this_ = this,
        cfg = Ext.apply({},
        config);
        this_.columns = [{
            text: pricecard.priceStandardSection.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
            dataIndex: 'id',
            width: 100,
            hidden:true
        },{
            dataIndex: 'transTypeCode',
            hidden:true
        },{
            dataIndex: 'transTypeName',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.transTypeName')  // '运输类型'
        },{
        	dataIndex: 'sendPriceCityCode',
        	hidden:true
        },{
        	dataIndex: 'sendPriceCityName',
            width: 120,
            text: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.sendPriceCity.name')  //'出发价格城市'
        },{
        	dataIndex: 'arrivePriceCityCode',
        	hidden:true
        },{
        	dataIndex: 'arrivePriceCityName',
            width: 120,
            text: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.arrivePriceCity.name')  //'出发价格城市'
        },{
            dataIndex: 'firstWeight',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstWeight')  // '一段首重'
        },{
            dataIndex: 'firstWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstWeightPrice')  // '一段首重单价'
        },{
            dataIndex: 'firstAddWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstAddWeightPrice')  // '一段续重单价'
        },{
            dataIndex: 'secondWeight',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondWeight')  // '二段首重'
        },{
            dataIndex: 'secondWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondWeightPrice')  // '二段首重单价'
        },{
            dataIndex: 'secondAddWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondAddWeightPrice')  // '二段续重单价'
        },{
            dataIndex: 'thirdWeight',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdWeight')  // '三段首重'
        },{
            dataIndex: 'thirdWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdWeightPrice')  // '三段首重单价'
        },{
            dataIndex: 'thirdAddWeightPrice',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdAddWeightPrice')  // '三段续重单价'
        },{
            dataIndex: 'effectiveTime',
            width: 150,
            renderer: function(value) {
                return dateRender(value,'Y-m-d H:i:s');
            },
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.effectiveTime')  // '生效时间'
        },{
            dataIndex: 'invalidTime',
            width: 150,
            renderer: function(value) {
                return dateRender(value,'Y-m-d H:i:s');
            },
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.invalidTime')  // '失效时间'
        },{
            dataIndex: 'state',
            width: 100,
            renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore('PRICE_SATUS'),
								value);
			},
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.state')  // '状态'
        },{
            dataIndex: 'active',
            width: 100,
            renderer: function(value) {
            	return booleanTypeRender(value);
            },
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.active')  // '是否有效'
        },{
        	text: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.remark'),  //'备注',
        	dataIndex: 'remark',
        	width: 100
        },{
            dataIndex: 'createUser',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.createUser')  // '创建人'
        },{
            dataIndex: 'createDate',
            width: 100,
            renderer: function(value) {
                return dateRender(value);
            },
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.createDate')  // '创建时间'
        },{
            dataIndex: 'modifyUser',
            width: 100,
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.modifyUser')  // '修改用户'
        },{
            dataIndex: 'modifyDate',
            width: 100,
            renderer: function(value) {
                return dateRender(value);
            },
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.modifyDate')  // '修改时间'
        }];
        this_.store = Ext.create('Miser.store.priceStandardSectionStore', {
            autoLoad: false
        });
        this_.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        this_.tbar = [{
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.addentry'),// '新增词条',
            xtype: 'addbutton',
            // 新增
            width: 80,
            //hidden:!pricecard.priceStandard.isPermission('dataDictionary/dataDictionaryAddButton'),
            handler: function() {
                this_.getPriceStandardSectionAddWindow().show();
            }
        },
        '-', {
            text:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.updateentry'),// '修改',
            xtype: 'updatebutton',
			width: 80,
            handler: function() {
                this_.updatePriceStandardSection();
            }
        },
        '-', {
        	id : 'miser_biz_pricecard_priceStandard_del_id',
            text:pricecard.priceStandardSection.i18n('miser.common.delete'),// '删除',
            xtype: 'deletebutton',
            disabled : true,
            // 作废
            width: 80,
            handler: function() {
                this_.deletePriceStandardSection();
            }
        },{
			xtype: 'uploadbutton',
            text: '导入',
            width: 80,
            handler:function(){
            	uploadT.excelWindow(null,imple);
            }
		},{
            id: 'price_pickUp_fee_downbutton_id',
            alias: 'widget.downloadbutton',
        	glyph : 0xf019,
            disabled:false,
        	cls: 'download-btn-item',
            text: pricecard.priceStandardSection.i18n('miser.base.importTemplateDownLoad'),
            width: 130,
            handler: function() {
            	miser.requestExportAjax('/excelTemplate/priceStandardSectionTemplate.xlsx');
            }
        },{
			xtype: 'downloadbutton',
            text: '导出',
            width: 80,
            handler:function(){
            	down();
            }
		}];
        this_.bbar = this_.getPagingToolbar();
        this_.selModel = Ext.create('Ext.selection.CheckboxModel', {
	        listeners: {
	            selectionchange: function(sm, selections) {
	                Ext.getCmp('miser_biz_pricecard_priceStandard_del_id').setDisabled(selections.length === 0);
	            }
	        }
	    });
        // Ext.setGlyphFontFamily('FontAwesome');
        this_.callParent([cfg]);
    }
});

/**
 * 校验单价：任何一段的重量不为0时，首重单价、续重单价也不能为0
 * @param weight 首重
 * @param price 首重单价或续重单价
 * @param msg 校验信息
 * @returns {Boolean 校验通过}{String 校验信息}
 */
function checkPrice(weight,price,msg){
	if(!isZero(weight)){
		if(isZero(price)){
			return msg;
		}
	}
	return true;
}

/**
 * 校验首重：首重（3段）＞首重（2段）＞首重（1段）
 * @param first 1段首重
 * @param second 2段首重
 * @param third 3段首重
 */
function checkWeight(first,second,third){
	var flag;
	if(isZero(third) && isZero(second)){
		flag = true;
	}else if(!isZero(third) && !isZero(second)){
		flag = third > second && second > first;
	}else{
		if(isZero(third)){
			flag = second > first;
		}
		if(isZero(second)){
			flag = third > first;
		}
	}
	if(!flag){
		flag = '首重（3段）＞首重（2段）＞首重（1段）';
	}
	return flag;
}

/**
 * 校验：是否为0
 * @param value
 * @returns {Boolean true 0}
 */
function isZero(value){
	return Ext.isEmpty(value) || value <= 0;
}

/**
 * 增加表单
 */
Ext.define('Miser.view.pricecard.priceStandardSectionAddForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 835,
    fieldDefaults: {
        labelWidth: 100,
        margin: '8 10 5 10'
    },
    layout: {
        type: 'table',
        columns: 4
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var this_ = this,
        cfg = Ext.apply({},config);
        
        this_.items = [{
            dataIndex: 'id',
            width: 100,
            hidden:true
        },{
            dataIndex: 'active',
            width: 100, 
            hidden:true
        },{
        	xtype:'transtypecombselector',
            name: 'transTypeCode',
            allowBlank:false,
            showAll:false,
            width: 250,
            fieldLabel:pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.transTypeName'),  // '运输类型'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype:'startPriceCityselector',
            name: 'sendPriceCityCode',
            allowBlank:false,
            width: 250,
            fieldLabel: '价格城市',  //'出发价格城市'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
            name: 'label',
            html:'————————',
            width: 150,
            xtype: 'label'
        },{
        	xtype:'arrivePriceCityselector',
            name: 'arrivePriceCityCode',
            allowBlank:false,
            margin: '8 11 5',
            width: 144,
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype :"numberfield",
        	id : 'firstWeight_form',
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0.01,
			validator : function(value) {
            	var second = Ext.getCmp('secondWeight_form').getValue();
            	var third = Ext.getCmp('thirdWeight_form').getValue();
            	return checkWeight(value, second, third);
            },  
            name: 'firstWeight',
            negativeText : '大于0',
            allowBlank:false,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstWeight'),  //'重量单价'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0.01,
            name: 'firstWeightPrice',
            negativeText : '大于0',
            allowBlank:false,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstWeightPrice'),  //'重量单价'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0.01,
            name: 'firstAddWeightPrice',
            negativeText : '大于0',
            allowBlank:false,
            width: 250,
            colspan: 2,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.firstAddWeightPrice'),  //'重量单价'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype :"numberfield",
        	id : 'secondWeight_form',
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var first = Ext.getCmp('firstWeight_form').getValue();
            	var third = Ext.getCmp('thirdWeight_form').getValue();
            	return checkWeight(first, value, third);
            }, 
            name: 'secondWeight',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondWeight')
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var weight = Ext.getCmp('secondWeight_form').getValue();
            	var msg = '如果首重不为0 ，续重单价必填且不为0';
            	return checkPrice(weight,value,msg);
            },  
            name: 'secondWeightPrice',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondWeightPrice')
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var weight = Ext.getCmp('secondWeight_form').getValue();
            	var msg = '如果首重不为0 ，续重单价必填且不为0';
            	return checkPrice(weight,value,msg);
            },  
            name: 'secondAddWeightPrice',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            colspan: 2,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.secondAddWeightPrice')
        },{
        	xtype :"numberfield",
        	id : 'thirdWeight_form',
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var first = Ext.getCmp('firstWeight_form').getValue();
            	var second = Ext.getCmp('secondWeight_form').getValue();
            	return checkWeight(first, second, value);
            }, 
            name: 'thirdWeight',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdWeight')
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var weight = Ext.getCmp('thirdWeight_form').getValue();
            	var msg = '如果首重不为0 ，首重单价必填且不为0';
            	return checkPrice(weight,value,msg);
            },  
            name: 'thirdWeightPrice',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdWeightPrice')
        },{
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
			validator : function(value) {
            	var weight = Ext.getCmp('thirdWeight_form').getValue();
            	var msg = '如果首重不为0 ，续重单价必填且不为0';
            	return checkPrice(weight,value,msg);
            },  
            name: 'thirdAddWeightPrice',
            negativeText : '最小值为0',
            allowBlank:true,
            width: 250,
            colspan: 2,
            fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.thirdAddWeightPrice')
        },{
			name: 'effectiveTime',
			width: 275,
			colspan: 4,
			fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.effectiveTime'),  //'生效时间',
			xtype : 'datefield',
			//minValue:(new Date(new Date().getTime()+6*60*1000)),
			value:new Date(new Date().getTime()+6*60*1000),
			allowBlank:false,
			format : 'Y-m-d H:i:s',
			beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	maxLength: 200,
        	colspan: 4,
        	fieldLabel: pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.remark'),  //'备注',
        	name: 'remark',
        	xtype: 'textareafield',
            grow: true,
            width: 520,
            anchor: '100%'
        }
        ];
        this_.callParent([cfg]);
    }
});

/**
 * 修改标准价格窗口
 */
Ext.define('Miser.view.pricecard.priceStandardSectionUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '修改',//修改
	closable : true,
	parent : null, // 父元素
	modal : true,
	resizable : false, // 可以调整窗口的大小
	closeAction : 'hide', // 点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	priceStandardSectionEntity: null,
	listeners : {
		beforehide : function(this_) { // 隐藏WINDOW的时候清除数据
			this_.getPriceStandardSectionAddForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(this_) { // 显示WINDOW的时候清除数据
			var fielsds = this_.getPriceStandardSectionAddForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			this_.getPriceStandardSectionAddForm().getForm().loadRecord(new Miser.model.PriceStandardSectionEntity(this_.priceStandardSectionEntity));
						
			//这块是处理下拉框赋值的问题
			var codeCombo = this_.getPriceStandardSectionAddForm().getForm().findField('transTypeCode');
			var newcodeComboRecord=Ext.data.Record({
				code: this_.priceStandardSectionEntity.transTypeCode,
			    name: this_.priceStandardSectionEntity.transTypeName
				}
			);
			codeCombo.getStore().add(newcodeComboRecord);
			codeCombo.setValue(this_.priceStandardSectionEntity.transTypeCode);
			codeCombo.disable();
			
			//到达价格城市 这块是处理下拉框赋值的问题
			var arrivePriceCityCombo = this_.getPriceStandardSectionAddForm().getForm().findField('arrivePriceCityCode');
			var arrivePriceCityComboRecord=Ext.data.Record({
				code: this_.priceStandardSectionEntity.arrivePriceCity.code,
				name: this_.priceStandardSectionEntity.arrivePriceCity.name
				}
			);
			arrivePriceCityCombo.getStore().add(arrivePriceCityComboRecord);
			arrivePriceCityCombo.setValue(this_.priceStandardSectionEntity.arrivePriceCity.code);
			arrivePriceCityCombo.disable();
			//出发价格城市 这块是处理下拉框赋值的问题
			var sendPriceCityCombo = this_.getPriceStandardSectionAddForm().getForm().findField('sendPriceCityCode');
			var sendPriceCityComboRecord=Ext.data.Record({
				code: this_.priceStandardSectionEntity.sendPriceCity.code,
				name: this_.priceStandardSectionEntity.sendPriceCity.name
				}
			);
			sendPriceCityCombo.getStore().add(sendPriceCityComboRecord);
			sendPriceCityCombo.setValue(this_.priceStandardSectionEntity.sendPriceCity.code);
			sendPriceCityCombo.disable();
			
		}
	},
	priceStandardSectionAddForm: null,
    getPriceStandardSectionAddForm: function() {
        if (Ext.isEmpty(this.priceStandardSectionAddForm)) {
            this.priceStandardSectionAddForm = Ext.create('Miser.view.pricecard.priceStandardSectionAddForm');
        }
        return this.priceStandardSectionAddForm;
    },
	submitDriver:function(){
		var this_ = this;
		if (this_.getPriceStandardSectionAddForm().getForm().isValid()) { //校验form是否通过校验
			var effectiveTime = this_.getPriceStandardSectionAddForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
        	Ext.Msg.wait('处理中，请稍后...', '提示');
			var priceStandardSectionEntity = new Miser.model.PriceStandardSectionEntity;
			var id =this_.getPriceStandardSectionAddForm().getForm().getRecord().data.id;
			this_.getPriceStandardSectionAddForm().getForm().findField('transTypeCode').enable();
			this_.getPriceStandardSectionAddForm().getForm().findField('arrivePriceCityCode').enable();
			this_.getPriceStandardSectionAddForm().getForm().findField('sendPriceCityCode').enable();
			this_.getPriceStandardSectionAddForm().getForm().updateRecord(priceStandardSectionEntity); //将FORM中数据设置到MODEL里面
			
			var formData=priceStandardSectionEntity.data;
            var sendPriceCity = {}; 
            var arrivePriceCity = {}; 
            sendPriceCity.code=formData.sendPriceCityCode;
            arrivePriceCity.code=formData.arrivePriceCityCode;
            formData.sendPriceCity=sendPriceCity;
            formData.arrivePriceCity=arrivePriceCity;
            var transTypeName= this_.getPriceStandardSectionAddForm().getForm().findField('transTypeCode').rawValue;
            if(transTypeName==null)transTypeName='';
            formData.transTypeName=transTypeName;
            formData.id='';
            var params = {
                    'priceStandardSectionVo': {
                        'priceStandardSectionEntity': formData
                    }
                }
            var failureFun = function (json) {
				Ext.Msg.hide();
				if (Ext.isEmpty(json)) {
					miser.showErrorMes('连接超时'); //请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message); //提示失败原因
				}
			};
			var successFun = function (json) {
				var entitys = json.priceStandardSectionVo.priceStandardSectionList;
				var successFun_ = function(json){
                	Ext.Msg.hide();
                	miser.showInfoMsg(json.message); // 提示新增成功
                	this_.close();
                    this_.parent.getStore().load(); // 成功之后重新查询刷新结果集
                }
				if(!Ext.isEmpty(entitys)){
                	entitys.unshift(formData);// 添加到开头
                	params = {
            			'priceStandardSectionVo': {
            				'priceStandardSectionList': entitys
                        }
                	}
                	miser.showQuestionMes(pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.isConfirm'),//是否删除
                        function(e) {
                        if (e == 'yes') { // 询问是否删除，是则发送请求
                        	Ext.Msg.wait('处理中，请稍后...', '提示');
                        	miser.requestJsonAjax('../pricecard/priceStandardSection!update.action', params, successFun_, failureFun); //发送AJAX请求
                        }
                 	});
                }
			};
			// 验证请求
            miser.requestJsonAjax('../pricecard/priceStandardSection!checkRecord.action', params, successFun, failureFun); // 发送AJAX请求
		}
	},
	constructor : function(config) {
		var this_ = this, cfg = Ext.apply({}, config);
		this_.fbar = [{
			text :pricecard.priceStandardSection.i18n('miser.common.save'), //保存
			margin : '0 0 0 305',
			handler : function() {
				 this_.submitDriver("0");
			}
		}, {
			text :pricecard.priceStandardSection.i18n('miser.common.reset'), //重置
			handler : function() {
				this_.close();
				this_.parent.updatePriceStandardSection();
			}
		},{
			text :pricecard.priceStandardSection.i18n('miser.common.cancel'), //取消
			handler : function() {
				this_.close();
			}
		} ];
		this_.items = [ this_.getPriceStandardSectionAddForm() ];
		this_.callParent([ cfg ]);
	}
});

//增加窗体
Ext.define('Miser.view.pricecard.priceStandardSectionAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增',// '新增词条',
    closable: true,
    left: -37,
    parent: null,
    // 父元素
    modal: true,
    resizable: false,
    // 可以调整窗口的大小
    closeAction: 'hide',
    // 点击关闭是隐藏窗口
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
    	// 隐藏WINDOW的时候清除数据
        beforehide: function(this_) { 
        	this_.getPriceStandardSectionAddForm().getForm().reset(); // 表格重置
        },
        // 显示WINDOW的时候清除数据
        beforeshow: function(this_) { 
            var fielsds = this_.getPriceStandardSectionAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            var effectiveTime = this_.getPriceStandardSectionAddForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
        }
    },
    // 新增表单
    priceStandardSectionAddForm: null,
    // 获取新增表单
    getPriceStandardSectionAddForm: function() {
        if (Ext.isEmpty(this.priceStandardSectionAddForm)) {
        	this.priceStandardSectionAddForm = Ext.create('Miser.view.pricecard.priceStandardSectionAddForm');
        }
        return this.priceStandardSectionAddForm;
    },
    // 提交新增表单
    submitPriceStandardSectionAddForm: function() {
        var this_ = this;
        if (this_.getPriceStandardSectionAddForm().getForm().isValid()) { // 表单校验 
        	// 验证生效时间
        	var effectiveTime = this_.getPriceStandardSectionAddForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
        	Ext.Msg.wait('处理中，请稍后...', '提示');
            var priceStandardSectionEntity = new Miser.model.PriceStandardSectionEntity;
            // 将FORM中数据设置到MODEL里面
            this_.getPriceStandardSectionAddForm().getForm().updateRecord(priceStandardSectionEntity);
            // 封装要提交的参数
            var formData=priceStandardSectionEntity.data;
            var sendPriceCity = {}; 
            var arrivePriceCity = {}; 
            sendPriceCity.code=formData.sendPriceCityCode;
            arrivePriceCity.code=formData.arrivePriceCityCode;
            formData.sendPriceCity=sendPriceCity;
            formData.arrivePriceCity=arrivePriceCity;
            // 获取运输类型名称
            var transTypeName= this_.getPriceStandardSectionAddForm().getForm().findField('transTypeCode').rawValue;
            if(transTypeName==null)transTypeName='';
            formData.transTypeName=transTypeName;
            formData.id='';
            var params = {
                'priceStandardSectionVo': {
                    'priceStandardSectionEntity': formData
                }
            }
            // 失败回调
            var failureFun = function(json) {
            	Ext.Msg.hide();
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes(pricecard.priceStandardSection.i18n('miser.common.requestTimeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            // 成功回调
            var successFun = function(json) {
                var entitys = json.priceStandardSectionVo.priceStandardSectionList;
                var successFun_ = function(json){
                	Ext.Msg.hide();
                	miser.showInfoMsg(json.message); // 提示新增成功
                	this_.close();
                    this_.parent.getStore().load(); // 成功之后重新查询刷新结果集
                }
                if(!Ext.isEmpty(entitys)){
                	entitys.unshift(formData);// 添加到开头
                	params = {
            			'priceStandardSectionVo': {
            				'priceStandardSectionList': entitys
                        }
                	}
                	// 提示有相同记录
                	miser.showQuestionMes(pricecard.priceStandardSection.i18n('pricecard.priceStandardSection.isConfirm'),//是否删除
                        function(e) {
                        if (e == 'yes') { // 询问是否继续添加
                        	Ext.Msg.wait('处理中，请稍后...', '提示');
                        	// 新增请求
                        	miser.requestJsonAjax('../pricecard/priceStandardSection!add.action', params, successFun_, failureFun); // 发送AJAX请求
                        }
                    });
                }else{
                	// 新增请求
                	miser.requestJsonAjax('../pricecard/priceStandardSection!add.action', params, successFun_, failureFun); // 发送AJAX请求
                }
            };
            // 验证请求
            miser.requestJsonAjax('../pricecard/priceStandardSection!checkRecord.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var this_ = this,
        cfg = Ext.apply({},
        config);
        this_.fbar = [{
            text: pricecard.priceStandardSection.i18n('miser.common.save'),//'保存',
            // 保存
            handler: function() {
                this_.submitPriceStandardSectionAddForm();
            }
        },
        {
            text:pricecard.priceStandardSection.i18n('miser.common.reset'),// '重置',
            // 重置
            handler: function() {
                this_.getPriceStandardSectionAddForm().reset();
            }
        },
        {
            text: pricecard.priceStandardSection.i18n('miser.common.cancel'),// '取消',
            // 取消
            handler: function() {
                this_.close();
            }
        }];
        this_.items = [this_.getPriceStandardSectionAddForm()];
        this_.callParent([cfg]);
    }
});

Ext.onReady(function() {
    Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.view.priceStandardSection.QueryForm'); //查询FORM
    var priceStandardSectionGrid = Ext.create('Miser.view.priceStandardSection.Grid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getPriceStandardSectionGrid: function() {
            return priceStandardSectionGrid;
        },
        items: [queryForm, priceStandardSectionGrid]
    });
});