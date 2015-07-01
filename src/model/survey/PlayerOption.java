package model.survey;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("survey_player_option")
public class PlayerOption implements Serializable {
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

	@Column("player_score_id")
	private Long playerScoreId;

	@Column("question_id")
	private Integer questionId;

	@Column("option_ids")
	private String optionIds;

	@Column
	private Integer score;

	@Column("create_date")
	private Date createDate;

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

	public Long getPlayerScoreId() {
		return playerScoreId;
	}

	public void setPlayerScoreId(Long playerScoreId) {
		this.playerScoreId = playerScoreId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}