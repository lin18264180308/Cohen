package com.mrong.lineview.common.action;

import java.util.List;

import com.mrong.lineview.common.entity.User;
import com.mrong.lineview.common.service.UserService;

@SuppressWarnings("all")
public class UserAction extends BaseAction {

	private User user;
	private UserService userService;
	private List<User> list;

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 登录方法
	 */
	public String login() {
		User u = userService.getLogin(user);
		if (u == null) {
			session.setAttribute("userName", "用户名或密码错误！");
			return INPUT;

		} else {
			session.setAttribute("userName", u.getUserName());
			if (u.getPermission().equals("a")) {
				return "admin";

			} else {
				return SUCCESS;
			}

		}
	}

	/**
	 * 注销方法
	 * 
	 * @return
	 */
	public String logout() {
		session.removeAttribute("userName");
		return INPUT;
	}

	/**
	 * 新增用户
	 */
	public String add() {
		userService.add(user);
		return SUCCESS;
	}

	/**
	 * 删除用户
	 */
	public String delete() {
		userService.delete(user.getId());
		return SUCCESS;
	}

	/**
	 * 获取所有用户信息
	 */
	public String getUsers() {
		list = userService.getAll();
		return SUCCESS;
	}

}
