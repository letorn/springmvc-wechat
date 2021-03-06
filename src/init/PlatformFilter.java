package init;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Admin;

import org.springframework.web.filter.OncePerRequestFilter;

public class PlatformFilter extends OncePerRequestFilter {

	private static Set<String> whiteSet = new HashSet<String>();

	static {
		whiteSet.add("/platform/login.jsp");
		whiteSet.add("/platform/login.do");
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String ctx = request.getContextPath();
		String uri = request.getRequestURI().substring(ctx.length());
		if ((uri.endsWith(".jsp") || uri.endsWith(".do")) && !whiteSet.contains(uri)) {
			HttpSession session = request.getSession();
			Admin admin = (Admin) session.getAttribute("admin");
			if (admin == null) {
				response.sendRedirect(ctx + "/platform/login.jsp");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
