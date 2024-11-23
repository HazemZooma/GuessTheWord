package Game;

import Texture.TextureReader;
import Texture.AnimListener;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.util.ArrayList;

public class AnimGLEventListener extends AnimListener {
    ArrayList<Letter> letters = new ArrayList<>();
    ArrayList<Letter> stars = new ArrayList<>();
    int[] win ={28, 18, 24, 26, 12, 17};
    int[] lost = {28, 18, 24, 15, 18, 22, 23};
    String[] words ={"hazem", "zooma", "mahmoud", "capa", "omar"};
    String randomWord;
    int score = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    double DotX = 10;
    double DotY = 80;
    int DotSpace;
    int lives = 10;

    public AnimGLEventListener() {
    }

    String[] textureNames = {
            "Man1.png", "Man2.png", "Man3.png", "Man4.png",
            "a.png", "b.png", "c.png", "d.png", "e.png", "f.png", "g.png", "h.png", "i.png", "j.png", "k.png", "l.png",
            "m.png", "n.png", "o.png", "p.png", "q.png", "r.png", "s.png", "t.png", "u.png", "v.png", "w.png", "x.png",
            "y.png", "z.png", "0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png",
            "..png", "HealthB.png", "Health.png", "Back.png", "NinjaStar.png"
    };
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    public int[] textures = new int[textureNames.length];

    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,
                        texture[i].getWidth(),
                        texture[i].getHeight(),
                        GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels()
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        randomWord = randomWord().toLowerCase();
        DotSpace = 90 / randomWord.length();
        dots();
        life();
        System.out.println(randomWord);
    }

    @Override
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        letters.forEach(letter -> letter.drawSprite(gl,textures));
        stars.forEach(star -> star.drawSprite(gl, textures));

        if (lives == 0) for (int i = 0; i < lost.length; i++) {
            if (i < 3) DrawSprite(gl, 2 + (13 * i), 50, lost[i], 1f, 3f);
            else DrawSprite(gl, 10 + (13 * i), 50, lost[i], 1f, 3f);
        }
        if (score == randomWord.length()) {
            for (int i = 0; i < win.length; i++) {
                if (i < 3) DrawSprite(gl, 2 + (15 * i), 50, win[i], 1.5f, 3f);
                else DrawSprite(gl, 12 + (15 * i), 50, win[i], 1.5f, 3f);
            }
        }
    }

    public String randomWord() {
        return words[(int) (Math.random() * words.length)];
    }

    public void dots() {
        for (int i = 0; i < randomWord.length(); i++) {
            letters.add(new Letter(DotX, DotY, textureNames.length - 5, 1, 1));
            DotX += DotSpace;
        }
    }

    public void life() {
        for (int i = 0; i < lives; i++)
            stars.add(new Letter(90 - (10 * i), 10, textureNames.length - 1, 1, 1));
    }

    public void check(int code) {
        boolean flag = false;
        if (lives > 0 && score < randomWord.length()) {
            for (int i = 0; i < randomWord.length(); i++) {
                if (letters.get(i).getTextureIndex() == textureNames.length - 5 && randomWord.charAt(i) == code) {
                    letters.get(i).setTextureIndex(code - 93);
                    flag = true;
                    score++;
                    break; // السطر ده علشان لو عايز تخلي كل التكرارات تظهر ولا يظهر حرف واحد في المرة
                }
            }
            if (!flag) {
                stars.get(lives - 1).setTextureIndex(code - 93);
                lives--;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode() + 32;
        if (code <= 122 && code >= 97) check(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void DrawSprite(GL gl, float x, float y, int index, float scaleX, float scaleY) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
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

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 2]);

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);

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

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}