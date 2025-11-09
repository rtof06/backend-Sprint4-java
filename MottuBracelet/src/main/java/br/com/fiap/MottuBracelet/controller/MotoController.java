package br.com.fiap.MottuBracelet.controller;

import br.com.fiap.MottuBracelet.model.Moto;
import br.com.fiap.MottuBracelet.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
@CrossOrigin(origins = "*")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarTodas() {
        return ResponseEntity.ok(motoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(motoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Moto> criar(@RequestBody Moto moto) {
        Moto novaMoto = motoService.criar(moto);
        return ResponseEntity.ok(novaMoto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> atualizar(@PathVariable Long id, @RequestBody Moto moto) {
        Moto motoAtualizada = motoService.atualizar(id, moto);
        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
