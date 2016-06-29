package com.itheima.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Meiling on 2016/6/25.
 */
public class EncoderFilter implements Filter {
    private FilterConfig filterConfig = null;
    private ServletContext servletContext = null;
    private String encode = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

        this.servletContext = filterConfig.getServletContext();
        this.encode = servletContext.getInitParameter("encode");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encode);
        servletResponse.setContentType("text/html;charset=" + encode); // --解决响应乱码
        filterChain.doFilter(new MyHttpServletRequest((HttpServletRequest) servletRequest),
                servletResponse);// --包装改造request中和获取请求参数相关的方法解决请求参数乱码
        System.out.println("过滤器===========================================");
    }

    @Override
    public void destroy() {

    }

    class MyHttpServletRequest extends HttpServletRequestWrapper {
        private HttpServletRequest request = null;
        private boolean isNotEncode = true;//第一次进来时，会组织map，对于get请求，用

        public MyHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public Map getParameterMap() {
            try {
                if (request.getMethod().equalsIgnoreCase("POST")) {// --如果是post提交,一行代码解决post提交请求参数乱码
                    request.setCharacterEncoding(encode);
                    return request.getParameterMap();
                } else if (request.getMethod().equalsIgnoreCase("GET")) {// --如果是get提交,则应该手动编解码解决乱码
                    Map<String, String[]> map = request.getParameterMap();// 获取有乱码的map
                    if (isNotEncode) {// 只能在第一次解决乱码
                        for (Map.Entry<String, String[]> entry : map.entrySet()) {// 遍历map,解决所有值的乱码
                            String[] vs = entry.getValue();
                            for (int i = 0; i < vs.length; i++) {
                                vs[i] = new String(vs[i].getBytes("iso8859-1"),
                                        encode);
                            }
                        }
                        isNotEncode = false;// 设置为false,第二次就不会再进这个代码块了
                    }
                    return map;
                } else {
                    return request.getParameterMap();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        @Override
        public String[] getParameterValues(String name) {
            return (String[]) getParameterMap().get(name);
        }

        @Override
        public String getParameter(String name) {
            return getParameterValues(name) == null ? null
                    : getParameterValues(name)[0];
        }

    }
}
