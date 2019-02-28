package baow.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {

	public static void main(String[] args) {
		// 先发送请求获取验证码
		//http://it.yusys.com.cn/yusys/PictureCheckCode.jpeg
//		String s = HttpRequest.sendGet("http://it.yusys.com.cn/",
//				"nocache=1550565767177");
//		System.out.println(s);
//		
		
		
//		String s2 = HttpRequest.doGet("http://it.yusys.com.cn/yusys/PictureCheckCode.jpeg",
//				"nocache="+System.currentTimeMillis());
//		System.out.println(s2);

		// 发送 POST 请求
		// http://it.yusys.com.cn/yusys/logout.asp

		// 请求 URL: http://it.yusys.com.cn/yusys/login.asp

		// loginname: baowei
		// password: qnvbofnh%408899
		// Accept: application/json, text/javascript, */*; q=0.01
		String sr = HttpRequest.sendPost(
				"http://it.yusys.com.cn/yusys/login.asp",
				"check_code=9763&loginname=baowei&password=qnvbofnh%408899");
		System.out.println(sr);

	}
	public static String doGet(String url, String param) {
		String result = "";
		BufferedInputStream in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				if(key=="Content-Type" && map.get(key).toString().indexOf("image/jpeg")>0)
					System.out.println("这是图片");
				System.out.println(key + "--->" + map.get(key));
				
			} // 定义 BufferedReader输入流来读取URL的响应
			
			in = new BufferedInputStream(connection.getInputStream());
			byte [] bys=new byte[1024];
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("checkCode.jpeg"));
			int len=1;
			while ((len = in.read(bys)) != -1) {
				bos.write(bys,0,len);
			}
			bos.flush();
			bos.close();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally块来关闭输入流
		finally {
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
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				if(key=="Content-Type" && map.get(key).toString().indexOf("image/jpeg")>0)
					System.out.println("这是图片");
				System.out.println(key + "--->" + map.get(key));
				
			} // 定义 BufferedReader输入流来读取URL的响应
			
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally块来关闭输入流
		finally {
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

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url); // 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection(); // 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); // 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true); // 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream()); // 发送请求参数
			out.print(param); // flush输出流的缓冲
			out.flush(); // 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
