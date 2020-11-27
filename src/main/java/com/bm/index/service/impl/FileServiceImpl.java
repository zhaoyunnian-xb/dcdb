package com.bm.index.service.impl;


import com.bm.index.dao.source1.DictItemDao;
import com.bm.index.dao.source1.FileDao;
import com.bm.index.service.FileService;
import com.bm.index.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Clob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenpeng
 * @version V1.0
 * @Package com.bm.ipa.service
 * @Description: (检察建议文件有关方法)
 * @date 2018/8/28
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private DictItemDao dictItemDao;

    //打印日志
    private static Log logger = LogFactory.getLog(FileUtil.class);

    //文书生成跟路径
    private final String WSSC_ROOT = "download/wssc/dcdb/";

    //文书模板根目录
    private final String WSMB_ROOT = "download/wsmb/dcdb/";
    //linux路径

    /**
     * 文件批量下载(单个文件单独下载,多个文件压缩下载)
     *
     * @param responses 请求
     * @param id        文件id
     */
    public boolean downloadFiles(HttpServletResponse responses, String id) {
        try {
            List<Map<String, String>> fileList = this.qryIpaFileList(null, null, id);
            return this.downloadFiles(responses, fileList);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件批量下载(单个文件单独下载,多个文件压缩下载)
     *
     * @param responses 请求
     * @param xsbh      线索id
     * @param nodeId    nodeId主节点id
     */
    public boolean downloadFiles(HttpServletResponse responses, String xsbh, String nodeId) {
        try {
            List<Map<String, String>> fileList = this.qryIpaFileList(xsbh, nodeId, null);
            return this.downloadFiles(responses, fileList);
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 文件批量下载(单个文件单独下载,多个文件压缩下载)
     *
     * @param fileList  查询出来的列表
     * @param responses 相应
     */
    public boolean downloadFiles(HttpServletResponse responses, List<Map<String, String>> fileList) {
        try {
            if (CollectionUtils.isNotEmpty(fileList)) {
                //一个文件单独下载
                if (fileList.size() == 1) {
                    return FileUtil.downloadFile(responses, fileList.get(0));
                } else {
                    //根据项目id查询项目名称
                    return FileUtil.downloadFiles(responses, fileList, fileList.get(0).get("XSBH"));
                }
            }
            CommonUtils.fixFailedMsgInResp(responses, "未找到相关文件！");
        } catch (Exception e) {
            logger.error("文件下载失败!", e);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 单个文件上传
     *
     * @param request
     * @param file    单个文件
     * @param xsbh    线索编号
     * @param nodeId  主节点id
     */
    public boolean uploadFile(HttpServletRequest request, MultipartFile file, String nodeId, String xsbh) {
        try {
            List<Map<String, String>> list = new ArrayList<>();
            if (file != null) {
                String uuid = getUUID();
                //文件上传
                FileUtil.uploadFile(file, xsbh, uuid);

                //对实体模型进行封装
                Map<String, String> map = new HashedMap();
                this.packageMap(file, xsbh, uuid, nodeId, map, request);

                list.add(map);
                this.batcheInsertFile(list);
                return true;
            }
        } catch (Exception e) {
            logger.error("文件上传失败!", e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 文件批量上传
     *
     * @param request
     * @param nodeId  主节点id
     * @param files   多个文件
     * @param xsbh    线索id
     * @return List<Map   <   String   ,   String>> 文件列表
     */
    public List<Map<String, String>> uploadFiles(List<MultipartFile> files, String nodeId, String xsbh, HttpServletRequest request) {
        if (CollectionUtils.isNotEmpty(files)) {
            MultipartFile[] files1 = new MultipartFile[files.size()];
            for (int i = 0; i < files.size(); i++) {
                files1[i] = files.get(i);
            }
            return this.uploadFiles(nodeId, files1, xsbh, request);
        }
        return new ArrayList<>();
    }

    /**
     * 文件批量上传
     *
     * @param request
     * @param nodeId  主节点id
     * @param files   多个文件
     * @param xsbh    线索id
     * @return List<Map   <   String   ,   String>> 文件列表
     */
    public List<Map<String, String>> uploadFiles(MultipartFile[] files, String nodeId, String xsbh, HttpServletRequest request) {
        return this.uploadFiles(nodeId, files, xsbh, request);
    }

    /**
     * 文件批量上传
     *
     * @param request
     * @param nodeId  主节点id
     * @param files   多个文件
     * @param xsbh    线索id
     */
    public boolean uploadFiles(HttpServletRequest request, MultipartFile[] files, String nodeId, String xsbh) {
        try {
            if (files != null && files.length > 0) {
                this.uploadFiles(nodeId, files, xsbh, request);
                return true;
            }
        } catch (Exception e) {
            logger.error("文件上传失败!", e);
            e.printStackTrace();
            //上传失败后，清空文件
            return false;
        }

        return true;
    }

    /**
     * 批量插入
     *
     * @param list list
     */
    public boolean batcheInsertFile(List<Map<String, String>> list) {
        fileDao.batcheInsertFile(list);
        return true;
    }

    /**
     * 获取UUID
     */
    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 封装map
     *
     * @param nodeId  主节点id
     * @param request 请求对象
     * @param file    文件
     * @param xsbh    线索编号
     * @param map     要放入的map
     * @param uuid    序列号
     */
    private void packageMap(MultipartFile file, String xsbh, String uuid, String nodeId, Map<String, String> map,
                            HttpServletRequest request) {
        map.put("xsbh", xsbh);
        map.put("nodeId", nodeId);
        map.put("uuid", uuid);
        map.put("fileName", file.getOriginalFilename());
        map.put("fileSize", String.valueOf(file.getSize()));
        map.put("userId", "001");
        map.put("userName", "张三");
        map.put("orgCode", "370000");
    }

    /**
     * 查询文件列表
     *
     * @param xsbh   应用id
     * @param nodeId 当前状态
     */
    public List<Map<String, String>> qryIpaFileList(String xsbh, String nodeId, String id) {
        Map<String, String> map = new HashedMap();
        map.put("xsbh", xsbh);
        map.put("nodeId", nodeId);
        map.put("id", id);
        return fileDao.qryIpaFileListById(map);
    }


    public List<Map<String, String>> qryFileInfoList(Map<String, String> map) {
        return fileDao.qryFileListByMap(map);
    }

    /**
     * 删除文件列表
     *
     * @param map(nodeId,xsbh,id)
     */
    public boolean deleteFileInfo(Map<String, String> map) {
        try {
            fileDao.deleteIpaFileInfo(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件IDs查询文件
     *
     * @param map(nodeId,xsbh,id)
     */
    private List<Map<String, String>> qryFileListByIds(Map<String, String> map) {
        return fileDao.qryFileListByIds(map);
    }

    /**
     * 删除文件
     *
     * @param bh     线索编号
     * @param nodeId 节点id
     * @param ids    文件id（可以是单个，也可以是多个sql IN的方式）
     */
    public boolean deleteIpaFiles(String bh, String nodeId, String ids) {
        try {
            Map<String, String> map = new HashedMap();
            map.put("xsbh", bh);
            map.put("nodeId", nodeId);
            map.put("id", ids);
            //查询文件信息
            List<Map<String, String>> fileList = this.qryFileInfoList(map);
            //删除数据库信息
            this.deleteFileInfo(map);

            //删除文件
            if (CollectionUtils.isNotEmpty(fileList)) {
                for (Map<String, String> item : fileList) {
                    //获取文件名
                    String fileName = item.get("TARGETFILENAME");
                    //获取项目相对路径
                    String xsbh = item.get("XSBH");

                    if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(xsbh)) {
                        //获取文件后缀名
                        String prefix = fileName.split("\\.")[1];
                        String id = item.get("FILENAME");
                        if (!StringUtils.isEmpty(id)) {
                            //拼接文件名
                            String path =
                                    FileUtil.getRoot() + "jcjy" + File.separator + xsbh + File.separator + id + "."
                                            + prefix;
                            FileUtil.deleteFile(path);
                        }
                    }
                }
                return true;
            }
            return true;
        } catch (Exception e) {
            logger.error("文件删除失败", e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param map 文件列表
     */
    public boolean deleteIpaFiles(Map<String, String> map) {
        try {
            List<Map<String, String>> fileList = this.qryIpaFileList(map.get("xsbh"), map.get("nodeId"), null);
            return fileDelete(fileList);
        } catch (Exception e) {
            logger.error("文件删除失败", e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param map 文件列表
     */
    public boolean deleteFiles(Map<String, String> map) {
        try {
            List<Map<String, String>> fileList = this.qryFileListByIds(map);
            if (fileDelete(fileList)) {
                return this.deleteFileInfo(map);
            }
            return false;
        } catch (Exception e) {
            logger.error("文件删除失败", e);
            return false;
        }
    }

    /**
     * 文书生成
     *
     * @param request
     * @param map     （参数，nodeId,xsbh）
     * @return map 返回参数code,docPath,htmlPath，
     */
    public boolean jcjyScws(HttpServletRequest request, Map<String, String> map) {
        try {
            //获取节点id
            String nodeId = map.get("nodeId");

            if (org.apache.commons.lang.StringUtils.isNotBlank(nodeId)) {
                String tempName = map.get("file");

                //查询文书内容
                Map<String, Object> wsnr = fileDao.jcjyScws(map);
                if (MapUtils.isNotEmpty(wsnr)) {
                    if(StringUtils.isEmpty(wsnr.get("spbz"))){
                        wsnr.put("SPBZ",map.get("spbz"));
                    }
                    this.creatWsDoc(request, map, wsnr, tempName);
                    return true;
                }

                logger.error("not found data for map !");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 下载文书
     *
     * @param response
     * @param map      （参数，nodeId,xsbh）
     * @return map 返回参数code,docPath,htmlPath，
     */
    public boolean downloadWs(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) {
        try {
            if (MapUtils.isNotEmpty(map)) {
                List<Map<String, String>> wsList = fileDao.downloadWs(map.get("xsbh"), map.get("nodeId"));
                if (CollectionUtils.isNotEmpty(wsList)) {
                    FileUtil.downloadWsFiles(request, response, map.get("xsbh"), wsList);
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下载文书
     *
     * @param response
     * @param xsbh     线索编号
     * @param wsList   查询出来的文书列表
     * @return map 返回参数code,docPath,htmlPath，
     */
    public boolean downloadWs(HttpServletRequest request, HttpServletResponse response,
                              List<Map<String, String>> wsList, String xsbh) {
        try {
            if (CollectionUtils.isNotEmpty(wsList)) {
                FileUtil.downloadWsFiles(request, response, xsbh, wsList);
                return true;
            }
            CommonUtils.fixFailedMsgInResp(response, "未找到该文件");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据用户编号查询已经完结的线索的文书列表
     *
     * @param map userId xsbh
     * @return List<Map   <   String   ,   String>>
     */
    public List<Map<String, String>> qryWsByUserId(Map<String, String> map) {
        return fileDao.qryWsByUserId(map);
    }

    /**
     * 根据用户编号查询已经完结的线索的文书列表
     *
     * @param id     线索编号
     * @param nodeId 节点id
     * @return List<Map   <   String   ,   String>>
     */
    public List<Map<String, String>> qryDcdbWsInfo(String id, String nodeId) {
        Map<String, String> map = new HashMap<>();
        map.put("nodeId", nodeId);
        map.put("xsbh", id);
        return fileDao.qryDcdbWsInfo(map);
    }

    /**
     * 根据前台返回的节点查询对应的文书
     *
     * @param map (xsbh 线索编号 type 是否全部查询1是0否 nodeId ,uuid sql中拼装的字符串)
     * @return List<Map   <   String   ,   String>>
     */
    public List<Map<String, String>> downloadWsList(Map<String, String> map) {
        return fileDao.downloadWsList(map);
    }

    /**
     * 生成检察建议文书
     *
     * @param request
     * @param map      （参数，nodeId,xsbh）
     * @param tempName 模板名称
     * @param wsnr     文书数据
     * @return map 返回参数code,docPath,htmlPath，
     */
    private boolean creatWsDoc(HttpServletRequest request, Map<String, String> map, Map<String, Object> wsnr,
                               String tempName) throws IOException, ParserConfigurationException, TransformerException {
        String path = WebUtils.getRealPath(request.getSession().getServletContext(), "");
        //获取线索编号
        String dbid = map.get("dbid");
        if (MapUtils.isNotEmpty(wsnr)) {
            //构造文书模板路径
            String tempPath = path + WSMB_ROOT + tempName;
            //构造文书生成的路径
            String wsscPath = path + WSSC_ROOT + dbid;

            //验证路径
            if (FileUtil.makeDirectory(wsscPath)) {
                // add begin for 案管受案  特殊逻辑  根据节点标志的不同
                String targetName;//文书生成的目标文件   文件名
                String nodeId = map.get("nodeId");//文件的节点标志   已经失去一部分原有节点控制标志的含义  更多的是一个标志
                targetName = tempName;
                // add end for 案管受案  特殊逻辑

                //文书生成名称（带路径）
                String wsPath = wsscPath + File.separator + targetName;
                //获取文书模板标签组
                List<String> wsTempSpan = WordPOI.getReplaceElementsInWord(tempPath, "\\$\\{[^{}]+\\}");

                //根据模板放入数据,和模板标签
                Map<String, String> tempMap = new HashedMap();
                for (String it : wsTempSpan) {
                    //根据正则匹配变量，获取map中的数据
                    Object value = wsnr.get(this.getValue(it));
                    String nvalue = "";
                    if (value instanceof Clob) {
                        nvalue = OracleUtil.oracleClob2Str((Clob) value);
                    } else {
                        nvalue = (String) value;
                    }
                    nvalue = org.apache.commons.lang.StringUtils.isNotBlank(nvalue) ? nvalue : " ";
                    if ("0504".equals(nodeId)) {
                        if (nvalue.contains("\n")) {
                            nvalue = nvalue.replaceAll("\n", "\r\n");
                        }
                    }
                    tempMap.put(it, nvalue);
                }

                boolean flag = WordPOI.replaceAndGenerateWord(tempPath, wsPath, tempMap);
                //替换文书中的内容
                if (flag) {
                    //插入数据
                    Map<String, String> insertData = new HashedMap();
                    insertData.put("uuid", UUIDUtils.getUUID());
                    insertData.put("bllx", map.get("bllx"));
                    insertData.put("dbid", dbid);
                    insertData.put("wjmc", targetName);
                    insertData.put("wsbm", map.get("wsbm"));
                    insertData.put("tempName", targetName);
                    insertData.put("wjlj", WSSC_ROOT + dbid + File.separator);

                    //文书入库(先删除)
                    fileDao.deleteWsscPath(insertData);
                    fileDao.insertWsscPath(insertData);
                    //转成html
                    WordToHtml.docToHtml(wsscPath + File.separator, targetName, targetName.split("\\.")[0] + ".html");
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 截取字符串${abc} 取 abc
     *
     * @param span 例${abc} regex "\\{(.*?)\\}"
     * @return string 例abc
     */
    private String getValue(String span) {
        //正则表达式
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(span);
        while (m.find()) {
            return m.group(1);
        }
        return " ";
    }

    /**
     * 生成检察建议文书多个
     *
     * @param request
     * @param map      （参数，nodeId,xsbh）
     * @param tempName 模板名称
     * @param wsnr     文书数据
     * @return map 返回参数code,docPath,htmlPath，
     */
    public boolean createDocs(HttpServletRequest request, Map<String, String> map, Map<String, Object> wsnr, String tempName) {

        String path = null;
        try {
            path = WebUtils.getRealPath(request.getSession().getServletContext(), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        //获取线索编号
        String xsbh = map.get("xsbh");
        if (MapUtils.isNotEmpty(wsnr)) {
            //构造文书模板路径
            String tempPath = path + WSMB_ROOT + tempName;
            //构造文书生成的路径
            String wsscPath = path + WSSC_ROOT + xsbh;

            String wsName = "犯罪嫌疑人权利义务告知书" + "(" + map.get("wsName") + ")";

            //验证路径
            FileUtil.makeDirectory(wsscPath);
            //文书生成名称（带路径）
            String wsPath = wsscPath + File.separator + wsName + ".doc";
            //获取文书模板标签组
            List<String> wsTempSpan = WordPOI.getReplaceElementsInWord(tempPath, "\\$\\{[^{}]+\\}");

            //根据模板放入数据,和模板标签
            Map<String, String> tempMap = new HashedMap();
            for (String it : wsTempSpan) {
                //根据正则匹配变量，获取map中的数据
                Object value = wsnr.get(this.getValue(it));
                String nvalue = "";
                if (value instanceof Clob) {
                    nvalue = OracleUtil.oracleClob2Str((Clob) value);
                } else {
                    nvalue = (String) value;
                }
                nvalue = org.apache.commons.lang.StringUtils.isNotBlank(nvalue) ? nvalue : " ";
                tempMap.put(it, nvalue);
            }

            boolean flag = WordPOI.replaceAndGenerateWord(tempPath, wsPath, tempMap);
            //替换文书中的内容
            if (flag) {
                //插入数据
                Map<String, String> insertData = new HashedMap();
                insertData.put("uuid", map.get("uuid"));
                insertData.put("xsbh", xsbh);
                insertData.put("nodeId", map.get("nodeId"));
                insertData.put("tempName", wsName + ".doc");
                insertData.put("path", WSSC_ROOT + xsbh + File.separator);

                fileDao.insertWsscPath(insertData);
                //转成html
                try {
                    WordToHtml.docToHtml(wsscPath + File.separator, wsName + ".doc", wsName + ".html");
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (TransformerException e) {
                    e.printStackTrace();
                    return false;
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 上传
     */
    public boolean uploadScZjBgFile(HttpServletRequest request, MultipartFile file, String nodeId, String xsbh, String fileName) {
        String rootPath = request.getRealPath("/");
        String path = rootPath + WSSC_ROOT + xsbh + File.separator;
        //生成文件
        FileUtil.makeDirectory(path);
        //根目录+文件相对目录+项目id (文件夹路径) uuid+后缀(文件名称)
        try {
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void saveSczjBg(Map<String, String> insertData) {
        fileDao.insertWsscPath(insertData);
    }

    @Override
    public boolean transToHtml(String fileName, String rootPath) {
        String fileBefore = (fileName.split("\\."))[1];
        String filename = fileName;
        if (fileBefore.equals("doc")) {
            try {
                WordToHtml.docToHtml(rootPath, filename, (filename.split("\\."))[0] + ".html");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (TransformerException e) {
                e.printStackTrace();
                return false;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (fileBefore.equals("docx")) {
            String filePath = rootPath + filename;
            String wsfilepath = rootPath + (filename.split("\\."))[0] + ".html";
            try {
                WordToHtml.html2utf(wsfilepath);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public int checkWj(String wjName, String nodeId, String xsbh, HttpServletRequest request) {
        //校验文件
        return fileDao.checkWj(wjName, nodeId, xsbh);

    }

    public void deleteXyrgzsInfo(String wsbh) {
        fileDao.deleteXyrgzsInfo(wsbh);
    }

    public void saveXyrgzsInfo(List<Map<String, String>> infoList) {
        fileDao.saveXyrgzsInfo(infoList);
    }

    /**
     * 文件批量上传
     *
     * @param request
     * @param nodeId  主节点id
     * @param files   多个文件
     * @param xsbh    线索id
     * @return List<Map   <   String   ,   String>> 文件列表
     */
    private List<Map<String, String>> uploadFiles(String nodeId, MultipartFile[] files, String xsbh, HttpServletRequest request) {
        try {
            if (files != null && files.length > 0) {
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (MultipartFile item : files) {
                    //获取uuid,并且上传文件
                    String uuid = getUUID();
                    FileUtil.uploadFile(item, xsbh, uuid);

                    //对实体模型进行封装
                    Map<String, String> map = new HashedMap();
                    this.packageMap(item, xsbh, uuid, nodeId, map, request);
                    list.add(map);
                }
                this.batcheInsertFile(list);
                return list;
            }
        } catch (Exception e) {
            logger.error("文件上传失败!", e);
            e.printStackTrace();
            //上传失败后，清空文件
            return new ArrayList<>();
        }

        return new ArrayList<>();
    }

    /*
     * 删除磁盘中的文件
     * @param fileList 查询出来的文件列表
     * */
    private boolean fileDelete(List<Map<String, String>> fileList) {
        try {
            if (CollectionUtils.isNotEmpty(fileList)) {
                for (Map<String, String> item : fileList) {
                    //获取文件名
                    String fileName = item.get("TARGETFILENAME");
                    //获取项目相对路径
                    String xsbh = item.get("XSBH");

                    if (!StringUtils.isEmpty(fileName) && !StringUtils.isEmpty(xsbh)) {
                        //获取文件后缀名
                        String prefix = fileName.split("\\.")[1];
                        String id = item.get("FILENAME");
                        if (!StringUtils.isEmpty(id)) {
                            //拼接文件名
                            String path =
                                    FileUtil.getRoot() + "jcjy" + File.separator + xsbh + File.separator + id + "."
                                            + prefix;
                            FileUtil.deleteFile(path);
                        }
                    }
                }
                return true;
            }
            return true;
        } catch (Exception e) {
            logger.error("文件删除失败", e);
            return false;
        }
    }

    @Override
    public List<String> selAlreadyNode(String xsbh) {
        return org.apache.commons.lang.StringUtils.isBlank(xsbh) ? null : fileDao.selAlreadyNode(xsbh);
    }

}
