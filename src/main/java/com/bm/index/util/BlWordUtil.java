package com.bm.index.util;

import com.bm.index.model.DcdbAkx;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenpeng
 * @version V1.0
 * @Package com.bm.sdgj.util
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2018/8/5
 */
public class BlWordUtil
{
	private Logger logger = Logger.getLogger(BlWordUtil.class);
	
	//返回信息常量CODE
	private static String INFO_CODE = "code";
	
	//返回信息常量MSG
	private static String INFO_MSG = "msg";
	
	//返回信息成功编码
	private static int SUCCESS = 1;
	
	//返回信息常量失败编码
	private static int ERROR = 1;
	
	private List<Map> b = new ArrayList<>();
	
	/**
	 * 根据不同后缀名处理word(直接生成实体类)
	 * @param is
	 * @param fileName
	 * @since 20180805
	 * @add
	 * */
	public DcdbAkx readWord(InputStream is,String fileName)
			throws IOException
	{
		String buffer = "";
		DcdbAkx xwblEntity = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			//.doc的处理方式
			if (fileName.endsWith(".doc")) {
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();
				//.docx的处理方式
			} else if (fileName.endsWith("docx")) {
				//OPCPackage opcPackage = POIXMLDocument.openPackage(path);
				XWPFDocument xwpfDocument = new XWPFDocument(is);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(xwpfDocument);
				buffer = extractor.getText();
				extractor.close();
			} else {
				logger.error("上传文件格式不正确!");
			}
			logger.debug("解析"+fileName+"内容:"+buffer);
			//把字符串放到io流中,读取并且处理
			b.clear();
			map = processString(buffer);
			xwblEntity = this.extractDataToList((List)map.get("head"));
			return xwblEntity;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件:",e);
		}
		finally
		{
			if(is != null){
				is.close();
			}
		}
		return new DcdbAkx();
	}
	
	/**
	 * 把字符串放到io流中,读取并且处理
	 * @param content
	 * @return Map<String,Object>
	 * @since 20180805
	 * @add
	 */
	public Map<String,Object> processString(String content)
	{
		//定义内容存储对象
		Map<String,Object> map = new HashMap<String,Object>();
		List group = new ArrayList();//问答组
		List titleList = new ArrayList();//标题组
		List endList = new ArrayList();//结尾组
		
		//设置文字缓冲流和读取流
		StringReader sr = null;
		BufferedReader br = null;
		String str = null;
		//遇到问答的次数
		int count = 0;
		try{
			sr = new StringReader(content);
			br = new BufferedReader(sr);
			//读取每一行数据
			for(int i = 0;;i++){
				str = br.readLine();
				//如果为空终止循环
				if(str == null){
					break;
				}
				//防止书写习惯,一问一答后面有回车
				else if(str.length() == 0){
					continue;
				}else{
					//如果内容带有问答模式的,放入到问答组中
					if((count == 0 ||count == 1) &&(str.startsWith("问") || str.startsWith("答"))){
						count = 1;
						group.add(str);
						
						//如果count为0,表示上方标题以及信息行
					}else if(count == 0 ){
						titleList.add(str);
						
						//剩下的为最后需要签字的信息
					}else{
						endList.add(str);
					}
				}
			}
			
			if(!CollectionUtils.isEmpty(b)){
				map.put(INFO_CODE,SUCCESS);
				map.put(INFO_MSG,"操作成功");
			}else{
				map.put(INFO_CODE,ERROR);
				map.put(INFO_MSG,"文档格式不正确");
			}
			
			map.put("head",titleList);
			map.put("body",b);
			map.put("end",endList);
		}catch (Exception e){
			map.put(INFO_CODE,ERROR);
			map.put(INFO_MSG,"操作异常");
			logger.error("解析字符串出现异常",e);
		}
		finally{
			if(sr != null){
				sr.close();
			}
			if(br != null){
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 根据文书类型提取文档部分数据(提取数据到实体中)
	 * @param list
	 * @since 20180805
	 * @add
	 */
	public DcdbAkx extractDataToList(List<String> list){
		if (CollectionUtils.isEmpty(list))
		{
			return null;
		}
		
		String content = this.associStringToList(list);
		return this.associStringToBean(content);
	}
	
	/**
	 *把list组装成string
	 * @param list
	 * @return String
	 */
	private String associStringToList(List<String> list){
		String content = "";
		
		//对返回的list数据进行拼接
		if(CollectionUtils.isNotEmpty(list)){
			for(String item : list){
				//把上半部分进行去除空格和回车,并且拼接起来
				content += item.trim().replace("\\n","");
			}
			content += " $end";
		}
		return content;
	}
	
	/**
	 *根据文书类型号填充bean
	 * @param content
	 * @param wsType
	 * @return String
	 */
	private DcdbAkx associStringToBean(String content){
		DcdbAkx xwblEntity = new DcdbAkx();
		//如果为空,返回实例类型
		if(StringUtils.isNotBlank(content)){
			content = content.replace("会议时间："," $Hysj");
			content = content.replace("会议名称："," $Hymc");
			content = content.replace("会议议题："," $Yjyt");
			content = content.replace("登记人："," $Djr");
			content = content.replace("会议决定事项："," $Hyjdsx");
			//content = content.replace("被询问人姓名："," $Bxwrmc");
			content = content.replace("会议督办事项："," $Dbsx");
			content = content.replace("任务要求："," $Rwyq");
			refectBeanForContent(xwblEntity,content);
		}
		return xwblEntity;
	}
	
	/*
	* 通过反射提取数据,并且放入到实体中(入参,出参一定要一致)
	* @param obj
	* @pram content
	* @result object
	* */
	private Object refectBeanForContent(Object obj,String content){
		try
		{
			//通过反射获取属性名称
			List<String> fields = this.refectToGetField(DcdbAkx.class);
			
			for (String item : fields)
			{
				//构造set方法
				String setName = "set" + item;
				String regex = "(?<=\\$"+item+").+?(?=\\$)";
				
				//获取该类指定的方法
				Method method = obj.getClass().getDeclaredMethod(setName, String.class);
				method.invoke(obj,this.matcherStr(regex,content));
			}
			logger.debug("提取的数据值为"+obj.toString());
		}catch (Exception e)
		{
			logger.error("执行反射提取文档属性出现错误",e);
		}
		
		return obj;
	}
	
	/*
	* 正则获取数据
	* @param regex
	* @param content
	* @return String
	* */
	private String matcherStr(String regex,String content){
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(content);
		if(m.find()){
			String a = m.group(0).trim();
			return m.group(0).trim();
		}
		return null;
	}
	
	/*
	* 反射获取类属性
	* @param clazz
	* @return List<String>
	* */
	private List<String> refectToGetField(Class clazz){
		List<String> strList = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		for(Field item : fields){
			
			//如果属性类型不为string类型,忽略
			if(!item.getGenericType().toString().
					equals("class java.lang.String")){
				break;
			}
				
			//获取属性的名称
			String fieldName = item.getName();
			//首字母大写
			strList.add(toUpperCase(fieldName));
		}
		strList.remove("GroupList");
		return strList;
	}
	
	/*
	* 首字母大写
	* @param str
	* @return String
	* */
	private String toUpperCase(String str){
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
	 * 处理询问时间
	 * @return str
	 * @since 20180805
	 * @add
	 * */
	public static String processXwsj(String str){
		//获取当前时间,作为数组
		Calendar now = Calendar.getInstance();
		String nowStr[] = {String.valueOf(now.get(Calendar.YEAR)),
				"01",
				"01",
				"00",
				"00"};

		//截取字符串,并且组成数组格式
		String thisXwsj = str.substring(0,str.indexOf("至")).replace("年","@").replace("月","@").replace("日","@").replace("时","@").replace("分","@");
		String strs[] = thisXwsj.split("@");
		String date = "";
		
		//处理时分秒数据,空格等
		for(int i = 0;i<strs.length;i++){
			//处理时间,如果为空,则为当前时间
			if(StringUtils.isBlank(strs[i])){//.trim()
				strs[i] = nowStr[i];
			}
			else{
				strs[i] = strs[i].trim();
			}
			
			//单独处理年份
			if(i == 0){
				//如果年份为两位数,前面加上20,即20..年
				if(strs[0].trim().length() == 2){
					strs[0] = 20 + strs[0].trim();
				}
			}
			//如果时间小于10,前面补0
			else if(Integer.valueOf(strs[i].trim())<10){
				strs[i] = "0" + Integer.valueOf(strs[i].trim());
			}
			
			date += strs[i];
		}
		
		return date;
	}
}
