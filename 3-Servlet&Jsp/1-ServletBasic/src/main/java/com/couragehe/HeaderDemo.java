package com.couragehe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ������Ϣ�Ļ�ȡ
 * getParameter("name") String
 * getParameterValues("name") String[]
 * getParameterNames() Enumeration
 * getHeader("name") ����ͷ
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
				request.getContextPath()//��ȡӦ�ó��򻷾�·��
				+"</h1>");
		Enumeration e = request.getHeaderNames();//��ȡ����ͷ����
		while(e.hasMoreElements()) {
			String param =(String )e.nextElement();
			out.println(param+":"+request.getHeader(param)+"<br/>");//�������ͷ��ֵ
		}
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
