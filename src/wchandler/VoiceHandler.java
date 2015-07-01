package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("voiceHandler")
public class VoiceHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String mediaId, String format, String msgId) {
		return null;
	}
}
