package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.level.Level;

import static com.apet2929.game.engine.Utils.TILE_SIZE;

public class EntityLoader {
    public static Entity LoadFromType(Level level, int typeOrdinal, float x, float y){
        EntityType type = EntityType.values()[typeOrdinal];
        return LoadFromType(level, type, x, y);
    }


    public static Entity LoadFromType(Level level, EntityType type, float x, float y){
        switch(type){
            case WALL: {
                return new Wall(level.getWorld(), x, y, TILE_SIZE, TILE_SIZE);
            }
            case BALL: {
                return new Ball(level.getWorld(), x, y);
            }


            default:
                throw new IllegalArgumentException("EntityType of " + type.name() + " not supported!");
        }
    }
}
