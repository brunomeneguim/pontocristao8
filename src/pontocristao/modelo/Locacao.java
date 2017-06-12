package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class Locacao extends ModeloBase {

    @Column(nullable = false)
    private Date data;

    @OneToMany(mappedBy = "locacao")
    private Set<ItemLocacao> ItensLocacao = new HashSet<ItemLocacao>(0);

    @Column(nullable = false)
    private Double valorTotal;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Cliente cliente;

    @Column(nullable = false)
    private Boolean pago;

    @Column(nullable = false)
    private Boolean devolvido;

    @OneToMany(mappedBy = "locacao")
    private Set<PagamentoLocacao> pagamentos = new HashSet<PagamentoLocacao>(0);

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Set<ItemLocacao> getItensLocacao() {
        return ItensLocacao;
    }

    public void setItensLocacao(Set<ItemLocacao> ItensLocacao) {
        this.ItensLocacao = ItensLocacao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Boolean getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(Boolean devolvido) {
        this.devolvido = devolvido;
    }

    public Set<PagamentoLocacao> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Set<PagamentoLocacao> pagamentos) {
        this.pagamentos = pagamentos;
    }

}
