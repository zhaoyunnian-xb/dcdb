package com.bm.index.model;

public class DcdbHfx {
    private String id;

    private String dbdbh;//督办单编号

    private String dbsx;//督办事项

    private String hfr;//回复人

    private String hfnr;//回复内容

    private String bmfzryj;//部门负责人意见

    private String nodeid;//流程id

    private String nodename;//流程名称

    private String jdjmc;//督办案件名称

    private String ReadOnly;// 0是可编辑,1是只读

    private String flag;

    private String isReback; //是否退回

    private String dbsyj; //督办室意见

    private String username;//当前操作人

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDbsyj() {
        return dbsyj;
    }

    public void setDbsyj(String dbsyj) {
        this.dbsyj = dbsyj;
    }

    public String getIsReback() {
        return isReback;
    }

    public void setIsReback(String isReback) {
        this.isReback = isReback;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getJdjmc() {
        return jdjmc;
    }

    public void setJdjmc(String jdjmc) {
        this.jdjmc = jdjmc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDbdbh() {
        return dbdbh;
    }

    public void setDbdbh(String dbdbh) {
        this.dbdbh = dbdbh == null ? null : dbdbh.trim();
    }

    public String getDbsx() {
        return dbsx;
    }

    public void setDbsx(String dbsx) {
        this.dbsx = dbsx == null ? null : dbsx.trim();
    }

    public String getHfr() {
        return hfr;
    }

    public void setHfr(String hfr) {
        this.hfr = hfr == null ? null : hfr.trim();
    }

    public String getHfnr() {
        return hfnr;
    }

    public void setHfnr(String hfnr) {
        this.hfnr = hfnr == null ? null : hfnr.trim();
    }

    public String getBmfzryj() {
        return bmfzryj;
    }

    public void setBmfzryj(String bmfzryj) {
        this.bmfzryj = bmfzryj == null ? null : bmfzryj.trim();
    }

    public String getReadOnly() {
        return ReadOnly;
    }

    public void setReadOnly(String readOnly) {
        ReadOnly = readOnly;
    }
}