package model.survey;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Mapping;
import dao.data.Table;

@Table("survey_player_score")
public class PlayerScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column("owner_id")
	private Long ownerId;

	@Column("player_id")
	private Long playerId;

	@Column
	private Integer ptype;// 1 擂主, 0 玩家

	@Column("topic_id")
	private Integer topicId;

	@Column("chapter_id")
	private Integer chapterId;

	@Column("question_ids")
	private String questionIds;

	@Column("option_ids")
	private String optionIds;

	@Column
	private Integer score;

	@Column("begin_date")
	private Date beginDate;

	@Column("end_date")
	private Date endDate;

	@Column("create_date")
	private Date createDate;

	@Mapping
	private Integer count;

	private String playerNickname;
	private Integer chapterNum;
	private String optionLetters;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public String getOptionIds() {
		return optionIds;
	}

	public void setOptionIds(String optionIds) {
		this.optionIds = optionIds;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPlayerNickname() {
		return playerNickname;
	}

	public void setPlayerNickname(String playerNickname) {
		this.playerNickname = playerNickname;
	}

	public Integer getChapterNum() {
		return chapterNum;
	}

	public void setChapterNum(Integer chapterNum) {
		this.chapterNum = chapterNum;
	}

	public String getOptionLetters() {
		return optionLetters;
	}

	public void setOptionLetters(String optionLetters) {
		this.optionLetters = optionLetters;
	}

}