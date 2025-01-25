package org.sde.cec.account;

import org.sde.cec.util.CryptographyUtil;
import org.sde.cec.util.hashCreat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class RSAKeyStorage {
    //生成实验用的模拟账户地址序列，利用内存临时变量keyStorage保存，不进行持久化
    //生成数量可通过accountNumber预设，生成后以账户地址为key，RSA公私秘钥聚合体为value
    public static Map<String, KeyPair> keyStorage = new HashMap<String, KeyPair>();
    public static List<String> accountAddressList = new ArrayList<>();
    public boolean keyGen() throws NoSuchAlgorithmException {
        CryptographyUtil cu=new CryptographyUtil();
        hashCreat hc=new hashCreat();

        KeyPair tempKey=cu.RSAKeyConstruction(1024);
        String accountAddress= hc.hashSHA_256(tempKey.getPublic().toString());
        System.out.println(accountAddress);
        accountAddressList.add(accountAddress);
        keyStorage.put(accountAddress,tempKey);

        return  true;
    }
    public boolean persistenceAccount(int num) throws NoSuchAlgorithmException, IOException {
        RSAKeyStorage rsaKeyStorage=new RSAKeyStorage();

        Properties properties = new Properties();
        properties.setProperty("account_num", String.valueOf(num));
        for(int i=0;i<num;i++){
            rsaKeyStorage.keyGen();
            properties.setProperty("acc"+i,accountAddressList.get(i));

        }

        properties.store(new FileOutputStream("/account.properties"), "");
        return true;
    }
    public static  void main(String args[]) throws Exception{
        RSAKeyStorage rs=new RSAKeyStorage();
        rs.persistenceAccount(50);
    }
}
