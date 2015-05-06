package de.pilzschaf.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import aurelienribon.tweenengine.TweenManager;

public class AndroidLauncher extends AndroidApplication implements ApplicationListener, ConnectionLostDialogHandler{
    View gameView;
    private EGameState currentState = EGameState.None;
    private Intro intro = new Intro();

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        GameData.getInstance().launcher = this;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useImmersiveMode = true;
        gameView = initializeForView(this, config);
        setContentView(gameView);

	}

    @Override
    public void create() {
        this.SetGamestate(EGameState.Intro);
        GameData.getInstance().FirstLoad();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        //update tween manager
        GameData.getInstance().manager.update(Gdx.graphics.getDeltaTime());

        switch(currentState){
            case Intro:
                intro.Move();
                intro.Render();
                break;
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void SetGamestate(EGameState newState){
        switch(currentState){
            case Intro:
                intro.Exit();
                break;
        }
        switch(newState){
            case Intro:
                intro.Init();
                break;
        }
        currentState = newState;
    }

    public EGameState GetGameState(){
        return currentState;
    }

    @Override
    public void showConnectionLostDialog(final ConnectionLostConfirmInstance confirmInstance) {
        gameView.post(new Runnable() {
            public void run() {
                new AlertDialog.Builder(AndroidLauncher.this)
                        .setTitle("Couldn't reach server!")
                        //.setMessage("Are you sure?")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmInstance.retry();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmInstance.quit();
                                dialog.cancel();
                            }
                        })
                        .create().show();
            }
        });
    }
}
