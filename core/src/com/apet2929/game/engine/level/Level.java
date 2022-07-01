package com.apet2929.game.engine.level;

import com.apet2929.game.engine.box2d.CollisionHandler;
import com.apet2929.game.engine.box2d.OnCollision;
import com.apet2929.game.engine.box2d.entity.Entity;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import static com.apet2929.game.engine.Utils.TILE_SIZE;
import static com.apet2929.game.engine.Utils.WORLD_SCALE;

public class Level {
    World world;
    ArrayList<Entity> entities;
    CollisionHandler collisionHandler;

    TextureRegion background;
    TextureRegion sky;


    public Level() {
        this.collisionHandler = new CollisionHandler();
        world = new World(new Vector2(0, -50f), true);
        entities = new ArrayList<>();
    }

    public void update(float delta){

        this.world.step(delta, 6, 2);
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }

    public void render(SpriteBatch sb){
        entities.forEach((Entity entity) -> {
            entity.render(sb);
        });
    }

    public ArrayList<Frog> getFrogs() {
        ArrayList<Frog> frogs = new ArrayList<>();
        entities.forEach((Entity entity) -> {
            if(entity.getBody().getFixtureList().get(0).getUserData().equals("frog")) frogs.add((Frog) (entity));
        });
        return frogs;
    }

    public World getWorld() {
        return world;
    }

    public void addCollisionListener(OnCollision onCollision){
        collisionHandler.addCollisionListener(onCollision);
        world.setContactListener(collisionHandler);
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
        world.destroyBody(entity.getBody());
    }

}
