package com.yrd980.qqcommon;

import java.io.Serializable;

/**
 * @author Yrd
 * @version 1.0
 */
@SuppressWarnings({"all"})
public class User implements Serializable {
	//增强兼容性
	//对象的序列化反序列化是根据序列化版本id进行的，没有显式得写出来会默认根据类的属性和方法分配一个。
	// 导致对象序列化入库之后，若类被修改，反序列化将会报错。所以显式加上序列化版本id，避免反序列化报错
	private static final long serialVersionUID = 1L;
	private String userId;
	private String pwd;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User(String userId, String pwd) {
		this.userId = userId;
		this.pwd = pwd;
	}
}
