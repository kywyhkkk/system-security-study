package org.sde.cec.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sde.cec.account.RSAKeyStorage;
import org.sde.cec.consensus.PBFT;
import org.sde.cec.consensus.ULW;
import org.sde.cec.model.P2PMessage;
import org.sde.cec.model.consensusTxSet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class chainService {
    //链对外服务的rpc api
    @PostMapping("/sdk/txpush")
    public ResponseEntity<String> txPush(@RequestBody String P2PMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message=P2PMessage;
        Type type1 = org.sde.cec.model.P2PMessage.class;
        JavaType javaType1 = objectMapper.getTypeFactory().constructType(type1);
        P2PMessage pMessage=objectMapper.readValue(message, javaType1);

        ObjectMapper objectMapper2 = new ObjectMapper();
        String txsetstr=pMessage.getMessage();
        Type type2 = org.sde.cec.model.consensusTxSet.class;
        JavaType javaType2 = objectMapper.getTypeFactory().constructType(type2);
        consensusTxSet cts=objectMapper.readValue(txsetstr, javaType2);

        Date currentDate = new Date();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        //String nodeAddress=pMessage.getNetAddress();//获取消息来源共识节点的网络地址
        System.out.println("-------------------------------------------");
        System.out.println("于 "+formattedDate+" 接收到来自模拟交易池的客户端调用消息 ");
        System.out.println("消息发送时间:"+pMessage.getMessageTime());
        System.out.println("消息类型为："+pMessage.getMessageType()+"消息内容为：");
        System.out.println(pMessage.getMessage());
        System.out.println("-------------------------------------------");
        if(pMessage.getMessageType().equals("txpush")){
            System.out.println("处理模拟交易池，推入交易免验证直接发送至共识节点");
            PBFT pbft =new PBFT();
            pbft.pre_Prepare(cts,RSAKeyStorage.keyStorage);
        }else {
            return ResponseEntity.ok("消息类型无效");
        }
        return ResponseEntity.ok("交易池推交易完成");
    }
    @PostMapping("/sdk/txpush_ulw")
    public ResponseEntity<String> txPush_ulw(@RequestBody String P2PMessage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String message=P2PMessage;
        Type type1 = org.sde.cec.model.P2PMessage.class;
        JavaType javaType1 = objectMapper.getTypeFactory().constructType(type1);
        P2PMessage pMessage=objectMapper.readValue(message, javaType1);

        ObjectMapper objectMapper2 = new ObjectMapper();
        String txsetstr=pMessage.getMessage();
        Type type2 = org.sde.cec.model.consensusTxSet.class;
        JavaType javaType2 = objectMapper.getTypeFactory().constructType(type2);
        consensusTxSet cts=objectMapper.readValue(txsetstr, javaType2);

        Date currentDate = new Date();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        //String nodeAddress=pMessage.getNetAddress();//获取消息来源共识节点的网络地址
        System.out.println("-------------------------------------------");
        System.out.println("于 "+formattedDate+" 接收到来自模拟交易池的客户端调用消息 ");
        System.out.println("消息发送时间:"+pMessage.getMessageTime());
        System.out.println("消息类型为："+pMessage.getMessageType()+"消息内容为：");
        System.out.println(pMessage.getMessage());
        System.out.println("-------------------------------------------");
        if(pMessage.getMessageType().equals("txpush")){
            System.out.println("处理模拟交易池，推入交易免验证直接发送至共识节点");
            ULW ulw =new ULW();
            ulw.ULWLeader(cts,RSAKeyStorage.keyStorage);
        }else {
            return ResponseEntity.ok("消息类型无效");
        }
        return ResponseEntity.ok("交易池推交易完成");
    }
}
