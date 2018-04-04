discount.discountCustomer.discountPriorityType = 'CUSTOMER_DISCOUNT_PRIORITY_LEVEL';
discount.discountCustomer.isActive = 'IS_ACTIVE';
//将时间转化为 2011-08-20 00:00:00 格式 
function dateFormat(value){ 
  if(null != value){ 
      return Ext.Date.format(new Date(value),'Y-m-d H:i:s'); 
  }else{ 
      return null; 
  } 
} 

function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
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
	       url : '../discount/discountCustomer!implExcel.action',  
	       method : 'post',  
	       timeout:1800000,
	       params : {  
	    	   'discountCustomerVo.filePath' : outFileName  
	          },  
	          success : function(response, options) {  
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	              var msg="本次导入"+responseArray.sumSize+"条数据，新增待生效数据"+responseArray.addSize+"条，覆盖原待生效数据"+responseArray.coverSize+"条。"
	               if(!Ext.isEmpty(responseArray.error))
	              {
	              Ext.MessageBox.show({title: '失败',msg: '导入失败:<br/>' + responseArray.error + ";<br/>请修改excel重新上传.",buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});
	              }
	              else
	              {
	              	miser.showInfoMsg(msg);
	              }	              
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
//导出downExcelFor
function downExcelFor(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
    	var transTypeCode=queryForm.getForm().findField('transTypeCode').getValue();
    	var customerCode=queryForm.getForm().findField('customerCode').getValue();
    	var customerName=queryForm.getForm().findField('customerName').rawValue;
    	var roadArea=queryForm.getForm().findField('roadArea').getValue();
    	var bigRegion=queryForm.getForm().findField('bigRegion').getValue();
    	var division=queryForm.getForm().findField('division').getValue();
    	var orgCode=queryForm.getForm().findField('orgCode').getValue();
    	var effectiveTime=queryForm.getForm().findField('effectiveTime').getValue();
    	var state=queryForm.getForm().findField('state').getValue();
        	params = {
        			'discountCustomerVo.discountCustomerEntity.transTypeCode':transTypeCode ,
                	'discountCustomerVo.discountCustomerEntity.customerCode': customerCode,
                	'discountCustomerVo.discountCustomerEntity.customerName': customerName,
                    'discountCustomerVo.discountCustomerEntity.roadArea': roadArea,
                    'discountCustomerVo.discountCustomerEntity.bigRegion': bigRegion,
                    'discountCustomerVo.discountCustomerEntity.division': division,
                    'discountCustomerVo.discountCustomerEntity.orgCode': orgCode,
                    'discountCustomerVo.discountCustomerEntity.effectiveTime': effectiveTime,
                    'discountCustomerVo.discountCustomerEntity.state': state
        };
    }
//    if(Ext.isEmpty(transTypeCode)&&Ext.isEmpty(customerCode)
//    &&Ext.isEmpty(customerName)&&Ext.isEmpty(roadArea)&&Ext.isEmpty(bigRegion)
//    &&Ext.isEmpty(division)&&Ext.isEmpty(orgCode)&&Ext.isEmpty(effectiveTime)&&Ext.isEmpty(state)){
//    	miser.showInfoMsg("请输入查询条件");
//    	return false;
//    }
    Ext.Ajax.setTimeout(600000);
	Ext.Ajax.request( {
	       url : '../discount/discountCustomer!exportExcelDiscountCustomer.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {  
	             //隐藏进度条   
	              Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              //addSize:增加条数,coverSize:覆盖条数,totalCount:总共条数,filePath:错误的信息的文件地址
	              if(undefined!=responseArray.count && responseArray.count != 0){
	                  var msg="本次导出共"+responseArray.count+"条数据！";
	            	  miser.showInfoMsg(msg);
	              }else{
	            	  miser.showInfoMsg(responseArray.error);
	            	  return;
	              }
	               if(undefined!=responseArray.filePath){
	                  miser.requestExportAjax(responseArray.filePath);
	               }
	             },
	        failure : function() {
	            Ext.Msg.hide();
	            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});
	            //设置
	        }
	});
	Ext.Ajax.setTimeout(60000);
}
//下载Excel
function downExcel(){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	var queryForm = Ext.getCmp('queryForm');
    if (queryForm != null) {
        	params = {
        			'discountCustomerVo.discountCustomerEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                	'discountCustomerVo.discountCustomerEntity.customerCode': queryForm.getForm().findField('customerCode').getValue(),
                	'discountCustomerVo.discountCustomerEntity.customerName': queryForm.getForm().findField('customerName').rawValue,
                    'discountCustomerVo.discountCustomerEntity.orgCode': queryForm.getForm().findField('orgCode').getValue(),
                    'discountCustomerVo.discountCustomerEntity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
                    'discountCustomerVo.discountCustomerEntity.state': queryForm.getForm().findField('state').getValue()
        };
    }
	Ext.Ajax.request( {
	       url : '../discount/discountCustomer!exportExcel.action',  
	       method : 'post',  
	       params : params,  
	          success : function(response, options) {
	             //隐藏进度条   
	               Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText); 
	              miser.requestExportAjax(responseArray);
	             },
	        failure : function() { 
	            Ext.Msg.hide();  
	            Ext.MessageBox.show({title: '失败',msg: '下载失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
	        }
	    });  
	
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

//折扣优先类型下拉框-end
/**
 * 客户折扣列表model
 */
Ext.define('Miser.model.DiscountCustomerEntity', {
    extend: 'Ext.data.Model',
    fields: [
    {name: 'id',type: 'string'},
    {name: 'customerCode',type: 'string'},
    {name: 'discountPriorityType',type: 'string'},
    {name: 'upstairsSectionCode',type: 'string'},
    {name: 'transTypeCode',type: 'string'},
    {name: 'freightSectionCode',type: 'string'},
    {name: 'pickupSectionCode',type: 'string'},
    {name: 'addSectionCode',type: 'string'},
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
    {name: 'defaultShowDiscountValue',type: 'string'},
    {name: 'discountAccodingPcTime',type : 'date',dateFormat:'time'},
    {name: 'heavyFloatPrice',type: 'float'},
    {name: 'lightFloatPrice',type: 'float'},
    {name: 'floatPercentage',type: 'float'},
    
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
    {name: 'createUserName',type: 'string'},
    {name: 'modifyUserName',type : 'string'},
    {name: 'logistCode',type : 'string'},
    {name: 'state',type: 'string'}
    ]
});
/**
 * 查询表单
 */
Ext.define('Miser.view.discountCustomer.QueryForm', {
    extend: 'Ext.form.Panel',
    id: 'queryForm',
    frame: true,
    title : discount.discountCustomer.i18n('discount.discountCustomer.querycondition'),// '查询条件',
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
        divisionLabel: discount.discountCustomer.i18n('discount.division'),
        divisionName: 'division',
        divisionIsBlank:true,
        divisionLabelWidth:70,
        divisionWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 大区
        bigregionLabel: discount.discountCustomer.i18n('discount.bigRegion'),
        bigregionName: 'bigRegion',
        bigregionIsBlank:true,
        bigregionLabelWidth:70,
        bigregionWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 路区
        roadareaLabel: discount.discountCustomer.i18n('discount.roadArea'),
        roadareaName: 'roadArea',
        roadareaIsBlank:true,
        roadareaLabelWidth:70,
        roadareaWidth:275,
        margin:'5px 0px 5px 0px',
        
        // 门店
        salesdepartmentLabel: discount.discountCustomer.i18n('discount.orgCodeName'),
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
		items : [ 
		{
			name : 'customerCode',
			fieldLabel :discount.discountCustomer.i18n('discount.discountCustomer.customerCode') ,
			xtype : 'textfield',
			width:275,
			margin:'5px 0px 5px 0px'
		},{
			name : 'customerName',
			fieldLabel :discount.discountCustomer.i18n('discount.discountCustomer.customerName') ,
			xtype : 'bsecustomercombselector',
			width:275,
			margin:'5px 0px 5px 0px'
		},{
			name : 'transTypeCode',
			fieldLabel :discount.discountCustomer.i18n('discount.discountCustomer.transTypeCode') ,
			showAll:true,
			containsRoot:true,
			xtype : 'transtypecombselector',
			width:275,
			margin:'5px 0px 5px 0px'
		},{
			name : 'state',
			fieldLabel : '当前状态',
			value:2,
			xtype : 'statuscombselector',
			width:275,
			margin:'5px 0px 5px 0px'
		}]
	},
	{
		layout : 'column',
		margin: '-5 0 0 10',
		defaults : {
			labelWidth : 70,
			labelAlign : "right"
		},
		items : [ 
		{
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
        text: discount.discountCustomer.i18n('discount.discountCustomer.query'),//'查询'
        handler: function() {
        	var queryForm = Ext.getCmp('queryForm');
			
        	//运输类型
			var transTypeCodeVal = queryForm.getForm().findField('transTypeCode').getValue();
			
			//状态
			var stateVal = queryForm.getForm().findField('state').getValue();
				
			//有效时间点
			var effectiveTimeVal = queryForm.getForm().findField('effectiveTime').getValue();
			
			//客户名称
			var customerCodeVal = queryForm.getForm().findField('customerCode').getValue();
			//客户名称
			var customerNameVal = queryForm.getForm().findField('customerName').rawValue;
			if(customerCodeVal == undefined || customerCodeVal == null || customerCodeVal == ''){
				if(customerNameVal != undefined && customerNameVal != null && customerNameVal != '' ){
					
					
				
					//所有模糊查询需要要求输入4个字符以上才能查询，否则提示：模糊查询必须输入4个字符以上才能查询
					if( strlen(customerNameVal) < 4){
						miser.showErrorMes(discount.discountCustomer.i18n('discount.discountCustomer.likeQryMoreFourChars'));//"模糊查询必须输入4个字符以上才能查询"
						return;
					}												
				}
			}
            if (me.getForm().isValid()) {
                me.up().getDiscountCustomerGrid().getPagingToolbar().moveFirst();;
            }
        }
    },
    {
        text: discount.discountCustomer.i18n('discount.discountCustomer.reset'),//'重置',
        handler: function() {
            me.getForm().reset();
        }
    }
    ];
    me.callParent([cfg]);
	}
});

/**
 * 客户折扣信息表格
 */
Ext.define('Miser.view.discountCustomer.Grid', {
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
    discountCustomerAddWindow: null,
    getDiscountCustomerAddWindow: function() {
        if (this.discountCustomerAddWindow == null) {
            this.discountCustomerAddWindow = Ext.create('Miser.view.discount.discountCustomerAddWindow');
            this.discountCustomerAddWindow.parent = this; // 父元素
        }
        return this.discountCustomerAddWindow;
    },
    discountCustomerUpdateWindow: null,
    getDiscountCustomerUpdateWindow: function() {
        if (this.discountCustomerUpdateWindow == null) {
            this.discountCustomerUpdateWindow = Ext.create('Miser.view.discount.discountCustomerUpdateWindow');
            this.discountCustomerUpdateWindow.parent = this; //父元素
        }
        return this.discountCustomerUpdateWindow;
    },
    updateDiscountCustomer : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage('请选择一条进行修改操作'); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		//判断数据是否失效
		for(var i=0;i<selections.length;i++){
        	if(selections[i].get('active')=='N'){
        		miser.showWoringMessage('你选择数据已作废，不能修改');
        		return; 
        	}
//        	var nowDate = dateFormat(new Date());
//        	var invalidTime = dateFormat(selections[i].get('invalidTime'));
//        	if(nowDate>=invalidTime){
//        		miser.showWoringMessage('你选择数据已失效，不能修改');
//        		return; 
//        	}
        }
		var id = selections[0].get('id');
		var params = {
				'discountCustomerVo' :{
					'discountCustomerEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var updateWindow = me.getDiscountCustomerUpdateWindow(); //获得修改窗口
			updateWindow.discountCustomerEntity = json.discountCustomerVo.discountCustomerEntity;
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
		miser.requestJsonAjax('../discount/discountCustomer!queryById.action', params, successFun, failureFun);
	},
	saveDiscountCustomer : function(){
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
				'discountCustomerVo' :{
					'discountCustomerEntity' : {
						'id' : id
					}
				}
			};
		var successFun = function (json) {
			var addWindow =  me.getDiscountCustomerAddWindow(); //获得新增窗口
			addWindow.discountCustomerEntity = json.discountCustomerVo.discountCustomerEntity;
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
		miser.requestJsonAjax('../discount/discountCustomer!queryAddById.action', params, successFun, failureFun);
	},
	deleteDiscountCustomer: function() {
        var me = this;
        var selections = me.getSelectionModel().getSelection(); // 获取选中的数据
        if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(discount.discountCustomer.i18n('discount.discountCustomer.oneselected')); // 请选择一条进行作废操作！
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
                		'discountCustomerVo': {
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
                        miser.showErrorMes(discount.discountCustomer.i18n('discount.discountCustomer.timeout')); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
                miser.requestJsonAjax('../discount/discountCustomer!deleteDiscountCustomer.action', params, successFun, failureFun);
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
				        {dataIndex: 'division',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.division')},
				        {dataIndex: 'bigRegion',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.bigRegion')},
				        {dataIndex: 'roadArea',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.roadArea')},
				        {dataIndex: 'orgCodeName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.orgCodeName')},
				        {dataIndex: 'logistCode',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.logistCode')},
				        {dataIndex: 'customerName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.customerName')},
				        {dataIndex: 'customerCode',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.customerCode')},
				        {dataIndex: 'discountPriorityType',
							renderer: function (value) {
								return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountCustomer.discountPriorityType), value);
							},
							width: 100,
							text:discount.discountCustomer.i18n('discount.discountCustomer.discountPriorityType')
							},
				        {dataIndex: 'discountAccodingPcTime',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.discountAccodingPcTime'),renderer: function(value) {return dateRender(value,'Y-m-d');}},
				        {dataIndex: 'orgCode',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.orgCode'),hidden:true},
				        {dataIndex: 'transTypeName', width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.transTypeCode')},
				        {dataIndex: 'freightSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.freightSectionCode')},
				        {dataIndex: 'addSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.addSectionCode')},
				        {dataIndex: 'pickupSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.pickupSectionCode')},
				        {dataIndex: 'deliverySectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.deliverySectionCode')},
				        {dataIndex: 'upstairsSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.upstairsSectionCode')},
				        {dataIndex: 'insuranceRateSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.insuranceRateSectionCode')},
				        {dataIndex: 'insuranceSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.insuranceSectionCode')},
				        {dataIndex: 'paperSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.paperSectionCode')},
				        {dataIndex: 'smsSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.smsSectionCode')},
				        {dataIndex: 'fuelSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.fuelSectionCode')},
				        {dataIndex: 'collectionRateSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.collectionRateSectionCode')},
				        {dataIndex: 'collectionSectionName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.collectionSectionCode')},
				        {dataIndex: 'heavyFloatPrice',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.heavyFloatPrice')},
				        {dataIndex: 'lightFloatPrice',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.lightFloatPrice')},
				        {dataIndex: 'floatPercentage',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.floatPercentage')},
				        {dataIndex: 'effectiveTime',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.effectiveTime'),renderer: function(value) {return dateRender(value,'Y-m-d H:i:s');}},
				        {dataIndex: 'invalidTime',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.invalidTime'),renderer: function(value) {return dateRender(value,'Y-m-d H:i:s');}},
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
				        {dataIndex: 'remark',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.remark')},
				        {dataIndex: 'createDate',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.createDate'),renderer: function(value) {return dateRender(value,'Y-m-d');}},
				        {dataIndex: 'createUser',width: 100,hidden:true},
				        {dataIndex: 'createUserName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.createUser'),
				        	   renderer:function(v,m,record,ri,ci){  
				        	   var aResult= record.get('createUser');  
				        	   return aResult+v;
				        }},
				        {dataIndex: 'modifyDate',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.modifyDate'),renderer: function(value) {return dateRender(value,'Y-m-d');}},
				        {dataIndex: 'modifyUser',width: 100,hidden:true},
				        {dataIndex: 'modifyUserName',width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.modifyUser'),
				        	   renderer:function(v,m,record,ri,ci){  
				        	   var aResult= record.get('modifyUser');  
				        	   return aResult+v;
				        }},
				        {dataIndex: 'defaultShowDiscountValue',
							renderer: function (value) {
								return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountCustomer.isActive), value);
							},width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.defaultShowDiscountValue')},
				        {dataIndex: 'active',
							renderer: function (value) {
								return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore(discount.discountCustomer.isActive), value);
							},width: 100,text:discount.discountCustomer.i18n('discount.discountCustomer.active')}
				     ],
        me.store = Ext.create('Miser.store.discountCustomerStore', {
            autoLoad: false
        });
        me.selModel = Ext.create('Ext.selection.CheckboxModel', { // 多选框
            mode: 'MULTI',
            checkOnly: true
        });
        me.tbar = [{
        	text:discount.discountCustomer.i18n('discount.discountCustomer.add'),// '新增',
            xtype: 'addbutton',
            // 新增
            width:80,
            handler: function() {
                //me.getDiscountCustomerAddWindow().show();
            	me.saveDiscountCustomer();
            }
        },
        '-', {
            text:discount.discountCustomer.i18n('discount.discountCustomer.update'),// '修改',
            xtype: 'updatebutton',
            width: 80,
            handler: function() {
                me.updateDiscountCustomer();
            }
        },
        '-', {
        	id : 'miser_biz_discount_discountCustomer_del_id',
            text:discount.discountCustomer.i18n('discount.discountCustomer.invalid'),// '作废',
            xtype: 'deletebutton',
            disabled : true,
            // 作废
            width: 80,
            handler: function() {
                me.deleteDiscountCustomer();
            }
        },{
			xtype: 'uploadbutton',
            text: '导入',
            width: 80,
            handler:function(){
            	uploadT.excelWindow(null,imple);
            }
		},{
			xtype: 'downloadbutton',
            text: '导入模板下载',
            width: 130,
            handler:function(){
            	//downExcel();
				miser.requestExportAjax('/excelTemplate/discountCustomer.xlsx');
            }
    	},{
			xtype: 'downloadbutton',
            text: '导出数据',
            width: 130,
            handler:function(){
            	downExcelFor();
            }
    	}
    ];
        me.bbar = me.getPagingToolbar();
        me.selModel = Ext.create('Ext.selection.CheckboxModel', {
        listeners: {
            selectionchange: function(sm, selections) {
                Ext.getCmp('miser_biz_discount_discountCustomer_del_id').setDisabled(selections.length == 0);
              //只有待生效和已失效的数据，在网点折扣管理界面选中某记录，作废按钮才能可用，否则灰色禁用
                for(var i=0;i<selections.length;i++){
                	if(selections[i].get('active')=='N'){
                		Ext.getCmp('miser_biz_discount_discountCustomer_del_id').setDisabled(true);
                		break;
                	}
                	var nowDate = dateFormat(new Date());
                	var effectiveTime = dateFormat(selections[i].get('effectiveTime'));
                	var invalidTime = dateFormat(selections[i].get('invalidTime'));
                	if(nowDate>=invalidTime){
                		Ext.getCmp('miser_biz_discount_discountCustomer_del_id').setDisabled(true);
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
Ext.define('Miser.view.discount.discountCustomerAddForm', {
	extend : 'Ext.form.Panel',
	id:'discountCustomerAddForm',
	frame : true,
	region : 'north',
	autoDestroy:true,
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
							name : 'id',
							fieldLabel : 'id',
							xtype : 'textfield',
							hidden : true
						 },{
			    			name: 'customerCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.customerName'),
			    			xtype : 'bsecustomercombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'transTypeCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.transTypeCode'),
			    			showAll:false,
			    			containsRoot:false,
			    			xtype : 'transtypecombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'freightSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.freightSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			margin:'5px 0px 5px 0px',
			    			sectionedItem:'FREIGHT',
			    			allowBlank:true
						},{
							name : 'addSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.addSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
							margin:'5px 0px 5px 0px',
			    			sectionedItem:'EXTRA_FEE',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [ {
			    			name: 'fuelSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.fuelSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FUEL',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'pickupSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.pickupSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PICKUP',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'deliverySectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.deliverySectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'DELIVERY',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'upstairsSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.upstairsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'UPSTAIRS',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'insuranceRateSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.insuranceRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.insuranceSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionRateSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.collectionRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECT_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.collectionSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECTION_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'paperSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.paperSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PAPER',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'smsSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.smsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'SMS',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'heavyFloatPrice',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.heavyFloatPrice'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'lightFloatPrice',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.lightFloatPrice'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:false
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'defaultShowDiscountValue',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.defaultShowDiscountValue'),
			    			xtype : 'yesnocombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'discountPriorityType',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.discountPriorityType'),
			    			xtype : 'dataDictionarySelector',
							termsCode : discount.discountCustomer.discountPriorityType,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'floatPercentage',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.floatPercentage'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'discountAccodingPcTime',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.discountAccodingPcTime'),
							xtype : 'datefield',
							value:new Date(),
							format : 'Y-m-d H:i:s',
							margin:'5px 0px 5px 0px',
			    			allowBlank:false
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
							name : 'effectiveTime',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.effectiveTime'),
							xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'invalidTime',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.invalidTime'),
			    			xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [ {
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.remark'),//'备注',
							name : 'remark',
							width : document.documentElement.clientWidth - 25,
							margin:'5px 0px 5px 0px',
							xtype : 'textarea'
						}]
					}];
        me.callParent([cfg]);
    }
});


/**
 * 修改表单
 */
Ext.define('Miser.view.discount.discountCustomerUpdateForm', {
	extend : 'Ext.form.Panel',
	id:'discountCustomerUpdateForm',
	frame : true,
	region : 'north',
	autoDestroy:true,
	defaults : {
		labelWidth : 0.4,
		columnWidth : 0.6,
		margin : '8 10 5 10',
		labelAlign : 'right'
	},
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        
        me.items = [
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
							name : 'id',
							fieldLabel : 'id',
							xtype : 'textfield',
							hidden : true
						 },{
			    			name: 'customerCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.customerName'),
			    			xtype : 'bsecustomercombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'transTypeCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.transTypeCode'),
			    			showAll:false,
			    			containsRoot:false,
			    			xtype : 'transtypecombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'freightSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.freightSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			margin:'5px 0px 5px 0px',
			    			sectionedItem:'FREIGHT',
			    			allowBlank:true
						},{
							name : 'addSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.addSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
							margin:'5px 0px 5px 0px',
			    			sectionedItem:'EXTRA_FEE',
			    			allowBlank:true
						} ]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [ {
			    			name: 'fuelSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.fuelSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'FUEL',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'pickupSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.pickupSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PICKUP',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'deliverySectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.deliverySectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'DELIVERY',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'upstairsSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.upstairsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'UPSTAIRS',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'insuranceRateSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.insuranceRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'insuranceSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.insuranceSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'INSURANCE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionRateSectionCode',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.collectionRateSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECT_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'collectionSectionCode',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.collectionSectionCode'),
							xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'COLLECTION_RATE',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'paperSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.paperSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'PAPER',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
			    			name: 'smsSectionCode',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.smsSectionCode'),
			    			xtype : 'dynamicPriceSectioncombselector',
			    			sectionedItem:'SMS',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'heavyFloatPrice',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.heavyFloatPrice'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'lightFloatPrice',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.lightFloatPrice'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:false
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
			    			name: 'defaultShowDiscountValue',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.defaultShowDiscountValue'),
			    			xtype : 'yesnocombselector',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'discountPriorityType',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.discountPriorityType'),
			    			xtype : 'dataDictionarySelector',
							termsCode : discount.discountCustomer.discountPriorityType,
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
							name : 'floatPercentage',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.floatPercentage'),
							xtype : 'textfield',
							margin:'5px 0px 5px 0px',
			    			allowBlank:true
						},{
							name : 'discountAccodingPcTime',
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.discountAccodingPcTime'),
							xtype : 'datefield',
							value:new Date(),
							format : 'Y-m-d H:i:s',
							margin:'5px 0px 5px 0px'
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [{
							name : 'effectiveTime',
							fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.effectiveTime'),
							xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						},{
			    			name: 'invalidTime',
			    			fieldLabel: discount.discountCustomer.i18n('discount.discountCustomer.invalidTime'),
			    			xtype : 'datetimefield',
			    			format : 'Y-m-d H:i:s',
			    			margin:'5px 0px 5px 0px',
			    			allowBlank:false
						}]
					},
					{
						layout : 'hbox',
						margin: '-5 0 0 10',
						defaults : {
							labelWidth : 100,
							labelAlign : "right"
						},
						items : [ {
							fieldLabel : discount.discountCustomer.i18n('discount.discountCustomer.remark'),//'备注',
							name : 'remark',
							width : document.documentElement.clientWidth - 25,
							margin:'5px 0px 5px 0px',
							xtype : 'textarea'
						}]
					}];
        me.callParent([cfg]);
    }
});

//增加窗体
Ext.define('Miser.view.discount.discountCustomerAddWindow', {
    extend: 'Ext.window.Window',
    title: '新增客户折扣',
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
        	me = this;
//        	var fielsds = me.getDiscountCustomerAddForm().getForm().getFields();
//            if (!Ext.isEmpty(fielsds)) {
//                fielsds.each(function(item, index, length) {
//                	alert(item.toString());
//                });
//            }
            me.getDiscountCustomerAddForm().getForm().reset(); // 表格重置
            
        },
        beforeshow: function(me) { // 显示WINDOW的时候清除数据
            var fielsds = me.getDiscountCustomerAddForm().getForm().getFields();
            if (!Ext.isEmpty(fielsds)) {
                fielsds.each(function(item, index, length) {
                    item.clearInvalid();
                    item.unsetActiveError();
                });
            }
            fielsds = null;
            me.getDiscountCustomerAddForm().getForm().loadRecord(new Miser.model.DiscountCustomerEntity(me.discountCustomerEntity));
			//这块是处理下拉框赋值的问题
			
			//运输类型
			var transTypeCode = me.getDiscountCustomerAddForm().getForm().findField('transTypeCode');
			var transTypeCodeRecord=Ext.data.Record({
				code :  me.discountCustomerEntity.transTypeCode,
				name :  me.discountCustomerEntity.transTypeName
				}
			);
			transTypeCode.getStore().add(transTypeCodeRecord);
			transTypeCode.setValue(me.discountCustomerEntity.transTypeCode);
			
			//客户编码信息
			var customerCode = me.getDiscountCustomerAddForm().getForm().findField('customerCode');
			var customerCodeRecord=Ext.data.Record({
				code :  me.discountCustomerEntity.customerCode,
				name :  me.discountCustomerEntity.customerName
				}
			);
			customerCode.getStore().add(customerCodeRecord);
			customerCode.setValue(me.discountCustomerEntity.customerCode);
        }
    },
    discountCustomerAddForm: null,
    getDiscountCustomerAddForm: function() {
        if (this.discountCustomerAddForm == null) {
            this.discountCustomerAddForm = Ext.create('Miser.view.discount.discountCustomerAddForm');
        }
        return this.discountCustomerAddForm;
    },
    submitDiscountCustomerAddForm: function(isConfirm) {
        var me = this;
        if (me.getDiscountCustomerAddForm().getForm().isValid()) { // 校验form是否通过校验
        	Ext.Msg.wait('处理中，请稍后...', '提示');
            var discountCustomerEntity = new Miser.model.DiscountCustomerEntity;
            me.getDiscountCustomerAddForm().getForm().updateRecord(discountCustomerEntity); // 将FORM中数据设置到MODEL里面
            var curData=discountCustomerEntity.data;
            curData.id='';
            
            var params = {
                'discountCustomerVo': {
                    'discountCustomerEntity': curData,
                    'isConfirm':isConfirm
                }
            }
            var successFun = function(json) {
                var message = json.message;
                if(message=='添加成功'){
                	miser.showInfoMsg(message); // 提示新增成功
                	me.close();
                    me.parent.getStore().load(); // 成功之后重新查询刷新结果集
                }else if (message==discount.discountCustomer.i18n('discount.discountCustomer.isConfirm')){
                	miser.showQuestionMes(message,//是否删除
                            function(e) {
                                if (e == 'yes') { // 询问是否删除，是则发送请求
                                	 me.submitDiscountCustomerAddForm("1");
                                	 me.close();
                                }
                     });
                }else{
                	miser.showInfoMsg(message);
				}
            };
            var failureFun = function(json) {
                if (Ext.isEmpty(json)) {
                    miser.showErrorMes(discount.discountCustomer.i18n('miser.discount.timeout')); // 请求超时
                } else {
                    var message = json.message;
                    miser.showErrorMes(message); // 提示失败原因
                }
            };
            miser.requestJsonAjax('../discount/discountCustomer!addDiscountCustomer.action', params, successFun, failureFun); // 发送AJAX请求
        }
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.fbar = [{
            text: discount.discountCustomer.i18n('miser.common.save'),//'保存'
            handler: function() {
            me.submitDiscountCustomerAddForm("0");
            }
        },
        {
            text:discount.discountCustomer.i18n('miser.common.reset'),// '重置'
            handler: function() {me.getDiscountCustomerAddForm().reset();}
        },
        {
            text: discount.discountCustomer.i18n('miser.common.cancel'),// '取消'
            handler: function() {me.close();}
        }];
        me.items = [me.getDiscountCustomerAddForm()];
        me.callParent([cfg]);
    }
});

/**
 * 修改客户折扣窗口
 */
	Ext.define('Miser.view.discount.discountCustomerUpdateWindow',{
		extend : 'Ext.window.Window',
		title : '修改客户折扣',//修改
		closable : true,
		parent : null, // 父元素
		modal : true,
		resizable : false, // 可以调整窗口的大小
		closeAction : 'hide', // 点击关闭是隐藏窗口
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		discountCustomerEntity: null,
		listeners : {
			beforehide : function(me) { // 隐藏WINDOW的时候清除数据
				me.getDiscountCustomerUpdateForm().getForm().reset(); // 表格重置
			},
			beforeshow : function(me) { // 显示WINDOW的时候清除数据
				var fielsds = me.getDiscountCustomerUpdateForm().getForm().getFields();
				if (!Ext.isEmpty(fielsds)) {
					fielsds.each(function(item, index, length) {
						item.clearInvalid();
						item.unsetActiveError();
					});
				}
				fielsds = null;
				me.getDiscountCustomerUpdateForm().getForm().loadRecord(new Miser.model.DiscountCustomerEntity(me.discountCustomerEntity));
				//这块是处理下拉框赋值的问题
				
				//运输类型
				var transTypeCode = me.getDiscountCustomerUpdateForm().getForm().findField('transTypeCode');
				var transTypeCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.transTypeCode,
					name :  me.discountCustomerEntity.transTypeName
					}
				);
				transTypeCode.getStore().add(transTypeCodeRecord);
				transTypeCode.setValue(me.discountCustomerEntity.transTypeCode);
				transTypeCode.setReadOnly(true);
				
				//纯运费分段
				var freightSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('freightSectionCode');
				var freightSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.freightSectionCode,
					name :  me.discountCustomerEntity.freightSectionName
					}
				);
				freightSectionCode.getStore().add(freightSectionCodeRecord);
				freightSectionCode.setValue(me.discountCustomerEntity.freightSectionCode);
				
				//提货费分段
				var pickupSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('pickupSectionCode');
				var pickupSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.pickupSectionCode,
					name :  me.discountCustomerEntity.pickupSectionName
					}
				);
				pickupSectionCode.getStore().add(pickupSectionCodeRecord);
				pickupSectionCode.setValue(me.discountCustomerEntity.pickupSectionCode);
				
				//送货费分段
				var deliverySectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('deliverySectionCode');
				var deliverySectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.deliverySectionCode,
					name :  me.discountCustomerEntity.deliverySectionName
					}
				);
				deliverySectionCode.getStore().add(deliverySectionCodeRecord);
				deliverySectionCode.setValue(me.discountCustomerEntity.deliverySectionCode);
				
				//保价费率分段
				var insuranceRateSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('insuranceRateSectionCode');
				var insuranceRateSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.insuranceRateSectionCode,
					name :  me.discountCustomerEntity.insuranceRateSectionName
					}
				);
				insuranceRateSectionCode.getStore().add(insuranceRateSectionCodeRecord);
				insuranceRateSectionCode.setValue(me.discountCustomerEntity.insuranceRateSectionCode);
				
				//保价费分段
				var insuranceSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('insuranceSectionCode');
				var insuranceSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.insuranceSectionCode,
					name :  me.discountCustomerEntity.insuranceSectionName
					}
				);
				insuranceSectionCode.getStore().add(insuranceSectionCodeRecord);
				insuranceSectionCode.setValue(me.discountCustomerEntity.insuranceSectionCode);
				
				//工本费分段
				var paperSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('paperSectionCode');
				var paperSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.paperSectionCode,
					name :  me.discountCustomerEntity.paperSectionName
					}
				);
				paperSectionCode.getStore().add(paperSectionCodeRecord);
				paperSectionCode.setValue(me.discountCustomerEntity.paperSectionCode);
				
				//信息费分段
				var smsSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('smsSectionCode');
				var smsSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.smsSectionCode,
					name :  me.discountCustomerEntity.smsSectionName
					}
				);
				smsSectionCode.getStore().add(smsSectionCodeRecord);
				smsSectionCode.setValue(me.discountCustomerEntity.smsSectionCode);
				
				//燃油费分段
				var fuelSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('fuelSectionCode');
				var fuelSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.fuelSectionCode,
					name :  me.discountCustomerEntity.fuelSectionName
					}
				);
				fuelSectionCode.getStore().add(fuelSectionCodeRecord);
				fuelSectionCode.setValue(me.discountCustomerEntity.fuelSectionCode);
				
				//代收手续费率分段
				var collectionRateSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('collectionRateSectionCode');
				var collectionRateSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.collectionRateSectionCode,
					name :  me.discountCustomerEntity.collectionRateSectionName
					}
				);
				collectionRateSectionCode.getStore().add(collectionRateSectionCodeRecord);
				collectionRateSectionCode.setValue(me.discountCustomerEntity.collectionRateSectionCode);
				
				//代收手续费分段
				var collectionSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('collectionSectionCode');
				var collectionSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.collectionSectionCode,
					name :  me.discountCustomerEntity.collectionSectionName
					}
				);
				collectionSectionCode.getStore().add(collectionSectionCodeRecord);
				collectionSectionCode.setValue(me.discountCustomerEntity.collectionSectionCode);
				//上楼费分段
				var upstairsSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('upstairsSectionCode');
				var upstairsSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.upstairsSectionCode,
					name :  me.discountCustomerEntity.upstairsSectionName
					}
				);
				upstairsSectionCode.getStore().add(upstairsSectionCodeRecord);
				upstairsSectionCode.setValue(me.discountCustomerEntity.upstairsSectionCode);
				//附件费分段
				var addSectionCode = me.getDiscountCustomerUpdateForm().getForm().findField('addSectionCode');
				var addSectionCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.addSectionCode,
					name :  me.discountCustomerEntity.addSectionName
					}
				);
				addSectionCode.getStore().add(addSectionCodeRecord);
				addSectionCode.setValue(me.discountCustomerEntity.addSectionCode);
				
				//客户编码信息
				var customerCode = me.getDiscountCustomerUpdateForm().getForm().findField('customerCode');
				var customerCodeRecord=Ext.data.Record({
					code :  me.discountCustomerEntity.customerCode,
					name :  me.discountCustomerEntity.customerName
					}
				);
				customerCode.getStore().add(customerCodeRecord);
				customerCode.setValue(me.discountCustomerEntity.customerCode);
				
				//判断生效时间能否修改
				var nowDate = dateFormat(new Date());
            	var effectiveTime = dateFormat(me.discountCustomerEntity.effectiveTime);
            	var invalidTime = dateFormat(me.discountCustomerEntity.invalidTime);
            	if(effectiveTime < nowDate &&　invalidTime　>= nowDate){
            		var effectiveTime = me.getDiscountCustomerUpdateForm().getForm().findField('effectiveTime');
    				effectiveTime.setReadOnly(true);
            	}
			}
		},
		discountCustomerUpdateForm: null,
	    getDiscountCustomerUpdateForm: function() {
	        if (Ext.isEmpty(this.discountCustomerUpdateForm)) {
	            this.discountCustomerUpdateForm = Ext.create('Miser.view.discount.discountCustomerUpdateForm');
	        }
	        return this.discountCustomerUpdateForm;
	    },
		submitDriver:function(isConfirm){
			var me = this;
			if (me.getDiscountCustomerUpdateForm().getForm().isValid()) { //校验form是否通过校验
				Ext.Msg.wait('处理中，请稍后...', '提示');
				var discountCustomerEntity = new Miser.model.DiscountCustomerEntity;
				var id = me.getDiscountCustomerUpdateForm().getForm().findField('id').rawValue;
				me.getDiscountCustomerUpdateForm().getForm().updateRecord(discountCustomerEntity); //将FORM中数据设置到MODEL里面
				var curData = discountCustomerEntity.data;
				curData.id = id;
	            var params = {
	                'discountCustomerVo': {
	                    'discountCustomerEntity': curData,
	                    'isConfirm':isConfirm
	                }
	            }
				
				var successFun = function (json) {
					var message = json.message;
					if(message=='更新成功'){
						miser.showInfoMsg(message); //提示修改成功
						me.close();
						me.parent.getStore().load(); //成功之后重新查询刷新结果集
					}else if (message==discount.discountCustomer.i18n('discount.discountCustomer.isConfirm')){
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
				miser.requestJsonAjax('../discount/discountCustomer!updateDiscountCustomer.action', params, successFun, failureFun); //发送AJAX请求
			}
		},
		constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.fbar = [{
				text :discount.discountCustomer.i18n('miser.common.save'), //保存
				handler : function() {
					 me.submitDriver("0");
				}
			}, 
//			{
//				text :discount.discountCustomer.i18n('miser.common.reset'), //重置
//				handler : function() {
//					 me.getDiscountCustomerUpdateForm().reset();
//					 me.getDiscountCustomerUpdateForm().getForm().loadRecord(
//					 new Miser.model.RoleEntity({
//						 'code' : me.roleEntity.code
//					 }));
//				}
//			},
			{
				text :discount.discountCustomer.i18n('miser.common.cancel'), //取消
				handler : function() {
					me.close();
				}
			} ];
			me.items = [ me.getDiscountCustomerUpdateForm() ];
			me.callParent([ cfg ]);
		}
	});
/**
 * 客户折扣列表store
 */
Ext.define('Miser.store.discountCustomerStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.model.DiscountCustomerEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: '../discount/discountCustomer!queryListByParam.action',
        reader: {
            type: 'json',
            rootProperty: 'discountCustomerVo.discountCustomerList',
            totalProperty: 'totalCount' // 总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('queryForm');
            if (queryForm != null) {
                var params = {
                	'discountCustomerVo.discountCustomerEntity.transTypeCode': queryForm.getForm().findField('transTypeCode').getValue(),
                	'discountCustomerVo.discountCustomerEntity.customerCode': queryForm.getForm().findField('customerCode').getValue(),
                	'discountCustomerVo.discountCustomerEntity.customerName': queryForm.getForm().findField('customerName').rawValue,
                    'discountCustomerVo.discountCustomerEntity.orgCode': queryForm.getForm().findField('orgCode').getValue(),
                    'discountCustomerVo.discountCustomerEntity.roadArea': queryForm.getForm().findField('roadArea').getValue(),
                    'discountCustomerVo.discountCustomerEntity.bigRegion': queryForm.getForm().findField('bigRegion').getValue(),
                    'discountCustomerVo.discountCustomerEntity.division': queryForm.getForm().findField('division').getValue(),
                    'discountCustomerVo.discountCustomerEntity.effectiveTime': queryForm.getForm().findField('effectiveTime').getValue(),
                    'discountCustomerVo.discountCustomerEntity.state': queryForm.getForm().findField('state').getValue()
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
         
    	
    }
    
});

Ext.onReady(function() {
	Ext.QuickTips.init();
    var queryForm = Ext.create('Miser.view.discountCustomer.QueryForm'); //查询FORM
    var discountCustomerGrid = Ext.create('Miser.view.discountCustomer.Grid');
    Ext.create('Ext.panel.Panel', {
        renderTo: Ext.getBody(),
        getQueryForm: function() {
            return queryForm;
        },
        getDiscountCustomerGrid: function() {
            return discountCustomerGrid;
        },
        items: [queryForm, discountCustomerGrid]
    });
});