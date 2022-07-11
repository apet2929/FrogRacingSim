package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.apet2929.game.engine.Utils.*;

public class FrogGrappleState extends FrogState{
    public Vector2 tonguePos;
    public Vector2 grapplePos;


    public FrogGrappleState(SmartEntity entity) {
        super(entity, Frog.GRAPPLE);
    }

    @Override
    public void update(float delta) {
        updateTonguePos();
    }

    @Override
    public void onEnter() {
        System.out.println("frog.getDirection() = " + frog.getDirection());
        updateTonguePos();
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    public Vector2 updateTonguePos(){
        tonguePos = new Vector2(frog.getPosition().x + Frog.BODY_WIDTH*0.7f * frog.getDirection().getVector().x, frog.getPosition().y);
        return tonguePos;
    }

    public void drawTongue(ShapeRenderer sr) {
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.8f, 0.3f, 0.3f, 1f);
        sr.rectLine(tonguePos, grapplePos, 3);
    }

    public void release(){
        if(frog.getNumFootContacts() > 0){
            frog.changeState(Frog.IDLE);
        } else {
            frog.changeState(Frog.JUMPING);
        }
    }

    public void setGrapplePos(Vector2 pos){
        grapplePos = pos;
    }

    public boolean canGrappleTo(String fixtureID, Vector2 normal){
        if(fixtureID.contains("frog") && fixtureID.contains("foot")) return false;
        int angleDeg = Math.round(normal.angleDeg());
        if(normal.y > 0.9 || normal.y < -0.9) { // hit the top or bottom of a body
            return false;
        }
        return true;
    }



}
