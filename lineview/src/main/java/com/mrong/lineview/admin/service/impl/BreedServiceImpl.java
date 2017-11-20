package com.mrong.lineview.admin.service.impl;

import java.util.List;

import com.mrong.lineview.admin.dao.BreedDao;
import com.mrong.lineview.admin.service.BreedService;
import com.mrong.lineview.common.entity.Breed;

public class BreedServiceImpl implements BreedService {

	private BreedDao breedDao;

	public void setBreedDao(BreedDao breedDao) {
		this.breedDao = breedDao;
	}

	public void add(Breed b) {
		breedDao.add(b);
	}

	public void delete(Breed b) {
		breedDao.delete(b);
	}

	public List<Breed> getAll() {
		List<Breed> breeds = breedDao.getAll();
		return breeds;
	}

	public void edit(Breed b) {
		breedDao.edit(b);
	}

}
