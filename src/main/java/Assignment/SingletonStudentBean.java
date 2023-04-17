package Assignment;

import model.Student;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
@Path("/highcg")
public class SingletonStudentBean {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Lock(LockType.READ)
    public String getHighestCgpa(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student_pu");
        EntityManager entityManager= emf.createEntityManager();

        Query query = entityManager.createNamedQuery("find student by cg");
        Student student= (Student) query.getSingleResult();
        return student.toString();
    }
}
