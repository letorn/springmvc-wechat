package dao.survey;

import model.survey.Topic;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("topicDao")
public class TopicDao extends Store<Topic> {

	public Topic get(Integer id) {
		return Stack.topicIdMap.get(id);
	}

}
