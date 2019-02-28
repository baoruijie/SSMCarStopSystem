package baow.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class RequestEncodingFilter
 */
public class RequestEncodingFilter implements Filter {

	private String encoding;
    /**
     * Default constructor. 
     */
    public RequestEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		encoding="UTF-8";
		String enc=fConfig.getInitParameter("encoding");
		System.out.println("baow.filter.RequestEncodingFilter-->"+enc+"---"+fConfig.toString());
		if(enc!=null&&enc.length()>0){
			encoding=enc;
			
		}
	}

}
