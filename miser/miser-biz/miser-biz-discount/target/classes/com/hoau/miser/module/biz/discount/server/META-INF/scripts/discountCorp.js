//将时间转化为 2011-08-20 00:00:00 格式 
function dateFormat(value){ 
  if(null != value){ 
      return Ext.Date.format(new Date(value),'Y-m-d H:i:s'); 
  }else{ 
      return null; 
  } 
} 

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
function imple(outFileName,flag){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
	       url : '../discount/discountCorp!implExcel.action',  
	       method : 'post',
	       timeout:1800000,
	       params : {  
	    	   'discountCorpVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导入"+responseArray.sumSize+"条数据，新增待生效数据"+responseArray.addSize+"条，覆盖原待生效数据"+responseArray.coverSize+"条。"
	              
	              miser.showInfoMsg(msg);
	              
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '导入失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	}else{
		miser.showErrorMes('服务器异常');
	}
	
}

//下载Excel
function downExcel(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
        	params = {
        			'discountCorpVo.discountCorpEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                    'discountCorpVo.discountCorpEntity.orgCode': queryForm.getForm().findField('orgCode').getValue(),
                    'discountCorpVo.discountCorpEntity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
                    'discountCorpVo.discountCorpEntity.state': queryForm.getForm().findField('state').getValue()
        };
    }
	Ext.Ajax.request( {  
	       url : '../discount/discountCorp!exportExcel.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	             // var msg="本次导出共"+responseArray.count+"条数据！";
	             // miser.showInfoMsg(msg);
	              miser.requestExportAjax(responseArray.filePath);
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
}

function typeRender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if('Y' == value){
		value= '是';
	}else if('N' == value){
		value= '否';
	}
	return value;
}
/**
 * 网点折扣列表model
 */
Ext.define('Miser.model.DiscountCorpEntity', {
    extend: 'Ext.data.Model',
    fields: [
    {name: 'id',type: 'string'},
    {name: 'orgCode',type: 'string'},
    {name: 'transTypeCode',type: 'string'},
    {name: 'freightSectionCode',type: 'string'},
    {name: 'pickupSectionCode',type: 'string'},
    {name: 'addSectionCode',type: 'string'},
    {name: 'upstairsSectionCode',type: 'string'},
    {name: 'deliverySectionCode',type: 'string'},
    {name: 'insuranceRateSectionCode',type: 'string'},
    {name: 'insuranceSectionCode',type: 'string'},
    {name: 'paperSectionCode',type: 'string'},
    {name: 'smsSectionCode',type: 'string'},
    {name: 'fuelSectionCode',type: 'string'},
    {name: 'collectionRateSectionCode',type: 'string'},
    {name: 'collectionSectionCode',type: 'string'},
    {name: 'effectiveTime',type : 'date',dateFormat:'time'},
    {name: 'invalidTime',type : 'date',dateFormat:'time'},
    {name: 'remark',type: 'string'},
    {name: 'createDate',type : 'date',dateFormat:'time'},
    {name: 'createUser',type: 'string'},
    {name: 'modifyDate',type : 'date',dateFormat:'time'},
    {name: 'modifyUser',type : 'string'},
    {name: 'active',type: 'string'},
    {name: 'roadArea',type: 'string'},
    {name: 'bigRegion',type: 'string'},
    {name: 'division',type: 'string'},
    {name: 'orgCodeName',type: 'string'},
    
    {name: 'transTypeName',type: 'string'},
    {name: 'freightSectionName',type: 'string'},
    {name: 'pickupSectionName',type: 'string'},
    {name: 'deliverySectionName',type: 'string'},
    {name: 'insuranceRateSectionName',type: 'string'},
    {name: 'insuranceSectionName',type: 'string'},
    {name: 'paperSectionName',type: 'string'},
    {name: 'smsSectionName',type: 'string'},
    {name: 'fuelSectionName',type: 'string'},
    {name: 'collectionRateSectionName',type: 'string'},
    {name: 'collectionSectionName',type: 'string'},
    {name: 'addSectionName',type: 'string'},
    {name: 'upstairsSectionName',type: 'string'},
    {name: 'createUserName',type: 'string'},
    {name: 'modifyUserName',type: 'string'},
    {name: 'logistCode',type: 'string'},
    {name: 'state',type: 'string'}
    ]
});
/**
 * 查询表单
 */
Ext.define('Miser.view.discountCorp.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title : discount.discountCorp.i18n('discount.discountCorp.querycondition'),// '查询条件',
    collapsible: true,
    region: 'north',
    defaults: {
    	labelWidth: 0.4,
    	columnWidth: 0.6,
    	margin: '0 10 0 10',
		labelAlign : 'right'
    },
    constructor: function(config) {
    var me = this,
    cfg = Ext.apply({},config);
    me.items = [
				{
					layout : 'column',
					defaults : {
					labelWidth : 70,
					labelAlign : "right"
				},
    items : [{
        name: 'linkorg',
        labelWidth: 5,
        flex:1.42,	
        // 是否加载默认值（当前用户部门）
        loaddefault:true,          
        width:document.documentElement.clientWidth-10,
        loadParentOrg:true,
        //height:40,
        
        //事业部
        divisionLabel: discount.discountCorp.i18n('discount.division'),
        divisionName: 'division',
        divisionIsBlank:true,
        divisionLabelWidth:70,
        divisionWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 大区
        bigregionLabel: discount.discountCorp.i18n('discount.bigRegion'),
        bigregionName: 'bigRegion',
        bigregionIsBlank:true,
        bigregionLabelWidth:70,
        bigregionWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 路区
        roadareaLabel: discount.discountCorp.i18n('discount.roadArea'),
        roadareaName: 'roadArea',
        roadareaIsBlank:true,
        roadareaLabelWidth:70,
        roadareaWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 门店
        salesdepartmentLabel: discount.discountCorp.i18n('discount.orgCodeName'),
        salesdepartmentName: 'orgCode',
        salesdepartmentIsBlank:true,	       
        salesdepartmentLabelWidth:70,
        salesdepartmentWidth:275,
        margin:'5px 0px 5px 0px',
        
        
        type: 'D-B-R-S',								            
	    formid:'queryForm',
        xtype: 'linkorgcombselector'
	}]
	},
	{
		layout : 'column',
		margin: '-5 0 0 10',
		defaults : {
			labelWidth : 70,
			labelAlign : "right"
		},
		items : [ {
			name : 'transTypeCode',
			fieldLabel :discount.discountCorp.i18n('discount.discountCorp.transTypeCode') ,
			showAll:true,
			containsRoot:true,
			xtype : 'transtypecombselector',
			width:275,
			margin:'5px 0px 5px 0px'
		},{
			name : 'state',
			fieldLabel : '当前状态',
			xtype : 'statuscombselector',
			width:275,
			margin:'5px 0px 5px 0px'
		},{
			name: 'effectiveTime',
			fieldLabel: '有效时间',//'有效时间点'
			xtype : 'datetimefield',
			width:275,
			format : 'Y-m-d H:i:s',
			margin:'5px 0px 5px 0px'
		}]
	}
	],
    me.buttons = [{
        text: discount.discountCorp.i18n('discount.discountCorp.query'),//'查询'
        handler: function() {
        	var queryForm = Ext.getCmp('queryForm');
			
        		
			
            if (me.getForm().isValid()) {
                me.up().getDiscountCorpGrid().getPagingToolbar().moveFirst();;
            }
        }
    },
    {
        text: discount.discountCorp.i18n('discount.discountCorp.reset'),//'重置',
        handler: function() {
            me.getForm().reset();
        }
    }
    ];
    me.callParent([cfg]);
	}
});

/**
 * 网点折扣信息表格
 */
Ext.define('Miser.view.discountCorp.Grid', {
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
    discountCorpAddWindow: null,
    getDiscountCorpAddWindow: function() {
        if (this.discountCorpAddWindow == null) {
            this.discountCorpAddWindow = Ext.create('Miser.view.discount.discountCorpAddWindow');
            this.discountCorpAddWindow.parent = this; // 父元素
        }
        return this.discountCorpAddWindow;
    },
    discountCorpUpdateWindow: null,
    getDiscountCorpUpdateWindow: function() {
        if (this.discountCorpUpdateWindow == null) {
            this.discountCorpUpdateWindow = Ext.create('Miser.view.discount.discountCorpUpdateWindow');
            this.discountCorpUpdateWindow.parent = this; //父元素
        }
        return this.discountCorpUpdateWindow;
    },
    updateDiscountCorp : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		//判断数据是否失效
		for(var i=0;i<selections.length;i++){
        	if(selections[i].get('active')=='N'){
        		miser.showWoringMessage('你选择数据已失效，不能修改');
        		return; 
        	}
        	var nowDate = dateFormat(new Date());
        	var invalidTime = dateFormat(selections[i].get('invalidTime'));
        	if(nowDate>=invalidTime){
        		miser.showWoringMessage('你选择数据已失效，不能修改');
        		return; 
        	}
        }
		var id = selections[0].get('id');
		var params = {
				'discountCorpVo' :{
					'discountCorpEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getDiscountCorpUpdateWindow(); //获得修改窗口
			updateWindow.discountCorpEntity = json.discountCorpVo.discountCorpEntity;
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
		miser.requestJsonAjax('../discount/discountCorp!queryById.action', params, successFun, failureFun);
	},
	saveDiscountCorp : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length > 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条或者不选进行新增操作'); 
			return; //没有则提示并返回
		}
		var id = null;
		if(selections.length !=0){
			id = selections[0].get('id');
		}
		var params = {
				'discountCorpVo' :{
					'discountCorpEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var addWindow = me.getDiscountCorpAddWindow(); //获得修改窗口
			addWindow.discountCorpEntity = json.discountCorpVo.discountCorpEntity;
			addWindow.show(); //显示新增窗口
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes('请求超时'); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../discount/discountCorp!queryAddById.action', params, successFun, failureFun);
	},
	deleteDiscountCorp: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(discount.discountCorp.i18n('discount.discountCorp.oneselected')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objects='';
        for(var i=0;i<selections.length;i++){
        	var object="{"
        	object+="\"id\":\""+selections[i].get('id')+"\",\"state\":\""+selections[0].get('state')+"\",\"active\":\""+selections[0].get('active')+"\"";
        	object+="}"
        	if(i!=selections.length-1)
        	object+=","
        	objects+=object;
        }
        miser.showQuestionMes('删除',//是否删除
        function(e) {
            if (e == 'yes') { // 询问是否删除，是则发送请求
                var params = {
                		'discountCorpVo': {
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
                        miser.showErrorMes(discount.discountCorp.i18n('discount.discountCorp.timeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../discount/discountCorp!deleteDiscountCorp.action', params, successFun, failureFun);
            }
        });
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [
                      	{text : '序号',width : 60,xtype : 'rownumberer',align : 'center'},
				        {dataIndex: 'id',width: 100,hidden:true},
				        {dataIndex: 'division',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.division')},
				        {dataIndex: 'bigRegion',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.bigRegion')},
				        {dataIndex: 'roadArea',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.roadArea')},
				        {dataIndex: 'orgCodeName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.orgCodeName')},
				        {dataIndex: 'orgCode',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.orgCode'),hidden:true},
				        {dataIndex: 'logistCode',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.logistCode')},
				        {dataIndex: 'transTypeName', width: 100,text:discount.discountCorp.i18n('discount.discountCorp.transTypeCode')},
				        {dataIndex: 'freightSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.freightSectionCode')},
				        {dataIndex: 'addSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.addSectionCode')},
				        {dataIndex: 'pickupSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.pickupSectionCode')},
				        {dataIndex: 'deliverySectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.deliverySectionCode')},
				        {dataIndex: 'insuranceRateSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.insuranceRateSectionCode')},
				        {dataIndex: 'insuranceSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.insuranceSectionCode')},
				        {dataIndex: 'paperSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.paperSectionCode')},
				        {dataIndex: 'smsSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.smsSectionCode')},
				        {dataIndex: 'fuelSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.fuelSectionCode')},
				        {dataIndex: 'collectionRateSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.collectionRateSectionCode')},
				        {dataIndex: 'collectionSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.collectionSectionCode')},
				        {dataIndex: 'upstairsSectionName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.upstairsSectionCode')},
				        {dataIndex: 'effectiveTime',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.effectiveTime'),renderer: function(value) {return dateRender(value,'Y-m-d H:i:s');}},
				        {dataIndex: 'invalidTime',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.invalidTime'),renderer: function(value) {return dateRender(value,'Y-m-d H:i:s');}},
				        {dataIndex: 'state',width: 100,text:'状态',
				        	   renderer:function(v,m,record,ri,ci){  
				        		   var nowDate = new Date();
				        		   var nowTime = nowDate.getTime();
					        	   var effectiveTime= new Date(record.get('effectiveTime')).getTime();  
					        	   var invalidTime= new Date(record.get('invalidTime')).getTime();
					        	   var active= record.get('active'); 
					        	   if(nowTime > invalidTime && active=='Y' && invalidTime !='' && invalidTime !=null){
					        		   return '已失效';
					        	   }else if(effectiveTime >nowTime && active=='Y'){
					        		   return '待生效';
					        	   }else if(active=='N'){
					        		   return '已废弃';
					        	   }else{
					        		   return '生效中';
					        	   }
					    }},
				        {dataIndex: 'remark',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.remark')},
				        {dataIndex: 'createDate',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.createDate'),renderer: function(value) {return dateRender(value,'Y-m-d');}},
				        {dataIndex: 'createUser',width: 100,hidden:true},
				        {dataIndex: 'createUserName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.createUser'),
				        	   renderer:function(v,m,record,ri,ci){  
				        	   var aResult= record.get('createUser');  
				        	   return aResult+v;
				        }},
				        {dataIndex: 'modifyDate',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.modifyDate'),renderer: function(value) {return dateRender(value,'Y-m-d');}},
				        {dataIndex: 'modifyUser',width: 100,hidden:true},
				        {dataIndex: 'modifyUserName',width: 100,text:discount.discountCorp.i18n('discount.discountCorp.modifyUser'),
				        	   renderer:function(v,m,record,ri,ci){  
				        	   var aResult= record.get('modifyUser');  
				        	   return aResult+v;
				        }},
				        {dataIndex: 'active',renderer: function(value) {return typeRender(value);},width: 100,text:discount.discountCorp.i18n('discount.discountCorp.active')}
				     ],
        me.store = Ext.create('Miser.store.discountCorpStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [{
        	text:discount.discountCorp.i18n('discount.discountCorp.add'),// '新增',
            xtype: 'addbutton',// 新增
            width:80,
            handler: function() {
               // me.getDiscountCorpAddWindow().show();
            	me.saveDiscountCorp();
            }
        },
        '-', {
            text:discount.discountCorp.i18n('discount.discountCorp.update'),// '修改',
            xtype: 'updatebutton',
            width: 80,
            handler: function() {
                me.updateDiscountCorp();
            }
        },
        '-', {
        	id : 'miser_biz_discount_discountCorp_del_id',
            text:discount.discountCorp.i18n('discount.discountCorp.invalid'),// '作废',
            xtype: 'deletebutton',
            disabled : true,
            // 作废
            width: 80,
            handler: function() {
                me.deleteDiscountCorp();
            }
        },{
    			xtype: 'uploadbutton',
                text: '导入',
                width: 80,
                handler:function(){
                	uploadT.excelWindow(null,imple);
                }
        }
        ,{
			xtype: 'downloadbutton',
            text: '导入模板下载',
            width: 130,
            handler:function(){
            	//downExcel();
            	miser.requestExportAjax('/excelTemplate/discountCorp.xlsx');
            }
    }];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                Ext.getCmp('miser_biz_discount_discountCorp_del_id').setDisabled(selections.length == 0);
                //只有待生效和生效中的数据，在网点折扣管理界面选中某记录，作废按钮才能可用，否则灰色禁用
                for(var i=0;i<selections.length;i++){
                	if(selections[i].get('active')=='N'){
                		Ext.getCmp('miser_biz_discount_discountCorp_del_id').setDisabled(true);
                		break;
                	}
                	var nowDate = dateFormat(new Date());
                	var effectiveTime = dateFormat(selections[i].get('effectiveTime'));
                	var invalidTime = dateFormat(selections[i].get('invalidTime'));
                	if(nowDate>=invalidTime &&　selections[i].get('active')=='Y'){
                		Ext.getCmp('miser_biz_discount_discountCorp_del_id').setDisabled(true);
                		break;
                	}
                }
            }
        }
    }),
        me.callParent([cfg]);
    }
});

/**
 * 增加表单
 */
Ext.define('Miser.view.discount.discountCorpAddForm', {
	extend : 'Ext.form.Panel',
	id:'discountCorpAddForm',
	frame : true,
	region : 'north',
	autoDestroy:true,
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin: '0 10 0 10',
		labelAlign : 'right'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [
					{
						layout : 'hbox',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [
						{
							name : 'id',
							fieldLabel : 'id',
							xtype : 'textfield',
							hidden : true
						 },
						 {
							    name: 'linkorg',
							    labelWidth: 5,

					            flex:1.42,	
					            // 是否加载默认值（当前用户部门）
					            loaddefault:false,          
					            width:1100,  
					            
					            //事业部
					            divisionLabel: discount.discountCorp.i18n('discount.division'),
					            divisionName: 'division',
					            divisionIsBlank:false,
					            divisionLabelWidth:80,
					            margin:'5px 0px 5px 0px',
					            divisionWidth:275,
					            
					            // 大区
					            bigregionLabel: discount.discountCorp.i18n('discount.bigRegion'),
					            bigregionName: 'bigRegion',
					            bigregionIsBlank:false,
					            bigregionLabelWidth:80,
					            bigregionWidth:275,
					            margin:'5px 0px 5px 0px',
					            
					            // 路区
					            roadareaLabel: discount.discountCorp.i18n('discount.roadArea'),
					            roadareaName: 'roadArea',
					            roadareaIsBlank:false,
					            roadareaLabelWidth:80,
					            roadareaWidth:275,
					            margin:'5px 0px 5px 0px',
					            
					            
					            // 门店
					            salesdepartmentLabel: discount.discountCorp.i18n('discount.orgCodeName'),
					            salesdepartmentName: 'orgCode',
					            salesdepartmentIsBlank:false,	       
					            salesdepartmentLabelWidth:80,
					            salesdepartmentWidth:275,
					            margin:'5px 0px 5px 0px',
							    
							    
							    type: 'D-B-R-S',
							    formid:'discountCorpAddForm',
							    loadParentOrg:true,
							    xtype: 'linkorgcombselector'
							}
						]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
			    			name: 'transTypeCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.transTypeCode'),
			    			showAll:false,
			    			containsRoot:false,
			    			xtype : 'transtypecombselector',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'freightSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.freightSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FREIGHT',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'addSectionCode',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.addSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'EXTRA_FEE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'fuelSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.fuelSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FUEL',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'pickupSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.pickupSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PICKUP',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'deliverySectionCode',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.deliverySectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'DELIVERY',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceRateSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.insuranceRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE_RATE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.insuranceSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'collectionRateSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.collectionRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECT_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.collectionSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECTION_RATE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'paperSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.paperSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PAPER',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'smsSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.smsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'SMS',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'upstairsSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.upstairsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'UPSTAIRS',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'effectiveTime',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.effectiveTime'),
							xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'invalidTime',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.invalidTime'),
			    			xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: '',
			    			fieldLabel: '',
			    			allowBlank:false
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.remark'),//'备注',
							name : 'remark',
							width : document.documentElement.clientWidth - 25,
							margin:'5px 0px 5px 0px',
							xtype : 'textarea'
						} ]
					}];
        me.callParent([cfg]);
    }
});


/**
 * 修改表单
 */
Ext.define('Miser.view.discount.discountCorpUpdateForm', {
	extend : 'Ext.form.Panel',
	id:'discountCorpUpdateForm',
	frame : true,
	region : 'north',
	autoDestroy:true,
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin: '0 10 0 10',
		labelAlign : 'right'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [
					{
						layout : 'hbox',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [
						{
							name : 'id',
							fieldLabel : 'id',
							xtype : 'textfield',
							hidden : true
						 },
						 {
							    name: 'linkorg',
							    labelWidth: 5,

					            flex:1.42,	
					            // 是否加载默认值（当前用户部门）
					            loaddefault:false,          
					            width:1100,  
					            
					            //事业部
					            divisionLabel: discount.discountCorp.i18n('discount.division'),
					            divisionName: 'division',
					            divisionIsBlank:false,
					            divisionLabelWidth:80,
					            margin:'5px 0px 5px 0px',
					            divisionWidth:275,
					            
					            // 大区
					            bigregionLabel: discount.discountCorp.i18n('discount.bigRegion'),
					            bigregionName: 'bigRegion',
					            bigregionIsBlank:false,
					            bigregionLabelWidth:80,
					            bigregionWidth:275,
					            margin:'5px 0px 5px 0px',
					            
					            // 路区
					            roadareaLabel: discount.discountCorp.i18n('discount.roadArea'),
					            roadareaName: 'roadArea',
					            roadareaIsBlank:false,
					            roadareaLabelWidth:80,
					            roadareaWidth:275,
					            margin:'5px 0px 5px 0px',
					            
					            
					            // 门店
					            salesdepartmentLabel: discount.discountCorp.i18n('discount.orgCodeName'),
					            salesdepartmentName: 'orgCode',
					            salesdepartmentIsBlank:false,	       
					            salesdepartmentLabelWidth:80,
					            salesdepartmentWidth:275,
					            margin:'5px 0px 5px 0px',
							    
							    
							    type: 'D-B-R-S',
							    formid:'discountCorpAddForm',
							    loadParentOrg:true,
							    xtype: 'linkorgcombselector'
							}
						]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
			    			name: 'transTypeCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.transTypeCode'),
			    			showAll:false,
			    			containsRoot:false,
			    			xtype : 'transtypecombselector',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'freightSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.freightSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FREIGHT',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'addSectionCode',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.addSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'EXTRA_FEE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'fuelSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.fuelSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FUEL',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'pickupSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.pickupSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PICKUP',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'deliverySectionCode',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.deliverySectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'DELIVERY',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceRateSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.insuranceRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE_RATE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.insuranceSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'collectionRateSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.collectionRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECT_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.collectionSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECTION_RATE',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'paperSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.paperSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PAPER',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'smsSectionCode',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.smsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'SMS',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							name : 'upstairsSectionCode',
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.upstairsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'UPSTAIRS',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'effectiveTime',
							fieldLabel: discount.discountCorp.i18n('discount.discountCorp.effectiveTime'),
							xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'invalidTime',
			    			fieldLabel: discount.discountCorp.i18n('discount.discountCorp.invalidTime'),
			    			xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			width:275,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: '',
			    			fieldLabel: '',
			    			allowBlank:false
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 80,
							labelAlign : "right"
						},
						items : [ {
							fieldLabel : discount.discountCorp.i18n('discount.discountCorp.remark'),//'备注',
							name : 'remark',
							width : document.documentElement.clientWidth - 25,
							margin:'5px 0px 5px 0px',
							xtype : 'textarea'
						} ]
					}];
        me.callParent([cfg]);
    }
});

//增加窗体
Ext.define('Miser.view.discount.discountCorpAddWindow', {
    extend: 'Ext.window.Window',
    id:'discountCorpAddWindow',
    title: '新增网点折扣',
    closable: true,
    parent: null,// 父元素
    modal: true,
    resizable: false,// 可以调整窗口的大小
    closeAction: 'hide',// 点击关闭是隐藏窗口
    autoDestroy:true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    listeners: {
        beforehide: function(me) { // 隐藏WINDOW的时候清除数据
            me.getDiscountCorpAddForm().getForm().reset(); // 表格重置
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getDiscountCorpAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getDiscountCorpAddForm().getForm().loadRecord(new Miser.model.DiscountCorpEntity(me.discountCorpEntity));
			//这块是处理下拉框赋值的问题
			//所属事业部
			var division = me.getDiscountCorpAddForm().getForm().findField('division');
			var divisionRecord=Ext.data.Record({
				displayField :  me.discountCorpEntity.division
				}
			);
			division.getStore().add(divisionRecord);
			division.setValue(me.discountCorpEntity.division);
			//所属大区
			var bigRegion = me.getDiscountCorpAddForm().getForm().findField('bigRegion');
			var bigRegionRecord=Ext.data.Record({
				displayField :  me.discountCorpEntity.bigRegion
				}
			);
			bigRegion.getStore().add(bigRegionRecord);
			bigRegion.setValue(me.discountCorpEntity.bigRegion);
			//所属路区
			var roadArea = me.getDiscountCorpAddForm().getForm().findField('roadArea');
			var roadAreaRecord=Ext.data.Record({
				displayField :  me.discountCorpEntity.roadArea
				}
			);
			roadArea.getStore().add(roadAreaRecord);
			roadArea.setValue(me.discountCorpEntity.roadArea);
			//所属门店
			var orgCode = me.getDiscountCorpAddForm().getForm().findField('orgCode');
			var orgCodeRecord=Ext.data.Record({
				name :  me.discountCorpEntity.orgCodeName,
				code : me.discountCorpEntity.orgCode
				}
			);
			orgCode.getStore().add(orgCodeRecord);
			orgCode.setValue(me.discountCorpEntity.orgCode);
			//orgCode.disable();
			
			//运输类型
			var transTypeCode = me.getDiscountCorpAddForm().getForm().findField('transTypeCode');
			var transTypeCodeRecord=Ext.data.Record({
				code :  me.discountCorpEntity.transTypeCode,
				name :  me.discountCorpEntity.transTypeName
				}
			);
			transTypeCode.getStore().add(transTypeCodeRecord);
			transTypeCode.setValue(me.discountCorpEntity.transTypeCode);
        }
    },
    discountCorpAddForm: null,
    getDiscountCorpAddForm: function() {
        if (Ext.isEmpty(this.discountCorpAddForm)) {
            this.discountCorpAddForm = Ext.create('Miser.view.discount.discountCorpAddForm');
        }
        return this.discountCorpAddForm;
    },
    submitDiscountCorpAddForm: function(isConfirm) {
        var me = this;
        if (me.getDiscountCorpAddForm().getForm().isValid()) { // 校验form是否通过校验
        	Ext.Msg.wait('处理中，请稍后...', '提示');
            var discountCorpEntity = new Miser.model.DiscountCorpEntity;
            me.getDiscountCorpAddForm().getForm().updateRecord(discountCorpEntity); // 将FORM中数据设置到MODEL里面
            var curData=discountCorpEntity.data;
            curData.id='';
            var params = {
                'discountCorpVo': {
                    'discountCorpEntity': curData,
                    'isConfirm':isConfirm
                }
            }
            var successFun = function(json) {
                var message = json.message;
                if(message=='添加成功'){
                	miser.showInfoMsg(message); // 提示新增成功
                	me.close();
                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
                }else if (message==discount.discountCorp.i18n('discount.discountCorp.isConfirm')){
                	miser.showQuestionMes(message,//是否删除
                            function(e) {
                                if (e == 'yes') { // 询问是否删除，是则发送请求
                                	 me.submitDiscountCorpAddForm("1");
                                	 me.close();
                                }
                     });
                }else{
                	miser.showInfoMsg(message);
				}
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes(discount.discountCorp.i18n('miser.discount.timeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('../discount/discountCorp!addDiscountCorp.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: discount.discountCorp.i18n('miser.common.save'),//'保存'
            handler: function() {me.submitDiscountCorpAddForm("0");}
        },
        {
            text:discount.discountCorp.i18n('miser.common.reset'),// '重置'
            handler: function() {me.getDiscountCorpAddForm().reset();}
        },
        {
            text: discount.discountCorp.i18n('miser.common.cancel'),// '取消'
            handler: function() {me.close();}
        }];
        me.items = [me.getDiscountCorpAddForm()];
        me.callParent([cfg]);
    }
});

/**
 * 修改网点折扣窗口
 */
	Ext.define('Miser.view.discount.discountCorpUpdateWindow',{
		extend : 'Ext.window.Window',
		id:'discountCorpUpdateWindow',
		title : '修改网点折扣',//修改
		closable : true,
		parent : null, // 父元素
		modal : true,
		resizable : false, // 可以调整窗口的大小
		closeAction : 'hide', // 点击关闭是隐藏窗口
		autoDestroy:true,
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		discountCorpEntity: null,
		listeners : {
			beforehide : function(me) { // 隐藏WINDOW的时候清除数据
				me.getDiscountCorpUpdateForm().getForm().reset(); // 表格重置
			},
			beforeshow : function(me) { // 显示WINDOW的时候清除数据
				var fielsds = me.getDiscountCorpUpdateForm().getForm().getFields();
				if (!Ext.isEmpty(fielsds)) {
					fielsds.each(function(item, index, length) {
						item.clearInvalid();
						item.unsetActiveError();
					});
				}
				fielsds = null;
				me.getDiscountCorpUpdateForm().getForm().loadRecord(new Miser.model.DiscountCorpEntity(me.discountCorpEntity));
				//这块是处理下拉框赋值的问题
				//所属事业部
				var division = me.getDiscountCorpUpdateForm().getForm().findField('division');
				var divisionRecord=Ext.data.Record({
					displayField :  me.discountCorpEntity.division
					}
				);
				division.getStore().add(divisionRecord);
				division.setValue(me.discountCorpEntity.division);
				division.disable();
				//所属大区
				var bigRegion = me.getDiscountCorpUpdateForm().getForm().findField('bigRegion');
				var bigRegionRecord=Ext.data.Record({
					displayField :  me.discountCorpEntity.bigRegion
					}
				);
				bigRegion.getStore().add(bigRegionRecord);
				bigRegion.setValue(me.discountCorpEntity.bigRegion);
				bigRegion.disable();
				//所属路区
				var roadArea = me.getDiscountCorpUpdateForm().getForm().findField('roadArea');
				var roadAreaRecord=Ext.data.Record({
					displayField :  me.discountCorpEntity.roadArea
					}
				);
				roadArea.getStore().add(roadAreaRecord);
				roadArea.setValue(me.discountCorpEntity.roadArea);
				roadArea.disable();
				//所属门店
				var orgCode = me.getDiscountCorpUpdateForm().getForm().findField('orgCode');
				var orgCodeRecord=Ext.data.Record({
					name :  me.discountCorpEntity.orgCodeName,
					code : me.discountCorpEntity.orgCode
					}
				);
				orgCode.getStore().add(orgCodeRecord);
				orgCode.setValue(me.discountCorpEntity.orgCode);
				//orgCode.disable();
				
				//运输类型
				var transTypeCode = me.getDiscountCorpUpdateForm().getForm().findField('transTypeCode');
				var transTypeCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.transTypeCode,
					name :  me.discountCorpEntity.transTypeName
					}
				);
				transTypeCode.getStore().add(transTypeCodeRecord);
				transTypeCode.setValue(me.discountCorpEntity.transTypeCode);
				transTypeCode.setReadOnly(true);
				
				//纯运费分段
				var freightSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('freightSectionCode');
				var freightSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.freightSectionCode,
					name :  me.discountCorpEntity.freightSectionName
					}
				);
				freightSectionCode.getStore().add(freightSectionCodeRecord);
				freightSectionCode.setValue(me.discountCorpEntity.freightSectionCode);
				
				//提货费分段
				var pickupSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('pickupSectionCode');
				var pickupSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.pickupSectionCode,
					name :  me.discountCorpEntity.pickupSectionName
					}
				);
				pickupSectionCode.getStore().add(pickupSectionCodeRecord);
				pickupSectionCode.setValue(me.discountCorpEntity.pickupSectionCode);
				
				//送货费分段
				var deliverySectionCode = me.getDiscountCorpUpdateForm().getForm().findField('deliverySectionCode');
				var deliverySectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.deliverySectionCode,
					name :  me.discountCorpEntity.deliverySectionName
					}
				);
				deliverySectionCode.getStore().add(deliverySectionCodeRecord);
				deliverySectionCode.setValue(me.discountCorpEntity.deliverySectionCode);
				
				//保价费率分段
				var insuranceRateSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('insuranceRateSectionCode');
				var insuranceRateSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.insuranceRateSectionCode,
					name :  me.discountCorpEntity.insuranceRateSectionName
					}
				);
				insuranceRateSectionCode.getStore().add(insuranceRateSectionCodeRecord);
				insuranceRateSectionCode.setValue(me.discountCorpEntity.insuranceRateSectionCode);
				
				//保价费分段
				var insuranceSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('insuranceSectionCode');
				var insuranceSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.insuranceSectionCode,
					name :  me.discountCorpEntity.insuranceSectionName
					}
				);
				insuranceSectionCode.getStore().add(insuranceSectionCodeRecord);
				insuranceSectionCode.setValue(me.discountCorpEntity.insuranceSectionCode);
				
				//工本费分段
				var paperSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('paperSectionCode');
				var paperSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.paperSectionCode,
					name :  me.discountCorpEntity.paperSectionName
					}
				);
				paperSectionCode.getStore().add(paperSectionCodeRecord);
				paperSectionCode.setValue(me.discountCorpEntity.paperSectionCode);
				
				//信息费分段
				var smsSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('smsSectionCode');
				var smsSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.smsSectionCode,
					name :  me.discountCorpEntity.smsSectionName
					}
				);
				smsSectionCode.getStore().add(smsSectionCodeRecord);
				smsSectionCode.setValue(me.discountCorpEntity.smsSectionCode);
				
				//燃油费分段
				var fuelSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('fuelSectionCode');
				var fuelSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.fuelSectionCode,
					name :  me.discountCorpEntity.fuelSectionName
					}
				);
				fuelSectionCode.getStore().add(fuelSectionCodeRecord);
				fuelSectionCode.setValue(me.discountCorpEntity.fuelSectionCode);
				
				//代收手续费率分段
				var collectionRateSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('collectionRateSectionCode');
				var collectionRateSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.collectionRateSectionCode,
					name :  me.discountCorpEntity.collectionRateSectionName
					}
				);
				collectionRateSectionCode.getStore().add(collectionRateSectionCodeRecord);
				collectionRateSectionCode.setValue(me.discountCorpEntity.collectionRateSectionCode);
				
				//代收手续费分段
				var collectionSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('collectionSectionCode');
				var collectionSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.collectionSectionCode,
					name :  me.discountCorpEntity.collectionSectionName
					}
				);
				collectionSectionCode.getStore().add(collectionSectionCodeRecord);
				collectionSectionCode.setValue(me.discountCorpEntity.collectionSectionCode);
				
				//上楼费分段
				var upstairsSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('upstairsSectionCode');
				var upstairsSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.upstairsSectionCode,
					name :  me.discountCorpEntity.upstairsSectionName
					}
				);
				upstairsSectionCode.getStore().add(upstairsSectionCodeRecord);
				upstairsSectionCode.setValue(me.discountCorpEntity.upstairsSectionCode);
				
				//附件费分段
				var addSectionCode = me.getDiscountCorpUpdateForm().getForm().findField('addSectionCode');
				var addSectionCodeRecord=Ext.data.Record({
					code :  me.discountCorpEntity.addSectionCode,
					name :  me.discountCorpEntity.addSectionName
					}
				);
				addSectionCode.getStore().add(addSectionCodeRecord);
				addSectionCode.setValue(me.discountCorpEntity.addSectionCode);
				
				//判断生效时间能否修改
				var nowDate = dateFormat(new Date());
            	var effectiveTime = dateFormat(me.discountCorpEntity.effectiveTime);
            	var invalidTime = dateFormat(me.discountCorpEntity.invalidTime);
            	if(effectiveTime < nowDate &&　invalidTime　>= nowDate){
            		var effectiveTime = me.getDiscountCorpUpdateForm().getForm().findField('effectiveTime');
    				effectiveTime.setReadOnly(true);
            	}
			}
		},
		discountCorpUpdateForm: null,
	    getDiscountCorpUpdateForm: function() {
	        if (Ext.isEmpty(this.discountCorpUpdateForm)) {
	            this.discountCorpUpdateForm = Ext.create('Miser.view.discount.discountCorpUpdateForm');
	        }
	        return this.discountCorpUpdateForm;
	    },
		submitDriver:function(isConfirm){
			var me = this;
			if (me.getDiscountCorpUpdateForm().getForm().isValid()) { //校验form是否通过校验
				Ext.Msg.wait('处理中，请稍后...', '提示');
				var discountCorpEntity = new Miser.model.DiscountCorpEntity;
				var id = me.getDiscountCorpUpdateForm().getForm().findField('id').rawValue;
				me.getDiscountCorpUpdateForm().getForm().updateRecord(discountCorpEntity); //将FORM中数据设置到MODEL里面
				var curData = discountCorpEntity.data;
				curData.id = id;
	            var params = {
	                'discountCorpVo': {
	                    'discountCorpEntity': curData,
	                    'isConfirm':isConfirm
	                }
	            }
				
				var successFun = function (json) {
					var message = json.message;
					if(message=='更新成功'){
						miser.showInfoMsg(message); //提示修改成功
						me.close();
						me.parent.getStore().load(); //成功之后重新查询刷新结果集
					}else if (message==discount.discountCorp.i18n('discount.discountCorp.isConfirm')){
						miser.showQuestionMes(message,//是否删除
	                            function(e) {
	                                if (e == 'yes') { // 询问是否删除，是则发送请求
	                                	 me.submitDriver("1");
	                                	 me.close();
	                                }
	                     });
					}else{
						miser.showInfoMsg(message);
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
				miser.requestJsonAjax('../discount/discountCorp!updateDiscountCorp.action', params, successFun, failureFun); //发送AJAX请求
			}
		},
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.fbar = [{
				text :discount.discountCorp.i18n('miser.common.save'), //保存
				handler : function() {
					 me.submitDriver("0");
				}
			}, 
//			{
//				text :discount.discountCorp.i18n('miser.common.reset'), //重置
//				handler : function() {
//					 me.getDiscountCorpUpdateForm().reset();
//					 me.getDiscountCorpUpdateForm().getForm().loadRecord(
//					 new Miser.model.RoleEntity({
//						 'code' : me.roleEntity.code
//					 }));
//				}
//			},
			{
				text :discount.discountCorp.i18n('miser.common.cancel'), //取消
				handler : function() {
					me.close();
				}
			} ];
			me.items = [ me.getDiscountCorpUpdateForm() ];
			me.callParent([ cfg ]);
		}
	});
/**
 * 网点折扣列表store
 */
Ext.define('Miser.store.discountCorpStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.DiscountCorpEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../discount/discountCorp!queryListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'discountCorpVo.discountCorpList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'discountCorpVo.discountCorpEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                    'discountCorpVo.discountCorpEntity.orgCode': queryForm.getForm().findField('orgCode').getValue(),
                    'discountCorpVo.discountCorpEntity.roadArea': queryForm.getForm().findField('roadArea').getValue(),
                    'discountCorpVo.discountCorpEntity.bigRegion': queryForm.getForm().findField('bigRegion').getValue(),
                    'discountCorpVo.discountCorpEntity.division': queryForm.getForm().findField('division').getValue(),
                    'discountCorpVo.discountCorpEntity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
                    'discountCorpVo.discountCorpEntity.state': queryForm.getForm().findField('state').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
         
    	
    }
    
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.view.discountCorp.QueryForm'); //查询FORM
    var discountCorpGrid = Ext.create('Miser.view.discountCorp.Grid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getDiscountCorpGrid: function() {
            return discountCorpGrid;
        },
        items: [queryForm, discountCorpGrid]
    });
});