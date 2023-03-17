package ge.kuznetsov.shortestRoute.service;

import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.domain.Route;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

@Service
public class FileUploadService {

    private final ExcelUploadService excelUploadService;

    @Autowired
    public FileUploadService(ExcelUploadService excelUploadService
            , PlanetService planetService
            , RouteService routeService) {
        this.excelUploadService = excelUploadService;
    }

    public Closeable readFile(MultipartFile file) {
        Closeable content = null;
        if (ExcelUploadService.isValidExcelFile(file)) {
            try {
                content = this.excelUploadService.readWorkbookFromExcel(file.getInputStream());
            } catch (IOException e) {
                throw new IllegalArgumentException("The file extension is not .xslx");
            }
        }
        return content;
    }

    public List<Planet> getPlanetsDataFromExcel(XSSFWorkbook workbook) {
        return excelUploadService.getPlanetsDataFromExcel(workbook);
    }

    public List<Route> getRouteDataFromExcel(XSSFWorkbook workbook) {
        return excelUploadService.getRouteDataFromExcel(workbook);
    }

    public List<Route> getTrafficDataFromExcel(XSSFWorkbook workbook) {
        return excelUploadService.getTrafficDataFromExcel(workbook);
    }




}
