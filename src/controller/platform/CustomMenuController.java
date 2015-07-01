package controller.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.platform.CustomMenuService;

@Controller
@RequestMapping("platform/custommenu/")
public class CustomMenuController {

	@Resource
	private CustomMenuService customMenuService;

	@RequestMapping("data.do")
	@ResponseBody
	public Map<String, Object> data() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<Menu> menus = customMenuService.getMenus();
		for (Menu menu : menus) {
			Map<String, Object> d = new HashMap<String, Object>();
			d.put("id", menu.getId());
			d.put("name", menu.getName());
			d.put("type", menu.getType());
			d.put("value", menu.getValue());
			d.put("status", menu.getStatus());
			d.put("num", menu.getNum());
			d.put("parentId", menu.getParentId());
			if (!"dir".equals(menu.getType())) {
				d.put("leaf", true);
			} else {
				d.put("expanded", true);
				List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
				if (menu.getSubs() != null)
					for (Menu sub : menu.getSubs()) {
						Map<String, Object> child = new HashMap<String, Object>();
						child.put("id", sub.getId());
						child.put("name", sub.getName());
						child.put("type", sub.getType());
						child.put("value", sub.getValue());
						child.put("status", sub.getStatus());
						child.put("num", sub.getNum());
						child.put("parentId", sub.getParentId());
						child.put("leaf", true);
						children.add(child);
					}
				d.put("children", children);
			}
			data.add(d);
		}
		resultMap.put("data", data);
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("sync.do")
	@ResponseBody
	public Map<String, Object> sync() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", customMenuService.initMenu());
		return resultMap;
	}

	@RequestMapping("save.do")
	@ResponseBody
	public Map<String, Object> save(Menu menu) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (menu != null) {
			if (menu.getParentId() == null)
				menu.setParentId(-1);
			if (customMenuService.save(menu))
				resultMap.put("success", true);
			else
				resultMap.put("success", false);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("delete.do")
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (customMenuService.delete(id)) {
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

}
