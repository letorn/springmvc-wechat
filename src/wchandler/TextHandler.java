package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("textHandler")
public class TextHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String content, String msgId) {
		return null;
	}

	public Msg letorn(String toUserName, String fromUserName, Long createTime, String msgType, String content, String msgId) {
		Msg msg = new Msg();
		msg.setToUserName(fromUserName);
		msg.setFromUserName(toUserName);
		msg.setCreateTime(System.currentTimeMillis());
		msg.setMsgType("text");
		msg.setContent("textHandler letorn ...");
		return msg;
	}

}
