package com.bm.index.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.sdgj.service
 * @Description: (文件上传下载)
 * @date 2018/9/6
 */
public interface FileService
{
	boolean deleteIpaFiles(Map<String, String> map);
	
	boolean deleteFileInfo(Map<String, String> map);
	
	boolean deleteFiles(Map<String, String> map);
	
	List<Map<String,String>> qryIpaFileList(String xsbh, String nodeId, String id);
	
	boolean batcheInsertFile(List<Map<String, String>> list);
	
	List<Map<String,String>> uploadFiles(List<MultipartFile> files, String nodeId, String xsbh,
			HttpServletRequest request);
	
	List<Map<String,String>> uploadFiles(MultipartFile[] files, String nodeId, String xsbh, HttpServletRequest request);
	
	boolean uploadFiles(HttpServletRequest request, MultipartFile[] files, String nodeId, String xsbh);
	
	boolean uploadFile(HttpServletRequest request, MultipartFile file, String nodeId, String xsbh);
	
	boolean downloadFiles(HttpServletResponse responses, String id);
	
	boolean downloadFiles(HttpServletResponse responses, String xsbh, String nodeId);
	
	boolean downloadFiles(HttpServletResponse responses, List<Map<String, String>> fileList);
		
	//生成单个文书
	boolean jcjyScws(HttpServletRequest request, Map<String, String> map);
	
	boolean downloadWs(HttpServletRequest request, HttpServletResponse response, Map<String, String> map);
	
	boolean downloadWs(HttpServletRequest request, HttpServletResponse response, List<Map<String, String>> wsList,
			String xsbh);
		
	List<Map<String,String>> qryWsByUserId(Map<String, String> map);
	
	List<Map<String,String>> qryDcdbWsInfo(String id, String nodeId);
	
	List<Map<String,String>> downloadWsList(Map<String, String> map);
	
	//在目录下可以生成多个文书，只是一个个的 生成，不会覆盖之前的
	boolean createDocs(HttpServletRequest request, Map<String, String> map, Map<String, Object> wsnr, String tempName) ;

	boolean uploadScZjBgFile(HttpServletRequest request, MultipartFile files, String nodeId, String xsbh,
			String fileName);

	void saveSczjBg(Map<String, String> insertData);

	boolean transToHtml(String fileName, String rootPath);

	int checkWj(String wjName, String nodeId, String xsbh, HttpServletRequest request);
	
	void deleteXyrgzsInfo(String wsbh);
	
	void saveXyrgzsInfo(List<Map<String, String>> infoList);
	
    List<String> selAlreadyNode(String xsbh);
	
	List<Map<String,String>> qryFileInfoList(Map<String, String> map);
}
