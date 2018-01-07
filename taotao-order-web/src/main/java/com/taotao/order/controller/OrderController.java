
package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbItem;

@Controller
public class OrderController {
	@Value("${CART_KEY}")
    private String CART_KEY;
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		//用户必须是登录状态
		//取用户ID
		//根据用户ID取收获地址列表，这里就使用静态数据了
		//把收货地址列表取出传递给页面
		//从cookie中取购物车商品列表展示到页面
		List<TbItem> cartList = getCartItemList(request);
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "order-cart";
	}
	
	private List<TbItem> getCartItemList(HttpServletRequest request){
		//从cookie中取购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);//为了防止乱码，统一下编码格式
		if(StringUtils.isBlank(json)){
			//说明cookie中没有商品列表，那么就返回一个空的列表
			return new ArrayList<TbItem>();
		}
		List<TbItem> list = JSON.parseArray(json, TbItem.class);
		return list;
	}
}

