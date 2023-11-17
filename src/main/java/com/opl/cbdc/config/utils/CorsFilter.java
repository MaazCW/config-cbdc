
package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.config.*;
import org.springframework.stereotype.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Component
public class CorsFilter implements Filter {


	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		boolean result = CorsFilterUtils.setHeader(response, request);
		if (!result) {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
		// Do nothing because of X and Y.
	}

	public void destroy() {
		// Do nothing because of X and Y.
	}

}

