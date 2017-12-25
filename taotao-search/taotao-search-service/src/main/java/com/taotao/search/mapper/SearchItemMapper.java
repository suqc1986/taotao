package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

public interface SearchItemMapper {
	//获取要导入索引库的数据
	public List<SearchItem> getSearchItemList();
	//根据商品ID查询商品详情
	public SearchItem getItemById(long itemId);
}
