package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.DcdbDjlbDbDao;
import com.bm.index.dao.source1.DcdbDjlbYbDao;
import com.bm.index.model.DcdbDjlbDb;
import com.bm.index.model.DcdbDjlbDbParam;
import com.bm.index.model.DcdbDjlbYbParam;
import com.bm.index.model.UserEntity;
import com.bm.index.service.DcdbDbService;
import com.bm.index.service.DcdbYbService;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.sdgj.controller
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2018/12/25
 */
@Controller
@RequestMapping("/reback")
public class DcdbRebackController
{
	@Autowired
	private DcdbDjlbDbDao dbdao;

	@Autowired
	private DcdbDjlbYbDao ybdao;




	//json实例
	private Gson mGson = new Gson();
	/**
	 * 节点退回
	 * */
	@RequestMapping("/int")
	public String init(){
		return "dcdblc/dcdbReback";
	}

	/**
	 * 节点退回
	 * */
	@RequestMapping("/rebNode")
	@ResponseBody
	public String rebackNodeId(DcdbDjlbDb jcyjDb){
		Map<String,String> map = new HashedMap();
		map.put("code","0" );
		int i = 0;
		if(StringUtil.isEmpty(jcyjDb.getId()) || StringUtil.isEmpty(jcyjDb.getNodeId())
				|| StringUtil.isEmpty(jcyjDb.getNodeName()) || StringUtil.isEmpty(jcyjDb.getUserid())
				||  StringUtil.isEmpty(jcyjDb.getUsername())){
			map.put("code","1" );
			map.put("msg","退回失败，参数为空" );
			return mGson.toJson(map);
		}else{
		  i = 	dbdao.updateByPrimaryKey(jcyjDb);
		  if( i == 1){
				map.put("msg","退回成功" );

			}else{
				map.put("code","1" );
				map.put("msg","退回失败，查询出1条或者多条记录" );
			}
			return mGson.toJson(map);
		}

	}

	/**
	 * 查询待办节点
	 * */
	@RequestMapping("/qryJcjyDb")
	@PageHelperAnn
	@ResponseBody
	public JSONObject qryJcjyDb(DcdbDjlbDbParam dcdb){
		JSONObject jo = new JSONObject();
		List<DcdbDjlbDbParam> info = dbdao.queryTableReback(dcdb);
		jo.put("data",info);
		return jo;
	}

	/**
	 * 已办列表查询方法
	 * @param response
	 * @return
	 */
	@RequestMapping("/qryJcjyYb.do")
	@ResponseBody
	@PageHelperAnn
	public JSONObject queryTable( DcdbDjlbYbParam ybParam){
		JSONObject jsonobject = new JSONObject();
		List<DcdbDjlbYbParam> tableList = ybdao.queryTableById(ybParam);
		jsonobject.put("data", tableList);
		return jsonobject;
	}

	/**
	 * 查询承办部门
	 * @return
	 */
	@RequestMapping("/queryCbBm.do")
	@ResponseBody
	public void queryCbBm(HttpServletResponse response, HttpSession se) {
		Map<String, Object> mapRes = new HashMap<>();
		try {
			List<Map<String,String>> bmList = dbdao.queryCbBm();
			mapRes.put("data", bmList);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(mGson.toJson(mapRes));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	/**
//	 * 查询待办节点
//	 * */
//	@RequestMapping("/qryTableInfo")
//	@ResponseBody
//	public String qryTableInfo(String tablName){
//		String[] tabs = {"JCJY_XSTRANSFER","JCJY_CH","JCJY_JCGGZH","JCJY_LAJDDJ","JCJY_SCZJBG","JCJY_JCJYS_LZQFD","JCJY_JAJDDJ","JCJY_XGPGB","JCJY_JCJYS_1","JCJY_JCJYS_2","JCJY_JCJYS_3","JCJY_JCJYS_4","JCJY_JCJYS_5","JCJY_JCJYS_6","JCJY_JCJYS_7","JCJY_JCJYS_8","JCJY_JSHF"};
//		Map<String,String> map = new HashedMap();
//
//		StringBuffer sb = new StringBuffer();
//		Map<String,Object> resultMap = new HashedMap();
//		for(String it : tabs){
//			map.put("result",it);
//			List<Map<String,String>> list = jcAdviceService.qryTableInfo(map);
//			resultMap.put(it,list);
//		}
//
//		return mGson.toJson(resultMap);
//	}

//	/**
//	 * 查询待办节点
//	 * */
//	@RequestMapping("/qryTableInfo1")
//	@PageHelperAnn
//	@ResponseBody
//	public JSONObject qryTableInfo1(String xsbh,String tabName){
//		JSONObject jo = new JSONObject();
//		Map<String,String> nMap = new HashedMap();
//		nMap.put("xsbh",xsbh);
//		nMap.put("tabName",tabName);
//		List<Map<String,String>> info = jcAdviceService.qryTableInfo1(nMap);
//		jo.put("data",info);
//		return jo;
//	}

//	/**
//	 * 查询待办节点
//	 * */
//	@RequestMapping("/upateTableInfo")
//	@ResponseBody
//	public String upateTableInfo(String tabName,String setCoulmn,String setValue,String whereKey,String whereValue){
//		JSONObject jo = new JSONObject();
//		Map<String,String> nMap = new HashedMap();
//		nMap.put("tabName",tabName);
//		nMap.put("setCoulmn",setCoulmn);
//		nMap.put("setValue",setValue);
//		nMap.put("whereKey",whereKey);
//		nMap.put("whereValue",whereValue);
//		jcAdviceService.upateTableInfo(nMap);
//		return null;
//	}
//
//	/**
//	 * 查询待办节点
//	 * */
//	@RequestMapping("/gotoSys")
//	@ResponseBody
//	public String gotoSys(UserEntity user, HttpSession session)
//	{
//		// 用户查询
//		List<UserEntity> users = commonDao.qryUserLgInf(user);
//		int count = 0;
//		// session存放
//		if (users != null && users.size() > 0)
//		{
//			UserEntity loginUser = users.get(0);
//			count = users.size();
//			List<UserEntity> userInfo = commonDao.getUserSessionInfo(loginUser);
//			StringBuilder bmbm = new StringBuilder();
//			StringBuilder bmmc = new StringBuilder();
//			StringBuilder jsmc = new StringBuilder();
//			for (UserEntity userEntity : userInfo)
//			{
//				bmbm.append(userEntity.getBmbm() + " ");
//				bmmc.append(userEntity.getBmmc() + " ");
//				jsmc.append(userEntity.getJsmc() + " ");
//			}
//			loginUser.setBmbm(bmbm.toString());
//			loginUser.setBmmc(bmmc.toString());
//			loginUser.setJsmc(jsmc.toString());
//			session.setAttribute("User", loginUser);
//			if (!StringUtils.isBlank(loginUser.getSfzh()))
//			{
//				DataCache.set(loginUser.getSfzh(), loginUser);
//			}
//		}
//		return mGson.toJson("SUCCESS");
//	}
}
