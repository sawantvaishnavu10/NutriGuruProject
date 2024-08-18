import React, { useEffect, useState } from 'react';
import AppointmentService from '../Service/AppointmentService';
import { useLocation, useNavigate } from "react-router-dom";
import NutritionistService from '../Service/NutritionistService';
 import '../Css/AllAppointmentCss.css';

// Define the AllAppointment component
export default function AllAppointment() {
    const location = useLocation();
    const navigate = useNavigate();
    const user = location.state?.user; 
    const nutriEmail = location.state?.nutriEmail; 
    const [nutriId, setNutriId] = useState();
    console.log("user logged in ", user);
    console.log("nutriEmail ", nutriEmail);

    const [appointments, setAppointments] = useState([]); // Initialize as an empty array

    useEffect(() => {
        NutritionistService.getNutriIdByEmail(nutriEmail)
            .then((resp) => {
                console.log("Response data: ", resp.data);
                setNutriId(resp.data);
            })
            .catch((err) => {
                console.error("Error fetching nutritionist ID: ", err);
            });
    }, [nutriEmail]);

    useEffect(() => {
        if (nutriId) {
            fetchAllAppointments(nutriId);
        }
    }, [nutriId]);

    const fetchAllAppointments = (nutriId) => {
        AppointmentService.getAll(nutriId)
            .then((resp) => {
                console.log("Response data: ", resp.data);
                setAppointments(resp.data);
            })
            .catch((err) => {
                console.error("Error fetching appointments: ", err);
            });
    };

    const addDietPlan = (cid, email) => {
        console.log("sending client id and email ", cid, email);
        navigate("/AddDiet", { state: { cid: cid, email: email, nutriEmail: nutriEmail } });
    };

    const logOut = () => {
        navigate("/signIn");
    };

    return (
        <div className='allAppointments'>
            <button className="allAppointments-log-out" onClick={logOut}>
                Log Out 
            </button>
            <div className="allAppointments-container">
                <h2>Appointment Details</h2>
                <table className="allAppointments-appointment-Table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Time Slot</th>
                            <th>Client Name</th>
                            {/* <th className='addbtnhead'></th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {appointments.map((appointment) => (
                            <tr key={appointment.id}>
                                <td>{appointment.date}</td>
                                <td>{appointment.timeSlot}</td>
                                <td>{appointment.clientName}</td>
                                <td>
                                    <button className='allAppointments-add-diet-plan' onClick={() => addDietPlan(appointment.clientId, appointment.clientEmail)}>
                                        Assign Diet Plan
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
