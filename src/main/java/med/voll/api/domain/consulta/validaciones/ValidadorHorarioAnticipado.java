package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAnticipado implements ValidadorCancelamientoConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatosCancelarConsulta datosCancelarConsulta){
        var consulta = consultaRepository.getReferenceById(datosCancelarConsulta.id());
        var ahora = LocalDateTime.now();
        var diferenciaHoraria = Duration.between(ahora, consulta.getFecha()).toHours();

        if(diferenciaHoraria<24){
            throw new ValidationException("Las consultas solo pueden ser canceladas con 24hs de antelacion");
        }
    }
}
