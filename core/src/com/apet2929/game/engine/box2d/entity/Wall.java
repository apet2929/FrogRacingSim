package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.apet2929.game.engine.Utils.createRectBody;

public class Wall extends Entity {
    public Body body;

    public Wall(World world, float x, float y, float width, float height){
        this.body = createRectBody(world, x, y, width, height);
    }


    @Override
    public void initAssets() {
        this.sprite = new Sprite(AssetManager.getInstance().get("brick1"));
    }
}
