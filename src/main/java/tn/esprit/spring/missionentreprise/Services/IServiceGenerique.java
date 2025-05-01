package tn.esprit.spring.missionentreprise.Services;

import java.util.List;

public interface IServiceGenerique<T> {

    T add(T t);
    List<T> addAll(List<T> t);
    List<T> getAll();
    T edit(T t);
    List<T> editAll(List<T> t);
    void delete(T t);
    void deleteAll();
    void deleteById(Long id);



    T getById(Long id);
    boolean existsById(Long id);
    Long count();

}
