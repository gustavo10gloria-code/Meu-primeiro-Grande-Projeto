package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {
    //Coisas essenciais da classe Menu
    private final Main game;
    private SpriteBatch batch;
    private Texture fundoMenu;
    private Music musicMenu;
    private GlyphLayout layout = new GlyphLayout();

    //Declarando as fontes.
    private BitmapFont fonteApresentacao;
    private BitmapFont fonteTitulo;

    //Pra deixar a tela certinha quando aumentar.
    private OrthographicCamera camera;
    private FitViewport viewport;

    //Oque eu vou usar pra fazer a animação inicial.
    private float tempo = 0;
    private float alpha = 0;
    private boolean mostrarMenu = false;

    public MenuScreen (Main game){
        this.game = game;
    }

    @Override
    public void show(){
        batch = new SpriteBatch();
        fundoMenu = new Texture("Backgrounds/TitleMenu.png");

        //Configuração da fonte (basicamente escolhendo uma fonte.
        FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("Fontes/Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametro= new FreeTypeFontGenerator.FreeTypeFontParameter();

        //Criando o texto pra aparecer na tela "Apresenta"
        parametro.size = 45;
        parametro.borderWidth = 2; //Criando umas bordas.
        fonteApresentacao = gerador.generateFont(parametro);

        //Criando o texto do titulo do menu
        parametro.size = 60;
        fonteTitulo = gerador.generateFont(parametro);

        gerador.dispose(); //Pra limpar o gerador de fontes depois dela ser usada.

        musicMenu = Gdx.audio.newMusic((Gdx.files.internal("Sound/Oppening.mp3")));
        musicMenu.setLooping(true); //Pra fazer a musica loopar.

        //Definindo um tamanho padrão para a tela.
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
    }

    @Override
    public void render(float delta){
        tempo += delta;//Soma o tempo que passou desde o ultimo frame.

        //Logica pra passar o tempo e depois amostrar o menu.
        if (!mostrarMenu && tempo > 5.0f){
            mostrarMenu = true;
            musicMenu.play();
        }

        ScreenUtils.clear(Color.BLACK); //Deixar a tela preta no começo.
        camera.update();
        batch.setProjectionMatrix(camera.combined); //Manter o tamanho da tela.
        batch.begin();

        if(!mostrarMenu){ //Escrever na tela
            if (alpha < 1) alpha += delta * 0.7f; //Pra o fade levar 2 segundo pra aparecer
            if (alpha > 1) alpha = 1;


            String textoIntro = "Gusta Games Aprensenta...";
            layout.setText(fonteApresentacao, textoIntro);
            float textoX = (1920 - layout.width) / 2;
            float textoY = (1080 - layout.height) / 2;

            fonteApresentacao.setColor(1, 1, 1, alpha); //Escolhendo a cor da fonte e colocando o fade
            fonteApresentacao.draw(batch, textoIntro, textoX, textoY);

            //Amostrar o menu apos 4 segundos
            if (tempo > 6.0f){
                mostrarMenu = true;
                alpha = 0; //Resetar pro fade funcionar no menu tbm.
                musicMenu.play();
            }
        } else { //Amostrar a tela de Menu principal
            if (alpha < 1) alpha += delta * 0.9f; //Fade pra o Menu Principal

            batch.setColor(1, 1, 1, alpha);
            batch.draw(fundoMenu, 0, 0, 1920, 1080);

            String textoMenu = "Pressione ENTER para Comer!";
            layout.setText(fonteTitulo, textoMenu);
            float menuX = (1920 - layout.width) / 2;
            fonteTitulo.setColor(1, 1, 1, alpha);
            fonteTitulo.draw(batch, textoMenu, menuX, 200);

            if (Gdx.input.isKeyPressed((Input.Keys.ENTER))){
                musicMenu.stop();
                game.setScreen(new SelecaoCapitulo(game));
            }
        }
        batch.end();
        batch.setColor(Color.WHITE); //Resetar o batch pra não dar bugs.
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        batch.dispose();
        fundoMenu.dispose();
    }


}
