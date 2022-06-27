package com.apet2929.game.engine.level;

import com.apet2929.game.engine.box2d.entity.Entity;
import com.apet2929.game.engine.box2d.entity.EntityLoader;
import com.apet2929.game.engine.box2d.entity.EntityType;
import com.apet2929.game.engine.box2d.entity.Wall;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import static com.apet2929.game.engine.Utils.TILE_SIZE;
import static com.apet2929.game.engine.Utils.createRectBody;

public class LevelLoader {

    private static final TmxMapLoader MAP_LOADER = new TmxMapLoader();

    public static Level Load(String path){
        TiledMap tiledMap = MAP_LOADER.load(path);
        Level level = new Level();
        loadWalls(level, (TiledMapTileLayer) tiledMap.getLayers().get("walls"));
        return level;
    }

    public static Level Load(int[][] tiles){
        Level level = new Level();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                int typeOrdinal = tiles[row][col];
                if(typeOrdinal == -1) continue;

                int y = tiles.length - (row + 1);
                Entity entity = EntityLoader.LoadFromType(level, typeOrdinal, col * TILE_SIZE, y * TILE_SIZE);
                level.entities.add(entity);
            }
        }
        return level;
    }

    private static void loadWalls(Level level, TiledMapTileLayer layer){
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) continue;
                if (cell.getTile() == null) continue;
                Entity entity = EntityLoader.LoadFromType(level, EntityType.WALL, col * TILE_SIZE, row * TILE_SIZE);
                level.entities.add(entity);
            }
        }
    }

}
