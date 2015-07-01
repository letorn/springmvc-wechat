package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Menu;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("menuDao")
public class MenuDao extends Store<Menu> {

	public List<Menu> findAll() {
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> subs = new ArrayList<Menu>();
		for (Integer id : Stack.menuIdMap.keySet()) {
			Menu menu = Stack.menuIdMap.get(id);
			if (menu.getParentId() == -1) {
				if (menu.getSubs() != null)
					menu.getSubs().clear();
				menus.add(menu);
			} else {
				subs.add(menu);
			}
		}
		for (Menu sub : subs) {
			Menu parent = Stack.menuIdMap.get(sub.getParentId());
			if (parent != null) {
				if (parent.getSubs() == null)
					parent.setSubs(new ArrayList<Menu>());
				parent.getSubs().add(sub);
			}
		}
		return sorter(menus);
	}

	public List<Menu> findByStatus(Integer status) {
		List<Menu> menus = new ArrayList<Menu>();
		List<Menu> subs = new ArrayList<Menu>();
		for (Integer id : Stack.menuIdMap.keySet()) {
			Menu menu = Stack.menuIdMap.get(id);
			if (menu.getStatus() == status.intValue())
				if (menu.getParentId() == -1) {
					if (menu.getSubs() != null)
						menu.getSubs().clear();
					menus.add(menu);
				} else {
					subs.add(menu);
				}
		}
		for (Menu sub : subs) {
			Menu parent = Stack.menuIdMap.get(sub.getParentId());
			if (parent != null) {
				if (parent.getSubs() == null)
					parent.setSubs(new ArrayList<Menu>());
				parent.getSubs().add(sub);
			}
		}
		return sorter(menus);
	}

	public Boolean linkedDelete(Integer id) {
		if (delete("delete from wechat_menu where id=? or parent_id=?", new Object[] { id, id })) {
			for (Integer menuId : Stack.menuIdMap.keySet()) {
				Menu menu = Stack.menuIdMap.get(menuId);
				if (menu.getId() == id || menu.getParentId() == id)
					Stack.menuIdMap.remove(id);
			}
			return true;
		}
		return false;
	}

	public Boolean add(Menu menu) {
		if (super.add(menu)) {
			if (menu.getId() != null)
				Stack.menuIdMap.put(menu.getId(), menu);
			return true;
		}
		return false;
	}

	public Boolean update(Menu menu) {
		if (super.update(menu)) {
			if (menu.getId() != null)
				Stack.menuIdMap.put(menu.getId(), menu);
			return true;
		}
		return false;
	}

	private static List<Menu> sorter(List<Menu> menus) {
		if (menus != null) {
			Collections.sort(menus, new Comparator<Menu>() {
				public int compare(Menu m1, Menu m2) {
					if (m1.getNum() == null)
						return 1;
					if (m2.getNum() == null)
						return -1;
					return m1.getNum().compareTo(m2.getNum());
				}
			});
			for (Menu menu : menus) {
				if (menu.getSubs() != null)
					menu.setSubs(sorter(menu.getSubs()));
			}
		}
		return menus;
	}

}
