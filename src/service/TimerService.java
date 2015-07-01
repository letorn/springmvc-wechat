package service;

import java.util.List;

import javax.annotation.Resource;

import model.Chatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import util.WeChatClient;
import dao.ChatterDao;

@Service("timerService")
public class TimerService {

	@Resource
	private ChatterDao chatterDao;

	@Scheduled(fixedDelay = 1000 * 60 * 50)
	public void updateWeChatToken() {
		if (WeChatClient.initAccessToken())
			WeChatClient.initTicket();
	}

	// @Scheduled(fixedDelay = 1000 * 60 * 100)
	public void updateChatter() {
		List<Chatter> chatters = chatterDao.findAll();
		for (Chatter chatter : chatters) {

		}
	}
}
