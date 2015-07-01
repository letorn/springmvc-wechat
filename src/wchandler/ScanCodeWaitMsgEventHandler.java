package wchandler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import model.Msg;
import model.Msg.Article;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import service.zcdh.JobFairService;
import util.HttpClient;
import util.WeChatClient;

@Service("scanCodeWaitMsgEventHandler")
public class ScanCodeWaitMsgEventHandler {

	@Resource
	private JobFairService jobFairService;

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, String scanType, String scanResult) {
		return null;
	}

	public Msg send(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, String scanType, String scanResult) {
		Msg msg = null;
		if (scanResult.contains("fairOpt")) {
			try {
				String paramStrs[] = scanResult.substring(scanResult.indexOf("?") + 1, scanResult.length()).split("&");
				Map<String, String> params = new HashMap<String, String>();
				String arr[] = null;
				for (int i = 0; i < paramStrs.length; i++) {
					arr = paramStrs[i].split("=");
					params.put(arr[0], arr[1]);
				}
				JSONObject responseJSONObject = HttpClient.get("http://172.18.100.50/ent/fair/fairOpt!getEntInfoForWeChat.action").data("entId", params.get("entId"), "fairId", params.get("fairId")).toJSONObject();
				String title = responseJSONObject.getString("entName");
				String description = "";
				String picUrl = "http://www.zcdhjob.com/weixin/images/65.jpg";
				String url = scanResult + "&openId=" + fromUserName;
				if (jobFairService.getJobhunteQuickLoginAccount(fromUserName) == null) {
					title = "你好，需要先注册或者绑定";
					picUrl = "http://www.zcdhjob.com/weixin/images/65.jpg";
					url = WeChatClient.getOAuthUrl("http://www.zcdhjob.com/weixin/service-to-manage.jsp", true, "STATE");
				}
				msg = new Msg(fromUserName, toUserName, System.currentTimeMillis() / 1000, "news", new Article(title, description, picUrl, url));
			} catch (Exception e) {
				e.printStackTrace();
				msg = new Msg(fromUserName, toUserName, System.currentTimeMillis() / 1000, "text", "对不起，版本不兼容");
			}
		} else {
			msg = new Msg(fromUserName, toUserName, System.currentTimeMillis() / 1000, "text", "你好，请扫描企业二维码");
		}
		return msg;
	}

}
