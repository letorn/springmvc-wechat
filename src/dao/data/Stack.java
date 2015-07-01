package dao.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import model.Chatter;
import model.Menu;
import model.survey.Chapter;
import model.survey.Option;
import model.survey.Player;
import model.survey.PlayerScore;
import model.survey.Question;
import model.survey.Topic;
import net.sf.json.JSONArray;
import dao.ChatterDao;
import dao.MenuDao;
import dao.data.C3P0Store.Iterator;
import dao.survey.ChapterDao;
import dao.survey.OptionDao;
import dao.survey.PlayerDao;
import dao.survey.PlayerScoreDao;
import dao.survey.QuestionDao;
import dao.survey.TopicDao;

public class Stack {

	@Resource
	private MenuDao menuDao;
	@Resource
	private ChatterDao chatterDao;

	@Resource
	private TopicDao topicDao;
	@Resource
	private ChapterDao chapterDao;
	@Resource
	private QuestionDao questionDao;
	@Resource
	private OptionDao optionDao;

	@Resource
	private PlayerDao playerDao;
	@Resource
	private PlayerScoreDao playerScoreDao;

	public static Map<Integer, Menu> menuIdMap = new ConcurrentHashMap<Integer, Menu>();
	public static Map<Long, Chatter> chatterIdMap = new ConcurrentHashMap<Long, Chatter>();
	public static Map<String, Chatter> chatterOpenIdMap = new ConcurrentHashMap<String, Chatter>();

	public static Map<Integer, Topic> topicIdMap = new ConcurrentHashMap<Integer, Topic>();
	public static Map<Integer, Chapter> chapterIdMap = new ConcurrentHashMap<Integer, Chapter>();
	public static Map<Integer, Question> questionIdMap = new ConcurrentHashMap<Integer, Question>();
	public static Map<Integer, Option> optionIdMap = new ConcurrentHashMap<Integer, Option>();

	public static Map<Long, Player> playerIdMap = new ConcurrentHashMap<Long, Player>();
	public static Map<Long, Map<Integer, Player>> playerChatterIdMap = new ConcurrentHashMap<Long, Map<Integer, Player>>();
	public static Map<Long, Map<Long, Map<Integer, Map<Integer, PlayerScore>>>> playerScoreOwnerIdMap = new ConcurrentHashMap<Long, Map<Long, Map<Integer, Map<Integer, PlayerScore>>>>();

	public void init() {
		menuDao.selectAll(new Iterator<Menu>() {
			public boolean next(Menu menu, int i) throws Exception {
				if (menu.getId() != null)
					menuIdMap.put(menu.getId(), menu);
				return true;
			}
		});
		chatterDao.selectAll(new Iterator<Chatter>() {
			public boolean next(Chatter chatter, int i) throws Exception {
				if (chatter.getId() != null)
					chatterIdMap.put(chatter.getId(), chatter);
				if (chatter.getOpenId() != null)
					chatterOpenIdMap.put(chatter.getOpenId(), chatter);
				return true;
			}
		});

		optionDao.selectAll(new Iterator<Option>() {
			public boolean next(Option option, int i) throws Exception {
				if (option.getId() != null)
					optionIdMap.put(option.getId(), option);
				return true;
			}
		});
		questionDao.selectAll(new Iterator<Question>() {
			public boolean next(Question question, int i) throws Exception {
				if (question.getOptionIds() != null) {
					question.setOptions(new ArrayList<Option>());
					JSONArray jsonArray = JSONArray.fromObject(question.getOptionIds());
					for (int j = 0; j < jsonArray.size(); j++)
						question.getOptions().add(optionIdMap.get(jsonArray.optInt(j)));
				}
				if (question.getId() != null)
					questionIdMap.put(question.getId(), question);
				return true;
			}
		});
		chapterDao.selectAll(new Iterator<Chapter>() {
			public boolean next(Chapter chapter, int i) throws Exception {
				if (chapter.getQuestionIds() != null) {
					chapter.setQuestions(new ArrayList<Question>());
					JSONArray jsonArray = JSONArray.fromObject(chapter.getQuestionIds());
					for (int j = 0; j < jsonArray.size(); j++)
						chapter.getQuestions().add(questionIdMap.get(jsonArray.optInt(j)));
				}
				if (chapter.getId() != null)
					chapterIdMap.put(chapter.getId(), chapter);
				return true;
			}
		});
		topicDao.selectAll(new Iterator<Topic>() {
			public boolean next(Topic topic, int i) throws Exception {
				if (topic.getChapterIds() != null) {
					topic.setChapters(new ArrayList<Chapter>());
					JSONArray jsonArray = JSONArray.fromObject(topic.getChapterIds());
					for (int j = 0; j < jsonArray.size(); j++)
						topic.getChapters().add(chapterIdMap.get(jsonArray.optInt(j)));
				}
				if (topic.getId() != null)
					topicIdMap.put(topic.getId(), topic);
				return true;
			}
		});
		playerDao.selectAll(new Iterator<Player>() {
			public boolean next(Player player, int i) throws Exception {
				if (player.getId() != null)
					playerIdMap.put(player.getId(), player);
				if (player.getChatterId() != null && player.getTopicId() != null) {
					Map<Integer, Player> playerTopicIdMap = playerChatterIdMap.get(player.getChatterId());
					if (playerTopicIdMap == null) {
						playerTopicIdMap = new HashMap<Integer, Player>();
						playerChatterIdMap.put(player.getChatterId(), playerTopicIdMap);
					}
					playerTopicIdMap.put(player.getTopicId(), player);
				}
				return true;
			}
		});
		playerScoreDao.selectList("select id,owner_id,player_id,ptype,topic_id,chapter_id,question_ids,option_ids,max(score) score,begin_date,end_date,create_date from survey_player_score group by owner_id,player_id,topic_id,chapter_id order by ptype desc", new Iterator<PlayerScore>() {
			public boolean next(PlayerScore playerScore, int i) throws Exception {
				if (playerScore.getOwnerId() != null && playerScore.getPlayerId() != null && playerScore.getTopicId() != null && playerScore.getChapterId() != null) {
					Map<Long, Map<Integer, Map<Integer, PlayerScore>>> playerScorePlayerIdMap = playerScoreOwnerIdMap.get(playerScore.getOwnerId());
					if (playerScorePlayerIdMap == null) {
						playerScorePlayerIdMap = new HashMap<Long, Map<Integer, Map<Integer, PlayerScore>>>();
						playerScoreOwnerIdMap.put(playerScore.getOwnerId(), playerScorePlayerIdMap);
					}
					Map<Integer, Map<Integer, PlayerScore>> playerScoreTopicIdMap = playerScorePlayerIdMap.get(playerScore.getPlayerId());
					if (playerScoreTopicIdMap == null) {
						playerScoreTopicIdMap = new HashMap<Integer, Map<Integer, PlayerScore>>();
						playerScorePlayerIdMap.put(playerScore.getPlayerId(), playerScoreTopicIdMap);
					}
					Map<Integer, PlayerScore> playerScoreChapterIdMap = playerScoreTopicIdMap.get(playerScore.getTopicId());
					if (playerScoreChapterIdMap == null) {
						playerScoreChapterIdMap = new HashMap<Integer, PlayerScore>();
						playerScoreTopicIdMap.put(playerScore.getTopicId(), playerScoreChapterIdMap);
					}
					playerScoreChapterIdMap.put(playerScore.getChapterId(), playerScore);
				}
				return true;
			}
		});
	}
}
