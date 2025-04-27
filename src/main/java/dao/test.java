package dao;

import entity.CartItem;
import entity.Products;
import entity.Review;
import entity.Users;
import dao.CartDao;

import java.sql.SQLException;
import java.util.List;

    public class test {
        public static void main(String[] args) throws SQLException {
            CartDao cartDao = new CartDao();
            int q = 8;
            System.out.print(cartDao.getProductStock(q));

        }
    } 