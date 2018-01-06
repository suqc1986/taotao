package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {
	TaotaoResult checkUserData(String data,int type);
}
