package ge.kuznetsov.shortestRoute.service;

import ge.kuznetsov.shortestRoute.domain.Planet;
import ge.kuznetsov.shortestRoute.domain.Route;
import ge.kuznetsov.shortestRoute.exception.PlanetNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelUploadService {

    private final PlanetService planetService;

    @Autowired
    public ExcelUploadService(PlanetService planetService) {
        this.planetService = planetService;
    }


    public static boolean isValidExcelFile(@NotNull MultipartFile file) {
        return Objects.equals(file.getContentType()
                , "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public XSSFWorkbook readWorkbookFromExcel(InputStream inputStream) {
        XSSFWorkbook workbook = null;
        try (XSSFWorkbook tempWorkbook = new XSSFWorkbook(inputStream)) {
            workbook = tempWorkbook;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return workbook;
    }


    public List<Planet> getPlanetsDataFromExcel(@NotNull XSSFWorkbook workbook) {
        List<Planet> planets = new ArrayList<>();
        XSSFSheet enumSheet = workbook.getSheet("Planet Names");
        int rowIndex = 0;

        for (Row row : enumSheet) {
            Planet planet = new Planet();
//            if header, skip
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            int cellIndex = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cellIndex) {
                    case 0 -> planet.setPlanetNodeName(cell.getStringCellValue());
                    case 1 -> planet.setPlanetName(cell.getStringCellValue());
                    default -> {
                    }
                }
                cellIndex++;
            }
            planets.add(planet);
        }
        return planets;
    }


    public List<Route> getRouteDataFromExcel(@NotNull XSSFWorkbook workbook) {
        List<Route> routes = new ArrayList<>();
        XSSFSheet routeSheet = workbook.getSheet("Routes");
//        XSSFSheet trafficSheet = workbook.getSheet("Traffic");
        int rowIndex = 0;


        for (Row row : routeSheet) {
            Route route = new Route();
//            if header, skip
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            int cellIndex = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cellIndex) {
                    case 1:
                        try {
                            if (planetService.findPlanetByNodeName(cell.getStringCellValue()).isPresent()) {
                                route.setSourceId(
                                        planetService.findPlanetByNodeName(cell.getStringCellValue())
                                                .get()
                                                .getPlanetId()
                                );
                            }
                        } catch (PlanetNotFoundException e) {
                            e.getStackTrace();
                        }

                        break;
                    case 2:
                        try {
                            if (planetService.findPlanetByNodeName(cell.getStringCellValue()).isPresent()) {
                                try {
                                    route.setTargetId(
                                            planetService.findPlanetByNodeName(cell.getStringCellValue())
                                                    .get()
                                                    .getPlanetId()
                                    );
                                } catch (PlanetNotFoundException e) {
                                    e.getStackTrace();
                                }
                            }
                        } catch (PlanetNotFoundException e) {
                            e.getStackTrace();
                        }
                        break;
                    case 3:
                        route.setDistance(cell.getNumericCellValue());
                        break;
                    case 0:
                        break;
                    default: {
                    }
                }
                cellIndex++;
            }
            routes.add(route);
        }

        return routes;
    }

    public List<Route> getTrafficDataFromExcel(@NotNull XSSFWorkbook workbook) {
        List<Route> routes = new ArrayList<>();
        XSSFSheet trafficSheet = workbook.getSheet("Traffic");
        int rowIndex = 0;
        for (Row row : trafficSheet) {
            Route route = new Route();
//            if header, skip
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            Iterator<Cell> cellIterator = row.cellIterator();
            int cellIndex = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cellIndex) {

                    case 1:
                        try {
                            if (planetService.findPlanetByNodeName(cell.getStringCellValue()).isPresent()) {
                                route.setSourceId(
                                        planetService.findPlanetByNodeName(cell.getStringCellValue())
                                                .get()
                                                .getPlanetId()
                                );
                            }
                        } catch (PlanetNotFoundException e) {
                            e.getStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            if (planetService.findPlanetByNodeName(cell.getStringCellValue()).isPresent()) {
                                route.setTargetId(
                                        planetService.findPlanetByNodeName(cell.getStringCellValue())
                                                .get()
                                                .getPlanetId()
                                );
                            }
                        } catch (PlanetNotFoundException e) {
                            e.getStackTrace();
                        }
                        break;
                    case 3:
                        route.setTrafficDelay(cell.getNumericCellValue());
                        break;
                    case 0:
                        break;
                    default: {
                    }
                }
                cellIndex++;
            }
            routes.add(route);
        }
        return routes;
    }


}
