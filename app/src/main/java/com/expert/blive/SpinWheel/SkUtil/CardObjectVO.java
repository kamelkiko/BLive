package com.expert.blive.SpinWheel.SkUtil;

public class CardObjectVO {
    private int _icn;
    private String _name;
    private int _no;

    public CardObjectVO(int i, String str, int i2) {
        this._icn = i;
        this._name = str;
        this._no = i2;
    }

    public int getIcn() {
        return this._icn;
    }

    public String getName() {
        return this._name;
    }

    public int getNo() {
        return this._no;
    }
}
