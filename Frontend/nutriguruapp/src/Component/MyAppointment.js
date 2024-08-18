import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import UserService from "../Service/UserService";
import { useNavigate } from "react-router-dom";
import '../Css/MyAppoinmtmnet.css';

export default function MyAppointment() {
  const location = useLocation();
  const navigate = useNavigate();
  const email = location.state?.clientEmail;
  const [clientName, setClientName] = useState("");
  const [clientId, setClientId] = useState(null);
  const [dietPlan, setDietPlan] = useState(null);
  const [showDietPlan, setShowDietPlan] = useState(false);
  const [client, setClient] = useState("");
  useEffect(() => {
    setClientName(email);
    if (email) {
      UserService.getByEmail(email)
        .then((resp) => {
          console.log("client info ", resp.data);
          setClientName(resp.data.name);
          setClientId(resp.data.id);
          setClient(resp.data);
          console.log("--client info ", client);
        })
        .catch((err) => {
          if (err.response && err.response.status === 302) {
            console.log("Redirect detected:", err.response.headers.location);
          } else {
            console.log("Error ", err);
          }
        });
    }
  }, [email]);

  const handleShowDietPlan = () => {
    if (clientId) {
      navigate(`/showDietPlan/${clientId}`);
    }
  };
  return (
    <div className="myInfo">
      <div className="my-appointment-container-c">
        <h2>Client Information</h2>
        <p>
          <b>Name:</b> {clientName}
        </p>
        <p>
          <b>Email:</b> {email}
        </p>
        <p>
          <b>Age:</b> {client.age}
        </p>
        <p>
          <b>Contact:</b> {client.contact}
        </p>
        <p>
          <b>Address:</b> {client.address?.addressLine},{client.address?.state}
        </p>
        <button
          id="my-appointment-container-c-btn"
          onClick={handleShowDietPlan}
        >
          Show Diet Plan
        </button>
      </div>
    </div>
  );
}
