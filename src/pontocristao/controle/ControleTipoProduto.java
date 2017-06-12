package pontocristao.controle;

import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleTipoProduto extends ControleBase {

    public TipoProduto getTipoProduto() {
        return (TipoProduto) this.getModelo();
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.setModelo(tipoProduto);
    }

    public ControleTipoProduto() {
        setModelo(new TipoProduto());
    }

    public List<TipoProduto> RetornarTiposProduto() {
        String sql = "SELECT * FROM TipoProduto WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoProduto.class);
        return (List<TipoProduto>) q.list();
    }

    public List<TipoProduto> RetornarTiposProduto(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM TipoProduto";

        if (camposPesquisa != null && textoPesquisa != null && camposPesquisa.length > 0 && textoPesquisa.length() > 0) {
            sql += " WHERE (";

            for (int i = 0; i < camposPesquisa.length; i++) {
                sql += camposPesquisa[i] + " LIKE '%" + textoPesquisa + "%'";
                if (i + 1 < camposPesquisa.length) {
                    sql += " OR ";
                } else {
                    sql += ") AND excluido = false";
                }
            }
        } else {
            sql += " WHERE excluido = false";
        }

        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoProduto.class);
        return (List<TipoProduto>) q.list();
    }

    public Exception RecuperarTipoProduto(long id) {
        String sql = "SELECT * FROM TipoProduto WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoProduto.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setTipoProduto((TipoProduto) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o tipo de produto com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM TipoProduto WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(TipoProduto.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                TipoProduto tipoProduto = (TipoProduto) resultados.get(0);
                tipoProduto.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(tipoProduto);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o tipo de produto com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        return Salvar(getModelo());
    }
}
