package edu.utep.cs.cs4381.quickninja;

import android.util.Log;

public class RectHitbox {
    protected float top;
    protected float left;
    protected float bottom;
    protected float right;
    private float height;

    public boolean intersects(RectHitbox rectHitbox) {
        return (this.right > rectHitbox.left
                && this.left < rectHitbox.right )
                && (this.top < rectHitbox.bottom
                && this.bottom > rectHitbox.top);

    }

    public void setTop(float y) {
        top = y;
    }

    public void setLeft(float x) {
        left = x;
    }

    public void setBottom(float v) {
        bottom = v;
    }

    public void setRight(float v) {
        right = v;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
