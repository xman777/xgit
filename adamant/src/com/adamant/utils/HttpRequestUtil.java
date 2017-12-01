package com.adamant.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * http请求获取数据
 * TODO: 
 * @author xman
 * @time 2017年8月30日 下午3:59:28
 * @version v1.0
 */
public class HttpRequestUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param){
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameStr = url + "?" + param;
			URL realUrl = new URL(urlNameStr);
			//打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//建立实际的连接
			conn.connect();
			//获取所有响应头字段
//			Map<String, List<String>> responseHeadMap = conn.getHeaderFields();
			//遍历所有的响应头字段
//			for(String key : responseHeadMap.keySet()){
//				System.out.println(key + "-->" + responseHeadMap.get(key));
//			}
			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
				System.out.println(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {//关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param){
		String result = "";
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			URL realUrl = new URL(url);
			//打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//发送POST请求设置输入输出（必须设置的信息）
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.println(param);
			//flush输出流的缓冲
			out.flush();
			//定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line ;
			while ((line = in.readLine()) != null) {
				result += line;
				System.out.println(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally { //关闭输入输出流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://localhost/api/ask/ask_index1";
		String param = "categoryId=120&uid=30886";
		String test = HttpRequestUtil.sendPost(url, param);
		System.out.println(test);
		
	}

}
