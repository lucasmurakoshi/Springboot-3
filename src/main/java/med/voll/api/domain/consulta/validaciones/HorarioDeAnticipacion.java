package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var ahora = LocalDateTime.now();
        var horaDeConsulta = datosAgendarConsulta.fecha();

        var diferenciaHoraria30min = Duration.between(ahora, horaDeConsulta).toMinutes()<30;
        if(diferenciaHoraria30min){
            throw new ValidationException("La consulta debe programarse con al menos 30' de anticipacion");
        }
    }
}
