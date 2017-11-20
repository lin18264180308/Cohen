package com.mrong.lineview.common.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 林金成
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ApplicationAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3187526632888713958L;

	protected static final String JSON = "json";

	protected HttpServletRequest request;
	protected HttpSession session;
	protected Map<String, Object> application;

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		session = request.getSession();
	}

	@Override
	public void setApplication(Map<String, Object> arg0) {
		this.application = arg0;

	}
}
