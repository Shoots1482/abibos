import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { products, categories } from '../services/api';
import ProductCard from '../components/ProductCard';

const HomePage = () => {
  const [latestProducts, setLatestProducts] = useState([]);
  const [topSellingProducts, setTopSellingProducts] = useState([]);
  const [featuredCategories, setFeaturedCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        
        // Fetch latest products
        const latestResponse = await products.getLatest();
        setLatestProducts(latestResponse.data);
        
        // Fetch top selling products
        const topSellingResponse = await products.getTopSelling(4);
        // Convert map to array of products
        const topSellingArray = Object.entries(topSellingResponse.data)
          .map(([key, value]) => ({
            ...JSON.parse(key),
            soldCount: value
          }))
          .sort((a, b) => b.soldCount - a.soldCount);
        
        setTopSellingProducts(topSellingArray);
        
        // Fetch categories
        const categoriesResponse = await categories.getAll();
        // Just get a few categories for the featured section
        setFeaturedCategories(categoriesResponse.data.slice(0, 3));
        
        setIsLoading(false);
      } catch (err) {
        console.error('Error fetching home page data:', err);
        setError('Failed to load data. Please try again later.');
        setIsLoading(false);
      }
    };
    
    fetchData();
  }, []);

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
    <div>
      {/* Hero Section */}
      <div className="bg-dark text-white p-5 mb-4 rounded">
        <div className="container-fluid py-5 text-center">
          <h1 className="display-5 fw-bold">Summer Collection 2023</h1>
          <p className="fs-4 mb-4">Discover the latest trends in fashion</p>
          <Link to="/products" className="btn btn-primary btn-lg">Shop Now</Link>
        </div>
      </div>

      {/* Featured Categories */}
      <section className="mb-5">
        <h2 className="mb-4">Shop by Category</h2>
        <div className="row g-4">
          {featuredCategories.map(category => (
            <div key={category.id} className="col-md-4">
              <div className="card bg-dark text-white h-100">
                <div className="card-img-overlay d-flex flex-column justify-content-center text-center">
                  <h3 className="card-title">{category.name}</h3>
                  <Link 
                    to={`/products?category=${category.id}`} 
                    className="btn btn-outline-light mt-2"
                  >
                    View Products
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>

      {/* Latest Products */}
      <section className="mb-5">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2>New Arrivals</h2>
          <Link to="/products?sort=latest" className="btn btn-outline-dark">View All</Link>
        </div>
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
          {latestProducts.slice(0, 4).map(product => (
            <div key={product.id} className="col">
              <ProductCard product={product} />
            </div>
          ))}
        </div>
      </section>

      {/* Top Selling Products */}
      <section className="mb-5">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2>Best Sellers</h2>
          <Link to="/products?sort=popular" className="btn btn-outline-dark">View All</Link>
        </div>
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
          {topSellingProducts.map(product => (
            <div key={product.id} className="col">
              <ProductCard product={product} />
            </div>
          ))}
        </div>
      </section>

      {/* Promotional Banner */}
      <section className="mb-5">
        <div className="bg-light p-4 text-center rounded">
          <h3>Special Offer</h3>
          <p className="lead mb-4">Get 20% off on all summer items!</p>
          <Link to="/products?promotion=summer" className="btn btn-primary">Shop the Sale</Link>
        </div>
      </section>
    </div>
  );
};

export default HomePage; 