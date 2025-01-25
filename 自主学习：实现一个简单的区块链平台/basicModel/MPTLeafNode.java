package org.sde.cec.basicModel;

public class MPTLeafNode {
    public String key;
    public String value;//存储反序列化json字符串
    public String hashPath;//存储完整的MPT前缀路径，即该叶子节点value的完整hash
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHashPath() {
        return hashPath;
    }

    public void setHashPath(String hashPath) {
        this.hashPath = hashPath;
    }
}
