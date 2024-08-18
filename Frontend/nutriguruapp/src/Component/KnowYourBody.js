import React, { useState } from "react";
import "../Css/KnowYourBody.css";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import { Link } from "react-router-dom";
export default function KnowYourBody() {
  const [weight, setWeight] = useState("");
  const [height, setHeight] = useState("");
  const [flag, setFlag] = useState(false);
  const [heightUnit, setHeightUnit] = useState("cm");
  const [bmi, setBmi] = useState("");
  const [feedback, setFeedback] = useState("");
  const navigate = useNavigate();

  const calculateBmi = (e) => {
    e.preventDefault();
    let heightInMeters;
    if (heightUnit === "cm") {
      heightInMeters = height / 100;
    } else if (heightUnit === "ft") {
      heightInMeters = height * 0.3048;
    }

    const bmiValue = weight / (heightInMeters * heightInMeters);
    setBmi(bmiValue.toFixed(2));

    if (bmiValue < 18.5) {
      setFeedback(
        " Looks like you are Underweight Your weight is low for your height.We recommend getting in touch with your healthcare provider to determine its causes."
      );
      setFlag(true);
    } else if (bmiValue < 25) {
      setFeedback(
        " Everything looks a-okay! You are at a healthy weight for your height. Keep it up! "
      );
      setFlag(true);
    } else if (bmiValue < 30) {
      setFeedback(
        " Your BMI is considered Overweight.Your weight is high for your height. Lower your risk of contracting heart disease by reducing your BMI to a healthy range"
      );
      setFlag(true);
    } else {
      setFeedback(
        " Obese , Your weight is high for your height. Lower your risk of suffering from serious health problems by reducing your BMI to a healthy range"
      );
      setFlag(true);
    }
  };

  return (
    <div className="bmi-calculator-c">
      <h2>Get deeper insights on your health</h2>
      <form className="bmi-form-c" onSubmit={(e) => calculateBmi(e)}>
        <label form="weight">Weight</label>
        <input
          id="weight"
          type="number"
          value={weight}
          onChange={(e) => setWeight(e.target.value)}
          placeholder="Kg"
          className="ip-c"
          required
        />
        <br />
        <label for="height">Height</label>
        <input
          id="height"
          type="number"
          value={height}
          onChange={(e) => setHeight(e.target.value)}
          required
        />
        <select
          value={heightUnit}
          onChange={(e) => setHeightUnit(e.target.value)}
        >
          <option value="cm">cm</option>
          <option value="ft">ft</option>
        </select>
        <button type="submit">Calculate BMI</button>
        {!flag && (
          <div>
            <br>
            </br>
            <br>
            </br>
            <Link to={"/home"} className="link-c">
              Home
            </Link>
          </div>
        )}
        <br></br>
        {flag && (
          <div>
            <div className="result-c">
              <b>Your BMI is {bmi}</b>
              <b>{feedback}</b>
            </div>
            <div className="link-container-c">
              <Link to={"/home"} className="link-c">
                Book Your Bite
              </Link>
              <br></br>
              <Link to={"/home"} className="link-c">
                Home
              </Link>
            </div>
          </div>
        )}
      </form>
    </div>
  );
}
