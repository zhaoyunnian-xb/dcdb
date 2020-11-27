package com.bm.index.util;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * poi 读取模板 doc或docx，替换其中的变量，生成新的doc或docx
 * 注：仅限于固定的模板替换，不支持动态的插入表格
 */
public class WordPOI {

    // 返回Docx中需要替换的特殊字符，没有重复项
    // 推荐传入正则表达式参数"\\$\\{[^{}]+\\}"
    public static ArrayList<String> getReplaceElementsInWord(String filePath, String regex) {
        String[] p = filePath.split("\\.");
        if (p.length > 0) {// 判断文件有无扩展名
            // 比较文件扩展名
            if (p[p.length - 1].equalsIgnoreCase("doc")) {
                ArrayList<String> al = new ArrayList<>();
                File file = new File(filePath);
                HWPFDocument document = null;
                try {
                    InputStream is = new FileInputStream(file);
                    document = new HWPFDocument(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Range range = document.getRange();
                String rangeText = range.text();
                CharSequence cs = rangeText.subSequence(0, rangeText.length());
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(cs);
                int startPosition = 0;
                while (matcher.find(startPosition)) {
                    if (!al.contains(matcher.group())) {
                        al.add(matcher.group());
                    }
                    startPosition = matcher.end();
                }
                return al;
            } else if (p[p.length - 1].equalsIgnoreCase("docx")) {
                ArrayList<String> al = new ArrayList<>();
                XWPFDocument document = null;
                try {
                    document = new XWPFDocument(
                            POIXMLDocument.openPackage(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 遍历段落
                Iterator<XWPFParagraph> itPara = document
                        .getParagraphsIterator();
                while (itPara.hasNext()) {
                    XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                    String paragraphString = paragraph.getText();
                    CharSequence cs = paragraphString.subSequence(0,
                            paragraphString.length());
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(cs);
                    int startPosition = 0;
                    while (matcher.find(startPosition)) {
                        if (!al.contains(matcher.group())) {
                            al.add(matcher.group());
                        }
                        startPosition = matcher.end();
                    }
                }
                // 遍历表
                Iterator<XWPFTable> itTable = document.getTablesIterator();
                while (itTable.hasNext()) {
                    XWPFTable table = (XWPFTable) itTable.next();
                    int rcount = table.getNumberOfRows();
                    for (int i = 0; i < rcount; i++) {
                        XWPFTableRow row = table.getRow(i);
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            String cellText = "";
                            cellText = cell.getText();
                            CharSequence cs = cellText.subSequence(0,
                                    cellText.length());
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(cs);
                            int startPosition = 0;
                            while (matcher.find(startPosition)) {
                                if (!al.contains(matcher.group())) {
                                    al.add(matcher.group());
                                }
                                startPosition = matcher.end();
                            }
                        }
                    }
                }
                return al;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // 替换word中需要替换的特殊字符
    public static boolean replaceAndGenerateWord(String srcPath,
            String destPath, Map<String, String> map) {
        String[] sp = srcPath.split("\\.");
        String[] dp = destPath.split("\\.");
        if ((sp.length > 0) && (dp.length > 0)) {// 判断文件有无扩展名
            // 比较文件扩展名
            if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
                try {
                    XWPFDocument document = new XWPFDocument(
                            POIXMLDocument.openPackage(srcPath));
                    // 替换段落中的指定文字
                    Iterator<XWPFParagraph> itPara = document
                            .getParagraphsIterator();
                    while (itPara.hasNext()) {
                        XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (int i = 0; i < runs.size(); i++) {
                            String oneparaString = runs.get(i).getText(
                                    runs.get(i).getTextPosition());
                            for (Map.Entry<String, String> entry : map
                                    .entrySet()) {
                                oneparaString = oneparaString.replace(
                                        entry.getKey(), entry.getValue());
                            }
                            runs.get(i).setText(oneparaString, 0);
                        }
                    }

                    // 替换表格中的指定文字
                    Iterator<XWPFTable> itTable = document.getTablesIterator();
                    while (itTable.hasNext()) {
                        XWPFTable table = (XWPFTable) itTable.next();
                        int rcount = table.getNumberOfRows();
                        for (int i = 0; i < rcount; i++) {
                            XWPFTableRow row = table.getRow(i);
                            List<XWPFTableCell> cells = row.getTableCells();
                            for (XWPFTableCell cell : cells) {
                                String cellTextString = cell.getText();
                                for (Entry<String, String> e : map.entrySet()) {
                                    if (cellTextString.contains(e.getKey()))
                                        cellTextString = cellTextString
                                                .replace(e.getKey(),
                                                        e.getValue());
                                }
                                cell.removeParagraph(0);
                                cell.setText(cellTextString);
                            }
                        }
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else
            // doc只能生成doc，如果生成docx会出错
            if ((sp[sp.length - 1].equalsIgnoreCase("doc"))
                    && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
                HWPFDocument document = null;
                try {
                    document = new HWPFDocument(new FileInputStream(srcPath));
                    Range range = document.getRange();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        range.replaceText(entry.getKey(), entry.getValue());
                    }
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(destPath);
                    document.write(outStream);
                    outStream.close();
                    return true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String  getWsmbmc(String wstype) {
    	String wsmbmc="";
    	switch (wstype) {
		case "0201008":
			wsmbmc="犯罪嫌疑人权利义务告知书";
			break;
		case "0201009":
			wsmbmc="被害人诉讼权利义务告知书";
			break;
		case "0201010" :
			wsmbmc = "不批准逮捕案件补充侦查提纲";
			break;
		case "0201011":
			wsmbmc="询问通知书";
			break;
        case "0201012":
            wsmbmc="提讯、提解证";
            break;
		case "0201013":
			wsmbmc="不批准逮捕理由说明书";
			break;
		case "0201014":
			wsmbmc="送达回证";
			break;
		case "0301012":
			wsmbmc="证人、鉴定人名单";
			break;
		case "0201015":
			wsmbmc="办案告知卡（xxx）";
			break;
	    case "0301009":
			wsmbmc="举证、质证提纲";
			break;
		case "0301011":
			wsmbmc="审查报告";
			break;
	    case "0301010":
            wsmbmc="不起诉决定书";
            break;
	    case "0301006":
            wsmbmc="起诉书";
            break;
	    case "0301008":
                wsmbmc="公诉意见书";
                break;
		default:
			break;
		}
		return wsmbmc;
	}
	/**
	 * poi生成文书(拼写)
	 * @param map(key align 对齐方式 1 2 3,fontFamily字体样式,size字体大小,bold是否加粗,value值)
	 * @param srcPath 文书存放路径(.docx)
	 * @return boolean
	 * */
	public static boolean getWsDocx(List<Map<String,Object>> map,String srcPath){
		try {
			XWPFDocument document = new XWPFDocument();
			for(Map item : map){
				String value = (String)item.get("value");
				if(StringUtils.isNotBlank(value)){
					//设置对齐方式
					Integer align = item.get("align") != null?(Integer)item.get("align"):0;
					//字体样式(默认仿宋_GB2312)
					String fontFamily = item.get("fontFamily") != null ? (String)item.get("fontFamily"):"仿宋";
					//字体大小(默认16号字体)
					Integer size = item.get("size") != null ? (Integer)item.get("size"):16;
					//是否加粗
					Boolean bold = item.get("bold") != null ? (Boolean)item.get("bold"):false;
					
					XWPFParagraph xwpfParagraph = document.createParagraph();
					//设置对齐方式
					if(align != 0){
						xwpfParagraph.setFontAlignment(Integer.valueOf(align));
					}
					
					XWPFRun xwpfRun = xwpfParagraph.createRun();
					xwpfRun.setText(value);
					xwpfRun.setFontFamily(fontFamily);
					xwpfRun.setFontSize(Integer.valueOf(size));
					xwpfRun.setBold(Boolean.valueOf(bold));
				}
			}
			
			// 如果存在文书,先删除掉
			File file = new File(srcPath);
			if(file.isFile()){
				file.delete();
			}
			FileOutputStream out = new FileOutputStream(srcPath);
			document.write(out);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
    /**
     * 
     * 办案告知卡生产word (doc03版本) 处理方法
     * @param inpath   模板路径
     * @param outPath	生成输出路径
     * @param replaceMap  替换内容
     * @param times   询问次数
     * @return  "1"  //输出操作完成
     *    		"2"  //获取的文档模板对象没有表格
     *    		"3"  //io过程中 catch 异常操作失败
     *          "4"  //文件模板不存在！
     */
    public static String getReplaceDoc(String inpath,String outPath,Map<String,String> replaceMap,int times) {
		File file = new File(inpath);
		if (file.exists()) {
			try (FileInputStream in = new FileInputStream(file);
				 FileOutputStream out = new FileOutputStream(new File (outPath))) {
				HWPFDocument document = new HWPFDocument(in);
				Range range = document.getRange();
				Paragraph p;
				if(inpath.contains("mx")) {
					 p = range.getParagraph(1);
				}else {
					p = range.getParagraph(2);
				}		
			    if (p.isInTable()){
			    	// 先把需要填充的内容替换
					for (Map.Entry<String,String> entry : replaceMap.entrySet()) {
						range.replaceText(entry.getKey(),entry.getValue());
					}
			    	// 把多余的内容清除
		    		Table tab = range.getTable(p);
		    		if (tab != null) {
		    			int rowNum = tab.numRows() - 1;
			    		int x = 3 + times;
			    		while (rowNum > x) {
			    			TableRow tabRow = tab.getRow(x);
				    		tabRow.delete();
				    		x++;
			    		}
		    		}
			    	document.write(out);
					return "1";//输出操作完成
			    } else {
			    	return "2";//获取的文档模板对象没有表格
			    }
			} catch (Exception e) {
				e.printStackTrace();
				return "3";//io过程中 catch 异常操作失败
			}
		} else {
			return "4";//文件模板不存在！
		}
	}
	public static void main(String args[]){
		String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\告知函.docx";
		path = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\检察建议结案决定登记表.doc";
		List<String> aa = getReplaceElementsInWord(path,"\\$\\{[^{}]+\\}");
		String regex = "\\$\\{(.*?)\\}";
		Map<String,String> map = new HashedMap();
		int i = 0;
		for(String it : aa){
			Pattern p = Pattern.compile("\\{(.*?)\\}");//正则表达式，取=和|之间的字符串，不包括=和|
			Matcher m = p.matcher(it);
			while(m.find()) {
				System.out.print(m.group(1)+" ");
			}
			
			//map.put(it,String.valueOf(i++));
		}
		/*String target = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\aa.doc";
		replaceAndGenerateWord(path,target,map);
		String target1 = "C:\\Users\\Administrator\\Desktop\\新建文件夹";
		WordToPDFUtil.wordToPDF("aa.doc",target1);*/
		//System.out.print(aa);
	}
}