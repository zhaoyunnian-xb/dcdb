package com.bm.index.model;

public class DcdbDjlbDbParam extends DcdbDjlbDb {
    private String psjmc;
    private String psrq;
    private String swrq;
    //private String pslx;
    //当前状态，前台展示用
    private String status;
    //基本信息表创建期限
    private String cbqx;

    private String zxdbh;

    //流程类型名称，前台展示
    private String lclxmc;

    private String psnr;//检察长批示内容（统计详情）

    private String  blqk;//检察长批示办理情况（统计详情）

    public String getPsnr() {
        return psnr;
    }

    public void setPsnr(String psnr) {
        this.psnr = psnr;
    }

    public String getBlqk() {
        return blqk;
    }

    public void setBlqk(String blqk) {
        this.blqk = blqk;
    }

    public String getLclxmc() {
        return lclxmc;
    }

    public void setLclxmc(String lclxmc) {
        this.lclxmc = lclxmc;
    }

    public String getZxdbh() {
        return zxdbh;
    }

    public void setZxdbh(String zxdbh) {
        this.zxdbh = zxdbh;
    }

    /* private String isfa;
    private String lclx;

    @Override
    public String getLclx() {
        return lclx;
    }

    @Override
    public void setLclx(String lclx) {
        this.lclx = lclx;
    }

    @Override
    public String getIsfa() {
        return isfa;
    }

    @Override
    public void setIsfa(String isfa) {
        this.isfa = isfa;
    }*/

    public String getCbqx() {
        return cbqx;
    }

    public void setCbqx(String cbqx) {
        this.cbqx = cbqx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


   /* public String getPslx() {
        return pslx;
    }

    public void setPslx(String pslx) {
        this.pslx = pslx;
    }*/

    public String getPsjmc() {
        return psjmc;
    }

    public void setPsjmc(String psjmc) {
        this.psjmc = psjmc;
    }

    public String getPsrq() {
        return psrq;
    }

    public void setPsrq(String psrq) {
        this.psrq = psrq;
    }

    public String getSwrq() {
        return swrq;
    }

    public void setSwrq(String swrq) {
        this.swrq = swrq;
    }
}
