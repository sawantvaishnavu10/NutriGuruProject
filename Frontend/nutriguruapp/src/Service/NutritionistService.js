import axios from "axios";
var baseUrl = "http://localhost:9090/nutritionist/";

class NutritionistService {
  getAll = () => {
    return axios.get(baseUrl + "getAllNutritionist");
  };

  getAllTimeSlots = () => {
    return axios.get("http://localhost:9090/client/showAllTimeSlots");
  };

  // getNutritionistById=()=>{
  //         return axios.get();
  //     }

  addDietPlan = (clientId, dietPlanDto) => {
    return axios.post(`${baseUrl}setDietPlan/${clientId}`, dietPlanDto, {
      headers: {
        'Content-Type': 'application/json',
      },
      withCredentials: true, // If your backend requires credentials
    });
  };

  getNutriIdByEmail = (email) => {
    return axios.get(`${baseUrl}getNutriIdByEmail/${email}`,{
      headers: {
        'Content-Type': 'application/json',
      },
      withCredentials: true, // If your backend requires credentials
    });
  };
}

export default new NutritionistService();
