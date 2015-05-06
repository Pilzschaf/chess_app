package de.pilzschaf.android;

/**
 * Created by Pilzschaf on 12.04.2015.
 */
public interface GameState {
    public boolean Init();
    public boolean Exit();
    public boolean Move();
    public boolean Render();
}
