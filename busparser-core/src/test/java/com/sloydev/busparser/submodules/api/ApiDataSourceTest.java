package com.sloydev.busparser.submodules.api;

import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ApiDataSourceTest {
    @Test
    public void reduces_paradas_with_single_section_to_parada_with_all_sections() throws Exception {
        // Given
        SeccionId seccion1 = SeccionId.create(LineaId.create(1), 1);
        Parada paradaSeccion1 = Parada.builder()
                .id(ParadaId.create(1))
                .descripcion("Parada")
                .latitud(1d)
                .longitud(1d)
                .secciones(Collections.singletonList(seccion1))
                .build();

        SeccionId seccion2 = SeccionId.create(LineaId.create(1), 2);
        Parada paradaSeccion2 = Parada.builder()
                .id(ParadaId.create(1))
                .descripcion("Parada")
                .latitud(1d)
                .longitud(1d)
                .secciones(Collections.singletonList(seccion2))
                .build();

        List<Parada> paradasWithSingleSection = Stream.of(paradaSeccion1, paradaSeccion2).collect(Collectors.toList());

        // When
        Parada paradaFinal = ApiDataSource.reduceParadaWithMergedSections(paradasWithSingleSection);
        List<SeccionId> secciones = paradaFinal.secciones();

        // Then
        assertEquals(2, secciones.size());
        assertEquals(seccion1, secciones.get(0));
        assertEquals(seccion2, secciones.get(1));
        assertEquals("Parada", paradaFinal.descripcion());
    }


}