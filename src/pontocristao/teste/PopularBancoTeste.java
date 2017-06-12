package pontocristao.teste;

import java.util.*;
import org.hibernate.*;
import pontocristao.modelo.*;
import pontocristao.util.HibernateUtil;

/**
 *
 * @author Marcondes
 */
public class PopularBancoTeste {

    public static void Popular() {
        try {
            Session sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction transacao = sessao.getTransaction();

            transacao.begin();

            CodigoBarrasProprio codigoBarras = RetornarNovoCodigoBarrasProprio();

            //Salva um novo código de barras próprio
            sessao.save(codigoBarras);

            Caixa caixa = RetornarNovoCaixa();

            //Salva um novo caixa
            sessao.save(caixa);

            TipoContaPagar tipoContaPagar = RetornarNovoTipoContaPagar();

            //Salva um novo tipo de conta a pagar
            sessao.save(tipoContaPagar);

            ContaPagar contaPagar = RetornarNovaContaPagar();
            contaPagar.setTipoContaPagar(tipoContaPagar);

            //Salva uma nova conta a pagar
            sessao.save(contaPagar);

            Fornecedor fornecedor = RetornarNovoFornecedor();

            //Salva um novo fornecedor
            sessao.save(fornecedor);

            TipoProduto tipoProduto = RetornarNovoTipoProduto();

            //Salva um novo tipo de produto
            sessao.save(tipoProduto);

            Produto produto = RetornarNovoProduto();
            produto.setFornecedor(fornecedor);
            produto.setTipoProduto(tipoProduto);

            //Salva um novo produto
            sessao.save(produto);

            TipoFilme tipoFilme = RetornarTipoFilme();

            //Salva um novo tipo de filme
            sessao.save(tipoFilme);

            Filme filme = RetornarNovoFilme();
            filme.setTipoFilme(tipoFilme);

            //Salva um novo filme
            sessao.save(filme);

            TabelaPrecoLocacao tabelaPrecoLocacao = RetornarNovaTabelaPrecoLocacao();

            //Salva uma nova tabela de preço de locação
            sessao.save(tabelaPrecoLocacao);

            Endereco enderecoFuncionario = RetornarNovoEndereco();

            //Salva um novo endereço para o funcionário
            sessao.save(enderecoFuncionario);

            Funcionario funcionario = RetornarNovoFuncionario();
            funcionario.setEndereco(enderecoFuncionario);

            //Salva um novo funcionário
            sessao.save(funcionario);

            Endereco enderecoClientePessoaFisica = RetornarNovoEndereco();

            //Salva um novo endereço para o cliente pessoa física
            sessao.save(enderecoClientePessoaFisica);

            ClientePessoaFisica clientePessoaFisica = RetornarNovoClientePessoaFisica();
            clientePessoaFisica.setEndereco(enderecoClientePessoaFisica);

            //Salva um novo cliente pessoa física
            sessao.save(clientePessoaFisica);

            Dependente dependenteClientePessoaFisica = RetornarNovoDependente();
            dependenteClientePessoaFisica.setCliente(clientePessoaFisica);

            //Salvar um novo dependente para o cliente pessoa física
            sessao.save(dependenteClientePessoaFisica);

            Endereco enderecoClientePessoaJuridica = RetornarNovoEndereco();

            //Salva um novo endereço para o cliente pessoa jurídica
            sessao.save(enderecoClientePessoaJuridica);

            ClientePessoaJuridica clientePessoaJuridica = RetornarNovoClientePessoaJuridica();
            clientePessoaJuridica.setEndereco(enderecoClientePessoaJuridica);

            //Salva um novo cliente pessoa jurídica
            sessao.save(clientePessoaJuridica);

            Dependente dependenteClientePessoaJuridica = RetornarNovoDependente();
            dependenteClientePessoaFisica.setCliente(clientePessoaJuridica);

            //Salvar um novo dependente para o cliente pessoa física
            sessao.save(dependenteClientePessoaJuridica);

            Locacao locacao = RetornarNovaLocacao();
            locacao.setCliente(clientePessoaFisica);
            locacao.setFuncionario(funcionario);

            //Salva uma nova locação
            sessao.save(locacao);

            ItemLocacao itemLocacao = RetornarNovoItemLocacao();
            itemLocacao.setFilme(filme);
            itemLocacao.setLocacao(locacao);

            //Salva um novo item de locação
            sessao.save(itemLocacao);

            TipoPagamento tipoPagamento = RetornarNovoTipoPagamento();

            //Salva um novo tipo de pagamento
            sessao.save(tipoPagamento);

            PagamentoLocacao pagamentoLocacao = RetornarNovoPagamentoLocacao();
            pagamentoLocacao.setLocacao(locacao);
            pagamentoLocacao.setTipoPagamento(tipoPagamento);

            //Salva um novo pagamento de locacao
            sessao.save(pagamentoLocacao);

            MovimentacaoCaixaLocacao movimentacaoCaixaLocacao = RetornarNovaMovimentacaoCaixaLocacao();
            movimentacaoCaixaLocacao.setCaixa(caixa);
            movimentacaoCaixaLocacao.setFuncionario(funcionario);
            movimentacaoCaixaLocacao.setLocacao(locacao);

            //Salva uma nova movimentação de caixa de locação
            sessao.save(movimentacaoCaixaLocacao);

            Venda venda = RetornarNovaVenda();
            venda.setCliente(clientePessoaFisica);
            venda.setFuncionario(funcionario);

            ItemVenda itemVenda = RetornarNovoItemVenda();
            itemVenda.setProduto(produto);

            itemVenda.setVenda(venda);

            //Salva uma nova venda
            sessao.save(venda);

            //Salva um novo item de venda            
            sessao.save(itemVenda);

            PagamentoVenda pagamentoVenda = RetornarNovoPagamentoVenda();
            pagamentoVenda.setTipoPagamento(tipoPagamento);
            pagamentoVenda.setVenda(venda);

            //Salva um novo pagamento de venda
            sessao.save(pagamentoVenda);

            transacao.commit();
            sessao.close();
        } catch (Exception e) {
            Exception erro = e;
        }

    }

    private static Caixa RetornarNovoCaixa() {
        Caixa caixa = new Caixa();
        caixa.setSaldo(1523.79);
        return caixa;
    }

    private static ClientePessoaFisica RetornarNovoClientePessoaFisica() {
        ClientePessoaFisica cliente = new ClientePessoaFisica();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.YEAR, -20);

        cliente.setCelular("4299887766");
        cliente.setCpf("12345678910");
        cliente.setDataCadastro(new Date());
        cliente.setDataNascimento(calendario.getTime());
        cliente.setEmail("clientepessoafisica@teste.com.br");
        cliente.setNome("Cliente Pessoa Fisica");
        cliente.setRg("12345678");
        cliente.setSexo(Sexo.Masculino);
        cliente.setTelefone("4230350011");
        cliente.setTotalLocacoes(1);

        return cliente;
    }

    private static ClientePessoaJuridica RetornarNovoClientePessoaJuridica() {
        ClientePessoaJuridica cliente = new ClientePessoaJuridica();

        cliente.setCelular("4299883322");
        cliente.setCnpj("31194832000110");
        cliente.setDataCadastro(new Date());
        cliente.setEmail("clientepessoajuridica@teste.com.br");
        cliente.setNome("Cliente Pessoa Jurídica");
        cliente.setTelefone("4230302233");
        cliente.setTotalLocacoes(1);

        return cliente;
    }

    private static Endereco RetornarNovoEndereco() {
        Endereco endereco = new Endereco();
        endereco.setBairro("Trianon");
        endereco.setCep("85015030");
        endereco.setCidade("Guarapuava");
        endereco.setComplemento("Ap 01");
        endereco.setEstado("PR");
        endereco.setNumero("123");
        endereco.setRua("Rua Endereço de Teste");

        return endereco;
    }

    private static Dependente RetornarNovoDependente() {
        Dependente dependente = new Dependente();
        dependente.setCpf("98765432109");
        dependente.setNome("Dependente Teste");
        dependente.setRg("98765432");
        dependente.setTelefone("4299885566");

        return dependente;
    }

    private static CodigoBarrasProprio RetornarNovoCodigoBarrasProprio() {
        CodigoBarrasProprio codigo = new CodigoBarrasProprio();
        codigo.setPadrao("cod");
        codigo.setUltimoCodigo(1);

        return codigo;
    }

    private static ContaPagar RetornarNovaContaPagar() {
        ContaPagar conta = new ContaPagar();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 15);

        conta.setData(new Date());
        conta.setDataVencimento(calendario.getTime());
        conta.setDescricao("Conta a pagar de teste");
        conta.setValor(102.15);

        return conta;
    }

    private static TipoContaPagar RetornarNovoTipoContaPagar() {
        TipoContaPagar tipo = new TipoContaPagar();
        tipo.setDescricao("Conta Teste");

        return tipo;
    }

    private static Fornecedor RetornarNovoFornecedor() {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setCelular("4499887766");
        fornecedor.setCnpj("44517523000185");
        fornecedor.setDescricao("Forncedor de Teste");
        fornecedor.setInscricaoEstadual("5599232062 ");
        fornecedor.setNomeFantasia("Forncedor Teste");
        fornecedor.setRazaoSocial("Teste Ltda");
        fornecedor.setTelefone("4436364455");

        return fornecedor;
    }

    private static Produto RetornarNovoProduto() {
        Produto produto = new Produto();

        produto.setCodigoBarra("cod1");
        produto.setDataCadastro(new Date());
        produto.setNome("Produto Teste");
        produto.setQuantidade(10);
        produto.setValorVenda(9.25);

        return produto;
    }

    private static Filme RetornarNovoFilme() {
        Filme filme = new Filme();

        filme.setCodigoBarra("cod2");
        filme.setDataCadastro(new Date());
        filme.setLancamento(true);
        filme.setNome("Filme Teste");
        filme.setQuantidade(5);
        filme.setValorVenda(29.9);

        return filme;
    }

    private static TipoFilme RetornarTipoFilme() {
        TipoFilme tipo = new TipoFilme();

        tipo.setDescricao("Teste");

        return tipo;
    }

    private static TipoProduto RetornarNovoTipoProduto() {
        TipoProduto tipo = new TipoProduto();

        tipo.setDescricao("Teste");

        return tipo;
    }

    private static Funcionario RetornarNovoFuncionario() {
        Funcionario funcionario = new Funcionario();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.YEAR, -3);

        funcionario.setCarteiraTrabalho("123123123");
        funcionario.setCelular("4299886655");
        funcionario.setCpf("01234567891");
        funcionario.setDataAdmissao(calendario.getTime());
        funcionario.setDataCadastro(calendario.getTime());

        calendario.add(Calendar.YEAR, -26);

        funcionario.setDataNascimento(calendario.getTime());
        funcionario.setEmail("funcionario@teste.com.br");
        funcionario.setLogin("funcionario");
        funcionario.setNome("Funcionário Teste");
        funcionario.setRg("32132196");
        funcionario.setSenha("1234");
        funcionario.setSexo(Sexo.Masculino);
        funcionario.setTelefoneResidencial("4230304561");

        return funcionario;
    }

    private static TabelaPrecoLocacao RetornarNovaTabelaPrecoLocacao() {
        TabelaPrecoLocacao tabela = new TabelaPrecoLocacao();

        tabela.setValorLancamento(6.9);
        tabela.setValorMultaDiaria(2.0);
        tabela.setValorNormal(4.9);

        return tabela;
    }

    private static Locacao RetornarNovaLocacao() {
        Locacao locacao = new Locacao();

        locacao.setData(new Date());
        locacao.setPago(true);
        locacao.setValorTotal(6.9);

        return locacao;
    }

    private static ItemLocacao RetornarNovoItemLocacao() {
        ItemLocacao item = new ItemLocacao();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_MONTH, 5);

        item.setDataEntrega(calendario.getTime());
        item.setDataPrevisaoEntrega(calendario.getTime());

        return item;
    }

    private static PagamentoLocacao RetornarNovoPagamentoLocacao() {
        PagamentoLocacao pagamento = new PagamentoLocacao();

        pagamento.setData(new Date());
        pagamento.setDescricao("Pagamento Teste");
        pagamento.setValor(6.9);

        return pagamento;
    }

    private static TipoPagamento RetornarNovoTipoPagamento() {
        TipoPagamento tipo = new TipoPagamento();

        tipo.setDescricao("Teste");

        return tipo;
    }

    private static MovimentacaoCaixaLocacao RetornarNovaMovimentacaoCaixaLocacao() {
        MovimentacaoCaixaLocacao movimentacao = new MovimentacaoCaixaLocacao();

        movimentacao.setData(new Date());
        movimentacao.setDataFaturar(new Date());
        movimentacao.setFaturado(true);
        movimentacao.setValor(6.9);

        return movimentacao;
    }

    private static Venda RetornarNovaVenda() {
        Venda venda = new Venda();

        venda.setData(new Date());
        venda.setPago(false);
        venda.setValorTotal(45.56);

        return venda;
    }

    private static ItemVenda RetornarNovoItemVenda() {
        ItemVenda item = new ItemVenda();

        item.setQuantidade(1);
        item.setValorUnitario(45.56);

        return item;
    }

    private static PagamentoVenda RetornarNovoPagamentoVenda() {
        PagamentoVenda pagamento = new PagamentoVenda();

        pagamento.setData(new Date());
        pagamento.setDescricao("Venda de teste");
        pagamento.setValor(45.56);

        return pagamento;
    }

    private static MovimentacaoCaixaVenda RetornarNovaMovimentacaoCaixaVenda() {
        MovimentacaoCaixaVenda movimentacao = new MovimentacaoCaixaVenda();

        movimentacao.setData(new Date());
        movimentacao.setDataFaturar(new Date());
        movimentacao.setFaturado(true);
        movimentacao.setValor(45.56);

        return movimentacao;
    }

    private static MovimentacaoCaixaContaPagar RetornarNovaMivimentacaoCaixaContaPagar() {
        MovimentacaoCaixaContaPagar movimentacao = new MovimentacaoCaixaContaPagar();

        movimentacao.setData(new Date());
        movimentacao.setValor(102.15);

        return movimentacao;
    }

    private static MovimentacaoCaixaDeposito RetornarNovaMivimentacaoCaixaDeposito() {
        MovimentacaoCaixaDeposito movimentacao = new MovimentacaoCaixaDeposito();

        movimentacao.setData(new Date());
        movimentacao.setValor(102.15);
        movimentacao.setDescricao("Movimentação de teste");

        return movimentacao;
    }

    private static MovimentacaoCaixaRetirada RetornarNovaMivimentacaoCaixaRetirada() {
        MovimentacaoCaixaRetirada movimentacao = new MovimentacaoCaixaRetirada();

        movimentacao.setData(new Date());
        movimentacao.setValor(102.15);
        movimentacao.setDescricao("Movimentação de teste");

        return movimentacao;
    }

}
