package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GerenciadorSave {
    //Nome do arquivo do Save que sera gerado
    private static final Preferences preds = Gdx.app.getPreferences("EnioAdventureSave");

    //Função para fazer o save
    public static void salvarProgessor(int capitulo, int estadoHistoria){
        preds.putInteger("capitulo", capitulo);
        preds.putInteger("estado", estadoHistoria);
        preds.flush();
    }

    public static int carregarCapitulo(){
        return preds.getInteger("capitulo", 0);
    }

    public static int carregarEstado(){
        return preds.getInteger("estado", 0);
    }

    public static void saveDesbloqueado(int capituloMaximo){
        int atual = carregarCapitloMaximo();
        //Aqui vai so salvar se o capítulo que estiver for maior que o capítulo Maximo
        if (capituloMaximo > atual){
            preds.putInteger("capituloMaximo", capituloMaximo);
            preds.flush();
        }
    }

    public static int carregarCapitloMaximo(){
        return preds.getInteger("capituloMaximo", 1); //Para começar sempre pelo 1
    }
}
