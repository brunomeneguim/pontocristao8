/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontocristao.controle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pontocristao.modelo.Caixa;
import pontocristao.modelo.MovimentacaoCaixa;
import pontocristao.modelo.MovimentacaoCaixaContaPagar;
import pontocristao.modelo.MovimentacaoCaixaDeposito;
import pontocristao.modelo.MovimentacaoCaixaEntrada;
import pontocristao.modelo.MovimentacaoCaixaLocacao;
import pontocristao.modelo.MovimentacaoCaixaRetirada;
import pontocristao.modelo.MovimentacaoCaixaSaida;
import pontocristao.modelo.MovimentacaoCaixaVenda;

/**
 *
 * @author Marcondes
 */
public class ControleCaixa extends ControleBase {

    public ControleCaixa(Session sessao) {
        if (sessao != null) {
            setSessao(sessao);
        }
    }

    public ControleCaixa() {
    }

    public Caixa RetornarCaixa() {
        String sql = "SELECT * FROM Caixa WHERE excluido = false";
        Query q = getSessao().createSQLQuery(sql).addEntity(Caixa.class);

        List<Caixa> caixas = (List<Caixa>) q.list();

        if (caixas.isEmpty()) {

            Caixa caixa = new Caixa();
            caixa.setSaldo(0d);

            getSessao().save(caixa);

            return RetornarCaixa();
        } else {
            return caixas.get(caixas.size() - 1);
        }
    }

    public List<MovimentacaoCaixa> RetornarMovimentacoesCaixa() {
        String sqlMovimentacaoCaixaContaPagar = "SELECT * FROM MovimentacaoCaixaContaPagar JOIN MovimentacaoCaixaSaida ON MovimentacaoCaixaContaPagar.id = MovimentacaoCaixaSaida.id JOIN MovimentacaoCaixa ON MovimentacaoCaixa.id = MovimentacaoCaixaSaida.Id WHERE MovimentacaoCaixa.excluido = false";
        String sqlMovimentacaoCaixaDeposito = "SELECT * FROM MovimentacaoCaixaDeposito JOIN MovimentacaoCaixaEntrada ON MovimentacaoCaixaDeposito.id = MovimentacaoCaixaEntrada.id JOIN MovimentacaoCaixa ON MovimentacaoCaixa.id = MovimentacaoCaixaEntrada.Id WHERE MovimentacaoCaixa.excluido = false";
        String sqlMovimentacaoCaixaLocacao = "SELECT * FROM MovimentacaoCaixaLocacao JOIN MovimentacaoCaixaEntrada ON MovimentacaoCaixaLocacao.id = MovimentacaoCaixaEntrada.id JOIN MovimentacaoCaixa ON MovimentacaoCaixa.id = MovimentacaoCaixaEntrada.Id WHERE MovimentacaoCaixa.excluido = false";
        String sqlMovimentacaoCaixaRetirada = "SELECT * FROM MovimentacaoCaixaRetirada JOIN MovimentacaoCaixaSaida ON MovimentacaoCaixaRetirada.id = MovimentacaoCaixaSaida.id JOIN MovimentacaoCaixa ON MovimentacaoCaixa.id = MovimentacaoCaixaSaida.Id WHERE MovimentacaoCaixa.excluido = false";
        String sqlMovimentacaoCaixaVenda = "SELECT * FROM MovimentacaoCaixaVenda JOIN MovimentacaoCaixaEntrada ON MovimentacaoCaixaVenda.id = MovimentacaoCaixaEntrada.id JOIN MovimentacaoCaixa ON MovimentacaoCaixa.id = MovimentacaoCaixaEntrada.Id WHERE MovimentacaoCaixa.excluido = false";

        Query qMovimentacaoCaixaContaPagar = getSessao().createSQLQuery(sqlMovimentacaoCaixaContaPagar).addEntity(MovimentacaoCaixaContaPagar.class);
        Query qMovimentacaoCaixaDeposito = getSessao().createSQLQuery(sqlMovimentacaoCaixaDeposito).addEntity(MovimentacaoCaixaDeposito.class);
        Query qMovimentacaoCaixaLocacao = getSessao().createSQLQuery(sqlMovimentacaoCaixaLocacao).addEntity(MovimentacaoCaixaLocacao.class);
        Query qMovimentacaoCaixaRetirada = getSessao().createSQLQuery(sqlMovimentacaoCaixaRetirada).addEntity(MovimentacaoCaixaRetirada.class);
        Query qMovimentacaoCaixaVenda = getSessao().createSQLQuery(sqlMovimentacaoCaixaVenda).addEntity(MovimentacaoCaixaVenda.class);

        List<MovimentacaoCaixaContaPagar> movimentacoesCaixaContaPagar = qMovimentacaoCaixaContaPagar.list();
        List<MovimentacaoCaixaDeposito> movimentacoesCaixaDeposito = qMovimentacaoCaixaDeposito.list();
        List<MovimentacaoCaixaLocacao> movimentacoesCaixaLocacao = qMovimentacaoCaixaLocacao.list();
        List<MovimentacaoCaixaRetirada> movimentacoesCaixaRetirada = qMovimentacaoCaixaRetirada.list();
        List<MovimentacaoCaixaVenda> movimentacoesCaixaVenda = qMovimentacaoCaixaVenda.list();

        ArrayList<MovimentacaoCaixa> movimentacoes = new ArrayList<MovimentacaoCaixa>();

        movimentacoes.addAll(movimentacoesCaixaContaPagar);
        movimentacoes.addAll(movimentacoesCaixaDeposito);
        movimentacoes.addAll(movimentacoesCaixaLocacao);
        movimentacoes.addAll(movimentacoesCaixaRetirada);
        movimentacoes.addAll(movimentacoesCaixaVenda);

        Collections.sort(movimentacoes, new Comparator<MovimentacaoCaixa>() {
            @Override
            public int compare(MovimentacaoCaixa c2, MovimentacaoCaixa c1) {
                if (c2.getId() > c1.getId()) {
                    return 1;
                } else {
                    return -1;
                }

            }
        });

        return movimentacoes;
    }

    public void AdicionarMovimentacao(MovimentacaoCaixa movimentacao) throws Exception {
        Caixa caixa = RetornarCaixa();
        movimentacao.setCaixa(caixa);
        caixa.getMovimentacoes().add(movimentacao);

        if (movimentacao instanceof MovimentacaoCaixaEntrada) {
            caixa.setSaldo(caixa.getSaldo() + movimentacao.getValor());

            MovimentacaoCaixaEntrada mov = (MovimentacaoCaixaEntrada) movimentacao;
            mov.setFaturado(true);
            mov.setDataFaturar(new Date());
        } else if (movimentacao instanceof MovimentacaoCaixaSaida) {
            caixa.setSaldo(caixa.getSaldo() - movimentacao.getValor());
        } else {
            throw new Exception("Tipo desconhecido.");
        }

        Session s = getSessao();

        s.saveOrUpdate(movimentacao);
        s.saveOrUpdate(caixa);

    }

}
