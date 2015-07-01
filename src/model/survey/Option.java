package model.survey;

import java.io.Serializable;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("survey_option")
public class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column
	private Integer num;

	@Column
	private Integer type;// 1 文本, 2 链接

	@Column
	private String text;

	@Column
	private Integer score;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}