package pontocristao.modelo;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento extends ModeloBase {

    @ManyToOne(optional = false)
    private TipoPagamento tipoPagamento;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Date data;

    private String descricao;

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
