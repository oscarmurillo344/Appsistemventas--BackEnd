package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.facturacionDto;
import com.tutorial.crud.dto.BetweenFechas;
import com.tutorial.crud.dto.VentasDay;
import com.tutorial.crud.entity.facturacion;
import com.tutorial.crud.entity.inventario;
import com.tutorial.crud.service.FacturaService;
import com.tutorial.crud.service.inventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/factura")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    FacturaService facturaservice;
    @Autowired
    inventarioService inventarioservice;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista/{numero}")
    public ResponseEntity<List<facturacion>> list(@PathVariable("numero") int numero){
        if (!facturaservice.existsByNumero(numero))
            return new ResponseEntity(new Mensaje("transacción no existente"), HttpStatus.OK);
        List<facturacion> list = facturaservice.listaNumero(numero);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!facturaservice.existsByNumero(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        facturaservice.eliminarFact(id);
        return new ResponseEntity(new Mensaje("factura eliminada"), HttpStatus.OK);
    }

    @PostMapping("/facturar")
    public ResponseEntity<?> create(@RequestBody facturacionDto factDto){
        int count=0,count2=0;
        if(factDto.getCantidad()<0)
            return new ResponseEntity(new Mensaje("cantidad debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        facturacion factura = new facturacion(factDto.getNumeroFact(), factDto.getUsuarioId()
                , factDto.getFecha(), factDto.getProductoId(),factDto.getCantidad());
        facturaservice.save(factura);
        inventario inventar=inventarioservice.ActulizarProduct(factDto.getProductoId());
        count=inventar.getCantidadExist()- factDto.getCantidad();
        inventar.setCantidadExist(count);

        if(factDto.getExtras()!= null){
            String[] lista=factDto.getExtras().split(",");
            for (int i=0;i < lista.length ;i++){
                inventario inven=inventarioservice.getOne(Integer.parseInt(lista[i])).get();
                count2=inven.getCantidadExist()- factDto.getCantidad();
                inven.setCantidadExist(count2);
                inventarioservice.save(inven);
            }
        }


        return new ResponseEntity(new Mensaje("Venta Exitosa"), HttpStatus.OK);
    }

    @GetMapping("/numero")
    public ResponseEntity<Integer> Numerofactura(){
        Integer listaN=facturaservice.MaximoValor();
        return new ResponseEntity(listaN, HttpStatus.OK);
    }

    @GetMapping("/totalDay/{usu}")
    public ResponseEntity<List<VentasDay>> Totalday(@PathVariable("usu") String usu){
        List<VentasDay> l=facturaservice.TotalDia(usu);
        return new ResponseEntity(l, HttpStatus.OK);
    }

    @PostMapping("/totalfechaUser")
    public ResponseEntity<List<VentasDay>> totalFechaUser(@RequestBody BetweenFechas fec){
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);
        if(fec.getUsuario().isEmpty())
            return new ResponseEntity(new Mensaje("No existe usuario"),HttpStatus.BAD_REQUEST);

        List<VentasDay> listar=facturaservice.TotalFechasUser(fec.getUsuario(),fec.getFechaFirst(),fec.getFechaSecond());
        return new ResponseEntity(listar,HttpStatus.OK);
    }

    @PostMapping("/totalfecha")
    public ResponseEntity<List<VentasDay>> totalFecha(@RequestBody BetweenFechas fec)
    {
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);

        List<VentasDay> listar=facturaservice.TotalFechas(fec.getFechaFirst(),fec.getFechaSecond());
        return new ResponseEntity(listar,HttpStatus.OK);
    }



}
