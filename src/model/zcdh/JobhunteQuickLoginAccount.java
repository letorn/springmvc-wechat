package model.zcdh;

import java.io.Serializable;
import java.util.Date;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("zcdh_jobhunte_quick_login_account")
public class JobhunteQuickLoginAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column
	private String type;

	@Column("user_id")
	private Long userId;

	@Column("warranty_id")
	private String warrantyId;

	@Column("create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWarrantyId() {
		return warrantyId;
	}

	public void setWarrantyId(String warrantyId) {
		this.warrantyId = warrantyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static class JobhunteQuickLoginAccountType {
		public final static String QQ = "QQ";
		public final static String WEIBO = "weiBo";
		public final static String MOBILE = "Mobile";
		public final static String WECHAT = "weChat";
	}

}