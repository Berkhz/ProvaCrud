package com.example.demo.repository;

import com.example.demo.entity.Emprego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpregoRepository extends JpaRepository<Emprego, Long> {
}