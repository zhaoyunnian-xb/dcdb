package com.bm.index.dao.source1;

import com.bm.index.model.RoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleUserDao {

    List<RoleUser> selectRoleList(RoleUser example);

    List<RoleUser> selectRoleUserList(RoleUser example);

    List<RoleUser> selectNodeStatus(RoleUser example);

    List<RoleUser> selectUserByNodeId(RoleUser example);


    int insertRole(RoleUser record);

    int insertUser(RoleUser record);

    int insertNodeStatus(RoleUser record);

    int deleteRole(RoleUser example);

    int deleteRoleUser(RoleUser example);

    int deleteRoleNode(RoleUser example);

    int updateByExampleSelective(@Param("record") RoleUser record);




}