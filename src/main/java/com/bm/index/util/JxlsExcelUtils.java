package com.bm.index.util;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ExcelUtils.java
 * @Package jxls
 * @Description:jxls 操作excel(导出)
 * @author Zhang Baojin
 * @date 2017年5月31日 下午9:08:53
 * @version V1.0
 */
public class JxlsExcelUtils {

	private static JxlsExcelUtils excelUtils;

	public static JxlsExcelUtils getInstance() {
		if (excelUtils == null) {
			excelUtils = new JxlsExcelUtils();
		}
		return excelUtils;
	}

	/*public static void main(String[] args) throws InvalidFormatException, IOException, SAXException {
		JxlsExcelUtils utils = JxlsExcelUtils.getInstance();
		List<IPModel> list = new ArrayList<IPModel>();
		IPModel ipModel = new IPModel();
		ipModel.setId(1);
		ipModel.setIp(UUID.randomUUID().toString());
		ipModel.setName(UUID.randomUUID().toString());
		ipModel.setOther(UUID.randomUUID().toString());
		list.add(ipModel);
		ipModel = new IPModel();
		ipModel.setId(2);
		ipModel.setIp(UUID.randomUUID().toString());
		ipModel.setName(UUID.randomUUID().toString());
		ipModel.setOther(UUID.randomUUID().toString());
		list.add(ipModel);
		utils.exportExcel(list);
	}
*/
	/**
	* @Title: exportExcel
	* @Description:导出excel
	* @param list
	* @return void
	* @author Zhang Baojin
	*/ 
	public static void  exportExcel(Map<String, Object> beans, String templatefn, String path, HttpServletResponse hr) {
		//String templateFileName = "src/main/resources/excelmodel/model.xls"; //D:\workpsace\train_question\src\main\resouces\execlTemplate\question1.xls
		String templateFileName=templatefn;
		String fileName=System.currentTimeMillis() + ".xls";
		String destPath = path+ "/export/" +fileName;
		File file = new File(path+ "/export/");
		if (!file.exists()){
			file.mkdir();
		}
		BufferedOutputStream bouts = null;
		BufferedInputStream bins = null;
		try {
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destPath);
			hr.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
			hr.setContentType("application/vnd.ms-excel;charset=utf-8");
			hr.setCharacterEncoding("UTF-8");
			bins = new BufferedInputStream(new FileInputStream(destPath));
			// 读取目标文件，通过response将目标文件写到客户端
			bouts = new BufferedOutputStream(hr.getOutputStream());
			// 写文件
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			// 开始向网络传输文件流
			while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
				bouts.write(buffer, 0, bytesRead);
			}
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bouts.flush();
				bins.close();
				bouts.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		FileUtil.deleteFile(destPath);
	}


}
