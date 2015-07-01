package controller.survey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import model.survey.Player;
import model.survey.PlayerScore;
import model.survey.ShareLog;
import model.survey.VisitLog;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import service.survey.EGameService;
import util.WeChatClient;

@Controller
@RequestMapping("egame/")
public class EGameController {

	@Resource
	private EGameService egameService;

	@RequestMapping("index.do")
	public String index(HttpServletRequest request, Long ownerId, Long playerId, String code, String state) {
		Player owner = egameService.getPlayer(ownerId, false);
		Player player = egameService.getPlayer(playerId, false);
		if ((owner == null || player == null) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state)) {
			try {
				ownerId = Long.valueOf(state);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (ownerId != null) {
				player = egameService.access(code, state);
				owner = ownerId == -1 ? player : egameService.getPlayer(ownerId, false);
			}
		}
		if (owner != null && player != null)
			return visit(request, owner, player, "/egame/index.jsp");
		return visit(request, owner, player, "/egame/error.jsp");
	}

	@RequestMapping("chapterList.do")
	public String chapterList(HttpServletRequest request, Long ownerId, Long playerId) {
		Player owner = egameService.getPlayer(ownerId, false);
		Player player = egameService.getPlayer(playerId, false);
		if (owner != null && player != null) {
			Integer chapterKeys = 0;
			for (int i = 0; i < egameService.getChapterCount(); i++) {
				PlayerScore playerScore = egameService.getPlayerScore(owner.getId(), player.getId(), i + 1);
				Integer score = playerScore != null && playerScore.getScore() != null ? playerScore.getScore() : 0;
				Integer heart = 0;
				if (score > 0 && score <= 20)
					heart = 1;
				else if (score > 20 && score <= 40)
					heart = 2;
				else if (score > 40 && score <= 60)
					heart = 3;
				else if (score > 60 && score <= 80)
					heart = 4;
				else if (score > 80 && score <= 100)
					heart = 5;
				else
					heart = 0;
				if (heart > 0)
					chapterKeys++;
				request.setAttribute(String.format("chapter%dScore", i + 1), score);
				request.setAttribute(String.format("chapter%dHeart", i + 1), heart);
			}
			request.setAttribute("chapterKeys", chapterKeys);
			return visit(request, owner, player, "/egame/chapterList.jsp");
		}
		return visit(request, owner, player, "/egame/error.jsp");
	}

	@RequestMapping("chapter.do")
	public String chapter(HttpServletRequest request, Long ownerId, Long playerId, Integer chapterNum) {
		Player owner = egameService.getPlayer(ownerId, false);
		Player player = egameService.getPlayer(playerId, false);
		if (owner != null && player != null && egameService.checkChapterNum(chapterNum)) {
			request.setAttribute("chapterNum", chapterNum);
			request.setAttribute("title", egameService.getChapterTitle(chapterNum));
			request.setAttribute("questions", egameService.getChapterQuestionsStr(chapterNum));
			request.setAttribute("remark", egameService.getChapterRemark(chapterNum));
			visit(request, owner, player, String.format("/egame/chapter%d.jsp", chapterNum));
			return "/egame/chapter.jsp";
		}
		return visit(request, owner, player, "/egame/error.jsp");
	}

	@RequestMapping("myArena.do")
	public String myArena(HttpServletRequest request, Long ownerId, Long playerId) {
		Player owner = egameService.getPlayer(ownerId, false);
		Player player = egameService.getPlayer(playerId, false);
		if (owner != null && player != null) {
			List<Player> players = egameService.getTops(ownerId);
			List<String> playerStrs = new ArrayList<String>();
			for (int i = 1; i < players.size(); i++) {
				playerStrs.add(String.format("{nickname: '%s', headImgUrl: '%s', score: %d, star: %d, honor: '%s', chapterKeys: %d}", players.get(i).getNickname(), players.get(i).getHeadImgUrl(), players.get(i).getScore(), players.get(i).getStar(), players.get(i).getHonor(), players.get(i).getChapterKeys()));
			}
			request.setAttribute("owner", String.format("{nickname: '%s', headImgUrl: '%s', score: %d, star: %d, honor: '%s', won: %d, drawn: %d, lost: %d}", owner.getNickname(), owner.getHeadImgUrl(), owner.getScore(), owner.getStar(), owner.getHonor(), owner.getWon(), owner.getDrawn(), owner.getLost()));
			request.setAttribute("players", String.format("[%s]", StringUtils.join(playerStrs, ",")));
			return visit(request, owner, player, "/egame/myArena.jsp");
		}
		return visit(request, owner, player, "/egame/error.jsp");
	}

	@RequestMapping("countChapterHeart.do")
	@ResponseBody
	public Map<String, Object> countChapterHeart(Long ownerId, Long playerId, Integer chapterNum, Date beginDate, Date endDate, Integer[] optionIndexes) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer score = 0;
		Integer heart = 0;
		if (egameService.existPlayer(ownerId) && egameService.existPlayer(playerId) && egameService.checkChapterNum(chapterNum) && beginDate != null && endDate != null && egameService.checkOptionIndexes(chapterNum, optionIndexes)) {
			score = egameService.countChapterScore(ownerId, playerId, chapterNum, beginDate, endDate, optionIndexes);
			if (score > 0 && score <= 20)
				heart = 1;
			else if (score > 20 && score <= 40)
				heart = 2;
			else if (score > 40 && score <= 60)
				heart = 3;
			else if (score > 60 && score <= 80)
				heart = 4;
			else if (score > 80 && score <= 100)
				heart = 5;
			else
				heart = 0;
		}
		resultMap.put("score", score);
		resultMap.put("heart", heart);
		return resultMap;
	}

	@RequestMapping("sharePage.do")
	@ResponseBody
	public Map<String, Object> sharePage(Long ownerId, Long playerId, String page, String target) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (egameService.existPlayer(ownerId) && egameService.existPlayer(playerId) && StringUtils.isNotBlank(page) && StringUtils.isNotBlank(target)) {
			resultMap.put("success", egameService.share(ownerId, playerId, page, target));
		}
		return resultMap;
	}

	@RequestMapping("platform/aggrData.do")
	@ResponseBody
	public Map<String, Object> aggrData(Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			Iterator<Player> playerIterator = egameService.aggrPlayerByDate(startDate, endDate).iterator();
			Iterator<PlayerScore> playerScoreIterator = egameService.aggrPlayerScoreByDate(startDate, endDate).iterator();
			Iterator<ShareLog> shareLogIterator = egameService.aggrShareLogByDate(startDate, endDate).iterator();
			Iterator<VisitLog> visitLogIterator = egameService.aggrVisitLogByDate(startDate, endDate).iterator();
			Player player = playerIterator.hasNext() ? playerIterator.next() : null;
			PlayerScore playerScore = playerScoreIterator.hasNext() ? playerScoreIterator.next() : null;
			ShareLog shareLog = shareLogIterator.hasNext() ? shareLogIterator.next() : null;
			VisitLog visitLog = visitLogIterator.hasNext() ? visitLogIterator.next() : null;

			Integer totalPlayer = 0;
			Integer totalPlayerScore = 0;
			Integer totalShareLog = 0;
			Integer totalVisitLog = 0;
			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for (cal.setTime(endDate); cal.getTimeInMillis() > startDate.getTime(); cal.add(Calendar.DATE, -1)) {
				Map<String, Object> d = new HashMap<String, Object>();

				d.put("date", dateFormat.format(cal.getTime()));
				if (player != null && cal.getTimeInMillis() - 1000 * 60 * 60 * 24 + 1 <= player.getCreateDate().getTime() && player.getCreateDate().getTime() < cal.getTimeInMillis()) {
					d.put("player", player.getCount());
					totalPlayer += player.getCount();
					player = playerIterator.hasNext() ? playerIterator.next() : null;
				} else {
					d.put("player", 0);
				}

				if (playerScore != null && cal.getTimeInMillis() - 1000 * 60 * 60 * 24 + 1 <= playerScore.getCreateDate().getTime() && playerScore.getCreateDate().getTime() < cal.getTimeInMillis()) {
					d.put("playerScore", playerScore.getCount());
					totalPlayerScore += playerScore.getCount();
					playerScore = playerScoreIterator.hasNext() ? playerScoreIterator.next() : null;
				} else {
					d.put("playerScore", 0);
				}

				if (shareLog != null && cal.getTimeInMillis() - 1000 * 60 * 60 * 24 + 1 <= shareLog.getCreateDate().getTime() && shareLog.getCreateDate().getTime() < cal.getTimeInMillis()) {
					d.put("shareLog", shareLog.getCount());
					totalShareLog += shareLog.getCount();
					shareLog = shareLogIterator.hasNext() ? shareLogIterator.next() : null;
				} else {
					d.put("shareLog", 0);
				}

				if (visitLog != null && cal.getTimeInMillis() - 1000 * 60 * 60 * 24 + 1 <= visitLog.getCreateDate().getTime() && visitLog.getCreateDate().getTime() < cal.getTimeInMillis()) {
					d.put("visitLog", visitLog.getCount());
					totalVisitLog += visitLog.getCount();
					visitLog = visitLogIterator.hasNext() ? visitLogIterator.next() : null;
				} else {
					d.put("visitLog", 0);
				}

				data.add(d);
			}

			Map<String, Object> d = new HashMap<String, Object>();
			d.put("date", String.format("%s ~ %s", dateFormat.format(startDate), dateFormat.format(endDate)));
			d.put("player", totalPlayer);
			d.put("playerScore", totalPlayerScore);
			d.put("shareLog", totalShareLog);
			d.put("visitLog", totalVisitLog);
			data.add(0, d);

			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/players.do")
	@ResponseBody
	public Map<String, Object> players(Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<Player> players = egameService.findPlayer(startDate, endDate);
			for (Player player : players) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("nickname", player.getNickname());
				d.put("datetime", player.getCreateDate());
				d.put("gender", player.getGender() == 1 ? "男" : "女");
				d.put("score", player.getScore());
				d.put("chapterKeys", player.getChapterKeys());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/aggrPlayerScores.do")
	@ResponseBody
	public Map<String, Object> aggrPlayerScores(Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PlayerScore> playerScores = egameService.findPlayerScore(startDate, endDate);
			for (PlayerScore playerScore : playerScores) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("playerId", playerScore.getPlayerId());
				d.put("nickname", playerScore.getPlayerNickname());
				d.put("count", playerScore.getCount());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/playerScores.do")
	@ResponseBody
	public Map<String, Object> playerScores(Long playerId, Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (playerId != null && startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<PlayerScore> playerScores = egameService.findPlayerScore(playerId, startDate, endDate);
			for (PlayerScore playerScore : playerScores) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("datetime", playerScore.getCreateDate());
				d.put("chapter", playerScore.getChapterNum());
				d.put("score", playerScore.getScore());
				d.put("options", playerScore.getOptionLetters());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/aggrShareLogs.do")
	@ResponseBody
	public Map<String, Object> aggrShareLogs(Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ShareLog> shareLogs = egameService.findShareLog(startDate, endDate);
			for (ShareLog shareLog : shareLogs) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("playerId", shareLog.getPlayerId());
				d.put("nickname", shareLog.getPlayerNickname());
				d.put("count", shareLog.getCount());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/shareLogs.do")
	@ResponseBody
	public Map<String, Object> shareLogs(Long playerId, Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (playerId != null && startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<ShareLog> shareLogs = egameService.findShareLog(playerId, startDate, endDate);
			for (ShareLog shareLog : shareLogs) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("datetime", shareLog.getCreateDate());
				if ("/egame/index.jsp".equals(shareLog.getPage()))
					d.put("page", "首页");
				else if ("/egame/chapterList.jsp".equals(shareLog.getPage()))
					d.put("pageName", "关卡列表");
				else if ("/egame/chapter1.jsp".equals(shareLog.getPage()))
					d.put("page", "测评(第一章)");
				else if ("/egame/chapter2.jsp".equals(shareLog.getPage()))
					d.put("page", "测评(第二章)");
				else if ("/egame/chapter3.jsp".equals(shareLog.getPage()))
					d.put("page", "测评(第三章)");
				else if ("/egame/chapter4.jsp".equals(shareLog.getPage()))
					d.put("page", "测评(第四章)");
				else if ("/egame/myArena.jsp".equals(shareLog.getPage()))
					d.put("page", "擂台");
				else if ("/egame/error.jsp".equals(shareLog.getPage()))
					d.put("pageName", "错误提示");
				else
					d.put("page", "未知");
				if ("wechat_friend".equals(shareLog.getTarget()))
					d.put("target", "微信(好友)");
				else if ("wechat_friends".equals(shareLog.getTarget()))
					d.put("target", "微信(朋友圈)");
				else
					d.put("target", "未知");
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/aggrVisitLogs.do")
	@ResponseBody
	public Map<String, Object> aggrVisitLogs(Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VisitLog> visitLogs = egameService.findVisitLog(startDate, endDate);
			for (VisitLog visitLog : visitLogs) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("page", visitLog.getPage());
				if ("/egame/index.jsp".equals(visitLog.getPage()))
					d.put("pageName", "首页");
				else if ("/egame/chapterList.jsp".equals(visitLog.getPage()))
					d.put("pageName", "关卡列表");
				else if ("/egame/chapter1.jsp".equals(visitLog.getPage()))
					d.put("pageName", "测评(第一章)");
				else if ("/egame/chapter2.jsp".equals(visitLog.getPage()))
					d.put("pageName", "测评(第二章)");
				else if ("/egame/chapter3.jsp".equals(visitLog.getPage()))
					d.put("pageName", "测评(第三章)");
				else if ("/egame/chapter4.jsp".equals(visitLog.getPage()))
					d.put("pageName", "测评(第四章)");
				else if ("/egame/myArena.jsp".equals(visitLog.getPage()))
					d.put("pageName", "擂台");
				else if ("/egame/error.jsp".equals(visitLog.getPage()))
					d.put("pageName", "错误提示");
				else
					d.put("pageName", "未知");
				d.put("log", visitLog.getCount());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	@RequestMapping("platform/visitLogs.do")
	@ResponseBody
	public Map<String, Object> visitLogs(String page, Date startDate, Date endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (page != null && startDate != null && endDate != null && startDate.getTime() < endDate.getTime()) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			List<VisitLog> visitLogs = egameService.findVisitLog(page, startDate, endDate);
			for (VisitLog visitLog : visitLogs) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("datetime", visitLog.getCreateDate());
				d.put("nickname", visitLog.getPlayerNickname());
				data.add(d);
			}
			resultMap.put("data", data);
			resultMap.put("success", true);
		} else {
			resultMap.put("success", false);
		}
		return resultMap;
	}

	private String visit(HttpServletRequest request, Player owner, Player player, String page) {
		Long ownerId = owner != null ? owner.getId() : -1l;
		Long playerId = player != null ? player.getId() : -1l;
		share(request, playerId);
		request.setAttribute("ownerId", ownerId);
		request.setAttribute("playerId", playerId);
		egameService.visit(ownerId, playerId, page);
		return page;
	}

	private void share(HttpServletRequest request, Long playerId) {
		Integer playerScoreCount = egameService.getPlayerScoreCount();
		Player player = egameService.getPlayer(playerId, true);
		WeChatClient.on(request);
		request.setAttribute("shareTitle", "我想静静");
		request.setAttribute("shareDesc", String.format("《我想静静》追女神情感测评已被测评%d人次，我得了%d分，你能超过我吗？", playerScoreCount, player != null && player.getScore() != null ? player.getScore() : 0));
		request.setAttribute("shareImgUrl", "http://www.zcdhjob.com/wechat/egame/img/b.png");
		request.setAttribute("shareUrl", WeChatClient.getOAuthUrl("http://www.zcdhjob.com/wechat/egame/index.do", true, player != null ? String.valueOf(player.getId()) : "-1"));
	}

}
