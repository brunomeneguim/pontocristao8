package pontocristao.modelo;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MovimentacaoCaixaEntrada extends MovimentacaoCaixa {

    @Column(nullable = false)
    private Date dataFaturar;

    @Column(nullable = false)
    private Boolean faturado;

    public Date getDataFaturar() {
        return dataFaturar;
    }

    public void setDataFaturar(Date dataFaturar) {
        this.dataFaturar = dataFaturar;
    }

    public Boolean getFaturado() {
        return faturado;
    }

    public void setFaturado(Boolean faturado) {
        this.faturado = faturado;
    }

}
