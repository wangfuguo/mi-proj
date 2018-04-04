package com.hoau.miser.module.sys.base.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.cache.CacheManager;
import com.hoau.hbdp.framework.cache.ICache;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.sys.base.api.server.service.IDataDictionaryValueService;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryEntity;
import com.hoau.miser.module.sys.base.api.shared.domain.DataDictionaryValueEntity;
import com.hoau.miser.module.sys.base.api.shared.exception.DataDictionaryValueException;
import com.hoau.miser.module.sys.base.server.cache.DataDictionaryCache;
import com.hoau.miser.module.sys.base.server.cache.DataDictionaryValueCache;
import com.hoau.miser.module.sys.base.server.dao.DataDictionaryDao;
import com.hoau.miser.module.sys.base.server.dao.DataDictionaryValueDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.UUIDUtil;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * @author：李旭锋
 * @create：2015年6月8日 上午9:59:31
 * @description：
 */
@Service
public class DataDictionaryValueService implements IDataDictionaryValueService {

	@Resource
	private DataDictionaryValueDao dataDictionaryValueDao;
	@Resource
	private DataDictionaryDao dataDictionaryDao;


	@Override
	@Transactional
	public void addDictionaryValue(DataDictionaryValueEntity dictionEntity) {

		dictionEntity = addIDandTimeToObject(dictionEntity);
		//值编码不能为空
		if(StringUtil.isBlank(dictionEntity.getValueCode())){
			throw new DataDictionaryValueException(DataDictionaryValueException.DATADICTIONVALUE_VALUECODE_ISNULL);
		}
        //值名称不能为空
		if(StringUtil.isBlank(dictionEntity.getValueName())){
			throw new DataDictionaryValueException(DataDictionaryValueException.DATADICTIONVALUE_VALUENAME_ISNULL);
			
		}
			
		//值顺序不能为空
		if(StringUtil.isBlank(dictionEntity.getValueSeq()+"")){
			throw new DataDictionaryValueException(DataDictionaryValueException.DATADICTIONVALUE_VALUESEQ_ISNULL);
			
		}
		dataDictionaryValueDao.saveDictionaryValueEntity(dictionEntity);
		

	}



	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueByEntity(
			DataDictionaryValueEntity dataDictionaryValueEntity, int limit,
			int start) {
		dataDictionaryValueEntity.setActive(MiserConstants.YES);
		
		RowBounds rowBounds = new RowBounds(start, limit);
		List<DataDictionaryValueEntity> list = dataDictionaryValueDao
				.queryDictionaryValueEntityByExample(dataDictionaryValueEntity,
						rowBounds);
		
		return list;
	}

	@Override
	public Long queryDataDictionaryValueByEntityCount(
			DataDictionaryValueEntity dataDictionaryValueEntity) {
		return dataDictionaryValueDao
				.queryTotalbyExample(dataDictionaryValueEntity);
	}

	public DataDictionaryValueEntity addIDandTimeToObject(
			DataDictionaryValueEntity object) {
		Date dt = new Date();
		object.setId(UUIDUtil.getUUID());
		object.setActive(MiserConstants.YES);
		object.setCreateDate(dt);
		object.setVersionNo(UUIDUtil.getVersion());
		object.setCreateUser(MiserUserContext.getCurrentUser().getUserName());
		object.setModifyDate(dt);
		object.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
		return object;
	}

	@Override
	@Transactional
	public void deleteDataDictionaryValueList(
			List<DataDictionaryValueEntity> dataDictionaryValuelist) {

		for (DataDictionaryValueEntity dataDictionaryValue : dataDictionaryValuelist) {
			
			dataDictionaryValue.setActive(MiserConstants.NO);
			dataDictionaryValue.setVersionNo(UUIDUtil.getVersion());
			dataDictionaryValue.setModifyDate(new Date());
			dataDictionaryValue.setModifyUser(MiserUserContext.getCurrentUser().getUserName());
			dataDictionaryValueDao.deleteDictionaryValueEntity(dataDictionaryValue);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public long getLastChangeVersionNo() {
		ICache<String, DataDictionaryEntity> dicCache = CacheManager
				.getInstance().getCache(
						DataDictionaryCache.DATA_DICTIONARY_UUID);
		Map<String, DataDictionaryEntity> dicMap = dicCache.get();
		long versionNo=0l;
		if(null!=dicMap){
			DataDictionaryEntity entity=dicMap.get("version");
			versionNo=entity.getVersionNo();
		}
		return versionNo;
	}



	@Override
	public List<DataDictionaryValueEntity> queryParamByTermsCode(
			String termscode) {
		DataDictionaryValueEntity dataDictionaryValueEntity = new DataDictionaryValueEntity();
		dataDictionaryValueEntity.setTermsCode(termscode);
		dataDictionaryValueEntity.setActive(MiserConstants.YES);
		return dataDictionaryValueDao.queryDictionaryValueEntityByExample(dataDictionaryValueEntity);
	}



	@Override
	public DataDictionaryValueEntity queryByTermscodeAndValueCode(
			String termscode, String valuecode) {
		DataDictionaryValueEntity dataDictionaryValueEntity = new DataDictionaryValueEntity();
		dataDictionaryValueEntity.setValueCode(valuecode);
		dataDictionaryValueEntity.setTermsCode(termscode);
		dataDictionaryValueEntity.setActive(MiserConstants.YES);
		List<DataDictionaryValueEntity> list = dataDictionaryValueDao.queryDictionaryValueEntityByExample(dataDictionaryValueEntity);
		if(list.size()==0)
			return null;
		return list.get(0);
	}



	@Override
	public DataDictionaryValueEntity queryByName(String name) {
		DataDictionaryValueEntity dataDictionaryValueEntity = new DataDictionaryValueEntity();
		dataDictionaryValueEntity.setValueName(name);
		dataDictionaryValueEntity.setActive(MiserConstants.YES);
		List<DataDictionaryValueEntity> list = dataDictionaryValueDao.queryDictionaryValueEntityByExample(dataDictionaryValueEntity);
		if(list.size()==0)
			return null;
		return list.get(0);
	}



	@Override
	public String queryValueNameByTermsCodeAndValueCode(String termsCode,
			String valueCode) {
		String key = termsCode+"#"+valueCode;
		return getValueNameCache().get(key);
	}
	
	@SuppressWarnings("unchecked")
	private ICache<String, String> getValueNameCache(){
		ICache<String, String> cache = CacheManager.getInstance().getCache(DataDictionaryValueCache.UUID);
		return cache;
	}
	
	

}
