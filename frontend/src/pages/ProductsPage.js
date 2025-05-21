import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { products, categories } from '../services/api';
import ProductCard from '../components/ProductCard';

const ProductsPage = ({ addToCart }) => {
  const [searchParams] = useSearchParams();
  const [productList, setProductList] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // Filter states
  const [selectedCategory, setSelectedCategory] = useState(searchParams.get('category') || '');
  const [selectedBrand, setSelectedBrand] = useState('');
  const [selectedColor, setSelectedColor] = useState('');
  const [selectedSize, setSelectedSize] = useState('');
  const [priceRange, setPriceRange] = useState({ min: '', max: '' });
  const [sortOption, setSortOption] = useState(searchParams.get('sort') || 'latest');
  
  // Available filter options derived from products
  const [availableBrands, setAvailableBrands] = useState([]);
  const [availableColors, setAvailableColors] = useState([]);
  const [availableSizes, setAvailableSizes] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        
        // Get search query if exists
        const searchQuery = searchParams.get('search');
        
        // Fetch products based on search or get all
        let productsResponse;
        if (searchQuery) {
          productsResponse = await products.search(searchQuery);
        } else {
          productsResponse = await products.getAll();
        }
        
        const fetchedProducts = productsResponse.data;
        setProductList(fetchedProducts);
        
        // Extract available filter options
        const brands = [...new Set(fetchedProducts.map(p => p.brand))];
        const colors = [...new Set(fetchedProducts.map(p => p.color))];
        const sizes = [...new Set(fetchedProducts.map(p => p.size))];
        
        setAvailableBrands(brands);
        setAvailableColors(colors);
        setAvailableSizes(sizes);
        
        // Fetch categories
        const categoriesResponse = await categories.getAll();
        setCategoryList(categoriesResponse.data);
        
        setIsLoading(false);
      } catch (err) {
        console.error('Error fetching products:', err);
        setError('Failed to load products. Please try again later.');
        setIsLoading(false);
      }
    };
    
    fetchData();
  }, [searchParams]);

  // Apply filters and sorting to products
  const filteredProducts = productList.filter(product => {
    // Apply category filter
    if (selectedCategory && !product.categories?.some(cat => cat.id.toString() === selectedCategory)) {
      return false;
    }
    
    // Apply brand filter
    if (selectedBrand && product.brand !== selectedBrand) {
      return false;
    }
    
    // Apply color filter
    if (selectedColor && product.color !== selectedColor) {
      return false;
    }
    
    // Apply size filter
    if (selectedSize && product.size !== selectedSize) {
      return false;
    }
    
    // Apply price range filter
    if (priceRange.min && product.price < parseFloat(priceRange.min)) {
      return false;
    }
    
    if (priceRange.max && product.price > parseFloat(priceRange.max)) {
      return false;
    }
    
    return true;
  });
  
  // Apply sorting
  const sortedProducts = [...filteredProducts].sort((a, b) => {
    switch (sortOption) {
      case 'price-low':
        return a.price - b.price;
      case 'price-high':
        return b.price - a.price;
      case 'latest':
        return new Date(b.createdAt) - new Date(a.createdAt);
      default:
        return 0;
    }
  });

  if (isLoading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="alert alert-danger" role="alert">
        {error}
      </div>
    );
  }

  return (
    <div className="row">
      {/* Filters Sidebar */}
      <div className="col-md-3 mb-4">
        <div className="card">
          <div className="card-body">
            <h5 className="card-title mb-3">Filters</h5>
            
            {/* Category Filter */}
            <div className="mb-4">
              <label className="form-label fw-bold">Category</label>
              <select 
                className="form-select" 
                value={selectedCategory} 
                onChange={(e) => setSelectedCategory(e.target.value)}
              >
                <option value="">All Categories</option>
                {categoryList.map(category => (
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            
            {/* Brand Filter */}
            <div className="mb-4">
              <label className="form-label fw-bold">Brand</label>
              <select 
                className="form-select" 
                value={selectedBrand} 
                onChange={(e) => setSelectedBrand(e.target.value)}
              >
                <option value="">All Brands</option>
                {availableBrands.map(brand => (
                  <option key={brand} value={brand}>
                    {brand}
                  </option>
                ))}
              </select>
            </div>
            
            {/* Color Filter */}
            <div className="mb-4">
              <label className="form-label fw-bold">Color</label>
              <select 
                className="form-select" 
                value={selectedColor} 
                onChange={(e) => setSelectedColor(e.target.value)}
              >
                <option value="">All Colors</option>
                {availableColors.map(color => (
                  <option key={color} value={color}>
                    {color}
                  </option>
                ))}
              </select>
            </div>
            
            {/* Size Filter */}
            <div className="mb-4">
              <label className="form-label fw-bold">Size</label>
              <select 
                className="form-select" 
                value={selectedSize} 
                onChange={(e) => setSelectedSize(e.target.value)}
              >
                <option value="">All Sizes</option>
                {availableSizes.map(size => (
                  <option key={size} value={size}>
                    {size}
                  </option>
                ))}
              </select>
            </div>
            
            {/* Price Range Filter */}
            <div className="mb-4">
              <label className="form-label fw-bold">Price Range</label>
              <div className="d-flex align-items-center">
                <input 
                  type="number" 
                  className="form-control me-2" 
                  placeholder="Min" 
                  value={priceRange.min}
                  onChange={(e) => setPriceRange({...priceRange, min: e.target.value})}
                />
                <span className="mx-1">-</span>
                <input 
                  type="number" 
                  className="form-control ms-2" 
                  placeholder="Max" 
                  value={priceRange.max}
                  onChange={(e) => setPriceRange({...priceRange, max: e.target.value})}
                />
              </div>
            </div>
            
            {/* Reset Filters Button */}
            <button 
              className="btn btn-outline-secondary w-100"
              onClick={() => {
                setSelectedCategory('');
                setSelectedBrand('');
                setSelectedColor('');
                setSelectedSize('');
                setPriceRange({ min: '', max: '' });
              }}
            >
              Reset Filters
            </button>
          </div>
        </div>
      </div>
      
      {/* Products Grid */}
      <div className="col-md-9">
        {/* Sort Options and Results Count */}
        <div className="d-flex justify-content-between align-items-center mb-4">
          <div>
            <span className="text-muted">
              {sortedProducts.length} {sortedProducts.length === 1 ? 'product' : 'products'} found
            </span>
          </div>
          <div className="d-flex align-items-center">
            <label className="me-2">Sort by:</label>
            <select 
              className="form-select form-select-sm" 
              style={{ width: 'auto' }}
              value={sortOption}
              onChange={(e) => setSortOption(e.target.value)}
            >
              <option value="latest">Newest</option>
              <option value="price-low">Price: Low to High</option>
              <option value="price-high">Price: High to Low</option>
            </select>
          </div>
        </div>
        
        {/* Products Grid */}
        {sortedProducts.length === 0 ? (
          <div className="alert alert-info">
            No products found matching your criteria. Try adjusting your filters.
          </div>
        ) : (
          <div className="row row-cols-1 row-cols-sm-2 row-cols-lg-3 g-4">
            {sortedProducts.map(product => (
              <div key={product.id} className="col">
                <ProductCard product={product} addToCart={addToCart} />
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductsPage; 