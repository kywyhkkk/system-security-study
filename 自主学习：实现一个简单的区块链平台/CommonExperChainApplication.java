package org.sde.cec;

import org.sde.cec.consensus.ULW;
import org.sde.cec.util.CryptographyUtil;
import org.sde.cec.util.NodeList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CommonExperChainApplication {

	public static void main(String[] args) throws IOException {
		NodeList nl=new NodeList();//初始化节点启动时默认加载所有已配置的节点信息
		nl.nodeList();
		ULW ulw=new ULW();//默认初始化ULW共识的投票缓存
		SpringApplication.run(CommonExperChainApplication.class, args);
	}

}
