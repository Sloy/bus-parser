package com.sloydev.busparser.submodules.sql;

import com.sloydev.busparser.core.model.DataSource;
import com.sloydev.busparser.core.model.Linea;
import com.sloydev.busparser.core.model.Parada;
import com.sloydev.busparser.submodules.sql.internal.SqlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SqlDataSource implements DataSource {

    private final Path lineasSqlFile;
    private final Path seccionesSqlFile;

    public SqlDataSource(Path lineasSqlFile, Path seccionesSqlFile) {
        this.lineasSqlFile = lineasSqlFile;
        this.seccionesSqlFile = seccionesSqlFile;
    }

    @Override
    public List<Linea> obtainLineas() {
        try {
            List<String> seccionInserts = Files.readAllLines(seccionesSqlFile);
            List<String> lineasInserts = Files.readAllLines(lineasSqlFile);

            return SqlMapper.mapLineasFromInserts(lineasInserts, seccionInserts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Parada> obtainParadas() {
        //TODO
        throw new IllegalStateException("Not yet implemented");
    }


}
