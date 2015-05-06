package de.pilzschaf.android;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Looper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Pilzschaf on 12.04.2015.
 */

public class Intro implements GameState {

    private SpriteBatch batch;
    private double currState = 0.0;
    private float elapsedTime = 0.0f;

    @Override
    public boolean Init() {
        batch = new SpriteBatch();
        GameData.getInstance().LoadFull();
        return true;
    }

    @Override
    public boolean Exit() {

        return true;
    }

    @Override
    public boolean Move() {
        if(GameData.getInstance().fullLoaded & GameData.getInstance().networkConnection.connected){
            //all data loaded switch to next GameState
            GameData.getInstance().launcher.SetGamestate(EGameState.Menu);
        }
        else{
            //Move Menu
            elapsedTime += Gdx.graphics.getDeltaTime();
            this.currState = (Math.sin((double)elapsedTime*1.7) / 2.0) + 0.5;
        }
        return true;
    }

    @Override
    public boolean Render() {
        //clear buffer with intro background color
        Gdx.gl.glClearColor(GameData.getInstance().introBackgroundColor.r, GameData.getInstance().introBackgroundColor.g, GameData.getInstance().introBackgroundColor.b, GameData.getInstance().introBackgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(GameData.getInstance().introKing_black, Gdx.graphics.getWidth() / 2 - GameData.getInstance().introKing_white.getWidth() / 2, Gdx.graphics.getHeight() / 2 - GameData.getInstance().introKing_white.getHeight() / 2);
        batch.draw(GameData.getInstance().introKing_white, Gdx.graphics.getWidth()/2 - GameData.getInstance().introKing_white.getWidth()/2, Gdx.graphics.getHeight()/2 - GameData.getInstance().introKing_white.getHeight()/2, GameData.getInstance().introKing_white.getWidth(), 800.0f * (float)this.currState, 0.0f, 1.0f, 1.0f, (((float)this.currState)-1.0f)*-1.0f);
        batch.draw(GameData.getInstance().introLoadingFont, Gdx.graphics.getWidth() / 2.0f -  204.0f, Gdx.graphics.getHeight() * 0.5f - GameData.getInstance().introKing_black.getHeight() * 0.5f - Gdx.graphics.getHeight() * 0.1f, 408.0f, 108.0f);
        batch.end();
        return true;
    }


}
