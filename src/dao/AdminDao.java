package dao;

import model.Admin;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("adminDao")
public class AdminDao extends Store<Admin> {

	public Admin get(String name, String password) {
		return select("select * from wechat_admin where name=? and password=?", new Object[] { name, password });
	}

}
