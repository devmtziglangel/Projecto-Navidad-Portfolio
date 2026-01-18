package com.portfolio.my_portfolio_backend.controller;

import com.portfolio.my_portfolio_backend.dto.ProjectDto;
import com.portfolio.my_portfolio_backend.mapper.ProjectMapper;
import com.portfolio.my_portfolio_backend.service.FileStorageService;
import com.portfolio.my_portfolio_backend.service.IProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final IProjectService projectService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String getAll(Model model) {
        List<ProjectDto> projects = projectService.findAll().stream()
                .map(ProjectMapper::toDto)
                .toList();
        model.addAttribute("projects", projects);
        return "projects/list";
    }


    @GetMapping("/new-project")
    public String showForm(Model model) {
        model.addAttribute("projectDto", new ProjectDto());
        return "projects/form-project"; // o el nombre que hayas elegido
    }


    @PostMapping("/save")
    public String saveProject(@Valid @ModelAttribute("projectDto") ProjectDto projectDto, BindingResult result,
                              @RequestParam("file") MultipartFile file) {

        // 1. VALIDACIÓN DE ARCHIVO
        // Comprobamos si el usuario seleccionó una imagen
        if (file.isEmpty()) {
            result.rejectValue("imageUrl", "file.required", "La imagen del proyecto es obligatoria.");
        }

        // Si hay errores de validación (campos vacíos, etc.), volvemos al formulario
        if (result.hasErrors()) {
            return "projects/form-project";
        }

        try {
            /*
             * EXPLICACIÓN DEL ERROR FORZADO (APUNTES):
             * Antes teníamos: throw new IOException("Error de pruebna.");
             * Esto se usa solo para testear que la página de error personalizada funciona.
             * Para que el código funcione de verdad, esa línea DEBE estar borrada o comentada.
             */
            // throw new IOException("Error de pruebna."); // <--- LÍNEA DESACTIVADA

            // 2. LÓGICA DE GUARDADO REAL
            // Guardamos el archivo físicamente y obtenemos el nombre/ruta
            String imageUrl = fileStorageService.storeFile(file);
            projectDto.setImageUrl(imageUrl);

            // Convertimos el DTO a Entidad para la base de datos
            // Usamos la ruta completa si hay conflictos de nombres
            com.portfolio.my_portfolio_backend.model.Project project = ProjectMapper.toEntity(projectDto);

            // Persistencia en base de datos
            projectService.save(project);

            return "redirect:/projects";

        } catch (IOException e) {
            // Si algo falla en el guardado del archivo, entonces sí mostramos el error
            return "error-page";
        }
    }

}
