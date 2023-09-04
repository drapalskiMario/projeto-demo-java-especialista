package com.example.demo.repositorios;

import com.example.demo.entidades.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AnimalRepositorio extends JpaRepository<Animal, Integer> {

    @Query("SELECT a FROM Animal a WHERE a.dataAdocao IS NULL ORDER BY a.dataEntrada")
    List<Animal> findNotAdopted();

    @Query("SELECT a FROM Animal a WHERE a.dataAdocao IS NOT NULL")
    List<Animal> findAdopted();

    @Query("SELECT a.nomeRecebedor AS nomeFuncionario, COUNT(a.id) AS quantidadeResgates " +
            "FROM Animal a " +
            "WHERE a.dataEntrada BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY a.nomeRecebedor")
    List<Object[]> countByEmployee(
            @Param("dataInicio") Date dataInicio,
            @Param("dataFim") Date dataFim
    );
}
