package com.whalien207.myweb.util.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class UserAuthHandler implements HandlerInterceptor{
	
	/*
	 * 1. HandlerInterceptor를 상속 받는다.
	 * 
	 * preHandle - 컨트롤러 진입전에 실행
	 * postHandle - 컨트롤러 수행후에 실행
	 * aftercomplate - 화면으로 가기 직전에 수행
	 * 
	 * 
	 * 2. 인터셉터 클래스를 bean으로 등록
	 * 
	 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("인터셉터실행");
		
		//현재 세션을 얻어온다.
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
//		System.out.println(user_id);
		
		//로그인 안된 상태
		if(user_id == null) {
			response.sendRedirect(request.getContextPath() + "/user/login"); //로그인페이지로 리다이렉션
			return false; //컨트롤러를 실행하지 않도록 해준다.
		}
		
		
		return true; //컨트롤러가 그대로 실행
	}

}
