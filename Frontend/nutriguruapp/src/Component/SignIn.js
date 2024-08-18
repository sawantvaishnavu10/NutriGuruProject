import { useState } from "react";
import '../Css/SignIn.css';
import { Link, useNavigate } from "react-router-dom";
import UserService from "../Service/UserService";

export default function SignIn() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [invalid, setInvalid] = useState("");

  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    UserService.signIn({ email, password })
      .then((resp) => {
        console.log("sign-in response", resp);

        if (resp.data) {
          const user = resp.data;
          const nutriEmail=resp.data.email;
          setInvalid("");
          console.log("--user--",user);
          const clientEmail=resp.data.email;

          // Navigate based on role
          if (user.role === "NUTRITIONIST") {
            navigate('/AllAppointment', { state: { user: user ,nutriEmail:nutriEmail} });
          } else if (user.role === "CLIENT") {
            navigate('/home', { state: { clientEmail:resp.data.email, } });
          } else {
            // Handle other roles or unknown roles
            navigate('/home'); // Default or error page
          }
        }
      })
      .catch((err) => {
        setInvalid("Invalid User or Password");
        console.log("Error in sign-in", err.response ? err.response.data : err.message);
      });
  };

  return (
    <div className="sign-in-c">
      <div className="sign-in-form-container-c">
        <form className="signIn-form-c" onSubmit={handleSubmit}>
          <div className="signin-tagline-c">
            <p>Sign In</p>
          </div>
          <label htmlFor="email" className="signIn-label-c">Email</label>
          <br />
          <input
            className="signIn-input-c"
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <br />
          <label htmlFor="password" className="signIn-label-c">Password</label>
          <input
            className="signIn-input-c"
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <br />
          <div className="invalid-user-c">
            <p>{invalid}</p>
          </div>
          <button
            className="signIn-btnSubmit-c"
            type="submit"
          >
            Submit
          </button>
          <br />
          <br />
          <div className="sign-up-link-c">
            <h6>
              Don't have an account <span>?</span>
              <Link to="/SignUpForClient"> Sign up as Client </Link>
            / <Link to="/SignUpForNutritionist"> Sign up as Nutritionist </Link>
            </h6>
          </div>
          <footer className="signIn-page-footer-c">
            <p>&copy; 2024 Nutriguru. All rights reserved.</p>
          </footer>
        </form>
      </div>
    </div>
  );
}
