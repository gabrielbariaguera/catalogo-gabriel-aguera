package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.ProdutoAuditoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.models.ProdutoOperacaoTipo;
import br.com.fatec.catalogo.repositories.ProdutoAuditoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ProdutoAuditoriaRepository auditoriaRepository;

    public List<ProdutoModel> listarTodos() {
        return repository.findAllByOrderByDataAtualizacaoDesc();
    }

    public List<ProdutoAuditoriaModel> listarParaAuditoria() {
        return auditoriaRepository.findAllByOrderByDataOperacaoDesc();
    }

    // Resolve o Desafio 1
    public List<ProdutoModel> listarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public ProdutoModel buscarPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    public List<ProdutoModel> listarPorCategoria(Long idCategoria) {
        return repository.findByCategoriaIdCategoria(idCategoria);
    }

    // Resolve o Desafio 2
    @Transactional
    public ProdutoModel salvar(ProdutoModel produto) {
        if (produto.getQuantidade() == null || produto.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }

        boolean novoProduto = produto.getIdProduto() == 0;

        // Regra: Não permitir duplicidade de nome em novos registros
        if (novoProduto && repository.existsByNome(produto.getNome())) {
            throw new RuntimeException("Já existe um produto com este nome.");
        }

        ProdutoModel salvo = repository.save(produto);
        ProdutoOperacaoTipo tipoAlteracao = novoProduto ? ProdutoOperacaoTipo.CREATE : ProdutoOperacaoTipo.UPDATE;
        auditoriaRepository.save(ProdutoAuditoriaModel.fromProduto(salvo, tipoAlteracao));
        return salvo;
    }

    @Transactional
    public void excluir(long id) {
        ProdutoModel produto = buscarPorId(id);
        auditoriaRepository.save(ProdutoAuditoriaModel.fromProduto(produto, ProdutoOperacaoTipo.DELETE));
        repository.delete(produto);
    }
}