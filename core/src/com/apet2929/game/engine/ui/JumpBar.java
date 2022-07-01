package com.apet2929.game.engine.ui;

import com.apet2929.game.engine.box2d.entity.Frog;
import com.apet2929.game.engine.box2d.entity.PlayerFrog;
import com.apet2929.game.engine.box2d.entity.states.FrogJumpChargingState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class JumpBar {
    Sprite fullBar;
    Sprite emptyBar;

    public JumpBar(){

        Texture fullTexture = new Texture(Gdx.files.internal("raw/bar_full.png"));
        Texture emptyTexture = new Texture(Gdx.files.internal("raw/bar_empty.png"));
        fullBar = new Sprite(fullTexture);
        fullBar.setBounds( 75, 92, 20, 2);
        emptyBar = new Sprite(emptyTexture);
        emptyBar.setBounds(75, 92, 20, 2);
    }

    public void draw(SpriteBatch sb, PlayerFrog frog){
        emptyBar.draw(sb);
        drawPercentFromLeft(fullBar, sb, getPercentCharged(frog));
    }

    float getPercentCharged(PlayerFrog frog){
        float percent = 0;
        if(frog.getState().equals(Frog.JUMP_CHARGING)){
            percent = ((FrogJumpChargingState) frog.stateMachine.getCurrent()).getPercentCharged();
        }
        return percent;
    }

    void drawPercentFromLeft(Sprite sprite, SpriteBatch sb, float percent){
        sb.draw(
                sprite.getTexture(),
                sprite.getX(),                       /* x the x-coordinate in screen space                                            */
                sprite.getY(),                       /* y the y-coordinate in screen space                                            */
                sprite.getWidth() / 2,               /* originX the x-coordinate of the scaling and rotation origin relative to the screen space coordinates   */
                sprite.getHeight() / 2,              /* originY the y-coordinate of the scaling and rotation origin relative to the screen space coordinates   */
                /* We only want to draw half the width of the sprite */
                sprite.getWidth() * percent,               /* width the width in pixels                                                     */
                sprite.getHeight(),                  /* height the height in pixels                                                   */
                sprite.getScaleX(),       /* scaleX the scale of the rectangle around originX/originY in x                 */
                sprite.getScaleY(),       /* scaleY the scale of the rectangle around originX/originY in y                 */
                0 ,                      /* rotation the angle of counter clockwise rotation of the rectangle around originX/originY               */
                sprite.getRegionX(),      /* srcX the x-coordinate in texel space                                          */
                sprite.getRegionY(),      /* srcY the y-coordinate in texel space                                          */
                /* We only want to use half the source texture region */
                (int) (sprite.getRegionWidth() * percent),   /* srcWidth the source with in texels                                            */
                sprite.getRegionHeight(), /* srcHeight the source height in texels                                         */
                false,                   /* flipX whether to flip the sprite horizontally                                 */
                false);                  /* flipY whether to flip the sprite vertically  */
    }

}
