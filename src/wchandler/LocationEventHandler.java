package wchandler;

import model.Msg;

import org.springframework.stereotype.Service;

@Service("locationEventHandler")
public class LocationEventHandler {

	public Msg service(String toUserName, String fromUserName, Long createTime, String msgType, String event, Double latitude, Double longitude, Double precision) {
		return null;
	}
}
