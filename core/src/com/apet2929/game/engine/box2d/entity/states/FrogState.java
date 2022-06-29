package com.apet2929.game.engine.box2d.entity.states;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.SmartEntity;

public class FrogState extends EntityState{
    Frog frog;
    public FrogState(SmartEntity entity, String id) {
        super(entity, id);
        this.frog = (Frog)(this.entity);
    }
}
