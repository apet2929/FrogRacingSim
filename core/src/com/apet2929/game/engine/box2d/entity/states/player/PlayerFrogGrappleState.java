package com.apet2929.game.engine.box2d.entity.states.player;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.apet2929.game.engine.box2d.entity.states.FrogGrappleState;
import com.apet2929.game.engine.box2d.entity.states.FrogState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.GRAPPLE_FORCE;
import static com.apet2929.game.engine.Utils.MAX_GRAPPLE_LENGTH;

public class PlayerFrogGrappleState extends FrogGrappleState {
    float closestFraction; // used in finding grapplePos

    public PlayerFrogGrappleState(SmartEntity entity) {
        super(entity);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        initGrapplePos();
        if(grapplePos == null) release();
    }

    @Override
    public void update(float delta) {
        updateTonguePos();
        pullToTarget();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            release();
        }
    }

    public void pullToTarget(){
        Vector2 direction = new Vector2(grapplePos.x - tonguePos.x, grapplePos.y - tonguePos.y).nor();

        frog.applyForceToCenter(direction, GRAPPLE_FORCE);
        if(direction.y > 0.85) { // release if angle is too steep
            System.out.println("direction = " + direction);
            System.out.println("direction.len() = " + direction.len());
            release();
        }
    }

    public void initGrapplePos() {
        Vector2 pos = this.frog.getPosition();
        float grappleTargetX = frog.getDirection().getVector().x * MAX_GRAPPLE_LENGTH;

        closestFraction = Float.MAX_VALUE;
        frog.getBody().getWorld().rayCast((fixture, point, normal, fraction) -> {
                    if(fixture.getUserData().equals("frog" + frog.getID()) || fixture.isSensor()) return 1;
                    if(fraction < closestFraction) {
                        closestFraction = fraction;
                        grapplePos = point;
                    }
                    return 1;
                }, tonguePos,
                new Vector2(pos.x + grappleTargetX, pos.y));
    }

}
