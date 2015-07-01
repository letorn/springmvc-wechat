package dao.survey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.survey.PlayerScore;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("playerScoreDao")
public class PlayerScoreDao extends Store<PlayerScore> {

	public List<PlayerScore> findTop(Long ownerId, Integer topicId, Integer chapterId) {
		List<PlayerScore> playerScores = new ArrayList<PlayerScore>();
		Map<Long, Map<Integer, Map<Integer, PlayerScore>>> playerScorePlayerIdMap = Stack.playerScoreOwnerIdMap.get(ownerId);
		if (playerScorePlayerIdMap != null) {
			for (Long playerId : playerScorePlayerIdMap.keySet()) {
				Map<Integer, Map<Integer, PlayerScore>> playerScoreTopicIdMap = playerScorePlayerIdMap.get(playerId);
				if (playerScoreTopicIdMap != null) {
					Map<Integer, PlayerScore> playerScoreChapterIdMap = playerScoreTopicIdMap.get(topicId);
					if (playerScoreChapterIdMap != null) {
						PlayerScore playerScore = playerScoreChapterIdMap.get(chapterId);
						if (playerScore != null)
							playerScores.add(playerScore);
					}
				}
			}
		}
		return sorter(playerScores);
	}

	public PlayerScore get(Long ownerId, Long playerId, Integer topicId, Integer chapterId) {
		Map<Long, Map<Integer, Map<Integer, PlayerScore>>> playerScorePlayerIdMap = Stack.playerScoreOwnerIdMap.get(ownerId);
		if (playerScorePlayerIdMap != null) {
			Map<Integer, Map<Integer, PlayerScore>> playerScoreTopicIdMap = playerScorePlayerIdMap.get(playerId);
			if (playerScoreTopicIdMap != null) {
				Map<Integer, PlayerScore> playerScoreChapterIdMap = playerScoreTopicIdMap.get(topicId);
				if (playerScoreChapterIdMap != null) {
					PlayerScore playerScore = playerScoreChapterIdMap.get(chapterId);
					if (playerScore != null)
						return playerScore;
				}
			}
		}
		return null;
	}

	public Boolean add(PlayerScore playerScore) {
		if (super.add(playerScore) && playerScore.getTopicId() != null && playerScore.getOwnerId() != null && playerScore.getPlayerId() != null && playerScore.getChapterId() != null) {
			Map<Long, Map<Integer, Map<Integer, PlayerScore>>> playerScorePlayerIdMap = Stack.playerScoreOwnerIdMap.get(playerScore.getOwnerId());
			if (playerScorePlayerIdMap == null) {
				playerScorePlayerIdMap = new HashMap<Long, Map<Integer, Map<Integer, PlayerScore>>>();
				Stack.playerScoreOwnerIdMap.put(playerScore.getOwnerId(), playerScorePlayerIdMap);
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
			PlayerScore oldPlayerScore = playerScoreChapterIdMap.get(playerScore.getChapterId());
			if (oldPlayerScore == null || playerScore.getScore() > oldPlayerScore.getScore())
				playerScoreChapterIdMap.put(playerScore.getChapterId(), playerScore);
			return true;
		}
		return false;
	}

	public Integer getCount(Integer topicId, Integer chapterId) {
		Long count = selectLong("select count(id) from survey_player_score where topic_id=? and chapter_id=?", new Object[] { topicId, chapterId });
		return count == null ? 0 : count.intValue();
	}

	public List<PlayerScore> aggrByDate(Integer topicId, Date startDate, Date endDate) {
		return selectList("select create_date, count(id) count from survey_player_score where topic_id=? and create_date>=? and create_date<? group by date_format(create_date, '%Y-%m-%d') order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<PlayerScore> aggrByPlayer(Integer topicId, Date startDate, Date endDate) {
		return selectList("select player_id,create_date,count(id) count from survey_player_score where topic_id=? and create_date>=? and create_date<? group by player_id order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<PlayerScore> find(Long playerId, Integer topicId, Date startDate, Date endDate) {
		return selectList("select * from survey_player_score where player_id=? and topic_id=? and create_date>=? and create_date<? order by create_date desc", new Object[] { playerId, topicId, startDate, endDate });
	}

	private List<PlayerScore> sorter(List<PlayerScore> playerScores) {
		Collections.sort(playerScores, new Comparator<PlayerScore>() {
			public int compare(PlayerScore ps1, PlayerScore ps2) {
				if (ps1.getScore() == null)
					return 1;
				if (ps2.getScore() == null)
					return -1;
				return ps2.getScore().compareTo(ps1.getScore());
			}

		});
		return playerScores;
	}

}
