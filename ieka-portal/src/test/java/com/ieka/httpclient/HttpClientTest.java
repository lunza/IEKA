package com.ieka.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base32;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.ieka.common.pojo.IEKAResult;
import com.ieka.common.pojo.SearchResult;

public class HttpClientTest {
	@Test
	public void doGet() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个get对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 获取响应结果(响应码)
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		// 获取响应结果(响应体)
		HttpEntity entity = response.getEntity();
		// 字符集utf-8
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		// 关闭httpclient
		response.close();
		httpClient.close();
	}

	@Test
	public void doGetWithParam() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个uri对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		uriBuilder.addParameter("query", "电影");
		// 创建一个get对象
		HttpGet get = new HttpGet(uriBuilder.build());
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		// 获取响应结果(响应码)
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		// 获取响应结果(响应体)
		HttpEntity entity = response.getEntity();
		// 字符集utf-8
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		// 关闭httpclient
		response.close();
		httpClient.close();
	}

	@Test
	public void doPost() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		// 获取响应结果(响应码)
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		// 获取响应结果(响应体)
		HttpEntity entity = response.getEntity();
		// 字符集utf-8
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		// 关闭httpclient
		response.close();
		httpClient.close();
	}

	@Test
	public void doPostWithParam() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建一个post对象(以.action结尾,走后门)
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
		// 创建一个entity(模拟一个表单)
		List<NameValuePair> kvList = new ArrayList<>();
		kvList.add(new BasicNameValuePair("username", "张三"));
		kvList.add(new BasicNameValuePair("password", "123"));
		// 包装表单
		StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
		post.setEntity(entity);
		// 执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		// 获取响应结果(响应码)
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);

		// 字符集utf-8
		String string = EntityUtils.toString(response.getEntity());
		System.out.println(string);
		// 关闭httpclient
		response.close();
		httpClient.close();
	}
}
