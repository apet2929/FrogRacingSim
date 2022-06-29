package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.*;

public class FrogGrappleState extends FrogState{
    Vector2 tonguePos;
    Vector2 grapplePos;

    float closestFraction; // used in finding grapplePos

    public FrogGrappleState(SmartEntity entity) {
        super(entity, Frog.GRAPPLE);
    }

    @Override
    public void update(float delta) {
        updateTonguePos();
        pullToTarget();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            release();
        }
    }

    @Override
    public void onEnter() {
        System.out.println("frog.getDirection() = " + frog.getDirection());
        updateTonguePos();
        initGrapplePos();
        if(grapplePos == null) release();
        else {
            System.out.println("grapplePoint = " + grapplePos);
            System.out.println("tonguePos = " + tonguePos);

        }
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    Vector2 updateTonguePos(){
        tonguePos = new Vector2(frog.getPosition().x + Frog.BODY_WIDTH*0.7f, frog.getPosition().y);
        return tonguePos;
    }

    public void drawTongue(ShapeRenderer sr){
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.8f, 0.3f, 0.3f, 1f);
        sr.rectLine(tonguePos, grapplePos, 3);
    }

    void release(){
        if(frog.getNumFootContacts() > 0){
            if(Frog.shouldWalk()) frog.changeState(Frog.WALKING);
            else frog.changeState(Frog.IDLE);
        } else {
            frog.changeState(Frog.JUMPING);
        }
    }

    void pullToTarget(){
        Vector2 direction = new Vector2(grapplePos.x - tonguePos.x, grapplePos.y - tonguePos.y).nor();

        frog.applyForceToCenter(direction, GRAPPLE_FORCE);
        if(direction.y > 0.85) { // release if angle is too steep
            System.out.println("direction = " + direction);
            System.out.println("direction.len() = " + direction.len());
            release();
        }
    }

    void initGrapplePos() {
        Vector2 pos = this.frog.getPosition();
        float grappleTargetX = frog.getDirection().getVector().x * MAX_GRAPPLE_LENGTH;

        closestFraction = Float.MAX_VALUE;
        frog.getBody().getWorld().rayCast((fixture, point, normal, fraction) -> {
            if(fixture.getUserData().equals("frog") || fixture.isSensor()) return 1;
            if(fraction < closestFraction) {
                closestFraction = fraction;
                grapplePos = point;
            }
            return 1;
        }, tonguePos,
            new Vector2(pos.x + grappleTargetX, pos.y));
    }
}
