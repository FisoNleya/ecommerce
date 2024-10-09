package com.manica.productscatalogue.subscriptions.vendor;

import com.manica.productscatalogue.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendorService {
    
    private final VendorRepository vendorRepository;
    private final VendorMapper mapper;

    public Vendor add(VendorRequest request) {

        Optional<Vendor>  optionalVendor = vendorRepository.findById(request.vendorId());
        if (optionalVendor.isPresent()) {
            throw new DataNotFoundException("Vendor with id " + request.vendorId() + " already exists");
        }

        Vendor vendor = new Vendor();

        mapper.update(request, vendor);
        return vendorRepository.save(vendor);
    }


    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Vendor findVendorById(long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Vendor with id " + id + " not found"));
    }

    public Vendor findVendorOrCreate(Vendor vendor){
        return vendorRepository.findById(vendor.getVendorId()).orElseGet(()-> vendorRepository.save(vendor));
    }

}
