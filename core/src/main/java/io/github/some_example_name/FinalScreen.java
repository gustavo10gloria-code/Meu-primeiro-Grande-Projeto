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

public class FinalScreen implements Screen {
    private Texture enioAtual, caxaDialogo, enioParado, enioGordo;
    private Texture[] eniolado1, eniolado2, enioCosta, enioFrente, backgroundFinal, BM;
    private SpriteBatch batch;
    private Music TCRMusic, BrunoMusic, MusicaTriste, CoroacaoMusic, creditosMusic;
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
    private int estadoHistoria = 0; //0: Narrador Inicial,
    private boolean exibindoDialogo = true; //Começar com narrador falando.
    private BitmapFont fonte;
    private int cenarioAtual = 0;

    private String[] falasEntrada = {
        "Narrador: Depois de voltar do Reino dos Negrolinos, Enio descansa um pouco, e depois vai direto para o castelo da TCR",
        "Enio: Essa é a hora, vou virar Rei e vingar todos que morreram na mão DELES.",
    };
    private String[] falasCastelo = {
        "Bruno Magrileno: Finalmente você chegou Enio.",
        "Enio: Você já estava esperando por mim? Tudo bem, vamos acabar logo com isso, eu tenho algo pra fazer.",
        "Bruno Magrileno: Você quer derrotar eles né? Primeiro presico ver e testar a sua força, me de uma demostração.",
        "Enio: Aqui a demonstração oh, vou te quebrar na porrada seu magrelo.", //3
        "Bruno Magrileno: É isso que vamos ver.",
        "Bruno Magrileno: Expansão de Dominio.",
    };
    private String[] falasPosLuta1 = {
        "Enio: É so isso que voce tem?",
        "Bruno Magrileno: Hahaha, eu vou te contar uma historia. A HISTORIA DO NOSSO MUNDO!!!!!",
        "Bruno Magrileno: Há mais ou menos 1000 anos atras, não existiam 5 reinos, e sim 1 grande reino chamado Bahia.",
        "Bruno Magrileno: Esse reino era muito grande e forte, porem havia outro reino, tão grande quanto, o reino DELES",
        "Bruno Magrileno: ELES não conseguiam viver em paz, sempre procurando ganhar mais e mais, então o reino da Bahia virou um alvo.",
        "Bruno Magrileno: Mas o rei da Bahia, Bruno, não iria abaixar a cabeça para eles, ele lutou com todas as forças.",
        "Bruno Magrileno: Ele era meu melhor amigo.",
        "Enio: Oque? Melhor amigo? Você não é o rei Bruno? Então quem você é?",
        "Bruno Magrileno: Olhe isso, e continue ouvindo.", //8
        "Bruno Magrileno: A guerra se intensificou muito, estavamos perdendo, as forças deles eram maiores que as nossas.",
        "Bruno Magrileno: Então um dia, eles invadiram o Castelo, e so sobrou eu e Bruno, eu era a ultima linha de defesa, infelizmente fui derrotado pelo rei deles.",
        "Bruno Magrileno: Depois de me derrotaram eles mataram o rei. Eu fiquei me perguntando porque me deixaram vivo",
        "Bruno Magrileno: Então o mago deles se aproximou de mim, drenou todo o meu poder, e me transformou na imagem de Bruno, mas uma versão magra e fraca.",
        "Bruno Magrileno: Uma zombaria do legado dele.",
        "Bruno Magrileno: Eles me forçaram a fingir que era Bruno, se não voltariam e destruiriam oque sobrou da Bahia.",
        "Bruno Magrileno: ELES colocaram um domo em volta do reino, que impedia qualquer um de entrar e sair",
        "Bruno Magrileno: Eu vi reinos Nascerem e Cairem, o propio Reino da Bahia virou uma fração do seu poder antigo, se transformando na TCR.",
        "Enio: Mas quem são ELES? Todo mundo so fala ELES, mas ninguem fala quem são",
        "Bruno Magrileno: É porque ninguem sabe quem são, eles matam na hora a pessoa se ela descobrir, e no meu caso eu sabia, mas aquele mago desgraçado.",
        "Bruno Magrileno: Ele tirou toda a minha identidade, ele apagou a memoria dos que eu amava, apagou da minha memoria quem eram ELES, apagou até o meu nome",
        "Bruno Magrileno: Eu não tinha outra opção mais, alem de ser Bruno, mas eu juntei forças por todo esse tempo, eu treinei escondido até me tornar tão forte quanto antes.",
        "Bruno Magrileno: Mas já estou velho e presciso passar essa força para outro, então propus um desafio, que qualquer um que comesse as 5 melhores comidas, seria digno de me enfrentar em uma luta.",
        "Bruno Magrileno: Esse desafio já existe a 200 anos, você foi o primeiro a conseguir isso.",
        "Bruno Magrileno: Eles tiraram tudo de mim, eu sou um rei, um REI SEM NOME. Mas irei te amostrar o meu poder verdadeiro.",
        "Bruno Magrileno: Eu não tenho e nunca tive o direito de ser chamado de Bruno, então quero que você me chame de...",
    };
    private String[] falasFinaisDefinitivas = {
        "Nameless King: Você se provou digno, tome o meu poder e vingue a gente.",
        "Narrador: Uma luz sai de dentro do Rei Sem Nome, e vai para Enio.",
        "Narrador: Enio sente uma quantidade exorbitante de poder fluindo pelo seu corpo.",
        "Narrador: Porem...",
        "Nameless King: Fazendo isso eu irei desaparecer, mas antes eu tenho mais uma ultima coisa pra te contar.",
        "Nameless King: Existe uma dimensão chamada Discord, ela é a sua porta de saida do domo, e a forma de você encontrar ELES.",
        "Nameless King: Nas catacumbas do castelo tem o portal dela. Use com sabedoria.",
        "Nameless King: Enio, obrigado por reacender a esperança no meu coração.", //7
        "Narrador: Então logo em seguida o Rei Sem Nome, some como se fosse o estalo do Thanos.",
        "Enio: Eu prometo que irei vingar todos, eu vou derrotar ELES e deixar o nosso mundo livre novamente.",
        "Narrador: Então Enio é coroado o rei, o mundo todo está em festa com isso", //10
        "Narrador: Porem ELES olham de longe, por baixo dos panos do mundo, calculando qual sera o proximo passo",
        "Narrador: Qual sera o destino de Enio? Oque sera que vai acontecer com o Mundo? Quem são eles?",
        "Narrador: Não sabemos, so sabemos de uma coisa",
        "Enio: MEU NOME É ENIO, EU SOU GORDO E ADORO COMER!!!!!",
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
        backgroundFinal = new Texture[5];
        backgroundFinal[0] = new Texture("Backgrounds/TCRCastle.png"); // Cidade
        backgroundFinal[1] = new Texture("Backgrounds/TCRCastleDentro.png"); // Castelo
        backgroundFinal[2] = new Texture("Backgrounds/Gemini_Generated_Image_jkd2gyjkd2gyjkd2.png"); // Campo de batalha
        backgroundFinal[3] = new Texture("Backgrounds/Coroação.png"); // Coroação
        backgroundFinal[4] = new Texture("Backgrounds/FIM.png");
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
        enioParado = new Texture("Enio/Enio.png");
        enioGordo = new Texture("Enio/EnioGordo.png");
        BM = new Texture[3];
        BM[0] = new Texture("Inimigos/BrunoBase.png");
        BM[1] = new Texture("Personagens/Peladinho.png");
        BM[2] = new Texture("Inimigos/Nameless King Base.png");
        //Musicas
        TCRMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/TCRMusic.mp3")); //Mudar aqui dps
        TCRMusic.setLooping(true);
        BrunoMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/BrunoMusic.mp3"));
        BrunoMusic.setLooping(true);
        MusicaTriste = Gdx.audio.newMusic(Gdx.files.internal("Sound/MusicaTensaETriste.mp3"));
        MusicaTriste.setLooping(true);
        CoroacaoMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Coroação.mp3"));
        CoroacaoMusic.setLooping(true);
        creditosMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Creditos.mp3"));
        //Falas
        FreeTypeFontGenerator gerador = new FreeTypeFontGenerator(Gdx.files.internal("Fontes/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametro = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametro.size = 30; //O tamanho da fonte
        parametro.color = Color.WHITE; //A cor da fonte
        parametro.borderWidth = 3; // Colocar a borda
        parametro.borderColor = Color.BLACK; //Cor da borda
        parametro.shadowOffsetX = 3; //Sombra da borda pra dar profundidade
        parametro.shadowOffsetY = 3;
        fonte = gerador.generateFont(parametro);
        gerador.dispose();

        if (estadoHistoria == 3) {
            falaIndice = 0;
            exibindoDialogo = true;
            cenarioAtual = 2;
        }
        if (estadoHistoria == 5) {
            falaIndice = 0;
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
        batch.draw(backgroundFinal[cenarioAtual], 0, 0, 1920, 1080);
        definirEnio();

        if (exibindoDialogo) {
            desenharCaixaDialogo();
        }

        batch.end();
    }

    private void definirEnio() {
        Texture enioAgora = enioParado;
        if (estadoHistoria == 1 && falaIndice < 3) {
            enioAgora = enioParado;
            batch.draw(enioAgora, 500, 300, 128, 128);
        } else if (estadoHistoria == 1 && falaIndice >= 3) {
            enioAgora = enioGordo;
            batch.draw(enioAgora, 500, 300, 128, 128);
        } else if (estadoHistoria == 0) {
            batch.draw(enioAtual, x, y, 128, 128);
        } else if (estadoHistoria == 3 && falaIndice <= 8) {
            enioAgora = enioParado;
            batch.draw(enioAgora, 500, 300, 200, 200);
            batch.draw(BM[0], 1000, 300, 350, 350);
        } else if (estadoHistoria == 3 && falaIndice >= 8) {
            enioAgora = enioParado;
            batch.draw(enioAgora, 500, 300, 200, 200);
            batch.draw(BM[1], 1000, 300, 350, 350);
        } else if (estadoHistoria == 5 && falaIndice >= 0 && falaIndice <= 7) {
            enioAgora = enioParado;
            batch.draw(enioAgora, 500, 300, 200, 200);
            batch.draw(BM[2], 1000, 300, 350, 350);
        } else if (estadoHistoria == 5 && falaIndice >= 10) {
            cenarioAtual = 3;
        }
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

        if (estadoHistoria == 0) {
            float limiteYAtual = 350;
            if (x < 550) {
                limiteYAtual = 240;
            } else if (x > 1200) {
                limiteYAtual = 180;
            } else {
                limiteYAtual = 240;
            }
            if (y > limiteYAtual) {
                y = limiteYAtual;
            }
        }
    }

    private void atualizarLogicaDialogos() {
        if (!exibindoDialogo) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && x > 640 && x < 1280 && y < 900 && y > 200 && cenarioAtual == 0) { //EU TENHO QUE AGEITAR O LOCAL DPS
                estadoHistoria = 1;
                cenarioAtual = 1;
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

                    if (estadoHistoria == 1) {
                        estadoHistoria = 2;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(9);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 3) {
                        estadoHistoria = 4;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(10);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 5) {
                        estadoHistoria = 6;
                        //FIM DE JOGO, DEPOIS EU FAÇO UMA TELA DE CREDITOS
                    }
                }

            }
        }
        if (estadoHistoria == 0) {
            TCRMusic.play();
        } else if (estadoHistoria == 1) {
            TCRMusic.stop();
            BrunoMusic.play();
        } else if (estadoHistoria == 3) {
            MusicaTriste.play();
        } else if (estadoHistoria == 5 && falaIndice < 10) {
            MusicaTriste.play();
        } else if (estadoHistoria == 5 && falaIndice >= 10) {
            CoroacaoMusic.play();
            MusicaTriste.stop();
        } else if (estadoHistoria == 6) {
            cenarioAtual = 4;
            creditosMusic.play();
            TCRMusic.stop();
            BrunoMusic.stop();
            MusicaTriste.stop();
            CoroacaoMusic.stop();
        } else {
            TCRMusic.stop();
            BrunoMusic.stop();
            MusicaTriste.stop();
            CoroacaoMusic.stop();
        }
    }

    private String[] pegarArrayFalasAtual() {
        if (estadoHistoria == 0) return falasEntrada;
        if (estadoHistoria == 1) return falasCastelo;
        if (estadoHistoria == 3) return falasPosLuta1;
        if (estadoHistoria == 5) return falasFinaisDefinitivas;
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
                    } else if (nome.equals("Bruno Magrileno")) {
                        fonte.setColor(Color.YELLOW);
                    } else if (nome.equals("Nameless King")) {
                        fonte.setColor(Color.NAVY);
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
        batch.dispose();
        caxaDialogo.dispose();
        enioParado.dispose();
        enioGordo.dispose();
        for (Texture t : backgroundFinal) t.dispose();
        for (Texture t : enioFrente) t.dispose();
        for (Texture t : enioCosta) t.dispose();
        for (Texture t : eniolado1) t.dispose();
        for (Texture t : eniolado2) t.dispose();
        for (Texture t : BM) t.dispose();

        TCRMusic.dispose();
        BrunoMusic.dispose();
        MusicaTriste.dispose();
        CoroacaoMusic.dispose();
        creditosMusic.dispose();
        fonte.dispose();
    }

    public int getEstadoHistoria() {
        return estadoHistoria;
    }

    public void setEstadoHistoria(int estadoHistoria) {
        this.estadoHistoria = estadoHistoria;
    }
}
