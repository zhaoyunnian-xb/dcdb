package com.bm.index.model;

import java.util.List;

public class Flow {
    private String nodeId;				   //节点id
    private String nodeName	;			//节点名称
    private String approveTime;          //审批时间
    private String userName;          //审批人
    private String userId;
    private String nodeId1;
    private String id;
    private String istf;
    private String wh;
    private List<Flow> children;
    private String cbbmmc;

    public String getCbbmmc() {
        return cbbmmc;
    }

    public void setCbbmmc(String cbbmmc) {
        this.cbbmmc = cbbmmc;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

    public List<Flow> getChildren() {
        return children;
    }

    public void setChildren(List<Flow> children) {
        this.children = children;
    }
    public String getIstf() {
        return istf;
    }

    public void setIstf(String istf) {
        this.istf = istf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId1() {
        return nodeId1;
    }

    public void setNodeId1(String nodeId1) {
        this.nodeId1 = nodeId1;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


}
