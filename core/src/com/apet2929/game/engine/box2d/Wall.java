package com.apet2929.game.engine.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.apet2929.game.engine.Utils.createRectBody;

public class Wall {
    public Body body;

    public Wall(World world, float x, float y, float width, float height){
        this.body = createRectBody(world, x, y, width, height);
    }


}
