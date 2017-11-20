package com.mrong.lineview.common.entity;

import java.util.Date;

public class Breed {
	private int id;
	private String name;
	private Date updateTime;
	private String updateOperator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

	@Override
	public String toString() {
		return "Breed [id=" + id + ", name=" + name + ", updateTime=" + updateTime + ", updateOperator="
				+ updateOperator + "]";
	}

}
