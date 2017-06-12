package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleProduto extends ControleBase {

    public Produto getProduto() {
        return (Produto) this.getModelo();
    }

    public void setProduto(Produto produto) {
        this.setModelo(produto);
    }

    public ControleProduto() {
        setModelo(new Produto());
    }

    public List<Produto> RetornarProdutos() {
        String sql = "SELECT * FROM Produto JOIN ProdutoBase ON ProdutoBase.Id = Produto.Id WHERE ProdutoBase.excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Produto.class);
        return (List<Produto>) q.list();
    }

    public List<Produto> RetornarProdutos(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Produto JOIN ProdutoBase ON ProdutoBase.id = Produto.id JOIN Fornecedor ON Fornecedor.id = ProdutoBase.fornecedor_id JOIN TipoProduto ON TipoProduto.id = Produto.tipoProduto_id ";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Produto.class);
        return (List<Produto>) q.list();
    }

    public List<Fornecedor> RetornarFornecedores() {
        String sql = "SELECT * FROM Fornecedor WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Fornecedor.class);
        return (List<Fornecedor>) q.list();
    }

    public List<TipoProduto> RetornarTiposProduto() {
        String sql = "SELECT * FROM TipoProduto WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(TipoProduto.class);
        return (List<TipoProduto>) q.list();
    }

    public Exception RecuperarProduto(long id) {
        String sql = "SELECT * FROM Produto JOIN ProdutoBase ON ProdutoBase.Id = Produto.Id WHERE Produto.Id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Produto.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setProduto((Produto) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o produto com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Produto WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Produto.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                ProdutoBase produto = (Produto) resultados.get(0);
                produto.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(produto);
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
        if (getProduto().getId() <= 0) {
            getProduto().setDataCadastro(new Date());
        }
        return Salvar(getModelo());
    }
}
