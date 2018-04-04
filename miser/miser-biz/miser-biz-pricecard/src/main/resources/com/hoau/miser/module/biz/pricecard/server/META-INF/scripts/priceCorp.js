//文件上传excel
var uploadT={
	oufileName:null,
	excelWindow:function(url,fn,parent){
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
									fn(action.result.outFileName,true,parent);
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

//后台读excel数据，再返回前台
function imple(outFileName,flag,parent){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
		   timeout:1800000,
	       url : '../pricecard/priceCorp!excelImpl.action',  
	       method : 'post',  
	       params : {  
		   		'priceCorpGirdVo.corpCode' : parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').getValue(),
		   		'priceCorpGirdVo.corpName' : getDisplayTextByValue(parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').getValue(),parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').store),
		   		'priceCorpGirdVo.effectiveTime' : parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').getValue(),
		   		'priceCorpGirdVo.invalidTime' : parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').getValue(),
		   		'priceCorpGirdVo.remark' : parent.getPriceCorpEntityChangeForm().getForm().findField('changeForm_remark').getValue(),
	    	    'priceCorpGirdVo.filePath' : outFileName
	          },  
	          success : function(response, options) {  
	              Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText);
	              // succCount:成功条数,failCount:失败条数, list:明细记录.
	              if(responseArray.repeatTip == ""){//如果没有重复，填充表单
	            	  var msg="成功：" + responseArray.succCount + "条；失败：" + responseArray.failCount + "条；";
	            	  miser.showInfoMsg(msg);         
	            	  parent.priceCorpAddGrid.store.loadData(responseArray.list,false);
	              }else{
	            	  Ext.MessageBox.show({title: '失败',msg: '导入失败:' + responseArray.repeatTip,buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR}); 
	            	  //miser.showInfoMsg("导入失败：" + responseArray.repeatTip);
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

//下载,导出网点详细excel
function down(parent){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	var params=null;
	
	var grid = Ext.getCmp('mainPanel').getFormGridName();
	var selections = grid.getSelectionModel().getSelection(); //获取选中的数据
	var corpCode = selections[0].get('corpCode');
	var corpName = selections[0].get('corpName');
	var effectiveTime = selections[0].get('effectiveTime');
	var invalidTime = selections[0].get('invalidTime');
	var division = selections[0].get('division');
	var bigRegion = selections[0].get('bigRegion');
	var roadArea = selections[0].get('roadArea');
	var state=selections[0].get('state');
	
	params = {
		'priceCorpGirdVo.corpCode' : corpCode,
		'priceCorpGirdVo.effectiveTime' : effectiveTime,
		'priceCorpGirdVo.invalidTime' : invalidTime,
		'priceCorpGirdVo.division' : division,
		'priceCorpGirdVo.bigRegion' : bigRegion,
		'priceCorpGirdVo.roadArea' : roadArea,
		'priceCorpGirdVo.state' : state
	};
	Ext.Ajax.request( {  
       url : '../pricecard/priceCorp!excelExport.action',  
       method : 'post',  
       params : params,  
          success : function(response, options) {  
              // 隐藏进度条
              Ext.Msg.hide();   
              var responseArray = Ext.util.JSON.decode(response.responseText); 
              // addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
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

var comboBoxFields = ['text','value'];
var stateData = [
                 {'text':pricecard.priceCorp.i18n('pricecard.priceCorp.wait'),'value':'WAIT'},
                 {'text':pricecard.priceCorp.i18n('pricecard.priceCorp.effective'),'value':'EFFECTIVE'},
                 {'text':pricecard.priceCorp.i18n('pricecard.priceCorp.passed'),'value':'PASSED'},
                 {'text':pricecard.priceCorp.i18n('pricecard.priceCorp.deleted'),'value':'DELETED'}];

/**
 * 表格编辑
 */
var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    
    autoCancel: false,
    saveBtnText : pricecard.priceCorp.i18n('pricecard.priceCorp.confirm'),
    cancelBtnText : pricecard.priceCorp.i18n('pricecard.priceCorp.cancell'),
    errorSummary:false,
	listeners:{
		edit:function(e,o,m){
			/**
				校验
			*/
			var dat = e.grid.store.getAt(o.rowIdx).data;
			var sdat = e.grid.store.data.items;
			var count = 0;
			var inx = 0;
			for(var i = 0 ; i< sdat.length ; i++){
				if(sdat[i].data.arrivePriceCity == dat.arrivePriceCity
						&& sdat[i].data.transTypeCode == dat.transTypeCode) {
					count++;
					if(count >= 2){
						
						miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.identical')+ (inx+1));
						rowEditing.startEdit(o.rowIdx,0);
						return;
					}else{
						inx = i;;
					}
				}
			}
			
			Ext.getCmp('combo_arrive').onTrigger1Click();
			Ext.getCmp('combo_trtype').onTrigger1Click();
			Ext.getCmp('combo_full').onTrigger1Click();
		},
		cancelEdit:function (e,o){
			//防止直接取消
			try{
				var dat = e.grid.store.getAt(o.rowIdx).data;
				if(dat.arrivePriceCity == "" || dat.transTypeName =="" || dat.volumePrice == "" || dat.weightPrice == ""){
					e.grid.store.removeAt(o.rowIdx);
					return ;
				}else
				if(o.store.data.items[o.rowIdx].data.arrivePriceCity =="" &&
						o.store.data.items[o.rowIdx].data.transTypeName == ""){
					e.grid.store.removeAt(o.rowIdx);
					return ;
				}
				
				var sdat = e.grid.store.data.items;
				var count = 0;
				var inx = 0;
				for(var i = 0 ; i< sdat.length ; i++){
					if(sdat[i].data.arrivePriceCity == dat.arrivePriceCity
							&& sdat[i].data.transTypeCode == dat.transTypeCode) {
						count++;
						if(count >= 2){
							e.grid.store.removeAt(o.rowIdx);
							return;
						}else{
							inx = i;;
						}
					}
				}
			}catch (e) {
			}finally{
				Ext.getCmp('combo_arrive').onTrigger1Click();
				Ext.getCmp('combo_trtype').onTrigger1Click();
				Ext.getCmp('combo_full').onTrigger1Click();
			}
		},
		dblclick: function(e,o){
			this.cancelEdit();
		},
		afteredit:function(){
		},
		beforeedit :function(e,o){
			try{
				//设置到达城市
				var dat = e.grid.store.getAt(o.rowIdx).data;
				
				if(dat.arrivePriceCity != ""){
					Ext.getCmp('combo_arrive').setCombValue(dat.arrivePriceCityName,dat.arrivePriceCity);
				}
				if(dat.transTypeCode != ""){
					
					Ext.getCmp('combo_trtype').setCombValue(dat.transTypeName,dat.transTypeCode);
				}
				if(dat.fuelAddFeeSection != ""){
					
					Ext.getCmp('combo_full').setCombValue(dat.fuelAddFeeSectionName,dat.fuelAddFeeSection);
				}
				
			}catch(e){
			}
			return this.grid.isEdit;
		}
		
	}
});


//状态
function stateTypeRender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if('PASSED' == value){
		value= pricecard.priceCorp.i18n('pricecard.priceCorp.passed');
	}else if('EFFECTIVE' == value){
		value= pricecard.priceCorp.i18n('pricecard.priceCorp.effective');
	}else if('WAIT' == value){
		value= pricecard.priceCorp.i18n('pricecard.priceCorp.wait');
	}else if('DELETED' == value){
		value= pricecard.priceCorp.i18n('pricecard.priceCorp.deleted');
	}
	return value;
}

/**
 * 获取显示名称
 * @param id
 * @param store
 * @returns {String}
 */
function getDisplayText(store,value){
	var displayText = "";
	 //获取当前id="combo_process"的comboBox选择的值
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	  }
	return displayText;
	
}

function getDisplayTextCommon(store,value){
	var displayText = "";
	
	 //获取当前id="combo_process"的comboBox选择的值
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	  }
	return displayText;
}

function getDisplayTextByValue(value,store){
	 //获取当前id="combo_process"的comboBox选择的值
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  var displayText = "";
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	  }
	return displayText;
}


Ext.define('Miser.model.PriceCorpVoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        // 运输类型编号
        type: 'string'
    },{
    	name:'division',
    	type:'string'
    },{
    	name:'bigRegion',
    	type:'string'
    },{
    	name:'roadArea',
    	type:'string'
    },{
    	name:'corpName',
    	type:'string'
    },{
    	name:'effectiveTime',
    	type:'string'
    },{
    	name:'invalidTime',
    	type:'string'
    },{
    	name:'remark',
    	type:'string'
    }]
});

Ext.define('Miser.view.pricecard.PriceCorpEntityChangeForm',{
	extend:'Ext.form.Panel',
	id:'changeForm',
	frame: true,
	//width: miser.getBrowserHeight() - 120,
	layout: 'column',
	region: 'north',
	defaults: {
		 
		// margin: '0 10 0 10'
		margin:'8 10 5 10',
		 
	},
	constructor: function(config) {
	  var me = this,
	  cfg = Ext.apply({},config);

	  me.items = [
		    {
		    	layout: 'column',
				defaults: {
					labelWidth: 70,
					labelAlign: "right"
					//margins : '10 10 5 5'
				},
			items:[{
				name: '',
				labelWidth: 5,
				flex:1.42,	
				loaddefault:false,  
				width : document.documentElement.clientWidth-20,
				//height : 28,
				//所属事业部
	            divisionLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.division') + ":",
	            divisionName: 'changeForm_Division',
	            divisionIsBlank:true,
	            divisionLabelWidth:100,
	            divisionWidth:275,
	            margin:'5px 0px 5px 0px',
	            
	            //所属大区
	            bigregionLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.bigRegion') + ":",
	            bigregionName: 'changeForm_BigRegion',
	            bigregionIsBlank:true,
	            bigregionLabelWidth:100,
	            bigregionWidth:275,
	            margin:'5px 0px 5px 0px',
	            // 所属路区
	            roadareaLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.roadArea') + ":",
	            roadareaName: 'changeForm_RoadArea',
	            roadareaIsBlank:true,
	            roadareaLabelWidth:100,
	            roadareaWidth:275,
	            margin:'5px 0px 5px 0px',
	            //所属门店
	            salesdepartmentLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.salesDepartment') + ":",
	            salesdepartmentName: 'changeForm_SalesDepartment',
	            salesdepartmentIsBlank:true,
	            salesdepartmentLabelWidth:100,
	            salesdepartmentWidth:275,
	            margin:'5px 0px 5px 0px',
	            
	            type: 'D-B-R-S',
	            loadParentOrg:true,
	            formid:'changeForm',
	            xtype: 'linkorgcombselector'
	    		}
			]},{
     	    	layout: 'column',
     	    	margin: '0 10 10 10',
     			defaults: {
     				labelWidth: 100,
     				height:25,
     				labelAlign: "right"
     			},
        	 items:[{
				 name : 'changeForm_effectiveTime',
	        	 xtype : 'datetimefield',
	        	 width:275,
	        	 //labelWidth: 75,
	        	 fieldLabel : pricecard.priceCorp.i18n('pricecard.priceCorp.effectiveTime'),//'有效时间点',
	        	 beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>',
	        	 allowBlank:false,
	        	 margin:'5px 0px 5px 0px',
	        	 format : 'Y-m-d H:i:s'
	         }/*,{
                 name: 'label',
                 html:'————',
                 
                 id:'changeForm_label_01',
                 xtype: 'label'
             }*/,{
             	 name : 'changeForm_invalidTime',
             	 xtype : 'datetimefield',
             	 fieldLabel : pricecard.priceCorp.i18n('pricecard.priceCorp.invalidTime'),//'失效时间点',
             	 beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>',
             	 allowBlank:false,
     			 width: 275,
     			//labelWidth:75,
     			margin:'5px 0px 5px 0px',
             	 format : 'Y-m-d H:i:s'
              },{
             	 name : 'changeForm_remark',
             	 xtype : 'textareafield',
             	 grow      : true,
             	 height:26,
             	 fieldLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.remark'),
             	 //labelWidth:75,
             	 width:550,
             	 margin:'5px 0px 5px 0px',
             	 anchor    : '100%'
              }]
         }];
	
	me.callParent([cfg]);
   }
});


//增加窗体
Ext.define('Miser.view.pricecard.PriceCorpEntityAddWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	parent : null,
	modal : true,
	id:'addWindow_modle',
	resizable : false,
	closeAction : 'hide',
	width: document.documentElement.clientWidth - 10,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	operationType : null,
	priceCorpEntity: null,
	priceCorpDetail:null,
	oldPriceCorpEntitly:null,
	listeners : {
		beforehide : function(me) { // 隐藏WINDOW的时候清除数据
			me.getPriceCorpEntityChangeForm().getForm().reset(); // 表格重置
			me.getPriceCorpAddGrid().store.removeAll();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division').store.load();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion').store.load();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea').store.load();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').store.load();
			me.priceCorpEntity = null;
			me.priceCorpDetail = null;
			me.oldPriceCorpEntitly = null;
			me.getPriceCorpAddGrid().isEdit = true;
			
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').enable();
			me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_remark').enable();
			
			Ext.getCmp('export_button').setVisible(true);
			Ext.getCmp('downloadTmplbutton').setVisible(true);
			Ext.getCmp('insert_button').setVisible(true);
			Ext.getCmp('updateButton').setVisible(true);
			Ext.getCmp('removeInfo').setVisible(true);
			Ext.getCmp('other_up').setVisible(true);
			Ext.getCmp('other_close').setVisible(true);
			Ext.getCmp('view_add').setVisible(false);
			Ext.getCmp('view_update').setVisible(false);
			Ext.getCmp('view_del').setVisible(false);
			Ext.getCmp('view_exprot').setVisible(false);
			Ext.getCmp('view_close').setVisible(false);
			
			Ext.getCmp('view_del').setDisabled(false);
			me.operationType = null;
			
		},
		beforeshow : function(me) { // 显示WINDOW的时候清除数据
			var fielsds = me.getPriceCorpEntityChangeForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function(item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
			if(me.priceCorpEntity != null){
				me.getPriceCorpEntityChangeForm().getForm().loadRecord(new Miser.model.PriceCorpVoModel(me.priceCorpEntity));
				
				//这块是处理下拉框赋值的问题
				var DivisionCombo = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division');
				DivisionCombo.setCombValue(me.priceCorpEntity.division,me.priceCorpEntity.divisionCode);
				
				var BigRegionCombo = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion');
				BigRegionCombo.setCombValue(me.priceCorpEntity.bigRegion,me.priceCorpEntity.bigRegionCode);
				
				var RoadAreaCombo = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea');
				RoadAreaCombo.setCombValue(me.priceCorpEntity.roadArea,me.priceCorpEntity.roadAreaCode)
				
				var SalesDepartmentCombo = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment');
				SalesDepartmentCombo.setCombValue(me.priceCorpEntity.corpName,me.priceCorpEntity.corpCode)
				
				if(me.operationType != 'insert'){
					var effectiveTimedatefield = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime');
					effectiveTimedatefield.setValue(new Date(parseFloat(me.priceCorpEntity.effectiveTime)));
					
					if(me.priceCorpEntity.invalidTime != null && me.priceCorpEntity.invalidTime != ""){
						var invalidTimedatefield = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime');
						invalidTimedatefield.setValue(new Date(parseFloat(me.priceCorpEntity.invalidTime)));
					}
					
					var remarkTimedatefield = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_remark');
					remarkTimedatefield.setValue(me.priceCorpEntity.remark);
					
					me.getPriceCorpAddGrid().store.loadData(me.priceCorpDetail,true);
				}else{
					
					
				}
				
			}
		}
	},
	priceCorpEntityChangeForm:null,
	getPriceCorpEntityChangeForm:function() {
		if(Ext.isEmpty(this.priceCorpEntityChangeForm)){
			this.priceCorpEntityChangeForm=Ext.create('Miser.view.pricecard.PriceCorpEntityChangeForm')
		}
		return this.priceCorpEntityChangeForm;
	},
	priceCorpAddGrid:null,
	getPriceCorpAddGrid:function(){
		if(Ext.isEmpty(this.priceCorpAddGrid)){
			this.priceCorpAddGrid=Ext.create('Miser.view.pricecard.PriceCorpEntity.AddGrid');
		}
		return this.priceCorpAddGrid;
	},
	submitPriceCorpAddForm : function() {
		var me = this;
		if (me.getPriceCorpEntityChangeForm().getForm().isValid()) { // 校验form是否通过校验
			
			/**
			 * 生效时间不能为空校验
			 */
			var changeForm_effectiveTime = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').getValue();
			var changeForm_invalidTime = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').getValue();
			if(changeForm_effectiveTime == null || changeForm_effectiveTime ==""){
				miser.showInfoMsg(pricecard.priceCorp.i18n('pricecard.priceCorp.eftimePromptNotNull'));
						return ;
			}
			if(changeForm_invalidTime == null || changeForm_invalidTime ==""){
				miser.showInfoMsg(pricecard.priceCorp.i18n('pricecard.priceCorp.invalidPromptNotNull'));
				return ;
			}
			
			/**
			 * 校验开始时间大于结束时间
			 */
			var beginTime = new Date(changeForm_effectiveTime);
			if(changeForm_invalidTime !=null && changeForm_invalidTime!=''){
				var endTime = new Date(changeForm_invalidTime);
				if(beginTime > endTime){
					miser.showInfoMsg(pricecard.priceCorp.i18n('pricecard.priceCorp.eftimePromptVli'));
					return ;
				}
			}
			
			//判断是否需要验证生效时间，后台验证
			var isVail = me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').disabled;
			if(!isVail){
				/**
				 * 校验生效时间早于当前时间前台不做此校验
				 */
				/*var nowTime = new Date();
				if(changeForm_effectiveTime <= nowTime){
					miser.showInfoMsg(pricecard.priceCorp.i18n('pricecard.priceCorp.eftimePrompt'));
					return;
				}*/
			}
			
			var gridVoList = [];
			
			var store = me.getPriceCorpAddGrid().store.data;
			
			if(store.length <= 0){
				miser.showInfoMsg(pricecard.priceCorp.i18n('pricecard.priceCorp.saveBefoeCheckPrompt'));
				return ;
			}
			var otherParam = {
					 "corpCode":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').getValue(),
					 "invalidTime":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').getValue(),
					 "effectiveTime" :me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').getValue()
			};
			
			var oldParam = {};
			/**
			 * 记录老数据
			 */
			if(me.oldPriceCorpEntitly != null) 
			{
				oldParam = {
						"corpCode" : me.oldPriceCorpEntitly.corpCode,
						"invalidTime" : parseFloat(me.oldPriceCorpEntitly.invalidTime),
						"effectiveTime" :parseFloat(me.oldPriceCorpEntitly.effectiveTime),
						"state":me.oldPriceCorpEntitly.state
				};
			}
			for (var i = 0 ; i < store.length ; i++){
				gridVoList.push({
					"transTypeCode":store.getAt(i).get("transTypeCode"),
					"transTypeName":store.getAt(i).get("transTypeName"),
					 "corpCode":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').getValue(),
					 "corpName":getDisplayTextByValue(me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').getValue()
							 ,me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').store),
					 "arrivePriceCity":store.getAt(i).get("arrivePriceCity"),
					 "weightPrice":store.getAt(i).get("weightPrice"),
					 "volumePrice":store.getAt(i).get("volumePrice"),
					 "addFee":store.getAt(i).get("addFee"),
					 "lowestFee":store.getAt(i).get("lowestFee"),
					 "fuelAddFeeSection":store.getAt(i).get("fuelAddFeeSection"),
					 "effectiveTime":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').getValue(),
					 "invalidTime":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').getValue(),
					 "remark":me.getPriceCorpEntityChangeForm().getForm().findField('changeForm_remark').getValue()});

			}
			var params = {
			    	'priceCorpVo':{
			    			'gridVoList': gridVoList,
			    			'gridVo' : otherParam,
			    			'operationType' : !isVail + '',
			    			'oldPriceCorp': oldParam
			    		}
			};
			var successFun = function(json) {
				Ext.Msg.hide();
				miser.showInfoMsg(json.message);
				me.close();
				me.parent.getStore().load();
			};
			
			var failureFun = function(json) {
				Ext.Msg.hide();
				if(json!=undefined){
					miser.showErrorMes(json.message);
				}
			};
			Ext.Msg.wait('处理中，请稍后...', '提示');
			miser.requestJsonAjax('../pricecard/priceCorp!savePriceCorp.action',params, successFun, failureFun);
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var nitems = [{
			id:'other_up',
			text : pricecard.priceCorp.i18n('pricecard.priceCorp.submit'),
			handler : function() {
				me.submitPriceCorpAddForm();
			}
		},{
			id:'other_close',
			text : pricecard.priceCorp.i18n('pricecard.priceCorp.cancell'),
			handler : function() {
				me.close();
			}
		},{
			id:"view_add",
			hidden:true,
			text :  pricecard.priceCorp.i18n('pricecard.priceCorp.add'),
			handler : function() {
				me.close();
				Ext.getCmp('mainPanel').getFormGridName().insertMethod();
			}
		},{
			id:'view_update',
			hidden:true,
			text : pricecard.priceCorp.i18n('pricecard.priceCorp.update'),
			handler : function() {
				me.close();
				Ext.getCmp('mainPanel').getFormGridName().updateMethod();
			}
		},{
			id:'view_del',
			hidden:true,
			text :  pricecard.priceCorp.i18n('pricecard.priceCorp.delete'),
			handler : function() {
				me.close();
				Ext.getCmp('mainPanel').getFormGridName().deleteMethod();
			}
		},{
			id:'view_exprot',
			hidden:true,
			text : pricecard.priceCorp.i18n('pricecard.priceCorp.export'),
			handler : function() {
				down(me);
			}
		},{
			id:'view_close',
			hidden:true,
			text : pricecard.priceCorp.i18n('pricecard.priceCorp.close'),
			handler : function() {
				me.close();
			}
		}];
		me.fbar = nitems;
		me.items = [ me.getPriceCorpEntityChangeForm(),me.getPriceCorpAddGrid()];
		me.callParent([ cfg ]);
	}
});


function createComboBox(label,states){
	return Ext.create('Ext.form.ComboBox', {
    fieldLabel: label,
    
    store: states,
    queryMode: 'local',
    displayField: 'text',
    valueField: 'value'
	});
}

function getComboBoxStates(data,fields){
	return  Ext.create('Ext.data.Store', {
	    fields: fields,
	    data : data
	});
}
/**
 * 定义查询表单
 */
Ext.define('Miser.view.pricecard.PriceCorpEntity.QueryForm',{
	extend:'Ext.form.Panel',
	title:pricecard.priceCorp.i18n('miser.common.queryCondition'),
	id:'queryForm',
	//height: 140,
	frame: true,
    collapsible: true,
    region: 'north',
    defaults: {
    	labelWidth: 0.4,
    	columnWidth: 0.6,
        //margin: '0 10 0 10',
    	margin : '8 10 5 10',
		labelAlign : 'right'
    },
	constructor: function(config) {
	  var me = this,
	  cfg = Ext.apply({},config);
	  me.items = [{
	    	layout: 'column',
			defaults: {
				labelWidth: 70,
				labelAlign: "right"
					
			},
			items:[{
				name: '',
				labelWidth: 5,
				flex:1.42,	
				loaddefault:true,  
				width : document.documentElement.clientWidth-20,
				//height : 28,
				//所属事业部
	            divisionLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.division') + ":",
	            name:'priceCorpVo.searchVo.division',
	            divisionName: 'priceCorpVo.searchVo.division',
	            divisionIsBlank:true,
	            divisionLabelWidth:100,
	            divisionWidth:276,
	            margin:'5px 0px 5px 0px',
	            //所属大区
	            bigregionLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.bigRegion') + ":",
	            name:'priceCorpVo.searchVo.bigRegion',
	            bigregionName: 'priceCorpVo.searchVo.bigRegion',
	            bigregionIsBlank:true,
	            bigregionLabelWidth:100,
	            bigregionWidth:276,
	            margin:'5px 0px 5px 0px',
	            // 所属路区
	            roadareaLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.roadArea') + ":",
	            name:'priceCorpVo.searchVo.roadArea',
	            roadareaName: 'priceCorpVo.searchVo.roadArea',
	            roadareaIsBlank:true,
	            roadareaLabelWidth:100,
	            roadareaWidth:276,
	            margin:'5px 0px 5px 0px',
	            //所属门店
	            salesdepartmentLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.salesDepartment') + ":",
	            name:'priceCorpVo.searchVo.sales',
	            salesdepartmentName: 'priceCorpVo.searchVo.sales',
	            salesdepartmentIsBlank:true,
	            salesdepartmentLabelWidth:100,
	            salesdepartmentWidth:276,
	            margin:'5px 0px 5px 0px',
	            
	            type: 'D-B-R-S',
	            loadParentOrg:true,
	            formid:'queryForm',
	            xtype: 'linkorgcombselector'
	    		}
			]},
			{
				layout: 'hbox',
				//margin: '-5 0 0 10',
				defaults: {
					labelWidth: 100,
					height : 25,
					labelAlign: "right",
					margins : '10 10 10 10'
				},
				items:[{
					xtype:'combobox',
					name : 'priceCorpVo.searchVo.state',
					fieldLabel: pricecard.priceCorp.i18n('pricecard.priceCorp.courrentState'),
				    store: getComboBoxStates(stateData,comboBoxFields),
				    queryMode: 'local',
				    displayField: 'text',
				    width:276,
				    margin:'5px 0px 5px 0px',
				    valueField: 'value'
				},
		         {
					 name : 'priceCorpVo.searchVo.effective',
		        	 xtype : 'datetimefield',
		        	 width:276,
		        	 margin:'5px 0px 5px 0px',
		        	 fieldLabel : pricecard.priceCorp.i18n('pricecard.priceCorp.effectiveTime'),
		        	 format : 'Y-m-d H:i:s'
		         }]
			}
	];
	me.buttons = [{
		text:pricecard.priceCorp.i18n('pricecard.priceCorp.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:pricecard.priceCorp.i18n('pricecard.priceCorp.view'),
		handler: function() {
			me.up().getFormGridName().viewMethod();
		}
	}];
	me.callParent([cfg]);
   }
});

/**
 * 定义表格数据Model
 */
Ext.define('Miser.model.pricecard.PriceCorpEntityModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'division',
		type:'string'
	},{
		name:'bigRegion',
		type:'string'
	},{
		name:'roadArea',
		type:'string'
	},{
		name:'corpCode',
		type:'string'
	},{
		name:'corpName',
		type:'string'
	},{
		name:'effectiveTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'invalidTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'state',
		type:'string'
	},{
		name:'remark',
		type:'string'
	},{
		name:'createUserCode',
		type:'string'
	},{
		name:'createTime',
		dateFormat:'time',
		type:'date'
	},{
		name:'modifyUserCode',
		type:'string'
	},{
		name:'modifyTime',
		dateFormat:'time',
		type:'date'
	}
	]
});

/**
 * 定义表格数据源
 */
Ext.define('Miser.store.pricecard.PriceCorpEntityStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.pricecard.PriceCorpEntityModel',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../pricecard/priceCorp!queryBySearch.action',
		reader: {
			type: 'json',
			rootProperty: 'priceCorpVo.gridVoList',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			
			if (queryForm != null) {
			  var params = {};
			  params = {
					  'priceCorpVo.searchVo.division':queryForm.getForm().findField("priceCorpVo.searchVo.division").getValue(),
					  'priceCorpVo.searchVo.bigRegion':queryForm.getForm().findField("priceCorpVo.searchVo.bigRegion").getValue(),
					  'priceCorpVo.searchVo.roadArea':queryForm.getForm().findField("priceCorpVo.searchVo.roadArea").getValue(),
					  'priceCorpVo.searchVo.sales':queryForm.getForm().findField("priceCorpVo.searchVo.sales").getValue(),
					  'priceCorpVo.searchVo.state':queryForm.getForm().findField("priceCorpVo.searchVo.state").getValue(),
					  'priceCorpVo.searchVo.effective':queryForm.getForm().findField("priceCorpVo.searchVo.effective").getValue()
			  };
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});

Ext.define('Miser.model.pricecard.PriceCorpDetailModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'transTypeCode',
		type:'string'
	},{
		name:'transTypeName',
		type:'string'
	},{
		name:'arrivePriceCityName',
		type:'string'
	},{
		name:'arrivePriceCity',
		type:'string'
	},{
		name:'weightPrice',
		type:'string'
	},{
		name:'volumePrice',
		type:'string'
	},{
		name:'addFee',
		type:'string'
	},{
		name:'lowestFee',
		type:'string'
	},{
		name:'fuelAddFeeSection',
		type:'string'
	},{
		name:'fuelAddFeeSectionName',
		type:'string'
	}]
});

Ext.define('Miser.store.pricecard.PriceCorpDetailStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.pricecard.PriceCorpDetailModel'
});

Ext.define('Miser.view.pricecard.PriceCorpEntity.AddGrid',{
	extend:'Ext.grid.Panel',
	frame: true,
	autoScroll: true,
	height: miser.getBrowserHeight() - 180,
	stripeRows: true,
	selType: 'rowmodel',
	columnLines: true,
	viewConfig: {
		enableTextSelection: true
	},
	isEdit:true,
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [
		{
		    xtype : 'rownumberer',
		    align : 'center',
		    sortable : false
		},{
			dataIndex:'arrivePriceCity',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.arriveCity'),//'到达价格城市',
			editor: {
				allowBlank: false,
				xtype:'arrivePriceCityselector',
				id:"combo_arrive"
			},
			renderer: function(value,o,m){
				/*if(m.data.arrivePriceCityName != ""){
					return m.data.arrivePriceCityName;
				}*/
				var display = value;
				var sto = Ext.getCmp('combo_arrive');
				if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0){
					display = getDisplayText(sto.getStore(),value);
					if(display == value){
						display = m.data.arrivePriceCityName;
					}
				}
				else{
					display = m.data.arrivePriceCityName;
				}
				
				m.data.arrivePriceCityName = display;
				return m.data.arrivePriceCityName;
			}
		},{
			dataIndex:'transTypeCode',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.transType'),//'运输类型',
			editor: {
				xtype:'transtypecombselector',
				allowBlank: false,
				showAll:false,
				containsRoot:false,
				id :"combo_trtype"
			},
			renderer: function(value,o,m){
				/*if(m.data.transTypeName != ""){
					return m.data.transTypeName;
				}*/
				var display = value;
				var sto = Ext.getCmp('combo_trtype');
				if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0){
					display = getDisplayTextCommon(sto.getStore(),value);
					if(display == value){
						display = m.data.transTypeName;
					}
				}
				else{
					display = m.data.transTypeName;
				}
				m.data.transTypeName = display;
				return m.data.transTypeName;
				
			 }
		},{
			dataIndex:'weightPrice',
			width:120, 
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				negativeText : pricecard.priceCorp.i18n('pricecard.priceCorp.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
					
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.weightPrice')//'重量单价'
		},{
			dataIndex:'volumePrice',
			width:120,
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				negativeText : pricecard.priceCorp.i18n('pricecard.priceCorp.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.volumePrice')//'体积单价'
			
		},{
			dataIndex:'addFee',
			width:120,
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				allowDecimals :true,
				decimalPrecision:2,
				negativeText : pricecard.priceCorp.i18n('pricecard.priceCorp.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.addFee')//'附加费'
			
		},{
			dataIndex:'lowestFee',
			width:120,
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				allowDecimals :true,
				decimalPrecision:2,
				negativeText : pricecard.priceCorp.i18n('pricecard.priceCorp.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.lowestFee')//'最低收费'
		},{
			dataIndex:'fuelAddFeeSection',
			width:150,
			align : 'center',
			editor:{
				sectionedItem:'FUEL',
				xtype:'dynamicPriceSectioncombselector',
				id:'combo_full'
			},
			renderer: function(value,o,m){
				/*if(m.data.fuelAddFeeSectionName != ""){
					return m.data.fuelAddFeeSectionName;
				}*/
				var display = value;
				var sto = Ext.getCmp('combo_full');
				if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0){
					display = getDisplayTextCommon(sto.getStore(),value);
					if(display == value){
						display = m.data.fuelAddFeeSectionName;
					}
				}
				else{
					display = m.data.fuelAddFeeSectionName;
				}
				
				m.data.fuelAddFeeSectionName = display;
				return m.data.fuelAddFeeSectionName;
			 },
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.fuelAddFeeSection')//'燃油服务费'
		}],
		me.store = Ext.create('Miser.store.pricecard.PriceCorpDetailStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = ['->',{
				id:'insert_button',
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.add'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
	                var r = Ext.create('Miser.store.pricecard.PriceCorpDetailStore');
	                me.store.insert(me.store.data.length+1, r);
	                rowEditing.startEdit(me.store.data.length-1, 0);
				}
			}, '-',{
				id : 'updateButton',
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.update'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
					var selections = me.getSelectionModel().getSelection();
					if(selections.length < 1){
						 miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.updatePrompt'));
						 return;
					}
					var selectData = selections[0];
					var dataIndex = me.store.indexOf(selectData);
					rowEditing.startEdit(dataIndex, 0);
					
				}
			}, '-',{
				itemId : "removeInfo",
				id:"removeInfo",
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.deleteV'),
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
					var selections = me.getSelectionModel().getSelection();
					if(selections.length < 1){
						 miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.delPrompt'));
						 return;
					}
					for(var i = 0 ; i < selections.length ; i++){
						var selectData = selections[i];
						var dataIndex = me.store.remove(selectData);
					}
					
				}
			},'-',{
				id : 'export_button',
				text: pricecard.priceCorp.i18n('pricecard.priceCorp.inexport'),//'导入',
				xtype: 'uploadbutton',
				width: 100,
				handler: function() {
					uploadT.excelWindow(null,imple,Ext.getCmp("addWindow_modle"));
				}
			}, '-',{
				id:'downloadTmplbutton',
				text: pricecard.priceCorp.i18n('pricecard.priceCorp.inexportTemp'),//'导入模板下载',
				xtype: 'downloadbutton',
				width: 130,
				handler: function() {
					miser.requestExportAjax('/excelTemplate/priceCorp.xls');
				}
			}
			];
		
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners: {
				selectionchange: function(sm, selections) {
					Ext.getCmp('removeInfo').setDisabled(selections.length == 0);
					Ext.getCmp('updateButton').setDisabled(selections.length == 0);
			 	}
			}
		});
		me.callParent([cfg]);
	},
	plugins:[rowEditing]
	
});
Ext.define('Miser.view.pricecard.PriceCorpEntity.Grid',{
	extend:'Ext.grid.Panel',
	frame: true,
	autoScroll: true,
	height: miser.getBrowserHeight() - 165,
	stripeRows: true,
	selType: 'rowmodel',
	emptyText:pricecard.priceCorp.i18n('pricecard.priceCorp.queryReult'),
	columnLines: true,
	viewConfig: {
		enableTextSelection: true
	},
	priceCorpEntityAddWindow:null,
	getpriceCorpEntityAddWindow:function() {
		if(this.priceCorpEntityAddWindow== null) {
			this.priceCorpEntityAddWindow=Ext.create('Miser.view.pricecard.PriceCorpEntityAddWindow');
			this.priceCorpEntityAddWindow.parent = this;
		}
		return this.priceCorpEntityAddWindow;
	},
	updateMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.updatePrompt')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var corpCode = selections[0].get('corpCode');
		var effectiveTime = selections[0].get('effectiveTime');
		var invalidTime = selections[0].get('invalidTime');
		var division = selections[0].get('division');
		var bigRegion = selections[0].get('bigRegion');
		var roadArea = selections[0].get('roadArea');
		var state=selections[0].get('state');
		var addwindow = me.getpriceCorpEntityAddWindow();
		addwindow.setTitle(pricecard.priceCorp.i18n('pricecard.priceCorp.updateInfoTitle'));
		addwindow.operationType = 'update';
		if(state == 'EFFECTIVE'){
			//生效中的数据不能更改生效时间
			
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').disable();
			
		}else if(state == 'WAIT'){
			//待生效的数据无限制但生效时间必须晚于当前时间+1小时
			
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea').disable();
			addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').disable();
			
		}
		else if(state == 'PASSED' || state == 'DELETED'){
			//已失效数据不能修改
			miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.optPrompt')); 
			return ;
		}
		var params = {
				'priceCorpVo' :{
					'gridVo' : {
						'corpCode' : corpCode,
						'effectiveTime' : effectiveTime,
						'invalidTime' : invalidTime,
						'division':division,
						'bigRegion' : bigRegion,
						'roadArea':roadArea,
						'state':state
					}
				}
			};
		var successFun = function (json) {
			addwindow.oldPriceCorpEntitly = json.priceCorpVo.gridVo;
			addwindow.priceCorpEntity = json.priceCorpVo.gridVo;
			addwindow.priceCorpDetail = json.priceCorpVo.gridVoList;
			addwindow.show();
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.timeOut')); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../pricecard/priceCorp!searchPriceCorp.action', params, successFun, failureFun);
	},
	insertMethod:function(){
		//自动带入选中记录信息。
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		var addwindow = me.getpriceCorpEntityAddWindow();
		addwindow.setTitle(pricecard.priceCorp.i18n('pricecard.priceCorp.addTitle'));
		addwindow.operationType = 'insert';
		if (selections.length == 1) { //判断选中了一条
			var corpCode = selections[0].get('corpCode');
			var effectiveTime = selections[0].get('effectiveTime');
			var invalidTime = selections[0].get('invalidTime');
			var division = selections[0].get('division');
			var bigRegion = selections[0].get('bigRegion');
			var roadArea = selections[0].get('roadArea');
			var state=selections[0].get('state');
			
			var params = {
					'priceCorpVo' :{
						'gridVo' : {
							'corpCode' : corpCode,
							'effectiveTime' : effectiveTime,
							'invalidTime' : invalidTime,
							'division':division,
							'bigRegion' : bigRegion,
							'roadArea':roadArea,
							'state':state
						}
					}
				};
			var successFun = function (json) {
				addwindow.priceCorpEntity = json.priceCorpVo.gridVo;
				addwindow.priceCorpDetail = json.priceCorpVo.gridVoList;
				addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').setValue(new Date(json.priceCorpVo.currentTime));
				addwindow.show();
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.timeOut')); //请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../pricecard/priceCorp!searchPriceCorp.action', params, successFun, failureFun);
		}else{
			var params = {};
			var successFun = function (json) {
				addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').setValue(new Date(json.priceCorpVo.currentTime));
				addwindow.show();
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.timeOut')); //请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../pricecard/priceCorp!currentTime.action', params, successFun, failureFun);
		}
	},
	viewMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.viewPrompt')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var corpCode = selections[0].get('corpCode');
		var effectiveTime = selections[0].get('effectiveTime');
		var invalidTime = selections[0].get('invalidTime');
		var division = selections[0].get('division');
		var bigRegion = selections[0].get('bigRegion');
		var roadArea = selections[0].get('roadArea');
		var state=selections[0].get('state');
		var addwindow = me.getpriceCorpEntityAddWindow();
		addwindow.setTitle(pricecard.priceCorp.i18n('pricecard.priceCorp.viewTitle'));
		addwindow.operationType = 'view';
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_Division').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_BigRegion').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_RoadArea').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_SalesDepartment').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_effectiveTime').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_invalidTime').disable();
		addwindow.getPriceCorpEntityChangeForm().getForm().findField('changeForm_remark').disable();
		me.getpriceCorpEntityAddWindow().getPriceCorpAddGrid().isEdit = false;
		Ext.getCmp('export_button').setVisible(false);
		Ext.getCmp('downloadTmplbutton').setVisible(false);
		Ext.getCmp('insert_button').setVisible(false);
		Ext.getCmp('updateButton').setVisible(false);
		Ext.getCmp('removeInfo').setVisible(false);
		Ext.getCmp('other_up').setVisible(false);
		Ext.getCmp('other_close').setVisible(false);
		Ext.getCmp('view_add').setVisible(true);
		Ext.getCmp('view_update').setVisible(true);
		Ext.getCmp('view_del').setVisible(true);
		Ext.getCmp('view_exprot').setVisible(true);
		Ext.getCmp('view_close').setVisible(true);
		
    	if(state!='WAIT'&&state!='EFFECTIVE'){
    		Ext.getCmp('view_del').setDisabled(true);
		}
		
		
		var params = {
				'priceCorpVo' :{
					'gridVo' : {
						'corpCode' : corpCode,
						'effectiveTime' : effectiveTime,
						'invalidTime' : invalidTime,
						'division':division,
						'bigRegion' : bigRegion,
						'roadArea':roadArea,
						'state' : state
					}
				}
			};
		var successFun = function (json) {
			addwindow.priceCorpEntity = json.priceCorpVo.gridVo;
			addwindow.priceCorpDetail = json.priceCorpVo.gridVoList;
			addwindow.show();
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.timeOut')); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../pricecard/priceCorp!searchPriceCorp.action', params, successFun, failureFun);
	},
	pagingToolbar: null,
	getPagingToolbar: function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
				store: this.store,
				pageSize: 20
			})
		}
		return this.pagingToolbar;
	},
	deleteMethod: function() {
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if (selections.length < 1) { // 判断是否至少选中了一条
            miser.showWoringMessage(pricecard.priceCorp.i18n('pricecard.priceCorp.delPrompt')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objects='';
        var objectLeng=0;
        for(var i=0;i<selections.length;i++){
        	var object="{"
        	var state=selections[i].get('state');
        	if(state!='WAIT'&&state!='EFFECTIVE'){
    			miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.optPrompt'));
    			return;
    		}
    		if(stateType==''){
    			stateType=state;
    		}else if(stateType!=state) {
    			miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.optPrompt01'));
    			return;
    		}
    		
    		var corpCode = selections[0].get('corpCode');
			var effectiveTime = selections[0].get('effectiveTime');
			var invalidTime = selections[0].get('invalidTime');
			var division = selections[0].get('division');
			var bigRegion = selections[0].get('bigRegion');
			var roadArea = selections[0].get('roadArea');
			var state=selections[0].get('state');
    		
			if(effectiveTime != ""){
				effectiveTime = new Date(effectiveTime).valueOf();
			}
			if(invalidTime !=null && invalidTime != ""){
				invalidTime = new Date(invalidTime).valueOf();
			}	
			
        	object+="\"corpCode\":\""+ corpCode +"\",\"state\":\""+ state +"\"" 
        		+",\"effectiveTime\":\""+ effectiveTime + "\"" 
        		+",\"invalidTime\":"+ invalidTime + "" ;
        	object+="}"
        	if(i!=selections.length-1)
        	object+=","
        	objects+=object;
        	objectLeng++;
        }
        var msg=pricecard.priceCorp.i18n('pricecard.priceCorp.mesPrefix')+objectLeng
        	+pricecard.priceCorp.i18n('pricecard.priceCorp.mesSuffix');
		miser.showQuestionMes(msg,
		function(e) {
			if (e == 'yes') {
				var params = {
						'priceCorpVo': {
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
						miser.showErrorMes(pricecard.priceCorp.i18n('pricecard.priceCorp.timeOut')); // 请求超时
					}else{
						var message = json.message;
						miser.showErrorMes(message);
					}
				};
				miser.requestJsonAjax('../pricecard/priceCorp!deletePriceCorp.action', params, successFun, failureFun);
			 }
		});
	},
	
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [
		{
		    xtype : 'rownumberer',
		    align : 'center',
		    sortable : false
		},{
			dataIndex:'division',
			width:100,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.division')
		},{
			dataIndex:'bigRegion',
			width:100,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.bigRegion')
		},{
			dataIndex:'roadArea',
			width:100,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.roadArea')
		},{
			dataIndex:'corpName',
			width:100, 
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.salesDepartment')
		},{
			dataIndex:'logisticName',
			width:100,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.logisticName')
		},{
			dataIndex:'effectiveTime',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.effectiveGridTime'),
			renderer:function(value){
				return dateRender(value,'Y-m-d H:i:s');
			}
		},{
			dataIndex:'invalidTime',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.invalidGridTime'),
			renderer:function(value){
				return dateRender(value,'Y-m-d H:i:s');
			}
		},{
			dataIndex:'state',
			width:100,
			align : 'center',
			renderer: function(value) {
                return stateTypeRender(value);
            },
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.courrentState')
		},{
			dataIndex:'remark',
			width:200,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.remark')
		},{
			dataIndex:'createUserCode',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.createUser')
		},{
			dataIndex:'createTime',
			width:150,
			align : 'center',
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.createDateTime')
		},{
			dataIndex:'modifyUserCode',
			width:150,
			align : 'center',
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.modifyUser')
		},{
			dataIndex:'modifyTime',
			width:150,
			align : 'center',
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:pricecard.priceCorp.i18n('pricecard.priceCorp.modifyTime')
		}
		],
		me.store = Ext.create('Miser.store.pricecard.PriceCorpEntityStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.add'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.insertMethod();
				
				}
			},
			'-',{
				id : 'update_id',
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.update'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:pricecard.priceCorp.i18n('pricecard.priceCorp.delete'),
				id: 'del_id',
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					me.deleteMethod();
				}
			}
			];
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners: {
				selectionchange: function(sm, selections) {
						if(selections.length == 1){
							var state = selections[0].get('state');
							Ext.getCmp('del_id').setDisabled(!(state == 'WAIT' || state == 'EFFECTIVE') )
						}else{
							Ext.getCmp('del_id').setDisabled(true);
						}
						Ext.getCmp('update_id').setDisabled(selections.length == 0);
			 		}
			}
		});
		
		me.callParent([cfg]);
	},
	listeners:{
		itemdblclick:function(view,model,item,index,e,eOpts){
			this.viewMethod();
		}
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.pricecard.PriceCorpEntity.QueryForm');
	var formGridName = Ext.create('Miser.view.pricecard.PriceCorpEntity.Grid');
	var mainPanel = Ext.create('Ext.panel.Panel',{
		id:'mainPanel',
		getQueryForm: function() {
			return queryForm;
		},
		getFormGridName: function() {
			return formGridName;
		},
		renderTo: Ext.getBody(),
		items: [queryForm,formGridName]
	});
	
	 //设置窗口大小改变时的响应
    window.onresize = function(){
    
        //控制整个 panel 的宽度
    	mainPanel.setWidth(document.documentElement.clientWidth);
    	
    	//控制整个 查询表单 的宽度
    	queryForm.setWidth(document.documentElement.clientWidth - 10);
        
        //设置区域一行表单的宽度
    	var items = queryForm.items;
    	var targetObj = items.items[0].items.items[0];
		targetObj.setWidth(document.documentElement.clientWidth - 10);
		
		//设置区域下一行表单的宽度
		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (document.documentElement.clientWidth - 10)+'px';
		
		//设置查询结果的宽度
		formGridName.setWidth(document.documentElement.clientWidth - 10);
    };
})
