// init prt window 
var KEY_def_width = 1000;
var KEY_def_height = 600;
// setup css
document.write("<style>");
document.write(".prtpreviewbtnup {margin:0px;padding:0px;width:100%;height:100%;background:#dfdfdf;cursor:hand;border:2 solid black;border-top:2 solid silver;border-left: 2 solid silver;}");
document.write(".prtpreviewbtndown {margin:0px;padding:0px;width:100%;height:100%;background:#dfdfdf;cursor:hand;border:2 solid silver;border-top:2 solid black;border-left: 2 solid black;}");
document.write(".prtpreviewtoolbar { }");
document.write(".prtpreviewtoolbar td { font-size:9pt;font-family:微软雅黑;text-align:center; }");
document.write(".prtpreviewtoolbar button { font-size:9pt;font-family:微软雅黑;text-align:center; }");
document.write("</style>");

var viewform_html = []; 
viewform_html.push('<table border=0 width=100% cellpadding=0 cellspacing=0>');
viewform_html.push('<tr><td><table class="prtpreviewtoolbar" border=0 cellpadding=0 cellspacing=0>');
viewform_html.push('<tr align=left>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(1)" value="适高"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(2)" value="正常"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(3)" value="适宽"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(4)" value="放大"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(5)" value="缩小"></td>');
viewform_html.push('<td><select id="percent" onchange="prtViewAction(6)">');
viewform_html.push('<option value=0>30%</option><option value=1>50%</option><option value=2>60%</option>');
viewform_html.push('<option value=3>70%</option><option value=4>80%</option><option value=5>85%</option>');
viewform_html.push('<option value=6>90%</option><option value=7>95%</option><option value=8>100%</option>');
viewform_html.push('<option value=9>125%</option><option value=10>150%</option><option value=11>200%</option>');
viewform_html.push('<option value=12>按整宽</option><option value=13>按整高</option><option value=14 selected>按整页</option>');
viewform_html.push('<option value=15>整宽不变形</option><option value=16>整高不变形</option><option value=17>其它比例</option>');
viewform_html.push('</select></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(7)" value="首页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(8)" value="上页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(9)" value="下页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(10)" value="尾页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(12)" value="设置"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(13)" value="打印全部"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(14)" value="打印本页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(15)" value="关闭"></td>');
viewform_html.push('<td><span id="chkdisplayimg" style="display:none;"><input id="ckhView" type=checkbox onclick="prtViewAction(16,this)" checked>显示背景</span></td>');
viewform_html.push('</tr></table></td></tr><tr><td>');
viewform_html.push('<div style="background:silver;padding:5px;">');
viewform_html.push('<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=100%>');
viewform_html.push('<embed id="LODOP_EM" TYPE="application/x-print-lodop" style="border:1px solid #000000;" width=100% height='+(KEY_def_height-200)+' color="#ffffff" '); 
viewform_html.push('PLUGINSPAGE="http://file.deppon.com.cn/FOSS_WEB_PRINT.exe"></embed></object></div>');
viewform_html.push('</td></tr></table><div id="printresultdiv" style="width:1px;height:1px;"></div>');

var hiddenhtml = [];
hiddenhtml.push('<div style="width:1px;height:1px;background:silver;padding:5px;">');
hiddenhtml.push('<object id="LODOP_OB_dprt" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=1 height=1>');
hiddenhtml.push('<embed id="LODOP_EM_dprt" TYPE="application/x-print-lodop" style="border:1px solid #000000;" width=1 height=1 color="#ffffff" '); 
hiddenhtml.push('PLUGINSPAGE="http://file.deppon.com.cn/FOSS_WEB_PRINT.exe"></embed></object></div>');
hiddenhtml.push('<div id="printresultdiv_dprt" style="width:1px;height:1px;"></div>');
//--

function btncssdown( pbtnobj){
	pbtnobj.className="prtpreviewbtndown";
}

function btncssup( pbtnobj){
	pbtnobj.className="prtpreviewbtnup";
}

function prtViewAction( pActionType, pobj){
	if (mLODOP){
		switch(pActionType){
			case 1:mLODOP.DO_ACTION("PREVIEW_ZOOM_HIGHT",0);break;
			case 2:mLODOP.DO_ACTION("PREVIEW_ZOOM_NORMAL",0);break;
			case 3:mLODOP.DO_ACTION("PREVIEW_ZOOM_WIDTH",0);break;
			case 4:mLODOP.DO_ACTION("PREVIEW_ZOOM_IN",0);break;
			case 5:mLODOP.DO_ACTION("PREVIEW_ZOOM_OUT",0);break;
			case 6:mLODOP.DO_ACTION("PREVIEW_PERCENT",document.getElementById('percent').value);break;
			case 7:mLODOP.DO_ACTION("PREVIEW_GOFIRST",0);break;
			case 8:mLODOP.DO_ACTION("PREVIEW_GOPRIOR",0);break;
			case 9:mLODOP.DO_ACTION("PREVIEW_GONEXT",0);break;
			case 10:mLODOP.DO_ACTION("PREVIEW_GOLAST",0);break;
			case 11:mLODOP.DO_ACTION("PREVIEW_GOTO",document.getElementById('inputpage').value);break;
			case 12:mLODOP.DO_ACTION("PREVIEW_SETUP",0);break;
			case 13:
				if (chkDisplayimg() == false) {
					break;
				} else {
					doPrintAll();break;
				}
			case 14:
				if (chkDisplayimg() == false) {
					break;
				} else {
					doPrintCurrentPage();break;
				}
			case 15:doClose();break;
			case 16:displaybkimg(pobj);break;
			default:break;
		}
	}
}

function doPrintAll(){	
	if (mLODOP) {
		var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
		mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
		mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
		mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	}
}

function doPrintCurrentPage(){	
	if (mLODOP) {
		var iThisNumber=mLODOP.GET_VALUE("PREVIEW_PAGE_NUMBER",0);//获得当前页号
		mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",iThisNumber);
		mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iThisNumber);	
		mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	}
}

function doClose(){	
	//alert("close this window");
	priviewWin.close();
}

var CreatedOKLodop7766=null;
function getLodop(oOBJECT,oEMBED){
/**************************
  本函数根据浏览器类型决定采用哪个页面元素作为Lodop对象：
  IE系列、IE内核系列的浏览器采用oOBJECT，
  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED,
  如果页面没有相关对象元素，则新建一个或使用上次那个,避免重复生成。
  64位浏览器指向64位的安装程序install_lodop64.exe。
**************************/
        var strHtmInstall="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
        var strHtmUpdate="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
        var strHtm64_Install="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
        var strHtm64_Update="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop64.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
        var strHtmFireFox="<br><br><font color='#FF00FF'>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
        var strHtmChrome="<br><br><font color='#FF00FF'>(如果此前正常，仅因浏览器升级或重安装而出问题，需重新执行以上安装）</font>";
        var LODOP;		
	try{	
	     //=====判断浏览器类型:===============
	     var isIE	 = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
	     var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
	     //=====如果页面有Lodop就直接使用，没有则新建:==========
	     if (oOBJECT!=undefined || oEMBED!=undefined) { 
               	 if (isIE) 
	             LODOP=oOBJECT; 
	         else 
	             LODOP=oEMBED;
	     } else { 
		 if (CreatedOKLodop7766==null){
          	     LODOP=document.createElement("object"); 
		     LODOP.setAttribute("width",0); 
                     LODOP.setAttribute("height",0); 
		     LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");  		     
                     if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA"); 
		     else LODOP.setAttribute("type","application/x-print-lodop");
		     document.documentElement.appendChild(LODOP); 
	             CreatedOKLodop7766=LODOP;		     
 	         } else 
                     LODOP=CreatedOKLodop7766;
	     };
	     //=====判断Lodop插件是否安装过，没有安装或版本过低就提示下载安装:==========
	     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
	             if (navigator.userAgent.indexOf('Chrome')>=0)
	                 document.documentElement.innerHTML=strHtmChrome+document.documentElement.innerHTML;
	             if (navigator.userAgent.indexOf('Firefox')>=0)
	                 document.documentElement.innerHTML=strHtmFireFox+document.documentElement.innerHTML;
	             if (is64IE) document.write(strHtm64_Install); else
	             if (isIE)   document.write(strHtmInstall);    else
	                 document.documentElement.innerHTML=strHtmInstall+document.documentElement.innerHTML;
	             return LODOP;
	     } else 
	     if (LODOP.VERSION<"6.1.9.8") {
	             if (is64IE) document.write(strHtm64_Update); else
	             if (isIE) document.write(strHtmUpdate); else
	             document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
	    	     return LODOP;
	     };
	     //=====如下空白位置适合调用统一功能(如注册码、语言选择等):====	     


	     //============================================================	     
	     return LODOP; 
	} catch(err) {
	     if (is64IE)	
            document.documentElement.innerHTML="Error:"+strHtm64_Install+document.documentElement.innerHTML;else
            document.documentElement.innerHTML="Error:"+strHtmInstall+document.documentElement.innerHTML;
	     return LODOP; 
	};
}


var mLODOP= null;
function fillLodopByFullJasperPrint(){
	var maindiv = document.getElementById("printresultdiv");
	
	var fulljpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("fulljpdiv"==odiv.id){
			fulljpdivobj = odiv;
			break;
		}
	}
	
	if(fulljpdivobj!=null){
		var divs = fulljpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示 
		mLODOP.PREVIEW();
	}
}

function fillLodopByNoImgJasperPrint(){
	
	var maindiv = document.getElementById("printresultdiv");
	
	var noimgjpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("noimgjpdiv"==odiv.id){
			noimgjpdivobj = odiv;
			break;
		}
	}
	
	if(noimgjpdivobj!=null){
		var divs = noimgjpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示
		mLODOP.PREVIEW();
	}
}

function newpage( pLODOP, pdivobj){
	var vwidth = pdivobj.offsetWidth;
	var vheight = pdivobj.offsetHeight
	pLODOP.NewPage();
	
	var imgs = pdivobj.getElementsByTagName("IMG");
	if(imgs!=null){
		for(var a=0;a<imgs.length;a++){
			var vsrc = imgs[a].src;
			if(vsrc.indexOf("data:image/")!=-1){
				imgs[a].style.display="none";
			}
		}
	}

	pLODOP.ADD_PRINT_HTM(0,0,"100%","100%",pdivobj.innerHTML);
	var imgs = pdivobj.getElementsByTagName("IMG");
	if(imgs!=null){
		for(var a=0;a<imgs.length;a++){
			var vsrc = imgs[a].src;
			if(vsrc.indexOf("data:image/")!=-1){
				//alert(parseInt(imgs[a].parentElement.style.top,10));
				//alert(parseInt(imgs[a].parentElement.style.left,10));
				//alert(imgs[a].style.top);
				//alert(imgs[a].style.left);
				var vtop = parseInt(imgs[a].parentElement.style.top,10) + parseInt(imgs[a].style.top,10);
				var vleft = parseInt(imgs[a].parentElement.style.left,10) + parseInt(imgs[a].style.left,10);
				
				var imgwidth = parseInt(imgs[a].parentElement.style.width);
				var imgheight = parseInt(imgs[a].parentElement.style.height);
				
				mLODOP.ADD_PRINT_IMAGE(vtop+"pt",vleft+"pt",imgwidth+"pt",imgheight+"pt",vsrc);
			}
		}
	}	
}
function chkDisplayimg() {
	var ckhView = document.getElementById("ckhView");
	var chkdisplay = document.getElementById("chkdisplayimg").style.display;
	if (chkdisplay == "block" && ckhView.checked) {
		alert("请点击显示背景按钮，以取消打印背景图。");
		return false;
	}
}
function opentosetup(){
	//mLODOP.PRINT_SETUP();	
}

function displaybkimg( pchkobj){
	if(pchkobj.checked){		
		fillLodopByFullJasperPrint();
	}
	else {
		fillLodopByNoImgJasperPrint();
	}
}
/**
 * 打印预览function
 * @param pPrtKey
 * @param pParam
 * @param pCtxPath
 * @param pWidth
 * @param pHeight
 */
function do_printpreview(pPrtKey, pParam, pCtxPath, pWidth, pHeight){
	var vwidth = KEY_def_width-50;
	var vheight = KEY_def_height-100;
	if(pWidth==null){
		pWidth = vwidth;
	}
	if(pHeight==null){
		pHeight = vheight;
	}	
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	do_printpreview_native(pParam,vCtxPath, pWidth,pHeight);
}

var priviewWin = null;
function do_printpreview_native( pParam,pCtxPath, pWidth, pHeight){

	priviewWin = new Ext.Window({
		title:'打印预览',
	    layout: 'fit',
		height: KEY_def_height,
		width: KEY_def_width,
	    border: false,
	    html: viewform_html.join('')
	});
	
	priviewWin.setWidth(pWidth);
	priviewWin.setHeight(pHeight);
	priviewWin.show();
	
	//var myMask = new Ext.LoadMask(priviewWin, {msg:"Please wait..."});
	//myMask.show();
	
	Ext.Ajax.request({
	    url: pCtxPath+'/common/exportHtmlAction!exportHtml.action',
	    params:pParam,
	    success: function(response){
	    	var reesultdivobj = document.getElementById('printresultdiv');
	    	reesultdivobj.innerHTML = response.responseText;
	    	try{
		    	var hasimg = document.getElementById("hasimg").value;
		    	if("true"==hasimg){
		    		document.getElementById("chkdisplayimg").style.display="block";
		    	}	    		
	    	}catch(exp1){
	    		document.getElementById("chkdisplayimg").style.display="none";
	    	}

	    	try{
	    		fillLodopByFullJasperPrint();
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    	//myMask.hide();
	    }
	});
}

function fillLodopByFullJasperPrint_dprt(){
	var maindiv = document.getElementById("printresultdiv_dprt");
	
	var fulljpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("fulljpdiv"==odiv.id){
			fulljpdivobj = odiv;
			break;
		}
	}
	
	if(fulljpdivobj!=null){
		var divs = fulljpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示 
		mLODOP.PREVIEW();
	}
}

function fillLodopByNoImgJasperPrint_dprt(){
	
	var maindiv = document.getElementById("printresultdiv_dprt");
	
	var noimgjpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("noimgjpdiv"==odiv.id){
			noimgjpdivobj = odiv;
			break;
		}
	}
	
	if(noimgjpdivobj!=null){
		var divs = noimgjpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB_dprt'),document.getElementById('LODOP_EM_dprt'));  
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示
		mLODOP.PREVIEW();
	}
}

var dprtform = null;
function do_print_full(pPrtKey, pParam,pCtxPath ){
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	
	var vwidth = 200;
	var vheight = 100;
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});

	if(dprtform==null){
	    var dprtform = Ext.create('Ext.Panel', {
	        floating: true,
	        bottomed: true,
	        width: 1,
	        height: 1,
	        html: hiddenhtml.join()
	    });
	}
	dprtform.show();
    
	Ext.Ajax.request({
	    url: vCtxPath+'/common/exportHtmlAction!exportHtml.action',
	    params:pParam,
	    success: function(response){
	    	var vobj = document.getElementById('printresultdiv_dprt');
	    	vobj.innerHTML = response.responseText;
	    	  	    	
	    	try{
	    		fillLodopByFullJasperPrint_dprt();
	    		if (mLODOP) {
	    			var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
	    			mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
	    			mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
	    			mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	    			//dprtform.hide();
	    		}
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    }
	});
}

function do_print_noimg(pPrtKey, pParam,pCtxPath ){
	
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	
	var vwidth = 200;
	var vheight = 100;
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});

	if(dprtform==null){
	    var dprtform = Ext.create('Ext.Panel', {
	        floating: true,
	        bottomed: true,
	        width: 1,
	        height: 1,
	        html: hiddenhtml.join()
	    });
	}
	dprtform.show();
    
	Ext.Ajax.request({
	    url: vCtxPath+'/common/exportHtmlAction!exportHtml.action',
	    params:pParam,
	    success: function(response){
	    	var vobj = document.getElementById('printresultdiv_dprt');
	    	vobj.innerHTML = response.responseText;
	    	  	    	
	    	try{
	    		fillLodopByNoImgJasperPrint_dprt();
	    		if (mLODOP) {
	    			var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
	    			mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
	    			mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
	    			mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	    			//dprtform.hide();
	    		}
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    }
	});
}
