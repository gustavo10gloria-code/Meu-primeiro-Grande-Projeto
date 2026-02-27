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

public class NegrolinosScreen implements Screen {
    private Texture caxaDialogo, enio, Goncalam, beijo, enioComendo, banana;
    private Texture[] backgroundNegrolinos;
    private SpriteBatch batch;
    private Music NegrolinosMusic, MusicaTriste, comendo;
    private OrthographicCamera camera;
    private FitViewport viewport;
    //Estados da História na Negrolinos
    private int estadoHistoria = 0; //0: Narrador Inicial, 1: Dentro do catelo, 2: Luta, 3: Historia final
    private boolean exibindoDialogo = true; //Começar com narrador falando.
    private BitmapFont fonte;
    private int cenarioAtual = 0;

    private String[] falasEntrada = {
        "Narrador: Apos uma looooooonga viagem Enio chega em um local de mata",
        "Narrador: Lá ele ve algo que não esperava, um reino em contrução, pessoas fazendo casas.",
        "Narrador: Então ele ve um castelo e decide ir lá, pois não viu nenhuma loja.",
    };
    private String[] falasCastelo = {
        "Narrador: No castelo ele pergunta para primeira pessoa que vé como consegue comer a banana flambada.",
        "Narrador: Falam pra ele que já estavam esperando ele, e aponta para onde ele deve ir.",
        "Narrador: Chegando lá ele encontra um homem bonito e formoso.",
        "Gonçalam: Ola Enio, bem vindo ao reino dos Negrolinos, tá aqui a Banana Flambada.",
        "Enio: Imagino que tenham falado de mim, mas você vai me dar tão facil assim?",
        "Gonçalam: Coma, eu só quero conversar por enquanto.", //5
        "Enio: Ok.", //6
        "Gonçalam: Você comeu as 5 melhores comidas do reino, você finalmente conseguiu, mas eu sei que você tem duvidas.",
        "Gonçalam: Do que aconteceu com a Madoka e a UFBA.",
        "Gonçalam: Eu era um dos Magic Quintet, o mais forte deles. Nois 5 fundamos o Reino da Madoka quando ainda era tudo mato", //9 --
        "Gonçalam: E crescemos e prosperamos bastante, viramos a maior potencia do mundo.", //10 --
        "Gonçalam: Porem, apareceu ELES, ELES queriam metade do que ganhavamos, a gente não podia aceitar isso, então recusamos", //11
        "Gonçalam: Então ELES, declararam Guerra. O rei da TCR mandou uma carta falando pra gente so acatar oque ELES pediam.",
        "Gonçalam: Mas a gente não podia aceitar aquilo, Então fomos para a luta.", //13
        "Enio: E foi assim que vocês perderam?",
        "Gonçalam: Antes de eu te contar, lute comigo, eu quero ver se você é tão forte assim mesmo,",
    };
    private String[] falasPosLuta = {
        "Gonçalam: Você é realmente muito forte, você está pronto, mas antes tenho que te contar o resto da historia.",
        "Enio: Por favor, eu presciso saber, como ELES conseguiram derrotar pessoas tão fortes.",
        "Gonçalam: Quando a guerra começou, a gente se juntou com o Reino da UFBA que era um grande reino na epoca, e que estavam sendo ameaçados tambem.", //2
        "Gonçalam: Porem eles eram muito numerosos e muito fortes.",
        "Gonçalam: Fizemos muitos sacrificios, Predoso, um dos Magic Quintet, sacrificou a sua humanidade.", //4
        "Gonçalam: Para se transformar em um dragão e ficar mais forte para derrotar ELES.",
        "Gonçalam: Mas nem isso foi pareo para a gente ganhar.",
        "Gonçalam: Então o resultado veio, a gente perdeu feio.",
        "Gonçalam: O reino da UFBA foi totalmente Obliterado, não sobrou nada.",
        "Gonçalam: Nois da Madoka sobrevivemos mas tivemos que voltar do 0", //9
        "Gonçalam: Um tempo depois Taboco saiu dos Magic Quintet para formar o seu propio Reino.",
        "Gonçalam: E alguns anos atras eu sai tambem, e fundei esse reino aqui.", //11
        "Enio: Entendi, então eles eram tão fortes ao ponto que 2 reinos juntos e poderosos não conseguiram derrotar?",
        "Enio: Como que eu vou vencer DELES? Eu não sou tão forte",
        "Gonçalam: Enio, a sua força de vontade é mais forte que todos, volte para TCR, vire o rei, e agora vamos juntar os 5 Reinos.",
        "Gonçalam: Para derrotar ELES, vamos nos preparar tambem, foram decadas e decadas vivendo escondidos, está na hora de vencermos.",
        "Enio: Obrigado por me contar tudo, agora eu tenho um assunto pra resolver na TCR.", //16
        "Enio: ESTÁ NA HORA DE ME TORNAR REI.",
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
        backgroundNegrolinos = new Texture[7];
        backgroundNegrolinos[0] = new Texture("Backgrounds/NegrolinosBackGround.png");
        backgroundNegrolinos[1] = new Texture("Backgrounds/CateloNegrolinos.png");
        backgroundNegrolinos[2] = new Texture("Backgrounds/UFBAProspera.png");
        backgroundNegrolinos[3] = new Texture("Backgrounds/MadokaInicio.png");
        backgroundNegrolinos[4] = new Texture("Backgrounds/Madoka Prospera.png");
        backgroundNegrolinos[5] = new Texture("Backgrounds/MadokaDestruida.png");
        backgroundNegrolinos[6] = new Texture("Backgrounds/NegrolinosFight.png");
        enio = new Texture("Enio/Enio.png");
        Goncalam = new Texture("Inimigos/GonçaloBase.png");
        beijo = new Texture("Enio/Beijo.png");
        banana = new Texture("Itens/BananaFlambada.png");
        enioComendo = new Texture("Enio/EnioBanana.png");
        //Musicas
        NegrolinosMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/NegrolinosMusic.mp3")); //Mudar aqui dps
        NegrolinosMusic.setLooping(true);
        MusicaTriste = Gdx.audio.newMusic(Gdx.files.internal("Sound/MusicaTristeNegrolinos.mp3"));
        MusicaTriste.setLooping(true);
        comendo = Gdx.audio.newMusic(Gdx.files.internal("Sound/Comendo.mp3"));
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
        if (estadoHistoria == 3) {
            cenarioAtual = 6;
        }
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // 1. Logica
        atualizarLogicaDialogos();

        // 2. Desenho
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        // Desenha o fundo baseado no cenário que ele está
        batch.draw(backgroundNegrolinos[cenarioAtual], 0, 0, 1920, 1080);
        if (cenarioAtual == 6 && falaIndice < 16){
            batch.draw(enio, 250, 200, 300, 300);
            batch.draw(Goncalam, 1050, 200, 600, 600);
        } else if (cenarioAtual == 6 && falaIndice >= 16){
            batch.draw(beijo, 660, 240, 600, 600);
        } else if (cenarioAtual == 1 && falaIndice == 5){
            batch.draw(banana, 660, 240, 600, 600);
        } else if (cenarioAtual == 1 && falaIndice == 6) {
            batch.draw(enioComendo, 660, 240, 600, 600);
            comendo.play();
        }

        if (exibindoDialogo) {
            desenharCaixaDialogo();
        }

        batch.end();
    }

    private void atualizarLogicaDialogos() {
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
                    Main game = (Main) Gdx.app.getApplicationListener();
                    if (game.combatScreen == null) {
                        game.combatScreen = new CombatScreen();
                    }
                    game.combatScreen.setLutaAtual(8);
                    game.setScreen(game.combatScreen);
                } else if (estadoHistoria == 3) {
                    estadoHistoria = 4;
                    Main game = (Main) Gdx.app.getApplicationListener();
                    game.finalScreen = new FinalScreen();
                }
            }
        }
        //ANTES DA LUTA
        if (estadoHistoria == 1 && falaIndice >= 9 && falaIndice <= 10) {
            cenarioAtual = 3;
        } else if (estadoHistoria == 1 && falaIndice >= 11 && falaIndice < 13) {
            cenarioAtual = 4;
        } else if (estadoHistoria == 1 && falaIndice >= 13) {
            cenarioAtual = 1;
        }
        //DEPOIS DA LUTA
        if (estadoHistoria == 3 && falaIndice >= 2 && falaIndice < 5) {
            cenarioAtual = 2;
        } else if (estadoHistoria == 3 && falaIndice >= 5 && falaIndice < 9) {
            cenarioAtual = 6;
        } else if (estadoHistoria == 3 && falaIndice >= 9 && falaIndice < 12) {
            cenarioAtual = 5;
        } else if (estadoHistoria == 3 && falaIndice >= 12) {
            cenarioAtual = 6;
        }
        if (estadoHistoria == 0 || estadoHistoria == 1) {
            MusicaTriste.stop();
            NegrolinosMusic.play();
        } else if (estadoHistoria == 3) {
            MusicaTriste.play();
        } else {
            NegrolinosMusic.stop();
            MusicaTriste.stop();
        }
    }


    private String[] pegarArrayFalasAtual() {
        if (estadoHistoria == 0) return falasEntrada;
        if (estadoHistoria == 1) return falasCastelo;
        if (estadoHistoria == 3) return falasPosLuta;
        return new String[0]; // array vazio
    }

    private void desenharCaixaDialogo() {
        if (exibindoDialogo) {
            batch.draw(caxaDialogo, 160, 20, 1600, 210);
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
                    } else if (nome.equals("Gonçalam")){
                        fonte.setColor(Color.ROYAL);
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
