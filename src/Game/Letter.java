package Game;

import javax.media.opengl.GL;

public class Letter implements Sprite {
    double x, y;
    int textureIndex;
    float scaleX;
    float scaleY;

    public Letter(double x, double y, int textureIndex, float scaleX , float scaleY) {
        this.x = x;
        this.y = y;
        this.textureIndex = textureIndex;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getScaleX() {
        return scaleX;
    }

    @Override
    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }
    @Override
    public float getScaleY() {
        return scaleY;
    }
    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public void drawSprite(GL gl, int[] textures) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureIndex]);
        gl.glPushMatrix();
        gl.glTranslated(x / (100f / 2.0) - 0.9, y / (100f / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scaleX, 0.1 * scaleY, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
}

