package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.Utils;
import com.apet2929.game.engine.box2d.entity.Direction;
import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.apet2929.game.engine.Utils.JUMPING_FORCE;

public class FrogIdleState extends FrogState{
    public FrogIdleState(SmartEntity entity) {
        super(entity, Frog.IDLE);
    }

    @Override
    public void update(float delta) {
        if(frog.getNumFootContacts() == 0){
            frog.changeState(Frog.JUMPING);
        }

    }

    @Override
    public void onEnter() {
        this.entity.changeAnimation(Frog.IDLE);
    }

    @Override
    public void onExit() {

    }
}
