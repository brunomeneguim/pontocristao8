package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class TabelaPrecoLocacao extends ModeloBase {

    @Column(nullable = false)
    private Double valorNormal;

    @Column(nullable = false)
    private Double valorLancamento;

    @Column(nullable = false)
    private Double valorMultaDiaria;

    public Double getValorNormal() {
        return valorNormal;
    }

    public void setValorNormal(Double valorNormal) {
        this.valorNormal = valorNormal;
    }

    public Double getValorLancamento() {
        return valorLancamento;
    }

    public void setValorLancamento(Double valorLancamento) {
        this.valorLancamento = valorLancamento;
    }

    public Double getValorMultaDiaria() {
        return valorMultaDiaria;
    }

    public void setValorMultaDiaria(Double valorMultaDiaria) {
        this.valorMultaDiaria = valorMultaDiaria;
    }

}
