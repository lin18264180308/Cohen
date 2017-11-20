package com.mrong.lineview.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckLogin implements Interceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 6241078124772719025L;

    public void destroy() {
        // System.out.println("------CheckLogin.destroy------");
    }

    public void init() {
        // System.out.println("------CheckLogin.init------");

    }

    public String intercept(ActionInvocation arg0) throws Exception {

        // System.out.println("------CheckLogin.intercept------");

        Map<String, Object> session = ActionContext.getContext().getSession();

        String userName = (String) session.get("userName");

        if ("com.mrong.lineview.common.action.UserAction".equals(arg0.getAction().getClass().getName())) {
            if (userName == null) {
                return arg0.invoke();
            }
        } else if (userName != null) {
            return arg0.invoke();
        }

        return "login";
    }

}
