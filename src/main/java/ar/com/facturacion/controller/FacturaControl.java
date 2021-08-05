package ar.com.facturacion.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.lang.Double;

import ar.com.facturacion.dominio.*;
import ar.com.facturacion.repositorio.*;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/facturas")
public class FacturaControl {
    @Autowired
    private EncabezadoRepositorio RepoEncabezado;
    @Autowired
    private ItemRepositorio RepoItem;
    @Autowired
    private ProductoRepositorio RepoProducto;
    @Autowired
    private PieRepositorio RepoPie;
    @Autowired
    private ClienteRepositorio RepoCliente;
    @Autowired
    private EmpresaRepositorio RepoEmpresa;
    private static Integer currentPage = 1;
    private static Integer pageSize = 5;


    @GetMapping("/index.html")
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Page<Encabezado> dataPageEnca = RepoEncabezado.findAllByAnulado(0, PageRequest.of(currentPage - 1, pageSize));
        //Page<Pie> dataPagePie = RepoPie.findAllByEncabezado(dataPageEnca.getContent().get(), PageRequest.of(currentPage - 1, pageSize));
        //Encabezado enca = new Encabezado();
        //BigDecimal total = enca.getPie().getTotal();
        //System.out.println("Precio total: %d" + total);
        int totalPages = dataPageEnca.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("titulo", "Listado de Facturas");
        model.addAttribute("data", dataPageEnca);
        //model.addAttribute("dataPie", dataPagePie);
        return "facturas/index";
    }
    @GetMapping("/facturas_anuladas.html")
    public String facturas_anuladas(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        Page<Encabezado> dataPageEnca = RepoEncabezado.findAllByAnulado(1, PageRequest.of(currentPage - 1, pageSize));
        Page<Pie> dataPagePie = RepoPie.findAll(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = dataPageEnca.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("titulo", "Lista de Facturas anuladas");
        model.addAttribute("data", dataPageEnca);
        model.addAttribute("dataPie", dataPagePie);
        return "facturas/facturas_anuladas";
    }

    @GetMapping("/infoFactura.html") /*Pasaje de ID (u otro parámetro) por medio del href, ¿cómo se hace?*/
    public String infoFactura(@RequestParam("id") Long id, Model model) {
        // id = Long.valueOf(1);
        Encabezado encabezado = RepoEncabezado.findById(id).get();
        Page<Item> item = RepoItem.findByEncabezado(encabezado, PageRequest.of(currentPage - 1, pageSize));
        Pie pie = RepoPie.findById(id).get();

        model.addAttribute("dataEnca", encabezado);
        model.addAttribute("dataItem", item);
        model.addAttribute("dataPie", pie);
        /*int totalPages = dataPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("data", dataPage); */
        model.addAttribute("titulo", "Contenido de Factura");

        return "facturas/infoFactura";
    }

    //esta función debería guardar la factura nueva en la base de datos
    @PostMapping(value = "/registrar.html")
    public String registroPost(@Valid Factura factura, Errors errors, Model model){
        if (errors.hasErrors()){
            return "facturas/facturar_nueva";
        }
        model.addAttribute("factura",new Factura());
        model.addAttribute("facturaInfo",factura);
        if(factura.getEncabezado().getId()==null){
            RepoEncabezado.save(factura.getEncabezado());
            //RepoItem.save(factura.getItems()); //save no puede guardar una lista?
            RepoPie.save(factura.getPie());
        }
        return "facturas/facturar_nueva";
    }
    //Boton en la lista de cliente que permita generar una factura con dicho cliente
    @GetMapping("/facturar.html") //lleva a la página con el formulario para facturar
    public String facturarCliente(@RequestParam("id") Long id, Model model){
        Cliente cliente = RepoCliente.findById(id).get();
        Empresa empresa = RepoEmpresa.findById((long) 1).get();
        ArrayList<Producto> productos = RepoProducto.findByEstado(1);
        Item item = new Item();
        model.addAttribute("cliente", cliente);
        model.addAttribute("empresa", empresa);
        model.addAttribute("productos", productos);
        model.addAttribute("item", item);
        return "/facturas/facturar_nueva.html";
    }

    //List<Producto> products sirve para cuando el usuario refresca la página al añadir un nuevo item a la lista,
    @PostMapping("/agregar.html") //regarga la página con el formulario para facturar
    public String agregarItem(@Valid Item item, Model model, BindingResult bindingResult, @RequestParam(required = false) List<Item> items, Long idCliente){
        Cliente cliente = RepoCliente.findById(idCliente).get();
        Empresa empresa = RepoEmpresa.findById((long) 1).get();
        ArrayList<Producto> productos = RepoProducto.findByEstado(1);

        List<Item> listaItems = items;
        listaItems.add(item);
        //si están seteadas las variables de products y cantidades debe ir creando los items
        /*if (!product.toString().isEmpty() && cantidad!=null){
            BigDecimal subTotal = cantidad * precio;
            Item newItem = new Item(product, cantidad);
            listaItems.add(newItem);
        }*/
        if (!listaItems.isEmpty()){
            model.addAttribute("items", listaItems);
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("empresa", empresa);
        model.addAttribute("productos", productos);
        return "/facturas/facturar_nueva.html";
    }


}
