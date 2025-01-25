package org.sde.cec.consensus;

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
import java.util.HashMap;
import java.util.Map;

public  class ULW {
    //UltraLightWeight共识机制，主节点发包直接共识通过，单阶段共识模式
    //适用于不需要考虑共识性能的实验

    public static Map<String, Integer> vote= new HashMap<String, Integer>();
    public String ULWLeader(consensusTxSet txSet, Map<String, KeyPair> keyStorage) throws Exception {
        TransactionVerify tv=new TransactionVerify();
        int totalTxNumber=txSet.getTxlist().size();
        try{
            if(tv.sigVerify(txSet,keyStorage)==false){
                System.out.println("测试模式，忽略验签失败");
                //return "txSigVerify_false";
            }
        }catch(NullPointerException e){
            System.out.println("测试模式，忽略验签exception");
        }

        blockHeader bh=new blockHeader();
        blockBody bb=new blockBody();
        hashCreat hc=new hashCreat();
        bb.setCtxset(txSet);
        bh.setBlockBodyHash(hc.hashSHA_256(new ObjectMapper().writeValueAsString(bb)));
        bh.setBlockTxSize(totalTxNumber);
        long sizeInBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        bh.setBlockSize((int) sizeInBytes);
        if(simpleBlockchain.blockHashList.size()>0){
            bh.setPre_blockTotalHash(simpleBlockchain.blockHashList.get(simpleBlockchain.blockHashList.size()-1));
        }else {
            bh.setPre_blockTotalHash("Genesis");
        }

        bh.setBlockTotalHash(hc.hashSHA_256(new ObjectMapper().writeValueAsString(bh)));
        block b=new block();
        b.setBlockbody(bb);
        b.setBlockheader(bh);
        P2PMessage pm=new P2PMessage();
        pm.setNetAddress(NodeList.local);
        pm.setMessageType("ulwleader");
        Date currentDate = new Date();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        pm.setMessageTime(formattedDate.toString());
        pm.setSig(NodeList.localsig);
        pm.setMessage(new ObjectMapper().writeValueAsString(b));
        P2PBroadcasting p2pb=new P2PBroadcasting();
        System.out.println(p2pb.springbootRpcBroadcasting(pm));
        //将当前区块写入区块链账本
        simpleBlockchain.nowledger.put(bh.getBlockTotalHash(),b);
        //将当前区块完整hash写入区块链区块哈希暂存器，便于后续查找
        simpleBlockchain.blockHashList.add(bh.getBlockTotalHash());
        if(vote.get(bh.getBlockTotalHash())==null){
            vote.put(bh.getBlockTotalHash(),1);//默认自己投自己一票
        }else{
            int i=vote.get(bh.getBlockTotalHash())+1;
            vote.put(bh.getBlockTotalHash(),i);
        }
        System.out.println("主节点区块上链 当前最新区块高度"+simpleBlockchain.blockHashList.size());
        System.out.println("主节点区块上链 当前最新区块hash"+simpleBlockchain.blockHashList.get(simpleBlockchain.blockHashList.size()-1));
        return "ULWLeader_Finish";
    }
    public String ULWSlave(block b) throws Exception{

        blockHeader bh=b.getBlockheader();
        //将当前区块写入区块链账本
        simpleBlockchain.nowledger.put(bh.getBlockTotalHash(),b);
        //将当前区块完整hash写入区块链区块哈希暂存器，便于后续查找
        simpleBlockchain.blockHashList.add(bh.getBlockTotalHash());
        System.out.println("从节点"+NodeList.local+"区块上链 当前最新区块高度"+simpleBlockchain.blockHashList.size());
        System.out.println("从节点"+NodeList.local+"区块上链 当前最新区块hash"+simpleBlockchain.blockHashList.get(simpleBlockchain.blockHashList.size()-1));
        try{
            if(vote.get(bh.getBlockTotalHash())==null){
                vote.put(bh.getBlockTotalHash(),1);//默认自己投自己一票
            }else{
                int i=vote.get(bh.getBlockTotalHash())+1;
                vote.put(bh.getBlockTotalHash(),i);
            }
        }catch (NullPointerException e){
            System.out.println("nullpointer");
            vote.put(bh.getBlockTotalHash(),1);
        }
        System.out.println("投票数量："+vote.get(bh.getBlockTotalHash()));
        P2PMessage pm=new P2PMessage();

        pm.setMessageType("ulwvote");
        Date currentDate = new Date();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        String formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        pm.setMessageTime(formattedDate.toString());
        pm.setSig(NodeList.localsig);
        pm.setMessage(bh.getBlockTotalHash());
        pm.setNetAddress(NodeList.local);
        P2PBroadcasting p2pb=new P2PBroadcasting();
        System.out.println(p2pb.springbootRpcBroadcasting(pm));
        currentDate = new Date();  // 获取当前时间
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  // 指定日期格式
        formattedDate = sdf.format(currentDate);  // 格式化日期为指定格式
        System.out.println(formattedDate);
        return "ULWSlave_Finish";
    }
    //该投票方法不影响区块上链，仅作为静态java变量实现全局非初始化计票的技术验证
    public String ULWVote(String blockHash) throws Exception{
        System.out.println("收到新的对区块"+blockHash+"的投票");
        if(vote.get(blockHash)==null){
            vote.put(blockHash,1);
        }else{
            int i=vote.get(blockHash)+1;
            vote.put(blockHash,i);
        }
        System.out.println("投票数量："+vote.get(blockHash));
        return "voteRecive_finish";
    }
}
