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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

import static com.apet2929.game.engine.Utils.TILE_SIZE;

public class Frog extends SmartEntity {
    public static final String IDLE = "idle";
    public static final String JUMPING = "jump";
    public static final String WALKING = "walking";
    public static final String JUMP_CHARGING = "jump_charging";
    public static final String GRAPPLE = "grapple";

    public static float BODY_WIDTH = 0.7f * TILE_SIZE * 2;
    public static float BODY_HEIGHT = 0.5f * TILE_SIZE * 2;
    private int numFootContacts;
    private Direction direction;

    public Frog(World world, float x, float y) {
        super(EntityType.FROG);
        initBody(world, x, y);
        this.sprite = new Sprite(this.currentAnimation.getFrame());
        this.sprite.setSize(BODY_WIDTH, BODY_HEIGHT);
        numFootContacts = 0;
        this.direction = Direction.RIGHT;
    }

    public void initCollisionListener(Level level){
        level.addCollisionListener(new OnCollision() {

            @Override
            public void start(Fixture fA, Fixture fB) {
                Fixture frog;
                Fixture other;
                String otherID;
                if(fA.getUserData().equals("foot")){
                    frog = fA;
                    other = fB;
                    numFootContacts++;
                } else if(fB.getUserData().equals("foot")){
                    frog = fB;
                    other = fA;
                    numFootContacts++;
                } else {
                    return;
                }
                otherID = (String) other.getBody().getFixtureList().get(0).getUserData();
                if(otherID.equals("wall")){

                }
            }

            @Override
            public void end(Fixture fA, Fixture fB) {

                Fixture frog;
                Fixture other;
                String otherID;
                if(fA.getUserData().equals("foot")){
                    frog = fA;
                    other = fB;
                    numFootContacts--;
                } else if(fB.getUserData().equals("foot")){
                    frog = fB;
                    other = fA;
                    numFootContacts--;
                } else {
                    return;
                }
                otherID = (String) other.getBody().getFixtureList().get(0).getUserData();
                if(otherID.equals("wall")){
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
        states.put(JUMP_CHARGING, () -> new FrogJumpChargingState(this));
        states.put(GRAPPLE, () -> new FrogGrappleState(this));
        this.stateMachine = new StateMachine(states);
        this.changeState(IDLE);
    }

    public int getNumFootContacts(){
        return numFootContacts;
    }

    public boolean canJump() {
        return numFootContacts > 0;
    }

    void initBody(World world, float x, float y){
        BodyFactory factory = BodyFactory.getInstance(world);
        this.body = factory.makeRectBody(x, y, BODY_WIDTH, BODY_HEIGHT, Material.WOOD, BodyDef.BodyType.DynamicBody, true);
        this.body.getFixtureList().get(0).setUserData("frog");
        PolygonShape footFixtureShape = new PolygonShape();
        footFixtureShape.setAsBox(BODY_WIDTH/3f, BODY_HEIGHT/5, new Vector2(0, -BODY_HEIGHT/2f), 0);

        Fixture footFixture = this.body.createFixture(footFixtureShape, 0.0f);

        footFixture.setSensor(true);
        footFixture.setUserData("foot");
    }


    public static boolean shouldWalk(){
        return Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D);
    }

    @Override
    public void changeState(String name) {
        super.changeState(name);
        System.out.println("Changing state! " + this.getState() + " -> " + name);
    }

    public void drawTongue(ShapeRenderer sr){
        if(getState().equals(GRAPPLE)){
            ((FrogGrappleState)stateMachine.getCurrent()).drawTongue(sr);
        }
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
