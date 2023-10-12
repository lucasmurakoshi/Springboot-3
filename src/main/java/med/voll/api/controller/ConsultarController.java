package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultarController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        agendaDeConsultaService.agendar(datos);

        return ResponseEntity.ok(new DatosDetallesConsulta(null, null, null, null));
    }
}
