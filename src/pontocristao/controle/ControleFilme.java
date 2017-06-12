package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleFilme extends ControleBase {

    public Filme getFilme() {
        return (Filme) this.getModelo();
    }

    public void setFilme(Filme filme) {
        this.setModelo(filme);
    }

    public ControleFilme() {
        setModelo(new Filme());
    }

    public List<Filme> RetornarFilmes() {
        String sql = "SELECT * FROM Filme JOIN ProdutoBase ON ProdutoBase.Id = Filme.Id WHERE ProdutoBase.excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Filme.class);
        return (List<Filme>) q.list();
    }

    public List<Filme> RetornarFilmes(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Filme JOIN ProdutoBase ON ProdutoBase.id = Filme.id JOIN Fornecedor ON Fornecedor.id = ProdutoBase.fornecedor_id JOIN TipoFilme ON TipoFilme.id = Filme.tipoFilme_id ";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Filme.class);
        return (List<Filme>) q.list();
    }

    public List<Fornecedor> RetornarFornecedores() {
        String sql = "SELECT * FROM Fornecedor WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Fornecedor.class);
        return (List<Fornecedor>) q.list();
    }

    public List<TipoFilme> RetornarTiposFilme() {
        String sql = "SELECT * FROM TipoFilme WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoFilme.class);
        return (List<TipoFilme>) q.list();
    }

    public Exception RecuperarFilme(long id) {
        String sql = "SELECT * FROM Filme JOIN ProdutoBase ON ProdutoBase.Id = Filme.Id WHERE Filme.Id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Filme.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setFilme((Filme) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o filme com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Filme WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Filme.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                ProdutoBase produto = (Filme) resultados.get(0);
                produto.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(produto);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o filme com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        if (getFilme().getId() <= 0) {
            getFilme().setDataCadastro(new Date());
        }
        return Salvar(getModelo());
    }
}
