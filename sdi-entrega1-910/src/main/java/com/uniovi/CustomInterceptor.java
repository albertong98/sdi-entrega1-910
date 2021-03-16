package com.uniovi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

public class CustomInterceptor implements HandlerInterceptor{
	@Autowired
	private UsersService usersService;
	
	@Override
    public void postHandle(final HttpServletRequest request,final HttpServletResponse response, final Object handler,final ModelAndView modelAndView) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
			String email = auth.getName();
			
	        if (modelAndView != null && !email.equals("anonymousUser")) {
	        	User user = usersService.getUserByEmail(email);
	            modelAndView.getModelMap().addAttribute("loggedUser",user);
	       
		}
    }
}
