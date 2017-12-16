package com.taotao.controller;  
  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.ResponseBody;  
  
import com.taotao.common.pojo.EasyUIDataGridResult;  
import com.taotao.common.pojo.TaotaoResult;  
import com.taotao.content.service.ContentService;  
import com.taotao.pojo.TbContent;  
  
@Controller  
public class ContentController {  
      
    @Autowired  
    private ContentService contentSerive;  
      
    @RequestMapping("/content/query/list")  
    @ResponseBody  
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows){  
        EasyUIDataGridResult result = contentSerive.getContentList(categoryId, page, rows);  
        return result;  
    }  
      
    @RequestMapping("/content/save")  
    @ResponseBody  
    public TaotaoResult addContent(TbContent content){  
        TaotaoResult result = contentSerive.addContent(content);  
        return result;  
    }  
      
    @RequestMapping("/rest/content/edit")  
    @ResponseBody  
    public TaotaoResult updateContent(TbContent content){  
        TaotaoResult result = contentSerive.updateContent(content);  
        return result;  
    }  
      
    @RequestMapping("/content/delete")  
    @ResponseBody  
    public TaotaoResult deleteContent(String ids){  
        TaotaoResult result = contentSerive.deleteContent(ids);  
        return result;  
    }  
      
    @RequestMapping("/content/getContent")  
    @ResponseBody  
    public TaotaoResult getContent(Long id){  
        TaotaoResult result = contentSerive.getContent(id);  
        return result;  
    }  
}  