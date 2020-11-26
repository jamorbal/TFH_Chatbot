package com.bbva.tinfoilhat.rest.api;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import com.bbva.tinfoilhat.dtos.Task;
import com.bbva.tinfoilhat.dtos.TaskAssign;
import com.bbva.tinfoilhat.dtos.TaskDone;

public interface TaskServicesInterface {

    @GET
    @Path("/children/{userid}/tasks")
    @Produces("application/json")
    List<Task> getUserTasks(@PathParam String userid);


    @GET
    @Path("/tasks")
    @Produces("application/json")
    List<Task> getAllTasks();


    @PUT
    @Path("/tasks")
    @Produces("application/json")
    TaskAssign assignTask(TaskAssign taskAssign);

    @PUT
    @Path("/tasks/status")
    void updateTaskStatus(TaskDone taskAssign);


}
