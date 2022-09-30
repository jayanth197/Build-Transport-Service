package com.cintap.transport.common.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import com.cintap.transport.common.enums.CONNECTIONTYPE;
import com.cintap.transport.common.ftp.service.FtpFileOperationsService;
import com.cintap.transport.common.message.model.Connection;
import com.cintap.transport.common.sftp.service.SFtpFileOperationsService;
import com.cintap.transport.common.util.TransportCommonUtility;
import com.cintap.transport.common.util.TransportConstants;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeader;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCarton;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCartonItem;
import com.cintap.transport.entity.edifact.desadv.shipnotice.DespatchAdviceShipNoticeLineItemCartonItemSerialNumber;
import com.cintap.transport.entity.trans.TransactionLog;
import com.cintap.transport.repository.edifact.desadv.shipnotice.DespatchAdviceShipNoticeHeaderRepository;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SerialNumberService {

	@Autowired
	DespatchAdviceShipNoticeHeaderRepository despatchAdviceShipNoticeHeaderRepository;

	@Autowired
	private FtpFileOperationsService ftpFileOperationsService;

	@Autowired
	private SFtpFileOperationsService sFtpFileOperationsService;

	private static String[] mainHeaderRow = { "Asus Order", "Ref Serial", "Item Seq", "Old ASUS Part", "New ASUS Part",
			"Serial", "Report Date" };

	private static Integer[] mainHeaderRowIndex = { 0, 1, 2, 3, 4, 5, 6 };

	public void generateSerialNumberFile(TransactionLog transactionLog, Connection connectionObj) {

		Optional<DespatchAdviceShipNoticeHeader> optHeader = despatchAdviceShipNoticeHeaderRepository
				.findByBpiLogId(transactionLog.getBpiLogId());
		XSSFWorkbook workbook = null;

		if (optHeader.isPresent()) {
			DespatchAdviceShipNoticeHeader header = optHeader.get();
			String fileType = "xlsx";
			String fileName = connectionObj.getFileName() + "_" + transactionLog.getStpTransId() + "_"
					+ transactionLog.getBpiLogId()+ "_Serial_Number_" + getFileDateFormatInCS() + "." + fileType;
			String targetFilepath = connectionObj.getTargetDirectory() + "" + fileName;
			String localFilePath = TransportConstants.SERIAL_NUMBER + fileName;
			workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Serial Number");

			int rowCount = 1;

			Row headerRow = sheet.createRow(0);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.BLACK.getIndex());
			headerFont.setFontName("Arial");
			// Create a CellStyle with the font
			CellStyle headerCellStyle = workbook.createCellStyle();

			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			// and solid fill pattern produces solid grey cell fill
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
			headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
			headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
			headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
			headerCellStyle.setWrapText(false);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			// Create cells
			for (int i = 0; i < mainHeaderRow.length; i++) {
				Cell cell = headerRow.createCell(mainHeaderRowIndex[i]);
				cell.setCellValue(mainHeaderRow[i]);
				cell.setCellStyle(headerCellStyle);
				sheet.autoSizeColumn(i);
			}

//		Row headerRow1 = sheet.createRow(1);
//
//		Font headerFont1 = workbook.createFont();
//		headerFont1.setBold(false);
//		headerFont1.setFontHeightInPoints((short) 13);
//		headerFont1.setColor(IndexedColors.BLACK.getIndex());
//		headerFont1.setFontName("Arial");
//		// Create a CellStyle with the font
//		CellStyle headerCellStyle1 = workbook.createCellStyle();
//
//		headerCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
//		// and solid fill pattern produces solid grey cell fill
//		headerCellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		headerCellStyle1.setBorderBottom(BorderStyle.MEDIUM);
//		headerCellStyle1.setBorderTop(BorderStyle.MEDIUM);
//		headerCellStyle1.setBorderRight(BorderStyle.MEDIUM);
//		headerCellStyle1.setBorderLeft(BorderStyle.MEDIUM);
//		headerCellStyle1.setWrapText(false);
//		headerCellStyle1.setFont(headerFont1);
//		headerCellStyle1.setAlignment(HorizontalAlignment.CENTER);
//
//		// Create cells
//		for (int i = 0; i < columns.length; i++) {
//			Cell cell = headerRow1.createCell(i);
//			cell.setCellValue(columns[i]);
//			cell.setCellStyle(headerCellStyle1);
//			sheet.autoSizeColumn(i);
//		}

			for (DespatchAdviceShipNoticeLineItem lineItem : header.getLstDesadvShipNoitceLineItem()) {
				for (DespatchAdviceShipNoticeLineItemCarton carton : lineItem.getLstDesadvShipNoitceLineItemCarton()) {
					for (DespatchAdviceShipNoticeLineItemCartonItem cartonItem : carton
							.getLstDesadvShipNoitceLineItemCartonItem()) {
						for (DespatchAdviceShipNoticeLineItemCartonItemSerialNumber serialNumber : cartonItem
								.getLstDesadvShipNoitceLineItemCartonItemSerialNumber()) {
							Row row = sheet.createRow(++rowCount);

							int columnCount = 0;

							// PO Number
							Cell cell = row.createCell(columnCount++);

							cell.setCellValue(carton.getPoNumber());

							columnCount += 3;

							// Part Number
							cell = row.createCell(columnCount++);

							cell.setCellValue(cartonItem.getPartNumber());

							// Serial Number
							cell = row.createCell(columnCount++);

							cell.setCellValue(serialNumber.getSerialNumber());

							// Report Date
							cell = row.createCell(columnCount++);

							cell.setCellValue(TransportCommonUtility.getReportDate());
						}
					}
				}
			}

			try (FileOutputStream outputStream = new FileOutputStream(localFilePath)) {
				workbook.write(outputStream);
				if (CONNECTIONTYPE.FTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					ftpFileOperationsService.uploadLocalFile(connectionObj, localFilePath, targetFilepath);
				} else if (CONNECTIONTYPE.SFTP.getConnection().equalsIgnoreCase(connectionObj.getType())) {
					sFtpFileOperationsService.uploadLocalFile(connectionObj, localFilePath, targetFilepath);
				}
				workbook.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getFileDateFormatInCS() {
		SimpleDateFormat format = new SimpleDateFormat(TransportConstants.DATE_DDMMYYYYHHMMSS);
		format.setTimeZone(TimeZone.getTimeZone("CST"));
		return format.format(new Date());
	}

}
