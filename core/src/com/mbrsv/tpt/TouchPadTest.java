package com.mbrsv.tpt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TouchPadTest extends ApplicationAdapter {

	private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));

        touchpadStyle = new TouchpadStyle();

        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15, 15, 200, 200);

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

        blockTexture = new Texture(Gdx.files.internal("data/block.png"));
        blockSprite = new Sprite(blockTexture);

        blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);

        blockSpeed = 5;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        blockSprite.setPosition(blockSprite.getX() + touchpad.getKnobPercentX() * blockSpeed,
                blockSprite.getY() + touchpad.getKnobPercentY() * blockSpeed);

		batch.begin();
		blockSprite.draw(batch);
		batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}
}
