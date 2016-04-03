package ch.fadre.sandhills;

class Bounds {
    private int top;
    private int bottom;
    private int left;
    private int right;

    Bounds(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    int getTop() {
        return top;
    }

    int getBottom() {
        return bottom;
    }

    int getLeft() {
        return left;
    }

    int getRight() {
        return right;
    }

    void decreaseTopIfNecessary(int top){
        this.top = Math.min(this.top, top);
    }

    void increaseBottomIfNecessary(int bottom){
        this.bottom = Math.max(this.bottom, bottom);
    }

    void decreaseLeftIfNecessary(int left){
        this.left = Math.min(this.left, left);
    }

    void increaseRightIfNecessary(int right){
        this.right = Math.max(this.right, right);
    }

}
