package es.codeurjc.backend.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.backend.Entitys.ExamStudent;
import es.codeurjc.backend.Entitys.Student;
import es.codeurjc.backend.repository.SubjectRepository;
import es.codeurjc.backend.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChartController {

    @Autowired
    StudentService studentRepository;
    @Autowired
    SubjectRepository subjectsList;

    @GetMapping("/chart/exams")
    public ResponseEntity<byte[]> generateChart(HttpServletRequest request) {
        byte[] chartImage = createChartImage(request); // Método que genera el gráfico y devuelve la imagen como bytes

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
    }

    private byte[] createChartImage(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Student student = studentRepository.getStudentByEmail(principal.getName());
        List<ExamStudent> exams = student.getExamStudents();

        int numAprobados = 0;
        int numSuspensos = 0;

        // Iteramos sobre la lista de exámenes para contar el número de aprobados y
        // suspendidos
        for (ExamStudent exam : exams) {
            double mark = exam.getMark();
            if (mark >= 5) {
                numAprobados++;
            } else {
                numSuspensos++;
            }
        }

        int totalExamenes = numAprobados + numSuspensos;

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Agregamos el número de exámenes aprobados como una serie
        dataset.addValue(numAprobados, "Aprobados", "");

        // Agregamos el número de exámenes suspendidos como otra serie
        dataset.addValue(numSuspensos, "Suspensos", "");

        JFreeChart chart = ChartFactory.createBarChart(
                "Passed Exams",
                "",
                "Number of Exams",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, totalExamenes);

        // Convertir el gráfico en una imagen byte[]
        byte[] chartImageBytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(outputStream, chart, 400, 400);
            chartImageBytes = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chartImageBytes;
    }

    @GetMapping("/chart/subjects")
    public ResponseEntity<byte[]> generateChart2(HttpServletRequest request) {
        byte[] chartImage = createChartImage2(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
    }

    public byte[] createChartImage2(HttpServletRequest request) {
        // QUERY
        List<Object[]> list = subjectsList.countSubjectsByGender();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Object[] data : list) {
            String gender = (String) data[0];
            long count = (long) data[1];
            dataset.addValue(count, "Number of subjects", gender);
        }

        // Crear un gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Subjects by Gender",
                "Gender",
                "Número de Exámenes",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, Color.decode("#f48840"));
        plot.setRenderer(renderer);

        // Convertir el gráfico en una imagen byte[]
        byte[] chartImageBytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(outputStream, chart, 1000, 400);
            chartImageBytes = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chartImageBytes;
    }

}