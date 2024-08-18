import React from 'react';
import '../Css/AboutUs.css';
import { useLocation, useNavigate } from "react-router-dom";


const About = () => {
    const location = useLocation();

    const clientEmail = location.state?.clientEmail;

    return (
        <div className="about-background-c">
            <div className="about-container-c">
                <h3 className="py-3-c">About Us</h3>
                <p className="about-text-c">
                    NutriGuru is a platform for booking medical 
                    consultations with specialist nutritionists online. Client can book an appointment 
                    by selecting any of the time slots given by the nutritionist. NutriGuru:Book Your 
                    Bite empowers users to take control of their health by 
                    connecting them with experts who can offer personalized plans.
                </p>
                <p className="about-text-muted-c">Regards, from creators:
                    <ul className="about-ul-c">
                        <li className="about-li-c"><b>Jui Chavan</b></li>
                        <h6>abc@gmail.com</h6>
                        <li className="about-li-c"><b>Vaishnvai Sawant</b></li>
                        <h6>xyz@gmail.com</h6>

                    </ul>
                </p>
            </div>
        </div>
    );
}

export default About;
