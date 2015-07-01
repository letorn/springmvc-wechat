package dao.survey;

import java.util.List;

import model.survey.PlayerOption;

import org.springframework.stereotype.Repository;

import dao.data.Store;

@Repository("playerOptionDao")
public class PlayerOptionDao extends Store<PlayerOption> {

	public List<PlayerOption> findAll() {
		return selectAll();
	}

}
