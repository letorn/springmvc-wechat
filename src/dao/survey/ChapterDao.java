package dao.survey;

import model.survey.Chapter;

import org.springframework.stereotype.Repository;

import dao.data.Stack;
import dao.data.Store;

@Repository("chapterDao")
public class ChapterDao extends Store<Chapter> {

	public Chapter get(Integer id) {
		return Stack.chapterIdMap.get(id);
	}

}
