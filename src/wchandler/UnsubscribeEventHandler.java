package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("unsubscribeEventHandler")
public class UnsubscribeEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		return null;
	}

	public Msg unsubscribe(String toUserName, String fromUserName, Long createTime, String msgType, String event) {
		return null;
	}

}
