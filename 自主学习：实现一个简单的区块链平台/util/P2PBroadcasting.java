package org.sde.cec.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.sde.cec.model.P2PMessage;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class P2PBroadcasting {
    public boolean springbootRpcBroadcasting(P2PMessage pm) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String json = new ObjectMapper().writeValueAsString(pm);
            StringEntity entity = new StringEntity(json, "UTF-8");
            entity.setContentType("application/json");
            for(int i=0;i<NodeList.nodelist.size();i++){
                System.out.println("send to: "+"http://"+NodeList.nodelist.get(i)+"/p2p/message");
                HttpPost httpPost = new HttpPost("http://"+NodeList.nodelist.get(i)+"/p2p/message"); // 替换为你的服务URL
                httpPost.setEntity(entity);
                CloseableHttpResponse response = httpClient.execute(httpPost);
                try {
                    // 获取响应实体
                    HttpEntity responseEntity = response.getEntity();

                    // 打印响应内容
                    if (responseEntity != null) {
                        System.out.println(EntityUtils.toString(responseEntity, "UTF-8"));
                    }
                }catch (HttpHostConnectException e){
                    System.out.println(e.toString());
                    response.close();
                }
                finally {
                    // 关闭响应
                    response.close();
                }
            }
            return true;

        }
    }
}
