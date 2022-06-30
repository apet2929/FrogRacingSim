package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.apet2929.game.engine.box2d.BodyFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.apet2929.game.engine.Utils.TILE_SIZE;


public class Ball extends SmartEntity {
    final Vector2 pushForce = new Vector2(100, 0);
    public Ball(World world, float x, float y){
        super(EntityType.BALL);

        BodyFactory factory = BodyFactory.getInstance(world);
        this.body = factory.makeCircleBody(x, y, TILE_SIZE, Material.RUBBER, BodyDef.BodyType.DynamicBody, false);
        this.setUserData("ball");
    }

    @Override
    public void initAssets() {
        this.sprite = new Sprite(AssetManager.getInstance().get("brick1"));
        sprite.setSize(TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void update(float delta) {
        this.body.applyForceToCenter(pushForce, true);

    }

    @Override
    void initStates() {

    }
}
