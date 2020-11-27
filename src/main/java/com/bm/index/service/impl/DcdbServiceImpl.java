package com.bm.index.service.impl;

import com.bm.index.dao.source1.DcdbAkxDao;
import com.bm.index.dao.source2.DcdbTyyhDao;
import com.bm.index.model.DcdbAkx;
import com.bm.index.model.Menu;
import com.bm.index.service.DcdbService;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.index.service.impl
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2019/1/4
 */
@Service
public class DcdbServiceImpl implements DcdbService
{
	@Autowired
	private DcdbAkxDao dcdbAkxDao;

	@Autowired
	private DcdbTyyhDao tyyhDao;
	
	@Override
	public DcdbAkx selectByExample(String id)
	{
		DcdbAkx dcdbAkx = new DcdbAkx();
		if(StringUtil.isEmpty(id)){
			return dcdbAkx;
		}
		dcdbAkx.setBh(id);
		List<DcdbAkx> list = dcdbAkxDao.selectByExample(dcdbAkx);
		if(CollectionUtils.isEmpty(list)){
			return dcdbAkx;
		}
		return list.get(0);
	}
	
	@Override
	public List<Menu> testTree(Map<String,String> info){
		return dcdbAkxDao.testTree(info);
	};
}
