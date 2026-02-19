package io.github.some_example_name;

import com.badlogic.gdx.Game;


public class Main extends Game {
    public TCRScreen tcrScreen;
    public CombatScreen combatScreen;
    public DialogueScreen dialogueScreen;
    public SelecaoCapitulo selecaoCapitulo;

    @Override
    public void create(){
        tcrScreen = new TCRScreen();
        dialogueScreen = new DialogueScreen(this);
        selecaoCapitulo = new SelecaoCapitulo(this);
        setScreen(selecaoCapitulo);
        setScreen(tcrScreen);
        setScreen(dialogueScreen);
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
