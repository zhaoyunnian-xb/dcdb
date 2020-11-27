package com.bm.index.model;

public class DcdbNdrwYdnr {
    private String id;

    private String ndid;

    private String czsj;

    private String ydnrmc;

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

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj == null ? null : czsj.trim();
    }

    public String getYdnrmc() {
        return ydnrmc;
    }

    public void setYdnrmc(String ydnrmc) {
        this.ydnrmc = ydnrmc == null ? null : ydnrmc.trim();
    }
}