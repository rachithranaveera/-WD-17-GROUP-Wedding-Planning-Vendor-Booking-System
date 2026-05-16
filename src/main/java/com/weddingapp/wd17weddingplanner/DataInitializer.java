package com.weddingapp.wd17weddingplanner;

import com.weddingapp.wd17weddingplanner.model.Admin;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.model.Vendor;
import com.weddingapp.wd17weddingplanner.repository.UserRepository;
import com.weddingapp.wd17weddingplanner.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setFullName("System Admin");
        userRepository.save(admin);

        Couple couple = new Couple();
        couple.setUsername("couple");
        couple.setPassword("couple123");
        couple.setFullName("John & Jane");
        couple.setEstimatedBudget(50000.0);
        userRepository.save(couple);

        Vendor v1 = new Vendor();
        v1.setName("Ballroom");
        v1.setCategory("Venue");
        v1.setPrice(10000.0);
        v1.setDescription("Luxurious ballroom for up to 500 guests.");
        vendorRepository.save(v1);

        Vendor v2 = new Vendor();
        v2.setName("Catering");
        v2.setCategory("Catering");
        v2.setPrice(5000.0);
        v2.setDescription("Delicious 5-course meal for all your guests.");
        vendorRepository.save(v2);

        Vendor v3 = new Vendor();
        v3.setName("Snap Moments");
        v3.setCategory("Photography");
        v3.setPrice(2000.0);
        v3.setDescription("Professional wedding photography.");
        vendorRepository.save(v3);
    }
}
