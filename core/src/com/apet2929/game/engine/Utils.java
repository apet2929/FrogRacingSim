package com.apet2929.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class Utils {

    public static final int VIEWPORT_SIZE = 100;
    public static final float TILE_SIZE = 10;

    public static final float PPM = VIEWPORT_SIZE / TILE_SIZE;

    public static void clearScreen(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,1,1,1);
    }

    public static void clearScreen(int r, int g, int b){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(r/255.0f,g/255f,b/255f,1);
    }

    public static Color intToFloatColor(int r, int g, int b){
        return new Color(r/255f, g/255f, b/255f, 1f);
    }

    public static Color intToFloatColor(int r, int g, int b, int a){
        return new Color(r/255f, g/255f, b/255f, a/255f);
    }

    public static float clamp(float value, float min, float max){
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int value, int min, int max){
        return Math.max(min, Math.min(max, value));
    }
}
