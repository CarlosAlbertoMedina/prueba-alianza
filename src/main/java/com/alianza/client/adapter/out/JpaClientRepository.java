package com.alianza.client.adapter.out;

import com.alianza.client.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE " +
            "(LOWER(c.sharedKey) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
            "LOWER(c.businessId) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :searchQuery, '%')))")
    Page<Client> findBySearchQuery(String searchQuery, Pageable pageable);

    boolean existsByBusinessId(String businessId);

}