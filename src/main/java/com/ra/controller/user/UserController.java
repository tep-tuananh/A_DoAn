package com.ra.controller.user;

import com.ra.models.entity.EnumDescriptionProduct;
import com.ra.models.entity.Product;
import com.ra.models.entity.ShoppingCart;
import com.ra.repository.user.ShoppingCartRepository;
import com.ra.security.UserPrincipal;
import com.ra.service.admin.product.IProductService;
import com.ra.service.auth.IAuthService;
import com.ra.service.user.UserService;
import com.ra.service.user.shopping_cart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    public static Long getUserId() { // lay ra user_id dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser().getId();
    }

    @GetMapping("")
    public String home(Model model){
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
        double total = 0;
        for (ShoppingCart item: shoppingCartList) {
        total += item.getProduct().getPrice() * item.getQuantity();
        }
        long countById = shoppingCartRepository.countByUserId(getUserId());
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("total", total);

        // Sản phẩm bán chạy
        List<Product> productListSellProduct =authService.getProductNewProduct(EnumDescriptionProduct.SellingProduct);
        model.addAttribute("productListSellProduct",productListSellProduct);

        // Sản phẩm mới
        List<Product> productListNewProduct =authService.getProductNewProduct(EnumDescriptionProduct.NewProduct);
        model.addAttribute("productListNewProduct",productListNewProduct);

        // sản phẩm yêu thích
        List<Product> productListNewProductFavoriteProduct = authService.getProductByFavoriteProduct(EnumDescriptionProduct.FavoriteProduct);
        model.addAttribute("productListNewProductFavoriteProduct",productListNewProductFavoriteProduct);
        return "user/index";
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id,
                          Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<ShoppingCart> shoppingCartList = shoppingCartService.getAll(getUserId());
        double total = 0;
        for (ShoppingCart item: shoppingCartList) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        long countById = shoppingCartRepository.count();
        model.addAttribute("countById",countById);
        model.addAttribute("shoppingCartList", shoppingCartList);
        model.addAttribute("total", total);
        return "user/details";
    }
}
