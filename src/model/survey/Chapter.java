package model.survey;

import java.io.Serializable;
import java.util.List;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("survey_chapter")
public class Chapter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column
	private Integer num;

	@Column
	private String title;

	@Column("question_ids")
	private String questionIds;

	@Column
	private String remark;

	private List<Question> questions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}