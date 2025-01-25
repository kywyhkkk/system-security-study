package org.sde.cec.model;

import org.sde.cec.basicModel.MerklePatriciaTree;

public class blockBody {
    MerklePatriciaTree txMPT;
    MerklePatriciaTree recipientMPT;
    MerklePatriciaTree accountStateMPT;
    MerklePatriciaTree contraceStateMPT;
    consensusTxSet ctxset;

    public MerklePatriciaTree getTxMPT() {
        return txMPT;
    }

    public void setTxMPT(MerklePatriciaTree txMPT) {
        this.txMPT = txMPT;
    }

    public MerklePatriciaTree getRecipientMPT() {
        return recipientMPT;
    }

    public void setRecipientMPT(MerklePatriciaTree recipientMPT) {
        this.recipientMPT = recipientMPT;
    }

    public MerklePatriciaTree getAccountStateMPT() {
        return accountStateMPT;
    }

    public void setAccountStateMPT(MerklePatriciaTree accountStateMPT) {
        this.accountStateMPT = accountStateMPT;
    }

    public MerklePatriciaTree getContraceStateMPT() {
        return contraceStateMPT;
    }

    public void setContraceStateMPT(MerklePatriciaTree contraceStateMPT) {
        this.contraceStateMPT = contraceStateMPT;
    }

    public consensusTxSet getCtxset() {
        return ctxset;
    }

    public void setCtxset(consensusTxSet ctxset) {
        this.ctxset = ctxset;
    }
}
