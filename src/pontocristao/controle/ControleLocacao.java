package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleLocacao extends ControleBase {

    private TabelaPrecoLocacao tabelaPreco;

    public Locacao getLocacao() {
        return (Locacao) this.getModelo();
    }

    public void setLocacao(Locacao locacao) {
        this.setModelo(locacao);
    }

    public ControleLocacao() {
        setModelo(new Locacao());
    }

    public List<Locacao> RetornarLocacoes() {
        String sql = "SELECT * FROM Locacao WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Locacao.class);
        return (List<Locacao>) q.list();
    }

    public List<Locacao> RetornarLocacoes(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Locacao JOIN Cliente ON Cliente.id = Locacao.cliente_id ";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Locacao.class);
        return (List<Locacao>) q.list();
    }

    public List<Cliente> RetornarClientes() {
        ControleCliente controleCliente = new ControleCliente(false);
        controleCliente.setSessao(getSessao());
        return controleCliente.RetornarClientes();
    }

    public List<Filme> RetornarFilmes() {
        ControleFilme controleFilme = new ControleFilme();
        controleFilme.setSessao(getSessao());
        return controleFilme.RetornarFilmes();
    }

    public List<TipoPagamento> RetornarTiposPagamento() {
        ControleTipoPagamento controleTipoPagamento = new ControleTipoPagamento();
        controleTipoPagamento.setSessao(getSessao());
        controleTipoPagamento.VerificarECadastrarTiposPagamento();
        return controleTipoPagamento.RetornarTiposPagamento();
    }

    public TabelaPrecoLocacao getTabelaPrecoLocacao() {
        if (tabelaPreco == null) {
            ControleTabelaPrecoLocacao controle = new ControleTabelaPrecoLocacao();
            controle.RecuperarTabelaPrecoLocacao();
            tabelaPreco = controle.getTabelaPrecoLocacao();
        }

        return tabelaPreco;
    }

    public Double getValorLocacao(Filme filme) {
        if (filme.getLancamento()) {
            return getTabelaPrecoLocacao().getValorLancamento();
        } else {
            return getTabelaPrecoLocacao().getValorNormal();
        }
    }
    
    public Exception DevolverLocacao(long id) {
        String sql = "SELECT * FROM Locacao WHERE Locacao.Id = " + id;
        Exception erro = null;

        try {
            Query q = getSessao().createSQLQuery(sql).addEntity(Locacao.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                Locacao locacao = (Locacao) resultados.get(0);
                
                locacao.setDevolvido(true);
                
                Transaction transacao = getSessao().getTransaction();
                transacao.begin();
                
                getSessao().saveOrUpdate(locacao);
                
                transacao.commit();
                
            } else {
                throw new Exception("Não foi possível encontrar a locação com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception RecuperarLocacao(long id) {
        String sql = "SELECT * FROM Locacao WHERE Locacao.Id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Locacao.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setLocacao((Locacao) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar a locação com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Locacao WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Locacao.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                Locacao locacao = (Locacao) resultados.get(0);
                locacao.setExcluido(true);

                Cliente cliente = locacao.getCliente();
                int totalLocacoes = cliente.getTotalLocacoes() - locacao.getItensLocacao().size();

                if (totalLocacoes < 0) {
                    totalLocacoes = 0;
                }

                cliente.setTotalLocacoes(totalLocacoes);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.saveOrUpdate(cliente);
                s.saveOrUpdate(locacao);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar a locação com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        if (getLocacao().getId() <= 0) {
            getLocacao().setData(new Date());
            getLocacao().setDevolvido(false);
        }
        return Salvar(getModelo());
    }
}
