package com.apet2929.game.engine.states;

import com.apet2929.game.engine.box2d.entity.Wall;
import com.apet2929.game.engine.box2d.entity.Ball;
import com.apet2929.game.engine.level.Level;
import com.apet2929.game.engine.level.LevelLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static com.apet2929.game.engine.Utils.*;

public class Box2DTestState extends State {
    FitViewport viewport;
    FitViewport stageViewport;

    Stage stage;
    Skin skin;

    Ball ball;
    Level level;
    Box2DDebugRenderer debugRenderer;
    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.getCamera().position.set(35, 40, 0);
        viewport.getCamera().update();
        stageViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.debugRenderer = new Box2DDebugRenderer();
        initWorld();
        initUI();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        ball.getBody().applyForce(100.0f, 0.0f, ball.getBody().getPosition().x, ball.getBody().getPosition().y, true);

        level.getWorld().step(delta, 6, 2);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("viewport.getCamera().position = " + viewport.getCamera().position);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            System.out.println("delta = " + delta);
            viewport.getCamera().position.add(0,100f*delta,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            viewport.getCamera().position.add(0, -100f*delta, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            viewport.getCamera().position.add(100f*delta,0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            viewport.getCamera().position.add(-100f*delta,0, 0);
        }
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

        debugRenderer.render(level.getWorld(), viewport.getCamera().combined);
//        stage.draw();
    }

    void drawUI(){
        stageViewport.apply();
        stage.draw();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stageViewport.update(width, height);
    }

    void initWorld(){
        int[][] tiles =
        {
                {-1,-1,-1,-1},
                {-1,-1,-1,-1},
                {-1,-1,-1,-1},
                {0,0,0,0,0,0,0,0,0,0,0,0}
        };
        this.level = LevelLoader.Load(tiles);

//        new Wall(world, 0, -25, VIEWPORT_SIZE, 20f);
        ball = new Ball(level.getWorld(), 0, 50);
    }

    void initUI(){
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(stageViewport);

//        TODO : Find a way to scale up the text
        Table root = new Table();
        stage.addActor(root);
        root.setSize(stage.getWidth(), stage.getHeight());

        root.defaults().pad(10);

        TextButton textButton = new TextButton("How to Play", skin);
        textButton.setScale(10);
//        TextButton.TextButtonStyle style = textButton.getStyle();
//        textButton.setStyle(textButton.getStyle());

        root.add(textButton).top().center().spaceBottom(50).width(300).height(200);

        root.row();

//        Label label = new Label("You play as an underwater explorer, \n" +
//                " navigating through the dangerous seas \n" +
//                "in his trusty steam-powered ship. \n " +
//                "To THRUST forward, press and hold space. \n" +
//                "To rotate, press the \n" +
//                "LEFT and RIGHT arrow keys. \n" +
//                "To stop, press BACK. When you THRUST, you lose STEAM. \n" +
//                "If you run out of STEAM, \n" +
//                "it's GAME OVER. Thankfully, the seas \n " +
//                "are filled with underwater volcanoes, which can \n " +
//                "restore your STEAM. Hover over them to refill! \n" +
//                "Try and avoid the fish, as you will lose \n " +
//                "STEAM if you bump into them. \n " +
//                "Can you find your way out of all 4 levels? \n " +
//                "Good luck!", skin);

        Label label = new Label("MOVE FORWARD->SPACE \n" +
                "ROTATE->LEFT/RIGHT \n " +
                "BRAKE->BACK \n " +
                "AVOID FISH \n " +
                "RESTORE STEAM \n IN VOLCANOES \n " +
                "GET TO THE END"
                , skin);
        label.setAlignment(Align.center);
        root.add(label).center();
//        label.setColor(1, 0, 0, 1);
        label.getStyle().fontColor = new Color(1, 0,0,1);
    }

    void reset(){
        ball.getBody().setTransform(new Vector2(0, 50), 0);
    }
}
