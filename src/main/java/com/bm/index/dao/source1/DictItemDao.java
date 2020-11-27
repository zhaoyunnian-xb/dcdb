package com.bm.index.dao.source1;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.sdgj.dao.source1
 * @Description: (字典项公共dao层)
 * @date 2018/9/7
 */
public interface DictItemDao
{
	/**
	 * 根据组id获取一组字典（根据序号排序后）
	 * @param groupId  组id
	 * @return List<Map<String,String>>
	 * */
	List<Map<String,String>> getGroup(@Param("groupId") String groupId);
	
	/**
	 * 根据组id，字典id获取一条记录
	 * @param groupId 组id
	 * @param dictId 字典id
	 * @return Map<String,String>
	 * */
	Map<String,String> getItem(@Param("groupId") String groupId, @Param("dictId") String dictId);
}
