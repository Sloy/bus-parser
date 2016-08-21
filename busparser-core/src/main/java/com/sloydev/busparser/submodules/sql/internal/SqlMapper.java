package com.sloydev.busparser.submodules.sql.internal;

import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.valueobject.Seccion;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
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

            String nombre = ((StringValue) expressions.get(1)).getValue();
            long numero = ((LongValue) expressions.get(2)).getValue();
            String horaInicio = ((StringValue) expressions.get(3)).getValue();
            String horaFin = ((StringValue) expressions.get(4)).getValue();
            long linea = ((LongValue) expressions.get(5)).getValue();


            SeccionInsert out = new SeccionInsert();
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

    public static Seccion mapSeccion(SeccionInsert in) {
        Seccion out = new Seccion();
        out.setNumeroSeccion(in.getNumeroSeccion());
        out.setNombreSeccion(in.getNombreSeccion());
        out.setHoraInicio(in.getHoraInicio());
        out.setHoraFin(in.getHoraFin());
        return out;
    }

    public static Linea mapLinea(LineaInsert in, List<Seccion> secciones) {
        Linea out = new Linea();
        out.setId(in.getId());
        out.setNumero(in.getNumero());
        out.setNombre(in.getNombre());
        out.setColor(in.getColor());
        out.setTipo(null);//TODO
        out.setTrayectos(secciones);
        return out;
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
          .sorted((o1, o2) -> o1.getNumero().compareTo(o2.getNumero()))
          .collect(Collectors.toList());
    }

    private static List<Seccion> seccionesByLinea(List<SeccionInsert> secciones, int lineaId) {
        return secciones.stream()
          .filter(s -> s.getLineaId() == lineaId)
          .map(SqlMapper::mapSeccion)
          .collect(Collectors.toList());
    }
}
