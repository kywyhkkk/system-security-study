package org.sde.cec.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class NodeList {
    public static Map<String,String> nodeMap=new HashMap<String,String>();
    public static List<String> nodelist=new ArrayList<>();
    public static String local="127.0.0.1";

    public static String localsig="1234567";//此处不做签名或证书验证，直接用简易字符串作为节点令牌，降低实现难度
    public void nodeList() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/nodepeers.properties");

        Properties properties = new Properties();
        properties.load(inputStream);
        properties.list(System.out);
        System.out.println("==============================================");
        int nodenum= Integer.parseInt(properties.getProperty("peers.num"));
        for(int i=1;i<=nodenum;i++){
            nodeMap.put(properties.getProperty("peers.node"+i+".addr"),properties.getProperty("peers.node"+i+".sig"));
            nodelist.add(properties.getProperty("peers.node"+i+".addr"));
            System.out.println("获得伙伴共识节点可信配置："+properties.getProperty("peers.node"+i+".addr")+" : "+properties.getProperty("peers.node"+i+".sig"));
        }
        local=properties.getProperty("local.addr");
        local+=":";
        local+=properties.getProperty("local.port");
        localsig=properties.getProperty("local.sig");
        System.out.println("节点本地网络配置："+local);
    }


}
