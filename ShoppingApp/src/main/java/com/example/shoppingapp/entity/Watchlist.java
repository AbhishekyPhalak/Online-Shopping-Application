package com.example.shoppingapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="watchlist")
public class Watchlist {

    @EmbeddedId
    private WatchlistId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public Watchlist() {}

    public Watchlist(User user, Product product) {
        this.user = user;
        this.product = product;
        this.id = new WatchlistId(user.getUserId(), product.getProductId());
    }

}
