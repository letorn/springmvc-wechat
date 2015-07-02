package service.platform;

import javax.annotation.Resource;

import model.Admin;

import org.springframework.stereotype.Service;

import dao.AdminDao;
import dao.data.Stack;

@Service("adminService")
public class AdminService {

	@Resource
	private Stack stack;
	@Resource
	private AdminDao adminDao;

	public Admin getAdmin(String name, String password) {
		return adminDao.get(name, password);
	}

	public Boolean initStack() {
		try {
			stack.init();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
