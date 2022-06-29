package com.apet2929.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.*;

public class Utils {

    public static final int VIEWPORT_SIZE = 100;
    public static final float TILE_SIZE = 5;
    public static final float WORLD_SCALE = TILE_SIZE / VIEWPORT_SIZE;
//    Forces
    public static final float WALKING_FORCE = 12000 * WORLD_SCALE;
    public static final float JUMPING_FORCE = 24000 * WORLD_SCALE;
    public static final float GRAPPLE_FORCE = 11000 * WORLD_SCALE;
    public static final float DRAG_FORCE = 600 * WORLD_SCALE;

    public static final float MAX_GRAPPLE_LENGTH = TILE_SIZE * 10;

    public static final float PPM = VIEWPORT_SIZE / TILE_SIZE;

    public static final int[] WASD = new int[]{Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D};

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


    public static Body createRectBody(World world, float x, float y, float width, float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        return createBody(world, bodyDef, shape);

    }
    public static Body createBody(World world, BodyDef bodyDef, FixtureDef fixtureDef){
        // Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        return body;
    }

    public static Body createBody(World world, BodyDef bodyDef, Shape shape){
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, 0.0f);
        return body;
    }
}
