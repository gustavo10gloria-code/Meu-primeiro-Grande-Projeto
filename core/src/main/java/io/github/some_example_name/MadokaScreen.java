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
    private int estadoHistoria = 0; //0: Narrador Inicial, 1:Gameplay Livre, 2: Falas na loja, 3: Castelo, 4: Floresta Bandidos, 5: Combate bandidos, 6: Dragão, 7: Combate Dragão, 8: Final Dragão
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
    private String[] falasCastelo = {
        "Lucos: Qual o seu pedido?",
        "Enio: Eu quero comer a melhor comida do reino, cachorro flambado.",
        "Tabajaro: Essa comida é so para a elite da Madoka, flashbangs que nem você não podem comer.",
        "Enio: Mas eu faço qualquer coisa.",
        "Lucos: Qualquer coisa? Hm interessante.",
        "Tabajaro: Estamos com dois problemas serios, que se voce conseguir resolver a gente te deixar comer.",
        "Tabajaro: Está tendo uma infestação de uns bandidos de merda, chamados figurantes, eles estão tentando controlar o dragão do reino.",
        "Lucos: O dragão Pedrozo, ele vive na floresta em paz, mas estão tentando controlar ele pra destruir o reino",
        "Lucos: Vá e derrote os bandidos, que voce tera a sua recompensa.",
        "Narrador: Então nosso guerreiro começa a sua andada para floresta.",
    };
    private String[] falasFloresta = {
        "Narrador: Enio acaba chegando na floresta na qual os bandidos estão.",
        "Bandidos: Oque você quer gordinho?",
        "Enio: Eu quero que vocês parem com isso de tentar dominar o dragão.",
        "Bandidos: Hahaha e quem vai fazer a gente parar? Você? Um gordo desse nem deveria levantar da cama.",
        "Enio: Eu vou dar uma lição em vocês então, pra vocês verem oque o gordo pode fazer.",
    };
    private String[] falasDragao = {
        "Bandidos: Vá Pedroso, mate ele e depois destrua todo o reino da Madoka.",
        "Enio: Ai meu Deus eu to fudido.",
    };
    private String[] falasFinais = {
        "Narrador: Então Enio volta ao castelo da Madoka.",
        "Lucos: Soube que você derrotou os bandidos, e até conseguiu quebrar o controle do Dragão.",
        "Lucos: Você é realmente muito forte, interessante.",
        "Tabajaro: Mas isso não vem ao caso agora, está na hora de você receber sua recompensa.",
        "Tabajaro: Aqui, cachorro flambado feito da melhor forma possivel.",
        "Enio: Caramba finalmente, que delicia.",
        "Narrador: Depois disso o nosso guerreiro volta para sua casa, pra o seu descanso do heroi.",
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
        backgroundMadoka = new Texture[4];
        backgroundMadoka[0] = new Texture("Backgrounds/Madoka.png"); // Cidade
        backgroundMadoka[1] = new Texture("Backgrounds/MadokaCastelo.png"); // Castelo
        backgroundMadoka[2] = new Texture("Backgrounds/MadokaFloresta.png"); // Floresta
        backgroundMadoka[3] = new Texture("Backgrounds/MadokaDragao.png"); //Dragão
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
        MadokaMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/MadokaMusic.mp3")); //Mudar aqui dps
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

        if (estadoHistoria == 8){
            exibindoDialogo = true;
        }
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

        if (estadoHistoria == 0 || estadoHistoria == 1 || estadoHistoria == 2) {
            batch.draw(enioAtual, x, y, 128, 128);
        } else if (estadoHistoria == 3) {
            batch.draw(enioAtual, 910, 300, 128, 128);
        } else if (estadoHistoria == 4) {
            batch.draw(enioAtual, 920, 300, 128, 128);
        }

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

        if (estadoHistoria == 1) {
            float limiteYAtual = 350;
            if (x < 550) {
                limiteYAtual = 180;
            } else if (x > 1250) {
                limiteYAtual = 180;
            } else {
                limiteYAtual = 140;
            }
            if (y > limiteYAtual) {
                y = limiteYAtual;
            }
        }
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
                String[] falasAtuais = pegarArrayFalasAtual();
                //Essa é pra se as falas acabarem, o jogo voltar pra gameplay
                if (falaIndice >= falasAtuais.length) {
                    exibindoDialogo = false;
                    falaIndice = 0;

                    if (estadoHistoria == 0) estadoHistoria = 1;

                    // Se acabou a conversa da loja, ele ganha a missão e pode ir pro castelo
                    if (estadoHistoria == 2) {
                        estadoHistoria = 3;
                        cenarioAtual = 1;
                        exibindoDialogo = true;
                    } else if (estadoHistoria == 3) {
                        estadoHistoria = 4;
                        cenarioAtual = 2;
                        exibindoDialogo = true;
                    } else if (estadoHistoria == 4) {
                        estadoHistoria = 5;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(2);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 6){
                        estadoHistoria = 7;
                        cenarioAtual = 1;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(3);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 8) {
                        estadoHistoria = 9;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        game.dialogueScreen.setEstadoHistoria(3);
                        game.setScreen(game.dialogueScreen);
                    }
                }
            }
            if (estadoHistoria == 0 || estadoHistoria == 1 || estadoHistoria == 3 || estadoHistoria == 4 || estadoHistoria == 8) {
                lojaMusic.stop();
                MadokaMusic.play();
            } else if (estadoHistoria == 2) {
                MadokaMusic.stop();
                lojaMusic.play();
            } else if (estadoHistoria == 5 || estadoHistoria == 9) {
                MadokaMusic.stop();
            } else if (estadoHistoria == 6){
                cenarioAtual = 3;
            }
        }
    }

    private String[] pegarArrayFalasAtual() {
        if (estadoHistoria == 0) return falasEntrada;
        if (estadoHistoria == 2) return falasLoja;
        if (estadoHistoria == 3) return falasCastelo;
        if (estadoHistoria == 4) return falasFloresta;
        if (estadoHistoria == 6) return falasDragao;
        if (estadoHistoria == 8) return falasFinais;
        return new String[0]; // array vazio
    }

    private void desenharCaixaDialogo() {
        if (exibindoDialogo) {
            batch.draw(caxaDialogo, 160, 40, 1600, 250);
            //Pro jogo n bugar, eu crio esse if temporario pra decidir quais falar amostrar
            String[] falasAtuais = pegarArrayFalasAtual();

            if (falasAtuais.length > 0 && falaIndice < falasAtuais.length) {
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
            } else {
                exibindoDialogo = false;
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

    public int getEstadoHistoria() {
        return estadoHistoria;
    }

    public void setEstadoHistoria(int estadoHistoria) {
        this.estadoHistoria = estadoHistoria;
    }
}
