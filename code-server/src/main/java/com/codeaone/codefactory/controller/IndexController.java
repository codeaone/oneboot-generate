package com.codeaone.codefactory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.codeaone.codefactory.entity.BusUserInfo;

@Controller
public class IndexController extends BaseController{
	private static final long JS_VERSION = System.currentTimeMillis();

	private static final String SYS_TITLE_KEY = "SYS_TITLE";

	private String titleName = "code";

	private String appHost = "";

	@GetMapping(value = { "/page", "/page/**" })
	public ModelAndView showAdmin(HttpServletRequest request, String roleId) {
		ModelAndView view = new ModelAndView("index");
		view.addObject("JS_VERSION", JS_VERSION);
		if(true)
		return view;
		
		BusUserInfo userInfo = new BusUserInfo();
		userInfo.setUsername("admin");
		String userId = "1";
		/*try {
			OsUser user = getUserDetails().getUser();
			userInfo.setUsername(user.getName());
			userInfo.setUserType(user.getUserType());
			userId = user.getUserId();
			if (StringUtils.equals(user.getUserType(), "client")) {
				LoggerUtil.warn("客户端的账号不能登录后台：{}", user);
				throw new ObootException(CommonResultCode.DATA_CHCEK_FAIL, "登录账号不存在");
			}
		} catch (Exception e) {
			view = new ModelAndView("redirect:/page/login");
			return view;
		}*/

		view.addObject("APPHOST", appHost);
		view.addObject("user", JSON.toJSONString(userInfo));

		
		view.addObject(SYS_TITLE_KEY, titleName);
		view.addObject("SYS_HOST", appHost);

		return view;
	}
	
	
}
