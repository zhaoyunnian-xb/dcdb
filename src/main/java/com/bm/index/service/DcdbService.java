package com.bm.index.service;

import com.bm.index.model.DcdbAkx;
import com.bm.index.model.Menu;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.index.service
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2019/1/4
 */
public interface DcdbService
{
	DcdbAkx selectByExample(String id);
	
	List<Menu> testTree(Map<String,String> info);
}
