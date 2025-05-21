import React from 'react';
import { Link } from 'react-router-dom';

const ProductCard = ({ product, addToCart }) => {
  const handleAddToCart = (e) => {
    e.preventDefault();
    addToCart(product);
  };

  // Placeholder image if no image is available
  const imageSrc = product.images && product.images.length > 0
    ? product.images[0].url
    : 'https://via.placeholder.com/300x300?text=No+Image';

  return (
    <div className="card product-card h-100">
      <Link to={`/products/${product.id}`} className="text-decoration-none">
        <img 
          src={imageSrc} 
          className="card-img-top product-image" 
          alt={product.productName} 
        />
      </Link>
      <div className="card-body d-flex flex-column">
        <h5 className="card-title">
          <Link to={`/products/${product.id}`} className="text-decoration-none text-dark">
            {product.productName}
          </Link>
        </h5>
        <p className="card-text text-muted mb-1">{product.brand}</p>
        <p className="card-text mb-1">
          <small className="text-muted">
            {product.color} • {product.size}
          </small>
        </p>
        <div className="d-flex justify-content-between align-items-center mt-auto">
          <span className="fs-5 fw-bold">${product.price.toFixed(2)}</span>
          <button 
            onClick={handleAddToCart} 
            className="btn btn-sm btn-outline-primary"
          >
            Add to Cart
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard; 