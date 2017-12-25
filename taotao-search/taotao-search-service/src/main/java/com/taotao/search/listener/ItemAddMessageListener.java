package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;

public class ItemAddMessageListener implements MessageListener {
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		try {
			//从消息中取出商品id
			TextMessage textMessage = (TextMessage)message;
			String text = textMessage.getText();
			long itemId = Long.valueOf(text);
			//根据商品id查询商品详情，这里需要注意的是消息发送方法
			//有可能还没有提交事务，因此这里是有可能取不到商品信息
			//的，为了避免这种情况出现，我们最好等待事务提交，这里
			//我采用3次尝试的方法，每尝试一次休眠一秒
			SearchItem searchItem = null;
			for(int i=0;i<3;i++){
				try {
					Thread.sleep(1000);//休眠一秒
					searchItem = searchItemMapper.getItemById(itemId);
					//如果获取到了商品信息，那就退出循环。
					if(searchItem != null){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			//向文档对象中添加域
			document.setField("id", searchItem.getId());
			document.setField("item_title", searchItem.getTitle());
			document.setField("item_sell_point", searchItem.getSell_point());
			document.setField("item_price", searchItem.getPrice());
			document.setField("item_image", searchItem.getImage());
			document.setField("item_category_name", searchItem.getItem_category_name());
			document.setField("item_desc", searchItem.getItem_desc());
			//把文档写入索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
