package com.apet2929.game.engine.level;

import com.apet2929.game.engine.box2d.entity.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Level {
    World world;
    ArrayList<Entity> entities;

    public Level() {
        world = new World(new Vector2(0, -50f), true);
        entities = new ArrayList<>();
    }

    public World getWorld() {
        return world;
    }
}
