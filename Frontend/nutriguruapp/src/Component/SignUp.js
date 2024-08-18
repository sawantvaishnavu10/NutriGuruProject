import { useState } from "react";
import "../Css/SignUp.css";
import UserService from "../Service/UserService";
import { useNavigate } from "react-router-dom";

export default function SignUp() {
  const [name, setname] = useState("");
  const [age, setAge] = useState("");
  const [dob, setDob] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [contact, setContact] = useState("");
  const [role, setRole] = useState("");
  const [state,setState]=useState("");
  const [coutryCode,setCoutryCode]=useState("");
  const [zipCode,setZipCode]=useState("");
  const [addressLine,setAddressLine]=useState("");
  const [address, setAddress] = useState({
    addressLine: "",
    state: "",
    coutryCode: 0,
    zipCode: 0,
  });

  const validateEmail = (email) => {
    const emailFormat = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{3,}$/;
    return emailFormat.test(email);
  };

  const navigate = useNavigate();

  const updateAddress = (key, value) => {
    setAddress(prevAddress => ({
      ...prevAddress,
      [key]: value
    }));
  };
  

  const submit = () => {
    if (!name || !email || !password || !contact || !age || !dob || !role) {
      alert("Please fill out all required fields.");
      return;
    }

    if (parseInt(age) < 18) {
      alert("You must be at least 18 years old to create an account.");
      return;
    }

    // Validate email format
    if (!validateEmail(email)) {
      alert("Please enter a valid email address.");
      return;
    }
    setAddress(address);
    const userDetails = {
      name,
      email,
      password,
      contact,
      age,
      dob,
      address,
      role,
    };

    UserService.signUp(userDetails)
      .then((resp) => {
        console.log("User data:", resp.data);
        navigate("/signIn");
      })
      .catch((err) => {
        console.error(
          "Error in sign-up:",
          err.response ? err.response.data : err.message
        );
        navigate("/signUp");
      });
  };

  return (
    <div className="sign-up">
      <div className="sign-up-form-container">
      
        <form className="signUp-form">
        <div className="s-up">
        <p>Sign Up</p>
      </div>
          <label htmlFor="name" className="signUp-label">
            Full Name
          </label>
          <input
            className="signUp-input"
            id="name"
            type="text"
            value={name}
            onChange={(e) => setname(e.target.value)}
            required
          />
          <label htmlFor="email" className="signUp-label">
            Email
          </label>
          <input
            className="signIn-input"
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <label htmlFor="age" className="signUp-label">
            Age
          </label>
          <input
            className="signUp-input"
            id="age"
            type="number"
            value={age}
            onChange={(e) => setAge(e.target.value)}
            required
          />
          <label htmlFor="dob" className="signUp-label">
            DOB
          </label>
          <input
            className="signUp-input"
            id="dob"
            type="date"
            value={dob}
            onChange={(e) => setDob(e.target.value)}
            required
          />
          <label htmlFor="password" className="signUp-label">
            Password
          </label>
          <input
            className="signUp-input"
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label htmlFor="contact" className="signUp-label">
            Contact
          </label>
          <input
            className="signUp-input"
            id="contact"
            type="tel"
            value={contact}
            min={10}
            max={10}
            onChange={(e) => setContact(e.target.value)}
            required
          />
         <label htmlFor="addressLine" className="signUp-label">
  Address
</label>
<input
  className="signUp-input"
  id="addressLine"
  type="text"
  value={address.addressLine}
  onChange={(e) => updateAddress('addressLine', e.target.value)}
  required
/>
<label htmlFor="state" className="signUp-label">
  State
</label>
<input
  className="signUp-input"
  id="state"
  type="text"
  value={address.state}
  onChange={(e) => updateAddress('state', e.target.value)}
  required
/>
<label htmlFor="countryCode" className="signUp-label">
  Country Code
</label>
<input
  className="signUp-input"
  id="countryCode"
  type="number"
  value={address.coutryCode}
  onChange={(e) => updateAddress('coutryCode', e.target.value)}
  required
/>
<label htmlFor="zipCode" className="signUp-label">
  
</label>
<input
  className="signUp-input"
  id="zipCode"
  type="number"
  value={address.zipCode}
  onChange={(e) => updateAddress('zipCode', e.target.value)}
  required
/>

          <label htmlFor="role" className="signUp-label">
            Role
          </label>
          <div id="role" className="signUp-radio-role">
            <label>
              <input
                className="opt"
                type="radio"
                name="role"
                value="CLIENT"
                checked={role === "CLIENT"}
                onChange={(e) => setRole(e.target.value)}
                required
              />
              CLIENT
            </label>
            <label>
              <input
                className="opt"
                type="radio"
                name="role"
                value="NUTRITIONIST"
                checked={role === "NUTRITIONIST"}
                onChange={(e) => setRole(e.target.value)}
                required
              />
              NUTRITIONIST
            </label>
          </div>
          <button
            className="signUp-btnSubmit"
            type="button"
            onClick={() =>
              submit(name, email, password, contact, age, dob, address)
            }
          >
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
}
