package med.voll.api.domain.consulta;

import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("deberia retornar true si existe algun medico a partir de un id y una fecha")
    void existsByMedicoIdAndFecha() {
        var proximoViernes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
                .atTime(10,0);

        Direccion direccion = Direccion.builder().calle("loca").distrito("azul").ciudad("Gigante").numero("321").complemento("15").build();
        Medico medico = Medico.builder().nombre("Lucas").email("l@hotmail.com").documento("123456").especialidad(Especialidad.PEDIATRIA).direccion(direccion).telefono("123123214").build();
        Paciente paciente = Paciente.builder().nombre("Sol").email("s@hotmail.com").documento("456789").telefono("123123214").direccion(direccion).build();
        Consulta consulta = Consulta.builder().paciente(paciente).medico(medico).fecha(proximoViernes10H).build();

        Medico medicoGuardado = medicoRepository.save(medico);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        Consulta consultaGuardada = consultaRepository.save(consulta);

        System.out.println(medico.toString());
        System.out.println(consulta.toString());

        var response = consultaRepository.existsByMedicoIdAndFecha(medicoGuardado.getId(), proximoViernes10H);

        assertThat(consulta).isNotNull();
        assertThat(response).isTrue();
    }

    @Test
    @DisplayName("deberia retornar true si el paciente ya tiene una consulta existente para ese dia")
    void existsByPacienteIdAndFechaBetween() {

        var proximoViernes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.FRIDAY))
                .atTime(10,0);

        Direccion direccion = Direccion.builder().calle("loca").distrito("azul").ciudad("Gigante").numero("321").complemento("15").build();
        Medico medico = Medico.builder().nombre("Lucas").email("l@hotmail.com").documento("123456").especialidad(Especialidad.PEDIATRIA).direccion(direccion).telefono("123123214").build();
        Paciente paciente = Paciente.builder().nombre("Sol").email("s@hotmail.com").documento("456789").telefono("123123214").direccion(direccion).build();
        Consulta consulta = Consulta.builder().paciente(paciente).medico(medico).fecha(proximoViernes10H).build();

        Medico medicoGuardado = medicoRepository.save(medico);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        Consulta consultaGuardada = consultaRepository.save(consulta);


        var primerHorario = consultaGuardada.getFecha().withHour(7);
        var ultimoHorario = consultaGuardada.getFecha().withHour(18);

        var response = consultaRepository.existsByPacienteIdAndFechaBetween(paciente.getId(), primerHorario, ultimoHorario);

        assertThat(response).isTrue();
    }
}
