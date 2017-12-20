package com.taotao.search.test;

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
}
