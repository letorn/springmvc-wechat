package service.platform;

import javax.annotation.Resource;

import model.Admin;

import org.springframework.stereotype.Service;

import dao.AdminDao;

@Service("adminService")
public class AdminService {

	@Resource
	private AdminDao adminDao;

	public Admin getAdmin(String name, String password) {
		return adminDao.get(name, password);
	}

}
