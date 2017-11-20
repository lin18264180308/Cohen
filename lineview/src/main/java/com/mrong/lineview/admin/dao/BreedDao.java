package com.mrong.lineview.admin.dao;

import java.util.List;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.common.entity.Breed;

public interface BreedDao extends IBaseDao {

    public void add(Breed b);

    public void delete(Breed b);

    public List<Breed> getAll();

    public void edit(Breed b);

}
