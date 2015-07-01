package model.survey;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Mapping;
import dao.data.Table;

@Table("survey_visit_log")
public class VisitLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column("owner_id")
	private Long ownerId;

	@Column("player_id")
	private Long playerId;

	@Column("topic_id")
	private Integer topicId;

	@Column
	private String page;

	@Column("create_date")
	private Date createDate;

	@Mapping
	private Integer count;

	private String playerNickname;

	public VisitLog() {

	}

	public VisitLog(Long ownerId, Long playerId, Integer topicId, String page) {
		this.ownerId = ownerId;
		this.playerId = playerId;
		this.topicId = topicId;
		this.page = page;
		createDate = new Date();
	}

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

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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

}