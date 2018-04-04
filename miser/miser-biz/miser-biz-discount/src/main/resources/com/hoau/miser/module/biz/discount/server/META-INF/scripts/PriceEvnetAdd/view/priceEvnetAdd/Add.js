/**
 * 优惠活动折扣明细
 */
Ext.define("Miser.view.priceEvnetAdd.Add", {
	extend: "Ext.form.Panel",//Ext.window.Window
    alias: "widget.priceEvnetAddWin",
   // title: "新增活动",
   // height: 540,
    width : '100%',
    autoScroll : true,
    layout: 'column',
    region : 'north',
    items : [{
    		xtype: 'form',
    		columnWidth : 1,
            title: '活动基本信息',
            animCollapse: true,
            bodyPadding: 5,
            height : 130,
            collapsible: true,
            collapsed: false,
            layout: 'column',
            defaultType: 'textfield',
            defaults:{
        		msgTarget : 'qtip',
        		margin : '5 0 5 30 ',
        		labelWidth : 100,
        		columnWidth : 0.33,
        		labelAlign: 'left',
               // regex : /^\S*$/,
              //  regexText : '不能含有空格'
        	},
        	items : [{
                xtype: 'hiddenfield',
                name: 'cId'
            },{
                xtype: 'hiddenfield',
                name: 'eventCode'
            },{
        		fieldLabel: '活动名称',//<font color="#FF0000"><b>*</b></font>
        		name : 'eventName',
        		maxLength : 100,
        		regex : /^\S*$/,
                regexText : '不能含有空格',
				allowBlank:false,
//				beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        	},{
        		fieldLabel : '活动开始时间',//<font color="#FF0000"><b>*</b></font>
				name : 'effectiveTime',
				xtype : 'datetimefield',//datefield
				format : 'Y-m-d H:i:s',
				value : startDate,
				allowBlank:false,
//				beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        	},{
        		fieldLabel : '活动结束时间',//<font color="#FF0000"><b>*</b></font>
				name : 'invalidTime',
				xtype : 'datetimefield',
				format : 'Y-m-d H:i:s',
				value : endDate,
				allowBlank:false,
//				beforeLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>']
        	},{
        		//xtype:"textarea",
    			name:"remark",
    			fieldLabel : '活动描述',
    			maxLength : 500,
    			columnWidth : 0.99,
                maxLengthText : "活动描述不能超过500个字符" 
        	
        	}]
    	},{
    		xtype: 'form',
    		columnWidth : 1,
            title: '活动范围',
            animCollapse: true,
            bodyPadding: 5,
            collapsible: true,
            collapsed: false,
            layout: 'column',
            height : 400,
            defaultType: 'textfield',
            defaults:{
        		msgTarget : 'qtip',
        		margin : '5 0 5 2 ',
        		labelWidth : 130,
        		columnWidth : 0.33,
        		labelAlign: 'left',
                regex : /^\S*$/,
                regexText : '不能含有空格'
        	},
        	items : [{
	    		xtype: 'form',
	    		columnWidth : 0.33,
	            //title: '活动范围1',
	            //animCollapse: true,
	            bodyPadding: 0,
	           // collapsible: true,
	           // collapsed: false,
	            layout: 'column',
	            height : 300,
	            defaultType: 'textfield',
	            style: {border:'1px solid #cecece' },//1px solid red
	            defaults:{
	        		msgTarget : 'qtip',
	        		margin : '5 0 5 0 ',
	        		labelWidth : 100,
	        		columnWidth : 0.33,
	        		labelAlign: 'left',
	                regex : /^\S*$/,
	                regexText : '不能含有空格'
	        	},
	        	items : [{
	        		xtype: 'checkboxgroup',
        	        id: 'selectall',
        	        name: 'lblName',
        	        columns: 1,
        	        border: true,
        	        anchor: '100%',
        	        labelWidth : 130,
        	        columnWidth : 0.99,
        	        fieldLabel:'勾选参与活动的订单'
	        	},{
	        		xtype: 'checkboxgroup',
        	        id: 'lblName',
        	        name: 'lblName',
        	        columns: 2,
        	        border: true,
        	        anchor: '100%',
        	        labelWidth : 130,
        	        columnWidth : 0.99,
        	        margin : '0 0 0 0 '
        	      //  fieldLabel:'勾选参与活动的订单'
	        	}]
//	        	,bbar: [  
//	                    {  
//	                        text: '反选', 
//	                        height : 20,
//	                        handler: function() {  
//	                            var array = Ext.getCmp('lblName').items;  
////	                            alert(array.items.length);  
//	                            array.each(function(item){  
//	                                if(item.getValue()==true){  
//	                                    item.setValue(false);  
//	                                }else{  
//	                                    item.setValue(true);  
//	                                }  
//	                            }); 
//	                        }  
//	                    },  
//	                    '-',  
//	                    {  
//	                        text: '全选',  
//	                        height : 20,
//	                        handler: function() {  
//	                            var array = Ext.getCmp('lblName').items; 
////	                            alert(array.items.length);  
//	                            array.each(function(item){  
//	                                item.setValue(true);  
//	                            });  
//	                        }  
//	                    }   
//	                ] 
            
        	},{
	    		xtype: 'form',
	    		id:"form2",
	    		columnWidth : 0.33,
	           // title: '活动范围2',
	          //  animCollapse: true,
	            bodyPadding: 0,
	         //   collapsible: true,
	          //  collapsed: false,
	            layout: 'column',
	            height : 300,
	            defaultType: 'textfield',
	            style: {border:'1px solid #cecece' },
	            defaults:{
	        		msgTarget : 'qtip',
	        		margin : '5 0 5 0 ',
	        		labelWidth : 100,
	        		columnWidth : 0.33,
	        		labelAlign: 'left',
	                regex : /^\S*$/,
	                regexText : '不能含有空格'
	        	},
	        	items : [
	        	         {
	        		columnWidth : 0.99,
	        		xtype : 'DistrictTreePanel'
	        	         }
//	        		fieldLabel : '区域范围',
//					name : 'areaScope',
//					xtype : 'combo',
//					labelWidth : 60,
//					columnWidth : 0.45,
//					store : getDataDictionary().getDataDictionaryStore(
//							'AREA_SCOPE', null, null, null),
//					queryMode : 'local',
//					displayField : 'valueName',
//					valueField : 'valueCode',
//					matchFieldWidth : false,
//					editable : false
//	        	}
//	        	,{
//					fieldLabel : '',
//					name : 'eventName',
//					columnWidth : 0.3,
//					margin:'5 5 5 5'
//				},{
//					text:'查询',
//					name : 'select',
//					xtype : 'button',
//					columnWidth : 0.2,
//					margin:'5 5 5 5'
//				}
				]
            
        	},{
	    		xtype: 'form',
	    		columnWidth : 0.33,
	           // title: '活动范围3',
	           // animCollapse: true,
	            bodyPadding: 0,
//	            collapsible: true,
//	            collapsed: false,
	            layout: 'column',
	            height : 300,
	            defaultType: 'textfield',
	            style: {border:'1px solid #cecece' },
	            defaults:{
	        		msgTarget : 'qtip',
	        		margin : '5 0 5 0 ',
	        		labelWidth : 100,
	        		columnWidth : 0.33,
	        		labelAlign: 'left',
	                regex : /^\S*$/,
	                regexText : '不能含有空格'
	        	},
	        	items : [{
	        		xtype:"startPriceCityselector",
	    			name:"startPriceCityselector",
	    			fieldLabel : '线路促销',
	    			columnWidth : 0.48,
	    			labelWidth : 58,
	    			matchFieldWidth : false
	        	},{
	        		xtype:"arrivePriceCityselector",
	    			name:"arrivePriceCityselector",
	    			fieldLabel : '至',
	    			columnWidth : 0.37,
	    			labelWidth : 10,
	    			matchFieldWidth : false
	        	},{
					text:'添加',
					name : 'addCity',
					id:'but_addCity',
					xtype : 'button',
					columnWidth : 0.14,
					margin:'5 0 0 5',
					handler: function(btn) {
						var form = btn.up('form').getForm();
						//显示的值
						var startPriceCityselector = form.findField("startPriceCityselector").getRawValue();
						var arrivePriceCityselector = form.findField("arrivePriceCityselector").getRawValue();
						if(startPriceCityselector == '' || startPriceCityselector == null || arrivePriceCityselector == null || arrivePriceCityselector == ''){
							Ext.MessageBox.alert('提示','出发价格城市或到达价格城市不能为空');
							return;
						}
			    		var win = Ext.getCmp('priceEventLineListId'); 
			    		var store = win.getStore();  
//			    		var count = store.getCount(); 
//			    		var data = [{}]; 
//			    		var data = [{'line_id':'','sendPriceCity': startPriceCityselector,  
//			    	              'arrivalPriceCity': arrivePriceCityselector}];
//			    		 store.insert(count+1,data);
			    		var r = Ext.create('Miser.model.PriceEventLineEntity');
			    		r.data.sendPriceCityName = startPriceCityselector;
			    		r.data.sendPriceCity = form.findField("startPriceCityselector").getValue();
			    		r.data.arrivalPriceCityName = arrivePriceCityselector;
			    		r.data.arrivalPriceCity = form.findField("arrivePriceCityselector").getValue();
			    		r.data.id='';
			            store.insert(store.data.length+1, r);
			    	
			    		 //设置空
			    		 form.findField("startPriceCityselector").setRawValue(null);
			    		 form.findField("arrivePriceCityselector").setRawValue(null);
//			    		 rowEditing.cancelEdit();
//			    		 rowEditing.startEdit(0,0);
//			    		 editorGrid.stopEditing(); 
			    	}
				},
				{
					id:'startArriveCityInput',
					xtype:'textfield',
					margin:'5 0 0 5',
					hidden:true
				},{
					id:'startArriveCitySearchBtn',
					text:'查询',
					xtype : 'button',
					width : 60,
					margin:'5 0 0 5',
					hidden:true,
					handler : function(){
			    		var cityLineGrid = Ext.getCmp('priceEventLineListId');
			    		var cityLineStore = cityLineGrid.store;
			    		var sm = cityLineGrid.getSelectionModel();
			    		
			    		//获取需要搜索的值
			    		var searchVal = Ext.String.trim(Ext.getCmp('startArriveCityInput').getValue());
			    		if(Ext.isEmpty(searchVal)){
			    			miser.showErrorMes('请填写搜索的内容');
			    			return;
			    		}
			    		
			    		//用于存放最后匹配搜索的值的数据集
			    		var toSelectedRowDataArr = [];
			    		
			    		//用于存放未匹配搜索值的数据集
			    		var notSelectedRowDataArr = [];
			    		
			    	    //用于存放需要搜索的字段
			    		var keyArr = ['sendPriceCityName','arrivalPriceCityName'];
			    		
			    		//存放搜索字段的值集合
			    		var curFieldVal = null;

			    		var isToSelected = false;

			    		//循环遍历搜索匹配搜索的值的数据
			    		for(var j=0;j<cityLineGrid.store.data.items.length;j++){

			    			valArr = [];

			    			var curRowData = cityLineGrid.store.data.items[j];

			    			for(var k=0;k<keyArr.length;k++){
			    				curFieldVal = curRowData.data[keyArr[k]];
			    				if(!Ext.isEmpty(curFieldVal)){
			    					var existedIdx = curFieldVal.indexOf(searchVal);
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
			    		cityLineGrid.store.removeAll();
			    		
			    		//将要重新添加的两个数组合并 toSelectedRowDataArr 的明细放前面
			    		//注意是将 返回值返回 而不是直接加入到 toSelectedRowDataArr
			    		var storeLocalReloadDataArr = toSelectedRowDataArr.concat(notSelectedRowDataArr);
			    		
			    		//重新加载对应明细数据
			    		cityLineGrid.store.loadData(storeLocalReloadDataArr);

			    		//调用 select 选中传入的数据明细
			    		sm.select(toSelectedRowDataArr);

			    		toSelectedRowDataArr = [];
			    		notSelectedRowDataArr = [];
			    		storeLocalReloadDataArr = [];
			    		isToSelected = false;
					}
				},				
				{
					columnWidth : 0.99,
	        		xtype : 'priceEventLineList'
				}]
        	
        	}]
        },{
    		xtype: 'form',
    		columnWidth : 1,
            title: '客户信息',
            animCollapse: true,
            bodyPadding: 5,
            collapsible: true,
            collapsed: false,
            layout: 'column',
            style : 'margin-bottom:0px;',
            height : 300,
           // hidden : Ext.isEmpty(cId),
            defaultType: 'textfield',
        	items : [{
        		columnWidth : 0.99,
        		xtype : 'priceCustomerList'
        	}]
    	},{
    		xtype: 'form',
    		columnWidth : 1,
            title: '优惠内容',
            animCollapse: true,
            bodyPadding: 5,
            collapsible: true,
            collapsed: false,
            layout: 'column',
            style : 'margin-bottom:0px;',
            height : 400,
           // hidden : Ext.isEmpty(cId),
            defaultType: 'textfield',
        	items : [{
        		columnWidth : 0.99,
        		xtype : 'priceSubList'
        	}]
    	}],
    buttons : [{
	    		text : '提交',
	    		id:'but_sumbit',
	    		action: 'sumbit'
    		},{
                text: '批量导入',
                id:'but_import',
                action : 'import'
            },{
        		text : '取消',
        		id:'but_canenl',
        		action :'canenl'
        	}
    	],
	listeners : {
		'beforeclose' : function(){
			
		},
		'afterrender':function(){
			
			var selectall = Ext.getCmp("selectall");
			var quanxuan = new Ext.form.Checkbox(
		     { boxLabel: "全选",
		       name: 'cb-0',
		       listeners : {
                   'change':function(item, check){
                	   var array = Ext.getCmp('lblName').items; 
                	   if(check){
                		   array.each(function(item){  
                			   item.setValue(true); 
    	                   }); 
                	   }else{
                		   array.each(function(item){  
                			   item.setValue(false); 
    	                   }); 
                	   }
                   }
		       }
		      });
			selectall.items.add(quanxuan);
			
			var checkboxgroup = Ext.getCmp("lblName");
			var obj = getDataDictionary().getDataDictionaryStore('ACTIVITY_ORDER', null, null, null);

			obj.each(function(record){  
				var checkbox = new Ext.form.Checkbox(
			     { boxLabel: record.data.valueName,
			       name: 'cb-' + record.data.valueCode,
			       inputValue:record.data.valueCode
			      });
				checkboxgroup.items.add(checkbox);
			});
//	        var win = Ext.widget("priceEvnetAddWin");
//	        win.doLayout();
		}
	}
});

