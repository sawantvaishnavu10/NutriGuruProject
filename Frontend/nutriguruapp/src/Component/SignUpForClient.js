import { useState } from "react";
import "../Css/SignUpForClient.css"
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
  const [state, setState] = useState("");
  const [coutryCode, setCoutryCode] = useState("");
  const [zipCode, setZipCode] = useState("");
  const [addressLine, setAddressLine] = useState("");
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
    setAddress((prevAddress) => ({
      ...prevAddress,
      [key]: value,
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

    UserService.signUpForClient(userDetails)
      .then((resp) => {
        console.log("User data:", resp.data);
        navigate("/signIn");
      })
      .catch((err) => {
        console.error(
          "Error in sign-up:",
          err.response ? err.response.data : err.message
        );
        navigate("/SignUpForClient");
      });
  };

  return (
    <div className="sign-up-n">
      <div className="sign-up-form-container-n">
        <form className="signUp-form-n">
          <div className="s-up-n">
            <p>Sign Up</p>
          </div>
          <label htmlFor="name" className="signUp-label-n">
            Full Name
          </label>
          <input
            className="signUp-input-n"
            id="name"
            type="text"
            value={name}
            onChange={(e) => setname(e.target.value)}
            required
          />
          <label htmlFor="email" className="signUp-label-n">
            Email
          </label>
          <input
            className="signIn-input-email"
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <label htmlFor="age" className="signUp-label-n">
            Age
          </label>
          <input
            className="signUp-input-n"
            id="age"
            type="number"
            value={age}
            onChange={(e) => setAge(e.target.value)}
            required
          />
          <label htmlFor="dob" className="signUp-label-n">
            DOB
          </label>
          <input
            className="signUp-input-n"
            id="dob"
            type="date"
            value={dob}
            onChange={(e) => setDob(e.target.value)}
            required
          />
          <label htmlFor="password" className="signUp-label-n">
            Password
          </label>
          <input
            className="signUp-input-n"
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label htmlFor="contact" className="signUp-label-n">
            Contact
          </label>
          <input
            className="signUp-input-n"
            id="contact"
            type="tel"
            value={contact}
            min={10}
            max={10}
            onChange={(e) => setContact(e.target.value)}
            required
          />
          <label htmlFor="addressLine" className="signUp-label-n">
            Address
          </label>
          <input
            className="signUp-input-n"
            id="addressLine"
            type="text"
            value={address.addressLine}
            onChange={(e) => updateAddress("addressLine", e.target.value)}
            required
          />
          <label htmlFor="state" className="signUp-label-n">
            State
          </label>
          <input
            className="signUp-input-n"
            id="state"
            type="text"
            value={address.state}
            onChange={(e) => updateAddress("state", e.target.value)}
            required
          />
          <label htmlFor="countryCode" className="signUp-label-n">
            Country Code
          </label>
          <input
            className="signUp-input-n"
            id="countryCode"
            type="number"
            value={address.coutryCode}
            onChange={(e) => updateAddress("coutryCode", e.target.value)}
            required
          />
          <label htmlFor="zipCode" className="signUp-label-n">
            Zip Code
          </label>
          <input
            className="signUp-input-n"
            id="zipCode"
            type="number"
            value={address.zipCode}
            onChange={(e) => updateAddress("zipCode", e.target.value)}
            required
          />

          <label htmlFor="role" className="signUp-label-n">
            Role
          </label>
          <div id="role" className="signUp-radio-role-n">
            <label>
              <input
                className="opt-n"
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
                className="opt-n"
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
            className="signUp-btnSubmit-n"
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
