package com.mrong.lineview.common.dao;

import java.util.List;

import com.mrong.lineview.common.entity.User;

public interface UserDao {

	/**
	 * 关于用户的查询方法（用户的登录）
	 * 
	 * @param user.xx
	 * @return user
	 */
	public User query(User u);

	/**
	 * 关于用户新增
	 */
	public void add(User u);

	/**
	 * 用户的修改
	 */
	public void edit(User u);

	/**
	 * 用户的删除
	 */
	public void delete(int id);

	/**
	 * 获取所有用户
	 */
	public List<User> getAll();
}
