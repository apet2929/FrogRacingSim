package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.apet2929.game.engine.Animation;
import com.apet2929.game.engine.box2d.BodyFactory;
import com.apet2929.game.engine.box2d.OnCollision;
import com.apet2929.game.engine.box2d.entity.states.*;
import com.apet2929.game.engine.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

import static com.apet2929.game.engine.Utils.TILE_SIZE;
import static com.apet2929.game.engine.Utils.WASD;

public class Frog extends SmartEntity {
    public static final String IDLE = "idle";
    public static final String JUMPING = "jump";
    public static final String WALKING = "walking";
    public static float BODY_WIDTH = 0.7f * TILE_SIZE * 2;
    public static float BODY_HEIGHT = 0.5f * TILE_SIZE * 2;
    public Frog(World world, float x, float y) {
        super(EntityType.FROG);
        initBody(world, x, y);
        this.sprite = new Sprite(this.currentAnimation.getFrame());
        this.sprite.setSize(BODY_WIDTH, BODY_HEIGHT);
    }

    public void initCollisionListener(Level level){
        level.addCollisionListener(new OnCollision() {

            @Override
            public void start(Fixture fA, Fixture fB) {
                Fixture frog;
                Fixture other;
                String otherID;
                if(fA.getBody().equals(body)){
                    frog = fA;
                    other = fB;
                    otherID = (String) other.getBody().getUserData();
                } else if(fB.getBody().equals(body)){
                    frog = fB;
                    other = fA;
                    otherID = (String) other.getBody().getUserData();
                } else {
                    return;
                }
                if(otherID.equals("wall")){
                    onWallCollision();
                }
            }

            @Override
            public void end(Fixture fA, Fixture fB) {
                Fixture frog;
                Fixture other;
                String otherID;
                if(fA.getBody().equals(body)){
                    frog = fA;
                    other = fB;
                    otherID = (String) other.getBody().getUserData();
                } else if(fB.getBody().equals(body)){
                    frog = fB;
                    other = fA;
                    otherID = (String) other.getBody().getUserData();
                } else {
                    return;
                }
                if(otherID.equals("wall")){
                    System.out.println("Stopped touching wall!");
                    if(body.getLinearVelocity().y > 0){
                        // just left the ground: bounce
                        System.out.println("Bouncing!");
                        changeState(JUMPING);
                    }
                }
            }
        });
    }

    @Override
    public void initAssets() {
        this.animations = new HashMap<>();
        AssetManager am = AssetManager.getInstance();
        TextureRegion[] frames = {
                am.get("brick1"),
                am.get("brick2"),
                am.get("clover")
        };
        Animation idleAnimation = new Animation(frames, 0.5f);
        this.animations.put(IDLE, idleAnimation);

        frames = new TextureRegion[]{
                am.get("clover"),
                am.get("rock01"),
                am.get("rock02")
        };

        Animation walkingAnimation = new Animation(frames, 1f);
        this.animations.put(WALKING, walkingAnimation);

    }

    @Override
    void initStates() {
        HashMap<String, StateGenerator> states = new HashMap<>();
        states.put(IDLE, () -> new FrogIdleState(this));
        states.put(WALKING, () -> new FrogWalkingState(this));
        states.put(JUMPING, () -> new FrogJumpingState(this));
        this.stateMachine = new StateMachine(states);
        this.changeState(IDLE);
    }

    void initBody(World world, float x, float y){
        BodyFactory factory = BodyFactory.getInstance(world);
        this.body = factory.makeRectBody(x, y, BODY_WIDTH, BODY_HEIGHT, Material.STEEL, BodyDef.BodyType.DynamicBody, false);
        this.body.setUserData("frog");
    }

    void onWallCollision(){
        String stateId = getState();
        if(stateId.equals(JUMPING)){
            System.out.println("Touched the ground!");
            if(shouldWalk()){
                changeState(WALKING);
            } else{
                changeState(IDLE);
            }
        }
    }

    public static boolean shouldWalk(){
        return Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D);
    }

}
