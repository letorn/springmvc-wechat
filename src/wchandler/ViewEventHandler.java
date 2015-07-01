package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("viewEventHandler")
public class ViewEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		Msg msg = new Msg();
		msg.setToUserName(fromUserName);
		msg.setFromUserName(toUserName);
		msg.setCreateTime(System.currentTimeMillis());
		msg.setMsgType("text");
		msg.setContent("textHandler letorn ...");
		return msg;
	}

}
