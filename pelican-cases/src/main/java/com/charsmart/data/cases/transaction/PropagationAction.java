package com.charsmart.data.cases.transaction;

/**
 * @Author: Wonder
 * @Date: Created on 2022/1/12 下午5:13
 */
public class PropagationAction {
    private final TransactionA transactionA;
    private final TransactionB transactionB;

    public PropagationAction(TransactionA transactionA, TransactionB transactionB) {
        this.transactionA = transactionA;
        this.transactionB = transactionB;
    }

    public void testPropagation() {
        transactionA.execute();
        transactionB.execute();
    }
}
