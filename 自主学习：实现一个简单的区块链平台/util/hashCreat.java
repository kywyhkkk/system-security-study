package org.sde.cec.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashCreat {
    public String hashSHA_256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // 选择所需的算法（这里使用SHA-256）
        byte[] hashBytes = md.digest(input.getBytes()); // 计算Hash值

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1)); // 将每个字节转换为十六进制表示并连接起来
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
