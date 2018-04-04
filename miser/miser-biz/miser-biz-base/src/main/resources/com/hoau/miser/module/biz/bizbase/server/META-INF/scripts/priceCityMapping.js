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
		});
			xwindow.show();
		}
};
//下载
function down(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('Miser_base_pricePriceUrban_pricePriceUrbanForm_id');
    if (queryForm != null) {
        	params = {
        	'priceCityMappingVo.priceCityMappingEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
            'priceCityMappingVo.priceCityMappingEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
            'priceCityMappingVo.priceCityMappingEntity.areaCode': queryForm.getForm().findField('areaName').getValue(),
            'priceCityMappingVo.priceCityMappingEntity.isLocations': queryForm.getForm().findField('isLocations').getValue(),
            'priceCityMappingVo.priceCityMappingEntity.startPriceCityCode': queryForm.getForm().findField('startPriceCityCode').getValue(),
            'priceCityMappingVo.priceCityMappingEntity.arrivalPriceCityCode': queryForm.getForm().findField('arrivalPriceCityCode').getValue()
        };
    }
	Ext.Ajax.request( {  
	       url : '../bizbase/priceCityMappingAction!excelExport.action',
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导出共"+responseArray.count+"条数据！";
	              miser.showInfoMsg(msg);
	              miser.requestExportAjax(responseArray.filePath);
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '修改失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
}
function imple(outFileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
	       url : '../bizbase/priceCityMappingAction!cityImport.action',
	       method : 'post',  
	       params : {  
	    	   'priceCityMappingVo.filePath' : outFileName
	          },  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次出发价格城市导入"+responseArray.addStartPriceCitySize+"条数据,到达价格城市导入"+responseArray.addArrivePriceCitySize+"条数据，覆盖出发价格城市数据"+responseArray.coverStartPriceCitySize+"条,覆盖到达价格城市数据"+responseArray.coverArrivePriceCitySize+"条"
	              
	              miser.showInfoMsg(msg);
	              if(!Ext.isEmpty(responseArray.errorFilePath)){
	            	  //新打开一个下载页面
	            	  miser.requestExportAjax(responseArray.errorFilePath);
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
/**
 * 价格城市
 */
Ext.define('Miser.model.bizbase.priceCityMappingEntityModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'provinceName',
        //行政省份
        type: 'string'
    }, {
        name: 'cityName',
        //行政城市
        type: 'string'
    }, {
        name: 'areaName',
        //行政区县
        type: 'string'
    }, {
        name: 'provinceCode',
        //行政省份
        type: 'string'
    }, {
        name: 'cityCode',
        //行政城市
        type: 'string'
    }, {
        name: 'areaCode',
        //行政区县
        type: 'string'
    }, {
        name: 'isLocations',
        //是否有网点
        type: 'string'
    }, {
        name: 'startPriceCity',
        //出发价格城市
        type: 'string'
    }, {
        name: 'arrivePriceCity',
        //到达价格城市
        type: 'string'
    }, {
		name: 'startPriceCityCode',
		//出发价格城市编码
		type: 'string'
	}, {
		name: 'arrivalPriceCityCode',
		//到达价格城市编码
		type: 'string'
	}, {
        name: 'remarks',
        //备注
        type: 'string'
    }, {
        name: 'modifyUserCode',
        //修改人
        type: 'string'
    }, {
        name: 'modifyTime',
        //修改时间
        type: 'date',
        dateFormat:'time'
    }]
});

Ext.define('Miser.base.priceCityMapping.PriceCityForm', {
    extend: 'Ext.form.Panel',
    id: 'Miser_base_pricePriceUrban_pricePriceUrbanForm_id',
    frame: true,
    title: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.queryCondition'),//查询条件
    collapsible: true,
    region: 'north',
    defaults : {
		labelWidth : 0.3,
		columnWidth : 0.6,
		margin : '10 10 10 10',
		labelAlign : 'right'
				},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [	        
     	   {
        		layout: 'column',
        		defaults : {
							labelWidth : 70,
							labelAlign : "left"
							},
				items:[{
		        	name: '',
		            labelWidth: 20,
		            cityLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.cityName'),
		            cityName: 'cityName',
		            cityIsBlank:true,
		            //名称
		            provinceLabel: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.provinceName'),
		            provinceName: 'provinceName',
		            provinceIsBlank:true,
		            //省名称
		            areaLabel: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.areaName'),
		            areaName: 'areaName',
		            areaIsBlank:true,
		            
		            // 县名称
		            type: 'P-C-C',
		            xtype: 'linkregincombselector'
		       		 },{
		        	 name: 'isLocations',
		        	 fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.isLocations'),
		             width: 275,
		             xtype: 'yesnocombselector'
		             }
             	]
        },{
	       	layout : 'column',
			defaults : {
				labelWidth : 70,
				labelAlign : "right"
			},
			defaults : {
				margins : '10 10 10 10'
			},
			items : [{
	       	 name: 'startPriceCityCode',
	    	 fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.startPriceCity'),
	         width: 275,
	         margin: '20 0 0 0',
	         xtype: 'startPriceCityselector'
	        },{
		   	 name: 'arrivalPriceCityCode',
			 fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.arrivePriceCity'),
			 margin: '20 0 0 30',
		     width: 275,
		     xtype: 'arrivePriceCityselector'
	         }]
	        }
	        ],
        me.buttons = [{
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.query'),
            //text:bizbase.priceCityMapping.i18n('miser.common.query'),//查询
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getpricePriceUrbanGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
        	id:'downbutton_id',
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.export'),
        	//text: bizbase.priceCityMapping.i18n('bse.pricePriceUrban.downbutton'),//导出
            alias: 'widget.downloadbutton',
        	glyph : 0xf019,
            disabled:false,
        	cls: 'download-btn-item',
            handler: function() {
            	down();
            }
        }];
        me.callParent([cfg]);
    }
});

	/**
	 * 修改表单
	 */
	Ext.define('Miser.base.priceCityMapping.CityUpdateForm', {
	    extend: 'Ext.form.Panel',
	    header: false,
	    frame: true,
	    collapsible: true,
	    width: 350,
	    fieldDefaults: {
	        labelWidth: 100,
	        margin: '8 10 5 10'
	    },
	    defaultType: 'textfield',
	    constructor: function(config) {
	        var me = this,
	        cfg = Ext.apply({},config);
	        
	        me.items = [{
	        	name: 'id',
	        	width: 250,
	        	hidden:true
	        },{
                name: 'provinceCode',
                xtype:'textfield',
                hidden:true
            },{
                name: 'cityCode',
                xtype:'textfield',
                hidden:true
            },{
                name: 'areaCode',
                xtype:'textfield',
                hidden:true
            },{
	        	name: 'provinceName',
	        	width: 250,
	        	colspan: 2,
	        	xtype:'textfield',
	        	allowBlank:false,
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.provinceName1'),
	        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
	        },{
	        	name: 'cityName',
	        	width: 250,
	        	allowBlank:false,
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.cityName1'),
	        	flex:1,
	    		xtype : 'textfield',
	        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
	        },{
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.areaName1'),
	        	name: 'areaName',
	        	xtype :'textfield',
	        	allowBlank:false,
	    		decimalPrecision:2,
	    		minValue: 0,
	        	width: 250,
	        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
	        },{
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.startPriceCity'),
	        	name: 'startPriceCityCode',
	        	xtype :'startPriceCityselector',
                showAll : false,
	    		decimalPrecision:2,
	    		minValue: 0,
	        	width: 250
	        },{
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.arrivePriceCity'),
	        	name: 'arrivalPriceCityCode',
	        	xtype :'arrivePriceCityselector',
                showAll : false,
	    		decimalPrecision:2,
	    		minValue: 0,
	        	width: 250
	        },
	        {
	        	name:'remarks',
	        	fieldLabel:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.remarks'),
	        	xtype: 'textarea'
	        }
	           ];
	        me.callParent([cfg]);
	    }
	});

Ext.define('Miser.base.priceCityMapping.CityUpdateWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	parent : null,
	resizable : false,
	// 可以调整窗口的大小
	closeAction : 'hide',
	// 点击关闭是隐藏窗口
	priceCityMappingEntity: null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getCityUpdateForm().getForm().reset(); // 表格重置
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getCityUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			me.getCityUpdateForm().getForm().loadRecord(new Miser.model.bizbase.priceCityMappingEntityModel(me.priceCityMappingEntity));
			me.getCityUpdateForm().getForm().findField('startPriceCityCode').getStore().load();
			me.getCityUpdateForm().getForm().findField('arrivalPriceCityCode').getStore().load();
            me.getCityUpdateForm().getForm().findField('startPriceCityCode').setCombValue(me.priceCityMappingEntity.startPriceCity, me.priceCityMappingEntity.startPriceCityCode);
            me.getCityUpdateForm().getForm().findField('arrivalPriceCityCode').setCombValue(me.priceCityMappingEntity.arrivePriceCity, me.priceCityMappingEntity.arrivalPriceCityCode);
			var provinceName= me.getCityUpdateForm().getForm().findField('provinceName');
			var cityName= me.getCityUpdateForm().getForm().findField('cityName');
			var areaName  = me.getCityUpdateForm().getForm().findField('areaName');
			provinceName.setReadOnly(true);
			cityName.setReadOnly(true);
			areaName.setReadOnly(true);
		}
	},
	cityUpdateForm:null,
    getCityUpdateForm:function(){
    	if(Ext.isEmpty(this.cityUpdateForm)){
    		this.cityUpdateForm = Ext.create('Miser.base.priceCityMapping.CityUpdateForm');
    	}
    	return this.cityUpdateForm;
    },
    submitDriver:function(){
		var me = this;
        var priceCityMappingEntity =new Miser.model.bizbase.priceCityMappingEntityModel;
        me.getCityUpdateForm().getForm().updateRecord(priceCityMappingEntity); //将FORM中数据设置到MODEL里面
        var curData=priceCityMappingEntity.data;
        var params = {
            'priceCityMappingVo': {
                'priceCityMappingEntity': curData
            }
        }
        var successFun = function (json) {
            var message = json.message;
            miser.showInfoMsg(message); //提示修改成功
            me.close();
             Ext.getCmp('cityGrid').getStore().reload(); //成功之后重新查询刷新结果集
        };
        var failureFun = function (json) {
            if (Ext.isEmpty(json)) {
                miser.showErrorMes('连接超时'); //请求超时
            } else {
                var message = json.message;
                miser.showErrorMes(message); //提示失败原因
            }
        };
        miser.requestJsonAjax('../bizbase/priceCityMappingAction!updatePriceCityResource.action', params, successFun, failureFun); //发送AJAX请求
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.save'),
            // 保存
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitDriver();
            }
        },
        {
            text: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.cancel'),// '取消',
            // 取消
            handler: function() {
            	miser.showQuestionMes('确认退出？',// 是否删除
						function(e) {
							if (e == 'yes') {
								 me.close();
							}
						});
            }
        }];
        me.items = [me.getCityUpdateForm()];
        me.callParent([cfg]);
    }
});
/**
 * 价格管理表格
 */
Ext.define('Miser.base.priceCityMapping.PriceCityGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    id : 'cityGrid',
    autoScroll: true,
    width: '300%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    // 交替行效果
    region: 'center',
    emptyText:'查询结果为空',
    //emptyText: bizbase.priceCityMapping.i18n('miser.common.resultisNull'),//查询结果为空
    //查询结果为空
    viewConfig: {
        enableTextSelection: true
    },
    cityUpdateWindow:null,
    getCityUpdateWindow:function(){
    	if(Ext.isEmpty(this.cityUpdateWindow)){
    		this.cityUpdateWindow = Ext.create('Miser.base.priceCityMapping.CityUpdateWindow');
    	}
    	return this.cityUpdateWindow;
    },
    updatePpfiItems : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var provinceName = selections[0].get('provinceName');
		var cityName = selections[0].get('cityName');
		var areaName = selections[0].get('areaName');
		var params = {
				'priceCityMappingVo' :{
					'priceCityMappingEntity' : {
						'provinceName' : provinceName,
						'cityName':cityName,
						'areaName':areaName
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getCityUpdateWindow(); //获得修改窗口
			updateWindow.priceCityMappingEntity = json.priceCityMappingVo.priceCityMappingEntity;
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
		miser.requestJsonAjax('../bizbase/priceCityMappingAction!queryPriceCityInUpdate.action', params, successFun, failureFun);
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
    selModel: Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                var length=selections.length;
                if( length == 1){
                	Ext.getCmp('miser_bizbase_pricecitymapping_updatePrice_id').setDisabled(false);
                }else{
                	Ext.getCmp('miser_bizbase_pricecitymapping_updatePrice_id').setDisabled(true);	
                }                
                
            }
        }
    }),
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
        cfg = Ext.apply({},
        config);
        me.columns = [{
            text: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.provinceName'),
            dataIndex: 'provinceName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.cityName'),
            dataIndex: 'cityName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.areaName'),
            dataIndex: 'areaName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.isLocations'),
            dataIndex: 'isLocations',
            renderer: function(value) {
                return booleanTypeRender(value);
            },
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.startPriceCity'),
            dataIndex: 'startPriceCity',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.arrivePriceCity'),
            dataIndex: 'arrivePriceCity',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.remarks'),
            dataIndex: 'remarks',
            width: 80,
            align: 'center',
            flex: 1
        },
        {        	
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.modifyTime'),
            dataIndex: 'modifyTime',
            width: 100,
            align: 'center',
            flex: 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        },
        {
            text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.modifyUserCode'),
            dataIndex: 'modifyUserCode',
            width: 80,
            align: 'center',
            flex: 1
        }],
        me.tbar = [
        	{
        	id:'miser_bizbase_pricecitymapping_updatePrice_id',
        	text:bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.priceCityModify'),
            xtype: 'updatebutton',
            width: 130,
            disabled:true,
            handler: function() {
            	  me.updatePpfiItems();
            }
        },{
            xtype: 'uploadbutton',
            text: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.import'),
            width: 80,
            handler: function() {
            		uploadT.excelWindow(null,imple);
            }
        },{
            alias: 'widget.downloadbutton',
        	glyph : 0xf019,
            disabled:false,
        	cls: 'download-btn-item',
            text: bizbase.priceCityMapping.i18n('bizbase.priceCityMapping.templateDownLoad'),
			width: 130,
            handler: function() {
            	miser.requestExportAjax('/excelTemplate/priceCity.xls');
            }
        }];
        me.store = Ext.create('Miser.base.priceCityMapping.PriceCityStore', {
            autoLoad: false
        });
        me.bbar
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
        
     
    }
});

/**
 * 价格城市价格store
 */
Ext.define('Miser.base.priceCityMapping.PriceCityStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.bizbase.priceCityMappingEntityModel',
    id:'priceCityStore',
    pageSize: 13,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../bizbase/priceCityMappingAction!queryPriceCityList.action',
        reader: {
            type: 'json',
            rootProperty: 'priceCityMappingVo.priceCityList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('Miser_base_pricePriceUrban_pricePriceUrbanForm_id');
            if (queryForm != null) {
                var params = {
                    'priceCityMappingVo.priceCityMappingEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
                    'priceCityMappingVo.priceCityMappingEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
                    'priceCityMappingVo.priceCityMappingEntity.areaCode': queryForm.getForm().findField('areaName').getValue(),
                    'priceCityMappingVo.priceCityMappingEntity.isLocations': queryForm.getForm().findField('isLocations').getValue(),
                    'priceCityMappingVo.priceCityMappingEntity.startPriceCityCode': queryForm.getForm().findField('startPriceCityCode').getValue(),
                    'priceCityMappingVo.priceCityMappingEntity.arrivalPriceCityCode': queryForm.getForm().findField('arrivalPriceCityCode').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

Ext.onReady(function() {
    var queryForm = Ext.create("Miser.base.priceCityMapping.PriceCityForm");
    var pricePriceUrbanGrid = Ext.create("Miser.base.priceCityMapping.PriceCityGrid");
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
        getQueryForm: function() {
            return queryForm;
        },
        getpricePriceUrbanGrid: function() {
            return pricePriceUrbanGrid;
        },
        items: [queryForm, pricePriceUrbanGrid]
    });
	
});


