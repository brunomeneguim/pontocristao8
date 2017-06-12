package pontocristao.controle;

import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleTipoContaPagar extends ControleBase {

    public TipoContaPagar getTipoContaPagar() {
        return (TipoContaPagar) this.getModelo();
    }

    public void setTipoContaPagar(TipoContaPagar tipoContaPagar) {
        this.setModelo(tipoContaPagar);
    }

    public ControleTipoContaPagar() {
        setTipoContaPagar(new TipoContaPagar());
    }

    public List<TipoContaPagar> RetornarTiposContaPagar() {
        String sql = "SELECT * FROM TipoContaPagar WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoContaPagar.class);
        return (List<TipoContaPagar>) q.list();
    }

    public List<TipoContaPagar> RetornarTiposContaPagar(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM TipoContaPagar";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoContaPagar.class);
        return (List<TipoContaPagar>) q.list();
    }

    public Exception RecuperarTipoContaPagar(long id) {
        String sql = "SELECT * FROM TipoContaPagar WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoContaPagar.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setTipoContaPagar((TipoContaPagar) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o tipo de conta a pagar com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM TipoContaPagar WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(TipoContaPagar.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                TipoContaPagar tipoContaPagar = (TipoContaPagar) resultados.get(0);
                tipoContaPagar.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(tipoContaPagar);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o tipo de conta a pagar com o id " + id);
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
