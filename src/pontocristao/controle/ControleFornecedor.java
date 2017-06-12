package pontocristao.controle;

import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleFornecedor extends ControleBase {

    public Fornecedor getFornecedor() {
        return (Fornecedor) this.getModelo();
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.setModelo(fornecedor);
    }

    public ControleFornecedor() {
        setModelo(new Fornecedor());
    }

    public List<Fornecedor> RetornarFornecedores() {
        String sql = "SELECT * FROM Fornecedor WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Fornecedor.class);
        return (List<Fornecedor>) q.list();
    }

    public List<Fornecedor> RetornarFornecedores(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Fornecedor";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Fornecedor.class);
        return (List<Fornecedor>) q.list();
    }

    public Exception RecuperarFornecedor(long id) {
        String sql = "SELECT * FROM Fornecedor WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Fornecedor.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setFornecedor((Fornecedor) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o fornecedor com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Fornecedor WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Fornecedor.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                Fornecedor fornecedor = (Fornecedor) resultados.get(0);
                fornecedor.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(fornecedor);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o fornecedor com o id " + id);
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
