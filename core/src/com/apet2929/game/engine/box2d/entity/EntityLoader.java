package com.apet2929.game.engine.box2d.entity;

import com.badlogic.gdx.physics.box2d.World;

import static com.apet2929.game.engine.Utils.TILE_SIZE;

public class EntityLoader {
    public static Entity LoadFromType(World world, int typeOrdinal, float x, float y){
        EntityType type = EntityType.values()[typeOrdinal];
        return LoadFromType(world, type, x, y);
    }


    public static Entity LoadFromType(World world, EntityType type, float x, float y){
        switch(type){
            case WALL: {
                return new Wall(world, x, y, TILE_SIZE, TILE_SIZE);
            }

            default:
                throw new IllegalArgumentException("EntityType of " + type.name() + " not supported!");
        }
    }
}
