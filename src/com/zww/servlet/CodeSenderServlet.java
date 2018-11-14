package com.zww.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import utils.VerifyCodeConfig;

/**
 * Servlet implementation class CodeSenderServlet
 */
public class CodeSenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeSenderServlet() {
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
		//获得电话号码
		String phone_no = request.getParameter("phone_no");
		if (phone_no == null) {
			return ;
		}
		
		//判断发送了几次
		Jedis jedis = new Jedis("192.168.122.128", 6379);
		String countKey = VerifyCodeConfig.PHONE_PREFIX + phone_no + VerifyCodeConfig.PHONE_SUFFIX;
		String countValue = jedis.get(countKey);
		//如果值大于三
		if(countValue == null) {
			//第一次输入
			jedis.setex(countKey, VerifyCodeConfig.SECONDS_PER_DAY, "1");
		}else {
			//是否没有到三天
			int count = Integer.parseInt(countValue);
			if (count >= 3) {
				response.getWriter().write("limit");
				jedis.close();
				return ;
			}else {
				jedis.incr(countKey);
			}
		}
		
		//生成验证码
		String verifyCode = getCode(6);
		//存入库中去
		
		String phoneKey = VerifyCodeConfig.PHONE_PREFIX + phone_no + VerifyCodeConfig.PHONE_SUFFIX;//生成前缀
		jedis.setex(phoneKey, VerifyCodeConfig.CODE_TIMEOUT, verifyCode);//设置过期时间
		//发送验证码
		System.out.println(verifyCode);
		jedis.close();
		//返回页面
		response.getWriter().write("true");
	}
	
	/**
	 * 生成验证码
	 * @param number
	 * @return
	 */
	public String getCode(int number) {
		String codeString = "";
		for (int i = 0; i < number; i++) {
			int rand = new Random().nextInt(10);
			codeString += rand + "";
		}
		return codeString;
	}

}
