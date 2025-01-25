package org.sde.cec.model;

public class P2PMessage {
    //共识节点内部通信使用的消息模型
    //共识节点内部消息类型，需要增加类型时，只需要在P2PService增加对应消息处理逻辑即可
    //初始类型包括：共识类prepare，commit，sync(用于共识后的全局同步验证)，
    //账本同步类 ledger_sync(新加入节点或落后节点同步账本)
    //节点同步类 node_sync(获取当前最新的已认证节点列表，新加入节点使用)
    public String messageType="";
    public String messageTime="";
    public String message;
    public String sig;
    public String NetAddress="127.0.0.1:8888";

    public String getNetAddress() {
        return NetAddress;
    }

    public void setNetAddress(String netAddress) {
        NetAddress = netAddress;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
