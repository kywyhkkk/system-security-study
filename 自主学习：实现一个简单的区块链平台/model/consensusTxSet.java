package org.sde.cec.model;

import java.util.ArrayList;
import java.util.List;

public class consensusTxSet {

    //用于共识的交易集合，由主节点等指定节点从交易池中获取
    public List<Transaction> txlist=new ArrayList<>();


    //共识交易集合的附加信息，可以存储必要的实验信息
    public String append;

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }
    //增加交易集合中的交易
    public void addTxlist(Transaction tx) {
        this.txlist.add(tx);
    }
    public void setTxlistTxlist(List<Transaction> list) {
        this.txlist=list;
    }

    public List<Transaction> getTxlist() {
        return txlist;
    }
}
