package com.aluracursos.challenge.Services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
