package com.adamant.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class XFilter implements Filter {
	private FilterConfig config; //FilterConfig可以获取部署描述符文件（web.xml）中分配的过滤器初始化参数
	
	private String param1; //web.xml文件中设置的参数
	private String param2; //web.xml文件中设置的参数
	
	@Override
	public void destroy() { //销毁
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException { //过滤
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		
		System.out.println("xxxxxxxxxxxxparam1:"+param1);
		System.out.println("xxxxxxxxxxxxparam2:"+param2);
		
		/*
		   getRequestURL 方法返回客户端发出请求时的完整URL。
		　　getRequestURI 方法返回请求行中的资源名部分。
		　　getQueryString 方法返回请求行中的参数部分。
		　　getPathInfo 方法返回请求URL中的额外路径信息。额外路径信息是请求URL中的位于Servlet的路径之后和查询参数之前的内容，它以“/”开头。
		　　getRemoteAddr 方法返回发出请求的客户机的IP地址。
		　　getRemoteHost 方法返回发出请求的客户机的完整主机名。
		　　getRemotePort 方法返回客户机所使用的网络端口号。
		　　getLocalAddr 方法返回WEB服务器的IP地址。
		　　getLocalName 方法返回WEB服务器的主机名。
		 */
		//获取用户请求URL
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		
		/*
		//创建类Constants.java，里面写的是无需过滤的页面
        for (int i = 0; i < Constants.NoFilter_Pages.length; i++) {

            if (url.indexOf(Constants.NoFilter_Pages[i]) > -1) {
            	filterChain.doFilter(request, response);
                return;
            }
        }*/
        
		
		//从session中获取过滤所需信息。例如：用户id等
		String userId = (String) session.getAttribute("userId");
		
		// 登陆页面无需过滤
        if(uri.indexOf("/index.jsp") > -1) {
        	System.out.println("index.jsp页面 无需过滤");
        	filterChain.doFilter(request, response);
            return;
        }
		
        if (userId==null || "".equals(userId)) {//未登陆处理
        	// 跳转到登陆页面
//        	String indexUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index.jsp";
//        	response.sendRedirect(indexUrl);
        	System.out.println("======================XFilter：判断为未登陆");
        	
        	filterChain.doFilter(request, response);//xxxxxxx未登陆则不可继续此次请求，实际需注释掉
		} else {//登陆的处理：继续此次请求
			//执行下一个过滤器
			filterChain.doFilter(request, response);
		}
		
//		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { //初始化
		this.config = filterConfig;
		
		param1 = config.getInitParameter("param1");
		param2 = config.getInitParameter("param2");
		ServletContext servletContext = config.getServletContext();
		System.out.println("ServletContext:"+servletContext);
		
		System.out.println("param1:"+param1);
		System.out.println("param2:"+param2);
	}

}
