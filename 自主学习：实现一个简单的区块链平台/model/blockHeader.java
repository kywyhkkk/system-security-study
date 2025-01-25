package org.sde.cec.model;

public class blockHeader {
    public String blockBodyHash="";
    public int blockTxSize=0;
    public int blockSize=0;//单位KB
    public String blockTotalHash="";//由除本字段外的其他区块头字段构造
    public String pre_blockTotalHash="";
    public String blockProducingNodeAddress="127.0.0.1";
    public String blockProducingNodeAddress_hash="";
    public String ledgerNodeAddress="127.0.0.1";
    public String ledgerNodeAddress_hash="127.0.0.1";

    public String getBlockBodyHash() {
        return blockBodyHash;
    }

    public void setBlockBodyHash(String blockBodyHash) {
        this.blockBodyHash = blockBodyHash;
    }

    public int getBlockTxSize() {
        return blockTxSize;
    }

    public void setBlockTxSize(int blockTxSize) {
        this.blockTxSize = blockTxSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getBlockTotalHash() {
        return blockTotalHash;
    }

    public void setBlockTotalHash(String blockTotalHash) {
        this.blockTotalHash = blockTotalHash;
    }

    public String getPre_blockTotalHash() {
        return pre_blockTotalHash;
    }

    public void setPre_blockTotalHash(String pre_blockTotalHash) {
        this.pre_blockTotalHash = pre_blockTotalHash;
    }

    public String getBlockProducingNodeAddress() {
        return blockProducingNodeAddress;
    }

    public void setBlockProducingNodeAddress(String blockProducingNodeAddress) {
        this.blockProducingNodeAddress = blockProducingNodeAddress;
    }

    public String getBlockProducingNodeAddress_hash() {
        return blockProducingNodeAddress_hash;
    }

    public void setBlockProducingNodeAddress_hash(String blockProducingNodeAddress_hash) {
        this.blockProducingNodeAddress_hash = blockProducingNodeAddress_hash;
    }

    public String getLedgerNodeAddress() {
        return ledgerNodeAddress;
    }

    public void setLedgerNodeAddress(String ledgerNodeAddress) {
        this.ledgerNodeAddress = ledgerNodeAddress;
    }

    public String getLedgerNodeAddress_hash() {
        return ledgerNodeAddress_hash;
    }

    public void setLedgerNodeAddress_hash(String ledgerNodeAddress_hash) {
        this.ledgerNodeAddress_hash = ledgerNodeAddress_hash;
    }
}
