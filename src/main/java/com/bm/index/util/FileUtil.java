package com.bm.index.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.sdgj.until
 * @Description: (文件上传下载,目录创建删除工具类)
 * @date 2018/8/28
 */
public class FileUtil
{
	//上传文件路径
	private static String UPLOAD_FILE = "dcdb";
	private static String AG_UPLOAD_FILE = "ag";
	//打印日志
	private static Log logger = LogFactory.getLog(FileUtil.class);

	//上传根路径
	private static String ROOT =  ConfigProperties.getProperties("dcdb_upload_path");
//	private static String ROOT = "D:/bm/uploadFiles/";
//	private static String ROOT = "/opt/bm/uploadFiles/";

	//获取properties属性值
	/*static
	{
		try
		{
			if( Os.OS_NAME.contains("window")){
				ROOT = ConfigProperties.getProperties("JCJY_UPLOAD_FILE").trim();
			}else{
				ROOT = ConfigProperties.getProperties("JCJY_UPLOAD_FILE_Linux").trim();
			}
			//判断路径是否存在，不存在创建
			makeDirectory(ROOT);
			if(logger.isInfoEnabled())
			{
				logger.info("complete loading config.properties");
			}
		}
		catch (Exception e)
		{
			logger.info("Failed loading config.properties",e);
		}
	}*/
	/**
	 * 单个文件上传
	 * @param file 文件
	 * @param xsbh  线索编号
	 * @param fileName 文件名(不包含后缀名)
	 * @return boolean
	 * */
	public static boolean uploadFile(MultipartFile file,String xsbh,String fileName)
			throws Exception
	{
		
		logger.debug("获取上传文件根路径:" + ROOT);
		
		//文件校验
		if(file == null || StringUtils.isEmpty(xsbh)){
			logger.error("上传文件失败,file或xsbh为空!");
			return false;
		}
		
		//拼接上传文件夹路径（C\://bm//uploadFiles//jcjy/XS37XXXX001/）
		String uploadPath = ROOT + UPLOAD_FILE + File.separator + xsbh;
		//创建目录
		logger.error("拼接上传文件夹路径"+uploadPath);
		//获取文件实际名称和路径名称
		String currFileName = file.getOriginalFilename();
		String name[] = currFileName.split("\\.");
		logger.error("获取文件实际名称和路径名称"+currFileName + ":" + name[0]);
		//创建文件夹
		if (makeDirectory(uploadPath)){
			//根目录+文件相对目录+项目id (文件夹路径) uuid+后缀(文件名称)
			logger.error("开始传送文件"+uploadPath + File.separator + fileName + "." +name[1]);
			file.transferTo(new File(uploadPath + File.separator + fileName + "." +name[1]));
			logger.debug("文件上传成功,上传路径:" + uploadPath + File.separator + fileName + "." +name[1]);
		}
		return true;
	}
	
	
	/**
	 * 单个文件下载
	 * @param response 相应
	 * @param map  内容(key xsbh 线索编号,fileName 实际保存的文件名,targetFileName 要显示的文件名,prefix 后缀名)
	 * @return boolean
	 * */
	public static boolean downloadFile(HttpServletResponse response,Map<String,String> map)
			throws Exception
	{
		//预校验
		if (MapUtils.isEmpty(map)){
			logger.error("文件下载失败!: map is null");
			CommonUtils.fixFailedMsgInResp(response,"未找到相关文件！");
			return false;
		}
		
		//项目id
		String xsbh = map.get("XSBH");
		//实际保存的文件名
		String fileName = map.get("FILENAME");
		//要显示的文件名
		String targetFileName = map.get("TARGETFILENAME");
		//后缀名
		String prefix = targetFileName.split("\\.")[1];;
		if(StringUtils.isEmpty(xsbh)
				|| StringUtils.isEmpty(fileName)
				|| StringUtils.isEmpty(targetFileName)
				|| StringUtils.isEmpty(prefix)){
			logger.error("文件下载失败!:map is null or value is null");
			CommonUtils.fixFailedMsgInResp(response,"文件下载失败，请联系管理员！");
			return false;
		}
		
		//获取项目根目录
		logger.debug("获取上传文件根路径:" + ROOT);
		
		//拼接上传文件夹路径
		String uploadPath = ROOT + UPLOAD_FILE + File.separator + xsbh;
		try
		{
			//判断是否是目录
			if(new File(uploadPath).isDirectory()){
				String filePath = uploadPath + File.separator + fileName + "." + prefix;
				//判断是否是文件
				if(new File(filePath).isFile()){
					//写入到响应流中
					setResponseInfo(response,filePath,targetFileName);
					logger.debug("文件下载成功,文件名为:" + targetFileName);
					return true;
				}
				logger.error("文件下载失败!找不到该文件:" + filePath);
				CommonUtils.fixFailedMsgInResp(response,"文件下载失败!找不到该文件！");
				return false;
			}
			logger.error("文件下载失败!找不到该目录:" + uploadPath);
			return false;
		} catch (Exception e){
			logger.error("文件下载异常!:" , e);
			return false;
		}
	}
	
	
	/**
	 * 多个文件下载(多个文件会打成压缩包下载下来)
	 * @param response 相应
	 * @param pathList  内容(key xsbh 线索编号,fileName 实际保存的文件名,targetFileName 要显示的文件名,prefix 后缀名)
	 * @param zipName 压缩包的名称(不包含后缀名)
	 * @return boolean
	 * */
	public static boolean downloadFiles(HttpServletResponse response,List<Map<String,String>> pathList,String zipName)
			throws Exception
	{
		//预校验
		if (CollectionUtils.isEmpty(pathList) || StringUtils.isEmpty(zipName)){
			logger.error("文件批量下载失败!: 路径为空或者压缩包名为空");
			CommonUtils.fixFailedMsgInResp(response,"文件批量下载失败!: 路径为空或者压缩包名为空！");
			return false;
		}
		try {
			//获取项目根目录
			logger.debug("获取文件下载根路径:" + ROOT);
			//将所有需要下载文件放到ZipOutputStream中
			String tmpFileName = zipName;
			//拼接下载文件路径
			String uploadPath = ROOT + UPLOAD_FILE ;
			String strZipPath = uploadPath + File.separator + tmpFileName + ".zip";
			//存在文件则先删除
			deleteFile(strZipPath);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			for(Map<String,String> item : pathList){
				
				//项目id
				String xsbh = item.get("XSBH");
				//实际保存的文件名
				String fileName = item.get("FILENAME");
				//要显示的文件名
				String targetFileName = item.get("TARGETFILENAME");
				//后缀名
				String prefix = targetFileName.split("\\.")[1];
				//下载路径
				String path = ROOT + UPLOAD_FILE + File.separator + xsbh;
				
				// 校验路径
				File filepath = new File(path);
				if(!filepath.exists()){
					logger.error("文件批量下载异常!:下载路径不存在" + path);
					return false;
				}
				// 校验文件是否存在
				File curfile = new File(path + File.separator + fileName + "." + prefix);
				if(!curfile.exists()){
					logger.error("文件批量下载异常!:文件不存在" + path + File.separator + fileName + "." + prefix);
					continue;
				}
				
				//把流分批写到压缩流中
				out.putNextEntry(new ZipEntry(targetFileName));
				FileInputStream in = new FileInputStream(curfile);
				
				int c;
				byte[] by = new byte[2048];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			}
			out.close();
			//写入到相应流中
			setResponseInfo(response,strZipPath,tmpFileName + ".zip");
			return true;
		} catch (Exception e){
			logger.error("文件批量下载异常!:" , e);
			return false;
		}
	}
	
	/**
	 * 多个文书下载(多个文件会打成压缩包下载下来)
	 * @param response 相应
	 * @param pathList  内容(key PATH,FILENAME)
	 * @param zipName 压缩包的名称(不包含后缀名)
	 * @return boolean
	 * */
	public static boolean downloadWsFiles(HttpServletRequest request,HttpServletResponse response,String zipName,List<Map<String,String>> pathList)
			throws Exception
	{
		String root = WebUtils.getRealPath(request.getSession().getServletContext(),"");
		//预校验
		if (CollectionUtils.isEmpty(pathList) && StringUtils.isEmpty(zipName)){
			logger.error("文件批量下载失败!: 路径为空或者压缩包名为空");
			CommonUtils.fixFailedMsgInResp(response,"文件批量下载失败!: 路径为空或者压缩包名为空！");
			return false;
		}
		try {
			if(pathList.size() == 1){
				Map<String,String> map = pathList.get(0);
				String path = root + map.get("WJLJ");
				//获取项目根目录
				logger.debug("获取文件下载根路径:" + path);
				String fileName = map.get("WJMC");
				//获取项目根目录
				logger.debug("获取文件下载名称:" + fileName);
				String filePath = path + fileName;
				setResponseInfo(response,filePath,fileName);
				return true;
			}else{
				//将所有需要下载文件放到ZipOutputStream中
				String tmpFileName = zipName;
				String strZipPath = root + pathList.get(0).get("PATH") + tmpFileName + ".zip";
				
				//存在文件则先删除
				deleteFile(strZipPath);
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
				
				for(Map<String,String> item : pathList){
					String path = root + item.get("PATH");
					// 校验路径
					File filepath = new File(path);
					if(!filepath.exists()){
						logger.error("文件批量下载异常!:下载路径不存在" + path);
						return false;
					}
					// 校验文件是否存在
					File curfile = new File(path + item.get("FILENAME"));
					if(!curfile.exists()){
						logger.error("文件批量下载异常!:文件不存在" + path + File.separator + item.get("FILENAME"));
						continue;
					}
					
					//把流分批写到压缩流中
					out.putNextEntry(new ZipEntry(item.get("FILENAME")));
					FileInputStream in = new FileInputStream(curfile);
					
					int c;
					byte[] by = new byte[2048];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
				}
				out.close();
				//写入到相应流中
				setResponseInfo(response,strZipPath,tmpFileName + ".zip");
				return true;
			}
		} catch (Exception e){
			CommonUtils.fixFailedMsgInResp(response,"下载文件异常");
			logger.error("文件批量下载异常!:" , e);
			return false;
		}
	}
	
	/**
	 * 删除文件
	 * @param path 文件路径
	 * */
	public static boolean deleteFile(String path){
		File file = new File(path);
		if(file.exists()){
			return file.delete();
		}
		return true;
	}
	
	/**
	 * 设置响应流状态,文件名称,响应头等
	 * @param response 响应
	 * @param targetFileName 文件名称
	 * @param filePath 文件路径
	 * */
	public static void setResponseInfo(HttpServletResponse response,String filePath,String targetFileName) throws IOException
	{
		//设置格式,防止乱码
		targetFileName = URLEncoder.encode(targetFileName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
				.replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
				.replaceAll("%26", "\\&");
		
		//设置相应头以及类型
		response.setHeader("content-disposition", "attachment;filename=" + targetFileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		// 放到缓冲流里面
		BufferedInputStream bins = new BufferedInputStream(new FileInputStream(filePath));
		// 读取目标文件，通过response将目标文件写到客户端
		BufferedOutputStream bouts = new BufferedOutputStream(response.getOutputStream());
		// 写文件
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		// 开始向网络传输文件流
		while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
			bouts.write(buffer, 0, bytesRead);
		}
		bouts.flush();
		bins.close();
		bouts.close();
	}
	
	/**
	 * 创建目录
	 * @param path 文件夹路径
	 * */
	public synchronized static boolean makeDirectory(String path){
		try{
			File file = new File(path);
			logger.error("创建目录"+path);
			if(!file.exists()){
				logger.error("不存在目录，创建"+path);
				return file.mkdirs();
			}
			logger.error("存在目录，目录"+path);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			logger.error("创建目录失败"+path,e);
			return false;
		}
	}
	
	/**
	 * 获取UUID
	 * */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	public static String getRoot(){
		try
		{
			if (StringUtils.isEmpty(ROOT))
			{
				return PropertiesLoaderUtils.loadProperties(new ClassPathResource("config.properties")).getProperty("prepare_file_path");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return ROOT;
	}
}
