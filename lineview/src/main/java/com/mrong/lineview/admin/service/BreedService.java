package com.mrong.lineview.admin.service;

import java.util.List;

import com.mrong.lineview.common.entity.Breed;

public interface BreedService {

	public void add(Breed b);

	public void delete(Breed b);

	public List<Breed> getAll();

	public void edit(Breed b);

}
