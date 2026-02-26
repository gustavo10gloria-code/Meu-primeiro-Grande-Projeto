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

public class UFBAScreen implements Screen {
    private Texture caxaDialogo;
    private Texture[] backgroundUFBA;
    private SpriteBatch batch;
    private Music UFBAMusic, MusicaTriste;
    private OrthographicCamera camera;
    private FitViewport viewport;
    //Limites do mapa
    private final float mapaLargura = 1920;
    private final float mapaAltura = 1080;
    //Estados da História na UFBA
    private int estadoHistoria = 0; //0: Narrador Inicial, 1: Dentro do PAF 1, 2: No PAF 3, 3: Luta1, 4: Apos Luta 1, 5: luta2, 6: Apos Luta 2
    private boolean exibindoDialogo = true; //Começar com narrador falando.
    private BitmapFont fonte;
    private int cenarioAtual = 0;

    private String[] falasComeco = {
        "Narrador: Enio chega na UFBA, porem ve um cenário bizzaro.",
        "Narrador: Um local todo destruído, abandonado, claramente foi o cenário de uma grande luta",
        "Narrador: E pelo visto o Reino da UFBA não saiu vitorioso.",
        "Enio: Meu Deus, o que aconteceu aqui? Como pode existir um lugar assim?",
        "Narrador: Derrepente ele ouve um barulho vindo de um grande prédio, com o nome escrito PAF 1, então ele decide entrar nele.",
    };
    private String[] falasPaf1 = {
        "Enio: Olá, tem alguém aqui?",
        "Paulo Freire: Faz muito tempo que ninguém vem aqui, quem seria você?",
        "Enio: Meu nome é Enio, eu estou aqui pra comer a melhor comida desse Reino",
        "Paulo Freire: Hahahaha, melhor comida? Aqui mal temos comida, mas se você quiser saber qual é, é uma sopa chamada RU.",
        "Enio: E como eu consigo essa sopa senhor?",
        "Paulo Freire: Pode me chamar de Paulo Freire, infelizmente estamos com escassez de comida, se você conseguir caçar, talvez consiga a sopa",
        "Enio: Caçar? Caçar o que?",
        "Paulo Freire: Larvas e Morcegos, é disso que o RU é feito, vai encarar?",
        "Enio: Ah, sacrifícios devem ser feitos, eu vou.",
        "Paulo Freire: Muito bem, vá até um prédio que tem escrito PAF 3, lá voce vai encontrar de sobra os ingredientes.",
        "Enio: Ai meu Deus, no que eu me meti",
    };
    private String[] falasPaf3 = {
        "Enio: Tá aqui é o PAF 3, eu ja vejo algumas larvas e morcegos, ele só esqueceu de me falar uma coisa besta",
        "Enio: AS LARVAS E MORCEGOS SÂO GIGANTES!!!",
    };
    private String[] falaComplementar = {
        "Enio: ..."
    };
    private String[] falasFinais = {
        "Enio: Ai que nojo, como eu vou comer esses negócios?",
        "Enio: Isso vai me deixar mais impusinado do que Pipoca com Sal",
        "Enio: Mas pra ser rei eu preciso fazer isso, vamo Enio.",
        "Enio: Tá aqui suas larvas e morcegos, da proxima vez avisa que são gigantes.",
        "Paulo Freire: Hahaha o meu metodo de ensino é o melhor.",
        "Paulo Freire: Muito bem, você conseguiu, tome essa ficha e espere um pouco",
        "Narrador: 30 minutos depois.",
        "Paulo Freire: Aqui está coma.",
        "Enio: Hm, pior que é gostoso.",
        "Paulo Freire: Eu sei muito bem quem é você.",
        "Paulo Freire: Você é o guerreiro que tem ido de Reino em Reino comendo as melhores comidas pra virar Rei.",
        "Enio: Como você sabe disso?",
        "Paulo Freire: É porque na verdade eu era o Rei desse Reino antes de ser destruído.",
        "Paulo Freire: Aqui era um Reino prospero com muitos habitantes, mas houve uma guerra, que nois perdemos.",
        "Paulo Freire: Nem com forças aliadas a gente era pareo para ELES. Eram tantos e tão fortes, não tivemos chances.",
        "Paulo Freire: Fomos devastados, eu sei que você é um cara forte, mas tem que ficar mais forte ainda, para derrotar ELES.",
        "Paulo Freire: A LIBERDADE DO MUNDO ESTÁ EM SUAS MÃOS ENIO, VINGUE TODOS QUE MORRERAM NAS MÃOS DELES!!!",
        "Narrador: Com aquelas palavras em sua mente, Enio voltou para casa mais determinado do que nunca.",
        "Enio: Eu vou acabar com esse mal que assola o mundo, custe o que custa.",
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
        backgroundUFBA = new Texture[4];
        backgroundUFBA[0] = new Texture("Backgrounds/UFBABackground.png"); // Chegada
        backgroundUFBA[1] = new Texture("Backgrounds/DentroPaf1.png"); // PAF1
        backgroundUFBA[2] = new Texture("Backgrounds/Paf3.png"); // PAF3
        backgroundUFBA[3] = new Texture("Backgrounds/DentroPaf3.png"); // PAF3 Dentro
        //Musicas
        UFBAMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/UFBAMusic.mp3")); //Mudar aqui dps
        UFBAMusic.setLooping(true);
        MusicaTriste = Gdx.audio.newMusic(Gdx.files.internal("Sound/MusicaTriste.mp3"));
        MusicaTriste.setLooping(true);
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
        if (estadoHistoria == 6){
            exibindoDialogo = true;
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
        batch.draw(backgroundUFBA[cenarioAtual], 0, 0, 1920, 1080);

        /*if (estadoHistoria == 0 || estadoHistoria == 1 || estadoHistoria == 2) {
            batch.draw(enioAtual, x, y, 128, 128);
        } else if (estadoHistoria == 3) {
            batch.draw(enioAtual, 910, 300, 128, 128);
        } else if (estadoHistoria == 4) {
            batch.draw(enioAtual, 920, 300, 128, 128);
        }*/

        if (exibindoDialogo) {
            desenharCaixaDialogo();
        }

        batch.end();
    }

    public void atualizarLogicaDialogos() {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                falaIndice++;
                String[] falasAtuais = pegarArrayFalasAtual();
                //Essa é pra se as falas acabarem, o jogo voltar pra gameplay
                if (falaIndice >= falasAtuais.length) {
                    exibindoDialogo = false;
                    falaIndice = 0;


                    if (estadoHistoria == 0) {
                        estadoHistoria = 1;
                        cenarioAtual = 1;
                        exibindoDialogo = true;
                    } else if (estadoHistoria == 1) {
                        estadoHistoria = 2;
                        cenarioAtual = 2;
                        exibindoDialogo = true;
                    } else if (estadoHistoria == 2) {
                        estadoHistoria = 3;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(6);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 4) {
                        System.out.println("DEBUG");
                        estadoHistoria = 5;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        if (game.combatScreen == null) {
                            game.combatScreen = new CombatScreen();
                        }
                        game.combatScreen.setLutaAtual(7);
                        game.setScreen(game.combatScreen);
                    } else if (estadoHistoria == 6) {
                        estadoHistoria = 7;
                        Main game = (Main) Gdx.app.getApplicationListener();
                        game.dialogueScreen.setEstadoHistoria(5);
                        game.setScreen(game.dialogueScreen);
                    }
                }
            }
            if (falaIndice >= 2 && estadoHistoria == 6){
                cenarioAtual = 1;
            }
            if (falaIndice >= 9 && estadoHistoria == 6) {
                MusicaTriste.play();
                UFBAMusic.stop();
            } else {
                if (estadoHistoria == 0 || estadoHistoria == 1 || estadoHistoria == 6) {
                    MusicaTriste.stop();
                    UFBAMusic.play();
                } else if (estadoHistoria == 2 && falaIndice == 1) {
                    cenarioAtual = 3;
                } else if (estadoHistoria == 3) {
                    UFBAMusic.stop();
                } else {
                    UFBAMusic.stop();
                    MusicaTriste.stop();
                }
            }
        }


    private String[] pegarArrayFalasAtual() {
        if (estadoHistoria == 0) return falasComeco;
        if (estadoHistoria == 1) return falasPaf1;
        if (estadoHistoria == 2) return falasPaf3;
        if (estadoHistoria == 4) return falaComplementar;
        if (estadoHistoria == 6) return falasFinais;
        return new String[0]; // array vazio
    }


    public void desenharCaixaDialogo() {
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
