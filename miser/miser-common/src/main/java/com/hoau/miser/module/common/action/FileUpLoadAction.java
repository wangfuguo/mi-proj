package com.hoau.miser.module.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.common.shared.util.TestEntity;
/**
 * excel上传解析示例
 * ClassName: ExcelImportAction 
 * @author 王茂
 * @date 2015年12月24日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class FileUpLoadAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private File serviceXls;
	private String serviceXlsFileName;
	private String serviceXlsContentType;
	private String outFileName;
	private String fileName;
	private boolean flag=true;
	private static final Logger log = LoggerFactory.getLogger(FileUpLoadAction.class);
	
	public String isExistFile(){
		String downloadPath = ServletActionContext.getServletContext().getRealPath("/"+fileName);
		File file = new File(downloadPath);
		if (!file.exists() && !file.isFile()){
			flag=false;
		}
		return returnSuccess();
	}
	/**
	 * 
	 * @Description: 上传文件返回文件名
	 * @param @return
	 * @param @throws IOException   
	 * @return String   返回文件名  outFileName
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String upLoadFile() throws IOException{
		String prefix = serviceXlsFileName.substring(serviceXlsFileName.lastIndexOf(".") + 1);
		InputStream is = new FileInputStream(serviceXls);
		// 设置上传文件目录
		String uploadPath = ServletActionContext.getServletContext()
				.getRealPath("/upLoad");
		File file = new File(uploadPath);
		if (!file.isDirectory()) {
			file.mkdir();
		}
		// 设置目标文件
		File toFile = new File(uploadPath, ExcelUtil.getNewFileName(prefix));
		// 创建一个输出流
		OutputStream os = new FileOutputStream(toFile);
		// 设置缓存
		byte[] buffer = new byte[1024];
		int length = 0;
		// 读取myFile文件输出到toFile文件中
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		// 关闭输入流
		is.close();
		// 关闭输出流
		os.close();
		serviceXls=null;
		outFileName="upLoad/"+toFile.getName();
		return returnSuccess();
	}
	/**
	 * 
	 * @Description: 导入excel并解析后导出
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public String impexcel() {
		List<TestEntity> list = null;
		try {
			list = ExcelUtil.readToList(serviceXls, serviceXlsFileName, 15, 2,
					TestEntity.class);
			log.info("-------------------解析excel集合条数" + list.size());
			list.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 基于myFile创建一个文件输入流
		serviceXls = null;
		return returnSuccess();
	}

	public File getServiceXls() {
		return serviceXls;
	}

	public void setServiceXls(File serviceXls) {
		this.serviceXls = serviceXls;
	}

	public String getServiceXlsFileName() {
		return serviceXlsFileName;
	}

	public void setServiceXlsFileName(String serviceXlsFileName) {
		this.serviceXlsFileName = serviceXlsFileName;
	}

	public String getServiceXlsContentType() {
		return serviceXlsContentType;
	}

	public void setServiceXlsContentType(String serviceXlsContentType) {
		this.serviceXlsContentType = serviceXlsContentType;
	}
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
