package dao.survey;

import model.survey.Option;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("optionDao")
public class OptionDao extends Store<Option> {

	public Option get(Integer id) {
		return Stack.optionIdMap.get(id);
	}

}
