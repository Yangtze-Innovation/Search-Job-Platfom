package com.couragehe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求信息的获取
 * getParameter("name") String
 * getParameterValues("name") String[]
 * getParameterNames() Enumeration
 * getHeader("name") 请求头
 * @author 52423
 *
 */
public class HeaderDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HeaderDemo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Servlet ShowHeader</title>");
		out.println("<head>");
		out.println("<body>");
		out.println("<h1>Hello showHeader at:"+
				request.getContextPath()//获取应用程序环境路径
				+"</h1>");
		Enumeration e = request.getHeaderNames();//获取请求头集合
		while(e.hasMoreElements()) {
			String param =(String )e.nextElement();
			out.println(param+":"+request.getHeader(param)+"<br/>");//输出请求头的值
		}
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
