package com.ecommerce.customer.email;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Set;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(CustomerDTO customerDTO) {
        try {
            String from = "haxigi1307@gmail.com";
            String to = customerDTO.getEmail();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Order confirmation from super fresh !");
            boolean html = true;

            StringBuilder sb = new StringBuilder();
            sb
                    .append("Dear ")
                    .append(customerDTO.getFullName())
                    .append("<br>")
                    .append("Ban vua dat hang tu Shoppers. <br> ")
                    .append("Dia chi nhan hang cua ban la: <b>")
                    .append(customerDTO.getAddress())
                    .append(", ")
                    .append(customerDTO.getCity())
                    .append(", ")
                    .append(customerDTO.getCountry())
                    .append("<br>")
                    .append("So dien thoai khi nhan hang cua ban la: <b>").append(customerDTO.getPhoneNo()).append(" </b> <br>")
                    .append("Cac san pham ban dat la: <br>");

            Set<CartItem> cartItems = customerDTO.getCart().getCartItems();

            for (CartItem item : cartItems) {
                sb
                        .append(item.getProduct().getName())
                        .append(" | ")
                        .append("Price:")
                        .append(item.getTotalPrice())
                        .append("$")
                        .append(" | ")
                        .append("Quantity:")
                        .append(item.getQuantity())
                        .append("<br>");
            }
            sb.append("Tong tien: ").append(customerDTO.getCart().getTotalPrices()).append("$").append("<br>");
            sb.append("Cam on ban da dat hang tai Shoppers<br>");
            sb.append("From: Chu cua hang");

//            message.setText(sb.toString());
            helper.setText(sb.toString(), html);

            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
