package com.bm.index.dao.source1;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.ipa.mapper
 * @Description: (用一句话描述该文件做什么)
 * @date 2018/8/28
 */
public interface FileDao
{
	/**
	 * 批量插入
	 * @param list list
	 * */
	void batcheInsertFile(List<Map<String,String>> list);
	
	/**
	 * 查询文件列表
	 * @param map  map（xsbh,id,nodeid）
	 * */
	List<Map<String,String>> qryIpaFileListById(Map<String,String> map);
	
	/**
	 * 删除文件列表
	 * @param map(nodeId,xsbh,id)
	 * */
	void deleteIpaFileInfo(Map<String, String> map);
	
	/**
	 *检察建议文书入库
	 * @param insertData
	 * @return map 查询卷宗节点所需要的参数
	 * */
	void insertWsscPath(Map<String, String> insertData);
	
	/**
	 *删除原来的文件
	 * @param insertData
	 * @return map 查询卷宗节点所需要的参数
	 * */
	void deleteWsscPath(Map<String, String> insertData);
	
	/**
	 *检察建议生成文书
	 * @param map （参数，nodeId,xsbh）
	 * @return map 查询卷宗节点所需要的参数
	 * */
	Map<String,Object> jcjyScws(Map<String, String> map);
	
	/**
	 *查询文书路径
	 * @param xsbh 线索编号
	 * @return nodeId 主节点
	 * */
	List<Map<String,String>> downloadWs(@Param("xsbh") String xsbh, @Param("nodeId")String nodeId);
	
	/**
	 * 根据用户编号查询已经完结的线索的文书列表
	 * @param map userId xsbh
	 * @return List<Map<String,String>>
	 * */
	List<Map<String,String>> qryWsByUserId(Map<String, String> map);
	
	/**
	 * 根据用户编号查询已经完结的线索的文书列表
	 * @param map  xsbh nodeId
	 * @return List<Map<String,String>>
	 * */
	List<Map<String,String>> qryDcdbWsInfo(Map<String, String> map);
	
	/**
	 * 根据前台返回的节点查询对应的文书
	 * @param map (xsbh 线索编号 type 是否全部查询1是0否 nodeId sql中拼装的字符串)
	 * @return List<Map<String,String>>
	 * */
	List<Map<String,String>> downloadWsList(Map<String, String> map);
	
	int checkWj(@Param("wjName") String wjName,@Param("nodeId")  String nodeId,@Param("xsbh")  String xsbh);
	
	List<Map<String,String>> qryFileListByIds(Map<String, String> map);
	
	void deleteXyrgzsInfo(String wsbh);
	
	void saveXyrgzsInfo(List<Map<String,String>> infoList);
	
	List<String> selAlreadyNode(@Param("xsbh") String xsbh);
	List<Map<String,String>> qryFileListByMap(Map<String, String> map);
}
