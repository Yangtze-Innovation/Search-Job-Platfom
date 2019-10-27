package com.couragehe;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��һ��servlet��д
 * @author 52423
 *
 */
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HelloServlet() {
    	
    }
    /**
     * �������յ��ͻ��˵�HTTP����󣬻��ռ�HTTP�����е���Ϣ�����ֱ𴴽�������������Ӧ��JAVA����
     * �����ڵ���doGet()ʱ����������������������
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��֪����������ص���ӦҪ��text/html�����������õı���ʱUTF8
		response.setContentType("text/html;charset=utf-8");
		//��ȡ������Ӧ�����PrintWriter����
		PrintWriter out = response.getWriter();
		//ȡ���û����͵��������ֵ
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

