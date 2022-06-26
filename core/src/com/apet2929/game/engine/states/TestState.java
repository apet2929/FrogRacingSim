package com.apet2929.game.engine.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.apet2929.game.engine.Utils.clearScreen;

public class TestState extends State{
    Color color;
    Rectangle rect;
    Texture test;
    Stage stage;

    public static final ShapeRenderer SR = new ShapeRenderer();
    public TestState(GameStateManager gsm, Color color) {
        super(gsm);
        this.color = color;
        int startX = (int)(Math.random() * Gdx.graphics.getWidth());
        int startY = (int)(Math.random() * Gdx.graphics.getHeight());
        this.rect = new Rectangle(startX, startY, 100, 100);
        this.stage = new Stage();
    }

    @Override
    public void show() {
        System.out.println("TestState Show!");
        test = gsm.assetManager.get("badlogic.jpg");
        Gdx.input.setInputProcessor(stage);
        initUI();
    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            System.out.println("Transitioning to new TestState!");
            gsm.transitionTo(new LoadingState(this.gsm));
        }
        stage.act();
    }

    @Override
    public void draw(SpriteBatch sb) {
        clearScreen();
        SR.begin(ShapeRenderer.ShapeType.Filled);
        SR.setColor(color);
        SR.rect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
        SR.end();
        sb.begin();
        sb.draw(test, 0,0,100,100);
        sb.end();
        stage.getViewport().apply();

        stage.draw();
    }

    @Override
    public void dispose() {

    }

    void initUI(){
        Table root = new Table();
        root.setFillParent(true);
        root.defaults().pad(10);
        stage.addActor(root);
        Label title = new Label("Welcome, testing!", gsm.skin);
        title.setColor(1,1,1,1);
        title.setFontScale(2);
        root.add(title).padBottom(100);

    }
}
