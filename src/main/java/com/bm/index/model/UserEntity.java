package com.bm.index.model;


import java.io.Serializable;

public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//用户维一标识
	private String id;
	//用户名称
	private String name;
	//密码
	private String pwd;
	//部门名称
	private String bmmc;
	//部门编码
	private String bmbm;

	private String corpId;
	private String corpName;
	//登录名称
	private String loginName;
	//单位级别
	private String orgLevCode;
	//工号
	private String gh;
	private String lineLev1Code;//所属业务条线
	private String lineLev1Name;
	//用户单位编码
	private String lineLev3Code;
	//用户单位
	private String lineLev3Name;
	private String depart_code;
	private String depart_name;
	//角色名称
	private String jsmc;
	//身份证号
	private String sfzh;
	//照片
	private byte[] zp;
	//照片存放路径
	private String imgPath;
	//是否领导
	private String positionId;
	//增加字段证书验证     ysc  2018/4/4
	private String certdn;
	//一级单位名称
	private String orgLev1Bm;
	//二级单位名称
	private String orgLev2Bm;
	//三级单位名称
	private String orgLev3Bm;

	
	private String lineLev2Code;
	

	public String getBmbm() {
		return bmbm;
	}

	public void setBmbm(String bmbm) {
		this.bmbm = bmbm;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getLineLev2Code() {
		return lineLev2Code;
	}

	public void setLineLev2Code(String lineLev2Code) {
		this.lineLev2Code = lineLev2Code;
	}

	public String getOrgLev1Bm() {
		return orgLev1Bm;
	}

	public void setOrgLev1Bm(String orgLev1Bm) {
		this.orgLev1Bm = orgLev1Bm;
	}

	public String getOrgLev2Bm() {
		return orgLev2Bm;
	}

	public void setOrgLev2Bm(String orgLev2Bm) {
		this.orgLev2Bm = orgLev2Bm;
	}

	public String getOrgLev3Bm() {
		return orgLev3Bm;
	}

	public void setOrgLev3Bm(String orgLev3Bm) {
		this.orgLev3Bm = orgLev3Bm;
	}

	public String getCertdn() {
		return certdn;
	}

	public void setCertdn(String certdn) {
		this.certdn = certdn;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getLineLev3Code() {
		return lineLev3Code;
	}

	public void setLineLev3Code(String lineLev3Code) {
		this.lineLev3Code = lineLev3Code;
	}

	public String getLineLev3Name() {
		return lineLev3Name;
	}

	public void setLineLev3Name(String lineLev3Name) {
		this.lineLev3Name = lineLev3Name;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public byte[] getZp() {
		return zp;
	}

	public void setZp(byte[] zp) {
		this.zp = zp;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getJsmc() {
		return jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrgLevCode() {
		return orgLevCode;
	}

	public void setOrgLevCode(String orgLevCode) {
		this.orgLevCode = orgLevCode;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getLineLev1Code() {
		return lineLev1Code;
	}

	public void setLineLev1Code(String lineLev1Code) {
		this.lineLev1Code = lineLev1Code;
	}

	public String getLineLev1Name() {
		return lineLev1Name;
	}

	public void setLineLev1Name(String lineLev1Name) {
		this.lineLev1Name = lineLev1Name;
	}

	public String getDepart_code() {
		return depart_code;
	}

	public void setDepart_code(String depart_code) {
		this.depart_code = depart_code;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

}
