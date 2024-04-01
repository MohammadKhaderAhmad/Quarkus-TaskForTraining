package org.acme.forCitizen;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.forCitizen.Citizen;
import org.acme.forCitizen.CitizenDTO;
import org.acme.forCitizen.CitizenRepository;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Path("/citizen")
public class CitizenResource {
    @Inject
    CitizenRepository citizenRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCitizens() {
        return Response.ok(citizenRepository.listAll()).build();
    }



    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCitizen(CitizenDTO citizenDTO){
        Citizen citizen = new Citizen();
        citizen.setName(citizenDTO.getName());
        citizen.setGender(citizenDTO.getGender());
        citizenRepository.persist(citizen);
        if(citizenRepository.isPersistent(citizen)) {
            return Response.created(URI.create("/citizen/" + citizen.id)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }



    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCitizen(@PathParam("id") Long id){
        Citizen citizen = citizenRepository.findById(id);
        if (citizen == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(citizen).build();
    }



    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCitizen(@PathParam("id") Long id, CitizenDTO updateCitizenDTO) {
        Optional<Citizen> optionalCitizen = citizenRepository.findByIdOptional(id);

        if (!optionalCitizen.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Citizen with id " + id + " not found.").build();
        }

        Citizen dbCitizen = optionalCitizen.get();

        if (Objects.nonNull(updateCitizenDTO.getName())) {
            dbCitizen.setName(updateCitizenDTO.getName());
        }

        if (Objects.nonNull(updateCitizenDTO.getGender())) {
            dbCitizen.setGender(updateCitizenDTO.getGender());
        }
        citizenRepository.persist(dbCitizen);

        return Response.ok(dbCitizen).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCitizen(@PathParam("id") Long id) {
        boolean deleted = citizenRepository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



}


