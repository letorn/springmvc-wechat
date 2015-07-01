package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("imageHandler")
public class ImageHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String picUrl, String mediaId, String msgId) {
		return null;
	}
}
