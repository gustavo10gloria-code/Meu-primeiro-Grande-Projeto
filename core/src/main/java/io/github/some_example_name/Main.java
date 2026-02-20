package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class Main extends Game {
    public TCRScreen tcrScreen;
    public CombatScreen combatScreen;
    public DialogueScreen dialogueScreen;
    public SelecaoCapitulo selecaoCapitulo;
    public MadokaScreen madokaScreen;

    @Override
    public void create(){
        Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("Sound/Oppening.mp3"));
        musicMenu.setLooping(true);
        tcrScreen = new TCRScreen();
        combatScreen = new CombatScreen();
        dialogueScreen = new DialogueScreen(this);
        madokaScreen = new MadokaScreen();
        selecaoCapitulo = new SelecaoCapitulo(this, musicMenu);
        setScreen(selecaoCapitulo);
        setScreen(tcrScreen);
        setScreen(dialogueScreen);
        setScreen(madokaScreen);
        this.setScreen((new MenuScreen(this)));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
