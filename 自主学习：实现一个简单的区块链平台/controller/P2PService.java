package org.sde.cec.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import org.sde.cec.consensus.ULW;
import org.sde.cec.model.P2PMessage;

import org.sde.cec.model.block;
import org.sde.cec.model.consensusTxSet;
import org.sde.cec.util.NodeList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController

public class P2PService{
    //仅供共识节点内部消息通信的rpc api
    @PostMapping("/p2p/message")
    public ResponseEntity<String> p2PMessageCollection(@RequestBody String P2PMessage) throws Exception {

        System.out.println("----------获取节点成员列表---------");
        for(int i=1;i<=NodeList.nodelist.size();i++){

            System.out.println(NodeList.nodelist.get(i-1)+":签名 "+NodeList.nodeMap.get(NodeList.nodelist.get(i-1)));
        }


        ObjectMapper objectMapper = new ObjectMapper();
        String message=P2PMessage;
        Type type1 = P2PMessage.class;
        JavaType javaType1 = objectMapper.getTypeFactory().constructType(type1);
        P2PMessage pMessage=objectMapper.readValue(message, javaType1);
        Date currentDate = new Date();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        String nodeAddress=pMessage.getNetAddress();//获取消息来源共识节点的网络地址
        System.out.println("-------------------------------------------");
        System.out.println("于 "+formattedDate+" 接收到来自共识节点 "+nodeAddress+" 的P2P消息");
        System.out.println("消息发送时间:"+pMessage.getMessageTime());
        System.out.println("消息类型为："+pMessage.getMessageType()+"消息内容为：");
        System.out.println(pMessage.getMessage());
        System.out.println("-------------------------------------------");

        if(pMessage.getMessageType().equals("prepare")){
            System.out.println("处理prepare，执行区块交易验证");
        } else if (pMessage.getMessageType().equals("commit")) {
            System.out.println("处理commit，执行区块上链");
        } else if (pMessage.getMessageType().equals("sync")) {
            System.out.println("处理sync，核验全局上链同步状态");
        } else if (pMessage.getMessageType().equals("ledger_sync")) {

        } else if (pMessage.getMessageType().equals("node_sync")) {

        }else if(pMessage.getMessageType().equals("ulwleader")){
            ULW ulw=new ULW();
            ObjectMapper objectMapper2 = new ObjectMapper();
            String blockstr=pMessage.getMessage();
            Type type2 = org.sde.cec.model.block.class;
            JavaType javaType2 = objectMapper.getTypeFactory().constructType(type2);
            block b=objectMapper.readValue(blockstr, javaType2);
            System.out.println(ulw.ULWSlave(b));
        }else if(pMessage.getMessageType().equals("ulwvote")){
            ULW ulw=new ULW();
            System.out.println(ulw.ULWVote(pMessage.getMessage()));
        }
        else {
            return ResponseEntity.ok("消息类型无效");

        }
        return ResponseEntity.ok("节点接收消息成功");
    }
}
