package com.mrong.lineview.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.UserDao;
import com.mrong.lineview.common.entity.User;

@SuppressWarnings("all")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@SuppressWarnings("unchecked")
	public User query(User u) {
		String hql = "FROM User u WHERE u.userName=:userName AND u.userPassword=:userPassword ";
		Map<String, Object> mapLogin = new HashMap<String, Object>();
		mapLogin.put("userName", u.getUserName());
		mapLogin.put("userPassword", u.getUserPassword());
		List<User> uList = (List<User>) getByHql(hql, mapLogin);
		if (uList == null || uList.size() == 0) {
			return null;
		} else {
			return uList.get(0);
		}
	}

	/**
	 * 保存用户方法
	 */
	public void add(User u) {
		super.save(u);
	}

	/**
	 * 编辑用户
	 */
	public void edit(User u) {
		super.update(u);
	}

	/**
	 * 删除用户
	 */
	public void delete(int id) {
		String sql = "DELETE FROM user WHERE id=:id";
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		getBySql(sql, map);
	}

	/**
	 * 获取所有用户信息
	 */
	public List<User> getAll() {
		String hql = "FROM User";
		List<User> users = getSession().createQuery(hql).list();
		if (users == null || users.size() == 0) {
			users = null;
		}
		return users;
	}

}
