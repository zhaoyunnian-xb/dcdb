package com.bm.index.model;

import java.util.Date;

public class DcdbProjectFile {
    private String id;

    private String filename;

    private Date filedate;

    private String filesize;

    private String ywtype;

    private String bmrwid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Date getFiledate() {
        return filedate;
    }

    public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize == null ? null : filesize.trim();
    }

    public String getYwtype() {
        return ywtype;
    }

    public void setYwtype(String ywtype) {
        this.ywtype = ywtype == null ? null : ywtype.trim();
    }

    public String getBmrwid() {
        return bmrwid;
    }

    public void setBmrwid(String bmrwid) {
        this.bmrwid = bmrwid == null ? null : bmrwid.trim();
    }
}