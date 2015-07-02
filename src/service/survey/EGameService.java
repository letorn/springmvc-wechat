package service.survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import model.Chatter;
import model.Oauth;
import model.survey.Chapter;
import model.survey.Option;
import model.survey.Player;
import model.survey.PlayerOption;
import model.survey.PlayerScore;
import model.survey.Question;
import model.survey.ShareLog;
import model.survey.Topic;
import model.survey.VisitLog;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import util.WeChatClient;
import dao.ChatterDao;
import dao.survey.ChapterDao;
import dao.survey.OptionDao;
import dao.survey.PlayerDao;
import dao.survey.PlayerOptionDao;
import dao.survey.PlayerScoreDao;
import dao.survey.ShareLogDao;
import dao.survey.TopicDao;
import dao.survey.VisitLogDao;

@Service("egameService")
public class EGameService {

	private static JsonConfig jsonConfig = new JsonConfig();
	private static String[] chapterTitlePrefixes = new String[] { "第1关", "第2关", "第3关", "第4关", "第5关" };
	private static String[] optionTextPrefixes = new String[] { "A", "B", "C", "D", "E", "F", "G" };
	private static List<String> questionsStrs = new ArrayList<String>();

	@Resource
	private ChatterDao chatterDao;
	@Resource
	private TopicDao topicDao;
	@Resource
	private ChapterDao chapterDao;
	@Resource
	private OptionDao optionDao;
	@Resource
	private PlayerDao playerDao;
	@Resource
	private PlayerScoreDao playerScoreDao;
	@Resource
	private PlayerOptionDao playerOptionDao;
	@Resource
	private ShareLogDao shareLogDao;
	@Resource
	private VisitLogDao visitLogDao;

	private Topic topic;

	@PostConstruct
	private void init() {
		topic = topicDao.get(1);
		for (Chapter chapter : topic.getChapters()) {
			List<Map<String, Object>> ques = new ArrayList<Map<String, Object>>();
			List<Question> questions = chapter.getQuestions();
			for (Question question : chapter.getQuestions()) {
				Map<String, Object> que = new HashMap<String, Object>();
				que.put("title", String.format("%d/%d.%s", question.getNum(), questions.size(), question.getText()));
				List<String> opts = new ArrayList<String>();
				for (Option option : question.getOptions()) {
					opts.add(String.format("%s. %s", optionTextPrefixes[option.getNum() - 1], option.getText()));
				}
				que.put("options", opts);
				que.put("description", question.getRemark());
				ques.add(que);
			}
			questionsStrs.add(JSONArray.fromObject(ques, jsonConfig).toString());
		}
	}

	public String getChapterTitle(Integer chapterNum) {
		return String.format("%s %s", chapterTitlePrefixes[chapterNum - 1], topic.getChapters().get(chapterNum - 1).getTitle());
	}

	public String getChapterQuestionsStr(Integer chapterNum) {
		return questionsStrs.get(chapterNum - 1);
	}

	public String getChapterRemark(Integer chapterNum) {
		return topic.getChapters().get(chapterNum - 1).getRemark();
	}

	public Integer getChapterCount() {
		return topic.getChapters().size();
	}

	public Boolean checkChapterNum(Integer chapterNum) {
		if (chapterNum != null && chapterNum >= 1 && chapterNum <= topic.getChapters().size())
			return true;
		return false;
	}

	public Boolean checkOptionIndexes(Integer chapterNum, Integer[] optionIndexes) {
		Chapter chapter = topic.getChapters().get(chapterNum - 1);
		if (chapter.getQuestions().size() == optionIndexes.length) {
			for (int i = 0; i < optionIndexes.length; i++) {
				if (optionIndexes[i] <= -1 || optionIndexes[i] >= chapter.getQuestions().get(i).getOptions().size())
					return false;
			}
			return true;
		}
		return false;
	}

	public Integer countChapterScore(Long ownerId, Long playerId, Integer chapterNum, Date beginDate, Date endDate, Integer[] optionIndexes) {
		Integer score = 0;

		Chapter chapter = topic.getChapters().get(chapterNum - 1);
		Integer[] optionIds = new Integer[optionIndexes.length];
		for (int i = 0; i < optionIndexes.length; i++) {
			Option option = chapter.getQuestions().get(i).getOptions().get(optionIndexes[i]);
			optionIds[i] = option.getId();
			score += option.getScore();
		}

		Integer ptype = ownerId == playerId ? 1 : 0;
		PlayerScore playerScore = new PlayerScore();
		playerScore.setOwnerId(ownerId);
		playerScore.setPlayerId(playerId);
		playerScore.setPtype(ptype);
		playerScore.setTopicId(topic.getId());
		playerScore.setChapterId(chapter.getId());
		playerScore.setQuestionIds(chapter.getQuestionIds());
		playerScore.setOptionIds(String.format("[%s]", StringUtils.join(optionIds, ",")));
		playerScore.setScore(score);
		playerScore.setBeginDate(beginDate);
		playerScore.setEndDate(endDate);
		playerScore.setCreateDate(new Date());
		playerScoreDao.add(playerScore);

		List<PlayerOption> playerOptions = new ArrayList<PlayerOption>();
		for (int i = 0; i < optionIndexes.length; i++) {
			Question question = chapter.getQuestions().get(i);
			Option option = question.getOptions().get(optionIndexes[i]);

			PlayerOption playerOption = new PlayerOption();
			playerOption.setOwnerId(ownerId);
			playerOption.setPlayerId(playerId);
			playerOption.setPtype(ptype);
			playerOption.setTopicId(topic.getId());
			playerOption.setChapterId(chapter.getId());
			playerOption.setPlayerScoreId(playerScore.getId());
			playerOption.setQuestionId(question.getId());
			playerOption.setOptionIds(String.format("[%s]", option.getId()));
			playerOption.setScore(option.getScore());
			playerOption.setCreateDate(new Date());
			playerOptions.add(playerOption);
		}
		playerOptionDao.add(playerOptions);

		return score;
	}

	public Player access() {
		Player player = new Player();
		player.setNickname("anonymous");
		if (playerDao.add(player))
			return player;
		return null;
	}

	public Player access(Long playerId) {
		if (playerId != null)
			return playerDao.get(playerId);
		return null;
	}

	public Player access(String code, String state) {
		Oauth oauth = WeChatClient.getOauth(code);
		if (oauth != null) {
			Chatter chatter = WeChatClient.getChatter(oauth);
			if (chatter != null) {
				Date now = new Date();
				chatter.setOauthDate(now);
				chatter.setUpdateDate(now);
				Chatter oldChatter = chatterDao.getByOpenId(chatter.getOpenId());
				Player player = null;
				if (oldChatter == null) {
					chatter.setCreateDate(now);
					if (chatterDao.add(chatter)) {
						player = new Player();
						player.setChatterId(chatter.getId());
						player.setTopicId(topic.getId());
						player.setNickname(chatter.getNickname());
						player.setGender(chatter.getSex() == 1 ? 1 : 0);
						player.setHeadImgUrl(chatter.getHeadImgUrl());
						player.setCreateDate(now);
						player.setUpdateDate(now);
						playerDao.add(player);
					}
				} else {
					player = playerDao.getByChatterId(oldChatter.getId(), topic.getId());
					if (player == null) {
						player = new Player();
						player.setChatterId(oldChatter.getId());
						player.setTopicId(topic.getId());
						player.setNickname(oldChatter.getNickname());
						player.setGender(oldChatter.getSex() == 1 ? 1 : 0);
						player.setHeadImgUrl(oldChatter.getHeadImgUrl());
						player.setCreateDate(now);
						player.setUpdateDate(now);
						playerDao.add(player);
					}
					if (chatter.getUpdateDate().getTime() - oldChatter.getUpdateDate().getTime() > 1000 * 60 * 50) {
						chatter.setId(oldChatter.getId());
						chatter.setCreateDate(oldChatter.getCreateDate());
						if (chatterDao.update(chatter) && player.getCreateDate() != now) {
							player.setNickname(chatter.getNickname());
							player.setGender(chatter.getSex() == 1 ? 1 : 0);
							player.setHeadImgUrl(chatter.getHeadImgUrl());
							player.setUpdateDate(now);
							playerDao.update(player);
						}
					}
				}
				return player;
			}
		}
		return null;
	}

	public Player getPlayer(Long playerId, Boolean initScore) {
		if (playerId != null) {
			Player player = playerDao.get(playerId);
			if (initScore && player != null) {
				player.setScore(0);
				player.setChapterKeys(0);
				for (Chapter chapter : topic.getChapters()) {
					PlayerScore playerScore = playerScoreDao.get(playerId, playerId, topic.getId(), chapter.getId());
					if (playerScore != null && playerScore.getScore() != null) {
						player.setScore(player.getScore() + playerScore.getScore());
						player.setChapterKeys(player.getChapterKeys() + 1);
					}
				}
			}
			return player;
		}
		return null;
	}

	public Boolean existPlayer(Long playerId) {
		if (playerId != null)
			return playerDao.get(playerId) == null ? false : true;
		return false;
	}

	public PlayerScore getPlayerScore(Long ownerId, Long playerId, Integer chapterNum) {
		return playerScoreDao.get(ownerId, playerId, topic.getId(), topic.getChapters().get(chapterNum - 1).getId());
	}

	public Integer getPlayerScoreCount() {
		return playerScoreDao.getCount(topic.getId(), topic.getChapters().get(0).getId());
	}

	public List<Player> getTops(Long ownerId) {
		Set<Player> playerSet = new HashSet<Player>();
		for (Chapter chapter : topic.getChapters()) {
			for (PlayerScore playerScore : playerScoreDao.findTop(ownerId, topic.getId(), chapter.getId())) {
				Player player = playerDao.get(playerScore.getPlayerId());
				if (!playerSet.contains(player)) {
					player.setScore(0);
					player.setChapterKeys(0);
					playerSet.add(player);
				}
				if (playerScore.getScore() != null) {
					player.setScore(player.getScore() + playerScore.getScore());
					player.setChapterKeys(player.getChapterKeys() + 1);
				}
			}
		}

		Player owner = playerDao.get(ownerId);
		if (!playerSet.remove(owner)) {
			owner.setScore(0);
			owner.setChapterKeys(0);
		}

		owner.setWon(0);
		owner.setDrawn(0);
		owner.setLost(0);
		for (Player player : playerSet) {
			int res = owner.getScore().compareTo(honorSetting(player).getScore());
			if (res > 0) {
				owner.setWon(owner.getWon() + 1);
				player.setMatched(-1);
			} else if (res == 0) {
				owner.setDrawn(owner.getDrawn() + 1);
				player.setMatched(0);
			} else if (res < 0) {
				owner.setLost(owner.getLost() + 1);
				player.setMatched(1);
			}
		}

		List<Player> players = arenaSorter(playerSet);
		players.add(0, honorSetting(owner));
		return players;
	}

	public Boolean share(Long ownerId, Long playerId, String page, String target) {
		for (int i = 0; i < topic.getChapters().size(); i++) {
			Chapter chapter = topic.getChapters().get(i - 1);
			PlayerScore oldPlayerScore = playerScoreDao.get(ownerId, playerId, topic.getId(), chapter.getId());
			if (oldPlayerScore != null) {
				PlayerScore playerScore = new PlayerScore();
				playerScore.setOwnerId(playerId);
				playerScore.setPlayerId(playerId);
				playerScore.setPtype(1);
				playerScore.setTopicId(oldPlayerScore.getTopicId());
				playerScore.setChapterId(oldPlayerScore.getChapterId());
				playerScore.setQuestionIds(oldPlayerScore.getQuestionIds());
				playerScore.setOptionIds(oldPlayerScore.getOptionIds());
				playerScore.setScore(oldPlayerScore.getScore());
				playerScore.setBeginDate(oldPlayerScore.getBeginDate());
				playerScore.setEndDate(oldPlayerScore.getEndDate());
				playerScore.setCreateDate(new Date());
				playerScoreDao.add(playerScore);
			}
		}
		return shareLogDao.add(new ShareLog(ownerId, playerId, topic.getId(), page, target));
	}

	public Boolean visit(Long ownerId, Long playerId, String page) {
		return visitLogDao.add(new VisitLog(ownerId, playerId, topic.getId(), page));
	}

	public List<Player> aggrPlayerByDate(Date startDate, Date endDate) {
		return playerDao.aggrByDate(topic.getId(), startDate, endDate);
	}

	public List<PlayerScore> aggrPlayerScoreByDate(Date startDate, Date endDate) {
		return playerScoreDao.aggrByDate(topic.getId(), startDate, endDate);
	}

	public List<ShareLog> aggrShareLogByDate(Date startDate, Date endDate) {
		return shareLogDao.aggrByDate(topic.getId(), startDate, endDate);
	}

	public List<VisitLog> aggrVisitLogByDate(Date startDate, Date endDate) {
		return visitLogDao.aggrByDate(topic.getId(), startDate, endDate);
	}

	public List<Player> findPlayer(Date startDate, Date endDate) {
		List<Player> players = playerDao.find(topic.getId(), startDate, endDate);
		for (Player player : players) {
			Player oldPlayer = getPlayer(player.getId(), true);
			if (oldPlayer != null) {
				player.setScore(oldPlayer.getScore());
				player.setChapterKeys(oldPlayer.getChapterKeys());
			}
		}
		return players;
	}

	public List<PlayerScore> findPlayerScore(Date startDate, Date endDate) {
		List<PlayerScore> playerScores = playerScoreDao.aggrByPlayer(topic.getId(), startDate, endDate);
		for (PlayerScore playerScore : playerScores) {
			Player player = playerDao.get(playerScore.getPlayerId());
			playerScore.setPlayerNickname(player != null && player.getNickname() != null ? player.getNickname() : "");
		}
		return playerScores;
	}

	public List<PlayerScore> findPlayerScore(Long playerId, Date startDate, Date endDate) {
		List<PlayerScore> playerScores = playerScoreDao.find(playerId, topic.getId(), startDate, endDate);
		for (PlayerScore playerScore : playerScores) {
			if (playerScore.getChapterId() != null) {
				Chapter chapter = chapterDao.get(playerScore.getChapterId());
				playerScore.setChapterNum(chapter != null && chapter.getNum() != null ? chapter.getNum() : -1);
			}

			if (playerScore.getOptionIds() != null) {
				JSONArray jsonArray = JSONArray.fromObject(playerScore.getOptionIds());
				String[] letters = new String[jsonArray.size()];
				for (int i = 0; i < jsonArray.size(); i++) {
					Option option = optionDao.get(jsonArray.optInt(i));
					if (option != null && option.getNum() != null)
						letters[i] = optionTextPrefixes[option.getNum() - 1];
				}
				playerScore.setOptionLetters(StringUtils.join(letters, ","));
			} else {
				playerScore.setOptionLetters("");
			}
		}
		return playerScores;
	}

	public List<ShareLog> findShareLog(Date startDate, Date endDate) {
		List<ShareLog> shareLogs = shareLogDao.aggrByPlayer(topic.getId(), startDate, endDate);
		for (ShareLog shareLog : shareLogs) {
			Player player = playerDao.get(shareLog.getPlayerId());
			shareLog.setPlayerNickname(player != null && player.getNickname() != null ? player.getNickname() : "");
		}
		return shareLogs;
	}

	public List<ShareLog> findShareLog(Long playerId, Date startDate, Date endDate) {
		return shareLogDao.find(playerId, topic.getId(), startDate, endDate);
	}

	public List<VisitLog> findVisitLog(Date startDate, Date endDate) {
		return visitLogDao.aggrByPage(topic.getId(), startDate, endDate);
	}

	public List<VisitLog> findVisitLog(String page, Date startDate, Date endDate) {
		List<VisitLog> visitLogs = visitLogDao.find(topic.getId(), page, startDate, endDate);
		for (VisitLog visitLog : visitLogs) {
			Player player = playerDao.get(visitLog.getPlayerId());
			if (player != null)
				visitLog.setPlayerNickname(player.getNickname());
		}
		return visitLogs;
	}

	private static List<Player> arenaSorter(Set<Player> playerSet) {
		List<Player> players = new ArrayList<Player>(playerSet);
		Collections.sort(players, new Comparator<Player>() {
			public int compare(Player p1, Player p2) {
				if (p1.getScore() == null)
					return 1;
				if (p2.getScore() == null)
					return -1;
				return p2.getScore().compareTo(p1.getScore());
			}

		});
		return players;
	}

	private static Player honorSetting(Player player) {
		if (player.getScore() > 0 && player.getScore() <= 60) {
			player.setStar(1);
			player.setHonor("天煞孤星");
		} else if (player.getScore() > 60 && player.getScore() <= 120) {
			player.setStar(2);
			player.setHonor("单身汪汪");
		} else if (player.getScore() > 120 && player.getScore() <= 180) {
			player.setStar(3);
			player.setHonor("暖男备胎");
		} else if (player.getScore() > 180 && player.getScore() <= 240) {
			player.setStar(4);
			player.setHonor("恋爱达人");
		} else if (player.getScore() > 240 && player.getScore() <= 300) {
			player.setStar(5);
			player.setHonor("女神杀手");
		} else {
			player.setStar(0);
			player.setHonor("暂无");
		}
		return player;
	}

}
