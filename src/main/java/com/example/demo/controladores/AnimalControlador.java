package com.example.demo.controladores;

import com.example.demo.dto.ContagemPorFuncionario;
import com.example.demo.entidades.Animal;
import com.example.demo.repositorios.AnimalRepositorio;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalControlador {

    private AnimalRepositorio repositorio;

    public AnimalControlador(AnimalRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    private List<Animal> buscarTodos() {
        return repositorio.findAll();
    }

    @PostMapping
    private Animal criar(@RequestBody Animal animal) {
        return repositorio.save(animal);
    }

    @GetMapping("/not-adopted")
    private List<Animal> buscarTodosNaoAdotados() {
        return repositorio.findNotAdopted();
    }

    @GetMapping("/adopted")
    private List<Animal> buscarTodosAdotados() {
        return repositorio.findAdopted();
    }


    @GetMapping("/count-by-employee")
    private List<ContagemPorFuncionario> buscarTodosAdotados(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim) {
        var diferencaEmMillis = Math.abs(dataInicio.getTime() - dataFim.getTime());

        long milissegundosEmUmAno = 365L * 24 * 60 * 60 * 1000;

        if (diferencaEmMillis > milissegundosEmUmAno) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A diferença entre datas dever ser no máximo de 1 ano");
        }

        var resultados = repositorio.countByEmployee(dataInicio, dataFim);
        var contagemFuncionarios = new ArrayList<ContagemPorFuncionario>();

        for (Object[] resultado : resultados) {
            var contagem = new ContagemPorFuncionario();
            contagem.setNomeFuncionario((String) resultado[0]);
            contagem.setQuantidadeResgates((Long) resultado[1]);
            contagemFuncionarios.add(contagem);
        }

        return contagemFuncionarios;
    }
}
