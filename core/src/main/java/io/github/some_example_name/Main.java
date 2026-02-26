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
    public ArabianosScreen arabianosScreen;
    public UFBAScreen ufbaScreen;
    public NegrolinosScreen negrolinosScreen;
    public FinalScreen finalScreen;

    @Override
    public void create(){
        Music musicMenu = Gdx.audio.newMusic(Gdx.files.internal("Sound/Oppening.mp3"));
        musicMenu.setLooping(true);
        tcrScreen = new TCRScreen();
        combatScreen = new CombatScreen();
        dialogueScreen = new DialogueScreen(this);
        madokaScreen = new MadokaScreen();
        arabianosScreen = new ArabianosScreen();
        ufbaScreen = new UFBAScreen();
        negrolinosScreen = new NegrolinosScreen();
        finalScreen = new FinalScreen();
        selecaoCapitulo = new SelecaoCapitulo(this, musicMenu);
        setScreen(selecaoCapitulo);
        setScreen(tcrScreen);
        setScreen(dialogueScreen);
        setScreen(madokaScreen);
        setScreen(arabianosScreen);
        setScreen(ufbaScreen);
        setScreen(negrolinosScreen);
        setScreen(finalScreen);
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
