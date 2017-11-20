package com.mrong.lineview.common.service.impl;

import java.util.List;

import com.mrong.lineview.common.dao.UserDao;
import com.mrong.lineview.common.entity.User;
import com.mrong.lineview.common.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getLogin(User user) {
		User u = userDao.query(user);
		return u;
	}

	@Override
	public void add(User u) {
		userDao.add(u);

	}

	@Override
	public void delete(int id) {
		userDao.delete(id);

	}

	@Override
	public void edit(User u) {
		userDao.edit(u);

	}

	@Override
	public List<User> getAll() {
		List<User> users = userDao.getAll();
		return users;
	}

}
