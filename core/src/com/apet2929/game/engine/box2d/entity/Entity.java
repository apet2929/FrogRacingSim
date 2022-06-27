package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.Animation;
import com.apet2929.game.engine.box2d.entity.states.StateMachine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.HashMap;

public abstract class Entity{
    Sprite sprite;
    Body body;

    public Entity() {
        initAssets();

    }

    /*
     *   Initializes any animations used and the sprite
     */
    public abstract void initAssets();

    public void render(SpriteBatch sb){
        sprite.setCenter(body.getPosition().x, body.getPosition().y);
        sprite.draw(sb);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
