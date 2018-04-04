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
	var queryForm = Ext.getCmp('Miser_base_priceAgingUrban_priceAgingUrbanForm_id');
    if (queryForm != null) {
        	params = {
        	'agingCityVo.agingCityEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
            'agingCityVo.agingCityEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
            'agingCityVo.agingCityEntity.areaCode': queryForm.getForm().findField('areaName').getValue(),
            'agingCityVo.agingCityEntity.isLocations': queryForm.getForm().findField('isLocations').getValue(),
            'agingCityVo.agingCityEntity.startAgingCity': queryForm.getForm().findField('startAgingCity').getValue(),
            'agingCityVo.agingCityEntity.arriveAgingCity': queryForm.getForm().findField('arriveAgingCity').getValue()
        };
    }
	Ext.Ajax.request( {  
	       url : '../bizbase/agingCityAction!excelExport.action',  
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
	       url : '../bizbase/agingCityAction!cityExport.action',  
	       method : 'post',  
	       timeout:1800000,
	       params : {  
	    	   'agingCityVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次出发时效城市导入"
	            	  +responseArray.addStartAgingCitySize
	            	  +"条数据,到达时效城市导入"
	            	  +responseArray.addArriveAgingCitySize
	            	  +"条数据，覆盖出发价格价格城市数据"
	            	  +responseArray.coverStartAgingCitySize
	            	  +"条,覆盖到达价格城市数据"
	            	  +responseArray.coverArriveAgingCitySize+"条"
	              
	              miser.showInfoMsg(msg);
	              if(responseArray.fileStartPath!=''||responseArray.fileArrivePath!=''){
	            	  miser.requestExportAjax(responseArray.fileStartPath);
	            	  miser.requestExportAjax(responseArray.fileArrivePath);
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
 * 时效城市
 */
Ext.define('Miser.model.bizbase.AgingCityEntityModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'provinceName',
        //行政省份
        type: 'string'
    },
    {
        name: 'cityName',
        //行政城市
        type: 'string'
    },
    {
        name: 'areaName',
        //行政区县
        type: 'string'
    },
    {
        name: 'isLocations',
        //是否有网点
        type: 'string'
    },{
        name: 'startAgingCityCode',
        //出发时效城市Code
        type: 'string'
    },{
        name: 'arriveAgingCityCode',
        //到达时效城市Code
        type: 'string'
    },
    {
        name: 'startAgingCity',
        //出发时效城市
        type: 'string'
    },{
        name: 'arriveAgingCity',
        //到达时效城市
        type: 'string'
    },{
        name: 'remarks',
        //备注
        type: 'string'
    },{
        name: 'modifyUserCode',
        //修改人
        type: 'string'
    },
    {
        name: 'modifyTime',
        //修改时间
        type: 'date',
        dateFormat:'time'
    }]
});

Ext.define('Miser.base.agingCity.AgingCityForm', {
    extend: 'Ext.form.Panel',
    id: 'Miser_base_priceAgingUrban_priceAgingUrbanForm_id',
    frame: true,
    title: bizbase.agingCity.i18n('bizbase.agingCity.queryCondition'),//查询条件
    height: 140,
    collapsible: true,
    layout: 'column',    
    region: 'north',
    
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [{layout: {
            type: 'hbox',
            pack: 'start',
            align: 'stretch'
        	},
        	name: '',
            labelWidth: 20,
            cityLabel: bizbase.agingCity.i18n('bizbase.agingCity.cityName'),
            cityName: 'cityName',
            cityIsBlank:true,
            //名称
            provinceLabel:bizbase.agingCity.i18n('bizbase.agingCity.provinceName'),
            provinceName: 'provinceName',
            provinceIsBlank:true,
            //省名称
            areaLabel: bizbase.agingCity.i18n('bizbase.agingCity.areaName'),
            areaName: 'areaName',
            areaIsBlank:true,
            
            // 县名称
            type: 'P-C-C',
            xtype: 'linkregincombselector'
        },{
        	 name: 'isLocations',
        	 fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.isLocations'),
             width: 275,
             xtype: 'yesnocombselector'
        },{
	       	 name: 'startAgingCity',
	    	 fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.startAgingCity'),
	    	 width: 275,
	         margin: '20 0 0 0',
	         xtype: 'startAgingCityselector'
	        },{
		   	 name: 'arriveAgingCity',
			 fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.arriveAgingCity'),
			 margin: '20 0 0 30',
		     width: 275,
		     xtype: 'arriveAgingCityselector'
	        }],
        me.buttons = [{
            text:bizbase.agingCity.i18n('bizbase.agingCity.query'),//查询
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getpriceAgingUrbanGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
        	id:'downbutton_id',
        	text: bizbase.agingCity.i18n('bizbase.agingCity.downbutton'),//导出
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
/**修改表单*/
Ext.define('Miser.base.PriceCity.CityUpdateForm', {
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
        	name: 'provinceName',
        	width: 250,
        	colspan: 2,
        	xtype:'textfield',
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.provinceName1'),
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	name: 'cityName',
        	width: 250,
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.cityName1'),
        	flex:1,
    		xtype : 'textfield',
    		beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.areaName1'),
        	name: 'areaName',
        	xtype :'textfield',
    		decimalPrecision:2,
    		minValue: 0,
        	width: 250,
        	beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        },{
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.startAgingCity'),
        	name: 'startAgingCity',
        	xtype :'startAgingCityselector',
    		decimalPrecision:2,
    		minValue: 0,
        	width: 250
        },{
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.arriveAgingCity'),
        	name: 'arriveAgingCity',
        	xtype :'arriveAgingCityselector',
    		decimalPrecision:2,
    		minValue: 0,
        	width: 250
        },
        {
        	name:'remarks',
        	fieldLabel:bizbase.agingCity.i18n('bizbase.agingCity.remarks'),
        	xtype: 'textarea'
        }
           ];
        me.callParent([cfg]);
    }
});


Ext.define('Miser.base.AgingCity.CityUpdateWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	parent : null,
	resizable : false,
	// 可以调整窗口的大小
	closeAction : 'hide',
	// 点击关闭是隐藏窗口
	agingCityEntity: null,
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
			me.getCityUpdateForm().getForm().loadRecord(new Miser.model.bizbase.AgingCityEntityModel(me.agingCityEntity));
			me.getCityUpdateForm().getForm().findField('startAgingCity').getStore().load();
			me.getCityUpdateForm().getForm().findField('arriveAgingCity').getStore().load();
			me.getCityUpdateForm().getForm().findField('startAgingCity').setCombValue(me.agingCityEntity.startAgingCity,me.agingCityEntity.startAgingCityCode);
			me.getCityUpdateForm().getForm().findField('arriveAgingCity').setCombValue(me.agingCityEntity.arriveAgingCity,me.agingCityEntity.arriveAgingCityCode);
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
    		this.cityUpdateForm = Ext.create('Miser.base.PriceCity.CityUpdateForm');
    	}
    	return this.cityUpdateForm;
    },
    submitDriver:function(){
		var me = this;
		if (me.getCityUpdateForm().getForm().isValid()) { //校验form是否通过校验
			var agingCityEntity =new Miser.model.bizbase.AgingCityEntityModel;
			me.getCityUpdateForm().getForm().updateRecord(agingCityEntity); //将FORM中数据设置到MODEL里面
			var curData=agingCityEntity.data;
			curData.startAgingCity = me.getCityUpdateForm().getForm().findField('startAgingCity').rawValue;
			curData.arriveAgingCity = me.getCityUpdateForm().getForm().findField('arriveAgingCity').rawValue;
            var params = {
                'agingCityVo': {
                    'agingCityEntity': curData
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
			miser.requestJsonAjax('../bizbase/agingCityAction!updateAgingCityResource.action', params, successFun, failureFun); //发送AJAX请求
		}
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
           text: bizbase.agingCity.i18n('bizbase.agingCity.save'),//'保存',
            /* margin : '0 0 0 55', */
            handler: function() {
                me.submitDriver();
            }
        },
        {
            text: bizbase.agingCity.i18n('bizbase.agingCity.cancel'),// '取消',
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
 * 时效管理表格
 */
Ext.define('Miser.base.agingCity.AgingCityGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    id : 'cityGrid',
    autoScroll: true,
    width: '300%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    // 交替行效果
    region: 'center',
    emptyText:bizbase.agingCity.i18n('bizbase.agingCity.resultisNull'),
    //查询结果为空
    viewConfig: {
        enableTextSelection: true
    },
    cityUpdateWindow:null,
    getCityUpdateWindow:function(){
    	if(Ext.isEmpty(this.cityUpdateWindow)){
    		this.cityUpdateWindow = Ext.create('Miser.base.AgingCity.CityUpdateWindow');
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
				'agingCityVo' :{
					'agingCityEntity' : {
						'provinceName' : provinceName,
						'cityName':cityName,
						'areaName':areaName
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getCityUpdateWindow(); //获得修改窗口
			updateWindow.agingCityEntity = json.agingCityVo.agingCityEntity;
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
		miser.requestJsonAjax('../bizbase/agingCityAction!queryAgingCityInUpdate.action', params, successFun, failureFun);
	},
    selModel: Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                var length=selections.length;
                if( length == 1){
                	Ext.getCmp('updateAging_id').setDisabled(false);
//                	Ext.getCmp('downbutton_id').setDisabled(false);
                }else{
                	Ext.getCmp('updateAging_id').setDisabled(true);	
//                	Ext.getCmp('downbutton_id').setDisabled(true);	
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
            text: bizbase.agingCity.i18n('bizbase.agingCity.rownumberer'),
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.provinceName1'),
            //text: bizbase.agingCity.i18n('bse.priceAgingUrban.province'),//'行政省份'
            dataIndex: 'provinceName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.cityName1'),
            //text: bizbase.agingCity.i18n('bse.priceAgingUrban.city'),//'行政城市',
            dataIndex: 'cityName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.areaName1'),
            //text: bizbase.agingCity.i18n('bse.priceAgingUrban.county'),//'行政区域',
            dataIndex: 'areaName',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.isLocations'),
        	//text: bizbase.agingCity.i18n('bse.priceAgingUrban.IsLocation'),//'是否有网点',
            dataIndex: 'isLocations',
            renderer: function(value) {
                return booleanTypeRender(value);
            },
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.startAgingCity'),
            //text:bizbase.agingCity.i18n('bse.priceAgingUrban.startAgingCity'),// '出发时效城市',
            dataIndex: 'startAgingCity',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.arriveAgingCity'),
           // text:bizbase.agingCity.i18n('bse.priceAgingUrban.arriveAgingCity'),//'到达时效城市',
           // dataIndex: 'arriveAgingCity',
            dataIndex: 'arriveAgingCity',
            width: 80,
            align: 'center',
            flex: 1
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.remarks'),
            dataIndex: 'remarks',
            width: 80,
            align: 'center',
            flex: 1
        },
        {        	
        	text:bizbase.agingCity.i18n('bizbase.agingCity.modifyTime'),
            dataIndex: 'modifyTime',
            width: 100,
            align: 'center',
            flex: 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        },
        {
        	text:bizbase.agingCity.i18n('bizbase.agingCity.modifyUserCode'),
            dataIndex: 'modifyUserCode',
            width: 80,
            align: 'center',
            flex: 1
        }],
        me.tbar = [
        	{
        	id:'updateAging_id',
        	text:bizbase.agingCity.i18n('bizbase.agingCity.agingCityUpdate'),
            xtype: 'updatebutton',
            width: 130,
            disabled:true,
            handler: function() {
            	 me.updatePpfiItems();
            }
        },{
			xtype: 'uploadbutton',
			text:bizbase.agingCity.i18n('bizbase.agingCity.export'),
            width: 80,
            handler:function(){
            	uploadT.excelWindow(null,imple);
            }
        },{
            id: 'aging_city_downbutton_id',
            alias: 'widget.downloadbutton',
        	glyph : 0xf019,
            disabled:false,
        	cls: 'download-btn-item',
            text: bizbase.agingCity.i18n('bizbase.agingCity.templateDownLoad'),
            width: 130,
            handler: function() {
            	miser.requestExportAjax('/excelTemplate/agingCity.xls');
            	}
            }];
        me.store = Ext.create('Miser.base.agingCity.AgingCityStore', {
            autoLoad: false
        });
        me.bbar
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    },
    listeners:{
		itemdblclick:function(view,model,item,index,e,eOpts){
			var me = this;
			me.updatePpfiItems();
		}
	}
});

/**
 * 价格城市时效store
 */
Ext.define('Miser.base.agingCity.AgingCityStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.bizbase.AgingCityEntityModel',
    id:'agingCityStore',
    pageSize: 13,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'agingCityAction!queryAgingCityList.action',
        reader: {
            type: 'json',
            rootProperty: 'agingCityVo.agingCityList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('Miser_base_priceAgingUrban_priceAgingUrbanForm_id');
            if (queryForm != null) {
                var params = {
                   // 'priceAgingUrbanVo.priceAgingUrbanEntity.isLocations': Ext.util.Format.trim(queryForm.getForm().findField('isLocations').getValue()),
                    'agingCityVo.agingCityEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
                    'agingCityVo.agingCityEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
                    'agingCityVo.agingCityEntity.areaCode': queryForm.getForm().findField('areaName').getValue(),
                    'agingCityVo.agingCityEntity.isLocations': queryForm.getForm().findField('isLocations').getValue(),
                    'agingCityVo.agingCityEntity.startAgingCity': queryForm.getForm().findField('startAgingCity').getValue(),
                    'agingCityVo.agingCityEntity.arriveAgingCity': queryForm.getForm().findField('arriveAgingCity').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});

Ext.onReady(function() {
    var queryForm = Ext.create("Miser.base.agingCity.AgingCityForm");
    var priceAgingUrbanGrid = Ext.create("Miser.base.agingCity.AgingCityGrid");
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
        getpriceAgingUrbanGrid: function() {
            return priceAgingUrbanGrid;
        },
        items: [queryForm, priceAgingUrbanGrid]
    });
	
});


