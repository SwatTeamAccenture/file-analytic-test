package analisedados.controller;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import analisedados.service.criararquivo.CriarTxt;
import analisedados.service.lerarquivo.LeituraTxt;
import analisedados.model.Relatorio;

public class AnaliseDadosController  extends TimerTask{
    private LeituraTxt leituraTxt = new LeituraTxt();
    private CriarTxt criarTxt = new CriarTxt();

    public void iniciaProjeto() {
        List<String> pegaArquivoTxt = leituraTxt.pegaArquivoTxt();
        if(!pegaArquivoTxt.isEmpty()) {
            try {
                for (String arq : pegaArquivoTxt) {
                    Relatorio leituraArquivo = leituraTxt.leituraArquivo(arq);
                    criarTxt.gravarArquivoTxt(leituraArquivo);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void run() {
        iniciaProjeto();
    }

}
