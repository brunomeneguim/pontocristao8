package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleContaPagar extends ControleBase {

    public ContaPagar getContaPagar() {
        return (ContaPagar) this.getModelo();
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.setModelo(contaPagar);
    }

    public ControleContaPagar() {
        setContaPagar(new ContaPagar());
    }

    public List<ContaPagar> RetornarContasPagar() {
        String sql = "SELECT * FROM ContaPagar WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(ContaPagar.class);
        return (List<ContaPagar>) q.list();
    }

    public List<ContaPagar> RetornarContasPagar(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM ContaPagar JOIN TipoContaPagar ON TipoContaPagar.id = ContaPagar.tipoContaPagar_id ";

        if (camposPesquisa != null && textoPesquisa != null && camposPesquisa.length > 0 && textoPesquisa.length() > 0) {
            sql += " WHERE (";

            for (int i = 0; i < camposPesquisa.length; i++) {
                sql += camposPesquisa[i] + " LIKE '%" + textoPesquisa + "%'";
                if (i + 1 < camposPesquisa.length) {
                    sql += " OR ";
                } else {
                    sql += ") AND ProdutoBase.excluido = false";
                }
            }
        } else {
            sql += " WHERE ProdutoBase.excluido = false";
        }

        Query q = this.getSessao().createSQLQuery(sql).addEntity(ContaPagar.class);
        return (List<ContaPagar>) q.list();
    }

    public List<TipoContaPagar> RetornarTiposContaPagar() {
        String sql = "SELECT * FROM TipoContaPagar WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoContaPagar.class);
        return (List<TipoContaPagar>) q.list();
    }

    public Exception RecuperarContaPagar(long id) {
        String sql = "SELECT * FROM ContaPagar WHERE Id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(ContaPagar.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setContaPagar((ContaPagar) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar a conta a pagar com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM ContaPagar WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(ContaPagar.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                ContaPagar contaPagar = (ContaPagar) resultados.get(0);
                contaPagar.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(contaPagar);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar a conta a pagar com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception PagarConta(long id) {
        String sql = "SELECT * FROM ContaPagar WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(ContaPagar.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                ContaPagar contaPagar = (ContaPagar) resultados.get(0);
                contaPagar.setPago(true);

                MovimentacaoCaixaContaPagar movimentacao = new MovimentacaoCaixaContaPagar();
                movimentacao.setContaPagar(contaPagar);
                movimentacao.setData(new Date());
                movimentacao.setValor(contaPagar.getValor());
                movimentacao.setFuncionario(ControleSistema.getFuncionarioLogado(s));

                ControleCaixa controleCaixa = new ControleCaixa(s);
                controleCaixa.AdicionarMovimentacao(movimentacao);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(contaPagar);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar a conta a pagar com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        if (getContaPagar().getId() <= 0) {
            getContaPagar().setData(new Date());
            getContaPagar().setPago(false);
        }
        return Salvar(getModelo());
    }
}
