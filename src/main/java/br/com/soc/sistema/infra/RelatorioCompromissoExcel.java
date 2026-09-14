package br.com.soc.sistema.infra;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioCompromissoExcel {

	private static final String[] CABECALHOS = { "ID", "Funcionário", "Agenda", "Data", "Horário" };

	public static ByteArrayInputStream gerar(List<CompromissoVo> compromissos) {
		try (Workbook workbook = new XSSFWorkbook(); 
			 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

			Sheet sheet = workbook.createSheet("Compromissos");

			CellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.WHITE.getIndex());
			headerStyle.setFont(headerFont);
			headerStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setAlignment(HorizontalAlignment.CENTER);

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < CABECALHOS.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(CABECALHOS[i]);
				cell.setCellStyle(headerStyle);
			}

			CellStyle centerStyle = workbook.createCellStyle();
			centerStyle.setAlignment(HorizontalAlignment.CENTER);

			int rowIdx = 1;
			for (CompromissoVo comp : compromissos) {
				Row row = sheet.createRow(rowIdx++);

				Cell cellId = row.createCell(0);
				cellId.setCellValue(comp.getRowid() != null ? comp.getRowid() : "");
				cellId.setCellStyle(centerStyle);

				row.createCell(1).setCellValue(comp.getNomeFuncionario() != null ? comp.getNomeFuncionario() : "");

				row.createCell(2).setCellValue(comp.getNomeAgenda() != null ? comp.getNomeAgenda() : "");

				Cell cellData = row.createCell(3);
				cellData.setCellValue(comp.getDataFormatada() != null ? comp.getDataFormatada() : "");
				cellData.setCellStyle(centerStyle);

				Cell cellHora = row.createCell(4);
				cellHora.setCellValue(comp.getHorarioCompromisso() != null ? comp.getHorarioCompromisso() : "");
				cellHora.setCellStyle(centerStyle);
			}

			for (int i = 0; i < CABECALHOS.length; i++) {
				sheet.autoSizeColumn(i);
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());

		} catch (IOException e) {
			throw new TechnicalException("Erro ao gerar a planilha Excel de compromissos.", e);
		}
	}
}