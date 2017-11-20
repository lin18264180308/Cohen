package com.mrong.lineview.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SetCharacterEncodingFilter
 * @Author NiWeiDaYe
 * @Description: 过滤全站中文问题(GET&POST请求)
 * @Return_param:
 * @Date: 2017-07-31
 */
public class SetCharacterEncodingFilter implements Filter {
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SetCharacterEncodingFilter（全站中文过滤器）初始化！");
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) resp;
		} catch (Exception e) {
			throw new RuntimeException("non-http request or response");
		}
		// 读取指定参数
		String encoding = filterConfig.getInitParameter("encoding");
		if (encoding == null) {
			// 没有配置参数--给一个默认值
			encoding = "UTF-8";
		}
		request.setCharacterEncoding(encoding);// 请求编码（只适合POST方式）
		response.setCharacterEncoding(encoding);
		response.setContentType("text/html;charset=" + encoding);// 服务器响应浏览器编码
		MyHttpServletRequest mRequest = new MyHttpServletRequest(request);
		chain.doFilter(mRequest, response);
	}

	@Override
	public void destroy() {
		System.out.println("SetCharacterEncodingFilter（全站中文过滤器）销毁！");
	}
}

/**
 * ServletRequestWrapper已经是包装类（装饰模式）
 * 
 * @author NiWeiDaYe
 * @date 2017-07-29
 * @description 解决GET请求中文问题过滤
 */
class MyHttpServletRequest extends HttpServletRequestWrapper {
	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 覆盖方法
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null) {
			return null;
		}
		if ("get".equalsIgnoreCase(super.getMethod())) {
			// 如果是GET提交方式
			try {
				// super.getCharacterEncoding()获取设置的编码集（不仅仅是UTF-8）
				value = new String(value.getBytes("ISO-8859-1"),super.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return value;
	}
}
