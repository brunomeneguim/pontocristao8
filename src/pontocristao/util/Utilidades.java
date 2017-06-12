package pontocristao.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.*;

/**
 *
 * @author Marcondes
 */
public class Utilidades {

    public static void setMascara(String mascara, JFormattedTextField campo) {
        try {
            MaskFormatter mask = new MaskFormatter(mascara);
            mask.install(campo);
        } catch (java.text.ParseException ex) {
            MostrarMensagemErro(ex);
        }
    }

    public static String getValorSemMascara(JFormattedTextField campo) {
        String valor = campo.getText();
        String valorSemMascara = valor
                .replace(".", "")
                .replace("-", "")
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "")
                .replace("/", "")
                .replace("R$", "");
        return valorSemMascara;
    }

    public static Boolean MostrarMensagemPergunta(String titulo, String texto, Boolean padraoSim) {
        Object[] botoes = {"Sim", "Não"};
        int indicePadrao = 0;
        if (!padraoSim) {
            indicePadrao = 1;
        }

        int resposta = JOptionPane.showOptionDialog(null, texto, titulo, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, botoes, botoes[indicePadrao]);

        return resposta == 0;
    }

    public static void MostrarMensagemErro(Exception erro) {
        Object[] botoes = {"Ok"};
        JOptionPane.showOptionDialog(null, erro.getMessage(), "Ocorreu um erro inesperado!", JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE, null, botoes, botoes[0]);
    }

    public static void MostrarMensagem(String titulo, String texto) {
        Object[] botoes = {"Ok"};
        JOptionPane.showOptionDialog(null, texto, titulo, JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE, null, botoes, botoes[0]);
    }
    
    public static String RetornarDataFormatada(Date data) {
        return new SimpleDateFormat("dd-MM-yyyy").format(data); 
    }
    
    public static String RetornarValorMonetarioFormatado(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }
}
