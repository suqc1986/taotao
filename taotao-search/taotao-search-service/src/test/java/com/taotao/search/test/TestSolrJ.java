package com.taotao.search.test;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrJ {
	private static final String URL = "http://192.168.137.150:8080/solr/collection1";
	@Test  
    public void testAddDocument() throws Exception{  
        //创建一个SolrServer对象，创建一个HttpSolrServer对象，需要指定solr服务的url  
        //如果有多个collection则需要指定要操作哪个collection，如果只有一个，可以不指定  
        SolrServer solrServer = new HttpSolrServer(URL);  
        //创建一个文档对象SolrInputDocument  
        SolrInputDocument document = new SolrInputDocument();  
        //向文档中添加域，必须有id域，域的名称必须在schema.xml中定义  
        document.addField("id", "1112");  
        document.addField("item_title", "海尔空调");  
        document.addField("item_sell_point", "送电暖宝一个哦");  
        document.addField("item_price", 20000);  
        document.addField("item_image", "http://www.123.jpg");  
        document.addField("item_category_name", "电器");  
        document.addField("item_desc", "这是一款最新的空调，质量好，值得信赖！！");  
        //将document添加到索引库  
        solrServer.add(document);  
        //提交  
        solrServer.commit();  
    }  
	@Test  
    public void testDeleteDocument() throws Exception{  
        //创建一个SolrServer对象，创建一个HttpSolrServer对象，需要指定solr服务的url  
        SolrServer solrServer = new HttpSolrServer(URL);  
        //通过id来删除文档  
        solrServer.deleteById("1111");  
        //提交  
        solrServer.commit();  
    } 
	@Test  
    public void deleteDocumentByQuery() throws Exception{  
        //创建一个SolrServer对象，创建一个HttpSolrServer对象，需要指定solr服务的url  
        SolrServer solrServer = new HttpSolrServer(URL);  
        //通过价格来删除文档  
        solrServer.deleteByQuery("item_price:20000");  
        //提交  
        solrServer.commit();  
    } 
	@Test  
	public void queryDocument() throws Exception{  
	    //创建一个SolrServer对象，创建一个HttpSolrServer对象，需要指定solr服务的url  
	    SolrServer solrServer = new HttpSolrServer(URL);  
	    //通过id来删除文档  
	    SolrQuery query = new SolrQuery();  
	    query.setQuery("id:1112");  
	    QueryResponse response = solrServer.query(query);  
	    SolrDocumentList list = response.getResults();  
	    for(SolrDocument document : list){  
	        String id = document.getFieldValue("id").toString();  
	        String title = document.getFieldValue("item_title").toString();  
	        System.out.println(id);  
	        System.out.println(title);  
	    }  
	} 
	@Test  
    public void queryDocument2() throws Exception{  
        //创建一个SolrServer对象，创建一个HttpSolrServer对象，需要指定solr服务的url  
        SolrServer solrServer = new HttpSolrServer(URL);  
        //创建一个SolrQuery对象  
        SolrQuery query = new SolrQuery();  
        //设置查询条件、过滤条件、分页条件、排序条件、高亮  
        //query.set("q", "*:*");  
        query.setQuery("手机");  
        //分页条件  
        query.setStart(0);  
        query.setRows(3);  
        //设置默认搜索域  
        query.set("df", "item_keywords");  
        //设置高亮  
        query.setHighlight(true);  
        //高亮显示的域  
        query.addHighlightField("item_title");  
        query.setHighlightSimplePre("<em>");  
        query.setHighlightSimplePost("</em>");  
        //执行查询，得到一个Response对象  
        QueryResponse response = solrServer.query(query);  
        //取查询结果  
        SolrDocumentList solrDocumentList = response.getResults();  
        //取查询结果总记录数  
        System.out.println("查询结果总记录数："+solrDocumentList.getNumFound());  
        for(SolrDocument document : solrDocumentList){  
            System.out.println(document.getFieldValue("id"));  
            //取高亮显示  
            Map<String,Map<String,List<String>>> highlighting = response.getHighlighting();  
            List<String> list = highlighting.get(document.getFieldValue("id")).get("item_title");  
            String itemTitle = "";  
            if(list != null && list.size() > 0){  
                itemTitle = list.get(0);  
            }else {  
                itemTitle = (String)document.get("item_title");  
            }  
            System.out.println(itemTitle);  
            System.out.println(document.get("item_sell_point"));  
            System.out.println(document.get("item_price"));  
            System.out.println(document.get("item_image"));  
            System.out.println(document.get("item_category_name"));  
            System.out.println("===============================================");  
        }  
    }  
}
