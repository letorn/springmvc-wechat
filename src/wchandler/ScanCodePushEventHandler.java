package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("scanCodePushEventHandler")
public class ScanCodePushEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, String scanType, String scanResult) {
		return null;
	}

}
