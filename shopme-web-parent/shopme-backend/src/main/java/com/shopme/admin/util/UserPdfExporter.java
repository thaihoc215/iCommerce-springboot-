package com.shopme.admin.util;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter {

    private static final String[] HEADER = {"ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of Users", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(HEADER.length);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});
        writeTableHeader(table);
        writeTableData(table, userList);
        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);

        for (String s : HEADER) {
            cell.setPhrase(new Phrase(s, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table, List<User> userList) {
        for (User user : userList) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(String.valueOf(user.getEmail()));
            table.addCell(String.valueOf(user.getFirstName()));
            table.addCell(String.valueOf(user.getLastName()));
            table.addCell(String.valueOf(user.getRoles().toString()));
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

}
