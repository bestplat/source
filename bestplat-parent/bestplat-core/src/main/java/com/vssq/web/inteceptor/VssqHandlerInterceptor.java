package com.vssq.web.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class VssqHandlerInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ModelMap mm = modelAndView == null ? null : modelAndView.getModelMap();
		if (mm != null) {
			// String cp = request.getScheme() + "://"
			// + InetAddress.getLocalHost().getHostAddress() + ":"
			// + request.getLocalPort() + request.getContextPath();
			String cp = request.getContextPath();
			mm.addAttribute("cp", cp);
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
