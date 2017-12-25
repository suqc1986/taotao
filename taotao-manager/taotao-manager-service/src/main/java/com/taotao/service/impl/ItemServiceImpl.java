package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired  
    private TbItemDescMapper itemDescMapper; 
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name="itemAddTopic")  
    private Destination destination;  
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	 @Override  
	    public TaotaoResult createItem(TbItem tbItem, String desc) throws Exception{  
	        //生成商品ID  
	        long itemId = IDUtils.genItemId();  
	        //补全item的属性  
	        tbItem.setId(itemId);  
	        //商品状态，1-正常，2-下架，3-删除  
	        tbItem.setStatus(((byte) 1));  
	        tbItem.setCreated(new Date());  
	        tbItem.setUpdated(new Date());  
	        itemMapper.insert(tbItem);  
	        //添加商品描述  
	        insertItemDesc(itemId, desc);  
	       //发送activemq消息  
	        jmsTemplate.send(destination,new MessageCreator() {  
	              
	            @Override  
	            public Message createMessage(Session session) throws JMSException {  
	                TextMessage textMessage = session.createTextMessage(itemId+"");  
	                return textMessage;  
	            }  
	        });  
	        return TaotaoResult.ok();  
	    }  
	//添加商品描述  
	    private void insertItemDesc(long itemId,String desc){  
	        //创建一个商品描述表对应的pojo  
	        TbItemDesc itemDesc = new TbItemDesc();  
	        //补全pojo的属性  
	        itemDesc.setItemId(itemId);  
	        itemDesc.setItemDesc(desc);  
	        itemDesc.setCreated(new Date());  
	        itemDesc.setUpdated(new Date());  
	        //向商品描述表插入数据  
	        itemDescMapper.insert(itemDesc);  
	    }  
}
