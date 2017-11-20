package com.mrong.lineview.common.action;

import com.mrong.lineview.common.entity.Online;
import com.mrong.lineview.common.service.OnlineService;

/**
 * 整线一览
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class OnlineAction extends BaseAction {
	
	private Online online;
	
    public Online getOnline() {
		return online;
	}

	public void setOnline(Online online) {
		this.online = online;
	}

	private OnlineService onlineService;

    public void setOnlineService(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    public String getState() {
    	
        online = onlineService.getState();
        if (online == null) {
            session.setAttribute("online", "获取的对象为 空，请检查服务器或者程序！");
        } else {

        }
        return "success";
    }

}
