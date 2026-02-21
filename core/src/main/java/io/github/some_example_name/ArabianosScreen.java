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

public class ArabianosScreen implements Screen {
    private Texture enioAtual, caxaDialogo;
    private Texture[] backgroundArabianos;
    private SpriteBatch batch;
    private Music ArabianosMusic;
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
    private int estadoHistoria = 0; //0: Narrador Inicial, 1: Palacio, 2: Luta Taboco, 3: Pos luta, 4: Luta Fase 2, 5: Pos Luta 2
    private boolean exibindoDialogo = true; //Começar com narrador falando.
    private BitmapFont fonte;
    private int cenarioAtual = 0;

    private String[] falasComeço = {
        "Narrador: Depois de alguns dias de viagem Enio, chega na terra dos Arabianos.",
        "Narrador: Porem no momento que ele chega é intimidado por soldados de lá",
        "Soldados: Parado ai Enio, o Sheik quer ver você, venha.",
        "Enio: Como assim? Como vocês sabem meu nome? Porque o Sheik quer me ver?",
        "Soldados: So nos acompanhe.",
    };
    private String[] falasPalacio = {
        "Narrador: No palacio Enio é colocado cara a acara com o Sheik Taboco",
        "Taboco: Finalmente você chegou, eu ouvi falar bem de você, me disseram que você é forte.",
        "Enio: Quem te disse isso?",
        "Taboco: Agora não importa, eu sei o seu objetivo, e posso te dar oque você quer, mas tem uma condição",
        "Taboco: Lute contra mim, vamos ter um duelo, desde que eu virei Sheik ninguem mais me desafia.",
        "Taboco: Eu quero sentir novamente o calor da batalha, se você me ganhar eu vou te dar a areia destilada, e te contarei alguns segredos.",
        "Enio: Certo, então é hora do duelo!!!",
    };
    private String[] falasPosL1 = {
        "Taboco: Você realmente é forte, mas essa não é minha força total, eu tenho algo pra te falar.",
        "Taboco: Eu era um dos Magic Quintet da Madoka, eu parei de ser um pra conquistar meu propio reino",
        "Taboco: Eu derrotei o antigo Sheik, e me apoderei do reino dele.",
        "Enio: Caramba que reviravolta, então imagino que você agora vai pra segunda fase do boss?",
        "Taboco:Sim, agora vou amostrar minha forma verdadeira de Magic Quintet.",
        "Taboco: AHHHHHHHHHHHHH",
    };
    private String[] falasPosL2 = {
        "Taboco: É realmente a minha força não é parea para a sua, você ganhou.",
        "Taboco: Tá aqui a areia destilada.",
        "Enio: Ae, agora so faltam 2 comidas.",
        "Taboco: Eu tenho algo pra te contar, você tem que ficar mais forte pra derrotar ELES.",
        "Taboco: Tem um motivo pros Magic Quintet não serem 5 mais, ELES nos dominaram, porfavor, fique mais forte.",
        "Taboco: Saiba que quando você virar o rei da TCR, ELES vão ir atras de você.",
        "Enio: Mas quem são eles???",
        "Taboco: Eu não posso contar, mas saiba que eles veem e cheiram tudo.",
        "Narrador: Depois desse aviso, Enio decide ir para casa, pensando quão forte pode ser esses inimigos para vencer dos Magic Quintet juntos."
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
        backgroundArabianos = new Texture[3];
        backgroundArabianos[0] = new Texture("Backgrounds/ArabianosBackground.png"); // Cidade
        backgroundArabianos[1] = new Texture("Backgrounds/ArabianoPalacio.png"); // Palacio
        backgroundArabianos[2] = new Texture("Backgrounds/ArabianoPalacio2.png"); // Palacio 2
        //Musicas
        ArabianosMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/ArabianoMusic.mp3"));
        ArabianosMusic.setLooping(true);
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

        if (estadoHistoria == 5) {
            ArabianosMusic.play();
            exibindoDialogo = true;
            cenarioAtual = 2;
        }
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        atualizarLogicaDialogos();

        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        // Desenha o fundo baseado no cenário que ele está
        batch.draw(backgroundArabianos[cenarioAtual], 0, 0, 1920, 1080);

        if (exibindoDialogo) {
            desenharCaixaDialogo();
        }

        batch.end();
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


                    // Se acabou a conversa da loja, ele ganha a missão e pode ir pro castelo
                    if (estadoHistoria == 0) {
                        estadoHistoria = 1;
                        cenarioAtual = 1;
                        exibindoDialogo = true;
                    } else if (estadoHistoria == 1) {
                        estadoHistoria = 2;
                        cenarioAtual = 2;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(4);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 3) {
                        estadoHistoria = 4;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(5);
                        game.setScreen(game.combatScreen);

                    } else if (estadoHistoria == 5) {
                        estadoHistoria = 6;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        game.dialogueScreen.setEstadoHistoria(4);
                        game.setScreen(game.dialogueScreen);
                    }
                }
            }
            if (estadoHistoria == 0 || estadoHistoria == 1) {
                ArabianosMusic.play();
            } else if (estadoHistoria == 2 || estadoHistoria == 4 || estadoHistoria == 6) {
                ArabianosMusic.stop();
            } else if (estadoHistoria == 3) {
                cenarioAtual = 1;
                ArabianosMusic.play();
            }
        }
    }

    private String[] pegarArrayFalasAtual() {
        if (estadoHistoria == 0) return falasComeço;
        if (estadoHistoria == 1) return falasPalacio;
        if (estadoHistoria == 3) return falasPosL1;
        if (estadoHistoria == 5) return falasPosL2;
        return new String[0]; // array vazio
    }

    private void desenharCaixaDialogo() {
        if (exibindoDialogo) {
            batch.draw(caxaDialogo, 160, 40, 1600, 200);
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
                    fonte.draw(batch, nome + ":", 220, 210);
                    fonte.setColor(Color.WHITE);
                    fonte.draw(batch, mensagem, 220, 160, 1480, -1, true);
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
