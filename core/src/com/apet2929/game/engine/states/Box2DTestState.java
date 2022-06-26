package com.apet2929.game.engine.states;

import com.apet2929.game.engine.box2d.Wall;
import com.apet2929.game.engine.box2d.entity.Ball;
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
    Ball ball;
    Box2DDebugRenderer debugRenderer;
    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initWorld();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float delta) {
        ball.getBody().applyForce(100.0f, 0.0f, ball.getBody().getPosition().x, ball.getBody().getPosition().y, true);

        world.step(delta, 6, 2);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            reset();
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
        sb.setProjectionMatrix(viewport.getCamera().combined);
//        sb.begin();
//        ball.render(sb);
//        sb.end();

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
        new Wall(world, 0, -25, VIEWPORT_SIZE, 20f);
        ball = new Ball(world, 0, 50);
    }

    void reset(){
        ball.getBody().setTransform(new Vector2(0, 50), 0);
    }
}
