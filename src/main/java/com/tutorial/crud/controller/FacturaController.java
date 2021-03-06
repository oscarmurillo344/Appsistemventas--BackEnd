package com.tutorial.crud.controller;

import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.BetweenFechas;
import com.tutorial.crud.dto.VentasDay;
import com.tutorial.crud.dto.facturaDto;
import com.tutorial.crud.entity.diaPollos;
import com.tutorial.crud.entity.facturacion;
import com.tutorial.crud.entity.inventario;
import com.tutorial.crud.service.FacturaService;
import com.tutorial.crud.service.diaPolloService;
import com.tutorial.crud.service.inventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/factura")
@CrossOrigin(origins = {"https://asaderoweb.herokuapp.com","http://192.168.100.115:4200"})
public class FacturaController {

    @Autowired
    FacturaService facturaservice;
    @Autowired
    inventarioService inventarioservice;
    @Autowired
    diaPolloService diaservice;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista/{numero}")
    public ResponseEntity<List<facturacion>> list(@PathVariable("numero") int numero){
        try{
            if (!facturaservice.existsByNumero(numero))
                return new ResponseEntity(new Mensaje("factura no existente"), HttpStatus.NOT_FOUND);
            List<facturacion> list = facturaservice.listaNumero(numero);
            return new ResponseEntity(list, HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        try{
            int presa=0,cantidad=0,Totalpresa=0;
            if(!facturaservice.existsByNumero(id))
                return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
            List<facturacion> Lfacturas=facturaservice.eliminarFact(id);
            for(facturacion factura : Lfacturas){
                presa+=factura.getProductoId().getPresa();
                cantidad=factura.getCantidad();
            }
            Totalpresa=presa*cantidad;
            diaPollos listaPollo= diaservice.Listar(1);
            if(Totalpresa>=8){
                while(Totalpresa>=8){
                    Totalpresa-=8;
                    listaPollo.setPollo(listaPollo.getPollo()+1);
                }
                listaPollo.setPresa(listaPollo.getPresa()+Totalpresa);
            }else listaPollo.setPresa(listaPollo.getPresa()+Totalpresa);
            diaservice.Guardar(listaPollo);
            return new ResponseEntity(new Mensaje("factura eliminada"), HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/facturar")
    public ResponseEntity<?> create(@RequestBody facturaDto factDto){
        int count=0,count2=0;
    try{
        if(factDto.getCantidad()<0)
            return new ResponseEntity(new Mensaje("cantidad debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        facturacion fact = new facturacion(factDto.getNumeroFact(), factDto.getUsuarioId()
                , factDto.getDatenow(),new Date(),factDto.getDia()
                ,factDto.getProductoId(),factDto.getCantidad());
        facturaservice.save(fact);

        inventario inventory=inventarioservice.ActulizarProduct(factDto.getProductoId());
        count=inventory.getCantidadExist()- factDto.getCantidad();
        inventory.setCantidadExist(count);
        inventarioservice.save(inventory);

        if(factDto.getExtras() != ""){
            String[] list=factDto.getExtras().split(",");
            for (int i=0;i < list.length ;i++){
                inventario invent=inventarioservice.getOne(Integer.parseInt(list[i])).get();
                count2=invent.getCantidadExist()- factDto.getCantidad();
                invent.setCantidadExist(count2);
                inventarioservice.save(invent);
            }
        }
        return new ResponseEntity(new Mensaje("Venta Exitosa"), HttpStatus.OK);
    }catch (DataAccessException ex){
        return new ResponseEntity(new Mensaje
                ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }


    @GetMapping("/numero")
    public ResponseEntity<?> Numerofactura(){
        try{
        Integer listaN=facturaservice.MaximoValor();
        return new ResponseEntity(listaN, HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/totalDay/{usu}")
    public ResponseEntity<?> Totalday(@PathVariable("usu") String usu){
        try{
        List<VentasDay> l=facturaservice.TotalDia(usu);
        return new ResponseEntity(l, HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/totalfecha")
    public ResponseEntity<?> totalFecha(@RequestBody BetweenFechas fec)
    {
        try{
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);

        List<VentasDay> listar=facturaservice.TotalFechas(fec.getFechaFirst(),fec.getFechaSecond());
        return new ResponseEntity(listar,HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/totalfechaUser")
    public ResponseEntity<?> totalFechaUser(@RequestBody BetweenFechas fec){
        try{
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);
        if(fec.getUsuario().isEmpty())
            return new ResponseEntity(new Mensaje("No existe usuario"),HttpStatus.BAD_REQUEST);

        List<VentasDay> lis=facturaservice.TotalFechasUser
                (fec.getUsuario(),fec.getFechaFirst(),fec.getFechaSecond());
        return new ResponseEntity(lis,HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/totalfechauserdia")
    public ResponseEntity<?> totalFechaUserDia(@RequestBody BetweenFechas fec){
        try{
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);
        if(fec.getUsuario().isEmpty())
            return new ResponseEntity(new Mensaje("No existe usuario"),HttpStatus.BAD_REQUEST);
        List<VentasDay> listar=facturaservice.TotalFechasUserDia
        (fec.getUsuario(),fec.getFechaFirst(),fec.getFechaSecond(),fec.getDia());
        return new ResponseEntity(listar,HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/totalfechadia")
    public ResponseEntity<?> totalFechaDia(@RequestBody BetweenFechas fec){
        try{
        if(fec.getFechaFirst() == null )
            return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);
        if(fec.getUsuario().isEmpty())
            return new ResponseEntity(new Mensaje("No existe usuario"),HttpStatus.BAD_REQUEST);
        List<VentasDay> listar=facturaservice.TotalFechasDia
                (fec.getFechaFirst(),fec.getFechaSecond(),fec.getDia());
        return new ResponseEntity(listar,HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/totalfechasComp")
    public ResponseEntity<?> totalFechaComp(@RequestBody BetweenFechas fec){
        try{
            if(fec.getFechaFirst() == null || fec.getFechaSecond() == null )
                return new ResponseEntity(new Mensaje("No existe fecha"),HttpStatus.BAD_REQUEST);
            List<VentasDay> listar=facturaservice.TotalFechasComplete
                    (fec.getFechaFirst(),fec.getFechaSecond());
            return new ResponseEntity(listar,HttpStatus.OK);
        }catch (DataAccessException ex){
            return new ResponseEntity(new Mensaje
                    ("Error: ".concat(ex.getMessage()).concat(", "+ex.getMostSpecificCause().getMessage())),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
