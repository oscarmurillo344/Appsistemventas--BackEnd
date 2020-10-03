package com.tutorial.crud.service;

import com.tutorial.crud.dto.VentasDay;
import com.tutorial.crud.entity.facturacion;
import com.tutorial.crud.repository.facturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {

    @Autowired
    facturaRepository facturarepository;

    public void save (facturacion fact){  facturarepository.save(fact);}

    public void delete(int id){
        facturarepository.deleteById(id);
    }

    public boolean existsById(int id){
        return facturarepository.existsById(id);
    }

    public boolean existsByNumero(int id){
        return facturarepository.existsByNumeroFact(id);
    }

    public List<facturacion> list(){
        return facturarepository.findAll();
    }

    public Optional<facturacion> getOne(int id){
        return facturarepository.findById(id);
    }

    public Integer MaximoValor(){ return facturarepository.FacturaMaxima(); }

    public List<VentasDay> TotalDia(String usuario){ return facturarepository.TotalDay(usuario);}

    public List<VentasDay> TotalFechas(String usua, Date dateF, Date dateS)
    { return facturarepository.TotalFechas(usua,dateF,dateS);}

    public List<facturacion> listaNumero(int id){return facturarepository.findByNumeroFact(id);}

    public long eliminarFact(int numero){return facturarepository.deleteByNumeroFact(numero);}

}
