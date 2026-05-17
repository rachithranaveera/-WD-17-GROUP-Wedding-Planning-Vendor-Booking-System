package com.weddingapp.wd17weddingplanner.repository;

import com.weddingapp.wd17weddingplanner.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}