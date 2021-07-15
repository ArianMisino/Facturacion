package ar.com.facturacion.controller;

import ar.com.facturacion.dominio.Cliente;
import ar.com.facturacion.dominio.Producto;
import ar.com.facturacion.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/clientes")

public class ClienteControl {

    @Autowired
    private ClienteRepositorio repository;
    private static Integer currentPage = 1;
    private static Integer pageSize = 5;
    @GetMapping("/index.html")
    public String index_cliente(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Page<Cliente> dataPage = repository.findAllByEstado(1, PageRequest.of(currentPage - 1, pageSize));
        int totalPages = dataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("data", dataPage);
        return "clientes/index";
    }
    @GetMapping("/lista_anulados.html")
    public String clientes_anulados(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Page<Cliente> dataPage = repository.findAllByEstado(0, PageRequest.of(currentPage - 1, pageSize));
        int totalPages = dataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("titulo", "Listado de Clientes anulados");
        model.addAttribute("data", dataPage);
        return "clientes/lista_anulados";
    }

    /*@GetMapping(value = "/registar")
    public String registroGet(Model model){
	    model.addAttribute("cliente",new Cliente());
	    return "clientes/registrocliente";
    }
    */
    @RequestMapping(value = "/registrar")
    public String registroPost(@Valid Cliente cliente, Errors errors, Model model){
        if (errors.hasErrors()){
            return "clientes/registro_cliente";
        }
        model.addAttribute("cliente",new Cliente());
        model.addAttribute("clienteInfo",cliente);
        if(cliente.getId()==null){
            repository.save(cliente);
        }
        return "clientes/registro_cliente";
    }

    //boton editar
    @PostMapping(value = "/editar.html") //este manda los datos a la base de datos
    public String modificarPost(@Valid Cliente cliente, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            repository.save(cliente);
            return "redirect:/clientes/index.html";
        }
        return "clientes/editar_cliente";
    }
    @GetMapping("/editar.html") //este lleva a la página con el formulario para editar
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        Cliente cliente = repository.findById(id).get();
        model.addAttribute("cliente", cliente);
        return "clientes/editar_cliente";
    }

    //botones habilitar y des-habilitar
    @GetMapping("/deshabilitar")
    public String deshabilitar(@RequestParam("id") Long id, Model model){ //recibe un id de cliente
        Cliente cliente = repository.findById(id).get(); //busca el cliente con ese id
        model.addAttribute("cliente", cliente); //asigna los datos de dicho producto a un objeto
        cliente.setEstado(0); //vuelvo a setear el estado y lo hago = 0 = anulado/deshabilitado
        repository.save(cliente); //guarda/actualiza el cliente
        return"redirect:/clientes/index.html"; //redirige/recarga el index (tabla de cliente habilitados)
    }
    @GetMapping("/habilitar")
    public String habilitar(@RequestParam("id") Long id, Model model){ //recibe un id de cliente
        Cliente cliente = repository.findById(id).get(); //busca el cliente con ese id
        model.addAttribute("cliente", cliente); //asigna los datos de dicho cliente a un objeto
        cliente.setEstado(1); //vuelvo a setear el estado y lo hago = 1 = habilitado
        repository.save(cliente); //guarda/actualiza el cliente
        return"redirect:/clientes/lista_anulados.html"; //redirige/recarga la lista de anulados (tabla de clientes deshabilitados)
    }


}
