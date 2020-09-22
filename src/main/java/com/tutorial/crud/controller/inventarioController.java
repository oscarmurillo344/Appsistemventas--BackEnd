package com.tutorial.crud.controller;


import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.dto.facturacionDto;
import com.tutorial.crud.dto.inventarioDto;
import com.tutorial.crud.entity.facturacion;
import com.tutorial.crud.entity.inventario;
import com.tutorial.crud.service.ProductoService;
import com.tutorial.crud.service.inventarioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventario")
@CrossOrigin(origins = "*")
public class inventarioController {

    @Autowired
    inventarioService inventarioservice;
    @Autowired
    ProductoService productoService;


    @GetMapping("/lista")
    public ResponseEntity<ArrayList<inventario>> list(){
        List<inventario> list = inventarioservice.listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addInventario")
    public ResponseEntity<?> create(@RequestBody inventarioDto invenDto){
        if(invenDto.getCantidad()<0)
            return new ResponseEntity(new Mensaje("cantidad debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(invenDto.getProductoId().getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(invenDto.getProductoId().getPrecio()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(invenDto.getProductoId().getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
            inventario inven=new inventario(
                    Calendar.getInstance(),invenDto.getProductoId(),
                    invenDto.getCantidad(),invenDto.getCantidad());
            inventarioservice.save(inven);
        return new ResponseEntity(new Mensaje("Ingreso Exitoso"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateinventario/{idIven}")
    public ResponseEntity<?> Update(@PathVariable("idIven")int id,@RequestBody inventarioDto invenDto){
        if(invenDto.getCantidad()<0)
            return new ResponseEntity(new Mensaje("cantidad debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        inventario inven=inventarioservice.getOne(id).get();
        inven.setFecha(Calendar.getInstance());
        inven.setCantidad(invenDto.getCantidad());
        inven.setCantidadExist(invenDto.getCantidad());
        inventarioservice.save(inven);
        return new ResponseEntity(new Mensaje("Actualizacion Exitosa"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!inventarioservice.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        inventarioservice.delete(id);
        return new ResponseEntity(new Mensaje("producto e inventario eliminado"), HttpStatus.OK);
    }
}
