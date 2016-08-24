package com.sloydev.busparser.submodules.sql;

import com.sloydev.busparser.submodules.sql.internal.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlMapperTest {

    @Test
    public void readsSeccionInsert() throws Exception {
        String insert = "INSERT INTO seccion (id, nombreSeccion, numeroSeccion, horaInicio, horaFin, linea_id) VALUES(892, 'HOSPITAL V.ROCIO', 1, '0:00', '1:00', 2);";

        SeccionInsert seccion = SqlMapper.mapSeccionInsert(insert);

        assertEquals(892, seccion.getId());
        assertEquals("HOSPITAL V.ROCIO", seccion.getNombreSeccion());
        assertEquals(1, seccion.getNumeroSeccion());
        assertEquals("0:00", seccion.getHoraInicio());
        assertEquals("1:00", seccion.getHoraFin());
        assertEquals(2, seccion.getLineaId());
    }

    @Test
    public void readsLineaInsert() throws Exception {
        String insert = "INSERT INTO linea (id, numero, nombre, tipo_id, color) VALUES(1, '01', 'Pgno.Norte-Glorieta Plus Ultra', 2, '#f54129');";

        LineaInsert linea = SqlMapper.mapLineaInsert(insert);

        assertEquals(1, linea.getId());
        assertEquals("01", linea.getNumero());
        assertEquals("Pgno.Norte-Glorieta Plus Ultra", linea.getNombre());
        assertEquals(2, linea.getTipoLinea());
        assertEquals("#f54129", linea.getColor());
    }

    @Test
    public void readsRelacionInsert() throws Exception {
        String insert = "INSERT INTO paradaseccion (id, seccion_id, parada_id) VALUES (1001000, 101, 257);";

        RelacionInsert relacion = SqlMapper.mapRelacionInsert(insert);

        assertEquals(1001000, relacion.id);
        assertEquals(101, relacion.seccion);
        assertEquals(257, relacion.parada);
    }

    @Test
    public void readsParadaInsert() throws Exception {
        String insert = "INSERT INTO parada (numero, descripcion, latitud, longitud) VALUES (1, 'Campana (Sierpes)', 37.3927116394043, -5.995132923126221);";

        ParadaInsert parada = SqlMapper.mapParadaInsert(insert);

        assertEquals(1, parada.numero);
        assertEquals("Campana (Sierpes)", parada.descripcion);
        assertEquals(37.3927116394043d, parada.latitud, 0.00000001d);
        assertEquals(-5.995132923126221, parada.longitud, 0.00000001d);
    }
}