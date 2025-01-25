package org.sde.cec.consensus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.util.RamUsageEstimator;
import org.sde.cec.ledger.simpleBlockchain;
import org.sde.cec.model.*;
import org.sde.cec.util.NodeList;
import org.sde.cec.util.P2PBroadcasting;
import org.sde.cec.util.hashCreat;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PBFT {
    //pbft实验模式，简易模式simple：不对交易做合法性验证，直接进行四阶段共识，在共识过程中由合约做验证
    //严格模式trictness：按照实验制定验证方法，由主节点对交易做验证，示例提供的是对交易签名进行验证(待实现)
    public static String EXPER_MODEL= "simple";

    public String txs="{}";
    public static String prepare_voteCollect="{}";
    public static String commit_voteCollect="{}";
    public String pre_Prepare(consensusTxSet txSet, Map<String, KeyPair> keyStorage) throws Exception {
        int totalTxNumber=txSet.getTxlist().size();
        if(EXPER_MODEL== "simple"){
            TransactionVerify tv=new TransactionVerify();
            if(tv.sigVerify(txSet,keyStorage)==false){
                System.out.println("测试模式，忽略验签失败");
                //return "txSigVerify_false";
            }
            blockHeader bh=new blockHeader();
            blockBody bb=new blockBody();
            hashCreat hc=new hashCreat();
            bb.setCtxset(txSet);
            bh.setBlockBodyHash(hc.hashSHA_256(new ObjectMapper().writeValueAsString(bb)));
            bh.setBlockTxSize(totalTxNumber);
            //bh.setBlockSize((int) RamUsageEstimator.sizeOf(bb));
            bh.setPre_blockTotalHash(simpleBlockchain.blockHashList.get(simpleBlockchain.blockHashList.size()));
            bh.setBlockTotalHash(hc.hashSHA_256(new ObjectMapper().writeValueAsString(bh)));
            block b=new block();
            b.setBlockbody(bb);
            b.setBlockheader(bh);
            P2PMessage pm=new P2PMessage();
            pm.setNetAddress(NodeList.local);
            pm.setMessageType("prepare");
            Date currentDate = new Date();  // 获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
            String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
            pm.setMessageTime(formattedDate.toString());
            pm.setSig(NodeList.localsig);
            pm.setMessage(new ObjectMapper().writeValueAsString(b));
            P2PBroadcasting p2pb=new P2PBroadcasting();
            System.out.println(p2pb.springbootRpcBroadcasting(pm));

        }
        return "";


    }

    public String Prepare(P2PMessage pm){



        return "";

    }
}
