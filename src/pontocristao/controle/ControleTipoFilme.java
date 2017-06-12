package pontocristao.controle;

import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleTipoFilme extends ControleBase {

    public TipoFilme getTipoFilme() {
        return (TipoFilme) this.getModelo();
    }

    public void setTipoFilme(TipoFilme tipoFilme) {
        this.setModelo(tipoFilme);
    }

    public ControleTipoFilme() {
        setModelo(new TipoFilme());
    }

    public List<TipoFilme> RetornarTiposFilme() {
        String sql = "SELECT * FROM TipoFilme WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoFilme.class);
        return (List<TipoFilme>) q.list();
    }

    public List<TipoFilme> RetornarTiposFilme(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM TipoFilme";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoFilme.class);
        return (List<TipoFilme>) q.list();
    }

    public Exception RecuperarTipoFilme(long id) {
        String sql = "SELECT * FROM TipoFilme WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoFilme.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setTipoFilme((TipoFilme) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o tipo de filme com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM TipoFilme WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(TipoFilme.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                TipoFilme tipoFilme = (TipoFilme) resultados.get(0);
                tipoFilme.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(tipoFilme);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o tipo fe filme com o id " + id);
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
