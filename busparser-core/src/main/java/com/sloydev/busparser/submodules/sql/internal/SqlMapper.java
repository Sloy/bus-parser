package com.sloydev.busparser.submodules.sql.internal;

import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.valueobject.LineaId;
import com.sloydev.busparser.core.model.Seccion;
import com.sloydev.busparser.core.model.valueobject.SeccionId;
import com.sloydev.busparser.core.model.valueobject.TipoLinea;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

import java.util.List;
import java.util.stream.Collectors;

public class SqlMapper {

    public static SeccionInsert mapSeccionInsert(String insert) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(insert);
            List<Expression> expressions = ((ExpressionList) ((Insert) stmt).getItemsList()).getExpressions();

            long id = ((LongValue) expressions.get(0)).getValue();
            String nombre = ((StringValue) expressions.get(1)).getValue();
            long numero = ((LongValue) expressions.get(2)).getValue();
            String horaInicio = ((StringValue) expressions.get(3)).getValue();
            String horaFin = ((StringValue) expressions.get(4)).getValue();
            long linea = ((LongValue) expressions.get(5)).getValue();


            SeccionInsert out = new SeccionInsert();
            out.setId((int) id);
            out.setNumeroSeccion((int) numero);
            out.setNombreSeccion(nombre);
            out.setHoraInicio(horaInicio);
            out.setHoraFin(horaFin);
            out.setLineaId((int) linea);
            return out;
        } catch (JSQLParserException e) {
            System.out.println("insert = [" + insert + "]");
            throw new RuntimeException(e);
        }
    }

    private static Seccion mapSeccion(LineaId lineaId, SeccionInsert in) {
        return Seccion.builder()
                .id(SeccionId.create(lineaId, in.getNumeroSeccion()))
                .nombreSeccion(in.getNombreSeccion())
                .horaInicio(in.getHoraInicio())
                .horaFin(in.getHoraFin())
                .build();
    }

    private static Linea mapLinea(LineaInsert in, List<Seccion> secciones) {
        return Linea.builder()
                .id(LineaId.create(in.getId()))
                .numero(in.getNumero())
                .nombre(in.getNombre())
                .color(in.getColor())
                .tipo(TipoLinea.create())//TODO
                .trayectos(secciones)
                .build();
    }

    public static LineaInsert mapLineaInsert(String insert) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(insert);
            List<Expression> expressions = ((ExpressionList) ((Insert) stmt).getItemsList()).getExpressions();

            long id = ((LongValue) expressions.get(0)).getValue();
            String numero = ((StringValue) expressions.get(1)).getValue();
            String nombre = ((StringValue) expressions.get(2)).getValue();
            long tipo = ((LongValue) expressions.get(3)).getValue();
            String color = ((StringValue) expressions.get(4)).getValue();

            LineaInsert out = new LineaInsert();
            out.setId((int) id);
            out.setNumero(numero);
            out.setNombre(nombre);
            out.setTipoLinea((int) tipo);
            out.setColor(color);
            return out;
        } catch (JSQLParserException e) {
            System.out.println("insert = [" + insert + "]");
            throw new RuntimeException(e);
        }
    }

    public static List<Linea> mapLineasFromInserts(List<String> lineasInserts, List<String> seccionInserts) {
        List<SeccionInsert> seccionInsertsList = seccionInserts.stream()
          .filter((s) -> !s.isEmpty())
          .map(SqlMapper::mapSeccionInsert)
          .collect(Collectors.toList());

        return lineasInserts.stream()
          .filter((s) -> !s.isEmpty())
          .map(SqlMapper::mapLineaInsert)
          .map(lineaInsert -> mapLinea(lineaInsert, seccionesByLinea(seccionInsertsList, lineaInsert.getId())))
          .sorted((o1, o2) -> o1.numero().compareTo(o2.numero()))
          .collect(Collectors.toList());
    }


    public static RelacionInsert mapRelacionInsert(String insert) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(insert);
            List<Expression> expressions = ((ExpressionList) ((Insert) stmt).getItemsList()).getExpressions();

            int id = (int) ((LongValue) expressions.get(0)).getValue();
            int seccion = (int) ((LongValue) expressions.get(1)).getValue();
            int parada = (int) ((LongValue) expressions.get(2)).getValue();

            RelacionInsert out = new RelacionInsert();
            out.id = id;
            out.seccion = seccion;
            out.parada = parada;
            return out;
        } catch (JSQLParserException e) {
            System.out.println("insert = [" + insert + "]");
            throw new RuntimeException(e);
        }
    }
    private static List<Seccion> seccionesByLinea(List<SeccionInsert> secciones, int lineaId) {
        LineaId lineaIdInstance = LineaId.create(lineaId);
        return secciones.stream()
          .filter(s -> s.getLineaId() == lineaId)
          .map((in) -> mapSeccion(lineaIdInstance, in))
          .collect(Collectors.toList());
    }

    public static ParadaInsert mapParadaInsert(String insert) {
        try {
            Statement stmt = CCJSqlParserUtil.parse(insert);
            List<Expression> expressions = ((ExpressionList) ((Insert) stmt).getItemsList()).getExpressions();

            int numero = (int) ((LongValue) expressions.get(0)).getValue();
            String descripcion = ((StringValue) expressions.get(1)).getValue();
            double latitud = unwrapSignedDouble(expressions.get(2));
            double longitud = unwrapSignedDouble(expressions.get(3));

            ParadaInsert out = new ParadaInsert();
            out.numero = numero;
            out.descripcion = descripcion;
            out.latitud = latitud;
            out.longitud = longitud;
            return out;
        } catch (JSQLParserException e) {
            System.out.println("insert = [" + insert + "]");
            throw new RuntimeException(e);
        }
    }

    public static double unwrapSignedDouble(Expression expression) {
        if (expression instanceof DoubleValue) {
            return ((DoubleValue) expression).getValue();
        } else if (expression instanceof SignedExpression) {
            double value = ((DoubleValue) ((SignedExpression) expression).getExpression()).getValue();
            if (((SignedExpression) expression).getSign() == '-') {
                value *= -1d;
            }
            return value;
        } else {
            throw new IllegalArgumentException("Wuut");
        }
    }
}
