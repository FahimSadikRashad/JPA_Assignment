package Assignment;

import model.Student;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/student")
public class StudentBean {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addStudent(@QueryParam("name") String nm,@QueryParam("id") int sid,@QueryParam("section") String sec,@QueryParam("cgpa") double cg){
        String temp="";
        temp+=(sid+" "+nm+" "+sec+" "+cg+" ");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student_pu");
        EntityManager entityManager= emf.createEntityManager();

        EntityTransaction entityTransaction=entityManager.getTransaction();
        entityTransaction.begin();
        Student student=new Student();
        student.setId(sid);
        student.setName(nm);
        student.setSection(sec);
        student.setCgpa(cg);


        entityManager.persist(student);
        entityTransaction.commit();

        return temp;
    }
    public Student findById(int id) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student_pu");
        EntityManager entityManager= emf.createEntityManager();

//        Query query = entityManager.createNamedQuery("find student by id");
//        query.setParameter("id", id);
        Query query = entityManager.createNamedQuery("StudentNew.findHighestCGPA");
        query.setParameter(1, id);
        return (Student) query.getSingleResult();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getName(@PathParam("id") int studentId){
        String temp="Id received " ;
        temp+=studentId;
        return findById(studentId).getName();
    }
    @GET
    @Path("/hcg")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestCg(@QueryParam("id1") int id_1,@QueryParam("id2") int id_2 ){
        String temp="The highest cgpa is";
        temp+=(id_1+" "+id_2);
        Student student_1=findById(id_1);
        Student student_2=findById(id_2);
        if(student_1.getCgpa()>student_2.getCgpa()) return student_1.toString();
        else return student_2.toString();

    }

    @GET
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateEntry(@QueryParam("id") int sid,@QueryParam("name") String nm ){
        String temp="Update the entry of";
        temp+=(sid+" "+nm);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student_pu");
        EntityManager entityManager= emf.createEntityManager();
        EntityTransaction entityTransaction=entityManager.getTransaction();
        entityTransaction.begin();
        Student student=entityManager.find(Student.class,sid);
        student.setName(nm);
        entityTransaction.commit();
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student_pu");
//        EntityManager entityManager= emf.createEntityManager();
//        EntityTransaction entityTransaction=entityManager.getTransaction();
//        entityTransaction.begin();
//        Student student=entityManager.find(Student.class,sid);
////        Student student=findById(sid);
////        Transaction tx=session.beginTransaction();
////        Query query = entityManager.createQuery("Update Student set name = '"+ nm + "' where id = " + sid );
////        query.executeUpdate();
//
////        int status=q.executeUpdate();
////        System.out.println(status);
////        tx.commit()
////        student.setName(nm);
//
////         entityManager.getTransaction().begin();
////    Query query = entityManager.createQuery("Update Student set firstName = '"+ firstName + "' where id = " + id );
////    query.executeUpdate();
////    entityManager.getTransaction().commit();
////    entityManager.clear()
//        entityTransaction.commit();
        return temp;
    }
}
