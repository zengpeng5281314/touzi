package com.mytest.web.controller.base;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mytest.admin.po.TUserInfoPo;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {

	Log logger = LogFactory.getLog(IndexController.class);

	@RequestMapping("/index")
	@Transactional
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean ismaster = false;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("adminInfo")) {
				String ad = URLDecoder.decode(cookie.getValue(), "utf-8");
				TUserInfoPo adminInfo = JSON.parseObject(ad, TUserInfoPo.class);
				if (adminInfo.getNickName().equals("admin"))
					ismaster = true;
				model.put("name", adminInfo.getNickName());
			}
		}
		model.put("ismaster", ismaster);
		return new ModelAndView("/index", model);
	}

}
