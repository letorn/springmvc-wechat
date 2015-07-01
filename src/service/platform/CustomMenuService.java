package service.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Menu;

import org.springframework.stereotype.Service;

import util.WeChatClient;
import dao.MenuDao;

@Service("customMenuService")
public class CustomMenuService {

	@Resource
	private MenuDao menuDao;

	public List<Menu> getMenus() {
		return menuDao.findAll();
	}

	public Boolean save(Menu menu) {
		if (menu.getId() == null)
			return menuDao.add(menu);
		else
			return menuDao.update(menu);
	}

	public Boolean delete(Integer id) {
		return menuDao.linkedDelete(id);
	}

	public Boolean initMenu() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("button", toListMap(menuDao.findByStatus(1)));
		return WeChatClient.initMenu(map);
	}

	private List<Map<String, Object>> toListMap(List<Menu> menus) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Menu menu : menus) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", menu.getName());
			if (menu.getValue() == null)
				menu.setValue("");
			if ("dir".equals(menu.getType())) {
				map.put("sub_button", menu.getSubs() == null ? "[]" : toListMap(menu.getSubs()));
			} else if ("click".equals(menu.getType())) {
				map.put("type", "click");
				map.put("key", menu.getValue());
			} else if ("view".equals(menu.getType())) {
				map.put("type", "view");
				map.put("url", menu.getValue());
			} else if ("scancode_push".equals(menu.getType())) {
				map.put("type", "scancode_push");
				map.put("key", menu.getValue());
			} else if ("scancode_waitmsg".equals(menu.getType())) {
				map.put("type", "scancode_waitmsg");
				map.put("key", menu.getValue());
			} else if ("pic_sysphoto".equals(menu.getType())) {
				map.put("type", "pic_sysphoto");
				map.put("key", menu.getValue());
			} else if ("pic_weixin".equals(menu.getType())) {
				map.put("type", "pic_weixin");
				map.put("key", menu.getValue());
			} else if ("pic_photo_or_album".equals(menu.getType())) {
				map.put("type", "pic_photo_or_album");
				map.put("key", menu.getValue());
			} else if ("location_select".equals(menu.getType())) {
				map.put("type", "location_select");
				map.put("key", menu.getValue());
			} else if ("media_id".equals(menu.getType())) {
				map.put("type", "media_id");
				map.put("media_id", menu.getValue());
			} else if ("view_limited".equals(menu.getType())) {
				map.put("type", "view_limited");
				map.put("media_id", menu.getValue());
			} else if ("oauth_base".equals(menu.getType())) {
				map.put("type", "view");
				if (menu.getValue().contains(",")) {
					String[] arr = menu.getValue().split(",", 2);
					map.put("url", WeChatClient.getOAuthUrl(arr[0], false, arr[1]));
				} else {
					map.put("url", WeChatClient.getOAuthUrl(menu.getValue(), false, null));
				}
			} else if ("oauth_info".equals(menu.getType())) {
				map.put("type", "view");
				if (menu.getValue().contains(",")) {
					String[] arr = menu.getValue().split(",", 2);
					map.put("url", WeChatClient.getOAuthUrl(arr[0], true, arr[1]));
				} else {
					map.put("url", WeChatClient.getOAuthUrl(menu.getValue(), true, null));
				}
			}
			list.add(map);
		}
		return list;
	}
}
