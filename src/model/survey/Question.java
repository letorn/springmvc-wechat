package model.survey;

import java.io.Serializable;
import java.util.List;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("survey_question")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column
	private Integer num;

	@Column
	private Integer type;// 1 单选, 2 多选

	@Column
	private String text;

	@Column("option_ids")
	private String optionIds;

	@Column
	private String remark;

	private List<Option> options;

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

	public String getOptionIds() {
		return optionIds;
	}

	public void setOptionIds(String optionIds) {
		this.optionIds = optionIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

}