package br.com.fatec.catalogo.repositories;

import br.com.fatec.catalogo.models.ProdutoAuditoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoAuditoriaRepository extends JpaRepository<ProdutoAuditoriaModel, Long> {
    List<ProdutoAuditoriaModel> findAllByOrderByDataOperacaoDesc();
}