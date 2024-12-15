package com.aluracursos.challenge.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosGenerales(
        @JsonAlias("results") List<DatosLibros> resultados
) {
}
