/**
 * 定义功能树的store
 */
Ext.define('Miser.base.district.districtTreeStore',{
	extend: 'Ext.data.TreeStore',
	root: {
		text:'行政区域',
		id : 'Root',
		expanded: true
	},
	proxy:{
		type:'ajax',
		url:'../base/districtAction!loadDistrictTree.action',
		actionMethods:'POST',
		reader:{
			type:'json',
			rootProperty:'nodes'
		}
	}
});


/**
 * 组织信息
 */
Ext.define('Miser.base.district.DistrictEntity', {
	 extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//组织编码
        type : 'string'
    },{
        name : 'name',//组织名称
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
    	name : 'simplePinyin',//拼音简写（首字母）
        type : 'string'
    },{
        name : 'parentName',//上级组织名称
        type : 'string'
    },{
        name : 'parentCode',//上级组织标编码
        type : 'string'
    },{
        name : 'active',//是否可用
        type : 'string'
    }]
});

/**
 * 左侧树结构面板
 */
Ext.define('Miser.base.district.DistrictTreePanle', {
   
	extend : 'Ext.tree.Panel',
    height : 500,
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
//			orgInfoPanel = me.up('panel').getDistrictInfoPanel();
//			orgMainInfoForm = orgInfoPanel.getOrgMainInfoForm();//主信息form
		if(Ext.isEmpty(record.raw)||record.raw.id==me.oldId){
		/*	Ext.getCmp('Foss_base_OrgAdministrativeInfo_SaveButton_Id').setDisabled(false);
			Ext.getCmp('Foss_base_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(false);*/
			return;
		}else{
			me.oldId = record.raw.id;
			me.removeData();//发送请求之前先清空数据
			if(!Ext.isEmpty(record.raw)){
				var orgAdministrativeInfoModel = new Miser.base.district.DistrictEntity(record.raw.entity);//得到部门的model
				me.oldFullPath = orgAdministrativeInfoModel.get('fullPath');
//				orgMainInfoForm.orgAdministrativeInfoModel = orgAdministrativeInfoModel;//设置加载的数据，重置用
			
				var params = {'node':record.raw.id};
		    	var successFun = function(json){
		    		
		    		//加载数据并展开form
		    		me.loadDataAndExpand(json);
		    		
		    	};
		    	
		    	var failureFun = function(json) {
                    if (Ext.isEmpty(json)) {
                    	miser.showErrorMes('请求超时'); // 请求超时
                    } else {
                        var message = json.message;
                        miser.showErrorMes(message);
                    }
                };
	
		    	miser.requestJsonAjax('../base/districtAction!loaddistrictAllInfo.action', params, successFun, failureFun, false);
		  
			}
		}
	},
	
	resTextfield: null,
	getResTextfield: function(){
		var me = this;
		if(this.resTextfield==null){
			this.resTextfield = Ext.create('Ext.form.field.Text',{
				height:25,
				columnWidth: 0.7,
		        emptyText: '行政区域名称',//'输入功能名',
		        /*margin:'0 0 0 19',*/
		        name: 'name',
		      /*  regex:  /^(\w|[\u4E00-\u9FA5])*$/,
		        regexText: '非法字符',*/
		        listeners: {
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
		    	columnWidth: 0.3,
		    	text: '查询',
		    	handler: function(){
		    		var field = me.getResTextfield(),
		    			queryValue = field.getValue();
		    		if(!Ext.isEmpty(queryValue)){
		    			var params = {'node' : queryValue};
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
	    						miser.log(pathList[i]);
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
		    	        miser.requestJsonAjax('../base/districtAction!queryTreePathForName.action', params, successFun, failureFun);
		    		}
		    	}
		    });
		}
		return this.queryButton;
	},
	
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Miser.base.district.districtTreeStore');
		me.dockedItems = [{
		    xtype: 'toolbar',
		    dock: 'top',
		    layout: 'column',
		    id: 'base_orgAdministrativeToolbar_id',
		    items: [me.getResTextfield(),me.getQueryButton()]
		}];
		
		// 监听事件
		me.listeners={
		  	scrollershow: function(scroller) {
		  		if (scroller && scroller.scrollEl) {
		  				scroller.clearManagedListeners(); 
		  				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		  		}
		  	},
	    	itemclick : me.treeLeftKeyAction//单击事件
	    },
		me.callParent([cfg]);
     }
});