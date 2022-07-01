package com.apet2929.game.engine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames;
    private TextureRegion source;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public Animation(TextureRegion texture, int frameCount, float cycleTime){
        source = texture;
        frames = new Array<>();
        int frameWidth = texture.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            frames.add(new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public Animation(TextureRegion[] frames, float cycleTime){
        this.frames = new Array<>(frames);
        maxFrameTime = cycleTime / frames.length;
        frameCount = frames.length;
        frame = 0;
    }

    public void update(float deltaTime){
        currentFrameTime += deltaTime;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frame = 0;
        }
    }

    public int getFrameIndex() {
        return this.frame;
    }

    public void setCurrentFrame(int index){
        frame = index;
    }

    public TextureRegion getFrame(int index){
        return frames.get(index);
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public void reset(){
        this.frame = 0;
    }

    public void dispose(){
        if(source != null) source.getTexture().dispose();
    }

}
