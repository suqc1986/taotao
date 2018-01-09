package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.jedis.service.JedisClient;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	

	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//���ɶ����ţ�����ʹ��redis��incr��������
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			//���ó�ʼֵ
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//��Ҫ��ȫpojo�����ԣ������Ķ��Ǵ�ҳ�洫�ݹ�����
		orderInfo.setOrderId(orderId);
		//����״̬��1��δ���2���Ѹ��3��δ������4���ѷ�����5�����׳ɹ���6�����׹رգ��տ�ʼ�϶���δ����
		orderInfo.setStatus(1);
		//��������ʱ��
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//�򶩵���������ݣ�����OrderInfo�̳���TbOrder���������ſ���ֱ�Ӱ�orderInfo��Ϊ����
		tbOrderMapper.insert(orderInfo);
		//�򶩵���ϸ���������
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//�����ϸ��������һ��ʹ��ORDER_ITEM_ID_GEN_KEY���key����û�г�ʼֵ�ģ���ô���Զ�����ʼֵ��Ϊ1
			String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			//����֮����ֻ�������������ԣ�����ΪtbOrderItem�����Ѿ���itemId�ˡ�
			tbOrderItem.setId(oid);//���Ƕ�����ϸ�������
			tbOrderItem.setOrderId(orderId);
			//������ϸ����
			tbOrderItemMapper.insert(tbOrderItem);
		}
		//�򶩵��������������
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		tbOrderShippingMapper.insert(orderShipping);
		//���ض�����
		return TaotaoResult.ok(orderId);
	}

}
