package io.github.erictowns.interfaces.filter;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: deal xss question
 *
 * @author EricTowns
 * @date 2023/10/17 17:10
 */
public class XssFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final XssRequestWrapper requestWrapper = new XssRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    static class XssRequestWrapper extends HttpServletRequestWrapper {
        public XssRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);
            if (values == null || values.length == 0) {
                return null;
            }
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
            }
            return encodedValues;
        }

        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            return StringEscapeUtils.escapeHtml4(value);
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return StringEscapeUtils.escapeHtml4(value);
        }
    }

}
