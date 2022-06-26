package com.apet2929.game.engine.box2d.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Objects;

import static com.apet2929.game.engine.Utils.TILE_SIZE;
import static com.apet2929.game.engine.Utils.createBody;

public class Ball extends Entity {
    public Ball(World world, float x, float y){
        super();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        CircleShape shape = new CircleShape();
        shape.setRadius(6f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.4f;

        this.body = createBody(world, bodyDef, fixtureDef);
        shape.dispose();
    }

    @Override
    public void initAssets() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        sprite.setSize(TILE_SIZE, TILE_SIZE);
    }


}
