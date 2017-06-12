package pontocristao.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.hibernate.*;
import pontocristao.modelo.*;

/**
 *
 * @author Marcondes
 */
public class ControleCliente extends ControleBase {

    public Cliente getCliente() {
        return (Cliente) this.getModelo();
    }

    public void setCliente(Cliente cliente) {
        this.setModelo(cliente);
    }

    public ControleCliente(boolean pessoaFisica) {
        if (pessoaFisica) {
            setModelo(new ClientePessoaFisica());
        } else {
            setModelo(new ClientePessoaJuridica());
        }
        getCliente().setEndereco(new Endereco());
    }

    public List<Cliente> RetornarClientes() {
        String sqlPessoaFisica = "SELECT * FROM ClientePessoaFisica JOIN Cliente ON ClientePessoaFisica.id = Cliente.id WHERE Cliente.excluido = false";
        String sqlPessoaJuridica = "SELECT * FROM ClientePessoaJuridica JOIN Cliente ON ClientePessoaJuridica.id = Cliente.id WHERE Cliente.excluido = false";

        List<Cliente> clientes = this.RetornarClientes(sqlPessoaFisica, sqlPessoaJuridica, this.getSessao());

        return clientes;
    }

    public List<Cliente> RetornarClientes(String[] camposPesquisa, String textoPesquisa) {
        String sqlPessoaFisica = "SELECT * FROM ClientePessoaFisica JOIN Cliente ON ClientePessoaFisica.id = Cliente.id";
        String sqlPessoaJuridica = "SELECT * FROM ClientePessoaJuridica JOIN Cliente ON ClientePessoaJuridica.id = Cliente.id";

        sqlPessoaFisica += this.RetornarClausulaWhere(camposPesquisa, textoPesquisa, "Cliente");
        sqlPessoaJuridica += this.RetornarClausulaWhere(camposPesquisa, textoPesquisa, "Cliente");

        List<Cliente> clientes = this.RetornarClientes(sqlPessoaFisica, sqlPessoaJuridica, this.getSessao());

        return clientes;
    }

    public Exception RecuperarCliente(long id) {
        Exception erro = null;

        try {
            Cliente cliente = this.RetornarCliente(id, this.getSessao());

            if (cliente != null) {
                this.setCliente(cliente);
            } else {
                throw new Exception("Não foi possível encontrar o cliente com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Excluir(long id) {
        Exception erro = null;

        try {
            Session s = getSessao();
            Cliente cliente = this.RetornarCliente(id, s);

            if (cliente != null) {
                cliente.setExcluido(true);

                Transaction transacao = s.getTransaction();

                transacao.begin();
                s.save(cliente);
                transacao.commit();

            } else {
                throw new Exception("Não foi possível encontrar o cliente com o id " + id);
            }
        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public Exception Salvar() {

        if (getCliente().getId() == 0) {
            getCliente().setDataCadastro(new Date());
        }

        Exception erro = null;
        Session s = getSessao();
        Transaction transacao = s.getTransaction();

        transacao.begin();

        try {

            s.saveOrUpdate(getCliente().getEndereco());
            s.saveOrUpdate(getCliente());

            for (Dependente dependente : getCliente().getDependentes()) {
                s.saveOrUpdate(dependente);
            }

            transacao.commit();

        } catch (Exception e) {
            transacao.rollback();
            erro = e;
        } finally {
            return erro;
        }
    }

    public Cliente RetornarCliente(long id, Session s) {
        String sqlPessoaFisica = "SELECT * FROM ClientePessoaFisica JOIN Cliente ON ClientePessoaFisica.id = Cliente.id WHERE ClientePessoaFisica.id = " + id;
        String sqlPessoaJuridica = "SELECT * FROM ClientePessoaJuridica JOIN Cliente ON ClientePessoaJuridica.id = Cliente.id WHERE ClientePessoaJuridica.id = " + id;

        List<Cliente> clientes = this.RetornarClientes(sqlPessoaFisica, sqlPessoaJuridica, s);

        if (clientes.size() == 1) {
            return clientes.get(0);
        } else {
            return null;
        }

    }

    private List<Cliente> RetornarClientes(String sqlPessoaFisica, String sqlPessoaJuridica, Session s) {
        Query qPessoaFisica = s.createSQLQuery(sqlPessoaFisica).addEntity(ClientePessoaFisica.class);
        Query qPessoaJuridica = s.createSQLQuery(sqlPessoaJuridica).addEntity(ClientePessoaJuridica.class);

        List<ClientePessoaFisica> clientesPessoaFisica = qPessoaFisica.list();
        List<ClientePessoaJuridica> clientesPessoaJuridica = qPessoaJuridica.list();

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        clientes.addAll(clientesPessoaFisica);
        clientes.addAll(clientesPessoaJuridica);

        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c2, Cliente c1) {
                if (c2.getId() > c1.getId()) {
                    return 1;
                } else {
                    return -1;
                }

            }
        });

        return clientes;
    }

    private String RetornarClausulaWhere(String[] camposPesquisa, String textoPesquisa, String nomeTabela) {
        String sql = "";

        if (camposPesquisa != null && textoPesquisa != null && camposPesquisa.length > 0 && textoPesquisa.length() > 0) {
            sql += " WHERE (";

            for (int i = 0; i < camposPesquisa.length; i++) {
                sql += nomeTabela + "." + camposPesquisa[i] + " LIKE '%" + textoPesquisa + "%'";
                if (i + 1 < camposPesquisa.length) {
                    sql += " OR ";
                } else {
                    sql += ") AND Cliente.excluido = false";
                }
            }
        } else {
            sql += " WHERE Cliente.excluido = false";
        }

        return sql;
    }
}
