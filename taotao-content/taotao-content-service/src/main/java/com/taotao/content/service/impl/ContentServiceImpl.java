package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service  
public class ContentServiceImpl implements ContentService {  
    @Autowired  
    private TbContentMapper contentMapper;  
  
    @Override  
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {  
        //设置分页信息  
        PageHelper.startPage(page, rows);  
        //执行查询  
        TbContentExample example = new TbContentExample();  
        Criteria createCriteria = example.createCriteria();  
        createCriteria.andCategoryIdEqualTo(categoryId);  
        //获取查询结果  
        List<TbContent> list = contentMapper.selectByExample(example);  
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);  
        EasyUIDataGridResult result = new EasyUIDataGridResult();  
        result.setRows(list);  
        result.setTotal(pageInfo.getTotal());  
        //返回结果  
        return result;   
    }  
  
    @Override  
    public TaotaoResult addContent(TbContent content) {  
        //补充属性  
        content.setCreated(new Date());  
        content.setUpdated(new Date());  
        //添加  
        contentMapper.insert(content);  
        //返回结果  
        return TaotaoResult.ok();  
    }  
  
    @Override  
    public TaotaoResult updateContent(TbContent content) {  
        // 填充属性  
        content.setUpdated(new Date());  
        //更新内容  
        contentMapper.updateByPrimaryKey(content);  
        //返回结果  
        return TaotaoResult.ok();  
    }  
  
    @Override  
    public TaotaoResult deleteContent(String ids) {  
        String[] idList = ids.split(",");  
        for(String id : idList){  
            //删除内容  
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));  
        }  
        //返回结果  
        return TaotaoResult.ok();  
    }  
  
    @Override  
    public TaotaoResult getContent(long id) {  
        TbContent content = contentMapper.selectByPrimaryKey(id);  
        return TaotaoResult.ok(content);  
    }

	@Override
	public List<TbContent> getContentListByCid(long cid) {
		 TbContentExample example = new TbContentExample();  
	        Criteria criteria = example.createCriteria();  
	        criteria.andCategoryIdEqualTo(cid);  
	        List<TbContent> list = contentMapper.selectByExample(example);  
	        return list;  
	}  
  
}  