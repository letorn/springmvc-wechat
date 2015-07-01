package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("linkHandler")
public class LinkHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String title, String description, String url, String msgId) {
		return null;
	}
}
