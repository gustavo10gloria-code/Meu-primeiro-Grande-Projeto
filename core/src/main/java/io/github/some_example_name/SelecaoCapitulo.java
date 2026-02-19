package io.github.some_example_name;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SelecaoCapitulo implements Screen{
    private Main game;
    private SpriteBatch batch;
    private BitmapFont fonte;
    private int opcaoSelecionada = 0;
    private String[] opcoes = {"CONTINUAR JOGO", "CAPITULO 1: TCR", "CAPITULO 2: MADOKA", "VOLTAR"};
    private int maxCapitulo;
    private Texture background;

    public SelecaoCapitulo(Main game){
        this.game = game;
        this.batch = new SpriteBatch();
        this.fonte = new BitmapFont();
        this.maxCapitulo = GerenciadorSave.carregarCapitloMaximo();
    }

    @Override
    public void show() {
        background = new Texture("Backgrounds/TitleMenu.png");


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        atualizarLogica();
        batch.begin();

        batch.draw(background, 0, 0, 1920, 1080);


        for (int i = 0; i < opcoes.length; i++){
            //Aqui vai ser pra se o cursor estiver em cima da opção, é pra ela brilhar em amarelo
            if (i == opcaoSelecionada){
                fonte.setColor(Color.YELLOW);
            } else{
                //Se o capitulo ainda estiver bloqueado é pra ficar cinza

            }
        }


    }

    private void atualizarLogica (){

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
