package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("scanEventHandler")
public class ScanEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, String ticket) {
		return null;
	}

}
