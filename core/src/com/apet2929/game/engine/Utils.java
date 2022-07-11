package com.apet2929.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class Utils {

    public static final float NET_TICKS_PER_SECOND = 30;
    public static final float NET_TIME_PER_TICK = 1 / NET_TICKS_PER_SECOND;

    public static final int VIEWPORT_SIZE = 100;
    public static final float SCREEN_TO_WORLD_RATIO = Gdx.graphics.getWidth() / VIEWPORT_SIZE;
    public static final float TILE_SIZE = 5;
    public static final float PPM = VIEWPORT_SIZE / TILE_SIZE;
    public static final float WORLD_SCALE = TILE_SIZE / VIEWPORT_SIZE;
    public static final float UNIT_SCALE = TILE_SIZE/417f;

//    Forces

    public static final float HOPPING_FORCE = 8000 * WORLD_SCALE;
    public static final float JUMPING_FORCE = 24000 * WORLD_SCALE;
    public static final float GRAPPLE_FORCE = 22000 * WORLD_SCALE;
    public static final float DRAG_FORCE = 600 * WORLD_SCALE;
    public static final float AIR_CONTROL_FORCE = 1000 * WORLD_SCALE;

    public static final float MAX_GRAPPLE_LENGTH = TILE_SIZE * 10;

    public static final float HOP_RIGHT_ANGLE = (float) Math.toRadians(75);
    public static final float HOP_LEFT_ANGLE = (float ) Math.toRadians(105);
    public static final float HOP_RIGHT_X = (float) Math.cos(HOP_RIGHT_ANGLE);
    public static final float HOP_RIGHT_Y = (float) Math.sin(HOP_RIGHT_ANGLE);
    public static final Vector2 HOP_RIGHT_VECTOR = new Vector2(HOP_RIGHT_X, HOP_RIGHT_Y);
    public static final float HOP_LEFT_X = (float) Math.cos(HOP_LEFT_ANGLE);
    public static final float HOP_LEFT_Y = (float) Math.sin(HOP_LEFT_ANGLE);
    public static final Vector2 HOP_LEFT_VECTOR = new Vector2(HOP_LEFT_X, HOP_LEFT_Y);

    public static final float HOP_SPEED_X = 3f;
    public static final float HOP_SPEED_Y = 20f;



    public static final int[] WASD = new int[]{Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D};

    public static float getUnitScale(){
        return (float) VIEWPORT_SIZE / Gdx.graphics.getWidth();
    }
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

    public static String getString(JSONObject object, String key){
        try {
            return object.getString(key);
        } catch (JSONException e) {
            Gdx.app.log("JSON", "Error getting string data with key: " + key);
            e.printStackTrace();
        }
        return null;
    }

    public static Double getDouble(JSONObject object, String key){
        try {
            return object.getDouble(key);
        } catch (JSONException e) {
            Gdx.app.log("JSON", "Error getting double data with key: " + key);
//            e.printStackTrace();
            System.out.println("object = " + object);
        }
        return null;
    }

    public static Integer getInt(JSONObject object, String key){
        try {
            return object.getInt(key);
        } catch (JSONException e) {
            Gdx.app.log("JSON", "Error getting double data with key: " + key);
//            e.printStackTrace();
            System.out.println("object = " + object);
        }
        return null;
    }

    public static JSONObject getArrayAt(JSONArray array, int index){
        try {
            return array.getJSONObject(index);
        } catch (JSONException e) {
            Gdx.app.log("JSON", "Error getting JSON object from array at index: " + index);
            e.printStackTrace();
        }
        return null;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
