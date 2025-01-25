package org.sde.cec.model;

public class block {
    //区块模型，包括区块头：区块哈希，区块体内各个树根，区块高度，区块体签名(当前节点的签名)，出块节点，出块节点签名
    //区块体：交易MPT，收据MPT，主体状态MPT，合约状态MPT，资产状态MPT
    blockBody blockbody;
    blockHeader blockheader;

    public blockBody getBlockbody() {
        return blockbody;
    }

    public void setBlockbody(blockBody blockbody) {
        this.blockbody = blockbody;
    }

    public blockHeader getBlockheader() {
        return blockheader;
    }

    public void setBlockheader(blockHeader blockheader) {
        this.blockheader = blockheader;
    }
}
