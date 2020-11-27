package com.bm.index.model;

public class DcdbNdrw {
    private String id;

    private String nd;

    private String ndrwmc;

    private String czr;

    private String czsj;

    private String czzt;

    private String cbrid;

    private String cbbmid;


    private String isBgs;

    private String cbr;

    private String ssbm;

    private String username;

    private String userid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr;
    }

    public String getSsbm() {
        return ssbm;
    }

    public void setSsbm(String ssbm) {
        this.ssbm = ssbm;
    }

    public String getIsBgs() {
        return isBgs;
    }

    public void setIsBgs(String isBgs) {
        this.isBgs = isBgs;
    }

    public String getCbrid() {
        return cbrid;
    }

    public void setCbrid(String cbrid) {
        this.cbrid = cbrid;
    }

    public String getCbbmid() {
        return cbbmid;
    }

    public void setCbbmid(String cbbmid) {
        this.cbbmid = cbbmid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd == null ? null : nd.trim();
    }

    public String getNdrwmc() {
        return ndrwmc;
    }

    public void setNdrwmc(String ndrwmc) {
        this.ndrwmc = ndrwmc == null ? null : ndrwmc.trim();
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr == null ? null : czr.trim();
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj == null ? null : czsj.trim();
    }

    public String getCzzt() {
        return czzt;
    }

    public void setCzzt(String czzt) {
        this.czzt = czzt == null ? null : czzt.trim();
    }
}