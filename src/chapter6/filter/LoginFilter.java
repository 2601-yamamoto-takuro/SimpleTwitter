package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/edit", "/setting"})

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest HttpServletRequest = (HttpServletRequest) request;
		HttpServletResponse HttpServletResponse = (HttpServletResponse) response;
		HttpSession session = HttpServletRequest.getSession();
	    User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add("ログインしてください");
            HttpServletRequest.getSession().setAttribute("errorMessages", errorMessages);
            HttpServletResponse.sendRedirect("./login");
            return;
        }

		chain.doFilter(HttpServletRequest, HttpServletResponse);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
