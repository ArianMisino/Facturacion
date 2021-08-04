/*Actualmente este archivo no está en uso*/

/* Seleccionar etiquetas del DOM */
// $("elemento")
/* Seleccionar id del DOM */
// $("#idelemento")
/* Seleccionar clase del DOM */
// $(".claseelemento")

//esto funcaaaaaaaa
//let precioid = document.getElementById("precio").value;
//console.log(precioid);

function demo(){
    alert("Se ha llamado la funcion JavaScript demo con exito");
}
$(document).ready(function() {
    $('.cantidad').on("keyup change", function() {
        actualizar_totales();
    });
});

//var [[${elsubtotal}]] = 0.00;

void function actualizar1() {
    let precio = document.getElementById("precio");
    let cantidad = document.getElementById("cantidad");
    let subtotal = (precio * cantidad);
    if (subtotal > 0){
        document.getElementById("subtotal").value = subtotal;
    }else{
        document.getElementById("subtotal").value = 0.0;
    }
}

function obtener_totales() {
    let precio = document.getElementsByName("precio");
    let cantidad = document.getElementsByName("cantidad");
    let subtotal = precio * cantidad;
    if (subtotal > 0){
        console.log('el subtotal es ' + subtotal);
    }else {
        console.log('no mi pana, no hay')
    }
}

function actualizar_totales() {
    let sum = 0.0;
    $('#latabla > tbody  > tr').each(function() {
        let precio = parseFloat($(this).find('.precio').val());
        let cantidad = parseFloat($(this).find('.cantidad').val());
        let subtotal = (cantidad * precio);
        if (subtotal > 0) {
            $(this).find('.subtotal').val(subtotal);
            [[${elsubtotal}]] = subtotal;
            document.getElementById("subtotal").value = subtotal;
            sum += subtotal;
            console.log("subtotal: " + subtotal);
            console.log("El subtotal: " + [[${elsubtotal}]]);
            console.log("sum: " + sum);
        } else {
            $(this).find('.subtotal').val("0.00");
        }
    });
    if (sum > 0) $('.totalpedido').val(sum);
    else $('.totalpedido').val("0.00");
}