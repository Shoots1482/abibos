package com.example.demo.repositories;

import com.example.demo.entities.Storage;
import com.example.demo.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
    
    // Find all storage locations for a specific store
    List<Storage> findByStore(Store store);
    
    // Find by location (exact match)
    List<Storage> findByLocation(String location);
    
    // Find by location (partial match)
    List<Storage> findByLocationContaining(String locationKeyword);
    
    // Find storage areas that need reordering (where current stock is below reorder level)
    @Query("SELECT s FROM Storage s WHERE EXISTS " +
           "(SELECT si FROM StoreInventory si WHERE si.store.id = s.store.id AND si.quantity <= s.reorderLevel)")
    List<Storage> findStorageNeedingReorder();
    
    // Find storage areas at full capacity
    @Query("SELECT s FROM Storage s WHERE EXISTS " +
           "(SELECT si FROM StoreInventory si WHERE si.store.id = s.store.id AND si.quantity >= s.maxStock)")
    List<Storage> findStorageAtCapacity();
    
    // Find storage areas with low stock
    @Query("SELECT s FROM Storage s WHERE EXISTS " +
           "(SELECT si FROM StoreInventory si WHERE si.store.id = s.store.id AND si.quantity <= s.minStock)")
    List<Storage> findStorageWithLowStock();
    
    // Find by store and location
    List<Storage> findByStoreAndLocationContaining(Store store, String locationKeyword);
}