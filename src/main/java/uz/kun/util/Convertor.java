package uz.kun.util;//User :Lenovo
//Date :09.06.2022
//Time :6:36
//Project Name :Kun.uzWithThymleaf

public interface Convertor<T,K> {

    public T toEntity(K dto);
    public K toDTO(T entity);
}
