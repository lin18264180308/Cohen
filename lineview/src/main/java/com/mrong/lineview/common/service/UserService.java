package com.mrong.lineview.common.service;

import java.util.List;

import com.mrong.lineview.common.entity.User;

public interface UserService {

	/**
	 * 登录
	 * 
	 * @param u
	 * @return
	 */
	public User getLogin(User u);

	/**
	 * 增
	 */
	public void add(User u);

	/**
	 * 删
	 */
	public void delete(int id);

	/**
	 * 改
	 */
	public void edit(User u);

	/**
	 * 获取所有用户
	 */
	public List<User> getAll();

}
