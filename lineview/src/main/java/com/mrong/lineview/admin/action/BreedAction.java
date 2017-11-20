package com.mrong.lineview.admin.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.service.BreedService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Breed;

public class BreedAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 7615144511592395362L;

    private Breed breed;
    private List<Breed> breeds;
    private BreedService breedService;

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public void setBreedService(BreedService breedService) {
        this.breedService = breedService;
    }

    /**
     * 品种增
     */
    public String add() {
        breed.setUpdateOperator((String) session.getAttribute("opernatename"));
        breed.setUpdateTime(new Date());
        breedService.add(breed);
        return SUCCESS;
    }

    /**
     * 删
     */
    public String delete() {
        breedService.delete(breed);
        return SUCCESS;
    }

    /**
     * 查
     */
    public String getAll() {
        breeds = breedService.getAll();
        return SUCCESS;
    }

    /**
     * 改
     */
    public String edit() {
        breed.setUpdateOperator((String) session.getAttribute("opernatename"));
        breed.setUpdateTime(new Date());
        breedService.edit(breed);
        return SUCCESS;
    }

}
