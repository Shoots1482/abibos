import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { products } from '../services/api';

const ProductDetailPage = ({ addToCart }) => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [selectedImage, setSelectedImage] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setIsLoading(true);
        
        // Fetch product details
        const response = await products.getById(id);
        setProduct(response.data);
        
        // Fetch product reviews
        const reviewsResponse = await products.getById(`${id}/reviews`);
        setReviews(reviewsResponse.data || []);
        
        setIsLoading(false);
      } catch (err) {
        console.error('Error fetching product details:', err);
        setError('Failed to load product details. Please try again later.');
        setIsLoading(false);
      }
    };
    
    fetchProduct();
  }, [id]);

  const handleQuantityChange = (e) => {
    const value = parseInt(e.target.value);
    if (value > 0) {
      setQuantity(value);
    }
  };

  const handleAddToCart = () => {
    addToCart({...product, quantity});
  };

  if (isLoading) {
    return (
      <div className="text-center py-5">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="alert alert-danger" role="alert">
        {error || 'Product not found'}
      </div>
    );
  }

  // Placeholder images if no images available
  const productImages = product.images && product.images.length > 0
    ? product.images
    : [{ url: 'https://via.placeholder.com/600x600?text=No+Image' }];

  return (
    <div className="row">
      {/* Breadcrumb */}
      <nav aria-label="breadcrumb" className="mb-4">
        <ol className="breadcrumb">
          <li className="breadcrumb-item"><Link to="/">Home</Link></li>
          <li className="breadcrumb-item"><Link to="/products">Products</Link></li>
          <li className="breadcrumb-item active" aria-current="page">{product.productName}</li>
        </ol>
      </nav>
      
      {/* Product Images */}
      <div className="col-md-6 mb-4">
        <div className="mb-3">
          <img 
            src={productImages[selectedImage].url} 
            alt={product.productName} 
            className="img-fluid rounded"
          />
        </div>
        
        {/* Thumbnail Images */}
        {productImages.length > 1 && (
          <div className="row g-2">
            {productImages.map((image, index) => (
              <div key={index} className="col-3">
                <img 
                  src={image.url} 
                  alt={`Thumbnail ${index + 1}`} 
                  className={`img-thumbnail cursor-pointer ${selectedImage === index ? 'border-primary' : ''}`}
                  onClick={() => setSelectedImage(index)}
                  style={{ cursor: 'pointer' }}
                />
              </div>
            ))}
          </div>
        )}
      </div>
      
      {/* Product Details */}
      <div className="col-md-6">
        <h2 className="mb-2">{product.productName}</h2>
        
        <p className="text-muted mb-2">{product.brand}</p>
        
        <div className="mb-3">
          <span className="fs-4 fw-bold">${product.price.toFixed(2)}</span>
        </div>
        
        <div className="mb-4">
          <p>{product.description}</p>
        </div>
        
        <div className="row mb-4">
          <div className="col-md-6 mb-3">
            <label className="form-label">Color</label>
            <div className="form-control bg-light">{product.color}</div>
          </div>
          
          <div className="col-md-6 mb-3">
            <label className="form-label">Size</label>
            <div className="form-control bg-light">{product.size}</div>
          </div>
        </div>
        
        <div className="d-flex align-items-center mb-4">
          <div className="me-3" style={{ width: '100px' }}>
            <label className="form-label">Quantity</label>
            <input 
              type="number" 
              className="form-control" 
              value={quantity} 
              onChange={handleQuantityChange}
              min="1"
            />
          </div>
          
          <div className="flex-grow-1">
            <label className="form-label">&nbsp;</label>
            <button 
              className="btn btn-primary w-100" 
              onClick={handleAddToCart}
            >
              Add to Cart
            </button>
          </div>
        </div>
        
        {/* Product Categories */}
        {product.categories && product.categories.length > 0 && (
          <div className="mb-4">
            <h5>Categories</h5>
            <div>
              {product.categories.map(category => (
                <Link 
                  key={category.id} 
                  to={`/products?category=${category.id}`} 
                  className="badge bg-secondary text-decoration-none me-2"
                >
                  {category.name}
                </Link>
              ))}
            </div>
          </div>
        )}
      </div>
      
      {/* Product Reviews */}
      <div className="col-12 mt-5">
        <h3 className="mb-4">Customer Reviews</h3>
        
        {reviews.length === 0 ? (
          <div className="alert alert-info">
            No reviews yet. Be the first to review this product.
          </div>
        ) : (
          <div>
            {reviews.map(review => (
              <div key={review.id} className="card mb-3">
                <div className="card-body">
                  <div className="d-flex justify-content-between mb-2">
                    <h5 className="card-title mb-0">{review.title}</h5>
                    <div>
                      {[...Array(5)].map((_, i) => (
                        <i 
                          key={i} 
                          className={`bi ${i < review.rating ? 'bi-star-fill' : 'bi-star'} text-warning`}
                        ></i>
                      ))}
                    </div>
                  </div>
                  <h6 className="card-subtitle mb-2 text-muted">
                    By {review.customer.firstName} on {new Date(review.createdAt).toLocaleDateString()}
                  </h6>
                  <p className="card-text">{review.content}</p>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductDetailPage; 