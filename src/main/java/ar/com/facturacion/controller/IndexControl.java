package ar.com.facturacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ar.com.facturacion.repositorio.ClienteRepositorio;
import ar.com.facturacion.repositorio.EmpresaRepositorio;
import ar.com.facturacion.repositorio.ProductoRepositorio;
import ar.com.facturacion.repositorio.EncabezadoRepositorio;
import ar.com.facturacion.repositorio.ItemRepositorio;
import ar.com.facturacion.repositorio.PieRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexControl {

    @RequestMapping("/")
    public String index(Model modelo) {
        modelo.addAttribute("mensaje","hola desde thymeleaf");
        return "/index";
    }
}