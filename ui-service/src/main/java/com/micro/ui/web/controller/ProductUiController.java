package com.micro.ui.web.controller;

import com.micro.ui.repositories.ProductCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class ProductUiController {

    private final ProductCrudRepository productCrudRepository;

    @RequestMapping("/index")
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping("/products")
    public String getProducts(Model model){
        System.out.println("calling localhost:8080/products");
        System.out.println(""+ productCrudRepository.findAll());
        model.addAttribute("products", productCrudRepository.findAll());
        return "productDtoList";
    }
}
