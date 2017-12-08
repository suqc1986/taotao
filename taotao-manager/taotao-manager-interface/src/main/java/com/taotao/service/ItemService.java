package com.taotao.service;

import com.taotao.common.pojo.EasyUIDDataGridResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	
	EasyUIDDataGridResult getItemList(int page,int rows);
}
