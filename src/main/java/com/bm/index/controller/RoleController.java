package com.bm.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.bm.index.common.annotation.PageHelperAnn;
import com.bm.index.dao.source1.RoleUserDao;
import com.bm.index.model.*;
import com.bm.index.util.FileUtil;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName JdtbController
 * @Description 督查督办-季度填报Controller
 * @Author daipx
 * @Date 2019/3/5 9:40
 * @Version 1.0
 **/

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleUserDao dao;

    private Gson mGson = new Gson();

    private static Log logger = LogFactory.getLog(RoleController.class);

    /**
     * 查询角色列表
     *
     * @param se
     * @return
     */
    @RequestMapping("/queryRoleList.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryRoleList(HttpSession se,  RoleUser roleUser) {
        JSONObject jsonobject = new JSONObject();
        try {

            List<RoleUser> roleUsers = dao.selectRoleList(roleUser);
            jsonobject.put("data", roleUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }
    /**
     * 查询用户状态列表
     *
     * @param se
     * @return
     */
    @RequestMapping("/queryNodeStatus.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryNodeStatus(HttpSession se,  RoleUser roleUser) {
        JSONObject jsonobject = new JSONObject();
        try {

            List<RoleUser> roleUsers = dao.selectNodeStatus(roleUser);
            jsonobject.put("data", roleUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }
    /**
     * 查询用户列表
     *
     * @param se
     * @return
     */
    @RequestMapping("/queryRoleUserList.do")
    @ResponseBody
    @PageHelperAnn
    public JSONObject queryRoleUserList(HttpSession se,  RoleUser roleUser) {
        JSONObject jsonobject = new JSONObject();
        try {

            List<RoleUser> roleUsers = dao.selectRoleUserList(roleUser);
            jsonobject.put("data", roleUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /** 保存角色
     * @param se
     * @param request
     * @param response
     * @param roleUser
     */
    @RequestMapping("/saveRole.do")
    @ResponseBody
    public void saveRole(HttpSession se, HttpServletRequest request, HttpServletResponse response,RoleUser roleUser) {
        Map<String, Object> mapRes = new HashMap<>();
        String msg = "添加成功";
        String code = "1";
        try {
            roleUser.setRoleid(FileUtil.getUUID());
            List<RoleUser> list = dao.selectRoleList(roleUser);
            if(CollectionUtils.isNotEmpty(list)){
                msg = "添加失败,"+roleUser.getYwtype()+"下的"+roleUser.getRolename()+"角色已添加过";
                code = "2";
            }else{
                int res = dao.insertRole(roleUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            code = "2";

        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }


    /** 保存用户
     * @param response
     * @param se
     * @param user
     */
    @RequestMapping("/saveUser.do")
    @ResponseBody
    public void saveUser(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "";
        List<RoleUser> list = new ArrayList<>();
        try {
            String[] users=user.getUsername().split(",");
            for (String str:users){
                RoleUser role= new RoleUser();
                role.setUserid(str.split("@")[2]);
                role.setUsername(str.split("@")[3]);
                role.setUnitname(str.split("@")[1]);
                role.setUnitid(str.split("@")[0]);
                role.setRoleid(user.getRoleid());
                role.setRolename(user.getRolename());
                role.setYwtype(user.getYwtype());
                list.add(role);
                //   str += depid+"@"+depname+"@"+userid+"@"+username+","
            }
            for(RoleUser us:list){
                dao.insertUser(us);
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    /** 保存节点状态
     * @param response
     * @param se
     * @param user
     */
    @RequestMapping("/saveNodeStatus.do")
    @ResponseBody
    public void saveNodeStatus(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "保存成功";
        try {
            List<RoleUser> list = dao.selectNodeStatus(user);
            if(CollectionUtils.isNotEmpty(list)){
                msg = "数据已重复录入请检查";
                code = "2";
            }else{
                dao.insertNodeStatus(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }


    /**
     * @return
     */
    @RequestMapping("/queryXzJsList.do")
    @ResponseBody
    public void queryXzJsList(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "";
        try {
            List<RoleUser> bmList = dao.selectRoleList(user);
            mapRes.put("data", bmList);

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    @RequestMapping("/deleteRole.do")
    @ResponseBody
    public void deleteRole(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "删除成功";
        try {
             dao.deleteRole(user);
             dao.deleteRoleUser(user);
             dao.deleteRoleNode(user);

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }
    @RequestMapping("/deleteRoleUser.do")
    @ResponseBody
    public void deleteRoleUser(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "删除成功";
        try {
            int res = dao.deleteRoleUser(user);
            if(res == 0){
                code = "2";
                msg = "删除失败，数据库中没有该记录";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }
    @RequestMapping("/deleteRoleNode.do")
    @ResponseBody
    public void deleteRoleNode(HttpServletResponse response, HttpSession se, RoleUser user) {
        Map<String, Object> mapRes = new HashMap<>();
        String code = "1";
        String msg = "删除成功";
        try {
            int res = dao.deleteRoleNode(user);
            if(res == 0){
                code = "2";
                msg = "删除失败，数据库中没有该记录";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "2";
            msg = e.getMessage();
        } finally {
            mapRes.put("code", code);
            mapRes.put("msg", msg);
            responseJson(response, mapRes);
        }

    }

    public void responseJson(HttpServletResponse response, Map<String, Object> mapRes) {
        try {
            /*设置编码格式，返回结果   */
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(mGson.toJson(mapRes));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
