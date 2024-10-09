package com.manica.productscatalogue.ordering.contact;


import com.manica.productscatalogue.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    
    private final ContactRepository contactRepository;
    private final ContactMapper mapper;

    public Contact add(ContactRequest request) {

        Contact contact  = contactRepository.findByEmail(request.email()).orElseGet(Contact::new);
        mapper.update(request, contact);
        contact.setType(request.addressType().name());
        return contactRepository.save(contact);
    }


    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findContactByEmail(String email) {
        return contactRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Contact not found with email: " + email));
    }

}
