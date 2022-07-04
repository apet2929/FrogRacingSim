package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.apet2929.game.engine.box2d.BodyFactory;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.apet2929.game.engine.Utils.TILE_SIZE;

public class Wall extends Entity {
    final float width, height;
    public Wall(World world, float x, float y, float width, float height){
        super(EntityType.WALL);
        this.width = width;
        this.height = height;
        this.sprite.setSize(this.width, this.height);

        BodyFactory factory = BodyFactory.getInstance(world);
        this.body = factory.makeRectBody(x, y, TILE_SIZE, TILE_SIZE, Material.STEEL, BodyDef.BodyType.StaticBody, true);
        this.setUserData("wall");

    }


    @Override
    public void initAssets() {
        this.sprite = new Sprite(AssetManager.getInstance().get("brick1"));

    }
}
