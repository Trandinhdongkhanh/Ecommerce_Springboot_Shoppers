package com.ecommerce.customer.email;

import com.ecommerce.library.dto.CustomerDTO;

public interface EmailService {
    void sendEmail(CustomerDTO to);
}
