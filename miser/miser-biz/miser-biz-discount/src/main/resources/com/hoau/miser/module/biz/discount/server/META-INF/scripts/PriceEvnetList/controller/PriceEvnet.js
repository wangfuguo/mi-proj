/**
 * 活动管理Controller
 */
Ext.define('Miser.controller.PriceEvnet', {
    extend: 'Ext.app.Controller',
    stores: ['PriceEvnet'],
    models: ['PriceEvnet'],
    views: ['Viewport', 'priceEvnet.List','priceEvnet.Search'],
    init: function () {
        this.control({
            'priceEvnetlist button[action=add]': {
                click: this.addPriceEvnet
            },
            'priceEvnetlist button[action=copyAdd]': {
                click: this.copyAddPriceEvnet
            },
            'priceEvnetSearch button[action=select]': {
                click: this.selectPriceEvnet
            },
            'priceEvnetlist button[action=selectdetail]': {
                click: this.selectDetailPriceEvnet
            },
            'priceEvnetlist button[action=update]': {
                click: this.updatePriceEvnet
            },
            'priceEvnetlist button[action=delete]': {
                click: this.deletePriceEvnet
            },
            'priceEvnetlist button[action=mandatory]': {
                click: this.mandatoryPriceEvnet
            },
            'priceEvnetSearch button[action=reset]': {
                click: this.resetPriceEvnet
            },
//            'priceEvnetlist': {
//            	cellclick: this.lookPriceEvnet
//            },
//            'priceEvnetlist': {
//            	itemdblclick:function(dataview, record, item, index, e){
//            	       alert("itemdblclick"); 
//            	  }
              //  'itemdblclick': this.lookPriceEvnet
//            },
            'priceEvnetlist': {
            	selectionchange: this.selectionchangeList
            }
        });
    },
    addPriceEvnet : function(btn){
    	window.parent.initTabpanel('101002', '新增活动', '/miser-biz-web/discount/priceEvnetAdd.action', true);
//    	var win = Ext.widget("priceEvnetAddWin"); 
//        win.show();
    },
    selectPriceEvnet : function(btn){
    	Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getPagingToolbar().moveFirst();
    },
    copyAddPriceEvnet : function(){
    	// 选中的数据
    	var selectArr = Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getSelectionModel().getSelection();
    	if(selectArr.length == 0){
			Ext.MessageBox.alert('提示','请选择需要复制的活动信息');
		}else if(selectArr.length > 1){
			Ext.MessageBox.alert('提示','只能选择一个活动信息进行复制');
		}else {
			// 修改客户
        	window.parent.delTabpanel('101002');
        	var cId = selectArr[0].get('id');
        	var eventCode = selectArr[0].get('eventCode');
        	var url = '/miser-biz-web/discount/priceEvnetAdd.action?cId=' + cId+'&lock=copyAdd&eventCode='+eventCode;
        	window.parent.initTabpanel('101002', '复制新增', url, true);
		}
    },
    lookPriceEvnet : function(dataview, record, item, index, e){
//    	alert("d");
//    	// 查看活动
//    	window.parent.delTabpanel('101001201');
//    	var selectArr = Ext.getCmp('customerViewId').getCustomerGrid().getSelectionModel().getSelection();
//    	var cId = selectArr[0].get('id');
//    	var eventCode = selectArr[0].get('eventCode');
//    	var url = '/miser-biz-web/discount/priceEvnetAdd.action?cId=' + cId+'&lock=lock&eventCode='+eventCode;
//    	window.parent.initTabpanel('101001201', '查看活动信息', url, true);
    },selectDetailPriceEvnet:function(btn){
    	// 选中的数据
    	var selectArr = Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getSelectionModel().getSelection();
    	if(selectArr.length == 0){
			Ext.MessageBox.alert('提示','请选择需要查看的活动信息');
		}else if(selectArr.length > 1){
			Ext.MessageBox.alert('提示','只能选择一个活动信息进行查看');
		}else {
			// 查看活动
        	window.parent.delTabpanel('101003');
        	var cId = selectArr[0].get('id');
        	var eventCode = selectArr[0].get('eventCode');
        	var url = '/miser-biz-web/discount/priceEvnetAdd.action?cId=' + cId+'&lock=lock&eventCode='+eventCode;
        	window.parent.initTabpanel('101003', '查看活动信息', url, true);
		}
    },updatePriceEvnet:function(btn){
    	// 选中的数据
    	var selectArr = Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getSelectionModel().getSelection();
    	if(selectArr.length == 0){
			Ext.MessageBox.alert('提示','请选择需要修改的活动信息');
		}else if(selectArr.length > 1){
			Ext.MessageBox.alert('提示','只能选择一个活动信息进行修改');
		}else {
			// 修改客户
        	window.parent.delTabpanel('101003');
        	var cId = selectArr[0].get('id');
        	var eventCode = selectArr[0].get('eventCode');
        	var url = '/miser-biz-web/discount/priceEvnetAdd.action?cId=' + cId+'&eventCode='+eventCode;
        	window.parent.initTabpanel('101003', '修改活动',url, true);
		}
    },deletePriceEvnet:function(btn){
    	// 选中的数据
    	var selectArr = Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getSelectionModel().getSelection();
    	if(selectArr.length > 0){
	    		if(selectArr.length > 1){
	    			Ext.MessageBox.alert('提示','只能选择一个活动信息进行修改');
	    			return;
	    		}
				//删除的信息集合
//				var deleteInfoArr = [];
//				for(var i=0; i<selectArr.length; i++){
//					deleteInfoArr.push(selectArr[i].get('id'))
//				}
	    		var priceEvnetEntity = {};
	    		priceEvnetEntity.id = selectArr[0].get('id');
				Ext.Msg.confirm('提示', '您确定要作废选中的活动信息？该操作不可恢复', function(btn) {
					if(btn == 'yes') {
						var params = {};
//						params.ids = deleteInfoArr;
						params.priceEvnetEntity = priceEvnetEntity;
						// AJAX请求
						miser.requestJsonAjax('priceEvnetAction!deletePriceEvnet.action', params, 
						function(){
							Ext.MessageBox.alert('提示','活动信息作废成功');
							Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getStore().reload();
						}, 
						function(){Ext.MessageBox.alert('提示','活动信息作废失败');});
					}
				});
		}else{
			Ext.MessageBox.alert('提示','请选择需要作废的活动信息');
		}
    
    },mandatoryPriceEvnet:function(btn){
    	// 选中的数据
    	var selectArr = Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getSelectionModel().getSelection();
    	if(selectArr.length > 0){
	    		if(selectArr.length > 1){
	    			Ext.MessageBox.alert('提示','只能选择一个活动信息进行修改');
	    			return;
	    		}
				//删除的信息集合
//				var deleteInfoArr = [];
//				for(var i=0; i<selectArr.length; i++){
//					deleteInfoArr.push(selectArr[i].get('id'))
//				}
	    		var priceEvnetEntity = {};
	    		priceEvnetEntity.id = selectArr[0].get('id');
				Ext.Msg.confirm('提示', '您确定要强制作废选中的活动信息？该操作不可恢复', function(btn) {
					if(btn == 'yes') {
						var params = {};
//						params.ids = deleteInfoArr;
						params.priceEvnetEntity = priceEvnetEntity;
						// AJAX请求
						miser.requestJsonAjax('priceEvnetAction!stopPriceEvent.action', params, 
						function(){
							Ext.MessageBox.alert('提示','活动信息作废成功');
							Ext.getCmp('priceEvnetViewId').getPriceEvnetGrid().getStore().reload();
						}, 
						function(){Ext.MessageBox.alert('提示','活动信息作废失败');});
					}
				});
		}else{
			Ext.MessageBox.alert('提示','请选择需要作废的活动信息');
		}
    },resetPriceEvnet:function(btn){
    	btn.up('form').getForm().reset();
    }
    ,selectionchangeList: function(sm, selections) {
    	if(selections.length == 1){
    		if(selections[0].get('state') == 2){
    			Ext.getCmp('delete_id').disable();
    			Ext.getCmp('update_id').disable();
    			Ext.getCmp('mandatory_id').enable();
    		}else{
    			if(selections[0].get('state') == 3){
    				Ext.getCmp('delete_id').enable();
    				Ext.getCmp('update_id').enable();
    				Ext.getCmp('mandatory_id').disable();
    			}else{
    				Ext.getCmp('mandatory_id').disable();
    				Ext.getCmp('delete_id').disable();
    				Ext.getCmp('update_id').disable();
    			}
    		}
    	}else{
    		Ext.getCmp('mandatory_id').setDisabled(false);
			Ext.getCmp('delete_id').setDisabled(false);
			Ext.getCmp('update_id').setDisabled(false);
//    		Ext.getCmp('delete_id').setDisabled(selections.length === 0);
    	}
//    	alert("1");
// 		Ext.getCmp('delete_id').setDisabled(selections.length === 0);
	}
});
