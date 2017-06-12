package pontocristao.controle;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pontocristao.modelo.*;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class ControleLogin extends ControleBase {

    public Funcionario FazerLogin(String login, String senha) {
        Session s = getSessao();
        String sql = "SELECT * FROM Funcionario WHERE excluido = false AND login = '" + login + "' AND senha = '" + senha + "'";
        Query q = s.createSQLQuery(sql).addEntity(Funcionario.class);

        List<Funcionario> funcionarios = q.list();
        Funcionario funcionarioLogado = null;

        if (login.equals("admin") && senha.equals("admin") && funcionarios.isEmpty()) {
            if (funcionarios.size() == 0) {
                ControleFuncionario controleFuncionario = new ControleFuncionario();

                funcionarioLogado = controleFuncionario.getFuncionario();

                funcionarioLogado.setCarteiraTrabalho("000000000");
                funcionarioLogado.setCelular("0000000000");
                funcionarioLogado.setCpf("00000000000");
                funcionarioLogado.setDataAdmissao(new Date());
                funcionarioLogado.setDataCadastro(new Date());
                funcionarioLogado.setDataNascimento(new Date());
                funcionarioLogado.setEmail("admin@admin.com.br");

                Endereco end = new Endereco();

                end.setBairro("admin");
                end.setCep("00000000");
                end.setCidade("admin");
                end.setEstado("admin");
                end.setNumero("000");
                end.setRua("admin");

                funcionarioLogado.setEndereco(end);

                funcionarioLogado.setLogin("admin");
                funcionarioLogado.setNome("Administrador");
                funcionarioLogado.setRg("00000000");
                funcionarioLogado.setSenha("admin");
                funcionarioLogado.setSexo(Sexo.Masculino);
                funcionarioLogado.setTelefoneResidencial("0000000000");

                Exception erro = controleFuncionario.Salvar();

                if (erro != null) {
                    Utilidades.MostrarMensagemErro(erro);
                }
            }

        } else {
            if (funcionarios.size() > 0) {
                funcionarioLogado = funcionarios.get(0);
            } else {
                Utilidades.MostrarMensagem("Falha no login", "Nome de usuário ou senha incorretos.");
            }
        }

        setModelo(funcionarioLogado);

        ControleSistema.FuncionarioLogado = funcionarioLogado;

        return funcionarioLogado;
    }
}
