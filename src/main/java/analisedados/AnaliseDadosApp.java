package analisedados;

import java.util.Date;
import java.util.Timer;

import analisedados.controller.AnaliseDadosController;

public class AnaliseDadosApp {
    public static void main(String[] args){
        var timer = new Timer();
        var analise = new AnaliseDadosController();

        timer.schedule(analise, new Date(),600);

    }

}
