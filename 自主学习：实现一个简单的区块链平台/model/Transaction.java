package org.sde.cec.model;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sde.cec.util.CryptographyUtil;
import org.sde.cec.util.hashCreat;

public class Transaction {
    //cec简化交易模型，包含交易hash，交易数据data，交易时间戳，发起方from，接收方to，绑定合约，附加信息，交易签名
    public String txHash="";
    public String timeStamp;
    //交易数据，正常情况下放置合约的输入参数，
    // 注意，此处不对参数正确性做预先验证，在共识阶段参数与合约不一致则合约直接失败，影响交易执行效率
    public String txData;
    public String fromAddress;
    public String toAddress;
    public String bindContractAddress;
    public String bindContractFunction;
    //交易签名存放了除本签名外其他交易字段构成的json的签名
    public byte[] signature=null;
    //交易附加信息，一般存放不需要合约处理，但需要链上存证的信息，
    // 还可以容纳各类实验所需的附加数据，如依赖标记，冲突标记，优先级标记等
    public String append;
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTxData(String txData) {
        this.txData = txData;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }



    public void setAppend(String append) {
        this.append = append;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }



    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTxData() {
        return txData;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }



    public String getAppend() {
        return append;
    }

    public byte[] getSignature() {
        return signature;
    }


    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getBindContractAddress() {
        return bindContractAddress;
    }

    public String getBindContractFunction() {
        return bindContractFunction;
    }

    public void setBindContractAddress(String bindContractAddress) {
        this.bindContractAddress = bindContractAddress;
    }

    public void setBindContractFunction(String bindContractFunction) {
        this.bindContractFunction = bindContractFunction;
    }
    //交易签名构造算法，返回已生成或新生成的签名
    public byte[] generateSignature(String encModel, PrivateKey privateKey) throws Exception {
        //简单实验模式，模拟签名
        if(encModel=="simple"){
            int j=0;
            for(int i=0;i<1000;i++){
                j=i;

            }
            return  this.signature;
        }
        if(this.signature!=null){

            return  this.signature;
        }
        //将当前交易内容完整转换为json str，对str签名
        String str = String.valueOf(new ObjectMapper().writeValueAsString(this).hashCode());
        CryptographyUtil cu=new CryptographyUtil();
        System.out.println(str);
        //this.signature=cu.encrypt(str,privateKey);
        return cu.encrypt(str,privateKey);
    }
    public boolean verifySignature(String encModel, byte[] sig, PublicKey publicKey) throws Exception {
        //简单实验模式，模拟验签
        if(encModel=="simple"){
            int j=0;
            for(int i=0;i<1000;i++){
                j=i;

            }
            return  true;
        }
        CryptographyUtil cu=new CryptographyUtil();
        String decryptedText = new String(cu.decrypt(sig,publicKey), "UTF-8");
        Transaction tempTx=this;
        tempTx.setSignature(null);
        String str = String.valueOf(new ObjectMapper().writeValueAsString(tempTx).hashCode());
        System.out.println(str);
        if (str.equals(decryptedText)){
            return true;
        }else {
            return  false;
        }
    }
    public static  void main(String args[]) throws Exception {
        CryptographyUtil cu=new CryptographyUtil();
        KeyPair keyPair=cu.RSAKeyConstruction(1024);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        Transaction txtest=new Transaction();
        txtest.setAppend("111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111111111111121432133321111111111111111");
        txtest.setFromAddress("0x129836789126398712637821637891628937612");
        txtest.setToAddress("0x12983678912313137821637891628937612");
        txtest.setTimeStamp("2024-01-01 18:00:00.999");
        txtest.setBindContractAddress("0x129836789126398712637821637891628937612");
        txtest.setTxData("qiowwwwwwwwwwwwwwwwwwwwqqqqqqqqqqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqqowwwwwwwwwwwwwwwwwwwwqqqq");
        txtest.setBindContractFunction("tx_function_unit_test11111111111111111111111111111111");
        hashCreat hc=new hashCreat();
        txtest.setTxHash(hc.hashSHA_256(new ObjectMapper().writeValueAsString(txtest)));
        System.out.println(txtest.getTxHash());
        System.out.println(txtest.verifySignature("",txtest.generateSignature("",privateKey),publicKey));



    }
}
