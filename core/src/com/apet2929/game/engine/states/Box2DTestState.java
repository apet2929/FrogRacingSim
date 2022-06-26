package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.apet2929.game.engine.Utils.*;

public class Box2DTestState extends State {
    FitViewport viewport;
    World world;
    Sprite sprite;
    Body body;

    Box2DDebugRenderer debugRenderer;
    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        sprite.setBounds(0,0, TILE_SIZE*2, TILE_SIZE*2);
        sprite.setCenter(0, 0);
        initWorld();
        this.debugRenderer = new Box2DDebugRenderer();

    }

    @Override
    public void update(float delta) {
        body.applyForce(100.0f, 0.0f, body.getPosition().x, body.getPosition().y, true);

        world.step(delta, 6, 2);
        sprite.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("body.getPosition() = " + body.getPosition());
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            System.out.println("delta = " + delta);
            viewport.getCamera().position.x = -100f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            viewport.getCamera().position.x = 100f;
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.A)){
//            camera.translate(new Vector3(-100f * delta, 0, 0));
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.D)){
//            camera.translate(new Vector3(100f * delta, 0, 0));
//        }
        viewport.getCamera().update();

    }

    @Override
    public void draw(SpriteBatch sb) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        sprite.setCenter(body.getPosition().x, body.getPosition().y);
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        sprite.draw(sb);
        sb.end();

        debugRenderer.render(world, viewport.getCamera().combined);

    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    void initWorld(){
        this.world = new World(new Vector2(0, -50f), true);
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 50);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.4f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

        // Create our body definition
        BodyDef groundBodyDef =new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, -25));

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(viewport.getCamera().viewportWidth, 10.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }
}
