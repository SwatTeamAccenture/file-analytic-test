package analisedados.service.criararquivo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import analisedados.model.Relatorio;

public class CriarTxt {
    private static final String CAMINHO = "C:\\data\\out\\relatorio.txt";

    public CriarTxt() {
    }
    //cria ou grava txt em arquivo
    public void gravarArquivoTxt(Relatorio LeituraArquivo) throws IOException {
        File pastaArquivo = new File(CAMINHO);

        if (!pastaArquivo.exists()) {
            pastaArquivo.createNewFile();
            FileWriter arquivo = new FileWriter(pastaArquivo);
            arquivo.write(formataSaidaTxt(LeituraArquivo));
            arquivo.close();
        } else {
            int contador = 1;
            File arquivoNovo = new File("C:\\data\\out\\relatorio" + contador + ".txt");
            while (arquivoNovo.exists()) {
                arquivoNovo = new File("C:\\data\\out\\relatorio" + ++contador + ".txt");
            }
            FileWriter arquivo = new FileWriter(arquivoNovo);
            arquivo.write(formataSaidaTxt(LeituraArquivo));
            arquivo.close();
        }
    }
    //formato saida
    private String formataSaidaTxt(Relatorio leituraArquivo) {
        return "Quantidade de Cliente: " + leituraArquivo.getQuantidadeCliente() + "\n" + "Quantidade de Vendedores: "
                + leituraArquivo.getQuantidadedeVendeores() + "\n" + "ID de venda mais cara: " + leituraArquivo.getIdVenda() + "\n"
                + "O pior vendedor: " + leituraArquivo.getPiorVendedor();
    }

}
