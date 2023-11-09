package com.ssafy.rollinghealer.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.ssafy.rollinghealer.member.UserDto;

public class ConfirmInterceptorAJAX extends WebContentInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		boolean result = false;
		
		HttpSession session = request.getSession();
		UserDto memberDto = (UserDto) session.getAttribute("userinfo");
		if (memberDto == null) {
			if(isAjaxRequest(request)) {
				try {
					/*
					 * ajax호출시 설정.
					 * beforeSend : function(xmlHttpRequest) {
					 * 	xmlHttpRequest.setRequestHeader("AJAX", "true");
					 * },
					 * success : function(data) {
					 * 	data 처리
					 * },
					 * error : function(e) {
					 * 	if (e.status == 400) {
					 * 		error처리
					 * 	}
					 * }
					 */
					response.sendError(400);
					result = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.sendRedirect(request.getContextPath() + "/user/login");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			result = true;
		return result;
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		String header = req.getHeader("AJAX");
		if ("true".equals(header)) {
			return true;
		} else {
			return false;
		}
	}

}
