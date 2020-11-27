package com.bm.dcdb.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

/**
 * 
 * @ProjectName:  [sdgj-platform]
 * @Package:      [com.bm.sdgj.test.dbcp.ZtfxJob.java]
 * @ClassName:    [ZtfxJob]
 * @Description:  [专题分析数据导入]
 * @Author:       [张宝金]
 * @CreateDate:   [2018年8月14日 下午7:25:18]
 * @Version:      [v1.0]
 *
 */
public class OaUserDbcpTest {

	@Test
	public void test(){
		UserUnitsUtil util = UserUnitsUtil.getInstance();
		
		
		
		DBCPUtils dbcp = DBCPUtils.getInstance();
		Connection conn = dbcp.init();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn.setAutoCommit(false);
			statement = conn.createStatement();
			//user
			String param = util.init();
			JSONArray userJsonArray = util.getUserList(param,"141.112.1.9:8001");
			System.out.println(userJsonArray);
			StringBuilder deluser = new StringBuilder();
			deluser.append("DELETE FROM USERS_NEW");
			statement.executeUpdate(deluser.toString());
			for (Object object : userJsonArray) {
				JSONObject o = JSONObject.fromObject(object.toString());
				StringBuilder usersql = new StringBuilder();
				usersql.append("INSERT INTO USERS_NEW (ID, USERNAME, FULLNAME, DESCRIPTION, ISENABLE, ISLOCKED, JYXM, IS_ADMIN, EMAILADDR, ORDERBY) VALUES ");
				usersql.append("('"+o.getString("id").trim()+"', '"+o.getString("username").trim()+"', '"+o.getString("fullname").trim()+"', ' ', '"+o.getString("isEnable")+"', '"+o.getString("isLocked")+"', NULL, '"+o.getString("is_admin")+"', '"+o.getString("emailaddr")+"', '"+o.getString("orderby")+"') ");
				statement.executeUpdate(usersql.toString());
			}
			System.out.println("用户同步成功");
			//unit
			StringBuilder delunit = new StringBuilder();
			delunit.append("DELETE FROM UNIT_NEW");
			statement.executeUpdate(delunit.toString());
			param = util.init();
			JSONArray unitJsonArray = util.getUnitsList(param,"141.112.1.9:8001");
			for (Object object : unitJsonArray) {
				JSONObject o = JSONObject.fromObject(object.toString());
				StringBuilder unitsql = new StringBuilder();
				unitsql.append("INSERT INTO UNIT_NEW ");
				unitsql.append("(ID, NAME, DESCRIPTION, PARENTID, PARENTIDS, AVAILABLE, VERSION, ORDERBY, IS_ADMIN) VALUES ");
				unitsql.append("('"+o.getString("id")+"', '"+o.getString("name")+"', ' ', '"+o.getString("parentId")+"',  '"+o.getString("parentId")+"', NULL, NULL, '"+o.getString("orderby")+"', '"+o.getString("is_admin")+"')");
				statement.executeUpdate(unitsql.toString());
			}
			System.out.println("部门同步成功");
			//userunit
			StringBuilder delUserUnit = new StringBuilder();
			delUserUnit.append("DELETE FROM USERUNIT_NEW");
			statement.executeUpdate(delUserUnit.toString());
			param = util.init();
			JSONArray userUnitJsonArray = util.getUserUnitsList(param,"141.112.1.9:8001");
			for (Object object : userUnitJsonArray) {
				JSONObject o = JSONObject.fromObject(object.toString());
				StringBuilder userUnitSql = new StringBuilder();
				userUnitSql.append("INSERT INTO USERUNIT_NEW (USERID, UNITID) VALUES ('"+o.getString("userId")+"', '"+o.getString("unitId")+"')");
				statement.executeUpdate(userUnitSql.toString());
			}
			System.out.println("用户部门关系同步成功");
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
			try {
				if(conn != null){
					conn.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
			
		}
		
	}
}
