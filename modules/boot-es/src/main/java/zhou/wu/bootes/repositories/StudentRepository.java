package zhou.wu.bootes.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.RepositoryDefinition;
import zhou.wu.bootes.models.Student;

import java.util.List;


/**
 * Created by lin.xc on 2019/8/2
 */
//@RepositoryDefinition(idClass = String.class, domainClass = Student.class)
public interface StudentRepository {
/*        extends ElasticsearchRepository {
    Student findByName(String name);
    List<Student> findByAddr(String author);
    Student findStudentById(String id);*/
}
