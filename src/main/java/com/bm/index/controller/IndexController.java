package com.bm.index.controller;

import com.bm.index.dao.source1.DcdbBmmbnrDao;
import com.bm.index.dao.source1.DcdbDbDao;
import com.bm.index.model.DcdbDb;
import com.bm.index.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("index")
public class IndexController {
	@Autowired
	DcdbDbDao dcdbDbDao;
	@Autowired
	DcdbBmmbnrDao dcdbBmmbnrDao;
	public final static Map<Integer, String> nodeMap = new HashMap<Integer, String>() {
		{
			put(1,"新建");
			put(2, "内勤接收");
			put(3, "承办人办理");
			put(4, "部门负责人审核");
			put(5, "承办人办理");
			put(6, "督查室办理");
		}
	};

	@RequestMapping()
	public String index(HttpSession session, String id,String username, Model model){
		UserEntity loginUser =new UserEntity();
		// 用户查询
	//	List<UserEntity> users = dcdbBmmbnrDao.loginQuery(id);
		List<UserEntity> users = dcdbBmmbnrDao.loginQueryByName(username);
		// session存放
		if (users != null && users.size() > 0) {
			 loginUser = users.get(0);
			model.addAttribute("bmmc", users.get(0).getBmmc());
			model.addAttribute("username", users.get(0).getName());

		}
		session.setAttribute("User",loginUser);
		return "index";
	}

	@RequestMapping("/updateDb.do")
	@ResponseBody
	public String updateDb(DcdbDb  dcdbDb){
		String ajzt=dcdbDb.getAjzt();
		//判断当前节点是不是整个节点的最后一部，3为整个节点的最后一步
		if(ajzt.equals("2")){
			Integer nodeid=Integer.valueOf(dcdbDb.getNodeid());
			dcdbDb.setNodeid(nodeid+1+"");
			dcdbDb.setAjzt("1");
			dcdbDb.setNodename(nodeMap.get(Integer.valueOf(dcdbDb.getNodeid())));
			//如果是承办人办理之后的节点没有待办事件了，因为只有审批的权限
			dcdbDb.setAjzt("2");
		}else{
			Integer zt=Integer.valueOf(ajzt);
			dcdbDb.setAjzt(zt+1+"");
		}
		//更新操作
	    int i= dcdbDbDao.updateByExampleSelective(dcdbDb);
		return i+"";
	}

	/**
	 * 模板下载
	 * @add
	 */
	@RequestMapping(value = "/download.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void documentDownload(HttpServletRequest request, String fileName,HttpServletResponse response, HttpSession session) {
		// 文书获取
		String url = session.getServletContext().getRealPath("/") + "download/file/";
		String realFile = url + fileName;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20").replaceAll("%28", "\\(")
					.replaceAll("%29", "\\)").replaceAll("%3B", ";").replaceAll("%40", "@").replaceAll("%23", "\\#")
					.replaceAll("%26", "\\&");

			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			// 输入流
			InputStream fis = new BufferedInputStream(new FileInputStream(realFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
