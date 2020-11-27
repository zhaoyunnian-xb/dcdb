package com.bm.index.model;

public class DcdbSpyj {
    private String id;

    private String bllx;

    private String splx;

    private String spr;

    private String sprq;

    private String spyj;

    private String spbz;

    private String blqk;

    public String getBlqk() {
        return blqk;
    }

    public void setBlqk(String blqk) {
        this.blqk = blqk;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }

    public String getSpbz() {
        return spbz;
    }

    public void setSpbz(String spbz) {
        this.spbz = spbz == null ? null : spbz.trim();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBllx() {
        return bllx;
    }

    public void setBllx(String bllx) {
        this.bllx = bllx == null ? null : bllx.trim();
    }

    public String getSplx() {
        return splx;
    }

    public void setSplx(String splx) {
        this.splx = splx == null ? null : splx.trim();
    }

    public String getSpr() {
        return spr;
    }

    public void setSpr(String spr) {
        this.spr = spr == null ? null : spr.trim();
    }

    public String getSprq() {
        return sprq;
    }

    public void setSprq(String sprq) {
        this.sprq = sprq == null ? null : sprq.trim();
    }
}