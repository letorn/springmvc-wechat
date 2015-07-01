package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("videoHandler")
public class VideoHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String mediaId, String thumbMediaId, String msgId) {
		return null;
	}
}
