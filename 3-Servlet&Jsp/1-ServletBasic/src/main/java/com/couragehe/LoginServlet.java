package com.couragehe;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;
    public LoginServlet() {
        super();

    }

	@Override
	public void init() throws ServletException {
		super.init();
		SUCCESS_VIEW = this.getInitParameter("SUCCESS");
	    ERROR_VIEW = this.getInitParameter("ERROR");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html;charset=utf-8");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if("couragehe".equalsIgnoreCase(name)&& "123456".equalsIgnoreCase(password)) {
			request.getRequestDispatcher(SUCCESS_VIEW).forward(request, response);
		}else {
			request.getRequestDispatcher(ERROR_VIEW).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
