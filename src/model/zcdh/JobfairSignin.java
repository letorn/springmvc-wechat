package model.zcdh;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("zcdh_jobfair_signin")
public class JobfairSignin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column("create_date")
	private Date createDate;

	@Column("ent_id")
	private Long entId;

	@Column("fair_id")
	private Long fairId;

	@Column("sign_type")
	private Integer signType;

	@Column("user_id")
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public Long getFairId() {
		return fairId;
	}

	public void setFairId(Long fairId) {
		this.fairId = fairId;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}