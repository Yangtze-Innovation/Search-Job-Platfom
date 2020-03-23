package com.couragehe;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第一个servlet编写
 * @author 52423
 *
 */
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HelloServlet() {
    	
    }
    /**
     * 容器接收到客户端的HTTP请求后，会收集HTTP请求中的信息，并分别创建代表请求与相应的JAVA对象
     * 而后在调用doGet()时讲这两个对象当作参数传入
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//告知浏览器，返回的响应要以text/html解析，而采用的编码时UTF8
		response.setContentType("text/html;charset=utf-8");
		//获取代表响应输出的PrintWriter对象，
		PrintWriter out = response.getWriter();
		//取得用户发送的请求参数值
		String name = request.getParameter("name");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>HelloServlet</title>");
		out.println("<head>");
		out.println("<body>");
		out.println("<h1>Hello !"+name+"</h1>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

