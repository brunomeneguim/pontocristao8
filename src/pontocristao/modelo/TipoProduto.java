package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
public class TipoProduto extends ModeloBase {

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "tipoProduto")
    private Set<Produto> produtos = new HashSet<Produto>(0);

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }
}
