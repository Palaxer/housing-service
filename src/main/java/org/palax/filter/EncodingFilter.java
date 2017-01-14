package org.palax.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * The {@code EncodingFilter} class implements {@link Filter} and used to set
 * {@code UTF-8} character encoding for all requests
 *
 * @author Taras Palashynskyy
 */

public class EncodingFilter implements Filter {

    /**
     * Filtering all requests and set {@code UTF-8} character encoding
     *
     * @param request {@code request} all http request represent by {@link ServletRequest}
     * @param response {@code response} all http response represent by {@link ServletResponse}
     * @param chain {@code chain} filter chain represents by {@link FilterChain}
     * @throws IOException {@link IOException}
     * @throws ServletException {@link ServletException}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }
}
