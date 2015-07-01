package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("subscribeEventHandler")
public class SubscribeEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey) {
		return null;
	}

	public Msg subscribe(String toUserName, String fromUserName, Long createTime, String msgType, String event) {
		return null;
	}

	public Msg qrscene(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, String ticket) {
		return null;
	}

}
