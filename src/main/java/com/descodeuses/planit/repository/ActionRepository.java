package com.descodeuses.planit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descodeuses.planit.entity.ActionEntity;

//                            ... extends JpaRepository<TYPE OBJET, TYPE ID OBJET>
//TYPE OBJET = Action
//TYPE ID OBJET = Type du champ id = Long
@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {

}
