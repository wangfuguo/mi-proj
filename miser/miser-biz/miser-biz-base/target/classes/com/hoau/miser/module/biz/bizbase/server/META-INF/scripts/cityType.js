//文件上传
var uploadT = {
	oufileName : null,
	excelWindow : function(url, fn) {
		if (Ext.isEmpty(fn)) {
			Ext.toast('请传入回调函数', '温馨提示', 't');
			return;
		}
		// 没有url则默认为上传文件
		var flag = false;
		if (Ext.isEmpty(url)) {
			flag = true;
			url = '../common/FileUpLoadAction!upLoadFile.action';
		}
		var fp = new Ext.FormPanel({
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
				regex : /^.*?\.(xlsx|xls|zip)$/,
				regexText : '只能上传 *.xlsx,*.xls,*.zip',
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
						fp.getForm().submit({
							method : 'post',
							url : url, // 后台处理的action
							waitMsg : '操作处理中，请稍等...',
							waitTitle : '提示',
							success : function(fp, action) {
								if (flag) {
									fn(action.result.outFileName, true);
								} else {
									fn(true);
								}
								xwindow.destroy();
							},
							failure : function(fp, action) {
								if (flag) {
									fn('服务器异常', false);
								} else {
									fn(false);
								}
								fn(action.result.outFileName, true);
								xwindow.destroy();
							}
						});
					}
				}
			} ]
		});
		var xwindow = new Ext.Window({
			renderTo : Ext.getBody(),
			closeAction : "hide",
			plain : true,
			width : 540,
			title : "上传数据",
			modal : true,
			items : [ fp ]
		});
		xwindow.show();
	}
};

// 下载
function down() {
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params = null;
	var queryForm = Ext.getCmp('Miser_base_cityTypeUrban_cityTypeUrbanForm_id');
	if (queryForm != null) {
		params = {
			'cityTypeVo.cityTypeEntity.provinceCode' : queryForm.getForm()
					.findField('provinceName').getValue(),
			'cityTypeVo.cityTypeEntity.cityCode' : queryForm.getForm()
					.findField('cityName').getValue(),
			'cityTypeVo.cityTypeEntity.areaCode' : queryForm.getForm()
					.findField('areaName').getValue(),
			'cityTypeVo.cityTypeEntity.cityType' : queryForm.getForm()
					.findField('cityType').getValue()
		};
	}
	Ext.Ajax.request({
		url : '../bizbase/cityTypeAction!excelExport.action',
		method : 'post',
		params : params,
		success : function(response, options) {
			// 隐藏进度条
			Ext.Msg.hide();
			var responseArray = Ext.util.JSON.decode(response.responseText);
			
			var msg = "本次导出共" + responseArray.count + "条数据！";
			miser.showInfoMsg(msg);
			location.href = "../common/FileDownLoadAction.action?fileName="
					+ responseArray.filePath;
		},
		failure : function() {
			Ext.Msg.hide();
			Ext.MessageBox.show({
				title : '失败',
				msg : '导出失败',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		}
	});
}


function imple(outFileName, flag) {
	if (flag) {
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request({
					url : '../bizbase/cityTypeAction!cityTypeImport.action',
					method : 'post',
					timeout:3600000,
					params : {
						'cityTypeVo.filePath' : outFileName
					},
					success : function(response, options) {
						// 隐藏进度条
						Ext.Msg.hide();
						var responseArray = Ext.util.JSON
								.decode(response.responseText);
						// addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
						var msg = "本次总共城市类别数据"
								+responseArray.sumCityTypeSize
							    + "条，导入城市类别数据"
								+ responseArray.addCityTypeSize
								+ "条，覆盖城市类别数据"
								+ responseArray.coverCityTypeSize
								+ "条，失败城市类别数据"
								+ responseArray.errorCityTypeSize
								+"条"
						miser.showInfoMsg(msg);
						if (!Ext.isEmpty(responseArray.errorFilePath)) {
							// 新打开一个下载页面
							location.href = "../common/FileDownLoadAction.action?fileName="
									+ responseArray.errorFilePath;
						}

					},
					failure : function() {
						Ext.Msg.hide();
						Ext.MessageBox.show({
							title : '失败',
							msg : '导入失败',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				});
	} else {
		miser.showErrorMes('服务器异常');
	}
}


// 定义城市类别数据类型
Ext.define('Miser.model.bizbase.cityTypeEntityModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'provinceName',
		// 行政省份
		type : 'string'
	}, {
		name : 'cityName',
		// 行政城市
		type : 'string'
	}, {
		name : 'areaName',
		// 行政区县
		type : 'string'
	}, {
		name : 'provinceCode',
		// 行政省份
		type : 'string'
	}, {
		name : 'cityCode',
		// 行政城市
		type : 'string'
	}, {
		name : 'areaCode',
		// 行政区县
		type : 'string'
	}, {
		name : 'cityType',
		// 城市类别
		type : 'string'
	}, {
		name : 'remark',
		// 备注
		type : 'string'
	}, {
		name : 'createUserCode',
		// 创建人
		type : 'string'
	}, {
		name : 'createTime',
		// 创建时间
		type : 'date',
		dateFormat : 'time'
	}, {
		name : 'modifyUserCode',
		// 修改人
		type : 'string'
	}, {
		name : 'modifyTime',
		// 修改时间
		type : 'date',
		dateFormat : 'time'
	} ]
});

Ext.define('Miser.base.cityType.CityTypeForm', {
	extend : 'Ext.form.Panel',
	id : 'Miser_base_cityTypeUrban_cityTypeUrbanForm_id',
	title : bizbase.cityType.i18n('bizbase.cityType.queryCondition'),// 查询条件
	collapsible: true,
	frame: true,
	region : 'north',
	width: document.documentElement.clientWidth - 20,
	defaults: {
		labelWidth : 0.3,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
	fieldDefaults: {
		labelWidth : 100,
		labelAlign : 'right',
        margin : '5 0 5 0'
    },
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			layout: 'column',
			border: false,
			items: [{
				name : '',
				width : document.documentElement.clientWidth - 360,
				provinceLabelWidth : 80,
				cityLabelWidth : 80,
				areaLabelWidth : 80,
				cityLabel : bizbase.cityType
						.i18n('bizbase.cityType.cityName'),
				cityName : 'cityName',
				cityIsBlank : true,
				// 名称
				provinceLabel : bizbase.cityType
						.i18n('bizbase.cityType.provinceName'),
				provinceName : 'provinceName',
				provinceIsBlank : true,
				// 省名称
				areaLabel : bizbase.cityType
						.i18n('bizbase.cityType.areaName'),
				areaName : 'areaName',
				areaIsBlank : true,

				// 县名称
				type : 'P-C-C',
				xtype : 'linkregincombselector'
			},
			{
				name : 'cityType',
				fieldLabel : bizbase.cityType
						.i18n('bizbase.cityType.cityType'),
				width : 275,
				margin : '10 10 5 10',
				xtype : 'dataDictionarySelector',
				anyRecords : all,
				termsCode : 'CITYTYPE'
			}]
		}];
		me.buttons = [
				{
					text : bizbase.cityType.i18n('bizbase.cityType.query'),
					// text:bizbase.cityType.i18n('miser.common.query'),//查询
					handler : function() {
						if (me.getForm().isValid()) {
							me.up().getcityTypeUrbanGrid().getPagingToolbar()
									.moveFirst();
						}
					}
				}, {
					id : 'downbutton_id',
					text : bizbase.cityType.i18n('bizbase.cityType.export'),
					// text:
					// bizbase.cityType.i18n('bse.pricePriceUrban.downbutton'),//导出
					alias : 'widget.downloadbutton',
					glyph : 0xf019,
					disabled : false,
					cls : 'download-btn-item',
					handler : function() {
						down();
					}
				} ];
		me.callParent([ cfg ]);
	}
});

/**
 * 修改表单
 */
Ext.define('Miser.base.cityType.CityUpdateForm',{
					extend : 'Ext.form.Panel',
					header : false,
					frame : true,
					collapsible : true,
					width : 350,
					fieldDefaults : {
						labelWidth : 100,
						margin : '8 10 5 10'
					},
					defaultType : 'textfield',
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);

						me.items = [
								{
									name : 'id',
									width : 250,
									hidden:true
								},
								{
									name : 'provinceCode',
									xtype : 'textfield',
									hidden:true
								},
								{
									name : 'cityCode',
									xtype : 'textfield',
									hidden : true
								},
								{
									name : 'areaCode',
									xtype : 'textfield',
									hidden : true
								},
								{
									name : 'provinceName',
									width : 250,
									colspan : 2,
									xtype : 'textfield',
									allowBlank : false,
									fieldLabel : bizbase.cityType
											.i18n('bizbase.cityType.provinceName1'),
									beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
								},
								{
									name : 'cityName',
									width : 250,
									allowBlank : false,
									fieldLabel : bizbase.cityType
											.i18n('bizbase.cityType.cityName1'),
									flex : 1,
									xtype : 'textfield',
									beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
								},
								{
									fieldLabel : bizbase.cityType
											.i18n('bizbase.cityType.areaName1'),
									name : 'areaName',
									xtype : 'textfield',
									allowBlank : false,
									decimalPrecision : 2,
									minValue : 0,
									width : 250,
									beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ]
								},
								{
						        	fieldLabel:bizbase.cityType.i18n('bizbase.cityType.cityType'),
						        	name: 'cityType',
						        	xtype : 'dataDictionarySelector',
									termsCode : 'CITYTYPE',
						        	width: 250
						        },
								{
									name : 'remark',
									fieldLabel : bizbase.cityType
											.i18n('bizbase.cityType.remarks'),
									xtype : 'textarea'
								} ];
						me.callParent([ cfg ]);
					}
				});

//修改窗体
Ext.define('Miser.base.cityType.CityUpdateWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	parent : null,
	resizable : false,
	// 可以调整窗口的大小
	closeAction : 'hide',
	// 点击关闭是隐藏窗口
	cityTypeEntity : null,
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
			me.getCityUpdateForm().getForm().loadRecord(new Miser.model.bizbase.cityTypeEntityModel(me.cityTypeEntity));
			var provinceName = me.getCityUpdateForm().getForm().findField(
					'provinceName');
			var cityName = me.getCityUpdateForm().getForm().findField(
					'cityName');
			var areaName = me.getCityUpdateForm().getForm().findField(
					'areaName');
			 provinceName.setReadOnly(true);
			 cityName.setReadOnly(true);
			 areaName.setReadOnly(true);
		}
	},
	cityUpdateForm : null,
	getCityUpdateForm : function() {
		if (Ext.isEmpty(this.cityUpdateForm)) {
			this.cityUpdateForm = Ext
					.create('Miser.base.cityType.CityUpdateForm');
		}
		return this.cityUpdateForm;
	},
	submitDriver : function() {
		var me = this;
		var cityTypeEntity = new Miser.model.bizbase.cityTypeEntityModel;
		me.getCityUpdateForm().getForm().updateRecord(cityTypeEntity); // 将FORM中数据设置到MODEL里面
		var curData = cityTypeEntity.data;
		var params = {
			'cityTypeVo' : {
				'cityTypeEntity' : curData
			}
		}
		console.info(params);
		var successFun = function(json) {
			var message = json.message;
			miser.showInfoMsg(message); // 提示修改成功
			me.close();
			Ext.getCmp('cityGrid').getStore().reload(); // 成功之后重新查询刷新结果集
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('连接超时'); // 请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message); // 提示失败原因
			}
		};
		miser.requestJsonAjax(
				'../bizbase/cityTypeAction!updateCityTypeResource.action',
				params, successFun, failureFun); // 发送AJAX请求
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [ {
			text : bizbase.cityType.i18n('bizbase.cityType.save'),
			// 保存
			/* margin : '0 0 0 55', */
			handler : function() {
				me.submitDriver();
			}
		}, {
			text : bizbase.cityType.i18n('bizbase.cityType.cancel'),// '取消',
			// 取消
			handler : function() {
				miser.showQuestionMes('确认退出？',// 是否删除
				function(e) {
					if (e == 'yes') {
						me.close();
					}
				});
			}
		} ];
		me.items = [ me.getCityUpdateForm() ];
		me.callParent([ cfg ]);
	}
});
/**
 * 价格管理表格
 */
Ext
		.define(
				'Miser.base.cityType.CityTypeGrid',
				{
					extend : 'Ext.grid.Panel',
					frame : true,
					id : 'cityGrid',
					autoScroll : true,
					width : '300%',
					height : document.documentElement.clientHeight - 150,
					stripeRows : true,
					// 交替行效果
					region : 'center',
					emptyText : '查询结果为空',
					// emptyText:
					// bizbase.cityType.i18n('miser.common.resultisNull'),//查询结果为空
					// 查询结果为空
					viewConfig : {
						enableTextSelection : true
					},
					cityUpdateWindow : null,
					getCityUpdateWindow : function() {
						if (Ext.isEmpty(this.cityUpdateWindow)) {
							this.cityUpdateWindow = Ext
									.create('Miser.base.cityType.CityUpdateWindow');
						}
						return this.cityUpdateWindow;
					},
					updatePpfiItems : function() {
						var me = this;
						var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
						if (selections.length != 1) { // 判断选中了一条
							miser.showWoringMessage('请选择一条进行修改操作'); // 请选择一条进行修改操作！
							return; // 没有则提示并返回
						}
						var provinceName = selections[0].get('provinceName');
						var cityName = selections[0].get('cityName');
						var areaName = selections[0].get('areaName');
						var provinceCode=selections[0].get('provinceCode');
						var cityCode=selections[0].get('cityCode');
						var areaCode=selections[0].get('areaCode');
						var cityType=selections[0].get('cityType');
						var remark=selections[0].get('remark');
						
						var params = {
							'cityTypeVo' : {
								'cityTypeEntity' : {
									'provinceName' : provinceName,
									'cityName' : cityName,
									'areaName' : areaName,
									'provinceCode':provinceCode,
									'cityCode':cityCode,
									'areaCode':areaCode,
									'cityType':cityType,
									'remark':remark
								}
							}
						};
						console.info(params);
							var updateWindow = me.getCityUpdateWindow(); // 获得修改窗口
							updateWindow.cityTypeEntity = params.cityTypeVo.cityTypeEntity;
							console.info(params.cityTypeVo.cityTypeEntity);
							updateWindow.show(); // 显示修改窗口
					},
//					pagingToolbar : null,
//					getPagingToolbar : function() {
//						if (this.pagingToolbar == null) {
//							this.pagingToolbar = Ext.create(
//									'Ext.toolbar.Paging', {
//										store : this.store,
//										pageSize : 20
//									});
//						}
//						return this.pagingToolbar;
//					},
					selModel : Ext
							.create(
									'Ext.selection.CheckboxModel',
									{
										listeners : {
											selectionchange : function(sm,
													selections) {
												var length = selections.length;
												if (length == 1) {
													Ext
															.getCmp(
																	'miser_bizbase_cityType_updateCityType_id')
															.setDisabled(false);
												} else {
													Ext
															.getCmp(
																	'miser_bizbase_cityType_updateCityType_id')
															.setDisabled(true);
												}

											}
										}
									}),
					pagingToolbar : null,
					getPagingToolbar : function() {
						if (this.pagingToolbar == null) {
							this.pagingToolbar = Ext.create(
									'Ext.toolbar.Paging', {
										store : this.store,
										pageSize : 20
									});
						}
						return this.pagingToolbar;
					},
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
								me.columns = [
								        { 
								        	 dataIndex: 'provinceCode',
								             hidden:true
								        	
								        },
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.rownumberer'),
											width : 60,
											xtype : 'rownumberer',
											align : 'center'
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.provinceName'),
											dataIndex : 'provinceName',
											width : 80,
											align : 'center',
											flex : 1
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.cityName'),
											dataIndex : 'cityName',
											width : 80,
											align : 'center',
											flex : 1
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.areaName'),
											dataIndex : 'areaName',
											width : 80,
											align : 'center',
											flex : 1
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.cityType'),
											dataIndex : 'cityType',
											renderer : function(value) {
												return booleanTypeRender(value);
											},
											width : 80,
											align : 'center',
											flex : 1,
											renderer : function(value) {
												return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('CITYTYPE'), value);
											}
										},

										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.remarks'),
											dataIndex : 'remark',
											width : 80,
											align : 'center',
											flex : 1
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.modifyTime'),
											dataIndex : 'modifyTime',
											width : 100,
											align : 'center',
											flex : 1,
											xtype : 'datecolumn',
											format : 'Y-m-d H:i:s'
										},
										{
											text : bizbase.cityType
													.i18n('bizbase.cityType.modifyUserCode'),
											dataIndex : 'modifyUserCode',
											width : 80,
											align : 'center',
											flex : 1
										} ],
								me.tbar = [
										{
											id : 'miser_bizbase_cityType_updateCityType_id',
											text : bizbase.cityType
													.i18n('bizbase.cityType.cityTypeModify'),
											xtype : 'updatebutton',
											width : 130,
											disabled : true,
											handler : function() {
												me.updatePpfiItems();
											}
										},
										{
											xtype : 'uploadbutton',
											text : bizbase.cityType
													.i18n('bizbase.cityType.import'),
											width : 80,
											handler : function() {
												uploadT.excelWindow(null,imple);
											}
										},
										{
											alias : 'widget.downloadbutton',
											glyph : 0xf019,
											disabled : false,
											cls : 'download-btn-item',
											text : bizbase.cityType
													.i18n('bizbase.cityType.templateDownLoad'),
											title : '查询',
											width : 130,
											handler : function() {
												miser.requestExportAjax('/excelTemplate/cityType.xlsx');
											}
										} ];
						me.store = Ext.create(
								'Miser.base.cityType.CityTypeStore', {
									autoLoad : false
								});
						me.bbar
						me.bbar = me.getPagingToolbar();
						me.callParent([ cfg ]);

					}
				});

/**
 * 价格城市价格store
 */
Ext.define('Miser.base.cityType.CityTypeStore', {
	extend : 'Ext.data.Store',
	model : 'Miser.model.bizbase.cityTypeEntityModel',
	id : 'cityTypeStore',
	pageSize : 13,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../bizbase/cityTypeAction!queryCityTypeList.action',
		reader : {
			type : 'json',
			rootProperty : 'cityTypeVo.cityTypeList',
			totalProperty : 'totalCount' // 总个数
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext
					.getCmp('Miser_base_cityTypeUrban_cityTypeUrbanForm_id');
			if (queryForm != null) {
				var params = {
					'cityTypeVo.cityTypeEntity.provinceCode' : queryForm
							.getForm().findField('provinceName').getValue(),
					'cityTypeVo.cityTypeEntity.cityCode' : queryForm.getForm()
							.findField('cityName').getValue(),
					'cityTypeVo.cityTypeEntity.areaCode' : queryForm.getForm()
							.findField('areaName').getValue(),
					'cityTypeVo.cityTypeEntity.cityType' : queryForm.getForm()
							.findField('cityType').getValue()
				};
				Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});

Ext.onReady(function() {
	var queryForm = Ext.create("Miser.base.cityType.CityTypeForm");
	var cityTypeUrbanGrid = Ext.create("Miser.base.cityType.CityTypeGrid");
	var mainPanel=Ext.create('Ext.Viewport', {
		header : {
			titlePosition : 2,
			titleAlign : 'center'
		},
		border : "0 0 0 0",
		renderTo : Ext.getBody(),
		layout : {
			type : 'border'
		},
		getQueryForm : function() {
			return queryForm;
		},
		getcityTypeUrbanGrid : function() {
			return cityTypeUrbanGrid;
		},
		items : [ queryForm, cityTypeUrbanGrid ]
	});
	
	
	//设置窗口大小改变时的响应
	window.onresize = function() {
		//控制整个 panel 的宽度
		mainPanel.setWidth(document.documentElement.clientWidth);
		var items = queryForm.items;
		var targetObj = items.items[0].items.items[0];
		targetObj.setWidth(document.documentElement.clientWidth);
		document.getElementById(items.items[1].items.items[0].getEl().el
				.parent().id).style.width = (document.documentElement.clientWidth)
				+ 'px';
		//控制整个 查询表单 的宽度
		queryForm.setWidth(document.documentElement.clientWidth);

		//设置查询结果的宽度
		cityTypeUrbanGrid.setWidth(document.documentElement.clientWidth
				- 20);
	};

});
