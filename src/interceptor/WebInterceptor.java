package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WebInterceptor implements HandlerInterceptor {

	private static Logger logger = Logger.getLogger(WebInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		logger.info(String.format("request: %s", request.getRequestURI()));
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		response.setCharacterEncoding("UTF-8");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {

	}

}
