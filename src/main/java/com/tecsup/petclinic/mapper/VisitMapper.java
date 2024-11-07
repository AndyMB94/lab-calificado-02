package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.entities.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "petId", target = "pet.id")
    Visit toVisit(VisitTO visitTO);

    @Mapping(source = "pet.id", target = "petId")
    VisitTO toVisitTO(Visit visit);

    List<VisitTO> toVisitTOList(List<Visit> visitList);

    List<Visit> toVisitList(List<VisitTO> visitTOList);
}