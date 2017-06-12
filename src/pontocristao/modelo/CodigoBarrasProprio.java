package pontocristao.modelo;

import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class CodigoBarrasProprio extends ModeloBase {

    @Column(nullable = false)
    private Integer ultimoCodigo;

    @Column(nullable = false)
    private String padrao;

    public Integer getUltimoCodigo() {
        return ultimoCodigo;
    }

    public void setUltimoCodigo(Integer ultimoCodigo) {
        this.ultimoCodigo = ultimoCodigo;
    }

    public String getPadrao() {
        return padrao;
    }

    public void setPadrao(String padrao) {
        this.padrao = padrao;
    }

}
