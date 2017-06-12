package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MovimentacaoCaixa extends ModeloBase {

    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    private Caixa caixa;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
}
