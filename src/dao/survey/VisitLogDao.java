package dao.survey;

import java.util.Date;
import java.util.List;

import model.survey.VisitLog;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("visitLogDao")
public class VisitLogDao extends Store<VisitLog> {

	public List<VisitLog> aggrByDate(Integer topicId, Date startDate, Date endDate) {
		return selectList("select create_date, count(id) count from survey_visit_log where topic_id=? and create_date>=? and create_date<? group by date_format(create_date, '%Y-%m-%d') order by create_date desc", new Object[] { topicId, startDate, endDate });
	}

	public List<VisitLog> aggrByPage(Integer topicId, Date startDate, Date endDate) {
		return selectList("select page,count(id) count from survey_visit_log where topic_id=? and create_date>=? and create_date<? group by page order by count desc", new Object[] { topicId, startDate, endDate });
	}

	public List<VisitLog> find(Integer topicId, String page, Date startDate, Date endDate) {
		return selectList("select * from survey_visit_log where topic_id=? and page=? and create_date>=? and create_date<? order by create_date desc", new Object[] { topicId, page, startDate, endDate });
	}

}
