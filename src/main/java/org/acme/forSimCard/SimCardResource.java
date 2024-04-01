package org.acme.forSimCard;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.forCitizen.Citizen;
import org.acme.forCitizen.CitizenRepository;
import org.acme.forSimCard.SimCard;
import org.acme.forSimCard.SimCardDTO;
import org.acme.forSimCard.SimCardRepository;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Path("/simCard")
public class SimCardResource {
    @Inject
    SimCardRepository simCardRepository;
    @Inject
    CitizenRepository citizenRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSimCard() {
        return Response.ok(simCardRepository.listAll()).build();
    }



    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSimCard(SimCardDTO simCardDTO){
        SimCard simCard = new SimCard();
        simCard.setNumber(simCardDTO.getNumber());
        simCard.setProvider(simCardDTO.getProvider());
        simCard.setIsActive(simCardDTO.getIsActive());

        Citizen citizen = citizenRepository.findById(simCardDTO.getCitizenId());
        if (citizen == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Citizen not found").build();
        }
        simCard.setCitizen(citizen);

        simCardRepository.persist(simCard);
        if(simCardRepository.isPersistent(simCard))
            return Response.created(URI.create("/simCard/" + simCard.id)).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }




    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimCard(@PathParam("id") Long id){
        SimCard simCard = simCardRepository.findById(id);
        if (simCard == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(simCard).build();
    }



    @PUT
    @Path("/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSimCard(@PathParam("id") Long id, SimCardDTO simCardDTO) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByIdOptional(id);

        if (!optionalSimCard.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).entity("SimCard with id " + id + " not found.").build();
        }

        SimCard dbSimCard = optionalSimCard.get();

        if (Objects.nonNull(simCardDTO.getNumber())) {
            dbSimCard.setNumber(simCardDTO.getNumber());
        }

        if (Objects.nonNull(simCardDTO.getProvider())) {
            dbSimCard.setProvider(simCardDTO.getProvider());
        }

        if (Objects.nonNull(simCardDTO.getIsActive())) {
            dbSimCard.setIsActive(simCardDTO.getIsActive());
        }

        if (Objects.nonNull(simCardDTO.getCitizenId())) {
            Citizen citizen = citizenRepository.findById(simCardDTO.getCitizenId());
            if (citizen == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Citizen with id " + simCardDTO.getCitizenId() + " not found.").build();
            }
            dbSimCard.setCitizen(citizen);
        }

        simCardRepository.persist(dbSimCard);

        return Response.ok(dbSimCard).build();
    }




    @DELETE
    @Path("/{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSimCard(@PathParam("id") Long id) {
        boolean deleted = simCardRepository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
