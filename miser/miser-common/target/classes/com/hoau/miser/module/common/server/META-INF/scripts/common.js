/**
 * Extjs编码规范：
 * 1.普通组件命名规范：Miser.'模块'.'功能菜单'.'**Form|Window|Grid|Store'
 * 	 (例如：Miser.baseinfo.goodsArea.queryForm)
 * 2.id命名规范:miser_'模块'_'功能'_'组件'_id
 * 	 (例如:miser_baseinfo_goodsArea_queryForm_id)
 * 3.公共选择器命名规范:Miser.common*.**|Selector|CheckBox
 *   (例如:Miser.commonSelector.DataDictionaryCommonSelector,Miser.commonCheckBox.YesOrNoCheckBox)
 * 4.常用工具类：在wing-common中的scripts中的miser-util.js
 * 5.数据字典使用:
 * 	 获取store:getDataDictionary().getDataDictionaryStore("GENDER")
 * 	 下拉框使用:
 * 	  var all = [{
 *			'valueName' : '全部',       //'全部'
 *			'valueCode' : ''
 *        }];
 * 	  {
 *        	name : 'status', 
 *        	fieldLabel : '车辆状态',        //'车辆状态'
 *    		width : 250,
 *    		value : '',
 *    		xtype : 'dataDictionarySelector',anyRecords:all,
 *   		termsCode : "termscode",
 *   		anyRecords : all
 *       }
 *    表格code转换value:
 *    renderer : function (value) {
 *    	miser.changeCodeToNameStore(getDataDictionary().getDataDictionaryStore("termscode"), value);
 * 	  }
 * 6.获取当前登录用户及部门信息:getUserContext();
 * 
 */
var all = [{
 			'valueName' : '全部',       //'全部'
 			'valueCode' : ''
         }];
/**
 * 获取当前登录用户信息
 * 从父页面中获取当前登录用户信息
 * @returns
 * @author 高佳
 * @date 2015年5月15日
 * @update
 */
function getUserContext(){
	return window.parent.UserContext;
}
/**
 * 获取数据字典信息
 * 从父页面中获取数据字典信息
 * @returns
 * @author 高佳
 * @date 2015年5月15日
 * @update
 */
function getDataDictionary(){
	return window.parent.DataDictionary;
}

/**
 * 日期类型数据转换器
 */
function dateConvert(value, record) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
}

/**
 * 表格日期显示
 * @param value
 * @param format
 * @returns
 */
function dateRender(value,format){
	if(Ext.isEmpty(format)){
		format = 'Y-m-d';
	}
	if(!Ext.isEmpty(value)){
		return Ext.Date.format(new Date(value), format);
	}
	return value;
}

/**
 * 是和否Render
 * @param value
 * @param format
 * @returns
 */
function booleanTypeRender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if(miser.booleanType.yes == value){
		return '是';
	}
	if(miser.booleanType.no == value){
		return '否';
	}
	return value;
}

/**
 * 保留2位小数 四舍五入
 */
function numberRound(value){
	return value.toFixed(2);
}

/**
 * 保留2位小数下取
 */
function numberFloor(value){
	return Math.floor(value*100)/100;
}

/**
 * 保留2位小数上取
 */
function numberCeil(value){
	return Math.ceil(value*100)/100;
	
}

/**
 * 字体图标按钮定义
 */
//新增按钮
Ext.define('Miser.commonButton.CommonAddButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.addbutton',
	glyph : 0xf055,
	cls: 'add-btn-item'
});
//删除按钮
Ext.define('Miser.commonButton.CommonDeleteButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.deletebutton',
	glyph : 0xf014,
	cls: 'delete-btn-item'
});
//修改编辑按钮
Ext.define('Miser.commonButton.CommonUpdateButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.updatebutton',
	glyph : 0xf044,
	cls: 'update-btn-item'
});
//设置按钮
Ext.define('Miser.commonButton.CommonSettingsButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.settingsbutton',
	glyph : 0xf085,
	cls: 'settings-btn-item'
});
//下载按钮
Ext.define('Miser.commonButton.CommonDownloadButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.downloadbutton',
	glyph : 0xf019,
	cls: 'download-btn-item'
});
//上传按钮
Ext.define('Miser.commonButton.CommonUploadButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.uploadbutton',
	glyph : 0xf093,
	cls: 'upload-btn-item'
});
//查询按钮
Ext.define('Miser.commonButton.CommonSearchButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.searchbutton',
	glyph : 0xf002,
	cls: 'search-btn-item'
});
//关闭按钮
Ext.define('Miser.commonButton.CommonCloseButton', {
	extend : 'Ext.button.Button',
	alias: 'widget.closebutton',
	glyph : 0xf00d,
	cls: 'delete-btn-item'
});

/**
 * 判断当前用户是否有此权限
 * 
 * @param authCode
 * @returns
 * @author 275636
 * @date 2016-1-14
 * @update
 */
function isButtonHide(functionCode){
	var roles = window.parent.UserContext.getCurrentUserRoleCodes();
	if(roles != null){
		if(Ext.Array.contains(roles, functionCode)){
			return false;
		}
	}
	return true;
}