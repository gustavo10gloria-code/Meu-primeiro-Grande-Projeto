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

public class CombatScreen implements Screen {
    private Texture background, caixaDialogo, enioBase, enioAtual, enioLuta, enioDano, inimigoAtual, enioEspecial;
    private SpriteBatch batch;
    private Music musicBattle, musicGanhou, musicMorreu, enioDanoSound, inimigoDanoSound;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private int estadoBatalha = 0; //0: Narrador Inicial, 1:Luta, 2: Falas de finalização
    private boolean exibindoDialogo = true;
    private BitmapFont fonteFala, fonteLuta;
    //localização dos personagens na tela
    private float enioX = 280, enioY = 200;
    private float inimigoX = 1400, inimigoY = 200;

    //Pra saber em qual luta está a história
    private int lutaAtual = 1;
    //Logica de turnos
    private boolean turnoEnio = true;
    private float timerInimigo = 0;
    private int danoFinalEnio, danoFinalInimigo;
    private boolean defesa = true;
    private String logCombate = "";
    private int especialContagem = 1;


    //falas
    private String[][] todasFalasIniciais = {
        {},
        //Luta 1 Bandido
        {"Enio: Seu desgraçado, você vai aprender a não roubar os outros",
            "Bandido Paripe: Tá viajando gordinho? Eu vou é te matar agora.",
            "Narrador: Enio atualmente tem 2 opções, ou ele ataca, ou ele defende.",
            "Narrador: Se você quiser fazer ele atacar aperte [A], para defender aperte [D]."},
        //Luta 2 Bandidos Madoka
        {"Bandidos: Tá pronto pra morrer gordinho?",
            "Enio: Você vai se surpreender com a força do gordinho aqui"},
        //Luta 3 Dragão Madoka
        {"Enio: Ai meu deus como que eu vou derrotar isso?",
            "Enio: Eu vou ter que usar meu poder novo que ganhei depois de comer o chocolate",
            "Narrador: Enio agora tem um ataque especial, porem so da pra usar 1 vez na batalha, use com sabedoria."},
    };
    private String[][] todasFalasFinais = {
        {},
        //Luta 1 Bandido
        {"Enio: Toma seu bosta, era você que ia me roubar?",
            "Bandido Paripe: Por favor, piedade, não me mata, eu tenho familia.",
            "Enio: Eu não vou te matar, mas se eu te ver roubando de novo, vou matar você e sua familia.",
            "Bandido Paripe: Certo, eu juro não roubar mais, na verdade eu to com uns planos pra criar um bairro, em outra cidade.",
            "Bandido Paripe: Uma cidade nova chamada Salvador, vou me mudar pra lá e criar um bairro prospero e seguro, que tenha tudo.",
            "Enio: Certo, um dia eu vou lá visitar.",
            "Bandido Paripe: Você vai se apaixonar e tenho certeza que os seus descendentes vão morar lá.",
        },
        //Luta 2 Bandidos Madoka
        {"Bandidos: Você pode ter derrotado a gente, mas já estamos controlando o dragão",
            "Enio: Oque?",
            "Bandidos: Vai Pedroso, mata esse desgraçado",
        },
        //Luta 3 Dragão Madoka
        {"Enio: Eu consegui, pode me chamar de Dragonborne porra!!!"},
    };
    private int falaIndice = 0;

    //Vida dos personagens (luta 1, luta2, luta 3.....)
    private int vidaEnio = 100;
    private int danoEnio = 10;
    private int[] vidaEnioL = {0, 100, 125, 125};
    private int[] danoBaseEnio = {0, 10, 15, 15};
    private int vidaInimigo = 70;
    private int danoInimigo = 5;
    private int[] vidaInimigoL = {0, 70, 2, 250};
    private int[] danoBaseInimigo = {0, 5, 7, 10};
    //Cenarios e Musicas e sprites
    private String[] backgrounds = {null, "Backgrounds/TCRCombat.png", "Backgrounds/MadokaCombat.png", "Backgrounds/MadokaCombat.png"};
    private String[] musicasLuta = {null, "Sound/TCRMusicBattle.mp3", "Sound/MadokaCombat.mp3", "Sound/DragonCombat.mp3"};
    private String[] inimigosBase = {null, "Inimigos/BandidoTCR.png", "Inimigos/bandidoMadoka.png", "Inimigos/DragãoBase.png"};
    private String[] inimigosBatendo = {null, "Inimigos/BandidoTCRAtaque.png", "Inimigos/bandidoMadokaBatendo.png", "Inimigos/DragãoBatendo.png"};
    private String[] inimigoRecebendoDano = {null, "Sound/BandidoTCRDano.mp3", "Sound/BandidoTCRDano.mp3", "Sound/BandidoTCRDano.mp3"};

    @Override
    public void show() {
        //Configuraçoes de camera
        estadoBatalha = 0;
        especialContagem = 1;
        exibindoDialogo = true;
        falaIndice = 0;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        camera.position.set(1920 / 2f, 1080 / 2f, 0);
        viewport.apply();

        //Todas os backgrounds de batalha
        background = new Texture(backgrounds[lutaAtual]);
        caixaDialogo = new Texture("UI/caixaDialogo.png");

        //Todos os sprites de batalha de Enio e configurações dele
        enioBase = new Texture("Enio/EnioBase.png");
        enioLuta = new Texture("Enio/EnioAtaque.png");
        enioDano = new Texture("Enio/EnioDano.png");
        enioEspecial = new Texture("Enio/EnioEspecial.png");
        enioAtual = enioBase;
        enioDanoSound = Gdx.audio.newMusic(Gdx.files.internal("Sound/EnioDano.mp3"));
        vidaEnio = vidaEnioL[lutaAtual];

        //Todos inimigos
        inimigoAtual = new Texture(inimigosBase[lutaAtual]);
        vidaInimigo = vidaInimigoL[lutaAtual];
        danoInimigo = danoBaseInimigo[lutaAtual];
        inimigoDanoSound = Gdx.audio.newMusic(Gdx.files.internal(inimigoRecebendoDano[lutaAtual]));

        //Musicas
        musicBattle = Gdx.audio.newMusic(Gdx.files.internal(musicasLuta[lutaAtual]));
        musicBattle.setLooping(true);
        musicBattle.play();
        musicGanhou = Gdx.audio.newMusic(Gdx.files.internal("Sound/Victory.mp3"));
        musicGanhou.setLooping(true);
        musicMorreu = Gdx.audio.newMusic(Gdx.files.internal("Sound/Derrota.mp3"));

        //Falas
        FreeTypeFontGenerator gerador1 = new FreeTypeFontGenerator(Gdx.files.internal("Fontes/PixelifySans.ttf"));
        FreeTypeFontGenerator gerador2 = new FreeTypeFontGenerator(Gdx.files.internal("Fontes/RetroGaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametro.size = 38; //O tamanho da fonte
        parametro.color = Color.WHITE; //A cor da fonte
        parametro.borderWidth = 3; // Colocar a borda
        parametro.borderColor = Color.BLACK; //Cor da borda
        parametro.shadowOffsetX = 3; //Sombra da borda pra dar profundidade
        parametro.shadowOffsetY = 3;
        fonteFala = gerador1.generateFont(parametro);
        fonteLuta = gerador2.generateFont(parametro);
        gerador1.dispose();
        gerador2.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Atualizar a logica do que vai rolar agora
        atualizarLogica(delta);

        //Desenhos
        batch.begin();
        desenharCenarioPersonagens();
        desenharInterface();
        batch.end();
    }

    private void atualizarLogica(float delta) {
        //Logica de falas.
        if (exibindoDialogo && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            String[] falasAtuais = (estadoBatalha == 0) ? todasFalasIniciais[lutaAtual] : todasFalasFinais[lutaAtual];

            if (falasAtuais != null && falasAtuais.length > 0) {
                falaIndice++;

                if (falaIndice >= falasAtuais.length) {
                    if (estadoBatalha == 0) {
                        exibindoDialogo = false;
                        estadoBatalha = 1; // Começa a luta
                        falaIndice = 0;
                    } else if (estadoBatalha == 2) {
                        musicBattle.stop();
                        //TCR
                        if (lutaAtual == 1) {
                            Main game = (Main) Gdx.app.getApplicationListener();
                            game.tcrScreen.setEstadoHistoria(3);
                            game.setScreen(game.tcrScreen);
                            //MADOKA
                        } else if (lutaAtual == 2) {
                            Main game = (Main) Gdx.app.getApplicationListener();
                            game.madokaScreen.setEstadoHistoria(6);
                            game.setScreen(game.madokaScreen);
                        } else if (lutaAtual == 3) { // <-- ADICIONA ISSO
                            Main game = (Main) Gdx.app.getApplicationListener();
                            game.madokaScreen.setEstadoHistoria(8); // Final do dragão
                            game.setScreen(game.madokaScreen);
                        }
                    }
                }
            }
        }

// 2. CHECAGEM DE VITÓRIA/DERROTA (Essencial para mudar o estado)
        if (estadoBatalha == 1 && !exibindoDialogo) {
            if (vidaInimigo <= 0) {
                vidaInimigo = 0;
                if (!musicGanhou.isPlaying()) {
                    musicBattle.stop();
                    musicGanhou.play();
                    estadoBatalha = 2;
                    exibindoDialogo = true;
                    falaIndice = 0;
                }
            } else if (vidaEnio <= 0) {
                vidaEnio = 0;
                musicBattle.stop();
                musicMorreu.play();
                // Aqui você decide se volta pro menu ou reinicia, tenho que fazer dps
            }

            // TURNO DE ENIO
            if (turnoEnio && vidaInimigo > 0) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                    int chance = com.badlogic.gdx.math.MathUtils.random(1, 100);
                    defesa = false;
                    if (chance <= 15) {
                        logCombate = "Enio errou o golpe, muito burro kkkkkk.";
                    } else if (chance >= 85) {
                        vidaInimigo -= (danoBaseEnio[lutaAtual] * 2);
                        logCombate = "Enio acertou um golpe critico de " + (danoBaseEnio[lutaAtual] * 2) + "!!!";
                        enioAtual = enioLuta;
                        inimigoDanoSound.play();
                    } else {
                        logCombate = "Enio acertou um ataque de " + danoBaseEnio[lutaAtual];
                        vidaInimigo -= danoBaseEnio[lutaAtual];
                        enioAtual = enioLuta;
                        inimigoDanoSound.play();
                    }
                    turnoEnio = false;
                    timerInimigo = 0;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                    logCombate = "Enio Defendeu o ataque";
                    defesa = true;
                    turnoEnio = false;
                    timerInimigo = 0;
                }
                if (lutaAtual >= 3) {
                    if ((Gdx.input.isKeyJustPressed(Input.Keys.E)) && especialContagem == 1) {
                        logCombate = "Enio usou o especial";
                        vidaInimigo -= (danoBaseEnio[lutaAtual] * 5);
                        enioAtual = enioEspecial;
                        especialContagem = 0;
                        turnoEnio = false;
                        timerInimigo = 0;
                    }
                }
            }
            // TURNO INIMIGO
            else if (!turnoEnio && vidaInimigo > 0 && vidaEnio > 0) {
                timerInimigo += delta;
                if (timerInimigo > 2.0f) {
                    int chance = com.badlogic.gdx.math.MathUtils.random(1, 100);
                    int danoTomado = 0;

                    if (chance <= 15) {
                        logCombate = "O inimigo errou o ataque!";
                    } else if (chance >= 85) {
                        danoTomado = danoBaseInimigo[lutaAtual] * 2;
                        if (defesa) danoTomado /= 2;
                        vidaEnio -= danoTomado;
                        logCombate = "CRITICO! O inimigo deu " + danoTomado + " de dano!";
                    } else {
                        danoTomado = danoBaseInimigo[lutaAtual];
                        if (defesa) danoTomado /= 2;
                        vidaEnio -= danoTomado;
                        logCombate = "O inimigo deu " + danoTomado + " de dano";
                    }

                    if (danoTomado > 0) {
                        enioAtual = enioDano;
                        enioDanoSound.play();
                    }
                    defesa = false;
                    turnoEnio = true;
                    timerInimigo = 0;
                }
            }
        }

        if (timerInimigo > 0.5f && enioAtual != enioBase) {
            enioAtual = enioBase;
        }
    }

    private void desenharCenarioPersonagens() {
        batch.draw(background, 0, 0, 1920, 1080);
        if (lutaAtual == 3) {
            batch.draw(inimigoAtual, 1100, inimigoY, 800, 800);
        } else {
            batch.draw(inimigoAtual, inimigoX, inimigoY, 300, 300);
        }
        batch.draw(enioAtual, enioX, enioY, 300, 300);
    }

    private void desenharInterface() {
        if (estadoBatalha == 1) {
            fonteLuta.draw(batch, "Enio HP: " + vidaEnio, enioX, enioY + 350);
            if (lutaAtual == 3) {
                fonteLuta.draw(batch, "Inimigo HP:" + vidaInimigo, inimigoX, inimigoY + 800);
            } else {
                fonteLuta.draw(batch, "Inimigo HP:" + vidaInimigo, inimigoX, inimigoY + 350);
            }
            fonteLuta.draw(batch, logCombate, 50, 900);
            if (turnoEnio && !exibindoDialogo) {
                if (lutaAtual >= 3 && especialContagem == 1) {
                    fonteLuta.draw(batch, "[A] ATACAR      [D] DEFENDER      [E] ESPECIAL", 300, 150);
                } else {
                    fonteLuta.draw(batch, "[A] ATACAR      [D] DEFENDER", 700, 150);
                }
            }
        }

        if (exibindoDialogo) {
            batch.draw(caixaDialogo, 160, 40, 1600, 250);
            String[] falasAtuais;
            if (estadoBatalha == 0) {
                falasAtuais = todasFalasIniciais[lutaAtual];
            } else {
                falasAtuais = todasFalasFinais[lutaAtual];
            }
            if (falaIndice < falasAtuais.length) {
                String[] partes = falasAtuais[falaIndice].split(": "); //Para Separar o nome das falas
                if (partes[0].equals(("Enio"))) {
                    fonteFala.setColor(Color.BROWN);
                } else if (partes[0].equals(("Narrador"))) {
                    fonteFala.setColor(Color.CYAN);
                } else {
                    fonteFala.setColor(Color.DARK_GRAY);
                }
                fonteFala.draw(batch, partes[0] + ":", 220, 260);
                fonteFala.setColor(Color.WHITE); // Texto da fala sempre branco
                fonteFala.draw(batch, partes[1], 220, 210, 1480, -1, true);
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
        musicBattle.stop();
        musicGanhou.stop();
        musicMorreu.stop();
    }

    @Override
    public void dispose() {
        // Limpando as texturas
        background.dispose();
        caixaDialogo.dispose();
        enioBase.dispose();
        enioLuta.dispose();
        enioDano.dispose();
        inimigoAtual.dispose();

        // Limpando sons
        musicBattle.dispose();
        musicGanhou.dispose();
        musicMorreu.dispose();
        enioDanoSound.dispose();
        inimigoDanoSound.dispose();

        // Outros
        batch.dispose();
        fonteFala.dispose();
        fonteLuta.dispose();
    }

    public int getLutaAtual() {
        return lutaAtual;
    }

    public void setLutaAtual(int lutaAtual) {
        this.lutaAtual = lutaAtual;
    }
}
