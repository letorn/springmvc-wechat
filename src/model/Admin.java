package model;

import java.io.Serializable;

import net.sf.json.JSONArray;
import dao.data.Column;
import dao.data.Id;
import dao.data.Table;

@Table("wechat_admin")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		String str = "[1,2,5]";
		System.out.println(JSONArray.fromObject(str).optInt(2));
	}
	
	@Id
	private Integer id;

	@Column
	private String name;

	@Column
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
