package org.sde.cec.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.sde.cec.model.P2PMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class testRequest {
    public static void main(String[] args) throws Exception {
        // 创建HttpClient实例
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // 创建HttpPost实例
            HttpPost httpPost = new HttpPost("http://localhost:9901/p2p/message"); // 替换为你的服务URL

            // 设置请求头（如果需要的话）
            // httpPost.setHeader("Content-Type", "application/json");

            // 创建请求体，这里我们假设MyData是一个简单的POJO类
            P2PMessage data = new P2PMessage();
            // 设置data的属性值...
            data.setMessage("测试共识消息，这是区块");
            Date currentDate = new Date();  // 获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
            String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
            data.setMessageTime(formattedDate.toString());
            data.setMessageType("prepare");
            data.setNetAddress("127.0.0.1:9001");
            data.setSig("测试签名iojdoiajdioajiodjasiodjasoidj");
            // 将MyData对象转换为JSON字符串
            String json = new ObjectMapper().writeValueAsString(data);

            // 设置请求体的内容
            StringEntity entity = new StringEntity(json, "UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            try {
                // 获取响应实体
                HttpEntity responseEntity = response.getEntity();

                // 打印响应内容
                if (responseEntity != null) {
                    System.out.println(EntityUtils.toString(responseEntity, "UTF-8"));
                }
            } finally {
                // 关闭响应
                response.close();
            }
        }
    }


}
