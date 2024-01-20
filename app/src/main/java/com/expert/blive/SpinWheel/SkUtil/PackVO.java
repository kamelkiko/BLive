package com.expert.blive.SpinWheel.SkUtil;

public class PackVO {
    private int _packImage;
    private int _price;
    private int _value;

    public PackVO(int i, int i2, int i3) {
        this._packImage = i;
        this._price = i2;
        this._value = i3;
    }

    public int getPackImage() {
        return this._packImage;
    }

    public int getPrice() {
        return this._price;
    }

    public int getValue() {
        return this._value;
    }
}
