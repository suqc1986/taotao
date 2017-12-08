package com.taotao.service;

import com.taotao.common.pojo.EasyUIDDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	//根据商品ID来查询商品
	TbItem getItemById(long itemId);
	//获取商品列表
	EasyUIDDataGridResult getItemList(int page,int rows);
	//添加商品
	TaotaoResult createItem(TbItem tbItem,String desc) throws Exception;
	
}
