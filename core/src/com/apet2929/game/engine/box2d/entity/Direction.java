package com.apet2929.game.engine.box2d.entity;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP(new Vector2(0, 1)),
    DOWN(new Vector2(0, -1)),
    LEFT(new Vector2(-1, 0)),
    RIGHT(new Vector2(1, 0));

    private Vector2 vector;
    Direction(Vector2 vector){
        this.vector = vector;
    }
    public Vector2 getVector(){
        return new Vector2(vector);
    }
}
