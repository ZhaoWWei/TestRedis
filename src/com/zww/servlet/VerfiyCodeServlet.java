package com.zww.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utils.VerifyCodeConfig;

/**
 * Servlet implementation class VerfiyCodeServlet
 */
public class VerfiyCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerfiyCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到电话
		//得到验证码
		//判断是否为空
		String phone_no = request.getParameter("phone_no");
		String verify_code = request.getParameter("verify_code");
		if (verify_code == null || phone_no == null) {
			return;
		}
		//查找到用户的验证吗
		Jedis jedis = new Jedis("192.168.122.128", 6379);
		String phoneKey = VerifyCodeConfig.PHONE_PREFIX + phone_no + VerifyCodeConfig.PHONE_SUFFIX;//生成前缀
		String exceptedCode = jedis.get(phoneKey);
		//判断是否一致
		if (verify_code.equals(exceptedCode)) {
			response.getWriter().write("true");
			return;
		}
		
	}

}
