package controller.platform;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.WeChatClient;

@Controller
@RequestMapping("platform/baseinfo/")
public class BaseInfoController {

	@RequestMapping("data.do")
	@ResponseBody
	public Map<String, Object> data() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("appId", WeChatClient.getAppId());
		data.put("appSecret", WeChatClient.getAppSecret());
		data.put("token", WeChatClient.getToken());
		data.put("accessToken", WeChatClient.getAccessToken());
		data.put("ticket", WeChatClient.getTicket());
		resultMap.put("data", data);
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("initAccessToken.do")
	@ResponseBody
	public Map<String, Object> initAccessToken() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", WeChatClient.initAccessToken());
		return resultMap;
	}

	@RequestMapping("initTicket.do")
	@ResponseBody
	public Map<String, Object> initTicket() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", WeChatClient.initTicket());
		return resultMap;
	}

}
