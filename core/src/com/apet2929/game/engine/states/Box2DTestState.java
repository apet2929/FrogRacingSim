package com.apet2929.game.engine.states;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.Wall;
import com.apet2929.game.engine.box2d.entity.Ball;
import com.apet2929.game.engine.level.Level;
import com.apet2929.game.engine.level.LevelLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    Frog frog;
    Level level;
    Box2DDebugRenderer debugRenderer;

    Label canJumpLabel;

//    Temporary
    ShapeRenderer sr;
    public Box2DTestState(GameStateManager gsm) {
        super(gsm);
        viewport = new FitViewport(VIEWPORT_SIZE, VIEWPORT_SIZE);
        viewport.getCamera().position.set(35, 40, 0);
        viewport.getCamera().update();
        stageViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sr = new ShapeRenderer();
        this.debugRenderer = new Box2DDebugRenderer();
        initWorld();
        initUI();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
//        ball.getBody().applyForce(100.0f, 0.0f, ball.getBody().getPosition().x, ball.getBody().getPosition().y, true);

        level.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println("frog.canJump() = " + frog.canJump());
            System.out.println("frog.getNumFootContacts() = " + frog.getNumFootContacts());
        }

        updateCamera();

    }

    @Override
    public void draw(SpriteBatch sb) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        sb.setProjectionMatrix(viewport.getCamera().combined);
        sb.begin();
        level.render(sb);
        sb.end();

        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        frog.drawTongue(sr);
        sr.end();

        debugRenderer.render(level.getWorld(), viewport.getCamera().combined);
        drawUI();
    }

    void drawUI(){
        stageViewport.apply();
        canJumpLabel.setText("Can frog jump? " + frog.canJump());
        canJumpLabel.validate();
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

    void updateCamera(){
        viewport.getCamera().position.lerp(new Vector3(frog.getPosition(), viewport.getCamera().position.z), 0.1f);
        viewport.getCamera().update();
    }

    void initWorld(){
        int[][] tiles =
        {
                {2,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,0, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {-1,-1,-1,-1,-1,-1,-1,-1, -1,-1,-1,-1,-1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
        this.level = LevelLoader.Load(tiles);
        frog = this.level.getFrogs().get(0);
    }

    void initUI(){
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        stage = new Stage(stageViewport);

//        TODO : Find a way to scale up the text
        Table root = new Table();
        stage.addActor(root);
        root.setSize(stage.getWidth(), stage.getHeight());

        root.defaults().pad(10);
//
//        TextButton textButton = new TextButton("How to Play", skin);
//        textButton.setScale(10);
////        TextButton.TextButtonStyle style = textButton.getStyle();
////        textButton.setStyle(textButton.getStyle());
//        textButton.getStyle().font.getData().scale(1.3f);
//
//        root.add(textButton).top().center().spaceBottom(50).width(300).height(200);
//
//        root.row();

        canJumpLabel = new Label("Can frog jump? false", skin);
        canJumpLabel.setAlignment(Align.center);
        root.add(canJumpLabel).bottom().right();
//        canJumpLabel.setColor(1, 0, 0, 1);
        canJumpLabel.getStyle().fontColor = new Color(1, 0,0,1);
    }

}
