package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("locationHandler")
public class LocationHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, Double locationX, Double locationY, Integer scale, String label, String msgId) {
		return null;
	}
}
