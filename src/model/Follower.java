package model;

public class Follower {

	private Integer total;
	private Integer count;
	private String[] openIds;
	private String nextOptionId;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String[] getOpenIds() {
		return openIds;
	}

	public void setOpenIds(String[] openIds) {
		this.openIds = openIds;
	}

	public String getNextOptionId() {
		return nextOptionId;
	}

	public void setNextOptionId(String nextOptionId) {
		this.nextOptionId = nextOptionId;
	}

}
