package Game;

import javax.media.opengl.GL;

public interface Sprite {
    void drawSprite(GL gl, int[] textures);
    public void setX(double x);
    public double getX();
    public void setY(double y);
    public double getY();
    public void setScaleX(float scaleX);
    public float getScaleX();
    public void setScaleY(float scaleY);
    public float getScaleY();
    public void setTextureIndex(int textureIndex);
    public int getTextureIndex();

}

