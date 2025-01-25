package org.sde.cec.util;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;

public class CryptographyUtil {
    //rsa秘钥构造函数，可以用于签名，keysize一般为1024，,2048，,2029年之前的安全密钥长度为4096
    //若实验中不涉及签名秘钥的长度设置，可直接keysize设为1024
    public KeyPair RSAKeyConstruction(int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keysize);
        KeyPair keyPair = keyGen.generateKeyPair();
        return  keyPair;
    }
    public final static int MAX_DECRYPT_BLOCK = 128;

    public  byte[] decrypt(byte[] data, PublicKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 返回UTF-8编码的解密信息
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String s = new String(decryptedData, "UTF-8");
        System.out.println(s);
        return decryptedData;
    }
    public static final int MAX_ENCRYPT_BLOCK = 128;
    public  byte[]  encrypt(String data, PrivateKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        int offset = 0;
        byte[] cache;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        return encryptedData;
    }
    public static  void main(String args[]) throws Exception {
        CryptographyUtil cu =new CryptographyUtil();
        KeyPair key=cu.RSAKeyConstruction(1024);
        cu.decrypt(cu.encrypt("g dataString dataString dataString dataString dataString dataString dataString dataString dataString dataString data", key.getPrivate()),key.getPublic());

    }
}
