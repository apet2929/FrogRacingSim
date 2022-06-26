package com.apet2929.game;

import com.apet2929.game.engine.states.Box2DTestState;
import com.apet2929.game.engine.states.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	private SpriteBatch sb;
	private GameStateManager gsm;
	Texture img;

	@Override
	public void create () {
		new AssetManager();
		sb = new SpriteBatch();
		gsm = new GameStateManager(new OrthographicCamera(), sb);
		img = new Texture("badlogic.jpg");

		gsm.push(new Box2DTestState(gsm));
	}

	@Override
	public void render() {
//		ScreenUtils.clear(1, 0, 0, 1);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
	}

	@Override
	public void dispose () {
		sb.dispose();
		img.dispose();
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width,height);
	}
}
