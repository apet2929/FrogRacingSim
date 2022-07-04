package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.engine.level.Level;

import static com.apet2929.game.engine.Utils.TILE_SIZE;

public class EntityLoader {
    public static Entity LoadFromType(Level level, int typeOrdinal, float x, float y){
        EntityType type = EntityType.values()[typeOrdinal];
        return LoadFromType(level, type, x, y);
    }


    public static Entity LoadFromType(Level level, EntityType type, float x, float y){
        Entity entity;
        switch(type){
            case WALL: {
                entity = new Wall(level.getWorld(), x, y, TILE_SIZE, TILE_SIZE);
                break;
            }
            case BALL: {
                entity = new Ball(level.getWorld(), x, y);
                break;
            }
            case LAMP: {
                entity = new GrappleTarget(level.getWorld(), EntityType.LAMP, x, y, TILE_SIZE, TILE_SIZE);
                break;
            }
            default:
                throw new IllegalArgumentException("EntityType of " + type.name() + " not supported!");
        }
        level.addEntity(entity);
        return entity;
    }
}
