package com.example.cms.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Publish;

public interface PublishRepository extends JpaRepository<Publish, Integer> {

}
