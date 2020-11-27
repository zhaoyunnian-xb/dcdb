package com.bm.index.controller;

import com.bm.index.model.DcdbAkx;
import com.bm.index.model.Menu;
import com.bm.index.service.DcdbService;
import com.bm.index.service.FileService;
import com.bm.index.util.BlWordUtil;
import com.bm.index.util.CommonUtils;
import com.bm.index.util.TreeBuilder;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.index.controller
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2019/1/3
 */
@Controller
@RequestMapping("dcdb")
public class DcdbController
{
	@Autowired
	private FileService fileService;
	//json实例
	private Gson mGson = new Gson();
	
	@Autowired
	private DcdbService dcdbService;
	@RequestMapping("/index.do")
	public String index(String id,String nodeid,String ajzt,String readOnly,Model model){
		DcdbAkx dcdbAkxe = dcdbService.selectByExample(id);
		model.addAttribute("dcdb",dcdbAkxe);
		model.addAttribute("id","DC"+ getYear()+String.valueOf(System.currentTimeMillis()).substring(8));
		model.addAttribute("first",1);
		if(StringUtils.isNotBlank(id)){
			model.addAttribute("id",id);
			model.addAttribute("first",0);
		}
		if(StringUtils.isBlank(readOnly)){
			readOnly = "0";
		}
		model.addAttribute("readOnly",readOnly);
		model.addAttribute("nodeId",nodeid);
		model.addAttribute("ajzt",ajzt);
		return "dcview";
	}
	public static String getYear() {
		try {
			return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		} catch (Exception e) {
			return "";
		}
	}

	@RequestMapping(value="/filesUpload.do")
	@ResponseBody
	public String filesUpload(String id,String nodeId, HttpServletRequest request){
		Map<String,Object> map = new HashedMap();
		List<MultipartFile> fileList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request))
		{
			//转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext())
			{
				//取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null)
				{
					map.put("fileName",file.getOriginalFilename());
					fileList.add(file);
				}
			}
		}
		
		//上传文件，并返回文件名和文件id
		List<Map<String,String>> fileInfoList = fileService.uploadFiles(fileList,nodeId,id,request);
		
		return mGson.toJson(fileInfoList);
	}
	
	@RequestMapping(value="/filesUploadHyjy.do")
	public void filesUploadHyjy(String id,String nodeId, HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		Map<String,Object> map = new HashedMap();
		List<MultipartFile> fileList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request))
		{
			//转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext())
			{
				//取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null)
				{
					map.put("fileName",file.getOriginalFilename());
					fileList.add(file);
				}
			}
		}
		DcdbAkx aa = new BlWordUtil().readWord(fileList.get(0).getInputStream(),fileList.get(0).getOriginalFilename());
		//上传文件，并返回文件名和文件id
		
		Map<String,String> map1 = new HashedMap();
		map1.put("xsbh",id);
		map1.put("nodeId",nodeId);
		fileService.deleteFileInfo(map1);
		
		List<Map<String,String>> fileInfoList = fileService.uploadFiles(fileList,nodeId,id,request);
		
		Map<String,Object> rst = new HashedMap();
		rst.put("file",fileInfoList);
		rst.put("info",aa);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write( mGson.toJson(rst));
	}
	/**
	 * 文书导出-归档页面
	 * @add
	 */
	@RequestMapping(value = "/downloadwsByPath.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void documentDownload(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String wspath = request.getParameter("wspath");
		// 文书获取
		String separator = File.separator;
		String fileName = wspath.substring(wspath.lastIndexOf(separator)+1);
		String realFile = session.getServletContext().getRealPath("/") + wspath;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
					.replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
					.replaceAll("%26", "\\&");
			
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			// 输入流
			InputStream fis = new BufferedInputStream(new FileInputStream(realFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 查询新建检查建议信息
	 * @param nodeId
	 * @param
	 */
	@RequestMapping(value="/qryJcjyList.do")
	@ResponseBody
	public void qryJcjyList(String id,String nodeId,HttpServletResponse response) throws IOException
	{
		Map<String,String> map = new HashedMap();
		Map<String,Object> map1 = new HashedMap();
		map.put("xsbh",id);
		map.put("nodeId",nodeId);
		try{
			//查询文件列表
			List<Map<String,String>> fileList = fileService.qryFileInfoList(map);
			map1.put("code",1);
			map1.put("fileList",fileList);
		} catch (Exception e){
			map1.put("code",0);
		}
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write( mGson.toJson(map1));
	}
	
	
	/**
	 * 文件下载
	 * @param ids 文件id数组
	 * @param id 部门受案号（JCsJY_PROJECT_FILE xsbh字段）
	 * */
	@RequestMapping(value="/filesDowload.do")
	public void filesDowload(String ids,String id,String nodeId,HttpServletResponse response){
		//处理数据
		Map<String,String> map = new HashedMap();
		map.put("xsbh", id);
		map.put("nodeId", nodeId);
		map.put("id", CommonUtils.stringToSqlInByRegex(ids,"-"));
		
		//查询上传文件
		List<Map<String,String>> fileList = fileService.qryFileInfoList(map);
		
		//下载附件
		fileService.downloadFiles(response,fileList);
	}
	
	/**
	 * 文件删除
	 * @param ids 文件id数组
	 * @param id 部门受案号（JCJY_PROJECT_FILE xsbh字段）
	 * */
	@RequestMapping(value="/filesDelete.do")
	@ResponseBody
	public String filesDelete(String ids,String id,String nodeId){
		//处理数据
		Map<String,String> map = new HashedMap();
		map.put("xsbh", id);
		map.put("id", CommonUtils.stringToSqlInByRegex(ids,"-"));
		map.put("nodeId", nodeId);
		fileService.deleteFiles(map);
		
		map.put("code","1");
		map.put("msg","删除成功");
		return mGson.toJson(map);
	}
	
	/**
	 * 点击节点查询对应文书
	 * @param id 编号
	 * @return 视图
	 */
	@RequestMapping(value="/qryDcdbInfo.do")
	@ResponseBody
	public void qryDcdbInfo(String id,String nodeId,HttpServletResponse response) throws IOException
	{
		Map<String,Object> map = new HashedMap();
		List<Map<String,String>> jcjyWsList = fileService.qryDcdbWsInfo(id,nodeId);
		map.put("code","1");
		map.put("msg",jcjyWsList);
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write( mGson.toJson(map));
	}
	
	/**
	 * 点击节点查询对应文书
	 * @param id 线索编号
	 * @param nodeId 主节点id
	 * @param uuid 拼装的uuid
	 * @return 视图
	 */
	@RequestMapping(value="/downLoadWs.do")
	public void downLoadWs(HttpServletRequest request,
			HttpServletResponse response,
			String id,
			String nodeId,
			String uuid) throws IOException
	{
		try{
			Map<String,String> map = new HashedMap();
			map.put("xsbh",id);
			map.put("nodeId",nodeId);
			map.put("uuid",uuid);
			
			//查询文书数据
			List<Map<String,String>> wsList= fileService.downloadWsList(map);
			if(CollectionUtils.isEmpty(wsList)){
				CommonUtils.fixFailedMsgInResp(response,"未查询到该文件");
				return;
			}
			//下载文书
			fileService.downloadWs(request,response,wsList,id);
		}catch (Exception e){
			e.printStackTrace();
			CommonUtils.fixFailedMsgInResp(response,"未查询到该文件");
		}
		
	}
	/**
	 * 点击节点查询对应文书
	 * @return 视图
	 */
	@RequestMapping(value="/testTree.do")
	@ResponseBody
	public void testTree(String isQryAll,String isRemove,String removeIds,HttpServletResponse response) throws IOException
	{
		Map<String,String> info = new HashedMap();
		if("false".equals(isQryAll)){
			info.put("isQryAll","0");
		}else{
			info.put("isQryAll","1");
		}
		if("true".equals(isRemove) && StringUtils.isNotEmpty(removeIds)){
			info.put("isRemove","1");
			String ids = "";
			if(!"@@".equals(removeIds)){
				ids = getSqlInByList(removeIds.split("@"));
			}
			info.put("removeIds",ids);
		}else{
			info.put("isRemove","0");
		}
		List<Menu> menus = dcdbService.testTree(info);
		// 拼装树形json字符串
		List<Menu> list = new TreeBuilder(menus).buildTree();
		Map<String,Object> map = new HashedMap();
		map.put("code","0");
		map.put("data",list);
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write( mGson.toJson(map));
	}
	
	public String getSqlInByList(String[] list){
		if(list.length == 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(String it : list){
			sb.append("'").append(it).append("',");
		}
		String result = sb.toString();
		return result.substring(0,result.length()-1);
	}
	
	/**
	 * 点击节点查询对应文书
	 * @return 视图
	 */
	@RequestMapping(value="/echoData.do")
	@ResponseBody
	public void echoData(String echoIds,HttpServletResponse response) throws IOException
	{
		Map<String,String> info = new HashedMap();
		List<Menu> menus = new ArrayList<>();
		if(StringUtils.isNotEmpty(echoIds)){
			String ids = "";
			//@分割的为年度任务回显，，分割的为流程审批部门回显
			if(echoIds.contains("@")){
				ids = getSqlInByList(echoIds.split("@"));
			}else if(echoIds.contains(",")){
				ids = getSqlInByList(echoIds.split(","));
			}
			info.put("ids",ids);
			info.put("isQryAll","1");
			menus = dcdbService.testTree(info);
			for(Menu item : menus){
				Map<String,String> map1 = new HashedMap();
				map1.put("id",item.getPid());
				List<Menu> menus1 = dcdbService.testTree(map1);
				if(CollectionUtils.isNotEmpty(menus1)){
					item.setParent(menus1.get(0));
				}
			}
		}
		
		Map<String,Object> map = new HashedMap();
		map.put("code","0");
		map.put("data",menus);
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write( mGson.toJson(map));
	}
}
