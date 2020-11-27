package com.bm.dcdb.test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class UserUnitsUtil {

	private static UserUnitsUtil util;

	public static synchronized UserUnitsUtil getInstance() {
		if (util == null) {
			util = new UserUnitsUtil();
		}
		return util;
	}

	public static void main(String[] args) {
		UserUnitsUtil util = new UserUnitsUtil();
		String param = util.init();
		JSONArray ss = util.getUnitsList(param,"141.112.1.9:8001");
		System.out.println(ss);
	}

	HttpClient client = new HttpClient();

	/**
	 * @Function : getUserList
	 * @Description : 获取用户列表
	 * @Author : 张宝金
	 * @param param
	 *            时间戳和数据签名
	 * @throws HttpException
	 * @throws IOException
	 */
	public JSONArray getUserList(String param,String ip) {
		
		GetMethod method = new GetMethod("http://"+ip+"/index/member/getlist?" + param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (200 != method.getStatusCode()) {
			return null;
		}
		JSONObject userListObject = new JSONObject();
		try {
			userListObject = JSONObject.fromObject(method
					.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(userListObject.getString("ret"))) {
			System.out.println(userListObject.getString("msg"));
			return null;
		}
		JSONArray userList = userListObject.getJSONArray("list");
		return userList;
	}

	/**
	 * @Function : getUserDeparmentsList
	 * @Description : 获取用户部门列表
	 * @Author : 张宝金
	 * @param param
	 *            时间戳和数据签名
	 * @throws HttpException
	 * @throws IOException
	 */
	public JSONArray getUserUnitsList(String param,String ip) {
		GetMethod method = new GetMethod("http://"+ip+"/index/member/memberunit?" + param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (200 != method.getStatusCode()) {
			return null;
		}
		JSONObject userDepartmentsObject = new JSONObject();
		try {
			userDepartmentsObject = JSONObject.fromObject(method
					.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(userDepartmentsObject.getString("ret"))) {
			System.out.println(userDepartmentsObject.getString("msg"));
			return null;
		}
		JSONArray userDepartmentsList = userDepartmentsObject
				.getJSONArray("list");
		method.releaseConnection();
		return userDepartmentsList;
	}

	/**
	 * @Function : getGroupsList
	 * @Description : 获取分组列表
	 * @Author : 张宝金
	 * @param param
	 * @return
	 */
	public JSONArray getGroupsList(String param,String ip) {
		
		GetMethod method = new GetMethod("http://"+ip+"/group/getlist?" + param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (200 != method.getStatusCode()) {
			return null;
		}
		JSONObject userDepartmentsObject = new JSONObject();
		try {
			userDepartmentsObject = JSONObject.fromObject(method
					.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(userDepartmentsObject.getString("ret"))) {
			System.out.println(userDepartmentsObject.getString("msg"));
			return null;
		}
		JSONArray userDepartmentsList = userDepartmentsObject
				.getJSONArray("list");
		method.releaseConnection();
		return userDepartmentsList;
	}

	public JSONArray getUserGroupsList(String param,String ip) {
		GetMethod method = new GetMethod("http://"+ip+"/group/member?" + param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (200 != method.getStatusCode()) {
			return null;
		}
		JSONObject userDepartmentsObject = new JSONObject();
		try {
			userDepartmentsObject = JSONObject.fromObject(method
					.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(userDepartmentsObject.getString("ret"))) {
			System.out.println(userDepartmentsObject.getString("msg"));
			return null;
		}
		JSONArray userDepartmentsList = userDepartmentsObject
				.getJSONArray("list");
		method.releaseConnection();
		return userDepartmentsList;
	}

	/**
	 * @Function : getDeparementsList
	 * @Description : 获取部门列表
	 * @Author : 张宝金
	 * @param param
	 *            时间戳和数据签名
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public JSONArray getUnitsList(String param,String ip) {
		GetMethod method = new GetMethod("http://"+ip+"/unit/getlist?" + param);
		try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (200 != method.getStatusCode()) {
			return null;
		}
		JSONObject json = new JSONObject();
		try {
			json = JSONObject.fromObject(method.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(json.getString("ret"))) {
			System.out.println(json.getString("msg"));
			return null;
		}
		JSONArray departmentsList = json.getJSONArray("list");
		method.releaseConnection();
		return departmentsList;
	}

	/**
	 * @Function : init
	 * @Description : 初始化接口
	 * @Author : 张宝金
	 * @return 时间戳和数据签名
	 * @throws HttpException
	 * @throws IOException
	 */
	public String init() {
		StringBuilder sb = new StringBuilder();
		sb.append("test_");
		long time = System.currentTimeMillis()/1000;
		sb.append(time+"_");
		sb.append("12345678");
		String sign = getMD5(sb.toString());
		StringBuilder param = new StringBuilder();
		param.append("app=test");
		param.append("&time="+time);
		param.append("&sign="+sign);
		return param.toString();
	}

	public String init(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("test_");
		long time = System.currentTimeMillis()/1000;
		sb.append(id+"_");
		sb.append(time+"_");
		sb.append("12345678");
		String sign = getMD5(sb.toString());
		StringBuilder param = new StringBuilder();
		param.append("app=test");
		param.append("&id="+id);
		param.append("&time="+time);
		param.append("&sign="+sign);
		return param.toString();
	}
	
	public static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	    	System.out.println("error");
	    }
	    return "";
	}
	
}
