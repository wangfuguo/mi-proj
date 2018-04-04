var customerName;
var cellCusEditing=Ext.create('Ext.grid.plugin.CellEditing', {
	clicksToEdit:2,
	listeners: {
		edit: function(e,o) {
			var data = e.grid.store.getAt(o.rowIdx).data;
    		var items = e.grid.store.data.items;
    		var dataKeyStr = data.customerCode;
    		var existCount = 0;
    		for(var i=0;i<items.length;i++)
    		{
    			var curSubData = items[i].data;
    			var curSubDataKeyStr = curSubData.customerCode;
    			if( dataKeyStr == curSubDataKeyStr)
    			{
    				existCount ++;
    			}
    		}
    		
    		if(existCount > 1)
    		{
				var ind = e.grid.store.find("customerCode",data.customerCode)+1;
				miser.showWoringMessage('已经存在一条相同客户信息在：第' + ind + "行");
				e.grid.store.removeAt(o.rowIdx);
//				 var me = this;
//				 var grid = me.context.grid;
//					var items = grid.getSelectionModel().getSelection();
//					Ext.each(items, function(item) {
//					});
//				cellCusEditing.startEdit(o.rowIdx,2);
    		}
		},beforeedit:function(editor,e,eOpts){
    		//点击 修改时 如下下拉框的值 会置空 需要单独重新填充值
    		var data = editor.grid.store.getAt(e.rowIdx).data;
//    		miser.log('客户编码'+data);
    		if(data.customerCode !=""){
    			Ext.getCmp('customerCode').setCombValue(data.customerName,data.customerCode);
    		}
    		return true;
    	},
	}
	});

/**
 * 优惠分段信息GRID
 */
Ext.define("Miser.view.priceEvnetAdd.PriceCustomerList", {
    extend: "Ext.grid.Panel",
    alias: 'widget.priceCustomerList',
    id : 'priceCustomerList',
    store: "PriceCustomerStore",
    region : 'center',
    width : '100%',
    height: 250,
    viewConfig:{
		enableTextSelection:true,
	},
    selModel : Ext.create('Ext.selection.CheckboxModel'),
    columns : {
		defaults: {
		    align : 'left'
		},
		items : [
		    {text : '序号',	width : 50,	dataIndex: 'rownumberer',xtype : 'rownumberer'},
		    {text:'id',dataIndex: 'cus_id',xtype: 'hiddenfield'},
	        { text: '客户编码', dataIndex: 'customerCode', width : 110,editor: { id:'customerCode',xtype: "bsecustomercombselector",listeners: {
//	        	'select' : function(combo, record){
//	        		customerName =  combo.getRawValue();
//				}
	        }},renderer: function(value,o,m){
				  var sto = Ext.getCmp('customerCode');
//				  var display = '';
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  m.data.customerName = getDisplayTextCommon(sto.getStore(),value);
				  }
//				  else
//				 {
//					  m.data.customerName= '';
//				 }
				 return value;
			 }},
	        { text: '客户名称', dataIndex: 'customerName', width : 120,editor: {id:'customerName',editable : false, xtype: "textfield"},renderer:isValue   },
		 ]
    },
    plugins: [cellCusEditing],
    autoScroll: true,
    tbar : [{
    	text : '新增',
    	id:'tbar_cus_add',
    	handler: function() {
    		var win = Ext.getCmp('priceCustomerList'); 
    		var store = win.getStore();  
    		 var count = store.getCount(); 
    		 var data = [{}]; 
    		 customerName=null;
//    		 store.insert(count+1,data);
    		 
    		 cellCusEditing.cancelEdit();
             var r = Ext.create('Miser.model.PriceCustomerEntity');
             store.insert(store.data.length+1, r);
             cellCusEditing.startEdit(store.data.length-1, 2);
    	}
    }, {
    	text : '修改',
    	id:'tbar_cus_edit',
    	handler: function() {
    		var records=Ext.getCmp('priceCustomerList').getSelectionModel().getSelection();
    		if(records.length > 0){
    			if(records.length == 1){
    				cellCusEditing.cancelEdit();
    				cellCusEditing.startEditByPosition({row:Ext.getCmp('priceCustomerList').getStore().indexOf(records[0]),column:2});
    			}else{
    				Ext.MessageBox.alert('提示','不能同时选择多行修改');
    			}
    		}else{
    			Ext.MessageBox.alert('提示','请选择需要修改的行');
    		}
    	}
    }, {
    	text : '删除',
    	id:'tbar_cus_del',
    	handler: function() {
    		var records=Ext.getCmp('priceCustomerList').getSelectionModel().getSelection();
    		if(records.length > 0){
    			Ext.each(records, function(record) {
    	    		//先通过ajax从后台删除数据，删除成功后再从页面删除数据
    				Ext.getCmp('priceCustomerList').getStore().remove(record);
    	    	});
    		}else{
    			Ext.MessageBox.alert('提示','请选择需要删除的行');
    		}
    	}
    }]
});



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


function isValue(value,meta,record, rowIndex, columnIndex, store){
	if(record.data.customerName != null){
		return record.data.customerName;
	}else
		return value;
}