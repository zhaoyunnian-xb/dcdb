package com.bm.index.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version V1.0
 * @Package com.bm.sdgj.util
 * @Description: (公共类)
 * @date 2018/8/16
 */
@Component
public class CommonUtils
{
	public static Object getEntityByMap(Map<String,String> map,Class clazz){
		try
		{
			Object obj = clazz.newInstance();
			Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext())
			{
				Map.Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				String methodName = "set" + toUpperCase(key);
				Method method = clazz.getDeclaredMethod(methodName, String.class);
				method.invoke(obj, value);
			}
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	* 首字母大写
	* @param str
	* @return String
	* */
	public static String toUpperCase(String str){
		// 效率高的方法
		char[] chars = str.toCharArray();
		if (chars[0] >= 'a' && chars[0] <= 'z') {
			chars[0] = (char)(chars[0] - 32);
		}
		
		return new String(chars);
	}
	
	/**
	 * 获取各种id(根据纳米级时间点,获取后十位)
	 * @return String
	 * @since 20180805
	 * @add
	 * */
	public static String longToString(){
		//纳米级时间(处理太快,毫秒不能用)
		return String.valueOf(System.nanoTime());
	}

	/**
	 * 根据节点名称获取文书名称
	 * @return nodeId
	 * @since 20180805
	 * @add
	 * */
	public static String getJcjyWs(String nodeId){
		switch (nodeId){
			case "02":
				return "检察建议立案决定书.doc";
			case "04":
				return "审查终结报告.doc";
			case "05":
				return "检察建议书.doc";
			case "07":
				return "检察建议效果评估表.doc";
			case "08":
				return "检察建议结案决定表.doc";
			default:
				return null;
		}
	}
	
	/**
	 * 根据所给的日期生成生固定格式日期（xx年xx月xx日），没有默认当前日期
	 * @param date(xxxx-xx-xx)
	 * */
	public static String getFormateDate(String date){
		if(StringUtils.isBlank(date)){
			date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		String dates[] = date.split("-");
		StringBuffer sb = new StringBuffer();
		sb.append(dates[0]).append("年").append(dates[1]).append("月").append(dates[2]).append("日");
		return sb.toString();
	}
	
	/**
	 * 根据某种规则生成把字符串sql IN方式
	 * @param info 所要拆分的字符串
	 * @param regex 字符串连接规则
	 * */
	public static String stringToSqlInByRegex(String info,String regex){
		if(StringUtils.isBlank(info) || StringUtils.isBlank(regex)){
			return "";
		}
		
		String[] infos = info.split(regex);
		StringBuffer sb = new StringBuffer();
		for(String it : infos){
			sb.append("'").append(it).append("',");
		}
		String result = sb.toString();
		return result.substring(0,result.length()-1);
	}
	
	/**
	 * 根据list拼成sql in 所需字符
	 * @param list
	 * */
	public static String getSqlInByList(List<String> list){
		if(CollectionUtils.isEmpty(list)){
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
	 * 根据list字典项拼成sql in 所需字符
	 * @param dictList
	 * */
	public static String getSqlInByDictList(List<Map<String,String>> dictList){
		if(CollectionUtils.isEmpty(dictList)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(Map<String,String> it : dictList){
			sb.append("'").append(it.get("DICTID")).append("',");
		}
		String result = sb.toString();
		return result.substring(0,result.length()-1);
	}
	
	/**
	 * 下载失败，异常判断
	 * @param msg 下载失败信息
	 * @param response 响应信息
	 * */
	public static void fixFailedMsgInResp(HttpServletResponse response, String msg) throws IOException
	{
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<script>").append("alert('")
				.append(msg).append("');").append("window.history.go(-1);")
				.append("</script>");
		PrintWriter pw = response.getWriter();
		pw.write(sb.toString());
	}
	
	public static String getDbLink(){
		String db_Link = ConfigProperties.getProperties(Constants.DB_LINK_NAME);
		switch(db_Link){
			//测试库dblink
			case "test" :
				return Constants.DB_LINK_TEST;
			//正式库dblink
			default:
				return Constants.DB_LINK_ZS;
		}
	}
}
