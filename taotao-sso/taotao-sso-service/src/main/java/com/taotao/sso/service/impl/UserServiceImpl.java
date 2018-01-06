package com.taotao.sso.service.impl;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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

	@Override
	public TaotaoResult register(TbUser tbUser) {
		//检查数据有效性  
        if(StringUtils.isBlank(tbUser.getUsername())){  
            return TaotaoResult.build(400, "用户名不能为空！");  
        }  
        TaotaoResult taotaoResult = checkUserData(tbUser.getUsername(), 1);  
        if(!(Boolean)taotaoResult.getData()){  
            return TaotaoResult.build(400, "用户名不能重复！");  
        }  
        if(StringUtils.isBlank(tbUser.getPassword())){  
            return TaotaoResult.build(400, "密码不能为空！");  
        }  
        if(StringUtils.isNotBlank(tbUser.getPhone())){  
            //如果电话不为空，那么接着判断是否重复，电话是不能重复的  
            taotaoResult = checkUserData(tbUser.getPhone(), 2);  
            if(!(Boolean)taotaoResult.getData()){  
                return TaotaoResult.build(400, "电话不能重复！");  
            }  
        }  
        if(StringUtils.isNotBlank(tbUser.getEmail())){  
            //如果邮箱不为空，那么接着判断是否重复，邮箱也是不能重复的  
            taotaoResult = checkUserData(tbUser.getEmail(), 3);  
            if(!(Boolean)taotaoResult.getData()){  
                return TaotaoResult.build(400, "邮箱不能重复！");  
            }  
        }  
        //填充属性  
        tbUser.setCreated(new Date());  
        tbUser.setUpdated(new Date());  
        //密码要进行Md5加密，我们不用添加额外的jar包，只需要使用Spring自带的包就可以了  
        String md5Str = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());  
        tbUser.setPassword(md5Str);  
        //添加  
        tbUserMapper.insert(tbUser);  
        return TaotaoResult.ok();  
	}

}
