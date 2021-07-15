package ar.com.facturacion.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import ar.com.facturacion.dominio.Producto;
import ar.com.facturacion.repositorio.ProductoRepositorio;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
@Controller
@RequestMapping("/productos")
public class ProductoControl {
	
	@Autowired
	private ProductoRepositorio repository;
	private static Integer currentPage = 1;
	private static Integer pageSize = 5;
	
	@GetMapping("/index.html")
	public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		Page<Producto> dataPage = repository.findAllByEstado(1, PageRequest.of(currentPage - 1, pageSize));
		int totalPages = dataPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("titulo", "Listado de Productos");
		model.addAttribute("data", dataPage);
		return "productos/index";
	}

    /*@GetMapping(value = "/registar")
    public String registroGet(Model model){
	    model.addAttribute("producto",new Producto());
	    return "productos/registro_prod";
    }*/

    @RequestMapping(value = "/registrar.html")
    public String registroPost(@Valid Producto producto, BindingResult bindingResult, Model model){
	    if (!bindingResult.hasErrors()){
			model.addAttribute("producto", new Producto());
			model.addAttribute("productoInfo",producto);
			System.out.println(producto.getCodigo()+" "+producto.getNombre()+" "+producto.getDescripcion()+" "+producto.getId()+" "+producto.getEstado());
			if(producto.getId()==null){
				repository.save(producto);
			}
	    	return "redirect:/productos/index.html";
        }
		return "productos/registro_prod.html";
    }

    //boton editar
	@PostMapping(value = "/modificar.html") //este manda los datos a la base de datos
	public String modificarPost(@Valid Producto producto, BindingResult bindingResult){
		if (!bindingResult.hasErrors()){
			repository.save(producto);
			return "redirect:/productos/index.html";
		}
		return "productos/modificar_producto";
	}
	@GetMapping("/modificar.html") //este lleva a la página con el formulario para modificar
	public String showUpdateForm(@RequestParam("id") Long id, Model model) {
		Producto producto = repository.findById(id).get();
		model.addAttribute("producto", producto);
		return "productos/modificar_producto";
	}

	//mostrar anulados
	@GetMapping("/lista_anulados.html")
	public String anulados(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		Page<Producto> dataPage = repository.findAllByEstado(0, PageRequest.of(currentPage - 1, pageSize));
		int totalPages = dataPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("titulo", "Listado de Productos Anulados");
		model.addAttribute("data", dataPage);
		return "productos/lista_anulados";
	}

	//botones habilitar y des-habilitar
	@GetMapping("/deshabilitar")
	public String deshabilitar(@RequestParam("id") Long id, Model model){ //recibe un id de producto
		Producto producto = repository.findById(id).get(); //busca el producto con ese id
		model.addAttribute("producto", producto); //asigna los datos de dicho producto a un objeto
		producto.setEstado(0); //vuelvo a setear el estado y lo hago = 0 = anulado/deshabilitado
		repository.save(producto); //guarda/actualiza el producto
		return"redirect:/productos/index.html"; //redirige/recarga el index (tabla de productos habilitados)
	}
	@GetMapping("/habilitar")
	public String habilitar(@RequestParam("id") Long id, Model model){ //recibe un id de producto
		Producto producto = repository.findById(id).get(); //busca el producto con ese id
		model.addAttribute("producto", producto); //asigna los datos de dicho producto a un objeto
		producto.setEstado(1); //vuelvo a setear el estado y lo hago = 1 = habilitado
		repository.save(producto); //guarda/actualiza el producto
		return"redirect:/productos/lista_anulados.html"; //redirige/recarga la lista de anulados (tabla de productos deshabilitados)
	}
}
