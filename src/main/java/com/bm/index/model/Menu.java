package com.bm.index.model;

import java.util.List;

public class Menu
{

	private String id;
	private String pid;
	private String name;
	private String label;
	private boolean open;//ztree  是否打开
	private String src;
	private String zjfx;
	private String jzbh;
	private boolean spread;//layui tree 是否打开
	private List<Menu> children;
	private Menu parent;
	public Menu() {
	}

	public Menu(String id, String pid, String name) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
	}
	
	public Menu getParent()
	{
		return parent;
	}
	
	public void setParent(Menu parent)
	{
		this.parent = parent;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		
		this.id = id == null?"":id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid==null?"":pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return true;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getZjfx() {
		return zjfx;
	}

	public void setZjfx(String zjfx) {
		this.zjfx = zjfx;
	}

	public String getJzbh() {
		return jzbh;
	}

	public void setJzbh(String jzbh) {
		this.jzbh = jzbh;
	}
	
	public boolean isSpread()
	{
		return spread;
	}
	
	public void setSpread(boolean spread)
	{
		this.spread = spread;
	}
}
