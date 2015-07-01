package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("picSysPhotoEventHandler")
public class PicSysPhotoEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, Integer count, String[] picMd5Sums) {
		return null;
	}

}
