package com.apet2929.game.engine.box2d.entity;

import com.apet2929.game.AssetManager;
import com.apet2929.game.engine.Animation;
import com.apet2929.game.engine.Network;
import com.apet2929.game.engine.box2d.BodyFactory;
import com.apet2929.game.engine.box2d.OnCollision;
import com.apet2929.game.engine.box2d.entity.states.*;
import com.apet2929.game.engine.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import static com.apet2929.game.engine.Utils.*;

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
    private String id;

    public Frog(Level level, float x, float y, String id) {
        super(EntityType.FROG);
        this.id = id;
        initBody(level.getWorld(), x, y);
        initCollisionListener(level);
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
                if(fA.getUserData().equals("foot" + id)){
                    frog = fA;
                    other = fB;
                    numFootContacts++;
                } else if(fB.getUserData().equals("foot" + id)){
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
                if(fA.getUserData().equals("foot" + id)){
                    frog = fA;
                    other = fB;
                    numFootContacts--;
                } else if(fB.getUserData().equals("foot" + id)){
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
    public void update(float delta) {
        String pastDirection = this.direction.name();
        super.update(delta);
        flip(pastDirection);
    }

    @Override
    public void initAssets() {
        this.animations = new HashMap<>();
        AssetManager am = AssetManager.getInstance();

        Animation jumping = new Animation(am.get("jump"), 9, 1.5f);
        this.animations.put(JUMPING, jumping);

        Animation idle = new Animation(am.get("grapple-start"), 2, 1.0f);
        this.animations.put(IDLE, idle);

//        Animation walkingAnimation = new Animation(frames, 1f);
        this.animations.put(WALKING, jumping);
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
        this.body.getFixtureList().get(0).setUserData("frog" + id);
        PolygonShape footFixtureShape = new PolygonShape();
        footFixtureShape.setAsBox(BODY_WIDTH/3f, BODY_HEIGHT/5, new Vector2(0, -BODY_HEIGHT/2f), 0);

        Fixture footFixture = this.body.createFixture(footFixtureShape, 0.0f);

        footFixture.setSensor(true);
        footFixture.setUserData("foot" + id);
    }

    public static boolean shouldWalk(){
        return Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D);
    }

    @Override
    public void changeState(String name) {
//        System.out.println("Changing state! " + this.getState() + " -> " + name);
        super.changeState(name);
    }

    public void drawTongue(ShapeRenderer sr){
        if(getState().equals(GRAPPLE)){
            ((FrogGrappleState)stateMachine.getCurrent()).drawTongue(sr);
        }
    }

    public String getCurrentAnimationName() {
        return getKeyByValue(animations, currentAnimation);
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPosition(Vector2 position){
        this.body.setTransform(position, 0);
    }

    public void setID(String id){
        this.id = id;
        this.body.getFixtureList().get(0).setUserData("frog" + id);
        this.body.getFixtureList().get(1).setUserData("foot" + id);
    }

    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        try {
            data.put("id", this.id);

            data.put("posX", this.getPosition().x);
            data.put("posY", this.getPosition().y);
            data.put("velX", this.body.getLinearVelocity().x);
            data.put("velY", this.body.getLinearVelocity().y);

            data.put("state", this.getState());
            data.put("animation", this.getCurrentAnimationName());
            data.put("frame", this.currentAnimation.getFrameIndex());

            Float grappleX = Float.MAX_VALUE;
            Float grappleY = Float.MAX_VALUE;
            if(this.getState().equals(GRAPPLE)){
                Vector2 grapplePos = ((FrogGrappleState) stateMachine.getCurrent()).grapplePos;
                grappleX = grapplePos.x;
                grappleY = grapplePos.y;
            }
            data.put("grappleX", grappleX);
            data.put("grappleY", grappleY);

        } catch (JSONException e){
            Gdx.app.log("Frog", "Failed to collect Frog data to JSON");
            e.printStackTrace();
        }
        return data;
    }

    public void setState(JSONObject data){
        float x = getDouble(data, "posX").floatValue();
        float y = getDouble(data, "posY").floatValue();
        this.body.setTransform(new Vector2(x, y), 0);

        float vx = getDouble(data, "velX").floatValue();
        float vy = getDouble(data, "velY").floatValue();
        this.body.setLinearVelocity(vx, vy);

        String state = getString(data, "state");
        if(!this.getState().equals(state)) {
            this.stateMachine.change(state);
        }

        String animationName = getString(data, "animation");
        int frame = getInt(data, "frame");
        if(animations.get(animationName) != this.currentAnimation){
            changeAnimation(animationName);
        }
        if(frame != currentAnimation.getFrameIndex()){
            currentAnimation.setCurrentFrame(frame);
        }

        Double grappleX = getDouble(data, "grappleX");
        Double grappleY = getDouble(data, "grappleY");
        if(state.equals(GRAPPLE)){
            FrogGrappleState grappleState = ((FrogGrappleState) stateMachine.getCurrent());
            grappleState.setGrapplePos(new Vector2(grappleX.floatValue(), grappleY.floatValue()));
        }
    }

    public String getID() {
        return id;
    }

    void flip(String pastDirection){
        if(!pastDirection.equals(this.direction.name())){
            this.sprite.setBounds(this.sprite.getX() - this.sprite.getWidth(), this.sprite.getY(), -this.sprite.getWidth(), this.sprite.getHeight());
        }
    }
}
