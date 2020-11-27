package com.bm.index.model;

public class DcdbSjjcsxDbzq {
    private String id;

    private String dcsxid;

    private String dczqtype;

    private String dcrq;

    private String cjrq;

    public String getCjrq() {
        return cjrq;
    }

    public void setCjrq(String cjrq) {
        this.cjrq = cjrq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDcsxid() {
        return dcsxid;
    }

    public void setDcsxid(String dcsxid) {
        this.dcsxid = dcsxid == null ? null : dcsxid.trim();
    }

    public String getDczqtype() {
        return dczqtype;
    }

    public void setDczqtype(String dczqtype) {
        this.dczqtype = dczqtype == null ? null : dczqtype.trim();
    }

    public String getDcrq() {
        return dcrq;
    }

    public void setDcrq(String dcrq) {
        this.dcrq = dcrq == null ? null : dcrq.trim();
    }
}