package br.com.fatec.catalogo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PRODUTO_AUDITORIA")
public class ProdutoAuditoriaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuditoria;

    @Column(name = "id_produto_original", nullable = false)
    private long idProdutoOriginal;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "categoria_nome", length = 100)
    private String categoriaNome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ProdutoOperacaoTipo tipoAlteracao;

    @Column(name = "data_operacao", nullable = false)
    private LocalDateTime dataOperacao;

    public static ProdutoAuditoriaModel fromProduto(ProdutoModel produto, ProdutoOperacaoTipo tipoAlteracao) {
        ProdutoAuditoriaModel auditoria = new ProdutoAuditoriaModel();
        auditoria.setIdProdutoOriginal(produto.getIdProduto());
        auditoria.setNome(produto.getNome());
        auditoria.setValor(produto.getValor());
        auditoria.setQuantidade(produto.getQuantidade());
        auditoria.setTipoAlteracao(tipoAlteracao);

        if (produto.getCategoria() != null) {
            auditoria.setCategoriaId(produto.getCategoria().getIdCategoria());
            auditoria.setCategoriaNome(produto.getCategoria().getNome());
        }

        return auditoria;
    }

    @PrePersist
    protected void registrarDataOperacao() {
        this.dataOperacao = LocalDateTime.now();
    }

    public long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public long getIdProdutoOriginal() {
        return idProdutoOriginal;
    }

    public void setIdProdutoOriginal(long idProdutoOriginal) {
        this.idProdutoOriginal = idProdutoOriginal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public ProdutoOperacaoTipo getTipoAlteracao() {
        return tipoAlteracao;
    }

    public void setTipoAlteracao(ProdutoOperacaoTipo tipoAlteracao) {
        this.tipoAlteracao = tipoAlteracao;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }
}