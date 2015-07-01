package model.survey;

import java.io.Serializable;
import java.util.List;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("survey_topic")
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column
	private Integer num;

	@Column
	private String title;

	@Column("chapter_ids")
	private String chapterIds;

	@Column
	private String remark;

	private List<Chapter> chapters;

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

	public String getChapterIds() {
		return chapterIds;
	}

	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

}