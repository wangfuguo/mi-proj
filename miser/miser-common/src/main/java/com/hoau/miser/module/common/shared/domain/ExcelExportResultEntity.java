package com.hoau.miser.module.common.shared.domain;

/**
 * @ClassName:
 * @Description: excel导出文件返回数据类
 * @author: Chenyl yulin.chen@hoau.net
 * @date: 2016/4/8 15:18
 */
public class ExcelExportResultEntity {

    /**
     * 文件存放路径
     */
    private String filePath;

    /**
     * 导出记录条数
     */
    private long recordCount;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
}
