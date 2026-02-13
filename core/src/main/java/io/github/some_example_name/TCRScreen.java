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


public class TCRScreen implements Screen {
    private Texture backgroundTCR, enioAtual, caxaDialogo;
    private Texture[] eniolado1, eniolado2, enioCosta, enioFrente;
    private SpriteBatch batch;
    private Music TCRMusic, lojaMusic;
    private float x = 100, y = 100;
    private float velocidade = 200;
    private float tempoAnimacao = 0;
    private int frameAtual = 0;
    private OrthographicCamera camera;
    private FitViewport viewport;
    //Limites do mapa
    private final float mapaLargura = 1920;
    private final float mapaAltura = 1080;
    //Estados da História na TCR
    private int estadoHistoria = 0; //0: Narrador Inicial, 1:Gameplay Livre, 2: Falas na loja, 3: Falas do final apos a luta
    private boolean exibindoDialogo = true; //Começar com narrador falando.

    private BitmapFont fonte;
    private String[] falasEntrada = {
        "Narrador: Então nosso aventureiro decide ir comprar a melhor comida da TCR.",
        "Enio: Pra começar a minha degustação eu vou ir comprar um chocolate Gonçalo.",
        "Enio: Vou comer e me lambuzar todo.",
    };
    private String[] falasLoja = {
        "Lojista: Ola bem vindo a minha loja, oque deseja?",
        "Enio: Ola bom dia, eu quero um chocolate Gonçalo.",
        "Lojista: É pra já meu jovem, Gonçalo vale 1 real.",
        "Enio: Certo, aqui está, 1 real é justo pra eu me lambuzar com Gonçalo.",
        "Bandido Paripe: Parado ai é um assalto, já que você tem dinheiro pra comprar chocolate.",
        "Bandido Paripe: Vai ter dinheiro pra me dar.",
        "Enio: Eu vou te dar uma surra tão grande que você vai voltar correndo pra mamãe, seu desgraçado.",
    };
    private String[] falasFinais = {
      "Enio: Pronto, finalmente posso comprar meu chocolate.",
      "Enio: Me dá um ai lojista.",
      "Lojista: Vou te dar um Gonçalo, mas vai ser por conta da casa, por vc ter dado uma nesse bandido",
      "Enio: Caramba muito obrigado.",
      "Narrador: Então nosso jovem guereirro come o chocolate e sente o seu poder aumentando.", //5
      "Enio: Ai nossa Gonçalo é uma delicia mesmo.",
      "Narrador: Agora quais aventuras aguardam o nosso grandioso guerreiro? Não sabemos ainda, mas sabemos que....",
      "Enio: MEU NOME É ENIO, EU SOU GORDO, EU AMO COMER!!!",
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
        backgroundTCR = new Texture("Backgrounds/TCR.png");
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
        TCRMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/TCRMusic.mp3"));
        TCRMusic.setLooping(true);
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
            //Esse comando é pra ele interagir com a loja, caso ele esteja perto da posição da loja
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && x > 1300 && y < 200) {
                estadoHistoria = 2;
                exibindoDialogo = true;
                falaIndice = 0;
            }
        } else { //Essa vai ser a lógica para as falas do Narrador e dos diálogos.
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                falaIndice++;
                //Essa é pra se as falas acabarem, o jogo voltar pra gameplay
                if (estadoHistoria == 0 && falaIndice >= falasEntrada.length) {
                    estadoHistoria = 1;
                    exibindoDialogo = false;
                    falaIndice = 0;
                } else if (estadoHistoria == 2 && falaIndice >= falasLoja.length) {
                    //Comando pra a luta começar
                    falaIndice = 0;
                    exibindoDialogo = false;
                    Main jogo = (Main) Gdx.app.getApplicationListener();
                    jogo.setScreen(new CombatScreen());
                    return;
                } else if (estadoHistoria == 3 && falaIndice >= falasFinais.length) {
                    Main game = (Main) Gdx.app.getApplicationListener();
                    game.dialogueScreen.setEstadoHistoria(2);
                    game.setScreen(game.dialogueScreen);
                    return;
                }
            }
        }
        //Config das musicas
        if (estadoHistoria == 0 || estadoHistoria == 1) {
            lojaMusic.stop();
            TCRMusic.play();
        } else {
            TCRMusic.stop();
            lojaMusic.play();
        }

        //Pra não deixar ele sair pelas laterais
        if (x < 0) x = 0;
        if (x > mapaLargura - 128) x = mapaAltura - 128; //128 por conta do tamanho do sprite
        if (y < 0) y = 0;

        //Pra não deixar ela sair pelo ceu ou por baixo
        float limiteYAtual = 350;//Limite de altura de onde ele pode ir
        if (x < 550) { //Pra ajustar o limite de acordo com onde Enio tá
            limiteYAtual = 180;
        } else if (x > 1250) {
            limiteYAtual = 180;
        } else {
            limiteYAtual = 250;
        }

        if (y > limiteYAtual) {
            y = limiteYAtual;
        }


        ScreenUtils.clear(0f, 0f, 0f, 1f);
        batch.begin();
        batch.draw(backgroundTCR, 0, 0, 1920, 1080);
        batch.draw(enioAtual, x, y, 128, 128);
        //Todas as configurações de diálogo
        if (exibindoDialogo) {
            batch.draw(caxaDialogo, 160, 40, 1600, 250);
            //Pro jogo n bugar, eu crio esse if temporario pra decidir quais falar amostrar
            String[] falasAtuais = new String[3];
            if (estadoHistoria == 0) falasAtuais = falasEntrada;
            if (estadoHistoria == 2) falasAtuais = falasLoja;
            if (estadoHistoria == 3) falasAtuais = falasFinais;

            if (falaIndice < falasAtuais.length) {
                String[] partes = falasAtuais[falaIndice].split(": ");
                if (partes.length >= 2) {
                    String nome = partes[0];
                    String mensagem = partes[1];
                    if (nome.equals("Enio")) {
                        fonte.setColor(Color.BROWN);
                    } else if (nome.equals("Narrador")) {
                        fonte.setColor(Color.CYAN);
                    } else if (nome.equals("Bandido Paripe")) {
                        fonte.setColor(Color.DARK_GRAY);
                    } else {
                        fonte.setColor(Color.YELLOW);
                    }
                    fonte.draw(batch, nome + ":", 220, 260);
                    fonte.setColor(Color.WHITE);
                    fonte.draw(batch, mensagem, 220, 210, 1480, -1, true);
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
        TCRMusic.stop();
        lojaMusic.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fonte.dispose();
        TCRMusic.dispose();
        lojaMusic.dispose();
        backgroundTCR.dispose();
        caxaDialogo.dispose();
        for (Texture tex : enioFrente) tex.dispose();
        for (Texture tex : enioCosta) tex.dispose();
        for (Texture tex : eniolado1) tex.dispose();
        for (Texture tex : eniolado2) tex.dispose();
    }

    public int getEstadoHistoria() {
        return estadoHistoria;
    }

    public void setEstadoHistoria(int estadoHistoria) {
        this.estadoHistoria = estadoHistoria;
    }
}


