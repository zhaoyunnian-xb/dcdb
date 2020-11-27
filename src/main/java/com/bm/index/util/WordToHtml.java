package com.bm.index.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.w3c.dom.Document;

/**
 * word 转换成html
 */
public class WordToHtml{
    
    /**
     * doc转换成html
     * @throws IOException
     */
 
    public static void docToHtml(String filepath,String fileName, String htmlName) throws IOException, TransformerException, ParserConfigurationException {
        final String imagepath = "C:/test/image/";
        final String file = filepath + fileName;
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
/*        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if(!imgPath.exists()){//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });*/
        
        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        
        File htmlFile = new File(filepath + htmlName);
        OutputStream outStream = new FileOutputStream(htmlFile);
        
        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        
        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
        outStream.close();
    }

    /**
     * 将gb2312的html 替换成utf-8的html
     * @throws IOException
     */
    public static void html2utf(String filePath) {
    	try {

    	String content = "charset=utf-8";
    	String templateContent = "";
    	FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
    	// 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
    	int lenght = fileinputstream.available();
    	byte bytes[] = new byte[lenght];
    	fileinputstream.read(bytes);
    	fileinputstream.close();
    	// 通过使用默认字符集解码指定的 byte 数组，构造一个新的 
    	//因为原来的html文件是gb2312格式，所以转为string时也要以GBK格式读取，不然依旧是乱码
    	templateContent = new String(bytes, "GBK");
    	templateContent = templateContent.replaceFirst("charset=gb2312", content);
    	// 因为已经替换字符串了，所以使用UTF-8字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
    	byte tag_bytes[] = templateContent.getBytes("UTF-8");
    	FileOutputStream fileoutputstream = new FileOutputStream(filePath);// 建立文件输出流
    	fileoutputstream.write(tag_bytes);
    	fileoutputstream.close();


    	} catch (Exception e) {
    	System.out.print(e.toString());
    	}


    	}

    /**
     * 替换html中的字符
     * @throws IOException
     */
    public static void htmlReplace(String filePath,String content,String replaceContent) {
        try {

          //  String content = "charset=utf-8";
            String templateContent = "";
            FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
            // 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
            int lenght = fileinputstream.available();
            byte bytes[] = new byte[lenght];
            fileinputstream.read(bytes);
            fileinputstream.close();
            // 通过使用默认字符集解码指定的 byte 数组，构造一个新的
            //因为原来的html文件是gb2312格式，所以转为string时也要以GBK格式读取，不然依旧是乱码
            templateContent = new String(bytes, "UTF-8");
            //templateContent = new String(bytes);
            templateContent = templateContent.replaceAll(content, replaceContent);
            // 因为已经替换字符串了，所以使用UTF-8字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
            byte tag_bytes[] = templateContent.getBytes("UTF-8");
            FileOutputStream fileoutputstream = new FileOutputStream(filePath);// 建立文件输出流
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();


        } catch (Exception e) {
            System.out.print(e.toString());
        }


    }
    public static void main(String argv[]) {
		try {
			docToHtml("C:\\Users\\Administrator\\Desktop\\","检察建议书.doc","检察建议书.html");
            //htmlReplace("F:\\intelProjectZHJW\\test\\svn\\public_prosecution_of_wisdom\\trunk\\sdgj-platform\\src\\main\\webapp\\download\\wssc\\济历下检捕受[2018]37010200154号犯罪嫌疑人权利义务告知书(张勇).html","仿宋_GB2312","fangsong");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}