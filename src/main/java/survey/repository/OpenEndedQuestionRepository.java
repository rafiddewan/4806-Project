package survey.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import survey.model.OpenEndedQuestion;

@RepositoryRestResource(collectionResourceRel = "openendedquestions", path = "openendedquestions")
public interface OpenEndedQuestionRepository extends PagingAndSortingRepository<OpenEndedQuestion, Integer> {
    OpenEndedQuestion findById(@Param("id") int id);
}




