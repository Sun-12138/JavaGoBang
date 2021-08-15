package pers.gobang.method;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	public String account;
	public String password;
	public String name;
	public Date birthday;
	public String sex;
	public Boolean game_mod;

	public User() {
		super();
	}

	/**
	 * @param account  账号
	 * @param password 密码
	 * @param name     姓名
	 * @param birthday 生日
	 * @param sex      性别
	 * @param game_mod 选择的游戏模式
	 */

	public User(String account, String password, String name, Date birthday, String sex, Boolean game_mod) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.game_mod = game_mod;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setGame_mod(Boolean game_mod) {
		this.game_mod = game_mod;
	}

	public String getAccount() {
		return account;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getSex() {
		return sex;
	}

	public Boolean getGame_mod() {
		return game_mod;
	}
}
