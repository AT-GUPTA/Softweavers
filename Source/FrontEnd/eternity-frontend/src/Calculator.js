import './Calculator.css';
import {useEffect, useState} from 'react';

import Settings from "./components/Settings";
import Basic from "./components/Basic";
import Scientific from "./components/Scientific";
import History from "./components/History";

function Calculator() {
    const [isChecked, setIsChecked] = useState(false);
    const [histories, setHistories] = useState([]);
    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

    const handleChangeHistory = () => {
        const display = document.getElementById("display");
        setHistories(histories => ([...histories,
            <option key={histories.length + 1} value={display.value}>{display.value}</option>]))
    };

    // The following is the code responsible for the actions behind both basic and scientific
    // calculator versions.
    useEffect(() => {
        const display = document.getElementById("display");
        const input_buttons = Array.from(document.querySelectorAll(".operator, .number"));

        // Handle adding the text of the button to the display of the calculator
        input_buttons.forEach(function (button) {
            // Add a click event listener
            button.addEventListener("click", () => {
                // Append the button's value to the display
                display.value += button.textContent;
            })
        })
    });

    function clearDisplay() {
        const display = document.getElementById("display");
        display.value = "";
    }

    return (
        <div className="columns">
            {isChecked ? (
                <Basic clearFunction={clearDisplay}/>
            ) : (
                <Scientific clearFunction={clearDisplay} execute={handleChangeHistory}/>
            )}
            <div className="second-column">
                <Settings handleCheckbox={handleCheckboxChange}/>
                <History histories={histories}/>
            </div>

        </div>
    );
}

export default Calculator;
