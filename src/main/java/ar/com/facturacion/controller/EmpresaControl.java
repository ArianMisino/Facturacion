package ar.com.facturacion.controller;

import ar.com.facturacion.dominio.Empresa;
import ar.com.facturacion.repositorio.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/empresas")
public class EmpresaControl {

    @Autowired
    private EmpresaRepositorio repository;
    private static Integer currentPage = 1;
    private static Integer pageSize = 5;

    @GetMapping("/index.html")
    public String index_empresa(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Page<Empresa> dataPage = repository.findAll(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = dataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("titulo", "Lista de Empresas");
        model.addAttribute("data", dataPage);
        return "empresas/index";
    }

    /*@GetMapping(value = "/registar")
    public String registroGet(Model model){
	    model.addAttribute("empresa",new Empresa());
	    return "clientes/registro_empresa";
    }
*/
    @PostMapping(value = "/modificar.html")
    public String modificarPost(@Valid Empresa empresa, BindingResult bindingResult, Model model){
        if (!bindingResult.hasErrors()){
            repository.save(empresa);
            return "redirect:/empresas/index.html";
        }
        return "empresas/modificar_empresa";
    }

    @GetMapping("/modificar.html")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        Empresa empresa = repository.findById(id).get();
        model.addAttribute("empresa", empresa);
        return "empresas/modificar_empresa";
    }
}
