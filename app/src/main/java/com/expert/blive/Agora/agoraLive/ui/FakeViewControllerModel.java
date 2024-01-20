package com.expert.blive.Agora.agoraLive.ui;

public class FakeViewControllerModel {
    public int sizeOfList;
    public int widthOfView;
    public int heightOfView;
    public int bottomMargin;

    public FakeViewControllerModel(int size, int width, int height) {
        this.sizeOfList = size;
        this.widthOfView = width;
        this.heightOfView = height;
    }

    public FakeViewControllerModel(int sizeOfList, int widthOfView, int heightOfView, int bottomMargin) {
        this.sizeOfList = sizeOfList;
        this.widthOfView = widthOfView;
        this.heightOfView = heightOfView;
        this.bottomMargin = bottomMargin;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public int getSizeOfList() {
        return sizeOfList;
    }

    public void setSizeOfList(int sizeOfList) {
        this.sizeOfList = sizeOfList;
    }

    public int getWidthOfView() {
        return widthOfView;
    }

    public void setWidthOfView(int widthOfView) {
        this.widthOfView = widthOfView;
    }

    public int getHeightOfView() {
        return heightOfView;
    }

    public void setHeightOfView(int heightOfView) {
        this.heightOfView = heightOfView;
    }
}
