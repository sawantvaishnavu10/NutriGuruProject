// DietPlan.js
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import UserService from "../Service/UserService";
import '../Css/ShowDietPlan.css'
export default function DietPlan() {
  const { clientId } = useParams();
  const [dietPlan, setDietPlan] = useState(null);
  const [appointments, setAppointments] = useState([]);
  const [nutritionistId, setNutritionistId] = useState(null);
  const [program,setProgram]=useState('');
  useEffect(() => {
    if (clientId) {
      UserService.getDietPlan(clientId)
        .then((resp) => {
          console.log(resp.data)
          if(resp.data.program=="TWELVE"){
            setProgram("12 - Week");
          }
          else if(resp.data.program=="FOUR"){
            setProgram("4 - Week");
          }
          else if(resp.data.program=="THREE"){
            setProgram("3 - Week");
          }
          setDietPlan(resp.data);
          setAppointments(resp.data.bookAppointmentIds || []);
          setNutritionistId(resp.data.nutritionistId);
        })
        .catch((err) => {
          console.log("Error ", err);
        });
    }
  }, [clientId]);

  if (!dietPlan) {
    return <div>Loading...</div>;
  }

  return (
    <div className="myPlan">
      <div className="showMyPlan">
        <h2>Diet Plan Information</h2>
        <p>
          <b>Name:</b> {dietPlan.name}
        </p>
        <p>
          <b>Program:</b> {program} 
        </p>
        <p>
          <b>Description:</b> {dietPlan.description}
        </p>
      </div>
    </div>
  );
}
