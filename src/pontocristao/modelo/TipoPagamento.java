package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class TipoPagamento extends ModeloBase {

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "tipoPagamento")
    private Set<Pagamento> pagamentos = new HashSet<Pagamento>(0);

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
