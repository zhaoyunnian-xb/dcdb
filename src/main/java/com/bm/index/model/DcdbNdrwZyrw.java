package com.bm.index.model;

public class DcdbNdrwZyrw {
    private String id;

    private String ndid;

    private String ydnrid;

    private String zrld;

    private String qtbm;

    private String zybm;

    private String phbm;

    private String zyrwmc;
//用于回显
    private String qtbm1;

    private String cjsj;

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getQtbm1() {
        return qtbm1;
    }

    public void setQtbm1(String qtbm1) {
        this.qtbm1 = qtbm1;
    }

    public String getZybm1() {
        return zybm1;
    }

    public void setZybm1(String zybm1) {
        this.zybm1 = zybm1;
    }

    public String getPhbm1() {
        return phbm1;
    }

    public void setPhbm1(String phbm1) {
        this.phbm1 = phbm1;
    }

    //用于回显
    private String zybm1;
    //用于回显
    private String phbm1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNdid() {
        return ndid;
    }

    public void setNdid(String ndid) {
        this.ndid = ndid == null ? null : ndid.trim();
    }

    public String getYdnrid() {
        return ydnrid;
    }

    public void setYdnrid(String ydnrid) {
        this.ydnrid = ydnrid == null ? null : ydnrid.trim();
    }

    public String getZrld() {
        return zrld;
    }

    public void setZrld(String zrld) {
        this.zrld = zrld == null ? null : zrld.trim();
    }

    public String getQtbm() {
        return qtbm;
    }

    public void setQtbm(String qtbm) {
        this.qtbm = qtbm == null ? null : qtbm.trim();
    }

    public String getZybm() {
        return zybm;
    }

    public void setZybm(String zybm) {
        this.zybm = zybm == null ? null : zybm.trim();
    }

    public String getPhbm() {
        return phbm;
    }

    public void setPhbm(String phbm) {
        this.phbm = phbm == null ? null : phbm.trim();
    }

    public String getZyrwmc() {
        return zyrwmc;
    }

    public void setZyrwmc(String zyrwmc) {
        this.zyrwmc = zyrwmc == null ? null : zyrwmc.trim();
    }
}