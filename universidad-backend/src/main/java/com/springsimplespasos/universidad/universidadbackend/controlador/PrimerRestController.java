package com.springsimplespasos.universidad.universidadbackend.controlador;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@RestController
@RequestMapping("/restapi")
public class PrimerRestController {
    Logger logger = LoggerFactory.getLogger(PrimerRestController.class);
    @GetMapping("/hola-mundo")
    public ResponseEntity<String> holaMundo(){
        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
        return new ResponseEntity<>("¡Hola, Mundo! =0", HttpStatus.ACCEPTED);
    }
}
