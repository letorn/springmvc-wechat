package dao.survey;

import java.util.List;

import model.survey.Question;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("questionDao")
public class QuestionDao extends Store<Question> {

	public List<Question> findAll() {
		return selectAll();
	}

}
