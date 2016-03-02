package ch.fadre.sandhills;

public class Bounds {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Bounds(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public void decreaseTopIfNecessary(int top){
        this.top = Math.min(this.top, top);
    }

    public void increaseBottomIfNecessary(int bottom){
        this.bottom = Math.max(this.bottom, bottom);
    }

    public void decreaseLeftIfNecessary(int left){
        this.left = Math.min(this.left, left);
    }

    public void increaseRightIfNecessary(int right){
        this.right = Math.max(this.right, right);
    }

}
