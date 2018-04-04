/**
 * 员工信息实体
 */
Ext.define('Miser.base.outerBranch.outerBranchEntity', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'code',
        //编号
        type: 'string'
    },
    {
        name: 'name',
        //名称
        type: 'string'
    },
    {
        name: 'logistCode',
        //物流代码
        type: 'string'
    },
    {
        name: 'province',
        //省
        type: 'string'
    },
    {
        name: 'city',
        //市
        type: 'string'
    },
    {
        name: 'county',
        //区
        type: 'string'
    },
    {
        name: 'phone',
        //电话
        type: 'string'
    },
    {
        name: 'fax',
        //传真
        type: 'string'
    },
    {
        name: 'modifyTime',
        type: 'date',
        dateFormat:'time'
    }]
});
//暂时留下来，做是否
function booleanTypeGender(value){
	if(Ext.isEmpty(value)){
		return value;
	}
	if(value== 'M'){
		return '男';
	}
	if(value == 'F'){
		return '女';
	}
	return value;
}



Ext.define('Miser.base.outerBranch.outerBranchForm', {
    extend: 'Ext.form.Panel',
    id: 'Miser_base_outerBranch_outerBranchForm_id',
    frame: true,
    title: base.outerBranch.i18n('miser.common.queryCondition'),//查询条件
    height: 113,
    collapsible: true,
    layout: 'column',
    region: 'north',
    
    defaultType: 'textfield',
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},config);
        me.items = [{layout: {
            type: 'hbox',
            pack: 'start',
            align: 'stretch'
        	},
        	name: '',
            labelWidth: 20,
            cityLabel: '市',
            cityName: 'cityName',
            cityIsBlank:true,
            //名称
            provinceLabel: '省',
            provinceName: 'provinceName',
            provinceIsBlank:true,
            //省名称
            areaLabel: '县',
            areaName: 'areaName',
            areaIsBlank:true,
            
            // 县名称
            type: 'P-C-C',
            xtype: 'linkregincombselector'
        },{
            name: 'logistCode',
            fieldLabel: base.outerBranch.i18n('bse.outerBranch.logistCode'),//'员工工号',
            xtype: 'textfield'
        }],
        me.buttons = [{
            text: base.outerBranch.i18n('miser.common.query'),//查询
            handler: function() {
                if (me.getForm().isValid()) {
                    me.up().getouterBranchGrid().getPagingToolbar().moveFirst();
                }
            }
        },
        {
            text: base.outerBranch.i18n('miser.common.reset'),//重置
            handler: function() {
                me.getForm().reset();
            }
        }];
        me.callParent([cfg]);
    }
});

/**
 * 员工信息表格
 */
Ext.define('Miser.base.outerBranch.outerBranchGrid', {
    extend: 'Ext.grid.Panel',
    frame: true,
    autoScroll: true,
    width: '100%',
    height: document.documentElement.clientHeight - 150,
    stripeRows: true,
    // 交替行效果
    region: 'center',
    emptyText: base.outerBranch.i18n('miser.common.resultisNull'),//查询结果为空
    //查询结果为空
    viewConfig: {
        enableTextSelection: true
    },
    pagingToolbar: null,
    getPagingToolbar: function() {
        if (this.pagingToolbar == null) {
            this.pagingToolbar = Ext.create('Ext.toolbar.Paging', {
                store: this.store,
                pageSize: 20
            });
        }
        return this.pagingToolbar;
    },
    constructor: function(config) {
        var me = this,
        cfg = Ext.apply({},
        config);
        me.columns = [{
            text: '序号',
            width: 60,
            xtype: 'rownumberer',
            align: 'center'
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.code'),//'编号'
            dataIndex: 'code',
            align: 'center',
            flex: 1
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.name'),//'名称',
            dataIndex: 'name',
            align: 'center',
            flex: 1
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.logistCode'),//'物流代码',
            dataIndex: 'logistCode',
            align: 'center',
            flex: 1
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.province'),//'省',
            dataIndex: 'province',
            align: 'center',
            flex: 1
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.city'),//'市',
            dataIndex: 'city',
            align: 'center',
            flex: 1
        },
        {
            text: base.outerBranch.i18n('bse.outerBranch.county'),//'区',
            dataIndex: 'county',
            align: 'center',
            flex: 1
        },
        {
            text:base.outerBranch.i18n('bse.outerBranch.phone'),// '电话',
            dataIndex: 'phone',
            align: 'center',
            flex: 1
        },
        {
            text:base.outerBranch.i18n('bse.outerBranch.fax'),//'传真',
            dataIndex: 'fax',
            align: 'center',
            flex: 1
        },
        {        	
            text: base.outerBranch.i18n('bse.outerBranch.modifyTime'),//'修改时间',
            dataIndex: 'modifyTime',
            align: 'center',
            flex: 1,
            xtype :'datecolumn', 
            format :'Y-m-d H:i:s'
        }],
        me.store = Ext.create('Miser.base.outerBranch.outerBranchStore', {
            autoLoad: false
        });
        me.bbar
        me.bbar = me.getPagingToolbar();
        me.callParent([cfg]);
    }
});



/**
 * 员工信息store
 */
Ext.define('Miser.base.outerBranch.outerBranchStore', {
    extend: 'Ext.data.Store',
    model: 'Miser.base.outerBranch.outerBranchEntity',
    pageSize: 20,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: 'outerBranch!queryOuterBranchList.action',
        reader: {
            type: 'json',
            rootProperty: 'outerBranchVo.outerBranchList',
            totalProperty: 'totalCount' //总个数
        }
    },
    listeners: {
        beforeload: function(store, operation, eOpts) {
            var queryForm = Ext.getCmp('Miser_base_outerBranch_outerBranchForm_id');
            if (queryForm != null) {
                var params = {
                    'outerBranchVo.outerBranchEntity.logistCode': Ext.util.Format.trim(queryForm.getForm().findField('logistCode').getValue()),
                    'outerBranchVo.outerBranchEntity.provinceCode': queryForm.getForm().findField('provinceName').getValue(),
                    'outerBranchVo.outerBranchEntity.cityCode': queryForm.getForm().findField('cityName').getValue(),
                    'outerBranchVo.outerBranchEntity.countyCode': queryForm.getForm().findField('areaName').getValue(),
                };
                Ext.apply(store.proxy.extraParams, params);
            }
        }
    }
});




Ext.onReady(function() {
    var queryForm = Ext.create("Miser.base.outerBranch.outerBranchForm");
    var outerBranchGrid = Ext.create("Miser.base.outerBranch.outerBranchGrid");
    Ext.create('Ext.Viewport', {
        header: {
            titlePosition: 2,
            titleAlign: 'center'
        },
        border: "0 0 0 0",
        renderTo: Ext.getBody(),
        layout: {
            type: 'border'
        },
        getQueryForm: function() {
            return queryForm;
        },
        getouterBranchGrid: function() {
            return outerBranchGrid;
        },
        items: [queryForm, outerBranchGrid]
    });
	
});
















