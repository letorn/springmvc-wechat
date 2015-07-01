package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("picPhotoOrAlbumEventHandler")
public class PicPhotoOrAlbumEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, String eventKey, Integer count, String[] picMd5Sums) {
		return null;
	}

}
