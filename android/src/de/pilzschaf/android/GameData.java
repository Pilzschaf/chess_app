package de.pilzschaf.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Pilzschaf on 12.04.2015.
 */

public class GameData extends Thread{

    //Game
    public Texture gameKing_white;
    public Texture gameKing_black;

    //Menu
    public FreeType.Bitmap font1;

    //Intro
    public Color introBackgroundColor = new Color(0.65f, 0.65f, 0.65f, 1.0f);
    public Texture introKing_black;
    public Texture introKing_white;
    public Texture introLoadingFont;

    //Other
    public TweenManager manager;
    public boolean fullLoaded = false;
    public AndroidLauncher launcher = null;
    public NetworkConnection networkConnection = null;

    //private
    private static GameData ourInstance = new GameData();


    public static GameData getInstance() {
        return ourInstance;
    }

    private GameData() {
        //Use this for loading first ressources

        //Make sure constructor is getting called
        System.out.println("GameData constructor");

    }
    public void LoadFull(){
        //Use this for loading all ressources
        if(this.getState()==State.RUNNABLE)
            this.start();
        this.start();
        System.out.println("start loading full");
    }
    public void FirstLoad(){
        System.out.println("loadsmall");
        introKing_black = new Texture(Gdx.files.internal("introking_black.png"));
        introKing_white = new Texture(Gdx.files.internal("introking_white.png"));
        introLoadingFont = new Texture(Gdx.files.internal("loading_pixelfont.png"));

        manager = new TweenManager();
    }
    @Override
    public void run(){
        //Thread loads all ressources in background
        System.out.println("runnnnnnn");
        networkConnection = new NetworkConnection();
        networkConnection.ConnectToServer();
        //font1 = new FreeType.Bitmap(Gdx.files.internal(""));
        gameKing_black = introKing_black;
        gameKing_white = introKing_white;


        //Do this to notify other classes that loading is completed
        fullLoaded = true;
    }

}
