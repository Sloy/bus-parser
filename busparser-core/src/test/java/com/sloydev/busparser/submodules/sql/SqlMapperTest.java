package com.sloydev.busparser.submodules.sql;

import com.sloydev.busparser.submodules.sql.internal.LineaInsert;
import com.sloydev.busparser.submodules.sql.internal.SeccionInsert;
import com.sloydev.busparser.submodules.sql.internal.SqlMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlMapperTest {

    @Test
    public void readsSeccionInsert() throws Exception {
        String insert = "INSERT INTO seccion (id, nombreSeccion, numeroSeccion, horaInicio, horaFin, linea_id) VALUES(892, 'HOSPITAL V.ROCIO', 1, '0:00', '1:00', 2);";

        SeccionInsert seccion = SqlMapper.mapSeccionInsert(insert);

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
}