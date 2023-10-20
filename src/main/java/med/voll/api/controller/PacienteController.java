package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "registrar paciente en la base de datos",
            description = "",
            tags = {"consulta", "post"}
    )
    public void registrar(@RequestBody @Valid DatosRegistroPaciente datos){
        pacienteRepository.save(new Paciente(datos));
    }

    // public void listadoPacientes(){}

    @PutMapping
    @Transactional
    @Operation(
            summary = "actualizar paciente agendado de la base de datos",
            description = "",
            tags = {"consulta", "put"}
    )
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "eliminar paciente agendado de la base de datos",
            description = "",
            tags = {"consulta", "delete"}
    )
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "consultar paciente agendado por id de la base de datos",
            description = "",
            tags = {"consulta", "get"}
    )
    public ResponseEntity<DatosRespuestaPaciente> retornarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero(), paciente.getDireccion().getComplemento()));

        return ResponseEntity.ok(datosPaciente);
    }
}
