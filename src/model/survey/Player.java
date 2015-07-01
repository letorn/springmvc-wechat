package model.survey;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Mapping;
import dao.data.Table;

@Table("survey_player")
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column("chatter_id")
	private Long chatterId;

	@Column("topic_id")
	private Integer topicId;

	@Column
	private String nickname;

	@Column
	private Integer gender;

	@Column("head_img_url")
	private String headImgUrl;

	@Column("create_date")
	private Date createDate;

	@Column("update_date")
	private Date updateDate;

	private Integer score = 0;
	private Integer star = 0;
	private String honor = "";
	private Integer won = 0;
	private Integer drawn = 0;
	private Integer lost = 0;
	private Integer matched = 1;// 1 赢, 0 平, -1 输
	private Integer chapterKeys = 0;

	@Mapping
	private Integer count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChatterId() {
		return chatterId;
	}

	public void setChatterId(Long chatterId) {
		this.chatterId = chatterId;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public Integer getWon() {
		return won;
	}

	public void setWon(Integer won) {
		this.won = won;
	}

	public Integer getDrawn() {
		return drawn;
	}

	public void setDrawn(Integer drawn) {
		this.drawn = drawn;
	}

	public Integer getLost() {
		return lost;
	}

	public void setLost(Integer lost) {
		this.lost = lost;
	}

	public Integer getMatched() {
		return matched;
	}

	public void setMatched(Integer matched) {
		this.matched = matched;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getChapterKeys() {
		return chapterKeys;
	}

	public void setChapterKeys(Integer chapterKeys) {
		this.chapterKeys = chapterKeys;
	}

}