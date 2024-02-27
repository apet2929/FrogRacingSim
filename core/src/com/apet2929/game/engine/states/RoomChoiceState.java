package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class RoomChoiceState extends State{


    public RoomChoiceState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            joinRoom(1);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            joinRoom(2);
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        ScreenUtils.clear(Color.CHARTREUSE);
    }

    @Override
    public void dispose() {

    }

    void joinRoom(int roomId){
        System.out.println("yee");
        Box2DTestState state = new Box2DTestState(gsm);
        state.setRoom(roomId);
        gsm.push(state);
    }
}
