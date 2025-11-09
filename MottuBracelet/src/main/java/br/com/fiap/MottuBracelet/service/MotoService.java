package br.com.fiap.MottuBracelet.service;

import br.com.fiap.MottuBracelet.model.Moto;
import br.com.fiap.MottuBracelet.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    public Moto criar(Moto moto) {
        return motoRepository.save(moto);
    }

    public Moto atualizar(Long id, Moto motoAtualizada) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        moto.setPlaca(motoAtualizada.getPlaca());
        moto.setMarca(motoAtualizada.getMarca());
        moto.setModelo(motoAtualizada.getModelo());
        moto.setAno(motoAtualizada.getAno());

        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        motoRepository.delete(moto);
    }
}
