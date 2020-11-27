package com.bm.index.model;

public class DcdbKHPZ {
    private String  yjid; //ID  审批任务目标（考核）ID
    private String  bmfzryj; //部门负责人审批意见
    private String  bgsyj; //办公室意见
    private String  bgsfzryj;//办公室负责人意见
    private String  bmmbfzryj;//目标：部门负责人审批意见
    private String  bgsmbyj;//目标：办公室意见审批意见
    private String  bgsldmbyj;//目标：办公室领导意见审批意见
    private String currentuserid; //当前操作人ID
    private String preuserid ;//上一步操作人ID
    private String nextuserid ;//下一步操作人ID
    private String zt; //流转状态
    private String bmfzrqm;//部门负责人签名
    private String bmfzrrq;//部门负责人签名日期
    private String bgsqm;//办公室签名
    private String bgsrq;//办公室签名日期
    private String bgsfzrqm;//办公室领导签名
    private String bgsfzrrq;//办公室领导签名日期


    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getYjid() {
        return yjid;
    }

    public void setYjid(String yjid) {
        this.yjid = yjid;
    }

    public String getBmfzryj() {
        return bmfzryj;
    }

    public void setBmfzryj(String bmfzryj) {
        this.bmfzryj = bmfzryj;
    }

    public String getBgsyj() {
        return bgsyj;
    }

    public void setBgsyj(String bgsyj) {
        this.bgsyj = bgsyj;
    }

    public String getBgsfzryj() {
        return bgsfzryj;
    }

    public void setBgsfzryj(String bgsfzryj) {
        this.bgsfzryj = bgsfzryj;
    }

    public String getBmmbfzryj() {
        return bmmbfzryj;
    }

    public void setBmmbfzryj(String bmmbfzryj) {
        this.bmmbfzryj = bmmbfzryj;
    }

    public String getBgsmbyj() {
        return bgsmbyj;
    }

    public void setBgsmbyj(String bgsmbyj) {
        this.bgsmbyj = bgsmbyj;
    }

    public String getBgsldmbyj() {
        return bgsldmbyj;
    }

    public void setBgsldmbyj(String bgsldmbyj) {
        this.bgsldmbyj = bgsldmbyj;
    }

    public String getCurrentuserid() {
        return currentuserid;
    }

    public void setCurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public String getPreuserid() {
        return preuserid;
    }

    public void setPreuserid(String preuserid) {
        this.preuserid = preuserid;
    }

    public String getNextuserid() {
        return nextuserid;
    }

    public void setNextuserid(String nextuserid) {
        this.nextuserid = nextuserid;
    }

    public String getBmfzrqm() {
        return bmfzrqm;
    }

    public void setBmfzrqm(String bmfzrqm) {
        this.bmfzrqm = bmfzrqm;
    }

    public String getBmfzrrq() {
        return bmfzrrq;
    }

    public void setBmfzrrq(String bmfzrrq) {
        this.bmfzrrq = bmfzrrq;
    }

    public String getBgsqm() {
        return bgsqm;
    }

    public void setBgsqm(String bgsqm) {
        this.bgsqm = bgsqm;
    }

    public String getBgsrq() {
        return bgsrq;
    }

    public void setBgsrq(String bgsrq) {
        this.bgsrq = bgsrq;
    }

    public String getBgsfzrqm() {
        return bgsfzrqm;
    }

    public void setBgsfzrqm(String bgsfzrqm) {
        this.bgsfzrqm = bgsfzrqm;
    }

    public String getBgsfzrrq() {
        return bgsfzrrq;
    }

    public void setBgsfzrrq(String bgsfzrrq) {
        this.bgsfzrrq = bgsfzrrq;
    }
}
