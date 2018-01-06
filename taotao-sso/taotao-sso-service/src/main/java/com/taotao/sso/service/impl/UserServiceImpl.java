package com.taotao.sso.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Override
	public TaotaoResult checkUserData(String data, int type) {
		//设置查询条件  
        TbUserExample example = new TbUserExample();  
        Criteria criteria = example.createCriteria();  
        //1.判断用户名是否可用  
        if(type == 1){  
            criteria.andUsernameEqualTo(data);  
        } else if(type == 2){  
            //2.判断电话是否可用  
            criteria.andPhoneEqualTo(data);  
        } else if(type == 3){  
            //3.判断邮箱是否可用  
            criteria.andEmailEqualTo(data);  
        } else {  
            return TaotaoResult.build(400, "所传参数非法！");  
        }  
        List<TbUser> list = tbUserMapper.selectByExample(example);  
        if(list != null && list.size() > 0){  
            return TaotaoResult.ok(false);  
        }   
          
        return TaotaoResult.ok(true);  
	}

}
