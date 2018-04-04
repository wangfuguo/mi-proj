/**
 * 活动信息导入FORM
 */
Ext.define("Miser.view.priceEvnetAdd.Import", {
	extend: "Ext.window.Window",
    alias: "widget.priceEvnetImportWin",
    title: "活动信息批量导入",
    width: 400,
    height: 200,
    layout: "fit",
    modal:true,
    items: {
        xtype: "form",
        id : 'form11',
        margin: 5,
        border: false,
        layout: "column",
        record : null, //活动信息
        fieldDefaults: {
            labelAlign: 'right',
            labelWidth : 80,
            margin : '15 0 5 0 ',
            columnWidth : 0.9
        },
        items : [{fieldLabel : '导入类型',
				name : 'importType',
				id:'importType',
				xtype : 'combo',
				store : getDataDictionary().getDataDictionaryStore(
						'HDGL_IMPORTTYPE', null, null, null),
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				matchFieldWidth : false,
				editable : false,
				allowBlank : false,
				value:'1',
				columnWidth : 0.5
			}, {
    		xtype : 'displayfield',
    		fieldLabel : '模板',
    		value : '<a href="javascript:downLoadFile(\'\/excelTemplate\/template.xlsx\', \'区域范围.xlsx\')">模板下载</a>',
    		columnWidth : 0.5
    	},{
    		name : 'serviceXls',
    		xtype : 'filefield',
    		fieldLabel : '文件',
    		msgTarget : 'side',
    		allowBlank : false,
    		buttonText : '请选择文件'
    	}],
    	buttons : [{
    		text : '上传',
    		handler : function() {
    			var form = this.up('form').getForm();
    			if (form.isValid()) {
    				form.submit({
    					url : '../common/FileUpLoadAction!upLoadFile.action',
    					//headers: {'Content-Type':'multipart/form-data; charset=UTF-8'},
    					waitMsg : '文件上传中，请稍后...',
    					success : function(form,response) {
    						var importType = form.findField('importType').getValue();
    						imple(response.result.outFileName,importType);
    						//upload.action
//    						Ext.MessageBox.alert('提示',response.result.outFileName);
//    						Ext.getCmp('customerInfoPoolViewId').getCustomerInfoPoolGrid().getPagingToolbar().moveFirst();
    					},
    					failure : function(form,response) {
    						var result = response.result;
    						if(result.message == undefined){
    							Ext.MessageBox.alert('提示',"系统异常,请联系系统管理员!");
    						}else{
    							//业务异常
    							Ext.MessageBox.alert('提示',result.message);
    						}
    			        } 
    				});
    			}
    		}
    		}
    	]
    },listeners :{
    	close:function(){
    		parent.allChildrenPanelMap.put('priceEvnetImportWin', undefined); 
    	}
    }
});


function imple(outFileName,importType){
	miser.showInfoMsg('上传完成');
	Ext.Msg.wait('数据处理中，请稍后...', '提示');
	Ext.Ajax.request( {  
       url : '../discount/priceEvnetAction!tableUpload.action',  
       method : 'post',  
       params : {  
    	   'resource.attachpath' : outFileName,
    	   'resource.attachtype' : importType,
    	   'resource.attachname' : eventCode
          },  
          success : function(response, options) {  
        	  //隐藏进度条   
               Ext.Msg.hide();
               if(importType ==  1){
            	   var responseArray = Ext.util.JSON.decode(response.responseText);
//            	   miser.log(responseArray.corpSubEntities);
            	   //设置范围
            	   var corpSubEntities = responseArray.corpSubEntities;
			    	 for(var i = 0; i < corpSubEntities.length; i++){
			    		 var sub = new Object();
		            		sub.corpType = corpSubEntities[i].corpType;
		            		 sub.orgCode = corpSubEntities[i].orgCode;
		                     sub.eventCode = eventCode;
		                	sub.id = '';
		            		priceEventCorpSubs.push(sub);
					  }
	               Ext.getCmp('districtTreePanel').getStore().reload();
	               Ext.MessageBox.alert('提示','区域范围导入成功');
               }else if(importType ==  2){
            	   var responseArray = Ext.util.JSON.decode(response.responseText); 
            	   var priceEventRouteSubs = responseArray.routeSubEntities;
			    	var storeline = Ext.getCmp('priceEventLineListId').getStore();
			    	var countline = storeline.getCount();
			    	//设置线路id为空
		    		 for(var i = 0; i < priceEventRouteSubs.length; i++){
//			    		 var data = [{'line_id':'','sendPriceCity': priceEventRouteSubs[i].sendPriceCity,  
//		    	              'arrivalPriceCity': priceEventRouteSubs[i].arrivalPriceCity}]; 
			    		storeline.insert(countline+1,priceEventRouteSubs[i]);
					  }
		    		 Ext.MessageBox.alert('提示','线路促销导入成功');
               }else{
            	 //设置优惠段
            	   var responseArray = Ext.util.JSON.decode(response.responseText); 
			    	var priceEventDiscountSubs = responseArray.discountSubEntities;
			    	var storelist = Ext.getCmp('priceSubListId').getStore();
			    	var countlist = storelist.getCount();
			    	//设置优惠段id为空
		    		for(var i = 0; i < priceEventDiscountSubs.length; i++){
//			    		var data = [{'sub_id':'',
//			    			'transTypeCode':priceEventDiscountSubs[i].transTypeCode,
//			    			'freightSectionCode': priceEventDiscountSubs[i].freightSectionCode,  
//    	              		'addSectionCode': priceEventDiscountSubs[i].addSectionCode,
//    	              		'fuelSectionCode': priceEventDiscountSubs[i].fuelSectionCode,
//    	              		'pickupSectionCode': priceEventDiscountSubs[i].pickupSectionCode,
//    	              		'deliverySectionCode': priceEventDiscountSubs[i].deliverySectionCode,
//    	              		'upstairSectionCode': priceEventDiscountSubs[i].upstairSectionCode,
//    	              		'insuranceRateSectionCode': priceEventDiscountSubs[i].insuranceRateSectionCode,
//    	              		'insuranceSectionCode': priceEventDiscountSubs[i].insuranceSectionCode,
//    	              		'paperSectionCode': priceEventDiscountSubs[i].paperSectionCode,
//    	              		'smsSectionCode': priceEventDiscountSubs[i].smsSectionCode,
//    	              		'collectionRateSectionCode': priceEventDiscountSubs[i].collectionRateSectionCode,
//    	              		'collectionSectionCode': priceEventDiscountSubs[i].collectionSectionCode}]; 
			    		storelist.insert(countlist+1,priceEventDiscountSubs[i]);
					  }
		    		Ext.MessageBox.alert('提示','优惠内容导入成功');
               }
//               var win = parent.allChildrenPanelMap.get('priceEvnetImportWin'); 
//               win.close();
             },
        failure : function() { 
            Ext.Msg.hide();  
            Ext.MessageBox.show({title: '失败',msg: '数据导入失败',buttons: Ext.MessageBox.OK,icon: Ext.MessageBox.ERROR});  
        }
    });  
}

/**
 * 模板下载
 * 
 * @param filePath
 * @param fileName
 * @author 275636
 * @date 2016-1-15
 * @update
 */
function downLoadFile(filePath, fileName) {
	var dtype = Ext.getCmp('importType').getValue();
	var dName = Ext.getCmp('importType').getRawValue();
	Ext.Msg.confirm('提示', '当前下载模板为['+dName+'],您确定下载吗？', function(btn) {
		if(btn == 'yes') {
			if(dtype == 1){
				filePath = '/excelTemplate/priceEventCorpSub.xlsx';
			}else if(dtype == 2){
				filePath = '/excelTemplate/priceEventRouteSub.xlsx';
			}else{
				filePath = '/excelTemplate/priceEventDiscountSub.xlsx';
			}
//			fileName = dName+'.xlsx';
//			var url = 'downloadCustomerTemplate.action?resource.attachpath=' + filePath
//					+ '&resource.attachname=' + fileName;
//			window.location.href = url;
			miser.requestExportAjax(filePath);
		}
	});
	
}