package baow.tools;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
 
public class HttpClientUtils {
    private static Map map=new HashMap();
    static{
//    	map.put("tranCode","100007");
    	map.put("url", "http://it.yusys.com.cn");
    	/*map.put("transData", "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
    			"<ebank>\n"+
    			"<reqHead>\n"+
    				"<custNo>2001008814</custNo>\n"+
    				"<userId>000000</userId>\n"+
    				"<tranCode>100007</tranCode>\n"+
    				"<serialNo>1000000599</serialNo>\n"+
    				"<reqDate>20190129</reqDate>\n"+
    				"<reqTime>172505289</reqTime>\n"+
    			"</reqHead>\n"+
    			"<reqBody>\n"+
    				"<iBatchOrderList>\n"+
    					"<row>\n"+
    						"<payAccount>201000190161851</payAccount>\n"+
    						"<payAccountName>银企测试五</payAccountName>\n"+
    						"<recAccount>101012703462641</recAccount>\n"+
    						"<recAccountName>测试</recAccountName>\n"+
    						"<recAccountOpenBank>交通银行</recAccountOpenBank>\n"+
    						"<recBankType>0</recBankType>\n"+
    						"<transferTowardType>0</transferTowardType>\n"+
    						"<payAmount>1.00</payAmount>\n"+
    						"<payUse>daikuan</payUse>\n"+
    						"<payRem></payRem>\n"+
    						"<unionBankNo>301493000035</unionBankNo>\n"+
    						"<tranMate>0</tranMate>\n"+
    					"</row>\n"+
    				"</iBatchOrderList>\n"+
    			"</reqBody>\n"+
    		"</ebank>");*/
    	
    }
    
    @Test
    public void testSendHttpPost() {
    	
        String url = "http:it.yusys.com.cn";
        try {
            String responseContent = doPost(url,map);
            System.out.println(responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String doPost(String url,Map<String,Object> map){
    	StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
    	PostMethod method = new PostMethod(url);
/*    	HttpPost httpPost = new HttpPost(url);
    	httpPost.setHeader("Content-Type", "; charset=UTF-8");*/
    	method.addRequestHeader("Content-Type","text/xml;charset=utf-8");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			method.setParameter(entry.getKey(),String.valueOf(entry.getValue()));
		}
    	try {
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								"UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line).append(System.getProperty("line.separator"));
				}
				reader.close();
			}
		} catch (IOException e) {
			System.out.println("执行post请求" + url + "时，发生异常！");
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		System.out.println("--------------------" + response.toString());
		return response.toString();
    }
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 
    public static String postXML(String url,String xml){
        CloseableHttpClient client = null;
        CloseableHttpResponse resp = null;
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml; charset=UTF-8");
            client = HttpClients.createDefault();
            StringEntity entityParams = new StringEntity(xml,"utf-8");
            httpPost.setEntity(entityParams);
            client = HttpClients.createDefault();
            resp = client.execute(httpPost);
            String resultMsg = EntityUtils.toString(resp.getEntity(),"utf-8");
            return resultMsg;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(client!=null){
                    client.close();
                }
                if(resp != null){
                    resp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
 
 
    public static String readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:" + responseString.replace("\r\n", ""));
        }
        return builder.toString();
    }
 
 
}
