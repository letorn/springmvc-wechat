package dao.survey;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.survey.Player;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("playerDao")
public class PlayerDao extends Store<Player> {

	public Player get(Long id) {
		return Stack.playerIdMap.get(id);
	}

	public Player getByChatterId(Long chatterId, Integer topicId) {
		Map<Integer, Player> playerTopicIdMap = Stack.playerChatterIdMap.get(chatterId);
		if (playerTopicIdMap != null)
			return playerTopicIdMap.get(topicId);
		return null;
	}

	public Boolean add(Player player) {
		if (super.add(player)) {
			if (player.getId() != null)
				Stack.playerIdMap.put(player.getId(), player);
			if (player.getChatterId() != null && player.getTopicId() != null) {
				Map<Integer, Player> playerTopicIdMap = Stack.playerChatterIdMap.get(player.getChatterId());
				if (playerTopicIdMap == null) {
					playerTopicIdMap = new HashMap<Integer, Player>();
					Stack.playerChatterIdMap.put(player.getChatterId(), playerTopicIdMap);
				}
				playerTopicIdMap.put(player.getTopicId(), player);
			}
			return true;
		}
		return false;
	}

	public Boolean update(Player player) {
		if (super.update(player)) {
			if (player.getId() != null)
				Stack.playerIdMap.put(player.getId(), player);
			if (player.getChatterId() != null && player.getTopicId() != null) {
				Map<Integer, Player> playerTopicIdMap = Stack.playerChatterIdMap.get(player.getChatterId());
				if (playerTopicIdMap == null) {
					playerTopicIdMap = new HashMap<Integer, Player>();
					Stack.playerChatterIdMap.put(player.getChatterId(), playerTopicIdMap);
				}
				playerTopicIdMap.put(player.getTopicId(), player);
			}
			return true;
		}
		return false;
	}

	public List<Player> aggrByDate(Integer topicId, Date startDate, Date endDate) {
		return selectList("select create_date, count(id) count from survey_player where topic_id=? and create_date>=? and create_date<? group by date_format(create_date, '%Y-%m-%d') order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<Player> find(Integer topicId, Date startDate, Date endDate) {
		return selectList("select * from survey_player where topic_id=? and create_date>=? and create_date<? order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

}
