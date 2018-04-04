package com.hoau.miser.module.common.action;

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;

import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class FileDownLoadAction extends ActionSupport implements  
ServletContextAware{
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	private String fileName;
	private String mimeType;
	private InputStream inStream;
	private static final Logger log = LoggerFactory.getLogger(FileDownLoadAction.class);
	/**
	 * 
	 * @Description: 获取下载文件流将其扔给struts
	 * @param @return   
	 * @return InputStream 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月24日
	 */
	public InputStream getInStream(){
		mimeType=context.getMimeType(fileName);
		log.info("文件下载-------------"+fileName);
		if(StringUtils.isNotEmpty(fileName)){
			inStream=context.getResourceAsStream(fileName);
		}else{
		log.info("文件名为空------------");
		}
		fileName=fileName.substring(fileName.lastIndexOf("/")+1);
		return inStream;
	}
	@Override
	public String execute(){
		String downloadPath = ServletActionContext.getServletContext()
				.getRealPath("/"+fileName);
		//判断不是zip文件则进入
		File file = new File(downloadPath);
		if (file.exists() && file.isFile()){
			if(0>downloadPath.lastIndexOf(".zip")&&false){
			//设置文件大于20M则压缩
			if(file.length()>=20971520){
				String zipFileNames = ServletActionContext.getServletContext()
						.getRealPath("/zipFile");
				File zipFiles = new File(zipFileNames);
				if (!zipFiles.isDirectory()) {
					zipFiles.mkdir();
				}
				String zipFileName=ServletActionContext.getServletContext()
						.getRealPath("/zipFile/"+ExcelUtil.getNewFileName("zip"));
				boolean flag=ExcelUtil.ZipFile(downloadPath, zipFileName);
				log.info("文件大于20M---------------------");
				if(flag){
					fileName=zipFileName;
				}
			}
			}
			log.info(file.length()+"---------------------");
	    }else{
	    	return ERROR;
	    }
		log.info("文件下载-------------"+fileName);
		return SUCCESS;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	@Override  
    public void setServletContext(ServletContext context) {  
        this.context = context;  
    }
}
