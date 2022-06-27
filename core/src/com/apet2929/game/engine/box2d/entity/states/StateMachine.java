package com.apet2929.game.engine.box2d.entity.states;

import java.util.HashMap;

public class StateMachine {
    private HashMap<String, StateGenerator> states;
    private EntityState current;


    /**
     * @param states
     * A HashMap where the key is the state's name,
     * and the value is an interface where the only function returns the desired state.
     * You can use a lambda for this.
     */
    public StateMachine(HashMap<String, StateGenerator> states) {
        this.states = states;
        this.current = new EmptyState();
    }

    public void change(String stateName){
        current.onExit();
        current = this.states.get(stateName).create();
        current.onEnter();
    }

    public void update(float delta){
        current.update(delta);
    }

    public EntityState get(String name){
        return this.states.get(name).create();
    }

    public EntityState getCurrent(){
        return current;
    }

    public void addState(String name, StateGenerator stateGenerator){
        this.states.put(name, stateGenerator);
    }
}
