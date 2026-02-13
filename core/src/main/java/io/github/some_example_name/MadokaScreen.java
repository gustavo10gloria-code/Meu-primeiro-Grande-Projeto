package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MadokaScreen implements Screen {
    private Texture enioAtual, caxaDialogo;
    private Texture[] eniolado1, eniolado2, enioCosta, enioFrente, backgroundMadoka;
    private SpriteBatch batch;
    private Music MadokaMusic, lojaMusic;
    private float x = 100, y = 100;
    private float velocidade = 200;
    private float tempoAnimacao = 0;
    private int frameAtual = 0;
    private OrthographicCamera camera;
    private FitViewport viewport;
    //Limites do mapa
    private final float mapaLargura = 1920;
    private final float mapaAltura = 1080;
    //Estados da História na Madoka
    private int estadoHistoria = 0; //0: Narrador Inicial, 1:Gameplay Livre, 2: Falas na loja
    private boolean exibindoDialogo = true; //Começar com narrador falando.
    private BitmapFont fonte;
    private int cenarioAtual = 0;

    private String[] falasEntrada = {
        "Narrador: Depois de uma pequena viagem Enio chega na Madoka.",
        "Enio: Nossa queria ter vindo dormindo no ónibus, mas não consigo =(",
        "Enio: Mas o que vai me animar é comer, vou ali naquela lojinha pra ver se consigo comprar o cachorro."
    };
    private String[] falasLoja = {
        "Lojista: Ola bem vindo a minha loja, oque deseja?",
        "Enio: Ola bom dia, eu quero um cachorro flambado",
        "Lojista: Senhor, cachorro flambado é algo que so os da alta cupula da Madoka come.",
        "Lojista: Os Magic Quintet, se voce não for um deles não vai comer nunca",
        "Enio: Nossa que injusto!",
        "Lojista: É poise, mas se vc quiser eu tenho outras coisas",
        "Enio: Não, eu preciso de cachorro flambado.",
        "Lojista: Então vá naquele castelo e peça uma audiencia com eles, vai que voce consegue.",
        "Narrador: Então Enio vai para o castelo da Madoka.",
    };
    private int falaIndice = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
        //Todas as imagens que aparecem na tela
        caxaDialogo = new Texture("UI/caixaDialogo.png");
        backgroundMadoka = new Texture[3];
        backgroundMadoka[0] = new Texture("Backgrounds/Madoka.png"); // Cidade
        backgroundMadoka[1] = new Texture("Backgrounds/0000.png"); // Castelo
        backgroundMadoka[2] = new Texture("Backgrounds/0000.png"); // Floresta
        enioFrente = new Texture[2];
        enioFrente[0] = new Texture("Enio/EnioFrente.png");
        enioFrente[1] = new Texture("Enio/EnioFrentef.png");
        enioCosta = new Texture[2];
        enioCosta[0] = new Texture("Enio/EnioCosta.png");
        enioCosta[1] = new Texture("Enio/EnioCostac.png");
        eniolado1 = new Texture[3];
        eniolado1[0] = new Texture("Enio/Eniolado1.png");
        eniolado1[1] = new Texture("Enio/Eniolado2.png");
        eniolado1[2] = new Texture("Enio/Enioladol.png");
        eniolado2 = new Texture[3];
        eniolado2[0] = new Texture("Enio/Eniolado1,1.png");
        eniolado2[1] = new Texture("Enio/Eniolado2,2.png");
        eniolado2[2] = new Texture("Enio/Enioladol1.png");
        enioAtual = enioFrente[frameAtual];
        //Musicas
        MadokaMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/0000.mp3")); //Mudar aqui dps
        MadokaMusic.setLooping(true);
        lojaMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Lojas.mp3"));
        lojaMusic.setLooping(true);
        //Falas
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
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // 1. Logica
        atualizarMovimentacao(delta);
        atualizarLogicaDialogos();

        // 2. Desenho
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        // Desenha o fundo baseado no cenário que ele está
        batch.draw(backgroundMadoka[cenarioAtual], 0, 0, 1920, 1080);

        batch.draw(enioAtual, x, y, 128, 128);

        if (exibindoDialogo) {
            desenharCaixaDialogo();
        }

        batch.end();
    }

    private void atualizarMovimentacao(float delta) {
        boolean andando = false;
        if (!exibindoDialogo) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                y += velocidade * delta;
                andando = true;
                tempoAnimacao += delta;
                if (tempoAnimacao > 0.20f) {
                    frameAtual = (frameAtual == 0) ? 1 : 0;
                    tempoAnimacao = 0;
                    enioAtual = enioCosta[frameAtual];
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                y -= velocidade * delta;
                andando = true;
                tempoAnimacao += delta;
                if (tempoAnimacao > 0.20f) {
                    frameAtual = (frameAtual == 0) ? 1 : 0;
                    tempoAnimacao = 0;
                    enioAtual = enioFrente[frameAtual];
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                x -= velocidade * delta;
                andando = true;
                tempoAnimacao += delta;
                if (tempoAnimacao > 0.20f) {
                    frameAtual = (frameAtual + 1) % 3;
                    tempoAnimacao = 0;
                    enioAtual = eniolado2[frameAtual];
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                x += velocidade * delta;
                andando = true;
                tempoAnimacao += delta;
                if (tempoAnimacao > 0.20f) {
                    frameAtual = (frameAtual + 1) % 3;
                    tempoAnimacao = 0;
                    enioAtual = eniolado1[frameAtual];
                }
            }
        }
        if (x < 0) x = 0;
        if (x > mapaLargura - 128) x = mapaAltura - 128; //128 por conta do tamanho do sprite
        if (y < 0) y = 0;

        //Pra não deixar ela sair pelo ceu ou por baixo
        /**
         switch float limiteYAtual = 350;//Limite de altura de onde ele pode ir
         if (x < 550) { //Pra ajustar o limite de acordo com onde Enio tá
         limiteYAtual = 180;
         } else if (x > 1250) {
         limiteYAtual = 180;
         } else {
         limiteYAtual = 250;
         }

         if (y > limiteYAtual) {

         }
         Esta assim por conta que dps vou ver os tamanhos do mapa pra delimitar, e vou ter que delimitar 1 pra cada mapa tbm*/
    }

    private void atualizarLogicaDialogos() {
        if (!exibindoDialogo) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && x > 640 && x < 1280 && y < 200 && y > 100 && cenarioAtual == 0) {
                estadoHistoria = 2;
                exibindoDialogo = true;
                falaIndice = 0;
                return;
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                falaIndice++;
                String[] falasAtuais = (estadoHistoria == 0) ? falasEntrada : falasLoja;
                //Essa é pra se as falas acabarem, o jogo voltar pra gameplay
                if (falaIndice >= falasAtuais.length) {
                    exibindoDialogo = false;
                    falaIndice = 0;

                    if (estadoHistoria == 0) estadoHistoria = 1;

                    // Se acabou a conversa da loja, ele ganha a missão e pode ir pro castelo
                    if (estadoHistoria == 2) {
                        // estadoHistoria = 3; // Missão dada!
                    }
                }
            }
            if (estadoHistoria == 0 || estadoHistoria == 1) {
                lojaMusic.stop();
                MadokaMusic.play();
            } else {
                MadokaMusic.stop();
                lojaMusic.play();
            }
        }
    }

private void desenharCaixaDialogo() {
    if (exibindoDialogo) {
        batch.draw(caxaDialogo, 160, 40, 1600, 250);
        //Pro jogo n bugar, eu crio esse if temporario pra decidir quais falar amostrar
        String[] falasAtuais = new String[3];
        if (estadoHistoria == 0) falasAtuais = falasEntrada;
        if (estadoHistoria == 2) falasAtuais = falasLoja;
        // if (estadoHistoria == 3) falasAtuais = falasFinais;

        if (falaIndice < falasAtuais.length) {
            String[] partes = falasAtuais[falaIndice].split(": ");
            if (partes.length >= 2) {
                String nome = partes[0];
                String mensagem = partes[1];
                if (nome.equals("Enio")) {
                    fonte.setColor(Color.BROWN);
                } else if (nome.equals("Narrador")) {
                    fonte.setColor(Color.CYAN);
                    /**} else if (nome.equals("Bandido Paripe")) {
                     fonte.setColor(Color.DARK_GRAY);*/
                } else {
                    fonte.setColor(Color.YELLOW);
                }
                fonte.draw(batch, nome + ":", 220, 260);
                fonte.setColor(Color.WHITE);
                fonte.draw(batch, mensagem, 220, 210, 1480, -1, true);
            }
        }
    }
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

}
}
