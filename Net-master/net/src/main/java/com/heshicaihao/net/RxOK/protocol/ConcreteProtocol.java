package com.heshicaihao.net.RxOK.protocol;

/**
 */

public class ConcreteProtocol {
    private BaseProtocol mBaseProtocol;

    public ConcreteProtocol(BaseProtocol mBaseProtocol) {
        this.mBaseProtocol = mBaseProtocol;
    }

    public Object parsed() {
        return mBaseProtocol.startProtocol();
    }
}
