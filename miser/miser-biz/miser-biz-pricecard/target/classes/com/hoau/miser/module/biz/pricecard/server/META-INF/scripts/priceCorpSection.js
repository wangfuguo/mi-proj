//文件上传excel
var uploadT={
	oufileName:null,
	excelWindow:function(url,fn,me){
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
									fn(action.result.outFileName,true,me);
								}else{
									fn(true);
								}
								 xwindow.destroy();
							},
							failure : function(fp, action) {
								if(flag){
									fn('服务器异常',false,me);
								}else{
									fn(false);
								}
								fn(action.result.outFileName,true,me);
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
function imple(outFileName,flag,me){
	if(flag){
		miser.showInfoMsg('上传完成');
		Ext.Msg.wait('处理中，请稍后...', '提示');
		Ext.Ajax.request( {  
		   timeout: 180000,
	       url : '../pricecard/priceCorpSection!impl.action',  
	       method : 'post',  
	       params : {  
	    	    'vo.filePath' : outFileName
	          },  
	          success : function(response, options) {  
	              Ext.Msg.hide();   
	              var responseArray = Ext.util.JSON.decode(response.responseText);
	              // succCount:成功条数,failCount:失败条数, list:明细记录.
	              if(responseArray.repeatTip == ""){//如果没有重复，填充表单
	            	  var msg="成功：" + responseArray.succCount + "条；失败：" + responseArray.failCount + "条；";
	            	  miser.showInfoMsg(msg);         
	            	  me.store.loadData(responseArray.list,false);
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

function down(me){
	Ext.Msg.wait('处理中，请稍后...', '提示');
	
	if(!(me.store.getRange().length > 0)){
		 Ext.Msg.hide(); 
		 miser.showInfoMsg("没有数据需要导出");
	}
	
	var corpCode =  me.store.getRange()[0].get("corpCode");
	var effectiveTime = me.up().getPriceCorpSectionAddForm().getForm().findField("effectiveTime").getValue();
	var invalidTime = me.up().getPriceCorpSectionAddForm().getForm().findField("invalidTime").getValue();
	var state = me.store.getRange()[0].get('state');
	
	var params1 = {
			'vo.priceCorpSection.corpCode' :  corpCode,
			'vo.priceCorpSection.effectiveTime' :  effectiveTime,
			'vo.priceCorpSection.invalidTime' :  invalidTime,
			'vo.priceCorpSection.state' :  state
	};
	
	Ext.Ajax.request( {  
       url : '../pricecard/priceCorpSection!export.action',  
       method : 'POST',  
       params : params1,  
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

//用于实现修改页面 查看详情页面的根据 到达价格城市 搜索功能
function subCustomerDetailSearch(grid,searchId){
	var subViewGrid = grid
	var sm = subViewGrid.getSelectionModel();
	var searchArrivePriceCityVal = Ext.String.trim(Ext.getCmp(searchId).getValue());
	if(Ext.isEmpty(searchArrivePriceCityVal)){
		miser.showErrorMes('请填写搜索的内容');
		return;
	}
	
	//用于存放最后匹配搜索的值的数据集
	var toSelectedRowDataArr = [];
	
	//用于存放未匹配搜索值的数据集
	var notSelectedRowDataArr = [];

    //用于存放需要搜索的字段
	var keyArr = ['effectiveTime','invalidTime','transTypeCode','transTypeName',
	              'arrivePriceCityName','arrivePriceCity','firstWeight','firstWeightPrice',
	              'firstAddWeightPrice','secondWeight','secondWeightPrice',
	              'secondAddWeightPrice','thirdWeight','thirdWeightPrice',
	              'thirdAddWeightPrice','freightSectionCode','freightSectionName'];
	
	//存放搜索字段的值集合
	var curFieldVal = null;

	var isToSelected = false;

	//循环遍历搜索匹配搜索的值的数据
	for(var j=0;j<subViewGrid.store.data.items.length;j++){

		valArr = [];

		var curRowData = subViewGrid.store.data.items[j];

		for(var k=0;k<keyArr.length;k++){
			curFieldVal = curRowData.data[keyArr[k]];
			if(!Ext.isEmpty(curFieldVal)){
				var existedIdx = curFieldVal.indexOf(searchArrivePriceCityVal);
				if(existedIdx != -1){
					isToSelected = true;
					toSelectedRowDataArr.push(curRowData);
					break;
				}
			}
		}

		if(isToSelected == false){
			notSelectedRowDataArr.push(curRowData);
		}

		isToSelected = false;
	}
	
	//将当前 grid store 的数据全移除 界面此时会自动刷新为空
	subViewGrid.store.removeAll();
	
	//将要重新添加的两个数组合并 toSelectedRowDataArr 的明细放前面
	//注意是将 返回值返回 而不是直接加入到 toSelectedRowDataArr
	var storeLocalReloadDataArr = toSelectedRowDataArr.concat(notSelectedRowDataArr);
	
	//重新加载对应明细数据
	subViewGrid.store.loadData(storeLocalReloadDataArr);

	//调用 select 选中传入的数据明细
	sm.select(toSelectedRowDataArr);

	toSelectedRowDataArr = [];
	notSelectedRowDataArr = [];
	storeLocalReloadDataArr = [];
	isToSelected = false;
}

Ext.define('Miser.model.pricecard.priceCorpSection.detailModel',{
	extend:'Ext.data.Model',
	fields: [{
		name:'effectiveTime',
		type:'date'
	},{
		name:'invalidTime',
		type:'date'
	},{
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
		name:'firstWeight',
		type:'string'
	},{
		name:'firstWeightPrice',
		type:'string'
	},{
		name:'firstAddWeightPrice',
		type:'string'
	},{
		name:'secondWeight',
		type:'string'
	},{
		name:'secondWeightPrice',
		type:'string'
	},{
		name:'secondAddWeightPrice',
		type:'string'
	},{
		name:'thirdWeight',
		type:'string'
	},{
		name:'thirdWeightPrice',
		type:'string'
	},{
		name:'thirdAddWeightPrice',
		type:'string'
	},{
		name:'freightSectionCode',
		type:'string'
	},{
		name:'freightSectionName',
		type:'string'
	}]
});

Ext.define('Miser.store.pricecard.priceCorpSection.detailStore',{
	extend:'Ext.data.Store',
	model:'Miser.model.pricecard.priceCorpSection.detailModel'
});

var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    autoCancel: false,
    saveBtnText : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.submit'),
    cancelBtnText : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.cancell'),
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
			if(dat.firstWeight!=0){
				if(dat.firstWeightPrice==''||dat.firstWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyyd01'));
					rowEditing.startEdit(o.rowIdx,5);
					return;
				}
				if(dat.firstAddWeightPrice==''||dat.firstAddWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyyd02'));
					rowEditing.startEdit(o.rowIdx,6);
					return;
				}
			}else{
				miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyyd03'));
				rowEditing.startEdit(o.rowIdx,4);
				return;
			}
			//2段不为空时检验 2段单价不能为空或不能为0
			if(dat.secondWeight!=''){
				if(!(dat.secondWeight > dat.firstWeight)){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyed01'));
					rowEditing.startEdit(o.rowIdx,7);
					return;
				}
				if(dat.secondWeightPrice==''||dat.secondWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyed02'));
					rowEditing.startEdit(o.rowIdx,8);
					return;
				}
				if(dat.secondAddWeightPrice==''||dat.secondAddWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jyed03'));
					rowEditing.startEdit(o.rowIdx,9);
					return;
				}
			}
			//3段不为空时检验 3段单价不能为空或不能为0
			if(dat.thirdWeight!=''){
				if(dat.secondWeight == ''){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jysd01'));
					rowEditing.startEdit(o.rowIdx,7);
					return;
				}
				if(!(dat.thirdWeight > dat.secondWeight)){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jysd01'));
					rowEditing.startEdit(o.rowIdx,10);
					return;
				}
				if(dat.thirdWeightPrice==''||dat.thirdWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jysd02'));
					rowEditing.startEdit(o.rowIdx,11);
					return;
				}
				if(dat.thirdAddWeightPrice==''||dat.thirdAddWeightPrice == 0){
					miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.jysd03'));
					rowEditing.startEdit(o.rowIdx,12);
					return;
				}
			}
			for(var i = 0 ; i< sdat.length ; i++){
				if(sdat[i].data.arrivePriceCity == dat.arrivePriceCity
						&& sdat[i].data.transTypeCode == dat.transTypeCode) {
					count++;
					if(count >= 2){
						miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.identical')+ (inx+1));
						rowEditing.startEdit(o.rowIdx,0);
						return;
					}else{
						inx = i;;
					}
				}
			}
			if(!this.grid.isDel){
				Ext.getCmp('combo_full').onTrigger1Click();
				Ext.getCmp('combo_trtype').onTrigger1Click();
				Ext.getCmp('combo_arrive').onTrigger1Click();
			}
		},
		cancelEdit:function (e,o){
			//防止直接取消
			try{
				if(this.grid.isDel){
					e.grid.store.removeAt(o.rowIdx);
				}else{
					var dat = e.grid.store.getAt(o.rowIdx).data;
					if(dat.arrivePriceCity == "" || dat.transTypeName =="" || dat.firstWeight == "" || dat.firstWeightPrice == "" || dat.firstAddWeightPrice == ""){
						e.grid.store.removeAt(o.rowIdx);
					}else if(o.store.data.items[o.rowIdx].data.arrivePriceCity =="" &&
							o.store.data.items[o.rowIdx].data.transTypeName == ""){
						e.grid.store.removeAt(o.rowIdx);
					}
				}
				Ext.getCmp('combo_full').onTrigger1Click();
				Ext.getCmp('combo_trtype').onTrigger1Click();
				Ext.getCmp('combo_arrive').onTrigger1Click();
			}catch (e) {
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
					Ext.getCmp('combo_full').setCombValue(dat.freightSectionName,dat.freightSectionCode);
				}
			}catch(e){
			}
			return this.grid.isEdit;
		}
		
	}
});
    

Ext.define('Miser.view.pricecard.priceCorpSection.AddGrid',{
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
	isDel:false,
	constructor: function(config) {
		var me=this,
		cfg = Ext.apply({},config);
		me.columns = [
		{
		    xtype : 'rownumberer',
		    align : 'center',
		    sortable : false
		},{
			dataIndex:'transTypeCode',
			width:80,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.transType'),//'运输类型',
			editor: {
				xtype:'transtypecombselector',
				allowBlank: false,
				showAll:false,
				containsRoot:false,
				id :"combo_trtype"
			},
			renderer: function(value,o,m){
				try{
					var dispaly = Ext.getCmp("combo_trtype").rawValue;
					var val = Ext.getCmp("combo_trtype").realValue;
					if(value == val){
						if(dispaly != ""){
							m.data.transTypeName = dispaly
						}
					}
					
				}catch (e) {
				}
				
				return m.data.transTypeName;
			 }
		},{
			dataIndex:'arrivePriceCity',
			width:110,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.arriveCity'),//'到达价格城市',
			editor: {
				allowBlank: false,
				xtype:'arrivePriceCityselector',
				id:"combo_arrive"
			},
			renderer: function(value,o,m){
				try{
					
					var dispaly = Ext.getCmp("combo_arrive").rawValue;
					var val = Ext.getCmp("combo_arrive").realValue;
					if(value == val){
						if(dispaly != ""){
							m.data.arrivePriceCityName = dispaly
						}
					}
					
				}catch (e) {
				}
				return m.data.arrivePriceCityName;
			}
		},{
			dataIndex:'firstWeight',
			width:80, 
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				negativeText : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
					
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.first')//'首重'
		},{
			dataIndex:'firstWeightPrice',
			width:110,
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				negativeText : pricecard.priceCorpSection.i18n('pricecard.priceCorp.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.firstPrice')//'单价'
			
		},{
			dataIndex:'firstAddWeightPrice',
			width:110,
			align : 'center',
			editor:{
				allowBlank:false,
				minValue: 0,
				allowDecimals :true,
				decimalPrecision:2,
				negativeText : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.negativeText'),
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.firstAdd')//'续重'
			
		},{
			dataIndex:'secondWeight',
			width:80, 
			align : 'center',
			editor:{
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				maxLength: 13,
				xtype:'numberfield'
					
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.second')//'首重2段'
		},{
			dataIndex:'secondWeightPrice',
			width:110,
			align : 'center',
			editor:{
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.secondPrice')//'单价'
			
		},{
			dataIndex:'secondAddWeightPrice',
			width:110,
			align : 'center',
			editor:{
				minValue: 0,
				allowDecimals :true,
				decimalPrecision:2,
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.secondAdd')//'续重'
			
		},{
			dataIndex:'thirdWeight',
			width:80, 
			align : 'center',
			editor:{
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				maxLength: 13,
				xtype:'numberfield'
					
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.third')//'首重3段'
		},{
			dataIndex:'thirdWeightPrice',
			width:110,
			align : 'center',
			editor:{
				minValue: 0,
				decimalPrecision:2,
				allowDecimals :true,
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.thirdPrice')//'单价'
			
		},{
			dataIndex:'thirdAddWeightPrice',
			width:110,
			align : 'center',
			editor:{
				minValue: 0,
				allowDecimals :true,
				decimalPrecision:2,
				maxLength: 13,
				xtype:'numberfield'
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.thirdAdd')//'续重'
			
		},{
			dataIndex:'freightSectionCode',
			width:120,
			align : 'center',
			editor:{
				sectionedItem:'FUEL',
				xtype:'dynamicPriceSectioncombselector',
				sectionedItem:'FREIGHT',
				id:'combo_full'
			},
			renderer: function(value,o,m){
				try{
					
					var dispaly = Ext.getCmp("combo_full").rawValue;
					var val = Ext.getCmp("combo_full").realValue;
					if(value == val){
						if(dispaly != ""){
							m.data.freightSectionName = dispaly
						}
					}
				}catch (e) {
				}
				return m.data.freightSectionName;
			 },
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.trsection')//'分段'
		}],
		me.store =new Ext.create('Miser.store.pricecard.priceCorpSection.detailStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = ['->',{
				id:'insert_button',
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.add'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
	                var r =  Ext.create('Miser.store.pricecard.priceCorpSection.detailStore');
	                me.store.insert(me.store.data.length+1, r);
	                rowEditing.startEdit(me.store.data.length-1, 0);
	                me.isDel = true;
				}
			}, '-',{
				id : 'updateButton',
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.update'),
				xtype: 'updatebutton',
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
					var selections = me.getSelectionModel().getSelection();
					if(selections.length < 1){
						 miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.update'));
						 return;
					}
					var selectData = selections[0];
					var dataIndex = me.store.indexOf(selectData);
					rowEditing.startEdit(dataIndex, 0);
					me.isDel = false;
					
				}
			}, '-',{
				itemId : "removeInfo",
				id:"removeInfo",
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.del'),
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					rowEditing.cancelEdit();
					var selections = me.getSelectionModel().getSelection();
					if(selections.length < 1){
						 return;
					}
					for(var i = 0 ; i < selections.length ; i++){
						var selectData = selections[i];
						var dataIndex = me.store.remove(selectData);
					}
				}
			},'-',{
				id : 'export_button',
				text: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.import'),//'导入',
				xtype: 'uploadbutton',
				width: 100,
				handler: function() {
					rowEditing.cancelEdit();
					uploadT.excelWindow(null,imple,me);
				}
			}, '-',{
				id:'downloadTmplbutton',
				text: pricecard.priceCorpSection.i18n('miser.base.importTemplateDownLoad'),//'导入模板下载',
				xtype: 'downloadbutton',
				width: 130,
				handler: function() {
					miser.requestExportAjax('/excelTemplate/priceCorpSection.xls');
				}
			}, '-',{
				id:'downloadbutton',
				text: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.download'),//'导出',
				xtype: 'downloadbutton',
				width: 130,
				handler: function() {
					if(me.up().opttype == 'view'){
						down(me);
					}else{
						me.up().downPriceCorpSectionData();
					}
					
				}
			}
			];
		
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners: {
				selectionchange: function(sm, selections) {
					Ext.getCmp('removeInfo').setDisabled(!(selections.length == 1));
					Ext.getCmp('updateButton').setDisabled(!(selections.length == 1));
			 	}
			}
		});
		me.callParent([cfg]);
	},
	plugins:[rowEditing]
	
});

/**
 * 明细表单
 */
Ext.define('Miser.view.pricecard.priceCorpSection.AddForm',{
	extend:'Ext.form.Panel',
	id:'AddForm',
	frame: true,
	layout: 'column',
	region: 'north',
	defaults: {
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
				width : miser.getBrowserWidth()-20,
				//height : 28,
				//所属事业部
	            divisionLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.division') + ":",
	            divisionName: 'divisionCode',
	            divisionIsBlank:true,
	            divisionLabelWidth:100,
	            divisionWidth:275,
	            margin:'5px 0px 5px 0px',
	            
	            //所属大区
	            bigregionLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.bigRegion') + ":",
	            bigregionName: 'bigRegionCode',
	            bigregionIsBlank:true,
	            bigregionLabelWidth:100,
	            bigregionWidth:275,
	            margin:'5px 0px 5px 0px',
	            // 所属路区
	            roadareaLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.roadArea') + ":",
	            roadareaName: 'roadAreaCode',
	            roadareaIsBlank:true,
	            roadareaLabelWidth:100,
	            roadareaWidth:275,
	            margin:'5px 0px 5px 0px',
	            //所属门店
	            salesdepartmentLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.salesDepartment') + ":",
	            salesdepartmentName: 'corpCode',
	            salesdepartmentIsBlank:true,
	            salesdepartmentLabelWidth:100,
	            salesdepartmentWidth:275,
	            margin:'5px 0px 5px 0px',
	            
	            type: 'D-B-R-S',
	            loadParentOrg:true,
	            formid:'AddForm',
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
				 name : 'effectiveTime',
	        	 xtype : 'datetimefield',
	        	 width:275,
	        	 //labelWidth: 75,
	        	 fieldLabel : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.effectiveTime'),//'有效时间点',
	        	 beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>',
	        	 allowBlank:false,
				 value:new Date(),
	        	 margin:'5px 0px 5px 0px',
	        	 format : 'Y-m-d H:i:s'
	         },{
             	 name : 'invalidTime',
             	 xtype : 'datetimefield',
             	 fieldLabel : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.invalidTime'),//'失效时间点',
             	 beforeLabelTextTpl: '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>',
             	 allowBlank:false,
     			 width: 275,
     			//labelWidth:75,
     			margin:'5px 0px 5px 0px',
             	 format : 'Y-m-d H:i:s'
              },{
             	 name : 'remark',
             	 xtype : 'textareafield',
             	 grow      : true,
             	 height:26,
             	 fieldLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.remark'),
             	 //labelWidth:75,
             	 width:550,
             	 margin:'5px 0px 5px 0px',
             	 anchor    : '100%'
              }]
         }];
	
	me.callParent([cfg]);
   }
});

/**
 * 窗体
 */
Ext.define('Miser.view.pricecard.priceCorpSection.AddWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	parent : null,
	modal : true,
	id:'addWindow_modle',
	resizable : false,
	closeAction : 'hide',
	width: miser.getBrowserWidth() - 10,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	old : null,
	newest:null,
	dlist:null,
	opttype:'new',
	listeners : {
		beforehide : function(me) {
			me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').onTrigger1Click();
			me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').onTrigger1Click();
			me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').onTrigger1Click();
			me.getPriceCorpSectionAddForm().getForm().findField('corpCode').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('corpCode').onTrigger1Click();
			me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').enable();
			me.getPriceCorpSectionAddForm().getForm().findField('remark').enable();
			me.getPriceCorpSectionAddGrid().isEdit = true;
			
			me.getPriceCorpSectionAddForm().getForm().reset(); // 表格重置
			me.getPriceCorpSectionAddGrid().store.removeAll();
			me.old = null;
			me.newest = null;
			me.dlist = null;
			
			Ext.getCmp('other_up').setVisible(true);
			Ext.getCmp('other_close').setVisible(true);
			
			Ext.getCmp('insert_button').setVisible(true);
			Ext.getCmp('updateButton').setVisible(true);
			
			Ext.getCmp('removeInfo').setVisible(true);
			Ext.getCmp('export_button').setVisible(true);
			
			Ext.getCmp('downloadTmplbutton').setVisible(true);
			Ext.getCmp('downloadbutton').setVisible(false);
			Ext.getCmp('export_button').setVisible(true);
		},
			
		beforeshow : function(me) {
			if(me.opttype == 'update'){
				//修改
				me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('corpCode').disable();
				Ext.getCmp('downloadbutton').setVisible(true);
			}else if(me.opttype == 'view'){
				//详情
				me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('corpCode').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').disable();
				me.getPriceCorpSectionAddForm().getForm().findField('remark').disable();
				me.getPriceCorpSectionAddGrid().isEdit = false;
				
				Ext.getCmp('other_up').setVisible(false);
				Ext.getCmp('other_close').setVisible(false);
				
				Ext.getCmp('insert_button').setVisible(false);
				Ext.getCmp('updateButton').setVisible(false);
				
				Ext.getCmp('removeInfo').setVisible(false);
				Ext.getCmp('export_button').setVisible(false);
				
				Ext.getCmp('downloadTmplbutton').setVisible(false);
				Ext.getCmp('downloadbutton').setVisible(true);
				
			}else if(me.opttype == 'new'){
				Ext.getCmp('downloadbutton').setVisible(true);
			}
			me.setData(me);
			
		},
	},
	priceCorpSectionAddForm:null,
	getPriceCorpSectionAddForm:function() {
		if(Ext.isEmpty(this.priceCorpSectionAddForm)){
			this.priceCorpSectionAddForm = Ext.create('Miser.view.pricecard.priceCorpSection.AddForm')
		}
		return this.priceCorpSectionAddForm;
	},
	priceCorpSectionAddGrid:null,
	getPriceCorpSectionAddGrid:function(){
		if(Ext.isEmpty(this.priceCorpSectionAddGrid)){
			this.priceCorpSectionAddGrid = Ext.create('Miser.view.pricecard.priceCorpSection.AddGrid');
		}
		return this.priceCorpSectionAddGrid;
	},
	setData:function(me){
		if(me.newest != null){
			var divisionCombo = me.getPriceCorpSectionAddForm().getForm().findField('divisionCode');
			divisionCombo.setCombValue(me.newest.divisionName,me.newest.divisionCode);
			
			var bigRegionCombo = me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode');
			bigRegionCombo.setCombValue(me.newest.bigRegionName,me.newest.bigRegionCode);
			
			var roadAreaCombo = me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode');
			roadAreaCombo.setCombValue(me.newest.roadAreaName,me.newest.roadAreaCode);
			
			var corpCombo = me.getPriceCorpSectionAddForm().getForm().findField('corpCode');
			corpCombo.setCombValue(me.newest.corpName,me.newest.corpCode);
			
			var effectiveTime = me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime');
			effectiveTime.setValue(new Date(parseFloat(me.newest.effectiveTime)));
			
			var invalidTime = me.getPriceCorpSectionAddForm().getForm().findField('invalidTime');
			if(me.newest.invalidTime != null && me.newest.invalidTime != ''){
				invalidTime.setValue(new Date(parseFloat(me.newest.invalidTime)));
			}
			
			var remark = me.getPriceCorpSectionAddForm().getForm().findField('remark');
			remark.setValue(me.newest.remark);
			if(me.dlist != null){
				me.getPriceCorpSectionAddGrid().store.loadData(me.dlist,true);
			}
		}
	},
	downPriceCorpSectionData:function(){
		var me = this;
		var store = me.getPriceCorpSectionAddGrid().store.data;
		
		if(store.length <= 0){
			miser.showInfoMsg(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.downNoData'));
			return ;
		}
		var list = [];
		var entity = {
				"divisionCode" : me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').realValue,
				"divisionName" :  me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').rawValue,
				"bigRegionCode" : me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').realValue,
				"bigRegionName" : me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').rawValue,
				"roadAreaCode" : me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').realValue,
				"roadAreaName" : me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').rawValue,
				"corpCode" : me.getPriceCorpSectionAddForm().getForm().findField('corpCode').realValue,
				"corpName" : me.getPriceCorpSectionAddForm().getForm().findField('corpCode').rawValue,
				"effectiveTime" : me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').rawValue,
				"invalidTime" : me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').rawValue,
				"remark" : me.getPriceCorpSectionAddForm().getForm().findField('remark').rawValue
				
		};
		
		var divisionCode =  me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').realValue;
		var divisionName =  me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').rawValue;
		var bigRegionCode = me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').realValue;
		var bigRegionName = me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').rawValue;
		var roadAreaCode =  me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').realValue;
		var roadAreaName =  me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').rawValue;
		var corpCode = me.getPriceCorpSectionAddForm().getForm().findField('corpCode').realValue;
		var corpName = me.getPriceCorpSectionAddForm().getForm().findField('corpCode').rawValue;
		var effectiveTime = me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').rawValue;
		var invalidTime = me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').rawValue;
		var remark = me.getPriceCorpSectionAddForm().getForm().findField('remark').rawValue;
		
		
		for (var i = 0; i < store.length; i++) {  
			list.push({
				"transTypeCode":store.getAt(i).get("transTypeCode"),
				"transTypeName":store.getAt(i).get("transTypeName"),
				"arrivePriceCity":store.getAt(i).get("arrivePriceCity"),
				"arrivePriceCityName":store.getAt(i).get("arrivePriceCityName"),
				"firstAddWeightPrice":store.getAt(i).get("firstAddWeightPrice"),
				"firstWeight":store.getAt(i).get("firstWeight"),
				"firstWeightPrice":store.getAt(i).get("firstWeightPrice"),
				"secondAddWeightPrice":store.getAt(i).get("secondAddWeightPrice"),
				"secondWeight":store.getAt(i).get("secondWeight"),
				"secondWeightPrice":store.getAt(i).get("secondWeightPrice"),
				"thirdAddWeightPrice":store.getAt(i).get("thirdAddWeightPrice"),
				"thirdWeight":store.getAt(i).get("thirdWeight"),
				"thirdWeightPrice":store.getAt(i).get("thirdWeightPrice"),
				"freightSectionCode":store.getAt(i).get("freightSectionCode"),
				"freightSectionName":store.getAt(i).get("freightSectionName"),
				"divisionCode" : divisionCode,
				"divisionName" : divisionName,
				"bigRegionCode" : bigRegionCode,
				"bigRegionName" : bigRegionName,
				"roadAreaCode" : roadAreaCode,
				"roadAreaName" : roadAreaName,
				"corpCode" : corpCode,
				"corpName" : corpName,
				"effectiveTime":effectiveTime,
				"invalidTime":invalidTime,
				"remark" : remark,
			});  
		}
		var params = {
				"vo":{
					"priceCorpSection":entity,
					"list":list,
					"old":me.old
				}
		};
		
		var successFun = function(json) {
			Ext.Msg.hide();
			miser.requestExportAjax(json.filePath);
			var msg="本次导出共"+json.count+"条数据！";
            miser.showInfoMsg(msg);
		};
		
		var failureFun = function(json) {
			Ext.Msg.hide();
			if(json!=undefined){
				miser.showErrorMes(json.message);
			}
		};
		Ext.Msg.wait('处理中，请稍后...', '提示');
		miser.requestJsonAjax('../pricecard/priceCorpSection!downFormPage.action',params, successFun, failureFun);
		
	},
	submitPriceCorpSectionAddForm:function(){
		var me = this;
		if (me.getPriceCorpSectionAddForm().getForm().isValid()) {
			/**
			 * 生效时间不能为空校验
			 */
			var effectiveTime = me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').getValue();
			var invalidTime = me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').getValue();
			if(effectiveTime == null || effectiveTime ==""){
				miser.showInfoMsg(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.eftimePromptNotNull'));
						return ;
			}
			if(invalidTime == null || invalidTime ==""){
				miser.showInfoMsg(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.invalidPromptNotNull'));
				return ;
			}
			
			/**
			 * 校验开始时间大于结束时间
			 */
			var beginTime = new Date(effectiveTime);
			if(invalidTime !=null && invalidTime!=''){
				var endTime = new Date(invalidTime);
				if(beginTime > endTime){
					miser.showInfoMsg(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.eftimePromptVli'));
					return ;
				}
			}
			
			//判断是否需要验证生效时间，后台验证
			
			var store = me.getPriceCorpSectionAddGrid().store.data;
			
			if(store.length <= 0){
				miser.showInfoMsg(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.saveBefoeCheckPrompt'));
				return ;
			}
			var list = [];
			var entity = {
					"divisionCode" : me.getPriceCorpSectionAddForm().getForm().findField('divisionCode').realValue,
					"bigRegionCode" : me.getPriceCorpSectionAddForm().getForm().findField('bigRegionCode').realValue,
					"roadAreaCode" : me.getPriceCorpSectionAddForm().getForm().findField('roadAreaCode').realValue,
					"corpCode" : me.getPriceCorpSectionAddForm().getForm().findField('corpCode').realValue,
					"corpName" : me.getPriceCorpSectionAddForm().getForm().findField('corpCode').rawValue,
					"effectiveTime":me.getPriceCorpSectionAddForm().getForm().findField('effectiveTime').rawValue,
					"invalidTime":me.getPriceCorpSectionAddForm().getForm().findField('invalidTime').rawValue,
					"remark" : me.getPriceCorpSectionAddForm().getForm().findField('remark').rawValue
			};
			
			
			for (var i = 0; i < store.length; i++) {  
				list.push({
					"transTypeCode":store.getAt(i).get("transTypeCode"),
					"transTypeName":store.getAt(i).get("transTypeName"),
					"arrivePriceCity":store.getAt(i).get("arrivePriceCity"),
					"arrivePriceCityName":store.getAt(i).get("arrivePriceCityName"),
					"firstAddWeightPrice":store.getAt(i).get("firstAddWeightPrice"),
					"firstWeight":store.getAt(i).get("firstWeight"),
					"firstWeightPrice":store.getAt(i).get("firstWeightPrice"),
					"secondAddWeightPrice":store.getAt(i).get("secondAddWeightPrice"),
					"secondWeight":store.getAt(i).get("secondWeight"),
					"secondWeightPrice":store.getAt(i).get("secondWeightPrice"),
					"thirdAddWeightPrice":store.getAt(i).get("thirdAddWeightPrice"),
					"thirdWeight":store.getAt(i).get("thirdWeight"),
					"thirdWeightPrice":store.getAt(i).get("thirdWeightPrice"),
					"freightSectionCode":store.getAt(i).get("freightSectionCode"),
					"freightSectionName":store.getAt(i).get("freightSectionName")
				});  
			}
			var params = {
					"vo":{
						"priceCorpSection":entity,
						"list":list,
						"old":me.old
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
			miser.requestJsonAjax('../pricecard/priceCorpSection!save.action',params, successFun, failureFun);
		}
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var nitems = [{
			id:'searchArrivePriceCityForUpdate',
			xtype:'textfield'
		},{
			text:'搜索',
			handler : function(){
				subCustomerDetailSearch(me.getPriceCorpSectionAddGrid(),'searchArrivePriceCityForUpdate');
			}
		},'->',{
			id:'other_up',
			text : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.submit'),
			handler : function() {
				me.submitPriceCorpSectionAddForm();
			}
		},{
			id:'other_close',
			text : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.cancell'),
			handler : function() {
				me.close();
			}
		}];
		me.fbar = nitems;
		me.items = [ me.getPriceCorpSectionAddForm(),me.getPriceCorpSectionAddGrid()];
		me.callParent([ cfg ]);
	}
});

//查询表单
Ext.define('Miser.view.pricecard.priceCorpSectionVo.QueryForm',{
	extend:'Ext.form.Panel',
	title:pricecard.priceCorpSection.i18n('miser.common.queryCondition'),
	id:'queryForm',
	frame: true,
    collapsible: true,
    region: 'north',
    defaults: {
    	labelWidth: 0.4,
    	columnWidth: 0.6,
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
				width : miser.getBrowserWidth()-20,
				//height : 28,
				//所属事业部
	            divisionLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.division') + ":",
	            divisionName: 'vo.priceCorpSection.divisionCode',
	            divisionIsBlank:true,
	            divisionLabelWidth:100,
	            divisionWidth:276,
	            margin:'5px 0px 5px 0px',
	            //所属大区
	            bigregionLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.bigRegion') + ":",
	            bigregionName: 'vo.priceCorpSection.bigRegionCode',
	            bigregionIsBlank:true,
	            bigregionLabelWidth:100,
	            bigregionWidth:276,
	            margin:'5px 0px 5px 0px',
	            // 所属路区
	            roadareaLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.roadArea') + ":",
	            roadareaName: 'vo.priceCorpSection.roadAreaCode',
	            roadareaIsBlank:true,
	            roadareaLabelWidth:100,
	            roadareaWidth:276,
	            margin:'5px 0px 5px 0px',
	            //所属门店
	            salesdepartmentLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.salesDepartment') + ":",
	            salesdepartmentName: 'vo.priceCorpSection.corpCode',
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
				width : miser.getBrowserWidth()-20,
				defaults: {
					labelWidth: 100,
					height : 25,
					labelAlign: "right"
				},
				items:[{
					xtype:'dataDictionarySelector',
					name : 'vo.priceCorpSection.state',
					fieldLabel: pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.courrentState'),
					anyRecords:all,
					width:276,
					termsCode : 'PRICE_SATUS'
				},{
					 name : 'vo.priceCorpSection.effectiveTime',
		        	 xtype : 'datetimefield',
		        	 width:276,
		        	 fieldLabel : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.effectiveTimePort'),
		        	 format : 'Y-m-d H:i:s'
		         }]
			}
	];
	me.buttons = [{
		text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.query'),
		handler: function() {
			if (me.getForm().isValid()) {
				me.up().getFormGridName().getPagingToolbar().moveFirst();
			}
		}
	},
	{
		text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.reset'),
		handler: function() {
			me.getForm().reset(); // 表格重置
		}
	}];
	me.callParent([cfg]);
   }
});

/**
 * 定义表格数据源
 */
Ext.define('Miser.store.pricecard.priceCorpSectionStore',{
	extend:'Ext.data.Store',
	model:'Ext.data.Model',
	pageSize:20,
	proxy:{
		type: 'ajax',
		actionMethods: 'post',
		url:'../pricecard/priceCorpSection!query.action',
		reader: {
			type: 'json',
			rootProperty: 'vo.list',
			totalProperty: 'totalCount'
		}
	},
	 listeners: {
		beforeload: function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('queryForm');
			
			if (queryForm != null) {
			  var params = {};
			  params = queryForm.getValues();
			  Ext.apply(store.proxy.extraParams, params);
			}
		}
	}
});

//查询表格
Ext.define('Miser.view.pricecard.priceCorpSectionVo.Grid',{
	extend:'Ext.grid.Panel',
	frame: true,
	autoScroll: true,
	height: miser.getBrowserHeight() - 165,
	stripeRows: true,
	selType: 'rowmodel',
	emptyText:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.queryReult'),
	columnLines: true,
	viewConfig: {
		enableTextSelection: true
	},
	priceCorpSectionAddWindow:null,
	getPriceCorpSectionAddWindow:function() {
		if(this.priceCorpSectionAddWindow== null) {
			this.priceCorpSectionAddWindow=Ext.create('Miser.view.pricecard.priceCorpSection.AddWindow');
			this.priceCorpSectionAddWindow.parent = this;
		}
		return this.priceCorpSectionAddWindow;
	},
	updateMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.updatePrompt')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var corpCode = selections[0].get('corpCode');
		var effectiveTime = selections[0].get('effectiveTime');
		var invalidTime = selections[0].get('invalidTime');
		var state=selections[0].get('state');
		var addwindow = me.getPriceCorpSectionAddWindow();
		addwindow.setTitle(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.updateInfoTitle'));
		addwindow.opttype = 'update'
		if(state == 'PASSED' || state == 'DELETED'){
			//已失效数据不能修改
			miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.optPrompt')); 
			return ;
		}
		
		var params = {
				'vo' :{
					'priceCorpSection' : {
						'corpCode' : corpCode,
						'effectiveTime' : effectiveTime,
						'invalidTime' : invalidTime,
						'state':state
					}
				}
		};
		var successFun = function (json) {
			addwindow.old= json.vo.old;
			addwindow.newest = json.vo.priceCorpSection;
			addwindow.dlist = json.vo.list;
			addwindow.show();
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.timeOut')); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../pricecard/priceCorpSection!search.action', params, successFun, failureFun);
	},
	insertMethod:function(){
		//自动带入选中记录信息。
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		var addwindow = me.getPriceCorpSectionAddWindow();
		addwindow.setTitle(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.addTitle'));
		addwindow.opttype = 'new';
		if (selections.length == 1) { //判断选中了一条
			var corpCode = selections[0].get('corpCode');
			var effectiveTime = selections[0].get('effectiveTime');
			var invalidTime = selections[0].get('invalidTime');
			var state=selections[0].get('state');
			
			var params = {
					'vo' :{
						'priceCorpSection' : {
							'corpCode' : corpCode,
							'effectiveTime' : effectiveTime,
							'invalidTime' : invalidTime,
							'state':state
						}
					}
			};
			var successFun = function (json) {
				addwindow.newest = json.vo.priceCorpSection;
				addwindow.show();
			};
			var failureFun = function (json) {
				if (Ext.isEmpty(json)) {
					miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.timeOut')); //请求超时
				} else {
					var message = json.message;
					miser.showErrorMes(message);
				}
			};
			miser.requestJsonAjax('../pricecard/priceCorpSection!search.action', params, successFun, failureFun);
		}else{
			addwindow.show();
		}
	},
	viewMethod:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection(); //获取选中的数据
		if (selections.length != 1) { //判断选中了一条
			miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.viewPrompt')); //请选择一条进行作废操作！
			return; //没有则提示并返回
		}
		var corpCode = selections[0].get('corpCode');
		var effectiveTime = selections[0].get('effectiveTime');
		var invalidTime = selections[0].get('invalidTime');
		var state=selections[0].get('state');
		var addwindow = me.getPriceCorpSectionAddWindow();
		addwindow.setTitle(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.viewTitle'));
		addwindow.getPriceCorpSectionAddGrid().isEdit = false;
		addwindow.opttype = 'view'
		var params = {
				'vo' :{
					'priceCorpSection' : {
						'corpCode' : corpCode,
						'effectiveTime' : effectiveTime,
						'invalidTime' : invalidTime,
						'state':state
					}
				}
		};
		var successFun = function (json) {
			addwindow.newest = json.vo.priceCorpSection;
			addwindow.dlist = json.vo.list;
			addwindow.show();
		};
		var failureFun = function (json) {
			if (Ext.isEmpty(json)) {
				miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.timeOut')); //请求超时
			} else {
				var message = json.message;
				miser.showErrorMes(message);
			}
		};
		miser.requestJsonAjax('../pricecard/priceCorpSection!search.action', params, successFun, failureFun);
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
            miser.showWoringMessage(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.delPrompt')); // 请选择一条进行作废操作！
            return; // 没有则提示并返回
        }
        
        //state类型
        var stateType='';
        var objectLeng=0;
        var list = [];
        for(var i=0;i<selections.length;i++){
        	
        	var state=selections[i].get('state');
        	if(state!='WAIT'&&state!='EFFECTIVE'){
    			miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.optPrompt'));
    			return;
    		}
    		if(stateType==''){
    			stateType=state;
    		}else if(stateType!=state) {
    			miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.optPrompt01'));
    			return;
    		}
    		
    		var corpCode = selections[0].get('corpCode');
			var effectiveTime = selections[0].get('effectiveTime');
			var invalidTime = selections[0].get('invalidTime');
			var states=selections[0].get('state');
			list.push({
				"corpCode":corpCode,
				"effectiveTime":effectiveTime,
				"invalidTime":invalidTime,
				"state":states
			});
			
        	if(i!=selections.length-1)
        	objectLeng++;
        }
        var msg=pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.mesPrefix')+objectLeng
        	+pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.mesSuffix');
		miser.showQuestionMes(msg,
		function(e) {
			if (e == 'yes') {
				var params = {
						'vo': {
                			'list' :list
                        }
			 	};
			 	var successFun = function(json) {
			 		var message = json.message;
			 		miser.showInfoMsg(message);
			 		me.getStore().load();
			 	};
				var failureFun = function(json) {
					if (Ext.isEmpty(json)) {
						miser.showErrorMes(pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.timeOut')); // 请求超时
					}else{
						var message = json.message;
						miser.showErrorMes(message);
					}
				};
				miser.requestJsonAjax('../pricecard/priceCorpSection!revoke.action', params, successFun, failureFun);
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
			dataIndex:'divisionName',
			width:100,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.division')
		},{
			dataIndex:'bigRegionName',
			width:100,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.bigRegion')
		},{
			dataIndex:'roadAreaName',
			width:100,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.roadArea')
		},{
			dataIndex:'corpName',
			width:100, 
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.salesDepartment')
		},{
			dataIndex:'logisticName',
			width:100,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.logisticName')
		},{
			dataIndex:'effectiveTime',
			width:150,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.effectiveTime'),
			renderer:function(value){
				return dateRender(value,'Y-m-d H:i:s');
			}
		},{
			dataIndex:'invalidTime',
			width:150,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.invalidTime'),
			renderer:function(value){
				return dateRender(value,'Y-m-d H:i:s');
			}
		},{
			dataIndex:'state',
			width:100,
			align : 'center',
			renderer: function(value) {
				return miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore('PRICE_SATUS'),value);
            },
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.courrentState')
		},{
			dataIndex:'remark',
			width:200,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.remark')
		},{
			dataIndex:'createUserName',
			width:150,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.createUser')
		},{
			dataIndex:'createTime',
			width:150,
			align : 'center',
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.createDateTime')
		},{
			dataIndex:'modifyUserName',
			width:150,
			align : 'center',
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.modifyUser')
		},{
			dataIndex:'modifyTime',
			width:150,
			align : 'center',
			renderer: function(value) {
				 return dateRender(value,'Y-m-d H:i:s');
			},
			text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.modifyTime')
		}
		],
		me.store = Ext.create('Miser.store.pricecard.priceCorpSectionStore', {
				autoLoad: false
		});
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
				mode: 'MULTI',
				checkOnly: true
		});
		me.tbar = [{
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.add'),
				xtype: 'addbutton',
				width: 80,
				handler: function() {
					me.insertMethod();
				
				}
			},
			'-',{
				id : 'update_id',
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.update'),
				xtype: 'updatebutton',
				disabled : true,
				width: 80,
				handler: function() {
					me.updateMethod();
				}
			},
			'-',{
				text:pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.delete'),
				id: 'del_id',
				xtype: 'deletebutton',
				disabled : true,
				width: 80,
				handler: function() {
					me.deleteMethod();
				}
			},'-',{
				id : 'view_btn',
				text : pricecard.priceCorpSection.i18n('pricecard.priceCorpSection.view'),//'查看详情',
				xtype : 'searchbutton',
				disabled : true,
				width : 100,
				handler:function(){
					me.viewMethod();
				}
		}];
		me.bbar = me.getPagingToolbar();
		me.selModel = Ext.create('Ext.selection.CheckboxModel', {
			listeners: {
				selectionchange: function(sm, selections) {
						for(var i = 0;i < selections.length; i++){
							var state = selections[i].get('state');
							Ext.getCmp('del_id').setDisabled(!(state == 'WAIT'));
						}
						Ext.getCmp('update_id').setDisabled(!(selections.length == 1));
						Ext.getCmp('view_btn').setDisabled(!(selections.length == 1));
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
/**
 * 页面初始化
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Miser.view.pricecard.priceCorpSectionVo.QueryForm');
	var formGridName = Ext.create('Miser.view.pricecard.priceCorpSectionVo.Grid');
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
    	mainPanel.setWidth(miser.getBrowserWidth());
    	
    	//控制整个 查询表单 的宽度
    	queryForm.setWidth(miser.getBrowserWidth() - 10);
        
        //设置区域一行表单的宽度
    	var items = queryForm.items;
    	var targetObj = items.items[0].items.items[0];
		targetObj.setWidth(miser.getBrowserWidth() - 10);
		
		//设置区域下一行表单的宽度
		document.getElementById(items.items[1].items.items[0].getEl().el.parent().id).style.width = (miser.getBrowserWidth() - 10)+'px';
		
		//设置查询结果的宽度
		formGridName.setWidth(miser.getBrowserWidth() - 10);
    };
})