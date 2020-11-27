package com.bm.index.model;

public class DcdbDb {
    private String id;

    private String bh;

    private String jdjmc;

    private String ajzt;

    private String ajztname;

    private String username;

    private String userid;

    private String spsj;

    private String nodeid;

    private String nodename;

    private String isReback; //是否退回

    private String gddate; //归档有效期

    public String getGddate() {
        return gddate;
    }

    public void setGddate(String gddate) {
        this.gddate = gddate;
    }

    public String getAjztname() {
        return ajztname;
    }

    public void setAjztname(String ajztname) {
        this.ajztname = ajztname;
    }

    public String getIsReback() {
        return isReback;
    }

    public void setIsReback(String isReback) {
        this.isReback = isReback;
    }
    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    public String getJdjmc() {
        return jdjmc;
    }

    public void setJdjmc(String jdjmc) {
        this.jdjmc = jdjmc == null ? null : jdjmc.trim();
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt == null ? null : ajzt.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getSpsj() {
        return spsj;
    }

    public void setSpsj(String spsj) {
        this.spsj = spsj;
    }
}