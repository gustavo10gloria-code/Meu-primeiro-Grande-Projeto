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

public class SelecaoCapitulo implements Screen {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont fonte;
    private int opcaoSelecionada = 0;
    private int estadoTela = 0;
    private String[] opcoes = {"NOVO JOGO", "CONTINUAR JOGO", "ESCOLHER CAPITULO", "CAPITULO 1: TCR", "CAPITULO 2: MADOKA", "CAPITULO 3: ARABIANOS", "CAPITULO 4: UFBA", "CAPITULO 5: NEGROLINOS", "CAPITULO 6: O FIM", "VOLTAR"};
    private int maxCapitulo;
    private Texture background;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // Controle de tempo para evitar repetição muito rápida das teclas
    private float tempoUltimaTecla = 0;
    private float intervaloTecla = 0.15f; // 150ms entre cada movimento

    private Music musicMenu, tecla, enter;

    public SelecaoCapitulo(Main game, Music musicMenu) {
        this.game = game;
        this.musicMenu = musicMenu;
        this.batch = new SpriteBatch();
        this.fonte = new BitmapFont();
        this.maxCapitulo = GerenciadorSave.carregarCapituloMaximo();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
        tecla = Gdx.audio.newMusic(Gdx.files.internal("Sound/tecla.mp3"));
        enter = Gdx.audio.newMusic(Gdx.files.internal("Sound/enter.mp3"));
        background = new Texture("Backgrounds/TitleMenu.png");

        //FONTES
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

        ScreenUtils.clear(Color.BLACK);
        atualizarLogica(delta);
        batch.begin();

        batch.draw(background, 0, 0, 1920, 1080);
        desenharOpcoes();

        batch.end();
    }

    private void atualizarLogica(float delta) {
        //Para Acumular o tempo da ultima tecla
        tempoUltimaTecla += delta;

        if (estadoTela == 0) { //Menu Principal
            if (tempoUltimaTecla > intervaloTecla) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                    opcaoSelecionada--;
                    tecla.play();
                    tempoUltimaTecla = 0; //Reseta o controlador de tempo
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                    opcaoSelecionada++;
                    tecla.play();
                    tempoUltimaTecla = 0;
                }
                if (opcaoSelecionada < 0) {
                    opcaoSelecionada = 2;
                }
                if (opcaoSelecionada > 2) {
                    opcaoSelecionada = 0;
                }
            }
        }
        if (estadoTela == 1) { //Escolha de capitulos
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                opcaoSelecionada--;
                tecla.play();
                tempoUltimaTecla = 0; //Reseta o controlador de tempo
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                opcaoSelecionada++;
                tecla.play();
                tempoUltimaTecla = 0;
            }
            if (opcaoSelecionada < 3) {
                opcaoSelecionada = 9;
            }
            if (opcaoSelecionada > 9) {
                opcaoSelecionada = 3;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            enter.play();
            executarOpcao(opcaoSelecionada);
        }
    }

    private void executarOpcao(int opcao) {
        if (estadoTela == 0) {
            switch (opcao) {
                case 0: //Novo Jogo
                    musicMenu.stop(); // PARA A MÚSICA
                    game.setScreen(new DialogueScreen(game));
                    break;
                case 1: //Continuar
                    musicMenu.stop(); // PARA A MÚSICA
                    int capituloSalvo = GerenciadorSave.carregarCapitulo();
                    break;
                case 2: //Escolher capitulo
                    estadoTela = 1;
                    opcaoSelecionada = 3;
                    break;
            }
        }
        if (estadoTela == 1) {
            // Verifica se é a opção VOLTAR (índice 9)
            if (opcao == 9) {
                estadoTela = 0; // Volta para o menu principal
                opcaoSelecionada = 2; // Seleciona "ESCOLHER CAPITULO"
                return;
            }

            // É um capítulo (índices 3 a 8)
            int numeroCapitulo = opcao - 2; // Converte índice para número (3 -> cap 1, 4 -> cap 2, etc)

            // Verifica se o capítulo está desbloqueado
            if (numeroCapitulo <= maxCapitulo) {
                System.out.println("Iniciando Capítulo " + numeroCapitulo);

                switch (opcao) {
                    case 3: //TCR
                        musicMenu.stop(); // PARA A MÚSICA
                        musicMenu.dispose();
                        game.setScreen(new TCRScreen());
                        break;
                    case 4: //Madoka
                        musicMenu.stop(); // PARA A MÚSICA
                        musicMenu.dispose();
                        game.setScreen(new MadokaScreen());
                        break;
                    case 5: //Arabianos
                        musicMenu.stop(); // PARA A MÚSICA
                        musicMenu.dispose();
                        game.setScreen(new ArabianosScreen());
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        estadoTela = 0;
                        break;
                }
            }
        }
    }

    private void desenharOpcoes() {
        if (estadoTela == 0) {
            // Menu Principal - desenha NOVO JOGO, CONTINUAR e ESCOLHER CAPITULO
            float y = 300; // Posição Y inicial (mais alto)

            for (int i = 0; i <= 2; i++) {
                // Define a cor da opção
                if (i == opcaoSelecionada) {
                    // Opção selecionada fica amarela e maior (efeito de destaque)
                    fonte.setColor(Color.YELLOW);
                } else {
                    // Opções não selecionadas ficam brancas
                    fonte.setColor(Color.WHITE);
                }

                // Desenha a opção na posição calculada
                // O y diminui a cada opção (300, 250, 200)
                fonte.draw(batch, opcoes[i], 800, y, 1480, -1, true);
                y -= 50; // Espaço de 50 pixels entre as opções
            }
        } else if (estadoTela == 1) {
            // Tela de Escolher Capítulo - desenha todos os capítulos + VOLTAR
            float y = 700; // Começa mais alto para caber todos os capítulos

            for (int i = 3; i < opcoes.length; i++) {
                // Define a cor baseada na seleção e disponibilidade
                if (i == opcaoSelecionada) {
                    fonte.setColor(Color.YELLOW); // Opção selecionada
                } else {
                    // Verifica se o capítulo está disponível (já foi desbloqueado)
                    int numeroCapitulo = i - 2; // Converte índice para número do capítulo
                    if (i < 9 && numeroCapitulo > maxCapitulo) { // É um capítulo (índices 3-8)
                        fonte.setColor(Color.GRAY); // Capítulo bloqueado fica cinza
                    } else {
                        fonte.setColor(Color.WHITE); // Capítulo disponível fica branco
                    }
                }

                // Desenha a opção
                fonte.draw(batch, opcoes[i], 800, y, 1480, -1, true);
                y -= 50; // Espaço de 50 pixels entre as opções
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
        fonte.dispose();
        background.dispose();
        musicMenu.dispose();
    }
}
