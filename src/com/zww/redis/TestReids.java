package com.zww.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.catalina.startup.Embedded;

import redis.clients.jedis.Jedis;

public class TestReids {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.122.128", 6379);
//redis具体的操作
		System.out.println(jedis.ping());

		Set<String> keys = jedis.keys("*");
		
		for (String key : keys) {
			System.out.println(key);
		}
//		System.out.println("jedists ====> " + jedis.exists("k1"));
//		System.out.println(jedis.ttl("k1"));
//		jedis.set("k5", "zhangsan");
//		System.out.println(jedis.get("k5"));
//		jedis.mset("k6", "v1","str2","v2","str3","v3");
//		System.out.println(jedis.mget("str3", "str2"));
//	
		List<String> lists = jedis.lrange("mylist", 0, -1);
		
		for (String list : lists) {
			System.out.println(list);
		}
		
		jedis.sadd("orders", "jd001");
		jedis.sadd("orders", "jd002");
		jedis.sadd("orders", "jd003");
		jedis.sadd("orders", "jd004");
		Set<String> smembers = jedis.smembers("orders");
		for (String smember : smembers) {
			System.out.println(smember);
		}
		
		jedis.srem("orders", "jd001");
		Set<String> smembers1 = jedis.smembers("orders");
		for (String smember : smembers1) {
			System.out.println(smember);
		}

		jedis.hset("hash1", "username", "lisi");
		jedis.hset("hash1", "age", "24");
		Map<String, String> map = new HashMap<String, String>();
		map.put("telphone", "13810169999");
		map.put("address", "atguigu");
		map.put("email", "abc@163.com");
		jedis.hmset("hash2", map);
		List<String> result = jedis.hmget("hash2", "telphone", "email");
		for (String element : result) {
			System.out.println(element);
		}

		jedis.zadd("zset01", 60d, "v1");
		jedis.zadd("zset01", 70d, "v2");
		jedis.zadd("zset01", 80d, "v3");
		jedis.zadd("zset01", 90d, "v4");
		Set<String> s1 = jedis.zrange("zset01", 0, -1);
		for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}

		jedis.close();
	}

}
