package com.bm.index.util;

import com.bm.index.model.Menu;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder
{

	List<Menu> Menus = new ArrayList<>();

	public String buildTree(List<Menu> Menus) {

		TreeBuilder treeBuilder = new TreeBuilder(Menus);

		return treeBuilder.buildJSONTree();
	}

	public TreeBuilder() {
	}

	public TreeBuilder(List<Menu> Menus) {
		super();
		this.Menus = Menus;
	}

	// 构建JSON树形结构
	public String buildJSONTree() {
		List<Menu> MenuTree = buildTree();
		  //设置过滤json格式
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
                public boolean apply(Object object, String fieldName, Object fieldValue) {
                if(fieldValue instanceof List){
                    List<Object> list = (List<Object>) fieldValue;
                    if (list.size()==0) {
                        return true;
                    }
                }
                return null == fieldValue;
                }
        };
        jsonConfig.setJsonPropertyFilter(filter);
		JSONArray jsonArray = JSONArray.fromObject(MenuTree,jsonConfig);
		return jsonArray.toString();
	}

	// 构建树形结构
	public List<Menu> buildTree() {
		List<Menu> treeMenus = new ArrayList<>();
		List<Menu> rootMenus = getRootMenus();
		for (Menu rootMenu : rootMenus) {
			buildChildMenus(rootMenu);
			treeMenus.add(rootMenu);
		}
		return treeMenus;
	}

	// 递归子节点
	public void buildChildMenus(Menu Menu) {
		List<Menu> children = getChildMenus(Menu);
		if (!children.isEmpty()) {
			for (Menu child : children) {
				buildChildMenus(child);
			}
			Menu.setChildren(children);
		}
	}

	// 获取父节点下所有的子节点
	public List<Menu> getChildMenus(Menu pMenu) {
		List<Menu> childMenus = new ArrayList<>();
		for (Menu n : Menus) {
			if (pMenu.getId().equals(n.getPid())) {
				childMenus.add(n);
			}
		}
		return childMenus;
	}

	// 判断是否为根节点
	public boolean rootMenu(Menu Menu) {
		boolean isRootMenu = true;
		for (Menu n : Menus) {
			if (n.getId().equals(Menu.getPid())) {
				isRootMenu = false;
				break;
			}
		}
		return isRootMenu;
	}

	// 获取集合中所有的根节点
	public List<Menu> getRootMenus() {

		List<Menu> rootMenus = new ArrayList<>();
		for (Menu n : Menus) {
			if (rootMenu(n)) {
				rootMenus.add(n);
			}
		}
		return rootMenus;
	}
}