package com.apet2929.game.engine.box2d;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class CollisionHandler implements ContactListener {
    private final ArrayList<OnCollision> runOnCollision;

    public CollisionHandler() {
        this.runOnCollision = new ArrayList<>();
    }

    public void addCollisionListener(OnCollision onCollision){
        runOnCollision.add(onCollision);
    }
    public void removeOnCollision(int index){
        runOnCollision.remove(index);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();
        runOnCollision.forEach((OnCollision onCollision) -> {
            onCollision.start(fA, fB);
        });
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();
        runOnCollision.forEach((OnCollision onCollision) -> {
            onCollision.end(fA, fB);
        });
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
