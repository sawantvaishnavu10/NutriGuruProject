import React from 'react';
import '../Css/Blogs.css' // Reuse the same CSS file


   
const Blog = () => {
    const blogPosts = [
      {
        id: 1,
        title: 'The Benefits of a Balanced Diet',
        image: 'https://via.placeholder.com/150', // Placeholder image URL
        content: 'It protects you against many chronic noncommunicable diseases, such as heart disease, diabetes and cancer. Eating a variety of foods and consuming less salt, sugars and saturated and industrially-produced trans-fats, are essential for healthy diet. A healthy diet comprises a combination of different foods.',
        author: 'John Doe',
        authorImage: 'https://via.placeholder.com/50', // Placeholder author image URL
        rating: 5,
      },
      {
        id: 2,
        title: 'How to Start a Healthy Lifestyle',
        image: 'https://via.placeholder.com/150',
        content: 'Exercising regularly, eating nourishing foods, and reducing your intake of sugar and alcohol are just some of the recommendations for maintaining a healthy lifestyle. Taking care of your health is arguably the most important thing you can do for yourself (and your loved ones)',
        author: 'Jane Smith',
        authorImage: 'https://via.placeholder.com/50',
        rating: 4,
      },
      {
        id: 3,
        title: 'Understanding Macronutrients',
        image: 'https://via.placeholder.com/150',
        content: 'Macronutrients are the nutrients we need in larger quantities that provide us with energy: in other words, fat, protein and carbohydrate. Micronutrients are mostly vitamins and minerals, and are equally important but consumed in very small amounts. We generally get our micronutrients along with macronutrients..',
        author: 'Jim Brown',
        authorImage: 'https://via.placeholder.com/50',
        rating: 5,
      },
      {
        id: 4,
        title: 'The Importance of Hydration',
        image: 'https://via.placeholder.com/150',
        content: 'Drinking enough water each day is crucial for many reasons: to regulate body temperature, keep joints lubricated, prevent infections, deliver nutrients to cells, and keep organs functioning properly. Being well-hydrated also improves sleep quality, cognition, and mood',
        author: 'Emily White',
        authorImage: 'https://via.placeholder.com/50',
        rating: 3,
      },
      {
        id: 5,
        title: 'The Importance of Hydration',
        image: 'https://via.placeholder.com/150',
        content: 'Water in the body is essential for many important processes to take place. From our blood system carrying essential glucose, oxygen and nutrients to cells, to the kidneys getting rid of waste products we no longer want, fluid in the body is vital to allow these to occur. It also lubricates our joints and eyes, helps our digestive system function and keeps our skin healthy..',
        author: 'Emily White',
        authorImage: 'https://via.placeholder.com/50',
        rating: 3,
      },

    ];
  
    return (
      <div className="blog-container">
        {blogPosts.map((post) => (
          <div key={post.id} className="blog-post">
            <h2>{post.title}</h2>
            <div className="author-info">
              <img src={post.authorImage} alt={post.author} className="author-image" />
              <div className="rating">
                {'★'.repeat(post.rating)}{'☆'.repeat(5 - post.rating)}
              </div>
            </div>
            <img src={post.image} alt={post.title} className="blog-image" />
            <p>{post.content}</p>
          </div>
        ))}
      </div>
    );
  };
  
  export default Blog;
