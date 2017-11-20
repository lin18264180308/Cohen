package com.mrong.lineview.common.service.impl;

import java.util.List;

import com.mrong.lineview.common.dao.HaltDao;
import com.mrong.lineview.common.dao.OnlineDao;
import com.mrong.lineview.common.entity.Online;
import com.mrong.lineview.common.service.OnlineService;

/**
 * 整线一览service实现类
 * 
 * @author 张裕宝
 */
public class OnlineServiceImpl implements OnlineService {

    private HaltDao haltDao;

    public void setHaltDao(HaltDao haltDao) {
        this.haltDao = haltDao;
    }

    private OnlineDao onlinehistoryDao;

    public void setOnlinehistoryDao(OnlineDao onlinehistoryDao) {
        this.onlinehistoryDao = onlinehistoryDao;
    }

    /* 具体实现方法 */
    public Online getState() {
        Online online = haltDao.getState();
        return online;
    }

    /* 具体实现方法 */
    @Override
    public List<Integer> initStatus(String machine_name) {
        String halfSql = "";
        switch (machine_name) {
        case "1":
            halfSql = "SELECT bottle_washer FROM four_machine WHERE four_time BETWEEN :start AND :end";
            break;
        case "2":
            halfSql = "SELECT filling_mechine FROM four_machine WHERE four_time BETWEEN :start AND :end";
            break;
        case "3":
            halfSql = "SELECT sterilization_machine FROM four_machine WHERE four_time BETWEEN :start AND :end";
            break;
        case "4":
            halfSql = "SELECT labeling_machine FROM four_machine WHERE four_time BETWEEN :start AND :end";
            break;
        /* case "贴标机--2：":
         * machine_name = "LABELING_MACHINE2";
         * break; */
        default:
            System.err
                    .println("------------------------------------>default-------------------------------------------");
            break;
        }
        List<Integer> initDatas = onlinehistoryDao.initStatus(halfSql);
        System.err.println("------------SERVICE-----------" + initDatas + "---------------------------------");
        return initDatas;
    }

    /**
     * 获取当前机台的瞬时状态
     */
    @Override
    public Integer currentStatus(String machine_name) {
        String sql = null;
        switch (machine_name) {
        case "1":
            sql = "select bottle_washer from Real_time_four_machine;";
            break;
        case "2":
            sql = "select filling_mechine from Real_time_four_machine;";
            break;
        case "3":
            sql = "select sterilization_machine from Real_time_four_machine";
            break;
        case "4":
            sql = "select labeling_machine from Real_time_four_machine";
            break;
        default:
            break;
        }
        Integer result = onlinehistoryDao.currentStatus(sql);
        return result;
    }

}
