package model.zcdh;

import java.io.Serializable;

import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("zcdh_jobfair_propaganda")
public class JobfairPropaganda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column("fair_id")
	private Long fairId;

	@Column("propaganda_content")
	private String propagandaContent;

	@Column("templet_id")
	private Long templetId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFairId() {
		return fairId;
	}

	public void setFairId(Long fairId) {
		this.fairId = fairId;
	}

	public String getPropagandaContent() {
		return propagandaContent;
	}

	public void setPropagandaContent(String propagandaContent) {
		this.propagandaContent = propagandaContent;
	}

	public Long getTempletId() {
		return templetId;
	}

	public void setTempletId(Long templetId) {
		this.templetId = templetId;
	}

}
