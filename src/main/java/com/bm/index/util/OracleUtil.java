package com.bm.index.util;

import java.sql.Clob;
import java.sql.SQLException;

/*
 * oracle工具类
 * 
 * @author daipx
 * 
 * */
public class OracleUtil
{
	
	//查询 clob数据，将clob类型转成string
	public static String oracleClob2Str(Clob clob)  {
		try {
			return (clob != null ? clob.getSubString(1, (int) clob.length()) : "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
