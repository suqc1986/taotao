package com.taotao.redis.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;  
  
public class TestJedis {  
    public static final String REDIS_URL = "192.168.137.150";
    @Test  
    public void testJedis(){  
        //创建jedis对象，需要指定Redis服务的IP和端口号  
        Jedis jedis = new Jedis(REDIS_URL, 6379);  
        System.out.println(jedis);
        //jedis.auth("redis");
        //直接操作数据库  
        jedis.set("jedis-key", "hello jedis!");  
        //获取数据  
        String result = jedis.get("jedis-key");  
        System.out.println(result);  
        //关闭jedis  
        jedis.close();  
    }  
    @Test  
    public void testJedisPool(){  
        //创建一个数据库连接池对象（单例，即一个系统共用一个连接池），需要指定服务的IP和端口号  
        JedisPool jedisPool = new JedisPool(REDIS_URL, 6379);  
        //从连接池中获得连接  
        Jedis jedis = jedisPool.getResource();  
        //jedis.auth("redis");
        //使用jedis操作数据库（方法级别，就是说只是在该方法中使用，用完就关闭）  
        String result = jedis.get("jedis-key");  
        System.out.println(result);  
        //用完之后关闭jedis连接  
        jedis.close();  
        //系统关闭前先关闭数据库连接池  
        jedisPool.close();  
    }  
}  