package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test/")
public class TestController {

	@RequestMapping("dao.do")
	@ResponseBody
	public Map<String, Object> dao() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}

	@RequestMapping("forward.do")
	public String forward() {
		return "forward:www.baidu.com";
	}

	@RequestMapping("redirect.do")
	public String redirect() {
		return "redirect:http://www.baidu.com";
	}

	@RequestMapping("setSession.do")
	@ResponseBody
	public Map<String, Object> setSession(HttpServletRequest request, String name) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		session.setAttribute("name", name);
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("getSession.do")
	@ResponseBody
	public Map<String, Object> getSession(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		resultMap.put("name", name);
		resultMap.put("success", true);
		return resultMap;
	}

}
