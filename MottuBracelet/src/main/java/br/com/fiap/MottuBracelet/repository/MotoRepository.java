package br.com.fiap.MottuBracelet.repository;

import br.com.fiap.MottuBracelet.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    // se depois quiser buscar por placa: Optional<Moto> findByPlaca(String placa);
}
