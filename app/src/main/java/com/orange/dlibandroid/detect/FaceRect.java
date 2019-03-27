package com.orange.dlibandroid.detect;

/**
 * created by czh on 2019/3/27
 */
public class FaceRect {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public FaceRect(int l, int t, int r, int b){
        left=l;
        top=t;
        right=r;
        bottom=b;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }
}
