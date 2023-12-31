package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name="consultas")
@Entity(name="Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Builder
@ToString
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
    }

    public void eliminarConsulta(MotivoCancelamiento motivoCancelamiento) {
        this.motivoCancelamiento = motivoCancelamiento;
    }
}
