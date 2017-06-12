package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pontocristao.modelo.Cliente;
import pontocristao.modelo.Produto;
import pontocristao.modelo.Venda;
import pontocristao.modelo.TipoPagamento;

/**
 *
 * @author Marcondes
 */
public class ControleVenda extends ControleBase {

    public Venda getVenda() {
        return (Venda) this.getModelo();
    }

    public void setVenda(Venda venda) {
        this.setModelo(venda);
    }

    public ControleVenda() {
        setModelo(new Venda());
    }

    public List<Venda> RetornarVendas() {
        String sql = "SELECT * FROM Venda WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Venda.class);
        return (List<Venda>) q.list();
    }

    public List<Venda> RetornarVendas(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Venda JOIN Cliente ON Cliente.id = Venda.cliente_id ";

        if (camposPesquisa != null && textoPesquisa != null && camposPesquisa.length > 0 && textoPesquisa.length() > 0) {
            sql += " WHERE (";

            for (int i = 0; i < camposPesquisa.length; i++) {
                sql += camposPesquisa[i] + " LIKE '%" + textoPesquisa + "%'";
                if (i + 1 < camposPesquisa.length) {
                    sql += " OR ";
                } else {
                    sql += ") AND Locacao.excluido = false";
                }
            }
        } else {
            sql += " WHERE Locacao.excluido = false";
        }

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Venda.class);
        return (List<Venda>) q.list();
    }

    public List<Cliente> RetornarClientes() {
        ControleCliente controleCliente = new ControleCliente(false);
        controleCliente.setSessao(getSessao());
        return controleCliente.RetornarClientes();
    }

    public List<Produto> RetornarProdutos() {
        ControleProduto controleProduto = new ControleProduto();
        controleProduto.setSessao(getSessao());
        return controleProduto.RetornarProdutos();
    }

    public List<TipoPagamento> RetornarTiposPagamento() {
        ControleTipoPagamento controleTipoPagamento = new ControleTipoPagamento();
        controleTipoPagamento.setSessao(getSessao());
        controleTipoPagamento.VerificarECadastrarTiposPagamento();
        return controleTipoPagamento.RetornarTiposPagamento();
    }

    public Exception RecuperarVenda(long id) {
        String sql = "SELECT * FROM Venda WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Venda.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setVenda((Venda) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar a venda com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Venda WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Venda.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                Venda venda = (Venda) resultados.get(0);
                venda.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.saveOrUpdate(venda);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar a venda com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        if (getVenda().getId() <= 0) {
            getVenda().setData(new Date());
        }
        return Salvar(getModelo());
    }

}
