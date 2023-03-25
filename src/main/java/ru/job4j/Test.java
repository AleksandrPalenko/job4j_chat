package ru.job4j;

/**
 * @author Aleksandr Palenko
 * @since 25.03.2023
 */
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;


public class Example {
    public static void main(String[] args) throws Exception {
        // Создание книги Excel
        Workbook workbook = new XSSFWorkbook(); // новая версия Excel
        // Workbook workbook = new HSSFWorkbook(); // старая версия Excel

        // Создание листа
        Sheet sheet = workbook.createSheet("Лист 1");
        sheet.setDefaultColumnWidth(14);

        // Настройка ширины столбцов
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 6000);

        // Определение названий ячеек
        String[] cellTitles = {
                "№ заключения",
                "Эксперт",
                "Заявленная НМЦК",
                "Рекомендованная НМЦК",
                "Снижение",
                "Основание снижения",
                "Срок исполнения контракта/договора \n(до 1 года; \n1-3 гг.; \nболее 3-х лет)",
                "Курс валюты \n(наименование валюты, курс, дата)",
                "Доля валютной составляющей \n(%)",
                "Примечание для 344-ПП",
                "Заявленная сумма начальных цен единиц",
                "Рекомендованная сумма начальных цен единиц",
                "Снижение суммы начальных цен единиц"
        };

        //  String[] cellTitles2 = {
        //    "Заявленная сумма начальных цен единиц",
        //    "Рекомендованная сумма начальных цен единиц",
        //    "Снижение суммы начальных цен единиц"
        //  };

        // Создание стиля ячейки для объединенных ячеек
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);

        // Создание объединенной ячейки и заполнение ее данными (A1-B1)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Информация к заключению");
        titleCell.setCellStyle(style);

        // Создание объединенной ячейки и заполнение ее данными (A13-B13)
        sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
        Row titleRow2 = sheet.createRow(12);
        Cell titleCell2 = titleRow2.createCell(0);
        titleCell2.setCellValue("Если есть лимит финансирования");
        titleCell2.setCellStyle(style);

        // Создание стиля ячейки для всех значений на листе, кроме объединенных ячеек
        CellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        Font font2 = workbook.createFont();
        font2.setFontName("Times New Roman");
        font2.setBold(false);
        font2.setFontHeightInPoints((short) 14);
        style2.setFont(font2);

        // создание строки для вставки значений
        for (int i = 0; i < cellTitles.length; i++) {
            Row dataRow = sheet.createRow(i + 1);
            Cell dataCell1 = dataRow.createCell(0);
            dataCell1.setCellValue(cellTitles[i]);
            dataCell1.setCellStyle(style2);
            style2.setDataFormat(workbook.createDataFormat().getFormat("1,00"));
        }

        // Запись данных в файл
        FileOutputStream outputStream = new FileOutputStream("example.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        System.out.println("Файл example.xlsx успешно создан в корневой директории проекта!");
    }
}
