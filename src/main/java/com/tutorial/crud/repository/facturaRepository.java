package com.tutorial.crud.repository;

import com.tutorial.crud.dto.VentasDay;
import com.tutorial.crud.entity.facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface facturaRepository extends JpaRepository<facturacion, Integer> {

    @Query(value = "SELECT case" +
            "     when max(numero_fact) is null then 1" +
            "     else max(numero_fact)" +
            "      end as valor" +
            "     FROM appasadero.facturacion", nativeQuery = true)
    Integer FacturaMaxima();

    @Query(value = "SELECT f.usuario,pr.nombre,pr.precio,sum(f.cantidad) as cantidad " +
            "FROM appasadero.facturacion f, appasadero.rel_fact_product pf,appasadero.producto pr " +
            " where pf.fk_product=pr.id and pf.fk_fact=f.id and f.usuario= :user " +
            " and day(f.registro_date)=day(now()) " +
            " group by f.usuario,pr.nombre,pr.precio " +
            " order by pr.nombre;", nativeQuery = true)
    List<VentasDay> TotalDay(@Param("user") String usuario);

    @Query(value = "SELECT f.usuario,pr.nombre,pr.precio,sum(f.cantidad) as cantidad " +
            "FROM appasadero.facturacion f, appasadero.rel_fact_product pf,appasadero.producto pr " +
            " where pf.fk_product=pr.id and pf.fk_fact=f.id and f.usuario= :user and " +
            " date(f.registro_date) between date(:dateFirst) and date(:dateSecond) " +
            " group by f.usuario,pr.nombre,pr.precio " +
            " order by pr.nombre;",nativeQuery = true)
    List<VentasDay> TotalFechas(@Param("user") String usuario,
                                @Param("dateFirst") Date dateF,
                                @Param("dateSecond") Date dateS);

}
