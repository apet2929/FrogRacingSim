package com.apet2929.game.engine.box2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public interface OnCollision {
    void start(Fixture fA, Fixture fB);
    void end(Fixture fA, Fixture fB);
}
