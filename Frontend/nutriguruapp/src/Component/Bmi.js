import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { Button } from 'react-bootstrap';

export default function Bmi() {
  const location = useLocation();
  const { bmi } = location.state || { bmi: 'N/A' };
  const [feedback, setFeedback] = useState('');

  useEffect(() => {
    if (bmi !== 'N/A') {
      const bmiValue = parseFloat(bmi);
      const feedbackTxt =
        bmiValue < 18.5
          ? 'Looks like you are Underweight Your weight is low for your height. We recommend getting in touch with your healthcare provider to determine its causes.'
          : bmiValue < 25
          ? 'Everything looks a-okay! You are at a healthy weight for your height. Keep it up! '
          : bmiValue < 30
          ? 'Your BMI is considered Overweight .our weight is high for your height. Lower your risk of contracting heart disease by reducing your BMI to a healthy range '
          : 'Obese , Your weight is high for your height. Lower your risk of suffering from serious health problems by reducing your BMI to a healthy range ';
      setFeedback(feedbackTxt);
    }
  }, [bmi]);

  console.log('Location State:', location.state); // Debug log

  return (
    <div className="bmi">
 
      
        <p>Your BMI is  {bmi}</p>
        <p>{feedback}</p>
     

      <Button variant="primary" className='btn1'> {/* Corrected variant */}
        Book Your Bite
      </Button>
    </div>
  );
}
