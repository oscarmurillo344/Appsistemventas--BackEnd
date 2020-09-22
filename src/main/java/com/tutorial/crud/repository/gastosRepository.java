package com.tutorial.crud.repository;

import com.tutorial.crud.entity.gastos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface gastosRepository extends JpaRepository<gastos, Integer> {

    List<gastos> findByTipo(String tipo);

    @Query(value = "SELECT * FROM appasadero.gastos " +
            "where tipo= :tipo and usuario= :user " +
            " and Date(fecha) between Date(:desde) and Date(:hasta)",nativeQuery = true)
    List<gastos>BuscarxFechaxUserxTipo(@Param("desde") Calendar since,
                                       @Param("hasta")Calendar until,
                                       @Param("user")String usuario,
                                       @Param("tipo")String type);

    @Query(value = "SELECT * FROM appasadero.gastos " +
            "where usuario= :user" +
            " and Date(fecha) between Date(:desde) and Date(:hasta)",nativeQuery = true)
    List<gastos>BuscarxFechaxUser(@Param("desde") Calendar since,
                                  @Param("hasta")Calendar until,
                                  @Param("user")String usuario);

    @Query(value = "SELECT * FROM appasadero.gastos " +
            "where Date(fecha) between Date(:desde) and Date(:hasta)",nativeQuery = true)
    List<gastos>BuscarxFecha(@Param("desde") Calendar since,
                             @Param("hasta")Calendar until);




}

