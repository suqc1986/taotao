package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	TaotaoResult checkUserData(String data,int type);
	TaotaoResult register(TbUser tbUser);
	TaotaoResult login(String username,String password);
}
