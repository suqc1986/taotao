package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
	@RequestMapping("/register")
	public String showRegister(){
		return "register";
	}
	@RequestMapping("/login")
	public String showLogin(String url,Model model){
		model.addAttribute("redirect", url);
		return "login";
	}
}
