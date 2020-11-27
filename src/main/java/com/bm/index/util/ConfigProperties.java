package com.bm.index.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigProperties {

	private static String fileName = "/config.properties";

	public static String getProperties(String key) {
		Properties p = new Properties();
		String value = "";
		try {
			InputStream in = ConfigProperties.class.getResourceAsStream(fileName);
			p.load(new InputStreamReader(in, "UTF-8"));
			value = p.getProperty(key).trim();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
