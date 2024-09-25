package org.example.humanity.userFunctionality.items.repo;

import jakarta.transaction.Transactional;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {

    Optional<Items> findById(Long id);
    List<Items>  findByUsername(String userName);
    Optional<Items>  findByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Items i WHERE i.id = :id")
    void deleteItem(Long id);


    @Transactional
    @Query("SELECT COUNT(o) FROM Items o")
    long totalNumberOfItems();

}
