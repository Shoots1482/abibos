package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import com.example.demo.entities.CustomerOrder;
import com.example.demo.entities.Employee;
import com.example.demo.entities.PaymentMethod;
import com.example.demo.entities.Promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
    List<CustomerOrder> findByCustomerId(Integer customerId);
    List<CustomerOrder> findByCustomerIdAndStatus(Integer customerId, String status);
    List<CustomerOrder> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);
    List<CustomerOrder> findByStatus(String status);
    List<CustomerOrder> findByEmployeeSellerId(Integer employeeId);
    List<CustomerOrder> findByAddressId(Integer addressId);
    
    @Query("SELECT o FROM CustomerOrder o WHERE o.returnDate IS NOT NULL")
    List<CustomerOrder> findOrdersWithReturns();
    
    List<CustomerOrder> findByCustomer(Customer customer);
    List<CustomerOrder> findByEmployeeSeller(Employee employee);
    List<CustomerOrder> findByPaymentMethod(PaymentMethod paymentMethod);
    List<CustomerOrder> findByPromotion(Promotion promotion);
    List<CustomerOrder> findByPurchaseDate(LocalDate date);
    List<CustomerOrder> findByTotalPriceGreaterThanEqual(BigDecimal minPrice);
    List<CustomerOrder> findByTotalPriceLessThanEqual(BigDecimal maxPrice);
    List<CustomerOrder> findByCustomerAndStatus(Customer customer, String status);
    
    @Query("SELECT o FROM CustomerOrder o WHERE o.purchaseDate >= :date")
    List<CustomerOrder> findRecentOrders(@Param("date") LocalDate date);
    
    @Query("SELECT SUM(o.totalPrice) FROM CustomerOrder o WHERE o.purchaseDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalRevenue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(o.totalPrice) FROM CustomerOrder o WHERE o.customer = :customer")
    BigDecimal calculateTotalRevenueByCustomer(@Param("customer") Customer customer);
    
    @Query("SELECT SUM(o.totalPrice) FROM CustomerOrder o WHERE o.employeeSeller = :employee")
    BigDecimal calculateTotalRevenueByEmployee(@Param("employee") Employee employee);
    
    long countByStatus(String status);
    
    @Query("SELECT o.customer, COUNT(o) FROM CustomerOrder o GROUP BY o.customer ORDER BY COUNT(o) DESC")
    List<Object[]> findTopCustomersByOrderCount();
    
    @Query("SELECT o.employeeSeller, SUM(o.totalPrice) FROM CustomerOrder o GROUP BY o.employeeSeller ORDER BY SUM(o.totalPrice) DESC")
    List<Object[]> findTopEmployeesBySales();
}
