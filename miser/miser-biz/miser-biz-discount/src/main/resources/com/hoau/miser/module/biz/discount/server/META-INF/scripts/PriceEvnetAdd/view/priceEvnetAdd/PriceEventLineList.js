/**
 * 线路信息GRID
 */
Ext.define("Miser.view.priceEvnetAdd.PriceEventLineList", {
    extend: "Ext.grid.Panel",
    alias: 'widget.priceEventLineList',
    id : 'priceEventLineListId',
    store: "PriceEventLineStore",
    region : 'center',
    width : '100%',
    height: 250,
    multiSelect:true,
    viewConfig:{
		enableTextSelection:true
	},
   // selModel : Ext.create('Ext.selection.CheckboxModel'),
    columns : {
		defaults: {
		    align : 'left'
		},
		items : [
		    {text : '序号',	width : 50,	dataIndex: 'rownumberer',xtype : 'rownumberer'},
		    {text:'id',dataIndex: 'line_id',xtype: 'hiddenfield'},
	        { text: '出发价格城市', dataIndex: 'sendPriceCity', width : 110,editor: {id:'sendPriceCity', xtype: "textfield"},
		    	renderer: function(value,o,m){
				  var sto = Ext.getCmp('sendPriceCity');
				  var display = '';
				  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
				  {
					  display = getDisplayTextCommon(sto.getStore(),value);
				  }
				  else
				 {
					  display = m.data.sendPriceCityName;
				 }
				  m.data.sendPriceCityName = display;
				 return display;
		     } },
	        { text: '到达价格城市', dataIndex: 'arrivalPriceCity', width : 110,editor: {id:'arrivalPriceCity', xtype: "textfield"},
		    	 renderer: function(value,o,m){
					  var sto = Ext.getCmp('arrivalPriceCity');
					  var display = '';
					  if(sto != null && sto != undefined && sto.getStore() !=null && sto.getStore().data.length != 0)
					  {
						  display = getDisplayTextCommon(sto.getStore(),value);
					  }
					  else
					 {
						  display = m.data.arrivalPriceCityName;
					 }
					  m.data.arrivalPriceCityName = display;
					 return display;
			     }},
	        {
                xtype: 'actioncolumn',
               
                width: 40,
                items: [ {
                	text : '删除',
                    icon: '../images/common/delete.gif',  // Use a URL in the icon config
                    tooltip: '删除',
                    align: 'center',
                    handler: function (grid, rowIndex, colIndex) {
                        var rec =  grid.getStore().getAt(rowIndex);
//                    	rowEditing.startEdit(rowIndex,0);
                    	grid.getStore().remove(rec);
//                        alert("Sell " + rec.get('company'));
                    }
                }]
            }
		 ]
    },
 //  plugins: [rowEditing],
    autoScroll: true,
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