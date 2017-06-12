package pontocristao.controle;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;
import pontocristao.modelo.TipoPagamento;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class ControleTipoPagamento extends ControleBase {

    public void VerificarECadastrarTiposPagamento() {
        List<TipoPagamento> tiposPagamento = RetornarTiposPagamento();

        //Se ainda não existe nenhum tipo de pagamento, cria os tipos principais (dinheiro, cartão de crédito e cartão de débito)
        if (tiposPagamento.isEmpty()) {
            TipoPagamento dinheiro = new TipoPagamento();
            dinheiro.setDescricao("Dinheiro");

            TipoPagamento credito = new TipoPagamento();
            credito.setDescricao("Cartão de crédito");

            TipoPagamento debito = new TipoPagamento();
            debito.setDescricao("Cartão de débito");

            try {
                Transaction transacao = getSessao().getTransaction();
                transacao.begin();

                getSessao().saveOrUpdate(dinheiro);
                getSessao().saveOrUpdate(credito);
                getSessao().saveOrUpdate(debito);

                transacao.commit();

            } catch (Exception e) {
                Utilidades.MostrarMensagemErro(e);
            }
        }

    }

    public List<TipoPagamento> RetornarTiposPagamento() {
        String sql = "SELECT * FROM TipoPagamento WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoPagamento.class);
        return (List<TipoPagamento>) q.list();
    }

}
