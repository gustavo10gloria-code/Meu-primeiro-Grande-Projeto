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

public class DialogueScreen implements Screen {
    private final Main game;
    private SpriteBatch batch;
    private Texture casaEnio;
    private Texture caxaDialogo;
    private Music casaEnioMusic;
    private BitmapFont fonte; //Essa é onde fica guardado os textos pra aparecer na tela
    private GlyphLayout layout = new GlyphLayout();
    private String[] falasComecoHistoria, falasPosTCR;
    private int falaAtualNum;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private int estadoHistoria = 1; //1: Pra puxar TCR, 2: Pra puxar Madoka


    public DialogueScreen(Main game) {
        this.game = game;
        falasComecoHistoria = new String[]{
            "Narrador: Nosso aventureiro Enio estava de boa mexendo no seu Pc.",
            "Narrador: Quando do nada vé uma noticia surpreendente.",
            "Jornal: O Rei da TCR, Bruno Magrileno, anuncia a sua renuncia com um comunicado!!!",
            "Bruno Magrileno: Eu já estou velho, não tenho mais muito tempo de vida, irei passar o meu posto de rei para outro.",
            "Narrador: Enio fica ouriçado em ver isso.",
            "Bruno Magrileno: O requisito pro meu sucessor ir ao trono sera!!!",
            "Bruno Magrileno: ...",
            "Bruno Magrileno: .....",
            "Bruno Magrileno: .......",
            "Bruno Magrileno: O pretendente ao trono tera que comer a melhor comida de cada reino e no final me demonstrar a sua força em um duelo",
            "Bruno Magrileno: Se ele me vencer, será coroado como o novo rei da TCR",
            "Enio: Essa é a minha chance de subir na vida, chega de POO ou Algebra Linear, eu vou é comer tudo e virar Rei!",
            "Narrador: E assim começa as aventuras do nosso bravo guerreiro",
            "Enio: MEU NOME É ENIO E EU SOU GORDO, EU ADORO COMER COISAS.",
        };
        falasPosTCR = new String[]{
            "Narrador: Sempre que Enio volta para a sua casa o jogo salva.",
            "Narrador: Então nosso lindo Enio, decide ir descansar em sua casa após encher a pança.",
            "Enio: Caramba to muito cheio, mas amanhã já vou para o proximo Reino.",
            "Enio: Vou pro reino vizinho da Madoka, hm deixa eu ver aqui qual é a comida de lá.",
            "Enio: Oxe porra.",
            "Enio: Cachorro flambado?",
            "Esposa Enio: Sim meu amor, é uma iguaria, mas são poucas pessoas que são autorizadas a comer isso",
            "Enio: Tá bom né, vou ter que passar por essa.",
            "Narrador: Então nosso gordinho se prepara para no dia seguinte ir para a Madoka.",
        };
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        casaEnio = new Texture("Backgrounds/casaEnio.png");
        caxaDialogo = new Texture("UI/caixaDialogo.png");
        //Configurações de Fonte
        FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("Fontes/PixelifySans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametro.size = 38; //O tamanho da fonte
        parametro.color = Color.WHITE; //A cor da fonte
        parametro.borderWidth = 3; // Colocar a borda
        parametro.borderColor = Color.BLACK; //Cor da borda
        parametro.shadowOffsetX = 3; //Sombra da borda pra dar profundidade
        parametro.shadowOffsetY = 3;
        fonte = gerador.generateFont(parametro);
        gerador.dispose();
        //Musica da casa
        casaEnioMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/QuartoEnio.mp3"));
        casaEnioMusic.setLooping(true);
        //Configuração do tamanho da tela
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        casaEnioMusic.play();
        batch.draw(casaEnio, 0, 0, 1920, 1080);
        batch.draw(caxaDialogo, 160, 40, 1600, 250);
        //Divisão das falas e configs
        String[] falaAtual = new String[2];
        if (estadoHistoria == 1) falaAtual = falasComecoHistoria;
        if (estadoHistoria == 2) falaAtual = falasPosTCR;

        if (falaAtualNum < falaAtual.length) {
            String[] partes = falaAtual[falaAtualNum].split(": "); //Pra dividir a frase onde tem :
            String nome = partes[0];
            String mensagem = partes[1];
            if (nome.equals("Enio")) {
                fonte.setColor(Color.BROWN);
            } else if (nome.equals("Bruno Magrileno")) {
                fonte.setColor(Color.GOLD);
            } else if (nome.equals("Jornal")) {
                fonte.setColor(Color.FIREBRICK);
            } else if (nome.equals("Esposa Enio")) {
                fonte.setColor(Color.BLUE);
            } else {
                fonte.setColor(Color.CYAN);
            }
            fonte.draw(batch, nome + ":", 220, 260); //Pra puxar o nome
            fonte.setColor(Color.WHITE);
            fonte.draw(batch, mensagem, 220, 210, 1480, -1, true); //Puxar as falas
            fonte.draw(batch, "[Aperte Enter]", 1300, 100);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (falaAtualNum < falaAtual.length - 1) {
                    falaAtualNum++;
                } else {
                    casaEnioMusic.stop();
                    if (estadoHistoria == 1) {
                        game.setScreen(new TCRScreen());
                    } else if (estadoHistoria == 2) {
                        game.setScreen(new MadokaScreen());
                    }
                }
            }
        }
        batch.end();
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
    public void dispose() { //Liberar espaço na memoria
        batch.dispose();
        casaEnio.dispose();
        fonte.dispose();
        caxaDialogo.dispose();
        casaEnioMusic.dispose();
    }

    public int getEstadoHistoria() {
        return estadoHistoria;
    }

    public void setEstadoHistoria(int estadoHistoria) {
        this.estadoHistoria = estadoHistoria;
    }
}
