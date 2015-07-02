package init;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import util.WeChatClient;

@Component
public class Scheduler {

	@Scheduled(fixedDelay = 1000 * 60 * 50)
	public void updateWeChatToken() {
		if (WeChatClient.initAccessToken())
			WeChatClient.initTicket();
	}

}
