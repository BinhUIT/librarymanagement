package com.library.librarymanagement.ulti;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

public final class JasperReport {
    private JasperReport() {
    }

    public static byte[] exportReportFromJasperFile(
            @NonNull final String jasperFilePath,
            @NonNull final Map<String, Object> parameters,
            @NonNull final DataSource dataSource) throws Exception {
        final var reportStream = JasperReport.class.getResourceAsStream(jasperFilePath);

        try (final var connection = dataSource.getConnection()) {
            final var jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);

            final var outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return outputStream.toByteArray();
        }
    }

    public static byte[] exportReportFromJrxmlFile(
            @NotNull final String jrxmlFilePath,
            @NonNull final Map<String, Object> parameters,
            @NonNull final DataSource dataSource) throws Exception {
        final var jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

        try (final var connection = dataSource.getConnection()) {
            final var jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            final var outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return outputStream.toByteArray();
        }
    }
}
