//文件上传
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
//下载
function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
        	params = {
        	'priceStandardVo.priceStandardEntity.sendPriceCity.code': queryForm.getForm().findField('sendPriceCityCode').getValue(),
            'priceStandardVo.sendProvinceCode': queryForm.getForm().findField('sendProvinceCode').getValue(),
            'priceStandardVo.sendCityCode': queryForm.getForm().findField('sendCityCode').getValue(),
            'priceStandardVo.sendAreaCode': queryForm.getForm().findField('sendAreaCode').getValue(),
            
            'priceStandardVo.priceStandardEntity.arrivePriceCity.code': queryForm.getForm().findField('arrivePriceCityCode').getValue(),
            'priceStandardVo.arriveProvinceCode': queryForm.getForm().findField('arriveProvinceCode').getValue(),
            'priceStandardVo.arriveCityCode': queryForm.getForm().findField('arriveCityCode').getValue(),
            'priceStandardVo.arriveAreaCode': queryForm.getForm().findField('arriveAreaCode').getValue(),
            
            'priceStandardVo.priceStandardEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
            'priceStandardVo.effectiveTime': dateFormat(queryForm.getForm().findField('effectiveTime').getValue()),
            'priceStandardVo.priceStandardEntity.state': queryForm.getForm().findField('state').getValue()
        };
    }
	Ext.Ajax.setTimeout(900000);
	Ext.Ajax.request( {  
	       url : '../pricecard/priceStandard!excelExport.action',  
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
		Ext.Ajax.request( {  
	       url : '../pricecard/priceStandard!excelImpl.action',  
	       method : 'post',  
	       timeout:3600000,
	       params : {  
	    	   'priceStandardVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  
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
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '修改失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	}else{
		miser.showErrorMes('服务器异常');
	}
	
}
//将时间转化为 2011-08-20 00:00:00 格式 
//解决Ext4的formPanel通过grid的store查询问题
function dateFormat(value){ 
    if(null != value){ 
        return Ext.Date.format(new Date(value),'Y-m-d H:i:s'); 
    }else{ 
        return null; 
    } 
} 

//状态
function stateTypeRender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if('1' == value){
		value= '已失效';
	}else if('2' == value){
		value= '生效中';
	}else if('3' == value){
		value= '待生效';
	}else if('4' == value){
		value= '已作废';
	}
	return value;
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
Ext.define('Miser.commonSelector.priceStandardStateStore', {
	extend : 'Ext.data.Store',
	model: 'Miser.model.baseinfo.baseCommonModel',
    data : [{'name':'全部','value':'0'},{'name':'已失效','value':'1'},{'name':'生效中','value':'2'},{'name':'待生效','value':'3'},{'name':'已作废','value':'4'}]
});
Ext.define('Miser.commonSelector.priceStandardStateSelector', {
	extend : 'Ext.form.ComboBox',
	alias : 'widget.pricestandardstate_combselector',
	listWidth : this.width,// 下拉列表宽度，从外面传入
	multiSelect : false,// 从外面传入
	displayField : 'name',// 显示名称
	valueField : 'value',// 值
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config); 
		me.store = Ext.create('Miser.commonSelector.priceStandardStateStore');
		me.callParent([cfg]);
	}
});
//状态下拉框-end

/**
 * 标准价格列表model
 */
Ext.define('Miser.model.PriceStandardEntity', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 运输类型编号
        type: 'string'
    },{
        name: 'transTypeCode',
        // 运输类型编号
        type: 'string'
    },{
        name: 'transTypeName',
        // 运输类型
        type: 'string'
    },{
        name: 'sendPriceCity_name',
        mapping:'sendPriceCity.name',
        // 出发价格城市
        type: 'string'
    },
    {
        name: 'arrivePriceCity_name',
        mapping:'arrivePriceCity.name',
        // 到达价格城市
        type: 'string'
    },{
        name: 'sendPriceCity_code',
        mapping:'sendPriceCity.code',
        // 出发价格城市
        type: 'string'
    },
    {
        name: 'arrivePriceCity_code',
        mapping:'arrivePriceCity.code',
        // 到达价格城市
        type: 'string'
    },
    {
        name: 'weightPrice',
        // 重量单价
        type: 'float'
    },
    {
        name: 'volumePrice',
        // 体积单价
        type: 'float'
    },
    {
        name: 'addFee',
        // 附加费
        type: 'float'
    },
    {
        name: 'lowestFee',
        // 最低收费
        type: 'float'
    },
    {
        name: 'fuelFeeSection',
        // 燃油分段
        type: 'string'
    },
    {
    	name: 'fuelFeeSectionName',
    	// 燃油分段
    	type: 'string'
    },
    {
    	name: 'effectiveTime',
    	// 生效时间
    	type : 'date', 
    	dateFormat:'time'
    },
    {
    	name: 'invalidTime',
    	// 失效时间
    	type : 'date', 
    	dateFormat:'time'
    },
    {
    	name: 'state',
    	// 当前状态
    	type: 'string'
    },
    {
    	name: 'active',
    	// 是否有效
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
 * 标准价格列表store
 */
Ext.define('Miser.store.PriceStandardStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.PriceStandardEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../pricecard/priceStandard!queryListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'priceStandardVo.priceStandardList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'priceStandardVo.priceStandardEntity.sendPriceCity.code': queryForm.getForm().findField('sendPriceCityCode').getValue(),
                    'priceStandardVo.sendProvinceCode': queryForm.getForm().findField('sendProvinceCode').getValue(),
                    'priceStandardVo.sendCityCode': queryForm.getForm().findField('sendCityCode').getValue(),
                    'priceStandardVo.sendAreaCode': queryForm.getForm().findField('sendAreaCode').getValue(),
                    
                    'priceStandardVo.priceStandardEntity.arrivePriceCity.code': queryForm.getForm().findField('arrivePriceCityCode').getValue(),
                    'priceStandardVo.arriveProvinceCode': queryForm.getForm().findField('arriveProvinceCode').getValue(),
                    'priceStandardVo.arriveCityCode': queryForm.getForm().findField('arriveCityCode').getValue(),
                    'priceStandardVo.arriveAreaCode': queryForm.getForm().findField('arriveAreaCode').getValue(),
                    
                    'priceStandardVo.priceStandardEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                    'priceStandardVo.effectiveTime': dateFormat(queryForm.getForm().findField('effectiveTime').getValue()),
                    'priceStandardVo.priceStandardEntity.state': queryForm.getForm().findField('state').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
         
    	
    }
    
});

/**
 * 查询表单
 */
Ext.define('Miser.view.priceStandard.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title : pricecard.priceStandard.i18n('miser.common.queryCondition'),// '查询条件',
    height: 200,
    width : document.documentElement.clientWidth-10,
    collapsible: true,
    //layout: 'column',
    region: 'north',
    defaults: {
    	//rowspan : 2,
        //colspan: 1,
    	labelWidth: 0.4,
    	columnWidth: 0.6,
        margin: '8 10 5 10',
		labelAlign : 'right'
    },
    //defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items=[{
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
					    fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.state'),// '当前状态',
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
		            fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.transTypeName'),// '词条名称',
		            xtype : 'transtypecombselector'
		        }]
            	}
           
        ],
        me.buttons = [{
            text: pricecard.priceStandard.i18n('miser.common.query'),
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getPriceStandardGrid().getPagingToolbar().moveFirst();;
                }
            }
        },
        {
            text: pricecard.priceStandard.i18n('miser.common.reset'),
            handler: function() {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});


/**
 * 增加表单
 */
Ext.define('Miser.view.pricecard.priceStandardAddForm', {
    extend: 'Ext.form.Panel',
    header: false,
    frame: true,
    collapsible: true,
    width: 600,
    fieldDefaults: {
        labelWidth: 100,
        margin: '8 10 5 10'
    },
    layout: {
        type: 'table',
        columns: 3
    },
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [{
            dataIndex: 'id',
            width: 100,
            hidden:true
        },{
            dataIndex: 'active',
            width: 100, 
            hidden:true
        },{
        	xtype:'startPriceCityselector',
            name: 'sendPriceCity_code',
            allowBlank:false,
            width: 250,
            fieldLabel: '价格城市',  //'出发价格城市'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
            name: 'label',
            html:'————',
            width: 50,
            xtype: 'label'
        },
        {
        	xtype:'arrivePriceCityselector',
            name: 'arrivePriceCity_code',
            allowBlank:false,
            width: 200,
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	xtype:'transtypecombselector',
            name: 'transTypeCode',
            allowBlank:false,
            showAll:false,
            colspan: 3,
            width: 250,
            fieldLabel:pricecard.priceStandard.i18n('pricecard.priceStandard.transTypeName'),  // '运输类型'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
        	xtype :"numberfield",
        	decimalPrecision:2,
			allowDecimals: true,
			maxValue:99999999.99,
			minValue: 0,
            name: 'weightPrice',
            negativeText : '最小值为0',
            allowBlank:false,
            width: 250,
            fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.weightPrice'),  //'重量单价'
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
        	xtype :"numberfield",
        	decimalPrecision:2,
        	maxValue:99999999.99,
			allowDecimals: true,
			minValue: 0,
            name: 'volumePrice',
            negativeText : '最小值为0',
            colspan: 2,
            allowBlank:false,
            width: 250,
            fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.volumePrice'),  //'体积单价',
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
        	xtype :"numberfield",
        	maxValue:99999999.99,
        	decimalPrecision:2,
			allowDecimals: true,
			minValue: 0,
			negativeText : '最小值为0',
			allowBlank:false,
            name: 'addFee',
            width: 250,
            fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.addFee'),  //'附加费',
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
	         name: 'fuelFeeSection',
	         allowBlank:false,
             colspan: 2,
             beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>'],
	         fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.fuelFeeSection'),
	         width: 250,
	         sectionedItem :'FUEL',
	         xtype: 'dynamicPriceSectioncombselector',
	         termsCode:pricecard.priceStandard.fuelFeeSection
	     },
        {
        	xtype :"numberfield",
        	maxValue:99999999.99,
        	decimalPrecision:2,
			allowDecimals: true,
			allowBlank:false,
			negativeText : '最小值为0',
			minValue: 0,
            name: 'lowestFee',
            width: 250,
            fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.lowestFee'),  //'最低收费',
            beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },
        {
    			
    			name: 'effectiveTime',
    			width: 250,
    			fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.effectiveTime'),  //'生效时间',
    			xtype : 'datefield',
    			//minValue:(new Date(new Date().getTime()+6*60*1000)),
    			value:new Date(new Date().getTime()+6*60*1000),
    			colspan: 2,
    			allowBlank:false,
    			format : 'Y-m-d H:i:s',
    			beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        }
        ,{
        	colspan: 3,
        	maxLength: 200,
        	fieldLabel: pricecard.priceStandard.i18n('pricecard.priceStandard.remark'),  //'备注',
        	name: 'remark',
        	xtype: 'textareafield',
            grow: true,
            width: 520,
            anchor: '100%'
        }
        ];
        me.callParent([cfg]);
    }
});

/**
 * 修改标准价格窗口
 */
	Ext.define('Miser.view.pricecard.priceStandardUpdateWindow',{
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
		priceStandardEntity: null,
		listeners : {
			beforehide : function(me) { // 隐藏WINDOW的时候清除数据
				me.getPriceStandardAddForm().getForm().reset(); // 表格重置
			},
			beforeshow : function(me) { // 显示WINDOW的时候清除数据
				var fielsds = me.getPriceStandardAddForm().getForm().getFields();
				if (!Ext.isEmpty(fielsds)) {
					fielsds.each(function(item, index, length) {
						item.clearInvalid();
						item.unsetActiveError();
					});
				}
				fielsds = null;
				me.getPriceStandardAddForm().getForm().loadRecord(new Miser.model.PriceStandardEntity(me.priceStandardEntity));
							
				
				//这块是处理下拉框赋值的问题
				var fuelFeeSectionCombo = me.getPriceStandardAddForm().getForm().findField('fuelFeeSection');
				var fuelFeeSectionComboRecord=Ext.data.Record({
					code: me.priceStandardEntity.fuelFeeSection,
					name: me.priceStandardEntity.fuelFeeSectionName
				}
				);
				fuelFeeSectionCombo.getStore().add(fuelFeeSectionComboRecord);
				fuelFeeSectionCombo.setValue(me.priceStandardEntity.fuelFeeSection);
				//fuelFeeSectionCombo.disable();
				
				//这块是处理下拉框赋值的问题
				var codeCombo = me.getPriceStandardAddForm().getForm().findField('transTypeCode');
				var newcodeComboRecord=Ext.data.Record({
					code: me.priceStandardEntity.transTypeCode,
				    name: me.priceStandardEntity.transTypeName
					}
				);
				codeCombo.getStore().add(newcodeComboRecord);
				codeCombo.setValue(me.priceStandardEntity.transTypeCode);
				codeCombo.disable();
				
				//到达价格城市 这块是处理下拉框赋值的问题
				var arrivePriceCityCombo = me.getPriceStandardAddForm().getForm().findField('arrivePriceCity_code');
				var arrivePriceCityComboRecord=Ext.data.Record({
					code: me.priceStandardEntity.arrivePriceCity.code,
					name: me.priceStandardEntity.arrivePriceCity.name
					}
				);
				arrivePriceCityCombo.getStore().add(arrivePriceCityComboRecord);
				arrivePriceCityCombo.setValue(me.priceStandardEntity.arrivePriceCity.code);
				arrivePriceCityCombo.disable();
				//出发价格城市 这块是处理下拉框赋值的问题
				var sendPriceCityCombo = me.getPriceStandardAddForm().getForm().findField('sendPriceCity_code');
				var sendPriceCityComboRecord=Ext.data.Record({
					code: me.priceStandardEntity.sendPriceCity.code,
					name: me.priceStandardEntity.sendPriceCity.name
					}
				);
				sendPriceCityCombo.getStore().add(sendPriceCityComboRecord);
				sendPriceCityCombo.setValue(me.priceStandardEntity.sendPriceCity.code);
				sendPriceCityCombo.disable();
				
			}
		},
		priceStandardAddForm: null,
	    getPriceStandardAddForm: function() {
	        if (Ext.isEmpty(this.priceStandardAddForm)) {
	            this.priceStandardAddForm = Ext.create('Miser.view.pricecard.priceStandardAddForm');
	        }
	        return this.priceStandardAddForm;
	    },
		submitDriver:function(isConfirm){
			var me = this;
			if (me.getPriceStandardAddForm().getForm().isValid()) { //校验form是否通过校验
				var effectiveTime = me.getPriceStandardAddForm().getForm().findField('effectiveTime');
	        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
	        		miser.showInfoMsg("请选择当前时间之后的时间");
	        		effectiveTime.focus();
	        		return;
	        	}
				var priceStandardEntity = new Miser.model.PriceStandardEntity;
				var id =me.getPriceStandardAddForm().getForm().getRecord().data.id;
				me.getPriceStandardAddForm().getForm().findField('transTypeCode').enable();
				me.getPriceStandardAddForm().getForm().findField('arrivePriceCity_code').enable();
				me.getPriceStandardAddForm().getForm().findField('sendPriceCity_code').enable();
				me.getPriceStandardAddForm().getForm().findField('fuelFeeSection').enable();
				me.getPriceStandardAddForm().getForm().updateRecord(priceStandardEntity); //将FORM中数据设置到MODEL里面
				
				priceStandardEntity.id=id;
				priceStandardEntity.data.id=id;
				var curData=priceStandardEntity.data;
	            var sendPriceCity = new Object(); 
	            var arrivePriceCity = new Object(); 
	            sendPriceCity.code=curData.sendPriceCity_code;
	            arrivePriceCity.code=curData.arrivePriceCity_code;
	            curData.sendPriceCity=sendPriceCity;
	            curData.arrivePriceCity=arrivePriceCity;
	            var transTypeName= me.getPriceStandardAddForm().getForm().findField('transTypeCode').rawValue;
	            if(transTypeName==null)transTypeName='';
	            curData.transTypeName=transTypeName;
	            var params = {
	                'priceStandardVo': {
	                    'priceStandardEntity': curData,
	                    'isConfirm':isConfirm
	                }
	            }
				
				var successFun = function (json) {
					var message = json.message;
					
					if(message=='更新成功'){
						miser.showInfoMsg(message); //提示修改成功
						me.close();
						me.parent.getStore().load(); //成功之后重新查询刷新结果集
					}else if (message==pricecard.priceStandard.i18n('pricecard.priceStandard.isConfirm')){
						miser.showQuestionMes(message,//是否删除
	                            function(e) {
	                                if (e == 'yes') { // 询问是否删除，是则发送请求
	                                	 me.submitDriver("1");
	                                	 me.close();
	                                }
	                     });
					}else if (message==pricecard.priceStandard.i18n('miser.base.stateChange')){
	                	miser.showInfoMsg(message); // 提示状态信息发生变更
	                	me.close();
	                    me.parent.getStore().load(); // 重新查询刷新结果集
	                }else{
						alert(message);
					}
				};
				var failureFun = function (json) {
					if (Ext.isEmpty(json)) {
						miser.showErrorMes('连接超时'); //请求超时
					} else {
						var message = json.message;
						miser.showErrorMes(message); //提示失败原因
					}
				};
				miser.requestJsonAjax('../pricecard/priceStandard!updatePriceStandard.action', params, successFun, failureFun); //发送AJAX请求
			}
		},
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.fbar = [{
				text :pricecard.priceStandard.i18n('miser.common.save'), //保存
				margin : '0 0 0 305',
				handler : function() {
					 me.submitDriver("0");
				}
			}, {
				text :pricecard.priceStandard.i18n('miser.common.reset'), //重置
				handler : function() {
					me.close();
					me.parent.updatePriceStandard();
				}
			},{
				text :pricecard.priceStandard.i18n('miser.common.cancel'), //取消
				handler : function() {
					me.close();
				}
			} ];
			me.items = [ me.getPriceStandardAddForm() ];
			me.callParent([ cfg ]);
		}
	});
	
//增加窗体
Ext.define('Miser.view.pricecard.priceStandardAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增',// '新增词条',
    closable: true,
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
        beforehide: function(me) { // 隐藏WINDOW的时候清除数据
            me.getPriceStandardAddForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getPriceStandardAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            
            var effectiveTime = me.getPriceStandardAddForm().getForm().findField('effectiveTime');
            effectiveTime.setMinValue(new Date(new Date().getTime()));
            effectiveTime.setValue(new Date(new Date().getTime()+6*60*1000));
            
            
        }
    },
    priceStandardAddForm: null,
    getPriceStandardAddForm: function() {
        if (Ext.isEmpty(this.priceStandardAddForm)) {
            this.priceStandardAddForm = Ext.create('Miser.view.pricecard.priceStandardAddForm');
        }
        return this.priceStandardAddForm;
    },
    submitPriceStandardAddForm: function(isConfirm) {
        var me = this;
        if (me.getPriceStandardAddForm().getForm().isValid()) { // 校验form是否通过校验
        	var effectiveTime = me.getPriceStandardAddForm().getForm().findField('effectiveTime');
        	if(effectiveTime.getValue().getTime()<new Date().getTime()){
        		miser.showInfoMsg("请选择当前时间之后的时间");
        		effectiveTime.focus();
        		return;
        	}
            var priceStandardEntity = new Miser.model.PriceStandardEntity;
            me.getPriceStandardAddForm().getForm().updateRecord(priceStandardEntity); // 将FORM中数据设置到MODEL里面
            var curData=priceStandardEntity.data;
            var sendPriceCity = new Object(); 
            var arrivePriceCity = new Object(); 
            sendPriceCity.code=curData.sendPriceCity_code;
            arrivePriceCity.code=curData.arrivePriceCity_code;
            curData.sendPriceCity=sendPriceCity;
            curData.arrivePriceCity=arrivePriceCity;
            var transTypeName= me.getPriceStandardAddForm().getForm().findField('transTypeCode').rawValue;
            if(transTypeName==null)transTypeName='';
            curData.transTypeName=transTypeName;
            curData.id='';
            var params = {
                'priceStandardVo': {
                    'priceStandardEntity': curData,
                    'isConfirm':isConfirm
                }
            }
            var successFun = function(json) {
                var message = json.message;
                if(message=='添加成功'){
                	miser.showInfoMsg(message); // 提示新增成功
                	me.close();
                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
                }else if (message==pricecard.priceStandard.i18n('pricecard.priceStandard.isConfirm')){
                	miser.showQuestionMes(message,//是否删除
                            function(e) {
                                if (e == 'yes') { // 询问是否删除，是则发送请求
                                	 me.submitPriceStandardAddForm("1");
                                	 me.close();
                                }
                     });
                }else if (message==pricecard.priceStandard.i18n('miser.base.stateChange')){
                	miser.showInfoMsg(message); // 提示状态信息发生变更
                	me.close();
                    me.parent.getStore().load(); // 重新查询刷新结果集
                }else{
					alert(message);
				}
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes(pricecard.priceStandard.i18n('miser.pricecard.timeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('../pricecard/priceStandard!addPriceStandard.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: pricecard.priceStandard.i18n('miser.common.save'),//'保存',
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitPriceStandardAddForm("0");
            }
        },
        {
            text:pricecard.priceStandard.i18n('miser.common.reset'),// '重置',
            // 重置
            handler: function() {
                me.getPriceStandardAddForm().reset();
            }
        },
        {
            text: pricecard.priceStandard.i18n('miser.common.cancel'),// '取消',
            // 取消
            handler: function() {
                me.close();
            }
        }];
        me.items = [me.getPriceStandardAddForm()];
        me.callParent([cfg]);
    }
});


/**
 * 标准价格信息表格
 */
Ext.define('Miser.view.priceStandard.Grid', {
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
    priceStandardAddWindow: null,
    getPriceStandardAddWindow: function() {
        if (this.priceStandardAddWindow == null) {
            this.priceStandardAddWindow = Ext.create('Miser.view.pricecard.priceStandardAddWindow');
            this.priceStandardAddWindow.parent = this; // 父元素
        }
        return this.priceStandardAddWindow;
    },
    priceStandardUpdateWindow: null,
    getPriceStandardUpdateWindow: function() {
        if (this.priceStandardUpdateWindow == null) {
            this.priceStandardUpdateWindow = Ext.create('Miser.view.pricecard.priceStandardUpdateWindow');
            this.priceStandardUpdateWindow.parent = this; //父元素
        }
        return this.priceStandardUpdateWindow;
    },
    updatePriceStandard : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
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
				'priceStandardVo' :{
					'priceStandardEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getPriceStandardUpdateWindow(); //获得修改窗口
			updateWindow.priceStandardEntity = json.priceStandardVo.priceStandardEntity;
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
		miser.requestJsonAjax('../pricecard/priceStandard!queryPriceStandardById.action', params, successFun, failureFun);
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
    deletePriceStandard: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(pricecard.priceStandard.i18n('pricecard.priceStandard.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objects='';
        var objectLeng=0;
        for(var i=0;i<selections.length;i++){
        	var object="{"
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
        	object+="\"id\":\""+selections[i].get('id')+"\",\"state\":\""+selections[0].get('state')+"\",\"active\":\""+selections[0].get('active')+"\"";
        	object+="}"
        	if(i!=selections.length-1)
        	object+=","
        	objects+=object;
        	objectLeng++;
        }
       // miser.showQuestionMes(pricecard.priceStandard.i18n('miser.pricecard.isdelete'),//是否删除
        var msg="您确认作废选中的"+objectLeng+"条信息吗？确认后状态会变为";
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
                		'priceStandardVo': {
                			'selects' :"["+ objects+"]"
                        }
                };
                var successFun = function(json) {
                    var message = json.message;
                    miser.showInfoMsg(message);
                    me.getStore().load();
                };
                var failureFun = function(json) {
                    if (Ext.isEmpty(json)) {
                        miser.showErrorMes(pricecard.priceStandard.i18n('pricecard.priceStandard.timeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../pricecard/priceStandard!deletePriceStandard.action', params, successFun, failureFun);
            }
        });
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [{
            text: pricecard.priceStandard.i18n('miser.base.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },{
            dataIndex: 'id',
            width: 100,
            hidden:true
        },{
            dataIndex: 'transTypeName',
            width: 100,
            text:pricecard.priceStandard.i18n('pricecard.priceStandard.transTypeName')  // '运输类型'
        },
        {
            dataIndex: 'sendPriceCity_name',
            width: 120,
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.sendPriceCity.name')  //'出发价格城市'
        },
        {
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.arrivePriceCity.name'),  //'到达价格城市',
            dataIndex: 'arrivePriceCity_name',
            width: 120
        },
        {
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.weightPrice'),  //'重量单价',
            dataIndex: 'weightPrice',
            width: 100
        },
        {
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.volumePrice'),  //'体积单价',
            dataIndex: 'volumePrice',
            width: 100
        },
        {
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.addFee'),  //'附加费',
            dataIndex: 'addFee',
            width: 100
        },
        {
            text: pricecard.priceStandard.i18n('pricecard.priceStandard.lowestFee'),  //'最低收费',
            dataIndex: 'lowestFee',
            width: 100
        },
        {
            dataIndex: 'fuelFeeSection',
            hidden:true
        },
        {
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.fuelFeeSection'),  //'燃油分段',
        	dataIndex: 'fuelFeeSectionName',
        	width: 100
        },
        {
    	text: pricecard.priceStandard.i18n('pricecard.priceStandard.effectiveTime'),  //'生效时间',
    	dataIndex: 'effectiveTime',
    	renderer: function(value) {
            return dateRender(value,'Y-m-d H:i:s');
        },
    	width: 150
    	
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.invalidTime'),  //'失效时间',
        	dataIndex: 'invalidTime',
        	renderer: function(value) {
                return dateRender(value,'Y-m-d H:i:s');
            },
        	width: 150
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.state'),  //'状态',
        	dataIndex: 'state',
        	renderer : function(value) {
				return miser
						.changeCodeToNameStore(
								getDataDictionary()
										.getDataDictionaryStore('PRICE_SATUS'),
								value);
			},
        	width: 100
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.active'),  //'是否有效',
        	dataIndex: 'active',
        	renderer: function(value) {
                return booleanTypeRender(value);
            },
        	width: 100
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.remark'),  //'备注',
        	dataIndex: 'remark',
        	width: 100
        },{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.createUser'),  //'创建人',
        	dataIndex: 'createUser',
        	width: 100
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.createDate'),  //'创建时间',
        	dataIndex: 'createDate',
        	renderer: function(value) {
                return dateRender(value);
            },
        	width: 100
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.modifyUser'),  //'修改用户',
        	dataIndex: 'modifyUser',
        	width: 100
        }
        ,{
        	text: pricecard.priceStandard.i18n('pricecard.priceStandard.modifyDate'),  //'修改时间',
        	dataIndex: 'modifyDate',
        	renderer: function(value) {
                return dateRender(value);
            },
        	width: 100
        }
        ],
        me.store = Ext.create('Miser.store.PriceStandardStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [{
            text:pricecard.priceStandard.i18n('pricecard.priceStandard.addentry'),// '新增词条',
            xtype: 'addbutton',
            // 新增
            width: 80,
            //hidden:!pricecard.priceStandard.isPermission('dataDictionary/dataDictionaryAddButton'),
            handler: function() {
                me.getPriceStandardAddWindow().show();
            }
        },
        '-', {
            text:pricecard.priceStandard.i18n('pricecard.priceStandard.updateentry'),// '修改',
            xtype: 'updatebutton',
			width: 80,
            handler: function() {
                me.updatePriceStandard();
            }
        },
        '-', {
        	id : 'miser_biz_pricecard_priceStandard_del_id',
            text:pricecard.priceStandard.i18n('miser.common.delete'),// '删除',
            xtype: 'deletebutton',
            disabled : true,
            // 作废
            width: 80,
            handler: function() {
                me.deletePriceStandard();
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
            text: pricecard.priceStandard.i18n('miser.base.importTemplateDownLoad'),
            width: 130,
            handler: function() {
            	miser.requestExportAjax('/excelTemplate/priceStandardTemplate.xls');
            }
        },{
			xtype: 'downloadbutton',
            text: '导出',
            width: 80,
            handler:function(){
            	down();
            }
		}];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                Ext.getCmp('miser_biz_pricecard_priceStandard_del_id').setDisabled(selections.length === 0);
            }
        }
    }),
        // Ext.setGlyphFontFamily('FontAwesome');
        me.callParent([cfg]);
    }
});







Ext.onReady(function() {
    /**
	 * 数据字典页面
	 */
    Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.view.priceStandard.QueryForm'); //查询FORM
    var priceStandardGrid = Ext.create('Miser.view.priceStandard.Grid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getPriceStandardGrid: function() {
            return priceStandardGrid;
        },
        items: [queryForm, priceStandardGrid]
    });
});