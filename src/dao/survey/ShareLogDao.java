package dao.survey;

import java.util.Date;
import java.util.List;

import model.survey.ShareLog;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("shareLogDao")
public class ShareLogDao extends Store<ShareLog> {

	public List<ShareLog> aggrByDate(Integer topicId, Date startDate, Date endDate) {
		return selectList("select create_date, count(id) count from survey_share_log where topic_id=? and create_date>=? and create_date<? group by date_format(create_date, '%Y-%m-%d') order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<ShareLog> aggrByPlayer(Integer topicId, Date startDate, Date endDate) {
		return selectList("select player_id,count(id) count from survey_share_log where topic_id=? and create_date>=? and create_date<? group by player_id order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<ShareLog> find(Long playerId, Integer topicId, Date startDate, Date endDate) {
		return selectList("select * from survey_share_log where player_id=? and topic_id=? and create_date>=? and create_date<? order by create_date desc", new Object[] { playerId, topicId, startDate, endDate });
	}

}
