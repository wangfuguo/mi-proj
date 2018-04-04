var transTypeCode,freightSectionCode,addSectionCode,fuelSectionCode,pickupSectionCode,
deliverySectionCode,upstairSectionCode,insuranceRateSectionCode,insuranceSectionCode,
paperSectionCode,smsSectionCode,collectionRateSectionCode,collectionSectionCode;
var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
//	clicksToEdit : 2,
	clicksToMoveEditor: 1,
	saveBtnText:'保存',
    cancelBtnText:'取消',
    errorSummary:false,
	cancelEdit : function(grid) {// 添加一行数据时，如果没有点击update而是点击cancel，则此行数据不会加入到store中
		var me = this;
		if (me.editing) {
			me.getEditor().cancelEdit();
			var record = me.context.record;// 包含属性如下：grid,record,field,value,row,column,rowIdx,colIdx,view,store
			var id = record.data.eventCode;
			if (id == '') {// id是由后台自动生成的，如果id不存在，则说明是通过页面的添加按钮直接添加的，还没有存入到后台数据库中，可以直接在页面删除
				var grid = me.context.grid;
				var items = grid.getSelectionModel().getSelection();
				Ext.each(items, function(item) {
					grid.store.remove(item);
				});
			}
		}
	},
	listeners : {
		edit:function(e,o){
    		var data = e.grid.store.getAt(o.rowIdx).data;
    		var items = e.grid.store.data.items;
    		var dataKeyStr = data.transTypeCode;
    		var existCount = 0;
    		for(var i=0;i<items.length;i++)
    		{
    			var curSubData = items[i].data;
    			var curSubDataKeyStr = curSubData.transTypeCode;
    			if( dataKeyStr == curSubDataKeyStr)
    			{
    				existCount ++;
    			}
    		}
    		
    		if(existCount > 1)
    		{
				var ind = e.grid.store.find("transTypeCode",data.transTypeCode)+1;
				miser.showWoringMessage('已经存在一条相同运输类型信息在：第' + ind + "行");
				rowEditing.startEdit(o.rowIdx,0);
    		}
    	},
    	beforeedit:function(editor,e,eOpts){
    		//点击 修改时 如下下拉框的值 会置空 需要单独重新填充值
    		var data = editor.grid.store.getAt(e.rowIdx).data;
    		
    		if(data.freightSectionName !=""){
    			Ext.getCmp('freightSectionCode').setCombValue(data.freightSectionName,data.freightSectionCode);
    		}
    		
    		if(data.addSectionName != ""){
    			Ext.getCmp('addSectionCode').setCombValue(data.addSectionName,data.addSectionCode);
    		}
    		
     		//运输类型
    		if(data.transTypeName != "" && !Ext.isEmpty(eventCode)){
    			Ext.getCmp('transTypeCode').setCombValue(data.transTypeName,data.transTypeCode);	
    		}
    		
    		//燃油服务费
    		if(data.fuelSectionName !=""){
    			Ext.getCmp('fuelSectionCode').setCombValue(data.fuelSectionName,data.fuelSectionCode);	
    		}
    		//提货费分段折扣
    		if(data.pickupSectionName !=""){
    			Ext.getCmp('pickupSectionCode').setCombValue(data.pickupSectionName,data.pickupSectionCode);	
    		}
    		//送货费
    		if(data.deliverySectionName !=""){
    			Ext.getCmp('deliverySectionCode').setCombValue(data.deliverySectionName,data.deliverySectionCode);	
    		}
    		//上楼费
    		if(data.upstairSectionName !=""){
    			Ext.getCmp('upstairSectionCode').setCombValue(data.upstairSectionName,data.upstairSectionCode);	
    		}
    		//报价率
    		if(data.insuranceRateSectionName !=""){
    			Ext.getCmp('insuranceRateSectionCode').setCombValue(data.insuranceRateSectionName,data.insuranceRateSectionCode);	
    		}
    		//保价费
    		if(data.insuranceSectionName !=""){
    			Ext.getCmp('insuranceSectionCode').setCombValue(data.insuranceSectionName,data.insuranceSectionCode);	
    		}
    		
    		//工本费
    		if(data.paperSectionName !=""){
    			Ext.getCmp('paperSectionCode').setCombValue(data.paperSectionName,data.paperSectionCode);	
    		}
    		
    		//信息费
    		if(data.smsSectionName !=""){
    			Ext.getCmp('smsSectionCode').setCombValue(data.smsSectionName,data.smsSectionCode);	
    		}
    		
    		//代收款费率
    		if(data.collectionRateSectionName !=""){
    			Ext.getCmp('collectionRateSectionCode').setCombValue(data.collectionRateSectionName,data.collectionRateSectionCode);	
    		}
    		
    		//代收款
    		if(data.collectionSectionName !=""){
    			Ext.getCmp('collectionSectionCode').setCombValue(data.collectionSectionName,data.collectionSectionCode);	
    		}
    		
            return true;
    	},
		dblclick: function(e,o){
			this.cancelEdit();
		},
		cancelEdit:function (e,o){
			e.grid.store.removeAt(o.rowIdx);
			Ext.getCmp('freightSectionCode').onTrigger1Click();
			Ext.getCmp('addSectionCode').onTrigger1Click();
			Ext.getCmp('transTypeCode').onTrigger1Click();
			Ext.getCmp('fuelSectionCode').onTrigger1Click();
			Ext.getCmp('pickupSectionCode').onTrigger1Click();
			Ext.getCmp('deliverySectionCode').onTrigger1Click();
			Ext.getCmp('upstairSectionCode').onTrigger1Click();
			Ext.getCmp('insuranceRateSectionCode').onTrigger1Click();
			Ext.getCmp('insuranceSectionCode').onTrigger1Click();
			Ext.getCmp('paperSectionCode').onTrigger1Click();
			Ext.getCmp('smsSectionCode').onTrigger1Click();
			Ext.getCmp('collectionRateSectionCode').onTrigger1Click();
			Ext.getCmp('collectionSectionCode').onTrigger1Click();
			
		},
		afteredit:function(){
			Ext.getCmp('freightSectionCode').onTrigger1Click();
			Ext.getCmp('addSectionCode').onTrigger1Click();
			Ext.getCmp('transTypeCode').onTrigger1Click();
			Ext.getCmp('fuelSectionCode').onTrigger1Click();
			Ext.getCmp('pickupSectionCode').onTrigger1Click();
			Ext.getCmp('deliverySectionCode').onTrigger1Click();
			Ext.getCmp('upstairSectionCode').onTrigger1Click();
			Ext.getCmp('insuranceRateSectionCode').onTrigger1Click();
			Ext.getCmp('insuranceSectionCode').onTrigger1Click();
			Ext.getCmp('paperSectionCode').onTrigger1Click();
			Ext.getCmp('smsSectionCode').onTrigger1Click();
			Ext.getCmp('collectionRateSectionCode').onTrigger1Click();
			Ext.getCmp('collectionSectionCode').onTrigger1Click();
		}
	}
});

var cellEditing=Ext.create('Ext.grid.plugin.CellEditing', {
	clicksToEdit:2,
	listeners: {
		edit: function(editor,e) {
//			miser.log(e.record.data);
//			if(e.record.data.id) {//id由后台自动生成，编辑时
//			//有id,则说明是更新数据库。
//			} else {
//			//无id，则说明是向数据库中插入一条记录
//			}
//			e.record.data.name;
//			e.record.data.email;
//			e.record.data.phone;
		},
    	beforeedit:function(editor,e,eOpts){
    		//点击 修改时 如下下拉框的值 会置空 需要单独重新填充值
    		var data = editor.grid.store.getAt(e.rowIdx).data;
    		
    		if(data.freightSectionName !=""){
    			Ext.getCmp('freightSectionCode').setCombValue(data.freightSectionName,data.freightSectionCode);
    		}
    		
    		if(data.addSectionName != ""){
    			Ext.getCmp('addSectionCode').setCombValue(data.addSectionName,data.addSectionCode);
    		}
    		
    		//运输类型
    		if(data.transTypeName != ""){
    			Ext.getCmp('transTypeCode').setCombValue(data.transTypeName,data.transTypeCode);	
    		}
    		
    		//燃油服务费
    		if(data.fuelSectionName !=""){
    			Ext.getCmp('fuelSectionCode').setCombValue(data.fuelSectionName,data.fuelSectionCode);	
    		}
    		//提货费分段折扣
    		if(data.pickupSectionName !=""){
    			Ext.getCmp('pickupSectionCode').setCombValue(data.pickupSectionName,data.pickupSectionCode);	
    		}
    		//送货费
    		if(data.deliverySectionName !=""){
    			Ext.getCmp('deliverySectionCode').setCombValue(data.deliverySectionName,data.deliverySectionCode);	
    		}
    		//上楼费
    		if(data.upstairSectionName !=""){
    			Ext.getCmp('upstairSectionCode').setCombValue(data.upstairSectionName,data.upstairSectionCode);	
    		}
    		//报价率
    		if(data.insuranceRateSectionName !=""){
    			Ext.getCmp('insuranceRateSectionCode').setCombValue(data.insuranceRateSectionName,data.insuranceRateSectionCode);	
    		}
    		//保价费
    		if(data.insuranceSectionName !=""){
    			Ext.getCmp('insuranceSectionCode').setCombValue(data.insuranceSectionName,data.insuranceSectionCode);	
    		}
    		
    		//工本费
    		if(data.paperSectionName !=""){
    			Ext.getCmp('paperSectionCode').setCombValue(data.paperSectionName,data.paperSectionCode);	
    		}
    		
    		//信息费
    		if(data.smsSectionName !=""){
    			Ext.getCmp('smsSectionCode').setCombValue(data.smsSectionName,data.smsSectionCode);	
    		}
    		
    		//代收款费率
    		if(data.collectionRateSectionName !=""){
    			Ext.getCmp('collectionRateSectionCode').setCombValue(data.collectionRateSectionName,data.collectionRateSectionCode);	
    		}
    		
    		//代收款
    		if(data.collectionSectionName !=""){
    			Ext.getCmp('collectionSectionCode').setCombValue(data.collectionSectionName,data.collectionSectionCode);	
    		}
    		
    		return true;
    	},
	
	}
	});

/**
 * 优惠分段信息GRID
 */
Ext.define("Miser.view.priceEvnetAdd.PriceSubList", {
    extend: "Ext.grid.Panel",
    alias: 'widget.priceSubList',
    id : 'priceSubListId',
    store: "PriceEvnetSub",
    region : 'center',
    width : '100%',
    height: 320,
    stripeRows : true,// 交替行效果
	isEdit:true,
    viewConfig:{
		enableTextSelection:true,
	},
    selModel : Ext.create('Ext.selection.CheckboxModel'),
    columns : {
		defaults: {
		    align : 'left'
		},
		items : [
		    { text : '序号',	width : 50,	dataIndex: 'rownumberer',xtype : 'rownumberer'},
		    { 
		    	text: '运输类型',
		    	dataIndex: 'transTypeCode', 
		    	width : 110,
		    	editor: new Ext.create('Miser.commonTranstypeSelector.TrantypeSelector',{
				  id :"transTypeCode",
				  forceSelection :true,
				  typeAhead :true,
				  triggerAction :'all',
				  allowBlank :false,
				  showAll:false
			    }),
		    	renderer: function(value,o,m){
	
				  var sto = Ext.getCmp('transTypeCode');
				  var display = value;
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  display = getDisplayTextCommon(sto.getStore(),value);
					  if(display == value){
						  display = m.data.transTypeName;
					  }
				  }
				  else
				 {
					  display = m.data.transTypeName;
				 }
				  m.data.transTypeName = display;
				 return m.data.transTypeName;
		       }
		    },
	        { 
			  text: '运费分段折扣', 
			  dataIndex: 'freightSectionCode', 
			  width : 110,
			  editor: {
				  id:'freightSectionCode', 
				  allowBlank: true,
				  sectionedItem:'FREIGHT', 
				  xtype: "dynamicPriceSectioncombselector",
				  listeners: {}
		     },
             renderer: function(value,o,m){
			  var sto = Ext.getCmp('freightSectionCode');
			  var display = value;
			  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
			  {
				  display = getDisplayTextCommon(sto.getStore(),value);
				  if(display == value){
					  display = m.data.freightSectionName
				  }
			  }
			  else
			 {
				  display = m.data.freightSectionName;
			 }
			 m.data.freightSectionName = display;
			 return m.data.freightSectionName;
		 }  },
	        { 
			  text: '附加费分段折扣', 
			  dataIndex: 'addSectionCode', 
			  width : 120,
			  editor: 
			  { 
				  id:'addSectionCode',
				  allowBlank: true,
				  sectionedItem:'EXTRA_FEE',
				  xtype: "dynamicPriceSectioncombselector",
				  listeners: {}
		      },
		      renderer: function(value,o,m){
				  var sto = Ext.getCmp('addSectionCode');
				  var display = value;
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  display = getDisplayTextCommon(sto.getStore(),value);
					  if(display == value){
						  display = m.data.addSectionName;
					  }
				  }
				  else
				 {
					  display = m.data.addSectionName;
				 }
				 m.data.addSectionName = display;
				 return m.data.addSectionName;
			 } },
	        { 
				 text: '燃油费分段折扣', 
				 dataIndex: 'fuelSectionCode',  
				 width : 120,
				 editor: { 
					 id:'fuelSectionCode',
					 allowBlank: true,
					 sectionedItem:'FUEL',
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			     },
			     renderer: function(value,o,m){
				    var sto = Ext.getCmp('fuelSectionCode');
				    var display = value;
				    if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				    {
					    display = getDisplayTextCommon(sto.getStore(),value);
					    if(display == value){
					    	display = m.data.fuelSectionName;
					    }
				    }
				    else
				   {
					   display = m.data.fuelSectionName;
				   }
				   
				   m.data.fuelSectionName = display; 
				   return m.data.fuelSectionName;
			 } },
	 		{ 
				 text: '提货费分段折扣', 
				 dataIndex: 'pickupSectionCode',  
				 width : 120,
				 editor: {
					 id:'pickupSectionCode',
					 allowBlank: true,
					 sectionedItem:'PICKUP', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			    },
			    renderer: function(value,o,m){
				  var sto = Ext.getCmp('pickupSectionCode');
				  var display = value;
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  display = getDisplayTextCommon(sto.getStore(),value);
					  if(display == value){
						  display = m.data.pickupSectionName;
					  }
				  }
				  else
				 {
					  display = m.data.pickupSectionName;
				 }
				  m.data.pickupSectionName = display;
				 return m.data.pickupSectionName;
			 }},
			{ 
				 text: '送货费分段折扣', 
				 dataIndex: 'deliverySectionCode',  
				 width : 120,
				 editor: {
					 id:'deliverySectionCode',
					 allowBlank: true,
					 sectionedItem:'DELIVERY', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			    },
			    renderer: function(value,o,m){
				      var sto = Ext.getCmp('deliverySectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.deliverySectionName;
						  }
					  }
					  else
					 {
						  display = m.data.deliverySectionName;
					 }
					 m.data.deliverySectionName = display;
					 return m.data.deliverySectionName;
			 }},
			{ 
				 text: '上楼费分段折扣', 
				 dataIndex: 'upstairSectionCode',  
				 width : 120,
				 editor: {
					 id:'upstairSectionCode',
					 allowBlank: true,
					 sectionedItem:'UPSTAIRS', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			     },
			     renderer: function(value,o,m){
					  var sto = Ext.getCmp('upstairSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.upstairSectionName;  
						  }
					  }
					  else
					 {
						  display = m.data.upstairSectionName;
					 }
					 
					 m.data.upstairSectionName = display;
					 return m.data.upstairSectionName;
			 }},
			{ 
				 text: '保价率分段折扣', 
				 dataIndex: 'insuranceRateSectionCode', 
				 width : 120,
				 editor: {
					 id:'insuranceRateSectionCode',
					 allowBlank: true, 
					 sectionedItem:'INSURANCE_RATE',
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			    },
			    renderer: function(value,o,m){
					  var sto = Ext.getCmp('insuranceRateSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.insuranceRateSectionName;
						  }
					  }
					  else
					 {
						  display = m.data.insuranceRateSectionName;
					 }
					 m.data.insuranceRateSectionName = display;
					 return m.data.insuranceRateSectionName;
			 }},
			{ 
				 text: '保价费分段折扣', 
				 dataIndex: 'insuranceSectionCode', 
				 width : 120,
				 editor: {
					 id:'insuranceSectionCode',
					 allowBlank: true, 
					 sectionedItem:'INSURANCE',
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			    },
			    renderer: function(value,o,m){
					  var sto = Ext.getCmp('insuranceSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.insuranceSectionName;
						  }
					  }
					  else
					 {
						  display = m.data.insuranceSectionName;
					 }
					 
					  m.data.insuranceSectionName = display;
					 return m.data.insuranceSectionName;
			 }},
			{ 
				 text: '工本费分段折扣', 
				 dataIndex: 'paperSectionCode',  
				 width : 120,
				 editor: {
					 id:'paperSectionCode',
					 allowBlank: true,
					 sectionedItem:'PAPER', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			    },
			    renderer: function(value,o,m){
				  var sto = Ext.getCmp('paperSectionCode');
				  var display = value;
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  display = getDisplayTextCommon(sto.getStore(),value);
					  if(display == value){
						  display = m.data.paperSectionName;
					  }
				  }
				  else
				 {
					  display = m.data.paperSectionName;
				 }
				  
				 m.data.paperSectionName = display;
				 return m.data.paperSectionName;
			 }},
			{ 
				 text: '信息费分段折扣', 
				 dataIndex: 'smsSectionCode',  
				 width : 120,
				 editor: {
					 id:'smsSectionCode',
					 allowBlank: true,
					 sectionedItem:'SMS', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			     },
			     renderer: function(value,o,m){
					  var sto = Ext.getCmp('smsSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.smsSectionName;
						  }
					  }
					  else
					 {
						  display = m.data.smsSectionName;
					 }
					  
					 m.data.smsSectionName = display;
					 return display;
			 }},
			{ 
				 text: '代收手续费率分段', 
				 dataIndex: 'collectionRateSectionCode',  
				 width : 130,
				 editor: {
					 id:'collectionRateSectionCode',
					 allowBlank: true,
					 sectionedItem:'COLLECTION_RATE', 
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			     },
			     renderer: function(value,o,m){
					  var sto = Ext.getCmp('collectionRateSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.collectionRateSectionName;
						  }
					  }
					  else
					 {
						  display = m.data.collectionRateSectionName;
					 }
					  
					 m.data.collectionRateSectionName = display; 
					 return display;
			 }},
			{ 
				 text: '代收手续费分段折扣', 
				 dataIndex: 'collectionSectionCode',  
				 width : 140,
				 editor: { 
					 id:'collectionSectionCode',
					 allowBlank: true,
					 sectionedItem:'COLLECTION',
					 xtype: "dynamicPriceSectioncombselector",
					 listeners: {}
			     },
			     renderer: function(value,o,m){
					  var sto = Ext.getCmp('collectionSectionCode');
					  var display = value;
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
						  if(display == value){
							  display = m.data.collectionSectionName;
						  }
					  }
					  else
					 {
						  display = m.data.collectionSectionName;
					 }
					 
					 m.data.collectionSectionName = display;
					 return m.data.collectionSectionName;
			 }}
		 ]
    },
    plugins: [rowEditing],
    autoScroll: true,
    tbar : [{
    	text : '新增',
    	id:'tbar_add',
    	handler: function() {
    		var win = Ext.getCmp('priceSubListId');//Ext.widget("priceSubList"); 
    		var store = win.getStore();  
    		rowEditing.cancelEdit();
            var r = Ext.create('Miser.model.PriceEvnetSub');
            store.insert(store.data.length+1, r);
            rowEditing.startEdit(store.data.length-1, 0);
    	}
    }, {
    	text : '修改',
    	id:'tbar_edit',
    	handler: function() {
    		var records=Ext.getCmp('priceSubListId').getSelectionModel().getSelection();
    		if(records.length > 0){
    			if(records.length == 1){
    				Ext.getCmp('priceSubListId').getStore().indexOfId(records.id);
        			rowEditing.startEdit(Ext.getCmp('priceSubListId').getStore().indexOf(records[0]),0);
    			}else{
    				Ext.MessageBox.alert('提示','不能同时选择多行修改');
    			}
    		}else{
    			Ext.MessageBox.alert('提示','请选择需要修改的行');
    		}
    	}
    }, {
    	text : '删除',
    	id:'tbar_del',
    	handler: function() {
    		var records=Ext.getCmp('priceSubListId').getSelectionModel().getSelection();
    		if(records.length > 0){
    			Ext.each(records, function(record) {
    	    		//先通过ajax从后台删除数据，删除成功后再从页面删除数据
    				Ext.getCmp('priceSubListId').getStore().remove(record);
    	    	});
    		}else{
    			Ext.MessageBox.alert('提示','请选择需要删除的行');
    		}
    	}
    }]
});

function isValue(value,meta,record, rowIndex, columnIndex, store){
//	if(columnIndex == 2){
//		if(transTypeCode != null){
//			record.data.transTypeCode = transTypeCode;
//			return transTypeCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 3)
//	{
//		if(freightSectionCode != null){
//			record.data.freightSectionCode = freightSectionCode;
//			return freightSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 4){
//		if(addSectionCode != null){
//			record.data.addSectionCode = addSectionCode;
//			return addSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 5){
//		if(fuelSectionCode != null){
//			record.data.fuelSectionCode = fuelSectionCode;
//			return fuelSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 6){
//		if(pickupSectionCode != null){
//			record.data.pickupSectionCode = pickupSectionCode;
//			return pickupSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 7){
//		if(deliverySectionCode != null){
//			record.data.deliverySectionCode = deliverySectionCode;
//			return deliverySectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 8){
//		if(upstairSectionCode != null){
//			record.data.upstairSectionCode = upstairSectionCode;
//			return upstairSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 9){
//		if(insuranceRateSectionCode != null){
//			record.data.insuranceRateSectionCode = insuranceRateSectionCode;
//			return insuranceRateSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 10){
//		if(insuranceSectionCode != null){
//			record.data.insuranceSectionCode = insuranceSectionCode;
//			return insuranceSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 11){
//		if(paperSectionCode != null){
//			record.data.paperSectionCode = paperSectionCode;
//			return paperSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 12){
//		if(smsSectionCode != null){
//			record.data.smsSectionCode = smsSectionCode;
//			return smsSectionCode;
//		}else
//		return value;
//	}
//	else if(columnIndex == 13){
//		if(collectionRateSectionCode != null){
//			record.data.collectionRateSectionCode = collectionRateSectionCode;
//			return collectionRateSectionCode;
//		}else
//		return value;	
//	}
//	else if(columnIndex == 14){
//		if(collectionSectionCode != null){
//			record.data.collectionSectionCode = collectionSectionCode;
//			return collectionSectionCode;
//		}else
//		return value;	
//	}
}


function getDisplayTextCommon(store,value){
	var displayText = "";
	
	 //获取当前id="combo_process"的comboBox选择的值
	  //var store = Ext.getCmp(id).getStore();
	  var index = store.find("code", value);
	  var record = store.getAt(index);
	  
	  // 如果下拉列表没有被选择，那么record也就不存在，这时候，返回传进来的value，
	  // 而这个value就是grid的deal_with_name列的value，所以直接返回
	  if (record == null) {
	  //返回默认值，这是与网上其他解决办法不同的。这个方法才是正确的。我研究了很久才发现。
	   displayText = value;
	  } else {
	   displayText = record.data.name;//获取record中的数据集中的process_name字段的值
	   
	   //针对于 出发价格城市 到达价格城市 不是取 name 属性值
	   if(displayText == undefined)
	   {
		   displayText = record.data.priceCityName;
	   }
	  }
	return displayText;
}