package pontocristao.controle;

import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class ControleTabelaPrecoLocacao extends ControleBase {

    public TabelaPrecoLocacao getTabelaPrecoLocacao() {
        return (TabelaPrecoLocacao) getModelo();
    }

    public ControleTabelaPrecoLocacao() {
        RecuperarTabelaPrecoLocacao();
    }

    public TabelaPrecoLocacao RecuperarTabelaPrecoLocacao() {
        String sql = "SELECT * FROM TabelaPrecoLocacao WHERE excluido = false";
        Query q = getSessao().createSQLQuery(sql).addEntity(TabelaPrecoLocacao.class);

        List<TabelaPrecoLocacao> tabelas = q.list();
        TabelaPrecoLocacao tabela = null;

        if (tabelas.isEmpty()) {
            tabela = new TabelaPrecoLocacao();
            tabela.setValorLancamento(5.9);
            tabela.setValorNormal(4.0);
            tabela.setValorMultaDiaria(2.0);

            Exception erro = Salvar(tabela);

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
                setModelo(null);
            }

        } else {
            tabela = tabelas.get(tabelas.size() - 1);
        }

        setModelo(tabela);
        return tabela;
    }

    public Exception Salvar() {
        return Salvar(getModelo());
    }
}
