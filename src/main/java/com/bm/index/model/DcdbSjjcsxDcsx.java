package com.bm.index.model;

/**
 * 上级决策事项—督察事项记录表
 * 
 * @author wcyong
 * 
 * @date 2019-04-12
 */
public class DcdbSjjcsxDcsx {
    /**
     * 主责部门名称
     */
    private String id;

    /**
     * 主责部门名称
     */
    private String djid;

    /**
     * 主责部门名称
     */
    private String dcsxmc;

    /**
     * 主责部门名称
     */
    private String dczqtype;

    /**
     * 主责部门名称
     */
    private String dczq;

    /**
     * 主责部门名称
     */
    private String qtbmid;

    /**
     * 主责部门名称
     */
    private String qtbmmc;

    /**
     * 主责部门名称
     */
    private String zzbmid;

    /**
     * 主责部门名称
     */
    private String zzbmmc;

    private String isfq;

    public String getIsfq() {
        return isfq;
    }

    public void setIsfq(String isfq) {
        this.isfq = isfq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDjid() {
        return djid;
    }

    public void setDjid(String djid) {
        this.djid = djid == null ? null : djid.trim();
    }

    public String getDcsxmc() {
        return dcsxmc;
    }

    public void setDcsxmc(String dcsxmc) {
        this.dcsxmc = dcsxmc == null ? null : dcsxmc.trim();
    }

    public String getDczqtype() {
        return dczqtype;
    }

    public void setDczqtype(String dczqtype) {
        this.dczqtype = dczqtype == null ? null : dczqtype.trim();
    }

    public String getDczq() {
        return dczq;
    }

    public void setDczq(String dczq) {
        this.dczq = dczq == null ? null : dczq.trim();
    }

    public String getQtbmid() {
        return qtbmid;
    }

    public void setQtbmid(String qtbmid) {
        this.qtbmid = qtbmid == null ? null : qtbmid.trim();
    }

    public String getQtbmmc() {
        return qtbmmc;
    }

    public void setQtbmmc(String qtbmmc) {
        this.qtbmmc = qtbmmc == null ? null : qtbmmc.trim();
    }

    public String getZzbmid() {
        return zzbmid;
    }

    public void setZzbmid(String zzbmid) {
        this.zzbmid = zzbmid == null ? null : zzbmid.trim();
    }

    public String getZzbmmc() {
        return zzbmmc;
    }

    public void setZzbmmc(String zzbmmc) {
        this.zzbmmc = zzbmmc == null ? null : zzbmmc.trim();
    }
}