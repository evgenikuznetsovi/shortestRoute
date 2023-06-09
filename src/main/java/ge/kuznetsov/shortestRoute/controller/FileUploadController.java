package ge.kuznetsov.shortestRoute.controller;

import ge.kuznetsov.shortestRoute.domain.Route;
import ge.kuznetsov.shortestRoute.exception.RouteNotFoundException;
import ge.kuznetsov.shortestRoute.service.FileUploadService;
import ge.kuznetsov.shortestRoute.service.PlanetService;
import ge.kuznetsov.shortestRoute.service.RouteService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("data")
public class FileUploadController {
    private final PlanetService planetService;
    private final RouteService routeService;
    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(PlanetService planetService
            , RouteService routeService
            , FileUploadService fileUploadService) {
        this.planetService = planetService;
        this.routeService = routeService;
        this.fileUploadService = fileUploadService;
    }


    @RequestMapping(value = "upload-galaxy-data", method = RequestMethod.POST
            , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadData(@RequestPart MultipartFile document) {
        Closeable content = fileUploadService.readFile(document);

        try {
            planetService.savePlanetsToDatabase(
                    fileUploadService.getPlanetsDataFromExcel((XSSFWorkbook) content)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            routeService.saveRoutesToDatabase(
                    fileUploadService.getRouteDataFromExcel((XSSFWorkbook) content)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Route> routeTraffics = fileUploadService.getTrafficDataFromExcel((XSSFWorkbook) content);

        routeTraffics
                .forEach(route -> {
                            try {

                                if (routeService.findRouteByEndIds(route.getSourceId(), route.getTargetId()).isPresent()) {
                                    Route tempRoute = routeService.findRouteByEndIds(route.getSourceId(), route.getTargetId())
                                            .get();
                                    routeService.updateRoutTraffic(tempRoute.getRouteId(), route.getTrafficDelay());
                                }
                            } catch (RouteNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                );


        return ResponseEntity.ok(Map.of("Message"
                , "All data was uploaded successfully and saved to corresponding databases"));
    }
}
