package controller.platform;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.platform.AdminService;

@Controller
@RequestMapping("platform/")
public class AdminController {

	@Resource
	private AdminService adminService;

	@RequestMapping("login.do")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, String name, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)) {
			Admin admin = adminService.getAdmin(name, password);
			if (admin != null) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);
				resultMap.put("success", true);
			}
		}
		return resultMap;
	}

	@RequestMapping("initStack.do")
	@ResponseBody
	public Map<String, Object> initStack() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", adminService.initStack());
		return resultMap;
	}
}
