/**
 * 左侧树结构面板
 */
Ext.define('Miser.view.priceEvnetAdd.DistrictTreePanel', {
	extend : 'Ext.tree.Panel',
	alias: 'widget.DistrictTreePanel',
	id:'districtTreePanel',
    height : 300,
    minWidth : 200,
	autoScroll:true,
	oldFullPath:null,//刷新之前展开的路径
	useArrows: true,
	rootVisible: true,  
	viewConfig: {plugins: { ptype: 'treeviewdragdrop', appendOnly: true } },
	layoutConfig : {
		// 展开折叠是否有动画效果
		animate : true
	},
	oldId:null,
	//清空数据+关闭form操作
	removeData:function(){
	},
	//加载数据+和展开页面
	loadDataAndExpand:function(json){
		
//		 Ext.getCmp('idCPC').setReginValue(orgAdministrativeInfoModel.get('provinceName'),orgAdministrativeInfoModel.get('provinceCode'),1)//省
//		 Ext.getCmp('idCPC').setReginValue(orgAdministrativeInfoModel.get('cityName'),orgAdministrativeInfoModel.get('cityCode'),2)//市
//		 Ext.getCmp('idCPC').setReginValue(orgAdministrativeInfoModel.get('countyName'),orgAdministrativeInfoModel.get('countyCode'),3)//区
	},
	//左键单击事件
	treeLeftKeyAction:function(node,record,item,index,e){
	var me = this;
//	miser.log(node.getPath());
//			orgInfoPanel = me.up('panel').getDistrictInfoPanel();
//			orgMainInfoForm = orgInfoPanel.getOrgMainInfoForm();//主信息form
		if(Ext.isEmpty(record.raw)||record.raw.id==me.oldId){
		/*	Ext.getCmp('Foss_base_OrgAdministrativeInfo_SaveButton_Id').setDisabled(false);
			Ext.getCmp('Foss_base_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(false);*/
			return;
		}else{
//			me.oldId = record.raw.id;
//			me.removeData();//发送请求之前先清空数据
//			if(!Ext.isEmpty(record.raw)){
//				var orgAdministrativeInfoModel = new Miser.base.district.DistrictEntity(record.raw.entity);//得到部门的model
//				me.oldFullPath = orgAdministrativeInfoModel.get('fullPath');
////				orgMainInfoForm.orgAdministrativeInfoModel = orgAdministrativeInfoModel;//设置加载的数据，重置用
//			
//				var params = {'node':record.raw.id};
//		    	var successFun = function(json){
//		    		
//		    		//加载数据并展开form
//		    		me.loadDataAndExpand(json);
//		    		
//		    	};
//		    	
//		    	var failureFun = function(json) {
//                    if (Ext.isEmpty(json)) {
//                    	miser.showErrorMes('请求超时'); // 请求超时
//                    } else {
//                        var message = json.message;
//                        miser.showErrorMes(message);
//                    }
//                };
//	
//		    	miser.requestJsonAjax('../base/priceEvnetAction!loaddistrictAllInfo.action', params, successFun, failureFun, false);
//		  
//			}
		}
	},
	resComboAreaScope: null,
	getResComboAreaScope: function(){
		var me = this;
		if(this.resComboAreaScope==null){
			this.resComboAreaScope = Ext.create('Ext.form.field.ComboBox',{
				height:25,
				columnWidth: 0.47,
				fieldLabel : '区域范围',
		        name: 'areaScope',
		        labelWidth : 60,
				store : getDataDictionary().getDataDictionaryStore(
						'AREA_SCOPE', null, null, null),
				queryMode : 'local',
				displayField : 'valueName',
				valueField : 'valueCode',
				value:'SEND',
				matchFieldWidth : false,
				editable : false,
		        listeners: {
		        	'select' : function(combo, record){
		        		var selNodes = me.getChecked();
//		        		alert(combo);
		            	//遍历获取所有的节点数据
		            	Ext.each(selNodes, function (node) {
		            		var sub = new Object();
		            		 sub.corpType=combo.getValue();
		            		 sub.orgCode = node.data.id;
		                     sub.eventCode = eventCode;
		                 	if(!Ext.isEmpty(lockView) && lockView == 'copyAdd')
		                		sub.id = '';
		                	else
		                		sub.id = node.data.entity.eventCorpId;
		            		priceEventCorpSubs.push(sub);
		            	});
		        		resComboAreaScope = combo.getValue();
		        		me.getStore().reload();//重新加载
					}
		        }
			});
		}
		return this.resComboAreaScope;
	},
	resTextfield: null,
	getResTextfield: function(){
		var me = this;
		if(this.resTextfield==null){
			this.resTextfield = Ext.create('Ext.form.field.Text',{
				height:25,
				columnWidth : 0.3,
		        emptyText: '输入区域范围',//'输入功能名',
		        /*margin:'0 0 0 19',*/
		        name: 'name',
		      /*  regex:  /^(\w|[\u4E00-\u9FA5])*$/,
		        regexText: '非法字符',*/
		        listeners: {
		        	//输入文本回车查询
		        	specialkey: function(field, e){
	                    if (e.getKey() == e.ENTER) {
	                        me.getQueryButton().handler();
	                    }
	                }
		        }
			});
		}
		return this.resTextfield;
	},
	queryButton: null,
	getQueryButton: function(){
		var me = this;
		if(this.queryButton==null){
			this.queryButton = Ext.create('Ext.button.Button',{ 
		    	height:25,
		    	margin:'0 0 0 3',
		    	columnWidth : 0.2,
//				margin:'5 5 5 5'
		    	text: '查询',
		    	handler: function(){
		    		var field = me.getResTextfield(),
		    			queryValue = field.getValue();
		    		if(!Ext.isEmpty(queryValue)){
		    			var params = {'orgEntity' : {'name':queryValue,'eventCode':''}};
		    			var successFun = function(json) {
		    				var view = me.getView(),
							position = false,
    						pathList = json.pathList;
	    					me.expandNodes = [];
	    					me.collapseAll();
	    					if(pathList.length==0){
	    						Ext.toast('没有找到行政组织','温馨提示','t');
	    						return;
	    					}
	    					for(var i=0;i<pathList.length;i++){
//	    						miser.log(pathList[i]);
	    						me.expandPath(pathList[i],'id','/',function(success, lastNode){
	    							if(success){
	    								var nodeHtmlEl = view.getNode(lastNode),
	    									nodeEl = Ext.get(nodeHtmlEl);
	    								if(Ext.isEmpty(nodeEl)){
	    									me.expandNodes.push(lastNode);
	    									return;
	    								}
	    								var divHtmlEl = nodeEl.query('div')[0],
	    								    divEl = Ext.get(divHtmlEl);
	    								divEl.highlight("ff0000", { attr: 'color', duration:10000 });
										if(!position){
											divHtmlEl.scrollIntoView();
											position = true;
										}
	    							}else{
	    								miser.log('展开失败');
	    							}
	    						});	    						
	    					}
		    	        };
		    	        var failureFun = function(json) {
		    	            if (Ext.isEmpty(json)) {
		    	                miser.showErrorMes('请求超时'); // 请求超时
		    	            } else {
		    	                var message = json.message;
		    	                miser.showErrorMes(message);
		    	            }
		    	        };
		    	        //Ajax请求得到所有查询到的节点全路径
		    	        miser.requestJsonAjax('../discount/priceEvnetAction!queryTreePathForName.action', params, successFun, failureFun);
		    		}
		    	}
		    });
		}
		return this.queryButton;
	},
	
    constructor : function(config) {
    	var me = this, 
		cfg = Ext.apply({}, config);
		me.dockedItems = [{
		    xtype: 'toolbar',
		    dock: 'top',
		    layout: 'column',
		    id: 'base_orgAdministrativeToolbar_id',
		    items: [me.getResComboAreaScope(),me.getResTextfield(),me.getQueryButton()]
		}];
		me.store = Ext.create('Miser.store.districtTreeStore');
		// 监听事件
		me.listeners={
		  	scrollershow: function(scroller) {
		  		if (scroller && scroller.scrollEl) {
		  				scroller.clearManagedListeners(); 
		  				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		  		}
		  	},
		  	checkchange:function(node, state){
		  		node.cascadeBy(function (child) {
                    child.set("checked",state);
                    child.set("cls",'');//如果父选中，就去掉子的样式
                 });
		  		node.bubble(function(node){
			  		//父节点选中 
			  		var parentNode = node.parentNode;
			  		if(parentNode.id=='Root'){
			  			return;
			  		}
			  		if(parentNode != null) {   
			  			 var childNodes = parentNode.childNodes;
			  			 var isChecked=false;
			  			 var isAllChecked=true;
			  			for (var i = 0; i < childNodes.length; i++){
			  				if(!childNodes[i].get('checked')){//
			  					isAllChecked=false;
		  						  break;
		  					 }
			  			}
			  			 
			  			  for (var i = 0; i < childNodes.length; i++) {
			  				  
			  				  if(!state){
			  					  if(childNodes[i].get('checked')){//
			  						isChecked=true;
			  						  break;
			  					  }
			  				  }else{//它选中
			  					  isChecked=true;
			  					  break;
//			  					  if(!parentNode.get('checked')){
//			  						
//			  						break;
//			  					  }
			  					
			  				  }
				  			  
				  			  
				  			  
			  			  }
			  			parentNode.set('checked',isChecked);
			  			if(!isAllChecked){
			  				parentNode.set('cls','myCls');
			  			}else{
			  				parentNode.set('cls','');
			  			}
			  			
		             }
		  		});
 
		  	},
		  	
	    	itemclick : me.treeLeftKeyAction//单击事件
	    },
		me.callParent([cfg]);
    }
});