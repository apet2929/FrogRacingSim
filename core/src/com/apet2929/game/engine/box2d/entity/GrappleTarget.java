package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.apet2929.game.engine.box2d.BodyFactory;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class GrappleTarget extends Entity{
    private final float width;
    private final float height;
    public GrappleTarget(World world, EntityType entityType, float x, float y, float width, float height) {
        super(entityType);
        this.width = width;
        this.height = height;
        this.sprite.setSize(width, height);
        this.body = BodyFactory.getInstance(world).makeSensorRectBody(x, y, width, height);
        this.setUserData("grappleTarget");
    }

    @Override
    public void initAssets() {
        switch (entityType){
            case LAMP: {
                TextureRegion texture = AssetManager.getInstance().get("rock03");
                this.sprite = new Sprite(texture);
            }
        }
    }
}
