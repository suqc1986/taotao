package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

public interface SearchItemService {
	//将数据导入索引库
	TaotaoResult importItemsToIndex();
}
