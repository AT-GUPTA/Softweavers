import './Calculator.css';
import {useState} from 'react';

import Settings from "./components/Settings";
import Scientific from "./components/Scientific";
import History from "./components/History";

function Calculator() {
    const [isChecked, setIsChecked] = useState(false);
    const [histories, setHistories] = useState([]);

    const handleCheckboxChange = (event) => {
        setIsChecked(event.target.checked);
    };

    const handleChangeHistory = (display) => {
        setHistories(histories => ([...histories,
            <option key={histories.length + 1} value={display.value.split('=')[0]}>{display.value.split('=')[0]}</option>]))
    };

    function isValid(str) {
        const errorTextArea = document.getElementById("errorMsg");

        // Make sure we have a closing parentheses for all the open ones
        var openCount = (str.match(/\(/g) || []).length;
        var closeCount = (str.match(/\)/g) || []).length;

        if(closeCount !== openCount) {
            errorTextArea.value = "For each '(' there must be a corresponding ')'";
            return false;
        }

        // fix for preventing functions not starting with '('
        const functions = ["log", "mad", "sd", "sqrt", "arccos", "sinh", "gamma", "x^y", "x", "e", ""]
        for (let i = 0; i < functions.length-2; i++){
            // loop until we find which function we are processing
            if(str.includes(functions[i])){
                //Check for the proper form
                if(!str.includes(functions[i] + "(")){
                    errorTextArea.value = "Functions must start with '('";
                    return false
                }
            }
        }

        // Remove everything but the characters for the functions
        const words = str.replace(/[0-9]|,|\.|\+|-|\*|\/|π|\)/g, '').toLowerCase().split('(');
        console.log(words)

        // Loop through each word and check if it's in the predetermined array
        for (let i = 0; i < words.length; i++) {
            if (!functions.includes(words[i])) {
                // If a word isn't in the array, return false
                // const word = words.filter(e => e.length);
                errorTextArea.value = "Unknown function: " + words[i];
                return false;
            }
        }

        // If all words are in the array, return true
        errorTextArea.value = '';
        return true;
    }

    function execute() {
        const display = document.getElementById("display");

        if(isValid(display.value)){
            handleChangeHistory(display);
            if(display.value.includes('=') === false)
                display.value += "=";
        }

    }
    return (
        <div className="columns">
            <Scientific execute={execute}/>
            <div className="second-column">
                <Settings handleCheckbox={handleCheckboxChange}/>
                <History histories={histories}/>
            </div>

        </div>
    );
}

export default Calculator;