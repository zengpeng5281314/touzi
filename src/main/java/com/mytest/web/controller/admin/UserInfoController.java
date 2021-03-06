package com.mytest.web.controller.admin;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mytest.admin.po.TUserInfoPo;
import com.mytest.admin.service.UserInfoService;
import com.mytest.web.controller.base.BaseController;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("")
public class UserInfoController extends BaseController {

	Log logger = LogFactory.getLog(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/login")
	@Transactional
	public ModelAndView index(@RequestParam(defaultValue = "", required = false, value = "username") String username,
			@RequestParam(defaultValue = "", required = false, value = "password") String password,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		if (request.getMethod().equals("POST")) {
			TUserInfoPo adminInfo = userInfoService.getTUserInfoPoByOpenId(username, password);
			Cookie autoCookie = null;
			if (adminInfo != null && adminInfo.getType() == 1) {
				String sef = URLEncoder.encode(JSON.toJSONString(adminInfo), "utf-8");
				// 声明cookie
				autoCookie = new Cookie("adminInfo", sef);
				autoCookie.setMaxAge(5);//秒
				response.addCookie(autoCookie);
				return new ModelAndView("redirect:/admin/index");
			} else {
				model.put("erroMsg", "用户名密码错误！");
			}
		}

		return new ModelAndView("/login", model);
	}

	@RequestMapping("/dologin")
	@Transactional
	@ResponseBody
	public String dologin(@RequestParam(defaultValue = "", required = false, value = "username") String username,
			@RequestParam(defaultValue = "", required = false, value = "password") String password,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		TUserInfoPo adminInfo = userInfoService.getTUserInfoPoByOpenId(username, password);
		Cookie autoCookie = null;
		if (adminInfo != null && adminInfo.getType() == 1) {
			String sef = URLEncoder.encode(JSON.toJSONString(adminInfo), "utf-8");
			// 声明cookie
			autoCookie = new Cookie("adminInfo", sef);
			autoCookie.setMaxAge(60*60*24*31);//秒
			response.addCookie(autoCookie);
			return successJson("登录成功", null);
		} else {
			return errorJson("用户名密码不正确");
		}

	}

	@RequestMapping("/signout")
	@Transactional
	public ModelAndView signOut(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		TUserInfoPo adminInfo = (TUserInfoPo) request.getAttribute("adminInfo");
		if (adminInfo != null) {
			String sef = null;
			// 声明cookie
			Cookie autoCookie = new Cookie("adminInfo", sef);
			autoCookie.setMaxAge(0);
			response.addCookie(autoCookie);
		}
		return new ModelAndView("redirect:/login", model);
	}

}
