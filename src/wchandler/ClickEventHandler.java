package wchandler;

import java.util.ArrayList;
import java.util.List;

import model.Msg;
import model.Msg.Article;

import org.springframework.stereotype.Service;

import util.WeChatClient;

@Service("clickEventHandler")
public class ClickEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		return null;
	}

	public Msg oauth(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		Msg msg = new Msg();
		msg.setToUserName(fromUserName);
		msg.setFromUserName(toUserName);
		msg.setMsgType("news");
		List<Article> articles = new ArrayList<Msg.Article>();
		articles.add(new Article("openid", "snsapi_base", "http://www.zcdhjob.com/pages/20150529/assets/img/banner_c640.png", WeChatClient.getOAuthUrl("http://letorn.aliapp.com/egame/playing.do", false, null)));
		articles.add(new Article("userinfo", "snsapi_userinfo", "http://www.zcdhjob.com/pages/20150529/assets/img/banner_c640.png", WeChatClient.getOAuthUrl("http://letorn.aliapp.com/egame/playing.do", true, null)));
		msg.setArticles(articles);
		return msg;
	}

	public Msg egame(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		Msg msg = new Msg();
		msg.setToUserName(fromUserName);
		msg.setFromUserName(toUserName);
		msg.setMsgType("news");
		List<Article> articles = new ArrayList<Msg.Article>();
		articles.add(new Article("静静与超超", "谁才是真正的女神杀手，来决一高下吧！", "http://letorn.aliapp.com/egame/img/a.png", WeChatClient.getOAuthUrl("http://letorn.aliapp.com/egame/index.do", true, "-1")));
		msg.setArticles(articles);
		return msg;
	}

}
