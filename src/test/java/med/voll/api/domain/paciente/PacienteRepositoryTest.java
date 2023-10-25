package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("deberia retornar true si el paciente se encuentra activo")
    void findActivoByIdCase1() {
        Direccion direccion = Direccion.builder().calle("loca").distrito("azul").ciudad("Gigante").numero("321").complemento("15").build();
        Paciente paciente = Paciente.builder().nombre("Sol").email("s@hotmail.com").documento("456789").telefono("123123214").direccion(direccion).build();

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        System.out.println(pacienteGuardado.toString());

        var response = pacienteRepository.findActivoById(pacienteGuardado.getId());

        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("deberia retornar false si el paciente se encuentra inactivo")
    void findActivoByIdCase2() {
        Direccion direccion = Direccion.builder().calle("loca").distrito("azul").ciudad("Gigante").numero("321").complemento("15").build();
        Paciente paciente = Paciente.builder().nombre("Sol").email("s@hotmail.com").documento("456789").telefono("123123214").direccion(direccion).build();

        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        pacienteGuardado.desactivarPaciente();

        var response = pacienteRepository.findActivoById(pacienteGuardado.getId());

        assertThat(response).isFalse();
    }
}