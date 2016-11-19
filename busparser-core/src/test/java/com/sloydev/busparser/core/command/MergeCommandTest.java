package com.sloydev.busparser.core.command;

import com.sloydev.busparser.core.model.DataOutput;
import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.valueobject.ParadaId;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MergeCommandTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    DataSource input1;
    @Mock
    DataSource input2;
    @Mock
    DataOutput output;

    @Captor
    ArgumentCaptor<List<Linea>> lineasCaptor;

    @Test
    public void merges_different_lines() throws Exception {
        Linea aLine = line(1);
        Linea anotherLine = line(2);
        given(input1.obtainLineas()).willReturn(singletonList(aLine));
        given(input2.obtainLineas()).willReturn(singletonList(anotherLine));

        List<Linea> lineas = mergeLines();

        assertThat(lineas).hasSize(2)
                .contains(aLine, anotherLine);
    }

    @Test
    public void not_repeated_lines() throws Exception {
        Linea aLine = line(1);
        Linea anotherLine = line(2);
        given(input1.obtainLineas()).willReturn(asList(aLine, anotherLine));
        given(input2.obtainLineas()).willReturn(singletonList(anotherLine));

        List<Linea> lineas = mergeLines();

        assertThat(lineas).hasSize(2)
                .contains(aLine, anotherLine);
    }

    @Test
    public void merge_sections_from_same_line() throws Exception {
        Linea line12 = line(1, section(1), section(2));
        Linea line23 = line(1, section(2), section(3));
        given(input1.obtainLineas()).willReturn(singletonList(line12));
        given(input2.obtainLineas()).willReturn(singletonList(line23));

        List<Linea> lineas = mergeLines();

        assertThat(lineas.get(0).trayectos())
                .hasSize(3)
                .contains(section(1), section(2), section(3));
    }

    @Test
    public void merge_bus_stops_from_same_section() throws Exception {
        Linea sectionWith12 = line(1, section(1, parada(1), parada(2)));
        Linea sectionWith23 = line(1, section(1, parada(2), parada(3)));
        Linea otherSection = line(1, section(2, parada(4)));
        given(input1.obtainLineas()).willReturn(asList(sectionWith12, otherSection));
        given(input2.obtainLineas()).willReturn(singletonList(sectionWith23));

        List<Linea> lineas = mergeLines();

        assertThat(lineas.get(0).trayectos().get(0).paradaIds())
                .hasSize(3)
                .contains(parada(1), parada(2), parada(3));
        assertThat(lineas.get(0).trayectos().get(1).paradaIds())
                .hasSize(1)
                .contains(parada(4));

    }

    private ParadaId parada(int numero) {
        return ParadaId.create(numero);
    }

    private List<Linea> mergeLines() {
        new MergeCommand(input1, input2, output).run();
        verify(output).outputLineas(lineasCaptor.capture());
        return lineasCaptor.getValue();
    }

    private static Seccion section(int section) {
        return section(section, new ParadaId[]{});
    }

    private static Seccion section(int section, ParadaId... paradaIds) {
        return Seccion.builder()
                .id(SeccionId.create(LineaId.create(1), section))
                .nombreSeccion("section_" + section)
                .horaFin("")
                .horaInicio("")
                .paradaIds(Arrays.asList(paradaIds))
                .build();
    }

    private static Linea line(int id, Seccion... trayectos) {
        return Linea.builder()
                .id(LineaId.create(id))
                .numero("n" + id)
                .nombre("nombre_" + id)
                .color("")
                .trayectos(Arrays.asList(trayectos))
                .tipo(TipoLinea.create())
                .build();
    }

    private static Linea line(int id) {
        return line(id, new Seccion[]{});
    }
}