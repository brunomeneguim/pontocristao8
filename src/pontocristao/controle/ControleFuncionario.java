package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleFuncionario extends ControleBase {

    public Funcionario getFuncionario() {
        return (Funcionario) this.getModelo();
    }

    public void setFuncionario(Funcionario funcionario) {
        this.setModelo(funcionario);
    }

    public ControleFuncionario() {
        setModelo(new Funcionario());
        getFuncionario().setEndereco(new Endereco());
    }

    public List<Funcionario> RetornarFuncionarios() {
        String sql = "SELECT * FROM Funcionario WHERE excluido = false";
        Query q = this.getSessao().createSQLQuery(sql).addEntity(Funcionario.class);
        return (List<Funcionario>) q.list();
    }

    public List<Funcionario> RetornarFuncionarios(String[] camposPesquisa, String textoPesquisa) {
        String sql = "SELECT * FROM Funcionario";

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

        Query q = this.getSessao().createSQLQuery(sql).addEntity(Funcionario.class);
        return (List<Funcionario>) q.list();
    }

    public Exception RecuperarFuncionario(long id) {
        String sql = "SELECT * FROM Funcionario WHERE id = " + id;
        Exception erro = null;

        try {
            Query q = this.getSessao().createSQLQuery(sql).addEntity(Funcionario.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                this.setFuncionario((Funcionario) resultados.get(0));
            } else {
                throw new Exception("Não foi possível encontrar o funcionário com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        String sql = "SELECT * FROM Funcionario WHERE id = " + id;
        Exception erro = null;

        try {
            Session s = getSessao();
            Query q = s.createSQLQuery(sql).addEntity(Funcionario.class);
            List resultados = q.list();

            if (resultados.size() == 1) {
                Funcionario funcionario = (Funcionario) resultados.get(0);
                funcionario.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(funcionario);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o funcionário com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {
        if (getFuncionario().getId() > 0) {
            return Salvar(getModelo());
        } else {

            getFuncionario().setDataCadastro(new Date());

            Exception erro = null;
            Session s = getSessao();
            Transaction transacao = s.getTransaction();

            transacao.begin();

            try {
                s.save(getFuncionario().getEndereco());
                s.save(getFuncionario());

                transacao.commit();

            } catch (Exception e) {
                transacao.rollback();
                erro = e;
            } finally {
                return erro;
            }
        }

    }
}
