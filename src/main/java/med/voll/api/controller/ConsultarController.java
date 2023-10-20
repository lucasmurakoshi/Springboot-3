package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import med.voll.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultarController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "registrar una consulta en la base de datos",
            description = "",
            tags = {"consulta", "post"}
    )
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) throws ValidacionDeIntegridad {

        var response = agendaDeConsultaService.agendar(datos);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @Operation(
            summary = "eliminar una consulta agendada de la base de datos",
            description = "",
            tags = {"consulta", "delete"}
    )
    public ResponseEntity eliminarConsulta(@RequestBody @Valid DatosCancelarConsulta datosCancelarConsulta){
        agendaDeConsultaService.cancelarConsulta(datosCancelarConsulta);
        return ResponseEntity.noContent().build();
    }
}
