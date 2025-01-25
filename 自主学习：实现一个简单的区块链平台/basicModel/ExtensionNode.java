package org.sde.cec.basicModel;

public class ExtensionNode {
    public String key;
    public String value;//分支节点的hash
    public BranchNode child;//通过java对象关联模拟MPT扩展节点与分支节点的关联

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

    public BranchNode getChild() {
        return child;
    }

    public void setChild(BranchNode child) {
        this.child = child;
    }
}
