package com.apet2929.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class AssetManager {
    /*
    AssetManager is a Singleton shared across the program. It only needs to be instantiated once,
    After that, call AssetManager.getInstance() to get the instance.
     */
    private ArrayList<TextureAtlas> atlases;
    private static AssetManager instance;

    public static AssetManager getInstance(){
        if(instance == null) {
            return new AssetManager();
        }
        return instance;
    }

    public TextureRegion get(String name) {
        for (TextureAtlas atlas : atlases) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(name);
            if(region != null) return region;
        }
        System.err.println("(AssetManager) Texture with name " + name + " not found!");
        return this.getTextureNotFound();
    }

    public Array<TextureAtlas.AtlasRegion> getAnimation(String name){
        for (TextureAtlas atlas : atlases) {
            Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions(name);
            if(regions.size != 0) return regions;
        }
        System.err.println("(AssetManager) Texture with name " + name + " not found!");
        return this.getTextureNotFoundList();
    }

    protected AssetManager(){
        if(instance != null){
            throw new Error("AssetManager has already been instantiated!");
        }
        instance = this;
        atlases = new ArrayList<>();
        atlases.add(new TextureAtlas("world-tiles.atlas"));
        atlases.add(new TextureAtlas("frog-racer.atlas"));
    }

    public void printTextureNames(){
        atlases.forEach((TextureAtlas atlas) -> {
            System.out.println("Textures in atlas: ");
            atlas.getRegions().forEach((TextureAtlas.AtlasRegion region) -> {
                System.out.println("Region name = " + region.name);
            });
        });
    }

    public Array<TextureAtlas.AtlasRegion> getTextureNotFoundList(){
        return this.atlases.get(0).findRegions("not_found");
    }

    public TextureRegion getTextureNotFound(){
        return this.atlases.get(0).findRegion("not_found");
    }

    public void dispose(){
        atlases.forEach(TextureAtlas::dispose);
    }
}
