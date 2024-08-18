import axios from "axios";
const baseUrl = "http://localhost:9090/user";

class UserService {
  signIn(credentials) {
    return axios.post(`http://localhost:9090/user/signIn`, credentials, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true, // If the backend requires cookies or other credentials
    });
  }

 /* signUp(credentials) {
    return axios.post(`${baseUrl}/signUp`, credentials, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true, // If the backend requires cookies or other credentials
    });
  }*/
  signUpForClient(credentials){
    return axios.post(`${baseUrl}/signUp`, credentials, {
      headers: {
        'Content-Type': 'application/json'
      },
      withCredentials: true // If the backend requires cookies or other credentials

    });
  }
  signUpForNutritionist(credentials){
    return axios.post(`http://localhost:9090/nutritionist/registerNutritionist`, credentials, {
      headers: {
        'Content-Type': 'application/json'
      },
      withCredentials: true // If the backend requires cookies or other credentials

    });
  }

  getByEmail(email) {
    return axios.get(`${baseUrl}/findByEmail`, {
      params: { email },
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true, // If the backend requires cookies or other credentials
      maxRedirects: 0, // Prevent automatic redirection handling by axios
    });
  }

  getDietPlan = (clientId) => {
    return axios.get(`http://localhost:9090/client/getDietPlan/${clientId}`, {
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });
  };
}

export default new UserService();
